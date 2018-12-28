package br.gov.caixa.siavs.view.jsf;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.util.Data;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.global.DominioSIAVS;
import br.gov.caixa.siavs.service.client.AvaliacaoSR;
import br.gov.caixa.siavs.service.client.GrupoSR;
import br.gov.caixa.siavs.service.client.MetaSR;
import br.gov.caixa.siavs.service.client.ProblemaSR;
import br.gov.caixa.siavs.to.ConsultaPainelAvaliacaoTO;
import br.gov.caixa.siavs.vo.AvaliacaoVO;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.MetaVO;
import br.gov.caixa.siavs.vo.ProblemaVO;
import br.gov.caixa.siavs.vo.ServicoVO;

/**
 * <b>Title:</b> PainelAvaliacaoBean <br>
 * <b>Description:</b> Classe reponsável por exibir o relatório com as
 * avaliações e fazer o detalhamento. <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: $, $Date: $
 */
@Named
@ConversationScoped
public class PainelAvaliacaoView extends AbstractViewSIAVS<AvaliacaoVO> {

	private static final long serialVersionUID = 1L;

	// ***********************************************************************************************************************************

	@Inject
	private ProblemaSR problemaSR;
	@Inject
	private GrupoSR grupoSR;
	@Inject
	private MetaSR metaSR;

	private List<ConsultaPainelAvaliacaoTO> lista;
	private List<ProblemaVO> listaProblema;
	private List<ServicoVO> listaServico;
	private List<ConsultaPainelAvaliacaoTO> listaUltimaQuinzena;
	private List<ConsultaPainelAvaliacaoTO> listaUltimoSemestre;

	private MetaVO metaVO;
	private ServicoVO servicoVO;
	private GrupoVO grupoVO;
	private Date dtInicial;
	private Date dtFinal;
	private boolean exibirMsg;
	private ServicoVO servicoVOGrafico;

	// ***********************************************************************************************************************************

	/**
	 * Inicia os dados da tela.
	 */
	public void iniciarDados() {
		// Valores default da primeira busca
		// RN016
		this.setGrupoVO(new GrupoVO(DominioSIAVS.ID_GRUPO_AGENCIA));
		this.setDtInicial(new Date());
		this.setDtFinal(new Date());
		this.setServicoVOGrafico(null);
	}

	/**
	 * Limpa os dados da tela.
	 */
	public void limparTela() {
		this.setGrupoVO(null);
		this.setDtInicial(null);
		this.setDtFinal(null);
		this.setListaServico(null);
		listaProblema = null;
		this.setLista(null);
		this.setExibirMsg(false);
		this.detalhar(null);
	}

	/**
	 * Executa a listagem das avaliações.
	 */
	public void listarPorData() {

		this.setDtFinal(this.getDtInicial());
		this.listar();
		// Se encontrar algum registro
		if (Util.notEmpty(this.getLista())) {
			// Exibe o relatório para o que tem a menor nota
			this.exibirGrafico(this.getLista().get(0).getServico());
		} else {
			// Apaga o relatório
			this.exibirGrafico(null);
		}
	}

	/**
	 * Executa a listagem das avaliações.
	 */
	public void listar() {
		try {
			metaVO = null;
			this.setLista(((AvaliacaoSR) this.getService()).listarAvaliacoesPainel(this.getGrupoVO(),
					this.getDtInicial(), this.getDtFinal(), this.getListaServico(), this.getListaProblema()));
			if (this.isExibirMsg() && !Util.notEmpty(this.getLista())) {
				throw new BusinessException("Não existem dados para consulta.");
			}

		} catch (Throwable e) {
			e.printStackTrace();
			this.tratarExcecao(e, null);
		}
	}

	// ***********************************************************************************************************************************

	/**
	 * @return List<ConsultaPainelAvaliacaoTO>
	 */
	public List<ConsultaPainelAvaliacaoTO> getLista() {
		if (lista == null) {
			lista = new LinkedList<ConsultaPainelAvaliacaoTO>();
		}
		return lista;
	}

	/**
	 * @param lista
	 *            List<ConsultaPainelAvaliacaoTO>
	 */
	public void setLista(List<ConsultaPainelAvaliacaoTO> lista) {
		this.lista = lista;
	}

