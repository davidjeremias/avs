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
import br.gov.caixa.siavs.dao.ServicoDAO;
import br.gov.caixa.siavs.global.DominioSIAVS;
import br.gov.caixa.siavs.global.Mensagem.Msg;
import br.gov.caixa.siavs.service.client.AvaliacaoSL;
import br.gov.caixa.siavs.service.client.GrupoServicoSL;
import br.gov.caixa.siavs.service.client.NoticiaSL;
import br.gov.caixa.siavs.service.client.ServicoSL;
import br.gov.caixa.siavs.service.client.ServicoSR;
import br.gov.caixa.siavs.service.client.ServicoSegmentoSL;
import br.gov.caixa.siavs.vo.AvaliacaoVO;
import br.gov.caixa.siavs.vo.GrupoServicoVO;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.NoticiaVO;
import br.gov.caixa.siavs.vo.ServicoSegmentoVO;
import br.gov.caixa.siavs.vo.ServicoVO;

/**
 * <b>Title:</b> ServicoSB <br>
 * <b>Description:</b> Serviço relacionado a serviço. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 07/06/2013$
 */
@Stateless
@Remote(ServicoSR.class)
@Local(ServicoSL.class)
public class ServicoSB extends AbstractServiceSIAVS <ServicoVO> implements ServicoSR, ServicoSL {

	@Inject
	private AvaliacaoSL avaliacaoSL;
	@Inject
	private ServicoSegmentoSL servicoSegmentoSL;
	@Inject
	private GrupoServicoSL grupoServicoSL;
	@Inject
	private NoticiaSL noticiaSL;
	
	@Inject
	private void setDao(ServicoDAO dao) {
		super.setDao(dao);
	}

	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#validar
	 */
	@Override
	public void validar(ServicoVO vo, Operacao operacao) throws SystemException, BusinessException {
		if(! operacao.equals(Operacao.LISTAR)) {
			if(vo == null){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("serviço"), "serviço");
			}
		}
		if(operacao.equals(Operacao.ALTERAR) || operacao.equals(Operacao.EXCLUIR) || operacao.equals(Operacao.CONSULTAR)) {
			if(! Util.notEmpty(vo.getNuServico())){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("identificador do serviço"), "nuServico");
			}
		}
		if(operacao.equals(Operacao.EXCLUIR)) {
			AvaliacaoVO avaliacaoVO = new AvaliacaoVO();
			avaliacaoVO.setServico(vo);
			List<AvaliacaoVO> listaAvaliacao = avaliacaoSL.listar(avaliacaoVO);
			// Se encontrar algum registro
			if(Util.notEmpty(listaAvaliacao)){
				throw new BusinessException(Msg.MN_EXCLUSAO_PROIBIDA.montar("serviço", "pois já existe avaliação"), "serviço");
			}
		}
		if(operacao.equals(Operacao.INCLUIR) || operacao.equals(Operacao.ALTERAR)) {
			if(! Util.notEmpty(vo.getDeServico())){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("descrição do serviço"), "deServico");
			}

			ServicoVO voTemp = this.getDao().consultar(new ServicoVO(vo.getDeServico()));
			// Se encontrar algum registro "E" Se não for o registro atual
			if(voTemp != null && !voTemp.getNuServico().equals(vo.getNuServico())){
				throw new BusinessException(Msg.MN_INCLUSAO_PROIBIDA.montar("serviço", "pois já existe um serviço cadastrado com esse nome"), "deServico");
			}
			
			if(! Util.notEmpty(vo.getNoSistema())){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("nome do sistema"), "noSistema");
			}
			if(vo.getIcCorporativo()){
				if(vo.getUnidadeRelacionamento() == null){
					throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("relacionamento"), "Relacionamento");
				}
				if(vo.getUnidadeProducao() == null){
					throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("produção"), "Producao");
				}
			}
			else {
				if(! Util.notEmpty(vo.getNoGestor())){
					throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("nome do gestor"), "noGestor");
				}
			}
			
			if(vo.getIcAgencia() && !Util.notEmpty(vo.getListaSegmento())){
				throw new BusinessException("Selecione pelo menos 1 (um) segmento para registrar o serviço.", "segmento");
			}
		}		
	}
	
	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#preOperacao
	 */
	@Override
	public ServicoVO preOperacao(ServicoVO vo, Operacao operacao) throws SystemException, BusinessException {
		if(Operacao.EXCLUIR.equals(operacao)){
			
			// Exclui as noticias vinculadas
			List<NoticiaVO> noticiasVinculadas = ((ServicoDAO) this.getDao()).listarNoticiasPorServico(vo);
			for (NoticiaVO noticiaVO : noticiasVinculadas) {
				noticiaSL.excluir(noticiaVO);	
			}
			
			// Exclui os segmentos vinculados
			List<ServicoSegmentoVO> servicosSegmentos = ((ServicoDAO) this.getDao()).listarSegmentosPorServico(vo);
			for (ServicoSegmentoVO servicoSegmentoVO : servicosSegmentos) {
				servicoSegmentoSL.excluir(servicoSegmentoVO);	
			}
			
			// Exclui o vínculo aos grupos
			List<GrupoServicoVO> gruposSegmentos = ((ServicoDAO) this.getDao()).listarGruposServicoPorServico(vo);
			for (GrupoServicoVO grupoServicoVO : gruposSegmentos) {
				grupoServicoSL.excluir(grupoServicoVO);	
			}
		}
		
		return vo;
	}
	
	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#posOperacao
	 */
	@Override
	public ServicoVO posOperacao(ServicoVO vo, Operacao operacao) throws SystemException, BusinessException {
		GrupoServicoVO grupoServicoVO = new GrupoServicoVO(new GrupoVO(DominioSIAVS.ID_GRUPO_AGENCIA), vo);
		
		if(Operacao.ALTERAR.equals(operacao)){
			// Exclui os segmentos vinculados
			servicoSegmentoSL.excluir(new ServicoSegmentoVO(vo, null));
			// Exclui o vínculo ao grupo agência, caso exista
			grupoServicoSL.excluir(grupoServicoVO);			
		}
		
		if(Operacao.INCLUIR.equals(operacao) || Operacao.ALTERAR.equals(operacao)){
			if(Util.notEmpty(vo.getListaSegmento())){
				// Insere os segmentos vinculados
				servicoSegmentoSL.incluir(vo.getListaSegmento());
			}
			// Se for um serviço vinculado ao grupo agência
			if(vo.getIcAgencia()){
				// Inclui o vínculo ao grupo agência, caso exista
				grupoServicoSL.incluir(grupoServicoVO);
			}
		}
		else if(Operacao.CONSULTAR.equals(operacao)){
			vo.setListaSegmento(servicoSegmentoSL.listar(new ServicoSegmentoVO(vo, null)));
		}
		
		return vo;
	}

	/**
	 * @see br.gov.caixa.siavs.service.client.ServicoSR#listarServicosAvaliados(br.gov.caixa.siavs.vo.GrupoVO)
	 */
	@Override
	public List<ServicoVO> listarServicosAvaliados(GrupoVO grupoVO) throws SystemException, BusinessException {
		return ((ServicoDAO) this.getDao()).listarServicosAvaliados(grupoVO);
	}
}