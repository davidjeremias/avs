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
import br.gov.caixa.siavs.dao.SegmentoDAO;
import br.gov.caixa.siavs.global.Mensagem.Msg;
import br.gov.caixa.siavs.service.client.FncroCaixaSegmentoSL;
import br.gov.caixa.siavs.service.client.SegmentoSL;
import br.gov.caixa.siavs.service.client.SegmentoSR;
import br.gov.caixa.siavs.service.client.ServicoSegmentoSL;
import br.gov.caixa.siavs.vo.FncroCaixaSegmentoVO;
import br.gov.caixa.siavs.vo.SegmentoVO;
import br.gov.caixa.siavs.vo.ServicoSegmentoVO;

/**
 * <b>Title:</b> SegmentoSB <br>
 * <b>Description:</b> Serviço relacionado a segmento. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 07/06/2013$
 */
@Stateless
@Remote(SegmentoSR.class)
@Local(SegmentoSL.class)
public class SegmentoSB extends AbstractServiceSIAVS<SegmentoVO> implements SegmentoSR, SegmentoSL {

	@Inject
	private ServicoSegmentoSL servicoSegmentoSL;
	@Inject
	private FncroCaixaSegmentoSL fncroCaixaSegmentoSL;
	
	@Inject
	private void setDao(SegmentoDAO dao) {
		super.setDao(dao);
	}

	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#validar
	 */
	@Override
	public void validar(SegmentoVO vo, Operacao operacao) throws SystemException, BusinessException {
			if(!operacao.equals(Operacao.LISTAR)) {
				if(vo == null){
					throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("segmento"), "segmento");
				}
			}
			if(operacao.equals(Operacao.ALTERAR) || operacao.equals(Operacao.EXCLUIR) || operacao.equals(Operacao.CONSULTAR)) {
				if(! Util.notEmpty(vo.getNuSegmento())){
					throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("identificador do segmento"), "nuSegmento");
				}
			}
			if(operacao.equals(Operacao.EXCLUIR)) {
				List<ServicoSegmentoVO> listaServicoSegmento = servicoSegmentoSL.listar(new ServicoSegmentoVO(null, vo));
				// Se encontrar algum registro
				if(Util.notEmpty(listaServicoSegmento)){
					throw new BusinessException(Msg.MN015.montar("segmento", "serviço"), "segmento");
				}
				
				List<FncroCaixaSegmentoVO> listaFncroCaixaSegmento = fncroCaixaSegmentoSL.listar(new FncroCaixaSegmentoVO(null, vo));
				// Se encontrar algum registro
				if(Util.notEmpty(listaFncroCaixaSegmento)){
					throw new BusinessException(Msg.MN_EXCLUSAO_PROIBIDA.montar("segmento", "pois já tem um avaliador designado"), "segmento");
				}
			}
			if(operacao.equals(Operacao.INCLUIR) || operacao.equals(Operacao.ALTERAR)) {
				if(! Util.notEmpty(vo.getNoSegmento())){
					throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("nome do segmento"), "noSegmento");
				}
				
				SegmentoVO voTemp = this.getDao().consultar(new SegmentoVO(vo.getNoSegmento()));
				// Se encontrar algum registro "E" Se não for o registro atual
					if(voTemp != null && !voTemp.getNuSegmento().equals(vo.getNuSegmento())){
	//				throw new BusinessException(Msg.MN_INCLUSAO_PROIBIDA.montar("segmento", "pois já existe um segmento cadastrado com esse nome"), "nuSegmento");
				if(voTemp != null && !voTemp.getNuSegmento().equals(vo.getNuSegmento())){
					throw new BusinessException(vo.getNoSegmento() + " já cadastrado.");
				}		
			}
		}
	}
}