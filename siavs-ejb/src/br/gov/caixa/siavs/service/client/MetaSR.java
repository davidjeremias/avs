package br.gov.caixa.siavs.service.client;

import java.util.List;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.vo.MetaVO;

/**
 * <b>Title:</b> MetaSR <br>
 * <b>Description:</b> Interface remota para o serviço relacionado a meta. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 11/06/2013$
 */
public interface MetaSR extends IServiceSIAVS <MetaVO> {
	
	/**
	 * Salva as metas.
	 * @param listaMeta List<MetaVO>
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public void salvar(List<MetaVO> listaMeta) throws SystemException, BusinessException;
}