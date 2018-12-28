package br.gov.caixa.siavs.view.jsf;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.global.Dominio.Operacao;
import br.gov.caixa.siavs.vo.EmailConvocacaoVO;

/**
 * <b>Title:</b> RegistraEmailConvocacaoView <br>
 * <b>Description:</b> Classe que permite ao usuário manter as informações do emailConvocacao. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 04/07/2013$
 */
@Named
@ConversationScoped
public class RegistraEmailConvocacaoView extends AbstractViewSIAVS <EmailConvocacaoVO> {

	private static final long serialVersionUID = 1L;
	
// ***********************************************************************************************************************************
	
	@Inject
	private ManterGrupoView manterGrupoView;

// ***********************************************************************************************************************************

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getExibirTitulo()
	 */
	@Override
	public Boolean getExibirTitulo(){
		return false;
	}

	/**
	 * @see br.com.spread.framework.view.jsf.AbstractViewJSF#encerrarConversacao()
	 */
	@Override
	public Boolean encerrarConversacao(){
		return false;
	}

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#doListar(br.gov.caixa.siavs.vo.AbstractVOSIAVS)
	 */
	@Override
	protected void doListar(EmailConvocacaoVO vo) throws SystemException, BusinessException {
		this.setVo(this.getService().consultar(this.getVo()));
		if(this.getVo() == null){
			this.limparVO();
		}
	}
	
	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getTitulo()
	 */
	@Override
	public String getTitulo() {
		return "Email de Convocação";
	}
	
	/**
	 * @see br.com.spread.framework.view.jsf.AbstractViewJSF#tratarSucesso(br.com.spread.framework.global.Dominio.Operacao)
	 */
	@Override
	protected void tratarSucesso(Operacao operacao) {
		this.adicionarMsgInfo(this.getMensagem("MN020"));
	}
	
	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getNomePadrao()
	 */
	@Override
	public String getNomePadrao() {
		return "registraEmailConvocacao";
	}
	
	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#limparVO()
	 */
	@Override
	protected void limparVO() {
		super.limparVO();
		this.getVo().setGrupo(manterGrupoView.getVo());
	}
		
// ***********************************************************************************************************************************		
		
}