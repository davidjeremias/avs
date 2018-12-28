package br.gov.caixa.siavs.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.global.Dominio.Operacao;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.dao.FuncionarioCaixaLocalDAO;
import br.gov.caixa.siavs.global.Mensagem.Msg;
import br.gov.caixa.siavs.service.client.FncroCaixaSegmentoSL;
import br.gov.caixa.siavs.service.client.FuncionarioCaixaLocalSL;
import br.gov.caixa.siavs.service.client.FuncionarioCaixaLocalSR;
import br.gov.caixa.siavs.vo.FncroCaixaSegmentoVO;
import br.gov.caixa.siavs.vo.FuncionarioCaixaLocalVO;
import br.gov.caixa.siavs.vo.FuncionarioCaixaVO;

/**
 * <b>Title:</b> FuncionarioCaixaLocalSB <br>
 * <b>Description:</b> Serviço relacionado a funcionário caixa local. <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 07/06/2013$
 */
@Stateless
@Remote(FuncionarioCaixaLocalSR.class)
@Local(FuncionarioCaixaLocalSL.class)
public class FuncionarioCaixaLocalSB extends AbstractServiceSIAVS<FuncionarioCaixaLocalVO>
		implements FuncionarioCaixaLocalSR, FuncionarioCaixaLocalSL {

	@Inject
	private FncroCaixaSegmentoSL fncroCaixaSegmentoSL;

	@Inject
	private void setDao(FuncionarioCaixaLocalDAO dao) {
		super.setDao(dao);
	}

	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#validar
	 */
	public void validar(FuncionarioCaixaLocalVO vo, Operacao operacao) throws SystemException, BusinessException {
		if (operacao.equals(Operacao.EXCLUIR)) {
			throw new BusinessException(Msg.MN_EXCLUSAO_PROIBIDA.montar("funcionário caixa local", ""),
					"funcionarioCaixaLocalVO");
		}
		if (operacao.equals(Operacao.INCLUIR) || operacao.equals(Operacao.ALTERAR)) {
			if (vo.getFuncionarioCaixa() == null || !Util.notEmpty(vo.getFuncionarioCaixa().getNuMatricula())) {
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("funcionário caixa"), "nuMatricula");
			}
			if (vo.getUnidade() == null || (!Util.notEmpty(vo.getUnidade().getNuUnidade())
					|| !Util.notEmpty(vo.getUnidade().getNuNatural()))) {
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("unidade"), "nuUnidade");
			}
		}
	}

	/**
	 * @see br.gov.caixa.siavs.service.client.FuncionarioCaixaLocalSR#listarTodos(br.gov.caixa.siavs.vo.FuncionarioCaixaLocalVO)
	 */
	@Override
	public List<FuncionarioCaixaLocalVO> listarTodos(FuncionarioCaixaLocalVO vo)
			throws SystemException, BusinessException {
		List<FuncionarioCaixaLocalVO> listaVO = ((FuncionarioCaixaLocalDAO) this.getDao()).listarTodos(vo);

		for (FuncionarioCaixaLocalVO funcionarioCaixaLocalVO : listaVO) {
			// Se tiver um id signifca que existe um registro local para este
			// usuário
			if (Util.notEmpty(funcionarioCaixaLocalVO.getNuFncroCaixaLocal())) {
				funcionarioCaixaLocalVO.setListaSegmento(
						fncroCaixaSegmentoSL.listar(new FncroCaixaSegmentoVO(funcionarioCaixaLocalVO, null)));
			}
		}

		return listaVO;
	}

	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#incluir(br.com.spread.framework.vo.AbstractVO)
	 */
	@Override
	public FuncionarioCaixaLocalVO incluir(FuncionarioCaixaLocalVO vo) throws SystemException, BusinessException {
		return this.salvarTudo(vo);
	}

	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#alterar(br.com.spread.framework.vo.AbstractVO)
	 */
	@Override
	public FuncionarioCaixaLocalVO alterar(FuncionarioCaixaLocalVO vo) throws SystemException, BusinessException {
		return this.salvarTudo(vo);
	}

	/**
	 * Salva o registro do funcionário local e a lista de segmentos.
	 * 
	 * @param vo
	 *            FuncionarioCaixaLocalVO
	 * @return FuncionarioCaixaLocalVO
	 * @throws SystemException
	 * @throws BusinessException
	 */
	private FuncionarioCaixaLocalVO salvarTudo(FuncionarioCaixaLocalVO vo) throws SystemException, BusinessException {
		this.salvar(vo);

		// Exclui os segmentos vinculados
		fncroCaixaSegmentoSL.excluir(new FncroCaixaSegmentoVO(vo, null));

		if (Util.notEmpty(vo.getListaSegmento())) {
			// Insere os segmentos vinculados
			fncroCaixaSegmentoSL.incluir(vo.getListaSegmento());
		}

		if (!Util.notEmpty(vo.getListaSegmento())) {
			throw new BusinessException(Msg.MN008.toString());
		}
		// this.posOperacao(vo, Operacao.ALTERAR);
		return vo;
	}

	/**
	 * @see br.gov.caixa.siavs.service.client.FuncionarioCaixaLocalSL#salvar(br.gov.caixa.siavs.vo.FuncionarioCaixaLocalVO)
	 */
	@Override
	public FuncionarioCaixaLocalVO salvar(FuncionarioCaixaLocalVO vo) throws SystemException, BusinessException {
		// Se o registro ainda não existir
		if (!Util.notEmpty(vo.getNuFncroCaixaLocal())) {
			FuncionarioCaixaLocalVO funcionarioCaixaLocalVO = this.consultar(vo);
			if (funcionarioCaixaLocalVO == null || !Util.notEmpty(funcionarioCaixaLocalVO.getNuFncroCaixaLocal())) {
				// Insere o novo registro
				funcionarioCaixaLocalVO = super.incluir(vo);
			}
			vo.setNuFncroCaixaLocal(funcionarioCaixaLocalVO.getNuFncroCaixaLocal());
		}

		return vo;
	}

	@Override
	public void criarNovo(FuncionarioCaixaVO vo) throws SystemException, BusinessException {
		((FuncionarioCaixaLocalDAO) getDao()).criarNovo(vo);
	}
}
