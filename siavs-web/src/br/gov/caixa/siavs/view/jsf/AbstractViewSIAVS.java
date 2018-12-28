package br.gov.caixa.siavs.view.jsf;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.richfaces.component.SortOrder;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.global.Dominio.Operacao;
import br.com.spread.framework.global.Dominio.TipoConteudoWeb;
import br.com.spread.framework.service.IService;
import br.com.spread.framework.util.Util;
import br.com.spread.framework.view.jsf.AbstractViewJSF;
import br.com.spread.framework.view.report.Relatorio;
import br.com.spread.framework.view.util.UtilView;
import br.gov.caixa.siavs.factory.FactorySIAVS;
import br.gov.caixa.siavs.service.client.FuncionarioCaixaSR;
import br.gov.caixa.siavs.vo.AbstractVOSIAVS;
import br.gov.caixa.siavs.vo.FuncionarioCaixaLocalVO;
import br.gov.caixa.siavs.vo.FuncionarioCaixaVO;
import br.gov.caixa.siavs.vo.UnidadeVO;
import br.gov.caixa.siavs.vo.UsuarioVO;
import net.sf.jasperreports.engine.JRParameter;

/**
 * <b>Title:</b> AbstractViewSIAVS <br>
 * <b>Description:</b> Classe abstrata que define o padrão básico das classes da
 * camada de visão do sistema SIAVS. <br>
 * <b>Company:</b> Spread
 *
 * @author leandro.vieira
 * @version: $Revision: $, $Date: $
 */
public abstract class AbstractViewSIAVS<T extends AbstractVOSIAVS> extends AbstractViewJSF<T> {

	private static final long serialVersionUID = 4108926083744449033L;

	// ***********************************************************************************************************************************

	public static final String TELA_PADRAO = "manter";
	private static final String RAIZ_RELATORIOS = File.separator + "WEB-INF" + File.separator + "relatorios"
			+ File.separator;

	// ***********************************************************************************************************************************

	private Map<String, SortOrder> mapOrdenacoes;
	/**
	 * Se a operação teve sucesso.
	 */
	private boolean opSucesso;

	@Inject
	private FuncionarioCaixaSR funcionarioCaixaSR;


	// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.view.basic.AbstractViewBasic#getService()
	 */
	@Override
	protected IService<T> getService() {
		if (super.getService() == null) {
			try {
				// Recupera a classe utilizando a factory
				super.setService((IService<T>) FactorySIAVS.getService(this.getClasseServiceRemoto()));
			} catch (Throwable e) {
				this.getLog().error("Erro ao tentar recuperar automaticamente o serviço.", e);
			}
		}
		return super.getService();
	}

	/**
	 * @see br.com.spread.framework.view.jsf.AbstractViewJSF#getTelaInicio
	 */
	@Override
	protected String getTelaInicio() {
		// Lista os registros
		this.listar(null);
		this.setOperacao(Operacao.LISTAR);
		return this.getNomePadrao();
	}

	/**
	 * @see br.com.spread.framework.view.basic.AbstractViewBasic#doExcluir
	 */
	@Override
	protected void doExcluir(T vo) throws SystemException, BusinessException {
		// Exclui o registro
		super.doExcluir(vo);
		// Direciona para a tela de início
		this.setRetorno(this.irTelaInicio());
	}

	/**
	 * @see br.com.spread.framework.view.basic.AbstractViewBasic#doListar
	 */
	@Override
	protected void doListar(T vo) throws SystemException, BusinessException {
		// A entrada foi pelo irTelaInicio
		if (vo == null) {
			// Limpa a vo utilizada
			this.limparVO();
		}
		super.doListar(vo);
	}

	/**
	 * @see br.com.spread.framework.view.basic.AbstractViewBasic#getNomePadrao()
	 */
	@Override
	public String getNomePadrao() {
		return TELA_PADRAO + super.getNomePadrao();
	}

	/**
	 * @see br.com.spread.framework.view.jsf.AbstractViewJSF#tratarExcecao(java.lang.Throwable,
	 *      br.com.spread.framework.global.Dominio.Operacao)
	 */
	@Override
	protected void tratarExcecao(Throwable excecao, Operacao operacao) {
		this.setOpSucesso(false);
		super.tratarExcecao(excecao, operacao);
	}

