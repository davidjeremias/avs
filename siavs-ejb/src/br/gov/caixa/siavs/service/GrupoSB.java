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
import br.gov.caixa.siavs.dao.GrupoDAO;
import br.gov.caixa.siavs.global.DominioSIAVS.FrequenciaAvaliacao;
import br.gov.caixa.siavs.global.Mensagem.Msg;
import br.gov.caixa.siavs.service.client.AvaliacaoSL;
import br.gov.caixa.siavs.service.client.EmailConvocacaoSL;
import br.gov.caixa.siavs.service.client.GrupoSL;
import br.gov.caixa.siavs.service.client.GrupoSR;
import br.gov.caixa.siavs.service.client.GrupoServicoSL;
import br.gov.caixa.siavs.service.client.GrupoUnidadeSL;
import br.gov.caixa.siavs.service.client.MetaSL;
import br.gov.caixa.siavs.vo.AvaliacaoVO;
import br.gov.caixa.siavs.vo.EmailConvocacaoVO;
import br.gov.caixa.siavs.vo.GrupoServicoVO;
import br.gov.caixa.siavs.vo.GrupoUnidadeVO;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.MetaVO;

/**
 * <b>Title:</b> GrupoSB <br>
 * <b>Description:</b> Serviço relacionado a Grupo. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: $$, $Date: $$
 */
@Stateless
@Remote(GrupoSR.class)
@Local(GrupoSL.class)
public class GrupoSB extends AbstractServiceSIAVS <GrupoVO> implements GrupoSR, GrupoSL {
	
	@Inject
	private AvaliacaoSL avaliacaoSL;
	@Inject
	private MetaSL metaSL;
	@Inject
	private GrupoServicoSL grupoServicoSL;
	@Inject
	private EmailConvocacaoSL emailConvocacaoSL;
	@Inject
	private GrupoUnidadeSL grupoUnidadeSL;

	@Inject
	private void setDao(GrupoDAO dao) {
		super.setDao(dao);
	}
	
	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#validar
	 */
	@Override
	public void validar(GrupoVO vo, Operacao operacao) throws SystemException, BusinessException {
		if(! operacao.equals(Operacao.LISTAR)) {
			if(vo == null){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("grupo"), "grupo");
			}
		}
		if(operacao.equals(Operacao.ALTERAR) || operacao.equals(Operacao.EXCLUIR) || operacao.equals(Operacao.CONSULTAR)) {
			if(! Util.notEmpty(vo.getNuGrupo())){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("identificador do grupo"), "nuGrupo");
			}
		}
		if(operacao.equals(Operacao.EXCLUIR)) {
			AvaliacaoVO avaliacaoVO = new AvaliacaoVO();
			avaliacaoVO.setGrupo(vo);
			List<AvaliacaoVO> listaAvaliacao = avaliacaoSL.listar(avaliacaoVO);
			// Se encontrar algum registro
			if(Util.notEmpty(listaAvaliacao)){
				throw new BusinessException(Msg.MN_EXCLUSAO_PROIBIDA.montar("grupo", "pois já existe avaliação"), "nuGrupo");
			}
		}
		if(operacao.equals(Operacao.INCLUIR) || operacao.equals(Operacao.ALTERAR)) {
			if(! Util.notEmpty(vo.getNoGrupo())){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("nome do grupo"), "noGrupo");
			}
			// Chave única
			GrupoVO voTemp = this.getDao().consultar(new GrupoVO(vo.getNoGrupo()));
			// Se encontrar algum registro no banco com o mesmo nome do grupo
			if(voTemp != null && !voTemp.getNuGrupo().equals(vo.getNuGrupo())){
				throw new BusinessException(vo.getNoGrupo() + " já cadastrado.");
			}
			
			if(vo.getIcEnviaEmailConvocacao() && FrequenciaAvaliacao.getValor(vo.getIcFrequenciaAvaliacao()) == null){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("frequência de avaliação"), "icFrequenciaAvaliacao");
			}
			else if(! vo.getIcEnviaEmailConvocacao() && FrequenciaAvaliacao.getValor(vo.getIcFrequenciaAvaliacao()) != null){
				throw new BusinessException(Msg.MN_CAMPO_PROIBIDO.montar("frequência de avaliação"), "icFrequenciaAvaliacao");
			}
		}

		if(operacao.equals(Operacao.INCLUIR) || operacao.equals(Operacao.ALTERAR) || operacao.equals(Operacao.EXCLUIR)) {
			
			if(operacao.equals(Operacao.INCLUIR)) {
				if(vo.getIcAgencia()) {
					throw new BusinessException(Msg.MN_INCLUSAO_PROIBIDA.montar("grupo", "pois não pode ser do tipo agência"), "icAgencia");
				}
			}
			else {
				GrupoVO voTemp = this.getDao().consultar(new GrupoVO(vo.getNuGrupo()));
				
				if(voTemp.getIcAgencia()) {
					if(operacao.equals(Operacao.ALTERAR)) {
						// Proibido alterar estes campos 
						if(!voTemp.getNoGrupo().equals(vo.getNoGrupo()) ||
							!voTemp.getIcEnviaEmailConvocacao().equals(vo.getIcEnviaEmailConvocacao()) ||
							!voTemp.getIcFrequenciaAvaliacao().equals(vo.getIcFrequenciaAvaliacao())) {
							throw new BusinessException(Msg.MN_ALTERACAO_PROIBIDA.montar("grupo", "pois não pode alterar o nome, email de convocação e frequência de avaliação"), "icAgencia");
						}
					}
					else if(operacao.equals(Operacao.EXCLUIR)) {
						throw new BusinessException(Msg.MN_EXCLUSAO_PROIBIDA.montar("grupo", "quando for do tipo agência"), "nuGrupo");
					}
				}
			}
		}
	}
	
	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#preOperacao(br.com.spread.framework.vo.AbstractVO, br.com.spread.framework.global.Dominio.Operacao)
	 */
	@Override
	public GrupoVO preOperacao(GrupoVO vo, Operacao operacao) throws SystemException, BusinessException {
		if(Operacao.EXCLUIR.equals(operacao)){
			// Exclui todos os grupos unidade vinculados
			grupoUnidadeSL.excluir(new GrupoUnidadeVO(vo, null));
			// Exclui todas as metas vinculadas
			metaSL.excluir(new MetaVO(vo, null));
			// Exclui todos os grupos vinculados
			grupoServicoSL.excluir(new GrupoServicoVO(vo, null));
			// Exclui o email de convocação
			emailConvocacaoSL.excluir(new EmailConvocacaoVO(vo));
		}
		
		return vo;
	}
}