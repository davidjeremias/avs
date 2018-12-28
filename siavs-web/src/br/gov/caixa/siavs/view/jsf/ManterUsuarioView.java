package br.gov.caixa.siavs.view.jsf;

import javax.enterprise.context.ConversationScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.global.Mensagem.Msg;
import br.gov.caixa.siavs.service.client.FuncionarioCaixaSR;
import br.gov.caixa.siavs.vo.FuncionarioCaixaLocalVO;
import br.gov.caixa.siavs.vo.FuncionarioCaixaVO;
import br.gov.caixa.siavs.vo.UnidadeVO;
import br.gov.caixa.siavs.vo.UsuarioVO;

/**
 * <b>Title:</b> ManterUsuarioView <br>
 * <b>Description:</b> Classe que permite ao usuário manter as informações do usuário. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 08/07/2013$
 */
@Named
@ConversationScoped
public class ManterUsuarioView extends AbstractViewSIAVS <UsuarioVO> {

	private static final long serialVersionUID = 1L;
	
// ***********************************************************************************************************************************
	
	@Inject
	private FuncionarioCaixaSR funcionarioCaixaSR;
	
// ***********************************************************************************************************************************
	
	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getTitulo()
	 */
	@Override
	public String getTitulo(){
		return "Usuário";
	}

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#limparVO()
	 */
	@Override
	protected void limparVO() {
		super.limparVO();
		this.getVo().setFuncionarioCaixaLocal(new FuncionarioCaixaLocalVO());
		this.getVo().getFuncionarioCaixaLocal().setUnidade(new UnidadeVO());
		this.getVo().getFuncionarioCaixaLocal().setFuncionarioCaixa(new FuncionarioCaixaVO());
	}
	
// ***********************************************************************************************************************************
	
	/**
	 * Consulta o usuário.
	 */
	public void consultarUsuario(){
		try {
			// Se não encontrar
			if(! Util.notEmpty(this.getVo().getFuncionarioCaixaLocal().getFuncionarioCaixa().getNuMatricula())){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("matrícula"), "nuMatricula");
			}
			
			FuncionarioCaixaVO funcionarioCaixaVO = funcionarioCaixaSR.consultar(this.getVo().getFuncionarioCaixaLocal().getFuncionarioCaixa());
			
			// Se não encontrar
			if(funcionarioCaixaVO == null){
				this.txtNuMatriculaOnChange(null);
			}
			else {
				this.getVo().getFuncionarioCaixaLocal().setFuncionarioCaixa(funcionarioCaixaVO);
				this.getVo().getFuncionarioCaixaLocal().setUnidade(funcionarioCaixaVO.getUnidade());
			}

			// Se não encontrar
			if(funcionarioCaixaVO == null){
				throw new BusinessException("A matrícula informada não foi encontrada.");
			}
		}
		catch (Throwable e) {
			this.tratarExcecao(e, null);
		}
	}
	
	/**
	 * Ação decorrente da alteração no campo txtNuMatricula.
	 * @param e ValueChangeEvent
	 */
	public void txtNuMatriculaOnChange(ValueChangeEvent e){
		String icAcesso = this.getVo().getIcAcesso();
		this.limparVO();
		this.getVo().setIcAcesso(icAcesso);
		 
		if(e != null && e.getNewValue() != null){
			// Limpa o campo
			this.getVo().getFuncionarioCaixaLocal().getFuncionarioCaixa().setDeMatricula(e.getNewValue().toString());
		}
	}
}