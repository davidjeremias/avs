package br.gov.caixa.siavs.service.client;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.vo.EmailPosicionamentoVO;

/**
 * <b>Title:</b> EmailPosicionamentoSL <br>
 * <b>Description:</b> Interface local para o serviço relacionado a EmailPosicionamento. <br>
 * <b>Company:</b> Spread
 * @author joana.espindola
 * @version: $Revision: 1.0 $, $Date: 07/06/2013$
 */
public interface EmailPosicionamentoSL extends IServiceSIAVS<EmailPosicionamentoVO> {

	/**
	 * Envia os emails de pocionamento.
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public void enviarEmail() throws SystemException, BusinessException;
}