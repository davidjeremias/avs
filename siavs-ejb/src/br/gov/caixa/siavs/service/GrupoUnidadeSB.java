package br.gov.caixa.siavs.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.global.Dominio.Operacao;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.dao.GrupoUnidadeDAO;
import br.gov.caixa.siavs.global.Mensagem.Msg;
import br.gov.caixa.siavs.service.client.GrupoUnidadeSL;
import br.gov.caixa.siavs.service.client.GrupoUnidadeSR;
import br.gov.caixa.siavs.service.client.UnidadeSL;
import br.gov.caixa.siavs.to.UnidadeVinculadoraTO;
import br.gov.caixa.siavs.vo.GrupoUnidadeVO;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.UnidadeVO;

/**
 * <b>Title:</b> GrupoUnidadeSB <br>
 * <b>Description:</b> Serviço relacionado a grupoUnidade. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 14/06/2013$
 */
@Stateless
@Remote(GrupoUnidadeSR.class)
@Local(GrupoUnidadeSL.class)
public class GrupoUnidadeSB extends AbstractServiceSIAVS<GrupoUnidadeVO> implements GrupoUnidadeSR, GrupoUnidadeSL {

	@Inject
	private UnidadeSL unidadeSL;

	@Inject
	private void setDao(GrupoUnidadeDAO dao) {
		super.setDao(dao);
	}

	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#validar(br.com.spread.framework.vo.AbstractVO, br.com.spread.framework.global.Dominio.Operacao)
	 */
	@Override
	public void validar(GrupoUnidadeVO vo, Operacao operacao) throws SystemException, BusinessException {
		if(operacao.equals(Operacao.INCLUIR) || operacao.equals(Operacao.ALTERAR)) {
			// Valida campo obrigatorio
			if (vo.getUnidade() == null || !Util.notEmpty(vo.getUnidade().getNuUnidade())) {
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("unidade"), "txtUnidade");
			}
			// FE2. Unidade já vinculada
			if (this.consultar(vo) != null) {
				throw new BusinessException("Unidade vinculada a outro grupo.", "txtUnidade");
			}
		}
	}
	
	/**
	 * @see br.gov.caixa.siavs.service.client.GrupoUnidadeSR#listarUnidadeTreeNode(br.gov.caixa.siavs.vo.GrupoVO)
	 */
	@Override
	public List<UnidadeVinculadoraTO> listarUnidadeTreeNode(GrupoVO grupoVO) throws SystemException, BusinessException {
		List<UnidadeVinculadoraTO> unidadesRetorno = new ArrayList<UnidadeVinculadoraTO>();
		List<UnidadeVinculadoraTO> unidades = ((GrupoUnidadeDAO)getDao()).listarUnidadeVinculadora(grupoVO);
		//
		for (UnidadeVinculadoraTO unidade : unidades) {
			buscaFilhas(unidade, unidades);
			// adiciona a raiz da árvore
			if (unidade.isRoot()) {
				unidadesRetorno.add(unidade);
			}
		}
		return unidadesRetorno;
	}

	/**
	 * Recupera as unidades filhas
	 * @param unidade UnidadeVinculadoraTO
	 * @param unidades List<UnidadeVinculadoraTO>
	 */
	private void buscaFilhas(UnidadeVinculadoraTO unidade, List<UnidadeVinculadoraTO> unidades) {
		unidade.setUnidadesVinculadas(new ArrayList<UnidadeVinculadoraTO>());
		boolean isRoot = true;
		
		for (UnidadeVinculadoraTO to : unidades) {
			// é filha?
			if (unidade.getGrupoUnidadeVO().getUnidade().equals(to.getUnidadeVinculadora())) {
				unidade.getUnidadesVinculadas().add(to);
			}
			// é pai?
			if (unidade.getUnidadeVinculadora().equals(to.getGrupoUnidadeVO().getUnidade())) {
				isRoot = false;
			}
			// ordena unidades
			if (Util.notEmpty(unidade.getUnidadesVinculadas())) {
				Collections.sort(unidade.getUnidadesVinculadas(), new Comparator<UnidadeVinculadoraTO>() {
					@Override
					public int compare(UnidadeVinculadoraTO to1, UnidadeVinculadoraTO to2) {
						return to1.getGrupoUnidadeVO().getUnidade().getNuUnidade().compareTo(to2.getGrupoUnidadeVO().getUnidade().getNuUnidade());
					}
				});
			}
		}
		unidade.setRoot(isRoot);
	}
	
	/**
	 * @see br.gov.caixa.siavs.service.client.GrupoUnidadeSR#incluirVinculadas(br.gov.caixa.siavs.vo.GrupoUnidadeVO)
	 */
	@Override
	public List<UnidadeVO> incluirVinculadas(GrupoUnidadeVO vo) throws SystemException, BusinessException {
		this.validar(vo, Operacao.INCLUIR);
		// inclui o pai
		this.incluir(vo);
		
		// tenta incluir os filhos
		List<UnidadeVO> naoVinculadas = new ArrayList<UnidadeVO>();
		List<UnidadeVO> vinculacao = this.unidadeSL.listarUnidadeVinculada(vo.getUnidade());
		
		for (UnidadeVO unidadeVO : vinculacao) {
			GrupoUnidadeVO grupoUnidadeVO = new GrupoUnidadeVO(null, unidadeVO);
			
			if (this.consultar(grupoUnidadeVO) != null) {
				naoVinculadas.add(unidadeVO);
			}
			else {
				grupoUnidadeVO.setGrupo(vo.getGrupo());
				grupoUnidadeVO.setDtInclusao(new Date());

				this.incluir(grupoUnidadeVO);
			}
		}
		return naoVinculadas;
	}
	
	/**
	 * @see br.gov.caixa.siavs.service.client.GrupoUnidadeSR#excluirUnidadeTreeNode(br.gov.caixa.siavs.to.UnidadeVinculadoraTO)
	 */
	@Override
	public void excluirUnidadeTreeNode(UnidadeVinculadoraTO unidadeVinculadora) throws SystemException, BusinessException {
		for (UnidadeVinculadoraTO to : unidadeVinculadora.getUnidadesVinculadas()) {
			this.getDao().excluir(to.getGrupoUnidadeVO());
		}
		this.getDao().excluir(unidadeVinculadora.getGrupoUnidadeVO());
	}
}
