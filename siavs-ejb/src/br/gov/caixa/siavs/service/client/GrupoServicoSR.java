package br.gov.caixa.siavs.service.client;

import java.util.List;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.vo.FuncionarioCaixaLocalVO;
import br.gov.caixa.siavs.vo.GrupoServicoVO;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.ServicoVO;

/**
 * <b>Title:</b> GrupoServicoSR <br>
 * <b>Description:</b> Interface remota para o serviço relacionado a grupoServico. <br>
 * <b>Company:</b> Spread
 * @author ${classe.autor}
 * @version: $Revision: ${classe.versao} $, $Date: 14/06/2013$
 */
public interface GrupoServicoSR extends IServiceSIAVS <GrupoServicoVO> {
	
	/**
	 * Salva os registros de GrupoServico
	 * @param grupoVO GrupoVO
	 * @param listaGrupoServico List<GrupoServicoVO>
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public void salvar(GrupoVO grupoVO, List<GrupoServicoVO> listaGrupoServico) throws SystemException, BusinessException;
	
	/**
	 * Lista os serviços que podem ser avaliados por determinado usuário.
	 * @param funcionarioCaixaLocalVO FuncionarioCaixaLocalVO
	 * @return List<ServicoVO>
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public List<ServicoVO> listar(FuncionarioCaixaLocalVO funcionarioCaixaLocalVO) throws SystemException, BusinessException;
}