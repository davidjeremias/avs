package br.gov.caixa.siavs.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.dao.FuncionarioCaixaDAO;
import br.gov.caixa.siavs.global.DominioSIAVS;
import br.gov.caixa.siavs.service.client.FuncionarioCaixaSL;
import br.gov.caixa.siavs.service.client.FuncionarioCaixaSR;
import br.gov.caixa.siavs.vo.FuncionarioCaixaVO;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.ServicoVO;

/**
 * <b>Title:</b> FuncionarioCaixaSB <br>
 * <b>Description:</b> Serviço relacionado a funcionário caixa. <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 28/06/2013$
 */
@Stateless
@Remote(FuncionarioCaixaSR.class)
@Local(FuncionarioCaixaSL.class)
public class FuncionarioCaixaSB extends AbstractServiceSIAVS<FuncionarioCaixaVO>
		implements FuncionarioCaixaSR, FuncionarioCaixaSL {

	@Inject
	private void setDao(FuncionarioCaixaDAO dao) {
		super.setDao(dao);
	}

	/**
	 * @see br.gov.caixa.siavs.service.client.FuncionarioCaixaSL#montaListaDestinatariosConvocacaoAgencia(java.util.Date,
	 *      br.gov.caixa.siavs.vo.GrupoVO)
	 */
	@Override
	public List<String> montaListaDestinatariosConvocacaoAgencia(GrupoVO grupoVO, Date data)
			throws SystemException, BusinessException {
		return this.montaListaDestinatarios(
				((FuncionarioCaixaDAO) this.getDao()).listarFuncionariosConvocacaoAgencia(data, grupoVO));
	}

	/**
	 * @see br.gov.caixa.siavs.service.client.FuncionarioCaixaSL#montaListaDestinatariosGrupo(br.gov.caixa.siavs.vo.GrupoVO)
	 */
	@Override
	public List<String> montaListaDestinatariosGrupo(GrupoVO grupoVO) throws SystemException, BusinessException {
		return this.montaListaDestinatarios(((FuncionarioCaixaDAO) this.getDao()).listarFuncionariosGrupo(grupoVO));
	}

	/**
	 * @see br.gov.caixa.siavs.service.client.FuncionarioCaixaSL#montaListaDestinatariosGrupoServico(br.gov.caixa.siavs.vo.GrupoVO,
	 *      br.gov.caixa.siavs.vo.ServicoVO)
	 */
	@Override
	public List<String> montaListaDestinatariosGrupoServico(GrupoVO grupoVO, ServicoVO servicoVO)
			throws SystemException, BusinessException {
		return this.montaListaDestinatarios(
				((FuncionarioCaixaDAO) this.getDao()).listarFuncionarioGrupoServico(grupoVO, servicoVO));
	}

	/**
	 * Monta a lista de destinatários.
	 * 
	 * @param listaFuncionarios
	 *            List<FuncionarioCaixaVO>
	 * @return List<String>
	 * @throws SystemException
	 * @throws BusinessException
	 */
	private List<String> montaListaDestinatarios(List<FuncionarioCaixaVO> listaFuncionarios)
			throws SystemException, BusinessException {
		List<String> destinatarios = new LinkedList<String>();

		for (FuncionarioCaixaVO vo : listaFuncionarios) {
			destinatarios.add(vo.getDeMatricula() + DominioSIAVS.EMAIL_CAIXA_COMPLEMENTO);
		}
		return destinatarios;
	}
}
