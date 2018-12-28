package br.gov.caixa.siavs.view.jsf;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.global.Dominio.Operacao;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.factory.FactorySIAVS;
import br.gov.caixa.siavs.global.enums.EnumTipoUnidade;
import br.gov.caixa.siavs.service.client.AvaliacaoSR;
import br.gov.caixa.siavs.service.client.FncroCaixaSegmentoSR;
import br.gov.caixa.siavs.service.client.GrupoServicoSR;
import br.gov.caixa.siavs.service.client.NoticiaSR;
import br.gov.caixa.siavs.service.client.ProblemaSR;
import br.gov.caixa.siavs.service.client.UnidadeSR;
import br.gov.caixa.siavs.service.client.UsuarioSR;
import br.gov.caixa.siavs.vo.AvaliacaoVO;
import br.gov.caixa.siavs.vo.FuncionarioCaixaLocalVO;
import br.gov.caixa.siavs.vo.GrupoServicoVO;
import br.gov.caixa.siavs.vo.NoticiaVO;
import br.gov.caixa.siavs.vo.ProblemaVO;
import br.gov.caixa.siavs.vo.ServicoVO;
import br.gov.caixa.siavs.vo.UnidadeVO;

/**
 * <b>Title:</b> AvaliaServicoView <br>
 * <b>Description:</b> Classe que permite ao usuário avaliar os serviços. <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 28/06/2013$
 */
@Named
@ConversationScoped
public class AvaliaServicoView extends AbstractViewSIAVS<AvaliacaoVO> {

	private static final long serialVersionUID = 1L;

	// ***********************************************************************************************************************************

	@Inject
	private GrupoServicoSR grupoServicoSR;
	@Inject
	private ProblemaSR problemaSR;
	@Inject
	private NoticiaSR noticiaSR;
	@Inject
	private FncroCaixaSegmentoSR fncroCaixaSegmentoSR;
	@Inject
	private UnidadeSR unidadeSR;

	@Inject
	private ControlaAcessoView controlaAcessoView;

	private NoticiaVO noticia;
	private List<AvaliaServicoBean> listaAvaliaServicoBean;
	private List<ProblemaVO> listaProblema;

	// ***********************************************************************************************************************************

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getTitulo()
	 */
	@Override
	public String getTitulo() {
		return "Avaliação de Serviços";
	}

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getTelaInicio()
	 */
	@Override
	protected String getTelaInicio() {
		listaProblema = null;
		listaAvaliaServicoBean = null;

		this.getListaAvaliaServicoBean();

		return "avaliaServico";
	}

	// ***********************************************************************************************************************************

