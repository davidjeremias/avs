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
import br.gov.caixa.siavs.dao.MetaDAO;
import br.gov.caixa.siavs.global.Mensagem.Msg;
import br.gov.caixa.siavs.service.client.MetaSL;
import br.gov.caixa.siavs.service.client.MetaSR;
import br.gov.caixa.siavs.vo.MetaVO;

/**
 * <b>Title:</b> MetaSB <br>
 * <b>Description:</b> Serviço relacionado a meta. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 11/06/2013$
 */
@Stateless
@Remote(MetaSR.class)
@Local(MetaSL.class)
public class MetaSB extends AbstractServiceSIAVS<MetaVO> implements MetaSR, MetaSL {

	@Inject
	private void setDao(MetaDAO dao) {
		super.setDao(dao);
	}

	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#validar
	 */
	@Override
	public void validar(MetaVO vo, Operacao operacao) throws SystemException, BusinessException {
		if(! operacao.equals(Operacao.LISTAR)) {
			if(vo == null){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("meta"), "meta");
			}
		}
		if(operacao.equals(Operacao.ALTERAR)) {
			if(! Util.notEmpty(vo.getNuMeta())){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("identificador da meta"), "nuMeta");
			}
		}
		if(operacao.equals(Operacao.INCLUIR) || operacao.equals(Operacao.ALTERAR) || operacao.equals(Operacao.EXCLUIR)) {
			if(vo.getGrupo() == null || !Util.notEmpty(vo.getGrupo().getNuGrupo())){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("grupo"), "grupo");
			}
		}
		if(operacao.equals(Operacao.INCLUIR) || operacao.equals(Operacao.ALTERAR)) {
			if(! Util.notEmpty(vo.getAaCompetencia())){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("ano"), "aaCompetencia");
			}
			if(Util.notEmpty(vo.getVrNotaMeta()) && (vo.getVrNotaMeta() < 0 || vo.getVrNotaMeta() > 10)){
				throw new BusinessException(Msg.MN_DADOS_INVALIDOS.getMsg(), "vrNotaMeta");
			}
		}
	}
	
	/**
	 * @see br.gov.caixa.siavs.service.client.MetaSR#salvar(java.util.List)
	 */
	@Override
	public void salvar(List<MetaVO> listaMeta) throws SystemException, BusinessException {
		// Valida todos os registros
		for (MetaVO vo : listaMeta) {
			if(vo != null){
				if(!Util.notEmpty(vo.getNuMeta())){
					this.validar(vo, Operacao.INCLUIR);
				}
				else {
					this.validar(vo, Operacao.ALTERAR);
				}
			}
		}
		// Inclui ou altera os registros
		for (MetaVO vo : listaMeta) {
			if(vo != null){
				if(!Util.notEmpty(vo.getNuMeta())){
					this.incluir(vo);
				}
				else {
					this.alterar(vo);
				}
			}
		}
	}
}