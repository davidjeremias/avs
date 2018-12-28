package br.gov.caixa.siavs.service;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.global.Dominio.Operacao;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.dao.ProblemaDAO;
import br.gov.caixa.siavs.service.client.ProblemaSL;
import br.gov.caixa.siavs.service.client.ProblemaSR;
import br.gov.caixa.siavs.vo.ProblemaVO;

/**
 * <b>Title:</b> ProblemaSB <br>
 * <b>Description:</b> Serviço relacionado a Problema. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: $$, $Date: $$
 */
@Stateless
@Remote(ProblemaSR.class)
@Local(ProblemaSL.class)
public class ProblemaSB extends AbstractServiceSIAVS <ProblemaVO> implements ProblemaSR, ProblemaSL {

	@Inject
	private void setDao(ProblemaDAO dao) {
		super.setDao(dao);
	}
	
	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#validar
	 */
	public void validar(ProblemaVO vo, Operacao operacao) throws SystemException, BusinessException {
		if(operacao.equals(Operacao.ALTERAR) || operacao.equals(Operacao.EXCLUIR) || operacao.equals(Operacao.CONSULTAR)) {
			if(! Util.notEmpty(vo.getNuProblema())){
				throw new BusinessException("É necessário informar o identificador do problema.");
			}
		}
		if(operacao.equals(Operacao.INCLUIR) || operacao.equals(Operacao.ALTERAR)) {
			if(! Util.notEmpty(vo.getDeProblema())){
				throw new BusinessException("É necessário informar a descrição do problema.");
			}
			
			// Validando chave única
			try {
				ProblemaVO voTemp = super.getDao().consultar(new ProblemaVO(vo.getDeProblema()));
				// Se encontrar algum registro "E" Se não for o registro atual
				if(voTemp != null && !voTemp.getNuProblema().equals(vo.getNuProblema())){
					throw new BusinessException("Já existe um problema cadastrado com essa descrição.");
				}
			}
			catch (SystemException e) {
				throw new BusinessException("Ocorreu um erro durante a validação do problema.", e);
			}
		}
	}
}