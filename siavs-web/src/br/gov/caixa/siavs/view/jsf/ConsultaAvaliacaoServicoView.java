package br.gov.caixa.siavs.view.jsf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.service.client.GrupoServicoSR;
import br.gov.caixa.siavs.service.client.ServicoSR;
import br.gov.caixa.siavs.to.ConsultaPainelAvaliacaoTO;
import br.gov.caixa.siavs.vo.AvaliacaoVO;
import br.gov.caixa.siavs.vo.GrupoServicoVO;
import br.gov.caixa.siavs.vo.ServicoVO;

/**
 * <b>Title:</b> ConsultaAvaliacaoServicoView <br>
 * <b>Description:</b> Classe que permite ao usuário consultar o painel de
 * avaliação. <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 28/06/2013$
 */
@Named
@ConversationScoped
public class ConsultaAvaliacaoServicoView extends AbstractViewSIAVS<AvaliacaoVO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ***********************************************************************************************************************************

	@Inject
	private PainelAvaliacaoView painelAvaliacao;
	@Inject
	private GrupoServicoSR grupoServicoSR;
	@Inject
	private ServicoSR servicoSR;

	private List<GrupoServicoVO> listaGrupoServico;
	private Boolean ckbMarcarTodos;

	// ***********************************************************************************************************************************

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getTitulo()
	 */
	@Override
	public String getTitulo() {
		return "Avaliação de Serviços de TI";
	}

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getTelaInicio()
	 */
	@Override
	protected String getTelaInicio() {
		this.getPainelAvaliacao().limparTela();
		this.getPainelAvaliacao().iniciarDados();
		this.getPainelAvaliacao().setExibirMsg(true);
		this.cmbGrupoOnChange(null);

		return "consultaAvaliacaoServico";
	}

	// ***********************************************************************************************************************************

	/**
	 * Executa a ação de consultar.
	 */
	public void consultar() {
		try {
			if (!Util.notEmpty(this.getListaServicosSelecionados())) {
				throw new BusinessException("Selecione pelo menos 1 (um) serviço.");
			}
			this.getPainelAvaliacao().listar();
		} catch (Throwable e) {
			this.tratarExcecao(e, null);
		}
	}

	/**
	 * Exibe o relatório.
	 */
	public void exibirRelatorio() {
		List<ServicoVO> listaServico = new LinkedList<ServicoVO>();
		// Carrega todos os serviços exibidos na lista
		for (ConsultaPainelAvaliacaoTO consultaPainelAvaliacaoTO : this.getPainelAvaliacao().getLista()) {
			listaServico.add(consultaPainelAvaliacaoTO.getServico());
		}
		// Lista as avaliações
		this.getPainelAvaliacao().listarAvalicoes(listaServico);
		this.emitirRelatorioXls("consultaAvaliacaoServico", null,
				new LinkedList<Object>(this.getPainelAvaliacao().getListaVo()));
	}

	/**
	 * Retorna a lista de serviços selecionados.
	 * 
	 * @return List<String>
	 */
	public List<String> getListaServicosSelecionados() {
		List<String> lista = new LinkedList<String>();

		for (ServicoVO servicoVO : this.getPainelAvaliacao().getListaServico()) {

			if (!lista.contains(servicoVO.getNuServico().toString())) {
				lista.add(servicoVO.getNuServico().toString());
			}

		}

		return lista;
	}

	/**
	 * Preenche a lista de serviços selecionados.
	 * 
	 * @param listaServicosSelecionados
	 *            List<String>
	 */
	public void setListaServicosSelecionados(List<String> listaServicosSelecionados) {
		for (String valor : listaServicosSelecionados) {
			this.getPainelAvaliacao().getListaServico().add(new ServicoVO(new Long(valor)));
		}
	}

	/**
	 * Retorna a lista de serviços vinculados ao grupo.
	 * 
	 * @return List<GrupoServicoVO>
	 */
	public List<GrupoServicoVO> getListaServicos() {
		if (listaGrupoServico == null) {
			try {
				if (painelAvaliacao.getGrupoVO() != null && Util.notEmpty(painelAvaliacao.getGrupoVO().getNuGrupo())) {
					listaGrupoServico = grupoServicoSR.listar(new GrupoServicoVO(painelAvaliacao.getGrupoVO(), null));

					// Lista os serviços que já foram avaliados
					List<ServicoVO> listaServico = servicoSR.listarServicosAvaliados(painelAvaliacao.getGrupoVO());
					// Adiciona na lista os que ainda não estiverem
					if (Util.notEmpty(listaServico)) {
						for (ServicoVO servicoVO : listaServico) {
							boolean adicionar = true;

							for (GrupoServicoVO grupoServicoVO : listaGrupoServico) {
								// Se já existir
								if (grupoServicoVO.getServico().equals(servicoVO)) {
									adicionar = false;
									break;
								}
							}
							if (adicionar) {
								listaGrupoServico.add(new GrupoServicoVO(painelAvaliacao.getGrupoVO(), servicoVO));
							}
						}

						Collections.sort(listaGrupoServico, new Comparator<GrupoServicoVO>() {
							@Override
							public int compare(GrupoServicoVO o1, GrupoServicoVO o2) {
								return o1.getServico().getDeServico().compareTo(o2.getServico().getDeServico());
							}
						});
					}
				} else {
					listaGrupoServico = new LinkedList<GrupoServicoVO>();
				}
			} catch (Throwable e) {
				this.tratarExcecao(e, null);
			}
		}

		return listaGrupoServico;
	}

	/**
	 * 
	 * @param pagina
	 * @param tamanho
	 * @return
	 */
	public List<GrupoServicoVO> getListaServicosPaginado(int pagina, int tamanho) {
		List<GrupoServicoVO> result = new ArrayList<GrupoServicoVO>();

		if (tamanho > 0) {
			int fromIndex = (pagina - 1) * tamanho;
			int toIndex = (pagina * tamanho) - 1;
			if (toIndex > getListaServicos().size()) {
				toIndex = getListaServicos().size();
			}

			result.addAll(getListaServicos().subList(fromIndex, toIndex));
		}

		return result;
	}

	/**
	 * 
	 * @return
	 */
	public List<GrupoServicoVO> getListaServicosGrupo1() {
		return getListaServicosPaginado(1, calcularTamanhoGrupoServicos());
	}

	/**
	 * 
	 * @return
	 */
	public List<GrupoServicoVO> getListaServicosGrupo2() {
		return getListaServicosPaginado(2, calcularTamanhoGrupoServicos());
	}

	/**
	 * 
	 * @return
	 */
	private int calcularTamanhoGrupoServicos() {
		int tamanho = getListaServicos().size() / 3;
		if (getListaServicos().size() % 3 > 0) {
			++tamanho;
		}
		return tamanho;
	}

	/**
	 * Ação decorrente da alteração no campo cmbGrupo.
	 * 
	 * @param e
	 *            ValueChangeEvent
	 */
	public void cmbGrupoOnChange(ValueChangeEvent e) {

		this.getPainelAvaliacao().getListaServico().clear();

		listaGrupoServico = null;
		if (e != null) {
			this.getPainelAvaliacao().getGrupoVO().setNuGrupo((Long) e.getNewValue());
		}
		this.marcarTodos(true);
	}

	/**
	 * Ação decorrente da alteração no campo ckbMarcarTodos.
	 * 
	 * @param e
	 *            ValueChangeEvent
	 */
	public void ckbMarcarTodosOnChange(ValueChangeEvent e) {
		this.marcarTodos(e != null && e.getNewValue().equals(true));
	}

	/**
	 * Se é para marcar todos.
	 * 
	 * @param marcar
	 *            Boolean
	 */
	private void marcarTodos(Boolean marcar) {
		this.getPainelAvaliacao().getListaServico().clear();

		if (marcar) {
			for (GrupoServicoVO grupoServicoVO : this.getListaServicos()) {
				this.getPainelAvaliacao().getListaServico().add(grupoServicoVO.getServico());
			}
		}
	}

	// ***********************************************************************************************************************************

	/**
	 * @return PainelAvaliacaoBean
	 */
	public PainelAvaliacaoView getPainelAvaliacao() {
		return painelAvaliacao;
	}

	/**
	 * @param painelAvaliacao
	 *            PainelAvaliacaoBean
	 */
	public void setPainelAvaliacao(PainelAvaliacaoView painelAvaliacao) {
		this.painelAvaliacao = painelAvaliacao;
	}

	/**
	 * @return Boolean
	 */
	public Boolean getCkbMarcarTodos() {
		// Se todos estiverem selecionados
		this.setCkbMarcarTodos(this.getListaServicos().size() == this.getListaServicosSelecionados().size());
		return ckbMarcarTodos;
	}

	/**
	 * @param ckbMarcarTodos
	 *            Boolean
	 */
	public void setCkbMarcarTodos(Boolean ckbMarcarTodos) {
		this.ckbMarcarTodos = ckbMarcarTodos;
	}

	// ***********************************************************************************************************************************
}