package br.gov.caixa.siavs.service.client;

import java.util.Date;
import java.util.List;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.to.ConsultaPainelAvaliacaoTO;
import br.gov.caixa.siavs.vo.AvaliacaoVO;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.ProblemaVO;
import br.gov.caixa.siavs.vo.ServicoVO;

/**
 * <b>Title:</b> AvaliacaoSR <br>
 * <b>Description:</b> Interface remota para o serviço relacionado a Avaliação.
 * <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: $$, $Date: $$
 */
public interface AvaliacaoSR extends IServiceSIAVS<AvaliacaoVO> {

	/**
	 * Consulta o ano da primeira avaliação.
	 * 
	 * @param grupo
	 *            GrupoVO
	 * @return Integer
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public Integer consultarPrimeiroAnoAvaliacao(GrupoVO grupo) throws SystemException, BusinessException;

	/**
	 * Lista as avaliações para o painel de avaliações.
	 * 
	 * @param grupo
	 *            GrupoVO
	 * @param dtInicial
	 *            Date
	 * @param dtFinal
	 *            Date
	 * @param listaServico
	 *            List<ServicoVO>
	 * @param listaProblema
	 *            List<ProblemaVO>
	 * @return List<ConsultaPainelAvaliacaoTO>
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public List<ConsultaPainelAvaliacaoTO> listarAvaliacoesPainel(GrupoVO grupo, Date dtInicial, Date dtFinal,
			List<ServicoVO> listaServico, List<ProblemaVO> listaProblema) throws SystemException, BusinessException;

	/**
	 * Lista as avaliações por período.
	 * 
	 * @param grupo
	 *            GrupoVO
	 * @param listaServico
	 *            List<ServicoVO>
	 * @param dtInicial
	 *            Date
	 * @param dtFinal
	 *            Date
	 * @return List<AvaliacaoVO>
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public List<AvaliacaoVO> listarAvaliacoesPorPeriodo(GrupoVO grupo, List<ServicoVO> listaServico, Date dtInicial,
			Date dtFinal) throws SystemException, BusinessException;

	/**
	 * Lista as médias por período.
	 * 
	 * @param servico
	 *            Serviço que deve ser listado
	 * @param ultimaQuinzena
	 *            Se é a última quinzena ou será o último semestre
	 * @return List<ConsultaPainelAvaliacaoTO>
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public List<ConsultaPainelAvaliacaoTO> listarMediasPorPeriodo(ServicoVO servico, boolean ultimaQuinzena, Date dtInicial)
			throws SystemException, BusinessException;
}