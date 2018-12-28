package br.gov.caixa.siavs.service.client;

import java.util.List;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.ServicoVO;

/**
 * <b>Title:</b> ServicoSR <br>
 * <b>Description:</b> Interface remota para o serviço relacionado a serviço. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 07/06/2013$
 */
public interface ServicoSR extends IServiceSIAVS <ServicoVO> {

	/**
	 * Lista os serviços que já tiveram avaliação para o grupo.
	 * @param grupoVO GrupoVO
	 * @return List<ServicoVO>
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public List<ServicoVO> listarServicosAvaliados(GrupoVO grupoVO) throws SystemException, BusinessException;
}