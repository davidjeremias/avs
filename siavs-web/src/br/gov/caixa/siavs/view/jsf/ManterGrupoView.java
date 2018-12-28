package br.gov.caixa.siavs.view.jsf;

import javax.enterprise.context.ConversationScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import br.gov.caixa.siavs.vo.GrupoVO;

/**
 * <b>Title:</b> ManterGrupoView <br>
 * <b>Description:</b> Classe que permite ao usuário manter as informações do grupo. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: $, $Date: $
 */
@Named
@ConversationScoped
public class ManterGrupoView extends AbstractViewSIAVS <GrupoVO> {

	private static final long serialVersionUID = 1;

	@Inject
	private VinculaServicoView vinculaServicoView;
	
	@Inject
	private ManterMetaView manterMetaView;
	
	public String irTelaInicio(){
		
		vinculaServicoView.setCarregarListas(Boolean.TRUE);
		manterMetaView.setCarregarListaMetas(Boolean.TRUE);
		
		return super.irTelaInicio();
	}
	
	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#permiteExcluir(br.gov.caixa.siavs.vo.AbstractVOSIAVS)
	 */
	@Override
	public Boolean permiteExcluir(GrupoVO vo){
		// Se não for agência
		return !vo.getIcAgencia();
	}

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getExibirTitulo()
	 */
	@Override
	public Boolean getExibirTitulo(){
		return false;
	}
	
// ***********************************************************************************************************************************
	
	/**
	 * Ação decorrente da alteração no campo chkIcEnviaEmailConvocacao.
	 * @param e ValueChangeEvent
	 */
	public void chkIcEnviaEmailConvocacaoOnChange(ValueChangeEvent e){
		// Se não for enviar email de convocação
		if(e != null && !e.getNewValue().equals(true)){
			// Limpa o campo
			this.getVo().setIcFrequenciaAvaliacao(null);
		}
	}
	
// ***********************************************************************************************************************************
}