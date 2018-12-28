package br.gov.caixa.siavs.service;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.gov.caixa.siavs.dao.ServicoSegmentoDAO;
import br.gov.caixa.siavs.service.client.ServicoSegmentoSL;
import br.gov.caixa.siavs.service.client.ServicoSegmentoSR;
import br.gov.caixa.siavs.vo.ServicoSegmentoVO;

/**
 * <b>Title:</b> ServicoSegmentoSB <br>
 * <b>Description:</b> Serviço relacionado a servicoSegmento. <br>
 * <b>Company:</b> Spread
 * @author ${classe.autor}
 * @version: $Revision: ${classe.versao} $, $Date: 10/06/2013$
 */
@Stateless
@Remote(ServicoSegmentoSR.class)
@Local(ServicoSegmentoSL.class)
public class ServicoSegmentoSB extends AbstractServiceSIAVS<ServicoSegmentoVO> implements ServicoSegmentoSR, ServicoSegmentoSL {

	@Inject
	private void setDao(ServicoSegmentoDAO dao) {
		super.setDao(dao);
	}
}