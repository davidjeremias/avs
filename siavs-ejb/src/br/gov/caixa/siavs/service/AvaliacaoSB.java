package br.gov.caixa.siavs.service;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.global.Dominio.Operacao;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.dao.AvaliacaoDAO;
import br.gov.caixa.siavs.global.Mensagem.Msg;
import br.gov.caixa.siavs.service.client.AvaliacaoSL;
import br.gov.caixa.siavs.service.client.AvaliacaoSR;
import br.gov.caixa.siavs.service.client.FuncionarioCaixaLocalSL;
import br.gov.caixa.siavs.service.client.MetaSL;
import br.gov.caixa.siavs.service.client.ProblemaSL;
import br.gov.caixa.siavs.to.ConsultaPainelAvaliacaoTO;
import br.gov.caixa.siavs.vo.AvaliacaoVO;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.MetaVO;
import br.gov.caixa.siavs.vo.ProblemaVO;
import br.gov.caixa.siavs.vo.ServicoVO;

/**
 * <b>Title:</b> AvaliacaoSB <br>
 * <b>Description:</b> Serviço relacionado a Avaliação. <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: $$, $Date: $$
 */
@Stateless
@Remote(AvaliacaoSR.class)
@Local(AvaliacaoSL.class)
public class AvaliacaoSB extends AbstractServiceSIAVS<AvaliacaoVO> implements AvaliacaoSR, AvaliacaoSL {

	@Inject
	private MetaSL metaSL;
	@Inject
	private ProblemaSL problemaSL;
	@Inject
	private FuncionarioCaixaLocalSL funcionarioCaixaLocalSL;

