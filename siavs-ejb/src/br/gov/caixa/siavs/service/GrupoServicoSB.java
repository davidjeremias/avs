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
import br.gov.caixa.siavs.dao.GrupoServicoDAO;
import br.gov.caixa.siavs.global.Mensagem.Msg;
import br.gov.caixa.siavs.service.client.GrupoServicoSL;
import br.gov.caixa.siavs.service.client.GrupoServicoSR;
import br.gov.caixa.siavs.vo.FuncionarioCaixaLocalVO;
import br.gov.caixa.siavs.vo.GrupoServicoVO;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.ServicoVO;

/**
 * <b>Title:</b> GrupoServicoSB <br>
 * <b>Description:</b> Serviço relacionado a grupoServico. <br>
 * <b>Company:</b> Spread
 * @author ${classe.autor}
 * @version: $Revision: ${classe.versao} $, $Date: 14/06/2013$
 */
@Stateless
@Remote(GrupoServicoSR.class)
@Local(GrupoServicoSL.class)
public class GrupoServicoSB extends AbstractServiceSIAVS<GrupoServicoVO> implements GrupoServicoSR, GrupoServicoSL {

	@Inject
	private void setDao(GrupoServicoDAO dao) {
		super.setDao(dao);
	}

	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#validar
	 */
	@Override
	public void validar(GrupoServicoVO vo, Operacao operacao) throws SystemException, BusinessException {
		if(! operacao.equals(Operacao.LISTAR)) {
			if(vo == null){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("grupo serviço"), "grupoServico");
			}
		}
		if(operacao.equals(Operacao.EXCLUIR) || operacao.equals(Operacao.INCLUIR)) {
			if(vo.getGrupo() == null || ! Util.notEmpty(vo.getGrupo().getNuGrupo())){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("grupo"), "nuGrupo");
			}
		}
		if(operacao.equals(Operacao.INCLUIR)) {
			if(vo.getServico() == null || !Util.notEmpty(vo.getServico().getNuServico())){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("serviço"), "nuServico");
			}
		}
	}
	
	/**
	 * @see br.gov.caixa.siavs.service.client.GrupoServicoSR#salvar(br.gov.caixa.siavs.vo.GrupoVO, java.util.List)
	 */
	@Override
	public void salvar(GrupoVO grupoVO, List<GrupoServicoVO> listaGrupoServico) throws SystemException, BusinessException {
		if(Util.notEmpty(listaGrupoServico)){
			// Valida todos os registros
			for (GrupoServicoVO vo : listaGrupoServico) {
				this.validar(vo, Operacao.INCLUIR);
			}
		}
		// Exclui todos os registros vinculados ao grupo
		this.excluir(new GrupoServicoVO(grupoVO, null));
		
		if(Util.notEmpty(listaGrupoServico)){
			// Inclui os novos registros
			this.incluir(listaGrupoServico);
		}
	}

	/**
	 * @see br.gov.caixa.siavs.service.client.GrupoServicoSR#listar(br.gov.caixa.siavs.vo.FuncionarioCaixaLocalVO)
	 */
	@Override
	public List<ServicoVO> listar(FuncionarioCaixaLocalVO funcionarioCaixaLocalVO) throws SystemException, BusinessException {
		return ((GrupoServicoDAO) this.getDao()).listar(funcionarioCaixaLocalVO);
	}
}