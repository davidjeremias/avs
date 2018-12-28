package br.gov.caixa.siavs.security;

import java.security.Principal;
import java.security.acl.Group;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;
import javax.servlet.http.HttpServletRequest;

import org.jboss.security.SimpleGroup;

import br.com.spread.framework.util.Util;

/**
 * <b>Title:</b> AbstractLoginModule <br>
 * <b>Description:</b> Classe abstrata que define o funcionamento básico das classes login do sistema. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: $, $Date: $
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractLoginModule implements LoginModule {

    // Estado inicial
    private Subject subject;
    private Map sharedState;
	private Map options;

    // Opções de configuração
    private boolean debug = false;

    // Status da autenticação
    private boolean sucesso = false;
    private boolean commitSucesso = false;
    
    private Principal usuario;
    private List<Principal> listaRoles;
    private HttpServletRequest request;
    
// ***********************************************************************************************************************************

    public static final String WEB_REQUEST_KEY = "javax.servlet.http.HttpServletRequest";
    
 // ***********************************************************************************************************************************

    /**
     * @see javax.security.auth.spi.LoginModule#initialize(javax.security.auth.Subject, javax.security.auth.callback.CallbackHandler, java.util.Map, java.util.Map)
     */
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.setSubject(subject);
		this.setSharedState(sharedState);
		this.setOptions(options);
		
		try {
			// Recupera o request e response para tratar as mensagens
			this.setRequest((HttpServletRequest) PolicyContext.getContext(WEB_REQUEST_KEY));
		}
		catch (PolicyContextException e) {
			if(this.isDebug()){
				e.printStackTrace();
			}
		}
		
		this.setDebug("true".equalsIgnoreCase((String) this.getOptions().get("debug")));
	}

	/**
	 * @see javax.security.auth.spi.LoginModule#login()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean login() throws LoginException {
		String msg;
		String login = null;
		this.setSucesso(false);
		try {
			login = this.recuperarUsuarioLogado();
			this.setUsuario(this.carregarUsuario(login));
		}
		catch (Exception e) {
			if(this.isDebug()){
				e.printStackTrace();
			}
			// Coloca a mensagem na sessão para poder ser exibida
			msg = "Não foi possível carregar os dados do usuário!";
			this.getRequest().getSession().setAttribute("msgErro", msg);
			throw new LoginException(msg);
		}
		
		if(this.getUsuario() == null){
			msg = "Usuário " + (Util.notEmpty(login) ? "'" + login + "' " : "") + "não encontrado!";
			this.getRequest().getSession().setAttribute("msgErro", msg);
			throw new LoginException(msg);
		}
		
		this.setSucesso(true);
		this.getSharedState().put("javax.security.auth.principal", this.getUsuario());
		return this.isSucesso();
	}

	/**
	 * @see br.gov.caixa.security.CaixaLoginModule#commit()
	 */
	@Override
	public boolean commit() throws LoginException {
		if (! this.isSucesso()) {
			return false;
		}

		// Atualiza os dados do usuário
		this.setUsuario(this.atualizarUsuario(this.getUsuario()));
		LinkedList<Principal> listaTemp = new LinkedList<Principal>();
		// Adiciona o usuário do SIAVS porque será considerado o User Principal
		listaTemp.add(this.getUsuario());
		// Limpa os registros anteriores
		this.getSubject().getPrincipals().clear();
		// Adiciona a nova lista
		this.getSubject().getPrincipals().addAll(listaTemp);
		// Adiciona as roles
		this.adicionarRoles(this.getListaRoles());
		
		this.setCommitSucesso(true);
		return commitSucesso;
	}
	
	/**
	 * @see javax.security.auth.spi.LoginModule#abort()
	 */
	@Override
	public boolean abort() throws LoginException {
		if (! this.isSucesso()) {
			return false;
		}
		else if (this.isSucesso() && !this.isCommitSucesso()) {
			// Login passou mas falhou repassando as informações para o servidor
			this.setSucesso(false);
			this.setUsuario(null);
		}
		else {
			this.logout();
		}
		return true;
	}

	/**
	 * @see javax.security.auth.spi.LoginModule#logout()
	 */
	@Override
	public boolean logout() throws LoginException {
		this.getSubject().getPrincipals().remove(this.getUsuario());
		this.setSucesso(false);
		this.setCommitSucesso(false);
		this.setUsuario(null);
		return true;
	}
	
