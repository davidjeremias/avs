package br.gov.caixa.siavs.service.client;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.vo.UnidadeSorteioVO;

/**
 * <b>Title:</b> UnidadeSorteioSL <br>
 * <b>Description:</b> Interface local para o serviço relacionado a unidadeSorteio. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 15/07/2013$
 */
public interface UnidadeSorteioSL extends IServiceSIAVS <UnidadeSorteioVO> {
	
	/**
	 * Realiza o sorteio das unidades.
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public void sorteio() throws SystemException, BusinessException;
}