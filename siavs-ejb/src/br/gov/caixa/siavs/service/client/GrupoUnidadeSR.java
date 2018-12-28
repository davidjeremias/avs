package br.gov.caixa.siavs.service.client;

import java.util.List;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.to.UnidadeVinculadoraTO;
import br.gov.caixa.siavs.vo.GrupoUnidadeVO;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.UnidadeVO;

/**
 * <b>Title:</b> GrupoUnidadeSR <br>
 * <b>Description:</b> Interface remota para o serviço relacionado a grupoUnidade. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 14/06/2013$
 */
public interface GrupoUnidadeSR extends IServiceSIAVS <GrupoUnidadeVO> {
	
	/**
	 * Lista o vínculo grupo-unidade em formato hierárquico (Tree) por grupo
	 * @param grupoVO
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public List<UnidadeVinculadoraTO> listarUnidadeTreeNode(GrupoVO grupoVO) throws SystemException, BusinessException;
	
	/**
	 * inclui unidade e suas vinculadas, retornando as que não puderam ser incluídas.
	 * @param vo
	 * @return List<UnidadeVO>
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public List<UnidadeVO> incluirVinculadas(GrupoUnidadeVO vo) throws SystemException, BusinessException;
	
	/**
	 * Exclui o vínculo grupo-unidade e seus filhos
	 * @param unidadeVinculadora
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public void excluirUnidadeTreeNode(UnidadeVinculadoraTO unidadeVinculadora) throws SystemException, BusinessException;
}