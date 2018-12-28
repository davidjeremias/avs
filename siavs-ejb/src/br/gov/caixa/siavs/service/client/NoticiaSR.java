package br.gov.caixa.siavs.service.client;

import java.util.List;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.NoticiaVO;

/**
 * <b>Title:</b> NoticiaSR <br>
 * <b>Description:</b> Interface remota para o serviço relacionado a notícia. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 17/06/2013$
 */
public interface NoticiaSR extends IServiceSIAVS <NoticiaVO> {
	
	/**
	 * Lista as notícias pelos serviços e onde o intervalo contemple a data atual.
	 * @param arrayNuServico Long[]
	 * @return List<NoticiaVO>
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public List<NoticiaVO> listar(Long[] arrayNuServico) throws SystemException, BusinessException;
	
	/**
	 * Lista as notícias filtrando pelos serviços vinculados ao grupo.
	 * @param grupoVO GrupoVO
	 * @return List<NoticiaVO>
	 * @throws BusinessException
	 */
	public List<NoticiaVO> listarPorGrupo(GrupoVO grupoVO) throws SystemException, BusinessException;
}