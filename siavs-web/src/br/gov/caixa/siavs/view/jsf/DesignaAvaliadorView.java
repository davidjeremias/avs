package br.gov.caixa.siavs.view.jsf;

import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.service.client.FuncionarioCaixaLocalSR;
import br.gov.caixa.siavs.service.client.SegmentoSR;
import br.gov.caixa.siavs.vo.FncroCaixaSegmentoVO;
import br.gov.caixa.siavs.vo.FuncionarioCaixaLocalVO;
import br.gov.caixa.siavs.vo.SegmentoVO;

/**
 * <b>Title:</b> DesignaAvaliadorView <br>
 * <b>Description:</b> Classe que permite ao usuário designar um avaliador. <br>
 * <b>Company:</b> Spread
 * @author joana.espindola
 * @version: $Revision: 1.0 $, $Date: 25/06/2013$
 */
@Named
@ConversationScoped
public class DesignaAvaliadorView extends AbstractViewSIAVS<FuncionarioCaixaLocalVO> {

	private static final long serialVersionUID = 1L;

// ***********************************************************************************************************************************
	@Inject
	private SegmentoSR segmentoSR;
	@Inject
	private ControlaAcessoView controlaAcessoView;
// ***********************************************************************************************************************************

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getTitulo()
	 */
	@Override
	public String getTitulo() {
		return "Lista Empregados";
	}

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getNomePadrao()
	 */
	@Override
	public String getNomePadrao() {
		return "designaAvaliador";
	}

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#permiteAlterar(br.gov.caixa.siavs.vo.AbstractVOSIAVS)
	 */
	@Override
	public Boolean permiteExcluir(FuncionarioCaixaLocalVO vo) {
		return false;
	}

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#permiteIncluir()
	 */
	@Override
	public Boolean permiteIncluir() {
		return false;
	}

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#doListar(br.gov.caixa.siavs.vo.AbstractVOSIAVS)
	 */
	@Override
	protected void doListar(FuncionarioCaixaLocalVO vo) throws SystemException, BusinessException {
		
		if(vo == null){
			vo = new FuncionarioCaixaLocalVO();
		}
		
		vo.setUnidade(controlaAcessoView.getFuncionarioCaixaLocalVO().getUnidade());
		
		// Lista todos os funcionários de determinada unidade
		this.setListaVo(((FuncionarioCaixaLocalSR) this.getService()).listarTodos(vo));
	}

// ***********************************************************************************************************************************

	/**
	 * Retorna a lista de segmentos disponíveis.
	 * @return List<SegmentoVO>
	 */
	public List<SegmentoVO> getListaSegmento(){
		try {
			return segmentoSR.listar(null);
		}
		catch (Throwable e) {
			this.tratarExcecao(e, null);
		}
		
		return null;
	}
	
	/**
	 * Retorna a lista de segmentos cadastrados.
	 * @return List<SegmentoVO>
	 */
	public List<String> getListaSegmentoCadastrados() {
		List<String> lista = new LinkedList<String>();
		
		if (this.getVo().getListaSegmento() != null) {
			for (FncroCaixaSegmentoVO fncroCaixaSegmentoVO : this.getVo().getListaSegmento()) {
				lista.add(fncroCaixaSegmentoVO.getSegmento().getNuSegmento().toString());
			}
		}

		return lista;
	}

	/**
	 * Preenche a lista de segmentos cadastrados.
	 * @param listaSegmentoCadastrados List<String>
	 */
	public void setListaSegmentoCadastrados(List<String> listaSegmentoCadastrados) {
		this.getVo().setListaSegmento(new LinkedList<FncroCaixaSegmentoVO>());

		for (String valor : listaSegmentoCadastrados) {
			this.getVo().getListaSegmento().add(new FncroCaixaSegmentoVO(this.getVo(), new SegmentoVO(new Long(valor))));
		}
	}

	// ***********************************************************************************************************************************
}