	/**
	 * @return List<ProblemaVO>
	 */
	public List<ProblemaVO> getListaProblema() {
		if (listaProblema == null) {
			try {
				listaProblema = problemaSR.listar(null);
			} catch (Throwable e) {
				this.tratarExcecao(e, null);
			}
		}

		return listaProblema;
	}

	/**
	 * @return List<GrupoVO>
	 */
	public List<GrupoVO> getListaGrupo() {
		try {
			return grupoSR.listar(null);
		} catch (Throwable e) {
			this.tratarExcecao(e, null);
		}

		return null;
	}

	/**
	 * @return Integer
	 */
	public Integer getValorMeta() {
		if (metaVO == null && this.getDtInicial() != null) {
			try {
				metaVO = metaSR.consultar(new MetaVO(this.getGrupoVO(), Data.obterParteAno(this.getDtInicial())));
			} catch (Throwable e) {
				this.tratarExcecao(e, null);
			}
		}

		return metaVO == null || metaVO.getVrNotaMeta() == null ? 0 : metaVO.getVrNotaMeta();
	}

	/**
	 * Direciona para a tela de detalhamento do serviço
	 * 
	 * @param servicoVO
	 */
	public void detalhar(ServicoVO servicoVO) {
		this.setServicoVO(servicoVO);
		this.setServicoVOGrafico(null);

		if (servicoVO != null) {
			List<ServicoVO> listaServico = new LinkedList<ServicoVO>();
			listaServico.add(this.getServicoVO());

			this.listarAvalicoes(listaServico);
		}
	}

	/**
	 * Lista as avaliações por período para os serviços informados.
	 * 
	 * @param listaServico
	 *            List<ServicoVO>
	 */
	public void listarAvalicoes(List<ServicoVO> listaServico) {
		try {
			this.setListaVo(((AvaliacaoSR) this.getService()).listarAvaliacoesPorPeriodo(this.getGrupoVO(),
					listaServico, this.getDtInicial(), this.getDtFinal()));
		} catch (Throwable e) {
			this.tratarExcecao(e, null);
		}
	}

	/**
	 * Carrega os dados para exibição do gráfico.
	 * 
	 * @param servicoVO
	 */
	public void exibirGrafico(ServicoVO servicoVO) {
		this.setServicoVOGrafico(servicoVO);

		if (servicoVO == null) {
			return;
		}

		try {
			this.setListaUltimaQuinzena(((AvaliacaoSR) this.getService())
					.listarMediasPorPeriodo(this.getServicoVOGrafico(), true, this.getDtInicial()));
			this.setListaUltimoSemestre(((AvaliacaoSR) this.getService())
					.listarMediasPorPeriodo(this.getServicoVOGrafico(), false, this.getDtInicial()));
		} catch (Throwable e) {
			this.tratarExcecao(e, null);
			return;
		}

		this.prepararDados(true, this.getDtInicial());
		this.prepararDados(false, this.getDtInicial());
	}

	/**
	 * Prepara os dados para exibição preenchendo quando a informação estiver
	 * vazia.
	 * 
	 * @param dataUltimoSemestre
	 * @param dataAtual
	 * @param posicao
	 */
	private void prepararDados(boolean ultimaQuinzena, Date dtReferencia) {
		Calendar dtInicial = new GregorianCalendar();

		if (dtReferencia != null) {
			dtInicial.setTime(dtReferencia);
		}

		List<ConsultaPainelAvaliacaoTO> lista;

		if (ultimaQuinzena) {
			// Última quinzena
			dtInicial.add(Calendar.DATE, -14);
			lista = this.getListaUltimaQuinzena();
		} else {
			// Último semestre
			dtInicial.add(Calendar.MONTH, -5);
			lista = this.getListaUltimoSemestre();
		}

		Calendar dtFinal = new GregorianCalendar();
		if (dtReferencia != null) {
			dtFinal.setTime(dtReferencia);
		}

		boolean jaExiste;
		int posicao = 0;

		while (!dtInicial.after(dtFinal)) {
			jaExiste = false;

			for (ConsultaPainelAvaliacaoTO to : lista) {
				// Se a data já existir
				if (Data.formatar(dtInicial.getTime()).equals(Data.formatar(to.getData()))) {
					jaExiste = true;
					break;
				}
			}
			// Se ainda não existe
			if (!jaExiste) {
				ConsultaPainelAvaliacaoTO toTemp = new ConsultaPainelAvaliacaoTO();
				toTemp.setMediaNotaAvaliacao(0d);
				toTemp.setData(dtInicial.getTime());
				lista.add(posicao, toTemp);
			}

			dtInicial.add(ultimaQuinzena ? Calendar.DATE : Calendar.MONTH, 1);
			posicao++;
		}
	}

