package br.gov.caixa.siavs.service.client;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.vo.FuncionarioCaixaLocalVO;

/**
 * <b>Title:</b> FuncionarioCaixaLocalSL <br>
 * <b>Description:</b> Interface local para o serviço relacionado a funcionário caixa local. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 07/06/2013$
 */
public interface FuncionarioCaixaLocalSL extends IServiceSIAVS <FuncionarioCaixaLocalVO> {
	
	/**
	 * Salva o registro do funcionário local.
	 * @param vo FuncionarioCaixaLocalVO
	 * @return FuncionarioCaixaLocalVO
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public FuncionarioCaixaLocalVO salvar(FuncionarioCaixaLocalVO vo) throws SystemException, BusinessException;
}
