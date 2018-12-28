package br.gov.caixa.siavs.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.global.Dominio.Operacao;
import br.gov.caixa.siavs.dao.FncroCaixaSegmentoDAO;
import br.gov.caixa.siavs.service.client.FncroCaixaSegmentoSL;
import br.gov.caixa.siavs.service.client.FncroCaixaSegmentoSR;
import br.gov.caixa.siavs.vo.FncroCaixaSegmentoVO;

/**
 * <b>Title:</b> FncroCaixaSegmentoSB <br>
 * <b>Description:</b> Serviço relacionado a fncroCaixaSegmento. <br>
 * <b>Company:</b> Spread
 * @author ${classe.autor}
 * @version: $Revision: ${classe.versao} $, $Date: 10/06/2013$
 */
@Stateless
@Remote(FncroCaixaSegmentoSR.class)
@Local(FncroCaixaSegmentoSL.class)
public class FncroCaixaSegmentoSB extends AbstractServiceSIAVS<FncroCaixaSegmentoVO> implements FncroCaixaSegmentoSR, FncroCaixaSegmentoSL {

	@Inject
	private void setDao(FncroCaixaSegmentoDAO dao) {
		super.setDao(dao);
	}

	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#validar
	 */
	public void validar(FncroCaixaSegmentoVO vo, Operacao operacao) throws SystemException, BusinessException {
	}

	@Override
	public List<FncroCaixaSegmentoVO> listarSegmentoVinculadoUsuario(String deMatricula, Integer nuUnidade, Integer nuNatural) throws SystemException {
		return  ((FncroCaixaSegmentoDAO) this.getDao()).listarSegmetosComUsuarioCaixaLocalVinculado(deMatricula, nuUnidade, nuNatural);
	}
}