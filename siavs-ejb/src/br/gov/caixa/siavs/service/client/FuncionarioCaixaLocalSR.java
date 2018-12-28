package br.gov.caixa.siavs.service.client;

import java.util.List;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.vo.FuncionarioCaixaLocalVO;
import br.gov.caixa.siavs.vo.FuncionarioCaixaVO;

/**
 * <b>Title:</b> FuncionarioCaixaLocalSR <br>
 * <b>Description:</b> Interface remota para o serviço relacionado a funcionário
 * caixa local. <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 07/06/2013$
 */
public interface FuncionarioCaixaLocalSR extends IServiceSIAVS<FuncionarioCaixaLocalVO> {

	/**
	 * Lista os funcionários da caixa juntamente com as informações dos
	 * funcionários locais.
	 * 
	 * @param vo
	 *            FuncionarioCaixaLocalVO
	 * @return List<FuncionarioCaixaLocalVO>
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public List<FuncionarioCaixaLocalVO> listarTodos(FuncionarioCaixaLocalVO vo)
			throws SystemException, BusinessException;

	/**
	 * 
	 * @param vo
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public void criarNovo(FuncionarioCaixaVO vo) throws SystemException, BusinessException;

}