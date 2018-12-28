package br.gov.caixa.siavs.view.jsf;

import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.spread.framework.global.Dominio.Operacao;
import br.gov.caixa.siavs.service.client.SegmentoSR;
import br.gov.caixa.siavs.vo.SegmentoVO;
import br.gov.caixa.siavs.vo.ServicoSegmentoVO;
import br.gov.caixa.siavs.vo.ServicoVO;

/**
 * <b>Title:</b> ManterServicoView <br>
 * <b>Description:</b> Classe que permite ao usuário manter as informações do serviço. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 10/06/2013$
 */
@Named
@ConversationScoped
public class ManterServicoView extends AbstractViewSIAVS <ServicoVO> {

	private static final long serialVersionUID = 1L;
	
// ***********************************************************************************************************************************
	@Inject
	private SegmentoSR segmentoSR;
	private boolean retornarVinculaServico;
	@Inject
	private VinculaServicoView vinculaServicoView;
// ***********************************************************************************************************************************

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#limparVO()
	 */
	protected void limparVO() {
		super.limparVO();
		this.setRetornarVinculaServico(false);
	}
	
	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getTitulo
	 */
	@Override
	public String getTitulo() {
		return "Serviço";
	}

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#irTelaAlteracao
	 */
	@Override	
	public String irTelaAlteracao(ServicoVO objeto) {
		if(objeto != null){
			try {
				objeto = this.getService().consultar(objeto);
			}
			catch (Throwable e) {
				this.tratarExcecao(e, Operacao.CONSULTAR);
			}
		}
		return super.irTelaAlteracao(objeto);
	}
	
	/**
	 * Direciona para a tela de cadastro e prepara o retorno para a tela de vincula serviço.
	 * @return String
	 */
	public String irTelaCadastroVinculaServico() {
		String retorno = this.irTelaCadastro();
		this.setRetornarVinculaServico(true);
		return retorno;
	}
	
	/**
	 * Ação de voltar para a tela de início ou para a tela que chamou a atual.
	 * @return nome da tela
	 */
	public String voltar() {
		return this.isRetornarVinculaServico() ? vinculaServicoView.irTelaInicio() : this.irTelaInicio();
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
	 * @return List<String>
	 */
	public List<String> getListaSegmentoCadastrados(){
		List<String> lista = new LinkedList<String>();
		if(this.getVo().getListaSegmento() != null){
			for (ServicoSegmentoVO servicoSegmentoVO : this.getVo().getListaSegmento()) {
				lista.add(servicoSegmentoVO.getSegmento().getNuSegmento().toString());
			}
		}
		
		return lista;
	}
	
	/**
	 * Preenche a lista de segmentos cadastrados.
	 * @param listaSegmentoCadastrados List<String>
	 */
	public void setListaSegmentoCadastrados(List<String> listaSegmentoCadastrados){
		this.getVo().setListaSegmento(new LinkedList<ServicoSegmentoVO>());
		
		for (String valor : listaSegmentoCadastrados) {
			this.getVo().getListaSegmento().add(new ServicoSegmentoVO(this.getVo(), new SegmentoVO(new Long(valor))));
		}
	}

	/**
	 * Ação decorrente da alteração no campo chkIcEnviaEmailConvocacao.
	 * @param e ValueChangeEvent
	 */
	public void chkIcCorporativoOnChange(ValueChangeEvent e){
		// Se não for corporativo
		if(e != null && !e.getNewValue().equals(true)){
			// Limpa o campo
			this.getVo().setUnidadeRelacionamento(null);
			this.getVo().setUnidadeProducao(null);
		}
		else {
			this.getVo().setNoGestor(null);
		}
	}
	
	/**
	 * Ação decorrente da alteração no campo chkIcAgencia.
	 * @param e ValueChangeEvent
	 */
	public void chkIcAgenciaOnChange(ValueChangeEvent e){
		// Se não for corporativo
		if(e != null && !e.getNewValue().equals(true)){
			// Limpa o campo
			this.getVo().setListaSegmento(null);
		}
	}

// ***********************************************************************************************************************************
	
	/**
	 * Retorna retornarVinculaServico.
	 */
	public boolean isRetornarVinculaServico() {
		return this.retornarVinculaServico;
	}

	/**
	 * @param retornarVinculaServico Preenche retornarVinculaServico
	 */
	private void setRetornarVinculaServico(boolean retornarVinculaServico) {
		this.retornarVinculaServico = retornarVinculaServico;
	}
}