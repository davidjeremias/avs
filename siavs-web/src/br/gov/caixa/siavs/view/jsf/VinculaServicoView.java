package br.gov.caixa.siavs.view.jsf;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.service.client.GrupoServicoSR;
import br.gov.caixa.siavs.service.client.ServicoSR;
import br.gov.caixa.siavs.vo.GrupoServicoVO;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.ServicoVO;

/**
 * <b>Title:</b> VinculaServicoView <br>
 * <b>Description:</b> Classe que permite ao usuário vincular serviços ao grupo.
 * <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 11/06/2013$
 */
@Named
@ConversationScoped
public class VinculaServicoView extends AbstractViewSIAVS<GrupoServicoVO> {

	private static final long serialVersionUID = 1L;

	@Inject
	private ManterGrupoView manterGrupoView;

	@Inject
	private ServicoSR servicoSR;

	private List<GrupoServicoVO> listaServicoVinculado;
	private List<ServicoVO> listaServicos;

	private boolean carregarListas;

	/**
	 * @return the carregarListas
	 */
	public boolean isCarregarListas() {
		return carregarListas;
	}

	/**
	 * @param carregarListas
	 *            the carregarListas to set
	 */
	public void setCarregarListas(boolean carregarListas) {
		this.carregarListas = carregarListas;
	}

	public String irTelaInicio() {

		if (isCarregarListas()) {
			try {
				this.listaServicoVinculado = this.getService()
						.listar(new GrupoServicoVO(manterGrupoView.getVo(), null));

				Collections.sort(this.listaServicoVinculado);
				
				this.listaServicos = servicoSR.listar(null);

			} catch (Throwable e) {
				this.tratarExcecao(e, null);
			}
		}

		setCarregarListas(Boolean.FALSE);

		return super.irTelaInicio();
	}

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getExibirTitulo()
	 */
	@Override
	public Boolean getExibirTitulo() {
		return false;
	}

	/**
	 * @see br.com.spread.framework.view.jsf.AbstractViewJSF#encerrarConversacao()
	 */
	@Override
	public Boolean encerrarConversacao() {
		return false;
	}

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getTitulo()
	 */
	@Override
	public String getTitulo() {
		return "Serviço";
	}

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getTelaInicio()
	 */
	@Override
	protected String getTelaInicio() {
		return "vinculaServico";
	}

	// ***********************************************************************************************************************************

	/**
	 * Retorna um array com os serviços vinculados.
	 * 
	 * @return Long[]
	 */
	public Long[] getListaServicoVinculado() {
		Long[] arrayServico = new Long[0];

		if (Util.notEmpty(this.listaServicoVinculado)) {
			arrayServico = new Long[this.listaServicoVinculado.size()];

			for (int r = 0; r < this.listaServicoVinculado.size(); r++) {
				arrayServico[r] = this.listaServicoVinculado.get(r).getServico().getNuServico();
			}
		}

		this.setListaServicoVinculado(arrayServico);

		return arrayServico;
	}

	/**
	 * Preenche a lista de serviços vinculados.
	 * 
	 * @param arrayServico
	 *            Long[]
	 */
	public void setListaServicoVinculado(Long[] arrayServico) {
		
		listaServicoVinculado.clear();

		for (Long id : arrayServico) {
			GrupoServicoVO tmp = new GrupoServicoVO(manterGrupoView.getVo(), new ServicoVO(id));
			if (!listaServicoVinculado.contains(tmp)){
				listaServicoVinculado.add(tmp);	
			}
		}
	}

	/**
	 * Retorna a lista com os serviços disponíveis.
	 * 
	 * @return List<ServicoVO>
	 */
	public List<ServicoVO> getListaServico() {
		Collections.sort(this.listaServicos);
		return this.listaServicos;
	}

	/**
	 * Salva os registros.
	 */
	public void salvar() {
		try {
			((GrupoServicoSR) this.getService()).salvar(manterGrupoView.getVo(), listaServicoVinculado);
			this.tratarSucesso(null);
		} catch (Throwable e) {
			this.tratarExcecao(e, null);
		}
	}
	// ***********************************************************************************************************************************

}