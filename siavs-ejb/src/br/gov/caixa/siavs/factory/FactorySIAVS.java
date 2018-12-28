package br.gov.caixa.siavs.factory;

import java.util.Properties;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.factory.AbstractFactory;
import br.com.spread.framework.factory.JNDIFactory;
import br.com.spread.framework.service.basic.IServiceBasic;
import br.com.spread.framework.util.Arquivo;
import br.com.spread.framework.util.Log;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.service.client.AgendamentoSR;
import br.gov.caixa.siavs.service.client.AvaliacaoSR;
import br.gov.caixa.siavs.service.client.FncroCaixaSegmentoSR;
import br.gov.caixa.siavs.service.client.FuncionarioCaixaLocalSR;
import br.gov.caixa.siavs.service.client.FuncionarioCaixaSR;
import br.gov.caixa.siavs.service.client.GrupoSR;
import br.gov.caixa.siavs.service.client.GrupoServicoSR;
import br.gov.caixa.siavs.service.client.GrupoUnidadeSR;
import br.gov.caixa.siavs.service.client.MetaSR;
import br.gov.caixa.siavs.service.client.NoticiaSR;
import br.gov.caixa.siavs.service.client.ProblemaSR;
import br.gov.caixa.siavs.service.client.SegmentoSR;
import br.gov.caixa.siavs.service.client.ServicoSR;
import br.gov.caixa.siavs.service.client.UnidadeSR;
import br.gov.caixa.siavs.service.client.UsuarioSR;

/**
 * <b>Title:</b> FactorySIAVS <br>
 * <b>Description:</b> Fábrica responsável por controlar a criação dos serviços e a conexão do sistema SIAVS. <br>
 * @author leandro.vieira
 * @version: $Revision: $, $Date: $$
 */
public class FactorySIAVS {

	private static Properties configuracoes;
	private static AbstractFactory fabrica;
	@Inject
	private static Log log;

	/**
	 * Construtor para bloquear a criação do objeto.
	 */
	static {
		try {
			configuracoes = Arquivo.carregarProperties(FactorySIAVS.class.getResourceAsStream("factory.properties"));
			fabrica = new JNDIFactory(configuracoes);
		}
		catch (SystemException e) {
			log.error(e);
		}
	}
	
	/**
	 * Retorna uma instância remota ou local do objeto.
	 * @param classeServico ClassClass<T extends AbstractService>
	 * @return T
	 * @throws SystemException
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T extends IServiceBasic<?>> T getService(Class<T> classeServico) throws SystemException {
		try {
			String nome = "java:";
			
			if(Util.notEmpty(configuracoes.getProperty("escopoServidorAplicacao"))){
				nome += configuracoes.getProperty("escopoServidorAplicacao") + "/";
			}
			if(Util.notEmpty(configuracoes.getProperty("arquivoEAR"))){
				nome += configuracoes.getProperty("arquivoEAR") + "/";
			}
			if(Util.notEmpty(configuracoes.getProperty("arquivoEJB"))){
				nome += configuracoes.getProperty("arquivoEJB") + "/";
			}
			
			nome += classeServico.getSimpleName().replace("SR", "SB").replace("SL", "SB");
			nome += "!" + classeServico.getName();
			
			return (T) fabrica.getResource(nome); 
		}
		catch(Exception e){
			throw new SystemException(e);
		}
	}
	
	/**
	 * Factory de AvaliacaoSR
	 * @return AvaliacaoSR
	 */
	@Produces
	private AvaliacaoSR getAvaliacaoSR(){
		try {
			// Recupera a classe utilizando a factory
			return FactorySIAVS.getService(AvaliacaoSR.class);
		}
		catch (Throwable e) {
			log.error("Erro ao tentar recuperar automaticamente o serviço de avaliação.", e);
			return null;
		}
	}
	
	/**
	 * Factory de GrupoSR
	 * @return GrupoSR
	 */
	@Produces
	private GrupoSR getGrupoSR(){
		try {
			// Recupera a classe utilizando a factory
			return FactorySIAVS.getService(GrupoSR.class);
		}
		catch (Throwable e) {
			log.error("Erro ao tentar recuperar automaticamente o serviço do serviço.", e);
			return null;
		}
	}
	
	/**
	 * Factory de ServicoSR
	 * @return ServicoSR
	 */
	@Produces
	private ServicoSR getServicoSR(){
		try {
			// Recupera a classe utilizando a factory
			return FactorySIAVS.getService(ServicoSR.class);
		}
		catch (Throwable e) {
			log.error("Erro ao tentar recuperar automaticamente o serviço do serviço.", e);
			return null;
		}
	}
	
	/**
	 * Factory de FuncionarioCaixaLocalSR
	 * @return FuncionarioCaixaLocalSR
	 */
	@Produces
	private FuncionarioCaixaLocalSR getFuncionarioCaixaLocalSR() {
		try {
			// Recupera a classe utilizando a factory
			return FactorySIAVS.getService(FuncionarioCaixaLocalSR.class);
		}
		catch (Throwable e) {
			log.error("Erro ao tentar recuperar automaticamente o serviço de funcionário caixa local.", e);
			return null;
		}
	}

