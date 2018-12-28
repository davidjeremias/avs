package br.gov.caixa.siavs.service.client;

import java.util.List;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.vo.UnidadeVO;

/**
 * <b>Title:</b> UnidadeSL <br>
 * <b>Description:</b> Interface local para o serviço relacionado a unidade. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 08/07/2013$
 */
public interface UnidadeSL extends IServiceSIAVS <UnidadeVO> {
	
	/**
	 * Envia os email de pendência de designação.
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public void enviarEmailPendenciaDesignacao() throws SystemException, BusinessException;
	
	/**
	 * Lista as unidades vinculadas a unidade pai.
	 * @param vo
	 * @return List<UnidadeVO>
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public List<UnidadeVO> listarUnidadeVinculada(UnidadeVO vo) throws SystemException, BusinessException;
}