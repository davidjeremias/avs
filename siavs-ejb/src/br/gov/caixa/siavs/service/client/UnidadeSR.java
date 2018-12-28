package br.gov.caixa.siavs.service.client;

import java.util.List;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.global.DominioSIAVS.TipoUnidade;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.UnidadeVO;

/**
 * <b>Title:</b> UnidadeSR <br>
 * <b>Description:</b> Interface remota para o serviço relacionado a unidade. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 08/07/2013$
 */
public interface UnidadeSR extends IServiceSIAVS <UnidadeVO> {
	
	/**
	 * Lista unidades não vinculadas ao grupo por estarem em outro grupo.
	 * @param grupoVO
	 * @return List<UnidadeVO>
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public List<UnidadeVO> listarUnidadeNaoVinculada(GrupoVO grupoVO) throws SystemException, BusinessException;
	
	/**
	 * Lista as unidade de relacionamento ou de produção dependendo do tipo.
	 * @param tipoUnidade Tipo da unidade
	 * @return List<UnidadeVO>
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public List<UnidadeVO> listarUnidadeRelProd(TipoUnidade tipoUnidade) throws SystemException, BusinessException;

	/**
	 * Consulta o tipo de unidade do usuário logado
	 * @param nuUnidade
	 * @param nuNatural
	 * @throws SystemException 
	 */
	public UnidadeVO consultaNuTipoUnidade(Integer nuUnidade, Integer nuNatural) throws SystemException;
}