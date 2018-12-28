package br.gov.caixa.siavs.service.client;

import java.util.List;

import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.vo.FncroCaixaSegmentoVO;

/**
 * <b>Title:</b> FncroCaixaSegmentoSR <br>
 * <b>Description:</b> Interface remota para o serviço relacionado a fncroCaixaSegmento. <br>
 * <b>Company:</b> Spread
 * @author ${classe.autor}
 * @version: $Revision: ${classe.versao} $, $Date: 10/06/2013$
 */
public interface FncroCaixaSegmentoSR extends IServiceSIAVS <FncroCaixaSegmentoVO> {

	public List<FncroCaixaSegmentoVO> listarSegmentoVinculadoUsuario(String deMatricula, Integer nuUnidade, Integer nuNatural) throws SystemException;
}