	/**
	 * @return List<AvaliaServicoBean>
	 */
	public List<AvaliaServicoBean> getListaAvaliaServicoBean() {

		if (listaAvaliaServicoBean != null) {
			return listaAvaliaServicoBean;
		}

		try {

			listaAvaliaServicoBean = new LinkedList<AvaliaServicoBean>();
			List<ServicoVO> listaServico = null;

			if (controlaAcessoView.getGrupoUnidadeUsuario() != null) {

				// Se o grupo do usuário não for Agência
				if (!controlaAcessoView.getGrupoUnidadeUsuario().getIcAgencia()) {

					// Todos os serviços vinculados ao grupo
					List<GrupoServicoVO> listaGrupoServico = grupoServicoSR
							.listar(new GrupoServicoVO(controlaAcessoView.getGrupoUnidadeUsuario(), null));
					listaServico = new LinkedList<ServicoVO>();
					for (GrupoServicoVO grupoServicoVO : listaGrupoServico) {
						listaServico.add(grupoServicoVO.getServico());
					}
				} else {

					/*
					 * Validação para atender a RN018 Consulta o tipo da unidade
					 * do usuário logado
					 */
					UnidadeVO vo = unidadeSR.consultaNuTipoUnidade(
							getUsuarioLogado().getFuncionarioCaixaLocal().getUnidade().getNuUnidade(),
							getUsuarioLogado().getFuncionarioCaixaLocal().getUnidade().getNuNatural());

					// atribui o número do tipo da unidade do usuario logado
					getUsuarioLogado().getFuncionarioCaixaLocal().getUnidade().setNuTipoUnidade(vo.getNuTipoUnidade());

					// verifica se o usuário pertence a uma PAB ou Agência
					if (EnumTipoUnidade.AGENCIA.getCodigo()
							.equals(getUsuarioLogado().getFuncionarioCaixaLocal().getUnidade().getNuTipoUnidade())
							|| EnumTipoUnidade.PAB.getCodigo().equals(
									getUsuarioLogado().getFuncionarioCaixaLocal().getUnidade().getNuTipoUnidade())) {

						// Se não houver um funcionário local "OU" Se não tiver
						// nenhum segmento
						if (controlaAcessoView.getFuncionarioCaixaLocalVO() == null
								|| !Util.notEmpty(fncroCaixaSegmentoSR.listarSegmentoVinculadoUsuario(
										controlaAcessoView.getFuncionarioCaixaLocalVO().getFuncionarioCaixa()
												.getNuMatricula(),
										getUsuarioLogado().getFuncionarioCaixaLocal().getUnidade().getNuUnidade(),
										getUsuarioLogado().getFuncionarioCaixaLocal().getUnidade().getNuNatural()))) {
							throw new BusinessException(
									"Empregado não vinculado a nenhum segmento, para avaliar o serviço favor solicitar ao gerente vinculação.");
						}
					}

					// Carrega os serviços vinculados ao grupo e que tenham os
					// mesmo segmentos desse usuário
					listaServico = grupoServicoSR.listar(controlaAcessoView.getFuncionarioCaixaLocalVO());
				}
			}

			if (Util.notEmpty(listaServico)) {
				// Carrega todas as notícias em uma única busca
				List<Long> listaNuServico = new LinkedList<Long>();

				for (ServicoVO servicoVO : listaServico) {
					listaNuServico.add(servicoVO.getNuServico());
				}
				List<NoticiaVO> listaNoticia = noticiaSR
						.listar(listaNuServico.toArray(new Long[listaNuServico.size()]));

				// Lista todas as avaliações do usuário no dia atual
				AvaliacaoVO avaliacaoVOTemp = new AvaliacaoVO();
				avaliacaoVOTemp.setFuncionarioCaixaLocal(controlaAcessoView.getFuncionarioCaixaLocalVO());
				avaliacaoVOTemp.setDtAvaliacao(new Date());
				List<AvaliacaoVO> listaAvaliacoesAnteiores = this.getService().listar(avaliacaoVOTemp);

				for (ServicoVO servicoVO : listaServico) {
					AvaliaServicoBean avaliaServicoBean = new AvaliaServicoBean();
					listaAvaliaServicoBean.add(avaliaServicoBean);

					avaliaServicoBean.setUsouTentouUsar(false);
					avaliaServicoBean.setAvaliacaoVO(new AvaliacaoVO());
					avaliaServicoBean.getAvaliacaoVO().setServico(servicoVO);
					avaliaServicoBean.getAvaliacaoVO().setProblema(new ProblemaVO());

					avaliaServicoBean.setListaNoticia(new LinkedList<NoticiaVO>());

					for (NoticiaVO noticiaVO : listaNoticia) {
						if (noticiaVO.getServico().getNuServico().equals(servicoVO.getNuServico())) {
							avaliaServicoBean.getListaNoticia().add(noticiaVO);
						}
					}

					listaNuServico.add(servicoVO.getNuServico());

					avaliaServicoBean.setJaAvaliado(Util.notEmpty(listaAvaliacoesAnteiores));
				}

				// Se já tiver feito alguma avaliação no dia
				for (AvaliacaoVO avaliacaoVO : listaAvaliacoesAnteiores) {
					SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
					Date d = new Date();

					if (Util.notEmpty(listaAvaliacoesAnteiores) && !Operacao.ALTERAR.equals(getOperacao())
							&& avaliacaoVO.getFuncionarioCaixaLocal().getNuFncroCaixaLocal()
									.equals(getUsuarioLogado().getFuncionarioCaixaLocal().getNuFncroCaixaLocal())
							&& data.format(avaliacaoVO.getDtAvaliacao()).equals(data.format(d))) {
						throw new BusinessException("Avaliação já realizada.");
					}
				}
			}
		} catch (Throwable e) {
			this.tratarExcecao(e, null);
		}

		// serve apenas para definir qual mensagem mostrar quando for
		// executado o metodo getListaAvaliaServicoBean
		setOperacao(Operacao.LISTAR);

		return listaAvaliaServicoBean;
	}

	/**
	 * @param listaAvaliaServicoBean
	 *            List<AvaliaServicoBean>
	 */
	public void setListaAvaliaServicoBean(List<AvaliaServicoBean> listaAvaliaServicoBean) {
		this.listaAvaliaServicoBean = listaAvaliaServicoBean;
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
	 * @return NoticiaVO
	 */
	public NoticiaVO getNoticia() {
		return noticia;
	}

	/**
	 * @param noticiaVO
	 *            noticia
	 */
	public void setNoticia(NoticiaVO noticia) {
		this.noticia = noticia;
	}

	// ***********************************************************************************************************************************

	/**
	 * Salva as avaliações.
	 */
	public void salvar() {
		try {

			FuncionarioCaixaLocalVO fc = FactorySIAVS.getService(UsuarioSR.class).consultar(getUsuarioLogado())
					.getFuncionarioCaixaLocal();

			List<AvaliacaoVO> listaAvaliacao = new LinkedList<AvaliacaoVO>();

			for (AvaliaServicoBean avaliaServicoBean : this.getListaAvaliaServicoBean()) {
				if (avaliaServicoBean.getUsouTentouUsar()) {
					listaAvaliacao.add(avaliaServicoBean.getAvaliacaoVO());

					avaliaServicoBean.getAvaliacaoVO().setGrupo(controlaAcessoView.getGrupoUnidadeUsuario());
				}
				avaliaServicoBean.getAvaliacaoVO().setFuncionarioCaixaLocal(fc);
			}

			if (!Util.notEmpty(listaAvaliacao)) {
				throw new BusinessException("Selecione pelo menos 1 (um) serviço para registrar sua avaliação.");
			}

			((AvaliacaoSR) this.getService()).incluir(listaAvaliacao);

			this.tratarSucesso(null);

			listaAvaliaServicoBean = null;
		} catch (Throwable e) {
			this.tratarExcecao(e, null);
		}

		// serve apenas para definir qual mensagem mostrar quando for
		// executado o metodo getListaAvaliaServicoBean
		setOperacao(Operacao.ALTERAR);

	}

	// ***********************************************************************************************************************************
}