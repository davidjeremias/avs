package br.gov.caixa.siavs.view.jsf;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.gov.caixa.siavs.vo.AvaliacaoVO;

/**
 * <b>Title:</b> ConsultaPainelAvaliacaoView <br>
 * <b>Description:</b> Classe que permite ao usuário consultar o painel de avaliação. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 28/06/2013$
 */
@Named
@ConversationScoped
public class ConsultaPainelAvaliacaoView extends AbstractViewSIAVS <AvaliacaoVO> {

	private static final long serialVersionUID = 1L;
	
// ***********************************************************************************************************************************
	@Inject
	private PainelAvaliacaoView painelAvaliacao;
	
// ***********************************************************************************************************************************
	
	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getTitulo()
	 */
	@Override
	public String getTitulo(){
		return "Avaliação de Serviços de TI";
	}

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getTelaInicio()
	 */
	@Override
	protected String getTelaInicio() {
		this.getPainelAvaliacao().limparTela();
		this.getPainelAvaliacao().iniciarDados();
		this.getPainelAvaliacao().listarPorData();
		return this.getNomePadrao();
	}
	
	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getNomePadrao()
	 */
	@Override
	public String getNomePadrao() {
		return "consultaPainelAvaliacao";
	}
	
// ***********************************************************************************************************************************

	/**
	 * @return PainelAvaliacaoBean
	 */
	public PainelAvaliacaoView getPainelAvaliacao() {
		return painelAvaliacao;
	}

	/**
	 * @param painelAvaliacao PainelAvaliacaoBean
	 */
	public void setPainelAvaliacao(PainelAvaliacaoView painelAvaliacao) {
		this.painelAvaliacao = painelAvaliacao;
	}
	
// ***********************************************************************************************************************************
}