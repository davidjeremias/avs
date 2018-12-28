package br.gov.caixa.siavs.security;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.factory.FactorySIAVS;
import br.gov.caixa.siavs.service.client.FuncionarioCaixaLocalSR;
import br.gov.caixa.siavs.service.client.FuncionarioCaixaSR;
import br.gov.caixa.siavs.service.client.UsuarioSR;
import br.gov.caixa.siavs.vo.FuncionarioCaixaLocalVO;
import br.gov.caixa.siavs.vo.FuncionarioCaixaVO;
import br.gov.caixa.siavs.vo.UnidadeVO;
import br.gov.caixa.siavs.vo.UsuarioVO;

/**
 * <b>Title:</b> LoginModuleSIAVS <br>
 * <b>Description:</b> Módulo de login do SIAVS. <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: $, $Date: $
 */
public class LoginModuleSIAVS extends AbstractLoginModule {

	// ***********************************************************************************************************************************
	/**
	 * @see br.com.framework.security.AbstractLoginModule#carregarUsuario(java.lang.String)
	 */
	@Override
	protected Principal carregarUsuario(String matricula) throws Exception {
		String msg = "Ocorreu um erro durante o carregamento das informações do usuário.";
		UsuarioVO usuarioVO = new UsuarioVO();

		try {
			// Carrega as informações do usuário direto na view utilizando
			// apenas a matrícula
			FuncionarioCaixaVO funcionarioCaixaVO = null;
			matricula = Util.desformatarMatriculaUsuarioCaixa(matricula);

			// Se a matrícula não for vazia
			if (Util.notEmpty(matricula)) {
				funcionarioCaixaVO = FactorySIAVS.getService(FuncionarioCaixaSR.class)
						.consultar(new FuncionarioCaixaVO(matricula));
			}

			// Se não encontrar o funcionário
			if (funcionarioCaixaVO == null) {
				return null;
			}

			// cadastra o usuario local se não existir
			FactorySIAVS.getService(FuncionarioCaixaLocalSR.class).criarNovo(funcionarioCaixaVO);

			// Verifica se já existe um usuário cadastrado para essa matrícula e
			// número da unidade
			usuarioVO.setFuncionarioCaixaLocal(new FuncionarioCaixaLocalVO(
					new UnidadeVO(new Integer(funcionarioCaixaVO.getUnidade().getNuUnidade()), null),
					new FuncionarioCaixaVO(funcionarioCaixaVO.getNuMatricula())));
			usuarioVO = FactorySIAVS.getService(UsuarioSR.class).consultar(usuarioVO);

			// Se não encontrar o usuário deve ser utilizado o funcionário da
			// view
			if (usuarioVO == null) {
				usuarioVO = new UsuarioVO();
				usuarioVO.setFuncionarioCaixaLocal(
						new FuncionarioCaixaLocalVO(funcionarioCaixaVO.getUnidade(), funcionarioCaixaVO));
			}
		} catch (BusinessException e) {
			throw new BusinessException(msg, e);
		} catch (SystemException e) {
			throw new BusinessException(msg, e);
		}

		return usuarioVO;
	}

	/**
	 * @see br.gov.caixa.siavs.security.AbstractLoginModule#getListaRoles()
	 */
	@Override
	protected List<Principal> getListaRoles() {
		LinkedList<Principal> lista = new LinkedList<Principal>();

		// Adiciona a Role padrão para o sistema. Deve ser a mesma utilizada no
		// arquivo web.xml
		lista.add(new Principal() {
			@Override
			public String getName() {
				return "siavs";
			}
		});

		return lista;
	}
}