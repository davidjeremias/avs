package br.gov.caixa.siavs.service.client;

import java.util.Date;
import java.util.List;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.vo.FuncionarioCaixaVO;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.ServicoVO;

/**
 * <b>Title:</b> FuncionarioCaixaSL <br>
 * <b>Description:</b> Interface local para o serviço relacionado a funcionário caixa. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 28/06/2013$
 */
public interface FuncionarioCaixaSL extends IServiceSIAVS <FuncionarioCaixaVO> {
	
	/**
	 * Monta a lista de destinatários com os funcionarios que deverão receber email de convocação de avaliação.
	 * @param grupoVO GrupoVO
	 * @param data Date
	 * @return List<String>
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public List<String> montaListaDestinatariosConvocacaoAgencia(GrupoVO grupoVO, Date data) throws SystemException, BusinessException;
	
	/**
	 * Lista todos os funcionários vinculados a um determinado grupo.
	 * @param grupoVO GrupoVO
	 * @return List<String>
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public List<String> montaListaDestinatariosGrupo(GrupoVO grupoVO) throws SystemException, BusinessException;
	
	/**
	 * Lista todos os funcionários vinculados a um determinado grupo/serviço.
	 * @param grupoVO GrupoVO
	 * @return List<String>
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public List<String> montaListaDestinatariosGrupoServico(GrupoVO grupoVO, ServicoVO servicoVO) throws SystemException, BusinessException;
}