	/**
	 * Factory de FncroCaixaSegmentoSR
	 * @return FncroCaixaSegmentoSR
	 */
	@Produces
	private FncroCaixaSegmentoSR getFncroCaixaSegmentoSR() {
		try {
			// Recupera a classe utilizando a factory
			return FactorySIAVS.getService(FncroCaixaSegmentoSR.class);
		}
		catch (Throwable e) {
			log.error("Erro ao tentar recuperar automaticamente o serviço de funcionário caixa segmento.", e);
			return null;
		}
	}	
	
	/**
	 * Factory de ProblemaSR
	 * @return ProblemaSR
	 */
	@Produces
	private ProblemaSR getProblemaSR() {
		try {
			// Recupera a classe utilizando a factory
			return FactorySIAVS.getService(ProblemaSR.class);
		}
		catch (Throwable e) {
			log.error("Erro ao tentar recuperar automaticamente o serviço de problema.", e);
			return null;
		}
	}	
	
	/**
	 * Factory de NoticiaSR
	 * @return NoticiaSR
	 */
	@Produces
	private NoticiaSR getNoticiaSR() {
		try {
			// Recupera a classe utilizando a factory
			return FactorySIAVS.getService(NoticiaSR.class);
		}
		catch (Throwable e) {
			log.error("Erro ao tentar recuperar automaticamente o serviço de noticia.", e);
			return null;
		}
	}	
	
	/**
	 * Factory de MetaSR
	 * @return MetaSR
	 */
	@Produces
	private MetaSR getMetaSR() {
		try {
			// Recupera a classe utilizando a factory
			return FactorySIAVS.getService(MetaSR.class);
		}
		catch (Throwable e) {
			log.error("Erro ao tentar recuperar automaticamente o serviço de meta.", e);
			return null;
		}
	}	
	
	/**
	 * Factory de FuncionarioCaixaSR
	 * @return FuncionarioCaixaSR
	 */
	@Produces
	private FuncionarioCaixaSR getFuncionarioCaixaSR() {
		try {
			// Recupera a classe utilizando a factory
			return FactorySIAVS.getService(FuncionarioCaixaSR.class);
		}
		catch (Throwable e) {
			log.error("Erro ao tentar recuperar automaticamente o serviço de funcionário caixa.", e);
			return null;
		}
	}

	/**
	 * Factory de SegmentoSR
	 * @return SegmentoSR
	 */
	@Produces
	private SegmentoSR getSegmentoSR() {
		try {
			// Recupera a classe utilizando a factory
			return FactorySIAVS.getService(SegmentoSR.class);
		}
		catch (Throwable e) {
			log.error("Erro ao tentar recuperar automaticamente o serviço de meta.", e);
			return null;
		}
	}	
	
	/**
	 * Factory de GrupoServicoSR
	 * @return GrupoServicoSR
	 */
	@Produces
	private GrupoServicoSR getGrupoServicoSR() {
		try {
			// Recupera a classe utilizando a factory
			return FactorySIAVS.getService(GrupoServicoSR.class);
		}
		catch (Throwable e) {
			log.error("Erro ao tentar recuperar automaticamente o serviço de meta.", e);
			return null;
		}
	}	
	
	/**
	 * Factory de UnidadeSR
	 * @return UnidadeSR
	 */
	@Produces
	private UnidadeSR getUnidadeSR() {
		try {
			// Recupera a classe utilizando a factory
			return FactorySIAVS.getService(UnidadeSR.class);
		}
		catch (Throwable e) {
			log.error("Erro ao tentar recuperar automaticamente o serviço de unidade.", e);
			return null;
		}
	}	
	
	/**
	 * Factory de GrupoUnidadeSR
	 * @return GrupoUnidadeSR
	 */
	@Produces
	private GrupoUnidadeSR getGrupoUnidadeSR() {
		try {
			// Recupera a classe utilizando a factory
			return FactorySIAVS.getService(GrupoUnidadeSR.class);
		}
		catch (Throwable e) {
			log.error("Erro ao tentar recuperar automaticamente o serviço de grupo unidade.", e);
			return null;
		}
	}	
	
	/**
	 * Factory de UsuarioSR
	 * @return UsuarioSR
	 */
	@Produces
	private UsuarioSR getUsuarioSR() {
		try {
			// Recupera a classe utilizando a factory
			return FactorySIAVS.getService(UsuarioSR.class);
		}
		catch (Throwable e) {
			log.error("Erro ao tentar recuperar automaticamente o serviço de usuário.", e);
			return null;
		}
	}	
	
	/**
	 * Factory de AgendamentoSR
	 * @return AgendamentoSR
	 */
	@Produces
	private AgendamentoSR getAgendamentoSR() {
		try {
			// Recupera a classe utilizando a factory
			return FactorySIAVS.getService(AgendamentoSR.class);
		}
		catch (Throwable e) {
			log.error("Erro ao tentar recuperar automaticamente o serviço de agendamento.", e);
			return null;
		}
	}	
}