// ***********************************************************************************************************************************
	
	/**
	 * Carrega o usuário do sistema.
	 * @param matricula String
	 * @return Principal
	 * @throws Exception
	 */
	protected abstract Principal carregarUsuario(String matricula) throws Exception;
	
	/**
	 * Atualiza os dados do usuário durante a execução do Commit, onde os dados de logins anteriores já estão registrados. Deve ser sobrescrito caso seja necessária alguma intervenção.
	 * @param principal Principal
	 * @return Principal
	 */
	protected Principal atualizarUsuario(Principal principal) {
		return usuario;
	}

	/**
	 * Adiciona as roles para os perfis de acesso.
	 * @param listapPerfilAcesso List<Principal>
	 */
	protected void adicionarRoles(List<Principal> listaRoles) {
		if(! Util.notEmpty(listaRoles)){
			return;
		}
		
		String nomeGrupo = "Roles";
		Group roles = null;
		for (Principal principal : this.getSubject().getPrincipals()) {
			// Se for um grupo com o mesmo nome
			if(principal instanceof Group && nomeGrupo.equals(principal.getName())){
				roles = (Group) principal;
			}
		}
		
		if(roles == null){
			roles = new SimpleGroup(nomeGrupo);
			this.getSubject().getPrincipals().add(roles);
		}
		
		for (Principal role : listaRoles) {
			roles.addMember(role);
		} 
	}
	
	/**
	 * Retorna o IP do usuário.
	 * @return String
	 */
	protected String getIP(){
		// Quando a requisição passa pelo Apache
		String ip = this.getRequest().getHeader("X-FORWARDED-FOR");
		// Se não encontrou
		if(! Util.notEmpty(ip)){
			ip = this.getRequest().getRemoteAddr();
		}
		
		return ip;
	}

	/**
	 * Recupera a matrícula utilizada no login.
	 * @return String
	 * @throws LoginException
	 */
	private String recuperarMatricula() throws LoginException {
		String matricula = null;
		Object login = sharedState.get("javax.security.auth.login.name");
		
		if(login != null && login.toString().length() >= 7) {
			matricula = login.toString().split("@")[0];
		}
		
		return matricula;
	}
	
// ***********************************************************************************************************************************
	
	private String recuperarUsuarioLogado() throws LoginException {
		return this.recuperarMatricula();
	}
	
// ***********************************************************************************************************************************
	
	/**
	 * @return Subject
	 */
	protected Subject getSubject() {
		return subject;
	}

	/**
	 * @param subject Subject
	 */
	protected void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	/**
	 * @return Map
	 */
	private Map getSharedState() {
		return sharedState;
	}

	/**
	 * @param sharedState Map
	 */
	private void setSharedState(Map sharedState) {
		this.sharedState = sharedState;
	}

	/**
	 * @return Map
	 */
	private Map getOptions() {
		return options;
	}

	/**
	 * @param options Map
	 */
	private void setOptions(Map options) {
		this.options = options;
	}

	/**
	 * @return boolean
	 */
	private boolean isDebug() {
		return debug;
	}

	/**
	 * @param debug boolean
	 */
	private void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * @return boolean
	 */
	private boolean isSucesso() {
		return sucesso;
	}

	/**
	 * @param sucesso boolean
	 */
	private void setSucesso(boolean sucesso) {
		this.sucesso = sucesso;
	}

	/**
	 * @return boolean
	 */
	private boolean isCommitSucesso() {
		return commitSucesso;
	}

	/**
	 * @param commitSucesso boolean
	 */
	private void setCommitSucesso(boolean commitSucesso) {
		this.commitSucesso = commitSucesso;
	}

	/**
	 * @return Principal
	 */
	protected Principal getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario Principal
	 */
	private void setUsuario(Principal usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return HttpServletRequest
	 */
	protected HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * @param request HttpServletRequest
	 */
	private void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	/**
	 * @return List<Principal>
	 */
	protected List<Principal> getListaRoles() {
		return this.listaRoles;
	}

	/**
	 * @param listaRoles List<Principal>
	 */
	protected void setListaRoles(List<Principal> listaRoles) {
		this.listaRoles = listaRoles;
	}
	
	public static void main(String[] args) {
		String login = "C012345";
		
		String matricula = null;
		
		if(login != null && login.toString().length() >= 7) {
			matricula = login.split("@")[0];
		}
		
		System.out.println(matricula);

		System.out.println(Util.desformatarMatriculaUsuarioCaixa(matricula));
	}
}