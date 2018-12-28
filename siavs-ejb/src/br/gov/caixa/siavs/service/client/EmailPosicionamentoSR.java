package br.gov.caixa.siavs.service.client;

import java.util.Date;
import java.util.List;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.vo.EmailPosicionamentoVO;
import br.gov.caixa.siavs.vo.GrupoVO;

/**
 * <b>Title:</b> EmailPosicionamentoSR <br>
 * <b>Description:</b> Interface remota para o serviço relacionado a email posicionamento. <br>
 * <b>Company:</b> Spread
 * @author joana.espindola
 * @version: $Revision: 1.0 $, $Date: 07/06/2013$
 */
public interface EmailPosicionamentoSR extends IServiceSIAVS<EmailPosicionamentoVO> {

	/**
	 * Busca os emails de filtrando por grupo, serviço e período de datas.
	 * @param voFiltro EmailPosicionamentoVO
	 * @param dtInicio Date
	 * @param dtFim Date
	 * @return List<EmailPosicionamentoVO>
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public List<EmailPosicionamentoVO> buscarEmails(EmailPosicionamentoVO voFiltro, Date dtInicio, Date dtFim) throws SystemException, BusinessException;
	
	/**
	 * Inclui email posicionamento passando uma lista de grupo
	 * @param vo
	 * @param grupoVO
	 * @throws SystemException
	 * @throws BusinessException 
	 */
	public void incluiEmailPosicionamentoEmLote(EmailPosicionamentoVO vo, List<GrupoVO> grupoVO) throws SystemException, BusinessException;
}