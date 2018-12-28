package br.gov.caixa.siavs.service;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.dao.UnidadeSorteioDAO;
import br.gov.caixa.siavs.service.client.UnidadeSorteioSL;
import br.gov.caixa.siavs.service.client.UnidadeSorteioSR;
import br.gov.caixa.siavs.vo.UnidadeSorteioVO;

/**
 * <b>Title:</b> UnidadeSorteioSB <br>
 * <b>Description:</b> Serviço relacionado a unidadeSorteio. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 15/07/2013$
 */
@Stateless
@Remote(UnidadeSorteioSR.class)
@Local(UnidadeSorteioSL.class)
public class UnidadeSorteioSB extends AbstractServiceSIAVS<UnidadeSorteioVO> implements UnidadeSorteioSR, UnidadeSorteioSL {

	/**
	 * Preenche a DAO utilizada pelo serviço
	 * @param dao
	 */
	@Inject
	private void setDao(UnidadeSorteioDAO dao) {
		super.setDao(dao);
	}

	/**
	 * @see br.gov.caixa.siavs.service.client.UnidadeSorteioSL#sorteio()
	 */
	@Override
	public void sorteio() throws SystemException, BusinessException {
		((UnidadeSorteioDAO) this.getDao()).sorteio();
	}
}