	// ***********************************************************************************************************************************

	/**
	 * @return
	 */
	public ServicoVO getServico() {
		return this.servicoVO;
	}

	/**
	 * @return List<ServicoVO>
	 */
	public List<ServicoVO> getListaServico() {
		if (listaServico == null) {
			this.setListaServico(new LinkedList<ServicoVO>());
		}
		return listaServico;
	}

	/**
	 * @param listaServico
	 *            List<ServicoVO>
	 */
	public void setListaServico(List<ServicoVO> listaServico) {
		this.listaServico = listaServico;
	}

	/**
	 * @return ServicoVO
	 */
	public ServicoVO getServicoVO() {
		return servicoVO;
	}

	/**
	 * @param servicoVO
	 *            ServicoVO
	 */
	public void setServicoVO(ServicoVO servicoVO) {
		this.servicoVO = servicoVO;
	}

	/**
	 * @return GrupoVO
	 */
	public GrupoVO getGrupoVO() {
		return grupoVO;
	}

	/**
	 * @param grupoVO
	 *            GrupoVO
	 */
	public void setGrupoVO(GrupoVO grupoVO) {
		this.grupoVO = grupoVO;
	}

	/**
	 * @return Date
	 */
	public Date getDtInicial() {
		return dtInicial;
	}

	/**
	 * @param dtInicial
	 *            Date
	 */
	public void setDtInicial(Date dtInicial) {
		this.dtInicial = dtInicial;
	}

	/**
	 * @return Date
	 */
	public Date getDtFinal() {
		return dtFinal;
	}

	/**
	 * @param dtFinal
	 *            Date
	 */
	public void setDtFinal(Date dtFinal) {
		this.dtFinal = dtFinal;
	}

	/**
	 * @return boolean
	 */
	public boolean isExibirMsg() {
		return exibirMsg;
	}

	/**
	 * @param exibirMsg
	 *            boolean
	 */
	public void setExibirMsg(boolean exibirMsg) {
		this.exibirMsg = exibirMsg;
	}

	/**
	 * Retorna listaUltimaQuinzena.
	 */
	public List<ConsultaPainelAvaliacaoTO> getListaUltimaQuinzena() {
		return this.listaUltimaQuinzena;
	}

	/**
	 * @param listaUltimaQuinzena
	 *            Preenche listaUltimaQuinzena
	 */
	private void setListaUltimaQuinzena(List<ConsultaPainelAvaliacaoTO> listaUltimaQuinzena) {
		this.listaUltimaQuinzena = listaUltimaQuinzena;
	}

	/**
	 * Retorna listaUltimoSemestre.
	 */
	public List<ConsultaPainelAvaliacaoTO> getListaUltimoSemestre() {
		return this.listaUltimoSemestre;
	}

	/**
	 * @param listaUltimoSemestre
	 *            Preenche listaUltimoSemestre
	 */
	private void setListaUltimoSemestre(List<ConsultaPainelAvaliacaoTO> listaUltimoSemestre) {
		this.listaUltimoSemestre = listaUltimoSemestre;
	}

	/**
	 * Retorna servicoVOGrafico.
	 */
	public ServicoVO getServicoVOGrafico() {
		return this.servicoVOGrafico;
	}

	/**
	 * @param servicoVOGrafico
	 *            Preenche servicoVOGrafico
	 */
	public void setServicoVOGrafico(ServicoVO servicoVOGrafico) {
		this.servicoVOGrafico = servicoVOGrafico;
	}

	// ***********************************************************************************************************************************
}