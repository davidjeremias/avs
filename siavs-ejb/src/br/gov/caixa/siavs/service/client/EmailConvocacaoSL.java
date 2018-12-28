package br.gov.caixa.siavs.service.client;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.vo.EmailConvocacaoVO;

/**
 * <b>Title:</b> EmailConvocacaoSL <br>
 * <b>Description:</b> Interface local para o serviço relacionado a emailConvocacao. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 04/07/2013$
 */
public interface EmailConvocacaoSL extends IServiceSIAVS <EmailConvocacaoVO> {
	
	/**
	 * Envia email de convocação
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public void enviarEmail() throws SystemException, BusinessException;
	
	/**
	 * Envia email de convocação para agência.
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public void enviarEmailAgencia() throws SystemException, BusinessException;
}