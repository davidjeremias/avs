package br.gov.caixa.siavs.view.jsf;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.service.client.NoticiaSR;
import br.gov.caixa.siavs.vo.NoticiaVO;
import br.gov.caixa.siavs.vo.ServicoVO;

/**
 * <b>Title:</b> PublicaNoticiaView <br>
 * <b>Description:</b> Classe que permite ao usuário publicar a notícia. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 17/06/2013$
 */
@Named
@ConversationScoped
public class PublicaNoticiaView extends AbstractViewSIAVS <NoticiaVO> {

	private static final long serialVersionUID = 1L;
	
// ***********************************************************************************************************************************
	
	@Inject
	private ControlaAcessoView controlaAcessoView;
	
// ***********************************************************************************************************************************

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getTitulo()
	 */
	@Override
	public String getTitulo(){
		return "Notícia";
	}

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getNomePadrao()
	 */
	@Override
	public String getNomePadrao() {
		return "publicaNoticia";
	}
	
	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#limparVO()
	 */
	@Override
	protected void limparVO() {
		super.limparVO();
		this.getVo().setServico(new ServicoVO());
	}
	
	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#permiteAlterar(br.gov.caixa.siavs.vo.AbstractVOSIAVS)
	 */
	@Override
	public Boolean permiteAlterar(NoticiaVO vo){
		// Se estiver inativo
		return vo != null && vo.getServico() != null && vo.getServico().getIcAtivo();
	}
	
	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#doListar(br.gov.caixa.siavs.vo.AbstractVOSIAVS)
	 */
	@Override
	protected void doListar(NoticiaVO vo) throws SystemException, BusinessException {
		if(controlaAcessoView.isPublicador()){
			// Se tiver algum grupo vinculado 
			if(controlaAcessoView.getGrupoUnidadeUsuario() != null){
				this.setListaVo(((NoticiaSR) this.getService()).listarPorGrupo(controlaAcessoView.getGrupoUnidadeUsuario()));
			}
		}
		else {
			super.doListar(vo);
		}
	}
	
// ***********************************************************************************************************************************
}