	/**
	 * @see br.com.spread.framework.view.jsf.AbstractViewJSF#tratarSucesso(br.com.spread.framework.global.Dominio.Operacao)
	 */
	@Override
	protected void tratarSucesso(Operacao operacao) {
		this.setOpSucesso(true);
		super.tratarSucesso(operacao);
	}
	// ***********************************************************************************************************************************

	/**
	 * Direciona para a tela de cadastro.
	 *
	 * @return String
	 */
	public String irTelaCadastro() {
		// Limpa a vo utilizada
		this.limparVO();
		this.setOperacao(Operacao.INCLUIR);

		return this.irTelaCadastroAlteracao();
	}

	/**
	 * Direciona para a tela de alteração.
	 *
	 * @param objeto
	 *            T
	 * @return String
	 */
	public String irTelaAlteracao(T objeto) {
		this.setVo(objeto);
		this.setOperacao(Operacao.ALTERAR);

		return this.irTelaCadastroAlteracao();
	}

	private String irTelaCadastroAlteracao() {
		// Avisa ao controlador qual é a View utilizada no momento
		this.getControladorView().setAction(this);
		return this.getTelaCadastro();
	}

	/**
	 * Limpa a vo utilizada na tela.
	 */
	@SuppressWarnings("unchecked")
	protected void limparVO() {
		this.setVo((T) Util.criarInstanciaGenerics(this));
		this.setVoFiltro((T) Util.criarInstanciaGenerics(this));
	}

	/**
	 * Ordena pela coluna selecionada.
	 *
	 * @return coluna String
	 * @return recuperar Boolean
	 * @return SortOrder
	 */
	public SortOrder ordenar(String coluna, Boolean recuperar) {
		// Recupera o tipo de ordenação
		SortOrder ordenacao = this.getMapOrdenacoes().get(coluna);
		if (ordenacao == null) {
			ordenacao = SortOrder.ascending;
		}

		if (!recuperar) {
			// Inverte a seleção
			ordenacao = ordenacao.equals(SortOrder.ascending) ? SortOrder.descending : SortOrder.ascending;
			// Remove as ordenações anteriores
			for (String nomeColuna : this.getMapOrdenacoes().keySet()) {
				this.getMapOrdenacoes().put(nomeColuna, SortOrder.unsorted);
			}
		}
		// Armazena a nova ordenação
		this.getMapOrdenacoes().put(coluna, ordenacao);

		return ordenacao;
	}

	/**
	 * @return String
	 */
	protected String getTelaCadastro() {
		return this.getNomePadrao();
	}

	/**
	 * Retorna o título da tela.
	 *
	 * @return String
	 */
	public String getTitulo() {
		return super.getNomePadrao();
	}

	/**
	 * Se permite alterar.
	 *
	 * @param vo
	 *            T
	 * @return Boolean
	 */
	public Boolean permiteAlterar(T vo) {
		return true;
	}

	/**
	 * Se permite alterar.
	 *
	 * @param vo
	 *            T
	 * @return Boolean
	 */
	public Boolean permiteExcluir(T vo) {
		return true;
	}

	/**
	 * Se permite incluir.
	 *
	 * @param vo
	 *            T
	 * @return Boolean
	 */
	public Boolean permiteIncluir() {
		return true;
	}

	/**
	 * Se é para exibir o título da tela.
	 *
	 * @return Boolean
	 */
	public Boolean getExibirTitulo() {
		return true;
	}

	/**
	 * Emite o relatório em xls.
	 *
	 * @param nomeArquivo
	 *            String
	 * @param parametros
	 *            Map<String, Object> parametros
	 * @param listaObjetos
	 *            Collection<Object>
	 */
	protected void emitirRelatorioXls(String nomeArquivo, Map<String, Object> parametros,
			Collection<Object> listaObjetos) {
		this.emitirRelatorio(TipoConteudoWeb.XLS, nomeArquivo, parametros, listaObjetos);
	}

