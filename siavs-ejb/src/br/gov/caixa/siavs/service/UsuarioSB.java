package br.gov.caixa.siavs.service;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.global.Dominio.Operacao;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.dao.UsuarioDAO;
import br.gov.caixa.siavs.global.DominioSIAVS.NivelAcesso;
import br.gov.caixa.siavs.global.Mensagem.Msg;
import br.gov.caixa.siavs.service.client.FuncionarioCaixaLocalSL;
import br.gov.caixa.siavs.service.client.UnidadeSL;
import br.gov.caixa.siavs.service.client.UsuarioSL;
import br.gov.caixa.siavs.service.client.UsuarioSR;
import br.gov.caixa.siavs.vo.UnidadeVO;
import br.gov.caixa.siavs.vo.UsuarioVO;

/**
 * <b>Title:</b> UsuarioSB <br>
 * <b>Description:</b> Serviço relacionado a usuário. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 08/07/2013$
 */
@Stateless
@Remote(UsuarioSR.class)
@Local(UsuarioSL.class)
public class UsuarioSB extends AbstractServiceSIAVS<UsuarioVO> implements UsuarioSR, UsuarioSL {

	@Inject
	private UnidadeSL unidadeSL;
	@Inject
	private FuncionarioCaixaLocalSL funcionarioCaixaLocalSL;
	
	@Inject
	private void setDao(UsuarioDAO dao) {
		super.setDao(dao);
	}

	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#validar
	 */
	public void validar(UsuarioVO vo, Operacao operacao) throws SystemException, BusinessException {
		if(! operacao.equals(Operacao.LISTAR)) {
			if(vo == null){
				throw new BusinessException(Msg.MN002.toString(), "usuario");
			}
		}
		if(operacao.equals(Operacao.ALTERAR) || operacao.equals(Operacao.EXCLUIR)) {
			if(! Util.notEmpty(vo.getNuUsuario())){
				throw new BusinessException(Msg.MN002.toString(), "nuUsuario");
			}
		}
		if(operacao.equals(Operacao.INCLUIR) || operacao.equals(Operacao.ALTERAR)) {
			if(vo.getFuncionarioCaixaLocal() == null || ! Util.notEmpty(vo.getFuncionarioCaixaLocal().getNuFncroCaixaLocal())){
				if( vo.getFuncionarioCaixaLocal() == null ||
					vo.getFuncionarioCaixaLocal().getFuncionarioCaixa() == null || ! Util.notEmpty(vo.getFuncionarioCaixaLocal().getFuncionarioCaixa().getNuMatricula()) ||
					vo.getFuncionarioCaixaLocal().getUnidade() == null || vo.getFuncionarioCaixaLocal().getUnidade().getPk() == null){
					
					throw new BusinessException(Msg.MN002.toString(), "nuMatricula");
				}
			}

			UsuarioVO usuarioVOTemp = this.consultar(vo);
			if(operacao.equals(Operacao.INCLUIR) && usuarioVOTemp != null){
				throw new BusinessException(Msg.MN028.toString(), "nuMatricula");
			}
			if(NivelAcesso.getValor(vo.getIcAcesso()) == null){
				throw new BusinessException(Msg.MN002.toString(), "icAcesso");
			}
			if(NivelAcesso.AMINISTRADOR.getCodigo().equals(vo.getIcAcesso())){
				// Carrega os dados da unidade
				UnidadeVO unidadeVO = unidadeSL.consultar(vo.getFuncionarioCaixaLocal().getUnidade());
				// RN029
				if(unidadeVO == null || !unidadeVO.getSgUnidade().equals("GEDTI")){
					throw new BusinessException("Somente os empregados lotados na GEDTI poderão ter nível de acesso Administrador.", "icAcesso");
				}
			}
		}
	}
	
	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#preOperacao
	 */
	@Override
	public UsuarioVO preOperacao(UsuarioVO vo, Operacao operacao) throws SystemException, BusinessException {
		if(Operacao.INCLUIR.equals(operacao) || Operacao.ALTERAR.equals(operacao)){
			funcionarioCaixaLocalSL.salvar(vo.getFuncionarioCaixaLocal());
		}
		
		return vo;
	}	
}