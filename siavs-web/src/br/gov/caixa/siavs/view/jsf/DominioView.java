package br.gov.caixa.siavs.view.jsf;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.global.DominioSIAVS.FrequenciaAvaliacao;
import br.gov.caixa.siavs.global.DominioSIAVS.NivelAcesso;
import br.gov.caixa.siavs.global.DominioSIAVS.TipoUnidade;
import br.gov.caixa.siavs.service.client.UnidadeSR;
import br.gov.caixa.siavs.vo.UnidadeVO;

/**
 * <b>Title:</b> DominioView <br>
 * <b>Description:</b> Classe que recupera as informações de Domínio. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: $, $Date: $
 */
@Named
@SessionScoped
public class DominioView implements Serializable {

	private static final long serialVersionUID = -3551826496654242253L;
	
	@Inject
	private UnidadeSR unidadesSR;
	
	private List<UnidadeVO> listaUnidadeRelacionamento;
	private List<UnidadeVO> listaUnidadeProducao;

// ***********************************************************************************************************************************
	
	/**
	 * Retorna um Array com todas as Frequências de avaliação.
	 * @return FrequenciaAvaliacao[]
	 */
	public FrequenciaAvaliacao[] getListaFrequenciaAvaliacao(){
		// Se não for agência
		return FrequenciaAvaliacao.values();
	}
	
	/**
	 * Retorna uma lista com todas as Unidades Responsáveis.
	 * @return List<UnidadeVO>
	 */
	public List<UnidadeVO> getListaUnidadeRelacionamento() throws SystemException, BusinessException {
		if(this.listaUnidadeRelacionamento == null){
			this.setListaUnidadeRelacionamento(this.getUnidadesSR().listarUnidadeRelProd(TipoUnidade.RELACIONAMENTO));
		}
		return this.listaUnidadeRelacionamento;
	}
	
	/**
	 * Retorna uma lista com todas as Unidades de Produção.
	 * @return List<UnidadeVO>
	 */
	public List<UnidadeVO> getListaUnidadeProducao() throws SystemException, BusinessException{
		if(this.listaUnidadeProducao == null){
			this.setListaUnidadeProducao(this.getUnidadesSR().listarUnidadeRelProd(TipoUnidade.PRODUCAO));
		}
		return this.listaUnidadeProducao;
	}
	
	/**
	 * Retorna um Array com todas os níveis de acesso.
	 * @return NivelAcesso[]
	 */
	public NivelAcesso[] getListaNivelAcesso(){
		return NivelAcesso.values();
	}
	
	/**
	 * Retorna um objeto de Nível de acesso.
	 * @return NivelAcesso
	 */
	public NivelAcesso getNivelAcesso(String codigo){
		return NivelAcesso.getValor(codigo);
	}

// ***********************************************************************************************************************************
	
	/**
	 * @param listaUnidadeRelacionamento Preenche listaUnidadeRelacionamento
	 */
	private void setListaUnidadeRelacionamento(List<UnidadeVO> listaUnidadeRelacionamento) {
		this.listaUnidadeRelacionamento = listaUnidadeRelacionamento;
	}

	/**
	 * @param listaUnidadeProducao Preenche listaUnidadeProducao
	 */
	private void setListaUnidadeProducao(List<UnidadeVO> listaUnidadeProducao) {
		this.listaUnidadeProducao = listaUnidadeProducao;
	}

	/**
	 * Retorna unidadesSR.
	 */
	private UnidadeSR getUnidadesSR() {
		return this.unidadesSR;
	}
}