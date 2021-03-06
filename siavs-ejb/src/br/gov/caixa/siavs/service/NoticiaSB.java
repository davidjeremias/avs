package br.gov.caixa.siavs.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.global.Dominio.Operacao;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.dao.NoticiaDAO;
import br.gov.caixa.siavs.global.Mensagem.Msg;
import br.gov.caixa.siavs.service.client.NoticiaSL;
import br.gov.caixa.siavs.service.client.NoticiaSR;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.NoticiaVO;

/**
 * <b>Title:</b> NoticiaSB <br>
 * <b>Description:</b> Serviço relacionado a notícia. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 17/06/2013$
 */
@Stateless
@Remote(NoticiaSR.class)
@Local(NoticiaSL.class)
public class NoticiaSB extends AbstractServiceSIAVS<NoticiaVO> implements NoticiaSR, NoticiaSL {

	@Inject
	private void setDao(NoticiaDAO dao) {
		super.setDao(dao);
	}

	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#validar
	 */
	public void validar(NoticiaVO vo, Operacao operacao) throws SystemException, BusinessException {
		 
		if(! operacao.equals(Operacao.LISTAR)) {
			if(vo == null){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("notícia"), "noticia");
			}
		}
		if(operacao.equals(Operacao.ALTERAR) || operacao.equals(Operacao.EXCLUIR) || operacao.equals(Operacao.CONSULTAR)) {
			if(! Util.notEmpty(vo.getNuNoticia())){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("identificador do noticia"), "nuNoticia");
			}
		}
		if(operacao.equals(Operacao.INCLUIR) || operacao.equals(Operacao.ALTERAR)) {
			if(vo.getServico() == null || !Util.notEmpty(vo.getServico().getNuServico())){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("serviço"), "servico");
			}
			if(vo.getTsInicioPublicacao() == null){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("data inicial da publicação"), "tsInicioPublicacao");
			}
			if(vo.getTsFimPublicacao() == null){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("data final da publicação"), "tsFimPublicacao");
			}
			//a data não pode ser antes
			if(vo.getTsInicioPublicacao().before(vo.getDataAtual())){
				//mas pode ser igual
				if(!vo.getTsInicioPublicacao().equals(vo.getDataAtual())){
					throw new BusinessException("A data inicial deve ser maior ou igual a data atual.", "tsFimPublicacao");
				}				
			}
			if(vo.getTsFimPublicacao().before(vo.getTsInicioPublicacao())){
				throw new BusinessException("A data final deve ser maior ou igual a data inicial.", "tsFimPublicacao");
			}
			String noHTMLString = StringEscapeUtils.unescapeHtml(vo.getDeNoticia().replaceAll("\\<.*?\\>", "").trim());
			if(noHTMLString.length() < 5) {
				throw new BusinessException(Msg.MN030.montar("notícia"), "deNoticia");
			} 			
		}
	}

	/**
	 * @see br.gov.caixa.siavs.service.client.NoticiaSR#listar(java.lang.Long[])
	 */
	@Override
	public List<NoticiaVO> listar(Long[] arrayNuServico) throws SystemException {
		return ((NoticiaDAO) this.getDao()).listar(arrayNuServico);
	}

	/**
	 * @see br.gov.caixa.siavs.service.client.NoticiaSR#listarPorGrupo(br.gov.caixa.siavs.vo.GrupoVO)
	 */
	@Override
	public List<NoticiaVO> listarPorGrupo(GrupoVO grupoVO) throws SystemException, BusinessException {
		return ((NoticiaDAO) this.getDao()).listarPorGrupo(grupoVO);
	}
}