	/**
	 * Emite o relatório no formato específicado.
	 *
	 * @param tipoConteudoWeb
	 *            TipoConteudoWeb
	 * @param nomeArquivo
	 *            String
	 * @param parametros
	 *            Map<String, Object> parametros
	 * @param listaObjetos
	 *            Collection<Object>
	 */
	protected void emitirRelatorio(TipoConteudoWeb tipoConteudoWeb, String nomeArquivo, Map<String, Object> parametros,
			Collection<Object> listaObjetos) {
		try {
			if (parametros == null) {
				parametros = new HashMap<String, Object>();
			}
			// Exibe o título das colunas apenas na primeira página
			parametros.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);

			new Relatorio()
					.setEnderecoRaiz(
							((ServletContext) this.getFacesContext().getExternalContext().getContext()).getRealPath("")
									+ RAIZ_RELATORIOS)
					.setHttpServletResponse(
							(HttpServletResponse) this.getFacesContext().getExternalContext().getResponse())
					.setTipoConteudoWeb(tipoConteudoWeb).emitirRelatorio(nomeArquivo, parametros, listaObjetos);
			this.getFacesContext().responseComplete();
		} catch (Throwable e) {
			this.tratarExcecao(e, null);
		}
	}

	/**
	 * Abre o arquivo.
	 *
	 * @param nomeArquivo
	 *            String
	 * @param arrayBytes
	 *            byte[]
	 */
	protected void abrirArquivo(String nomeArquivo, byte[] arrayBytes) {
		try {
			new UtilView()
					.setHttpServletResponse(
							(HttpServletResponse) this.getFacesContext().getExternalContext().getResponse())
					.abrirArquivo(nomeArquivo, arrayBytes);
			this.getFacesContext().responseComplete();
		} catch (Throwable e) {
			this.tratarExcecao(e, null);
		}
	}

	/**
	 * Retorna a quantidade de linhas que deve ser exibida na tabela.
	 *
	 * @return Integer
	 */
	public Integer getQtdeLinhasTabela() {
		return 0;
	}

	/**
	 * Retira os caracteres html do texto.
	 *
	 * @param valor
	 *            String
	 * @return String
	 */
	public String limparHtml(String valor) {
		return valor == null ? valor : StringEscapeUtils.unescapeHtml(valor.replaceAll("\\<.*?\\>", "").trim());
	}

	// ***********************************************************************************************************************************

	/**
	 * @return Map<String,SortOrder>
	 */
	private Map<String, SortOrder> getMapOrdenacoes() {
		if (mapOrdenacoes == null) {
			mapOrdenacoes = new HashMap<String, SortOrder>();
		}
		return mapOrdenacoes;
	}

	// ***********************************************************************************************************************************

	/**
	 * @return boolean
	 */
	public boolean isOpSucesso() {
		return opSucesso;
	}

	/**
	 * @param opSucesso
	 *            boolean
	 */
	public void setOpSucesso(boolean opSucesso) {
		this.opSucesso = opSucesso;
	}

	// ***********************************************************************************************************************************

	/**
	 * Retorna o usuário logado.
	 *
	 * @return String
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public UsuarioVO getUsuarioLogado() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest)facesContext.getExternalContext().getRequest();

		Cookie[] cookies = request.getCookies();
		String[] matricula = new String[6];
		for(int i = 0; i < cookies.length; i++) {
			if(cookies[i].getName().equals("keycloak")) {
				String values = cookies[i].getValue();
				matricula = values.split("-");
			}
		}

		System.out.println(matricula[0]);

		UsuarioVO usu = new UsuarioVO();
		usu.setIcAcesso("1");
		usu.setNuUsuario(9L);

		FuncionarioCaixaLocalVO func = new FuncionarioCaixaLocalVO();
		func.setNuFncroCaixaLocal(1L);

		UnidadeVO unidade = new UnidadeVO();
		unidade.setNuUnidade(57);
		unidade.setNuNatural(57);

		FuncionarioCaixaVO funcVo = new FuncionarioCaixaVO();
		funcVo.setNuMatricula("C577394");
		funcVo.setDeMatricula("C577394");
		funcVo.setNoFuncionario(matricula[0]);
		funcVo.setUnidade(unidade);
		funcVo.setNuTipoFuncao(43);

		func.setUnidade(unidade);
		func.setFuncionarioCaixa(funcVo);

		usu.setFuncionarioCaixaLocal(func);

		return usu;
	}

	/*public FuncionarioCaixaVO consultaUsuario() {
		try {
			FuncionarioCaixaVO func = new FuncionarioCaixaVO();
			func.setDeMatricula("C577394");
			FuncionarioCaixaVO funcionarioCaixaVO = funcionarioCaixaSR.consultar(func);
			return funcionarioCaixaVO;
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}*/


}