	@Inject
	private void setDao(AvaliacaoDAO dao) {
		super.setDao(dao);
	}

	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#validar
	 */
	@Override
	public void validar(AvaliacaoVO vo, Operacao operacao) throws SystemException, BusinessException {
		if (!operacao.equals(Operacao.LISTAR)) {
			if (vo == null) {
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("avaliação"), "avaliação");
			}
		}
		if (operacao.equals(Operacao.ALTERAR) || operacao.equals(Operacao.EXCLUIR)
				|| operacao.equals(Operacao.CONSULTAR)) {
			if (!Util.notEmpty(vo.getNuAvaliacao())) {
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("identificador da avaliação"),
						"nuAvaliacao");
			}
		}
		if (operacao.equals(Operacao.EXCLUIR)) {
			throw new BusinessException(Msg.MN_EXCLUSAO_PROIBIDA.montar("avaliação", ""), "nuAvaliacao");
		}
		if (operacao.equals(Operacao.INCLUIR) || operacao.equals(Operacao.ALTERAR)) {
			if (vo.getGrupo() == null || !Util.notEmpty(vo.getGrupo().getNuGrupo())) {
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("grupo"), "nuGrupo");
			}
		}

		if (operacao.equals(Operacao.INCLUIR) || operacao.equals(Operacao.ALTERAR)) {
			if (vo.getServico() == null || !Util.notEmpty(vo.getServico().getNuServico())) {
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("serviço"), "nuServico");
			}

			String compMsg = " para o serviço: \"" + vo.getServico().getDeServico() + "\"";

			if (vo.getVrNotaAvaliacao() == null || vo.getVrNotaAvaliacao() < 0) {
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("nota da avaliação" + compMsg),
						"vrNotaAvaliacao");
			}
			if (vo.getVrNotaAvaliacao() > 10) {
				throw new BusinessException("A nota da avaliação deve estar entre 0 e 10.", "vrNotaAvaliacao");
			}

			MetaVO metaVO = metaSL
					.consultar(new MetaVO(vo.getGrupo(), new GregorianCalendar().get(GregorianCalendar.YEAR)));
			// RN012
			if (metaVO != null && Util.notEmpty(metaVO.getVrNotaMeta())
					&& vo.getVrNotaAvaliacao() < metaVO.getVrNotaMeta()) {
				if (vo.getProblema() == null || !Util.notEmpty(vo.getProblema().getNuProblema())) {
					throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("problema" + compMsg), "nuProblema");
				}
			}

			// RN013
			if (vo.getProblema() != null && Util.notEmpty(vo.getProblema().getNuProblema())
					&& !Util.notEmpty(vo.getDeComentario())) {
				ProblemaVO problemaVO = problemaSL.consultar(vo.getProblema());

				if (problemaVO != null && problemaVO.getIcGenerico()) {
					throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("comentário" + compMsg),
							"deComentario");
				}
			}

			if (vo.getFuncionarioCaixaLocal() == null
					|| !Util.notEmpty(vo.getFuncionarioCaixaLocal().getNuFncroCaixaLocal())) {
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("funcionário" + compMsg),
						"nuFncroCaixaLocal");
			}
		}
	}

	/**
	 * @see br.gov.caixa.siavs.service.client.AvaliacaoSR#consultarPrimeiroAnoAvaliacao(br.gov.caixa.siavs.vo.GrupoVO)
	 */
	@Override
	public Integer consultarPrimeiroAnoAvaliacao(GrupoVO grupo) throws SystemException, BusinessException {
		return ((AvaliacaoDAO) this.getDao()).consultarPrimeiroAnoAvaliacao(grupo);
	}

	/**
	 * @see br.gov.caixa.siavs.service.client.AvaliacaoSR#listarAvaliacoesPainel(br.gov.caixa.siavs.vo.GrupoVO,
	 *      java.util.Date, java.util.Date, java.util.List, java.util.List)
	 */
	@Override
	public List<ConsultaPainelAvaliacaoTO> listarAvaliacoesPainel(GrupoVO grupo, Date dtInicial, Date dtFinal,
			List<ServicoVO> listaServico, List<ProblemaVO> listaProblema) throws SystemException, BusinessException {
		AvaliacaoVO avaliacaoVO = new AvaliacaoVO();
		avaliacaoVO.setGrupo(grupo);

		this.validar(avaliacaoVO, Operacao.LISTAR);
		this.validarPeriodoDatas(dtInicial, dtFinal);

		return ((AvaliacaoDAO) this.getDao()).listarAvaliacoesPainel(grupo, dtInicial, dtFinal, listaServico,
				listaProblema);
	}

	/**
	 * @see br.gov.caixa.siavs.service.client.AvaliacaoSR#listarAvaliacoesPorPeriodo(br.gov.caixa.siavs.vo.GrupoVO,
	 *      java.util.List, java.util.Date, java.util.Date)
	 */
	@Override
	public List<AvaliacaoVO> listarAvaliacoesPorPeriodo(GrupoVO grupo, List<ServicoVO> listaServico, Date dtInicial,
			Date dtFinal) throws SystemException, BusinessException {
		this.validarPeriodoDatas(dtInicial, dtFinal);
		return ((AvaliacaoDAO) this.getDao()).listarPorPeriodo(grupo, listaServico, dtInicial, dtFinal);
	}

	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#preOperacao
	 */
	@Override
	public AvaliacaoVO preOperacao(AvaliacaoVO vo, Operacao operacao) throws SystemException, BusinessException {
		if (Operacao.INCLUIR.equals(operacao)) {
			funcionarioCaixaLocalSL.salvar(vo.getFuncionarioCaixaLocal());
		}

		return vo;
	}

	/**
	 * @see br.gov.caixa.siavs.service.client.AvaliacaoSR#listarMediasPorPeriodo(br.gov.caixa.siavs.vo.ServicoVO,
	 *      boolean)
	 */
	@Override
	public List<ConsultaPainelAvaliacaoTO> listarMediasPorPeriodo(ServicoVO servico, boolean ultimaQuinzena,
			Date dtInicial) throws SystemException, BusinessException {
		return ((AvaliacaoDAO) this.getDao()).listarMediasPorPeriodo(servico, ultimaQuinzena, dtInicial);
	}

	/**
	 * Valida o período de datas.
	 * 
	 * @param dtInicial
	 *            Date
	 * @param dtFinal
	 *            Date
	 */
	private void validarPeriodoDatas(Date dtInicial, Date dtFinal) throws BusinessException {
		if (dtInicial == null) {
			throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("data inicial da avaliação"), "dtInicial");
		}
		if (dtFinal == null) {
			throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("data final da avaliação"), "dtFinal");
		}
		// RN017
		if (dtFinal.after(new Date())) {
			throw new BusinessException("Deve ser informada uma data final igual ou inferior a data atual.", "dtFinal");
		}
		if (dtFinal.before(dtInicial)) {
			throw new BusinessException("A data final deve ser maior ou igual a data inicial.", "dtFinal");
		}
	}
}