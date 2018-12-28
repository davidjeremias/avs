package br.gov.caixa.siavs.vo;

import java.security.Principal;

import br.gov.caixa.siavs.global.DominioSIAVS.NivelAcesso;

/**
 * <b>Title:</b> UsuarioVO <br>
 * <b>Description:</b> Permite ao Administrador manter as informações cadastrais relativas ao acesso ao sistema. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 08/07/2013$
 */
public class UsuarioVO extends AbstractVOSIAVS implements Principal {

	private static final long serialVersionUID = 1L;

// ****************************************************************
	/**
	 * Identificador do Publicador
	 */
	private Long nuUsuario;
	/**
	 * Nível de acesso do usuário. Valores possíveis: 1 - Administrador; 2 - Publicador TI; 3 - Publicador;
	 */
	private String icAcesso;
	/**
	 * Relacionamento com funcionário caixa local.
	 */
	private FuncionarioCaixaLocalVO funcionarioCaixaLocal;
// ****************************************************************
	/**
	 * Construtor padrão.
	 */
	public UsuarioVO(){}

	/**
	 * Construtor com chave primária.
	 * @param nuUsuario Long
	 */
	public UsuarioVO(Long nuUsuario){
		this.setNuUsuario(nuUsuario);
	}
// ****************************************************************
	
	/**
	 * Sobrescreve o nome que representará o usuário para o servidor de aplicações.
	 * 
	 * @see java.security.Principal#getName()
	 */
	@Override
	public String getName() {
		if(this.getFuncionarioCaixaLocal() != null && this.getFuncionarioCaixaLocal().getFuncionarioCaixa() != null) {
			return this.getFuncionarioCaixaLocal().getFuncionarioCaixa().getDeMatricula();
		}
		
		return null;
	}
	
// ****************************************************************

	/**
	 * @return Long
	 */
	public Long getNuUsuario() {
		return nuUsuario;
	}

	/**
	 * @param nuUsuario Long
	 */
	public void setNuUsuario(Long nuUsuario) {
		this.nuUsuario = nuUsuario;
	}

	/**
	 * @return String
	 */
	public String getIcAcesso() {
		return icAcesso;
	}

	/**
	 * @param icAcesso String
	 */
	public void setIcAcesso(String icAcesso) {
		this.icAcesso = icAcesso;
	}

	/**
	 * @return FuncionarioCaixaLocalVO
	 */
	public FuncionarioCaixaLocalVO getFuncionarioCaixaLocal() {
		return funcionarioCaixaLocal;
	}

	/**
	 * @param funcionarioCaixaLocal FuncionarioCaixaLocalVO
	 */
	public void setFuncionarioCaixaLocal(FuncionarioCaixaLocalVO funcionarioCaixaLocal) {
		this.funcionarioCaixaLocal = funcionarioCaixaLocal;
	}

// ****************************************************************

	/**
	 * @see br.com.spread.framework.vo.AbstractVO#getPk
	 */
	@Override
	public Object getPk() {
		return this.getNuUsuario();
	}
	
// ****************************************************************
	
	/**
	 * Retorna o nível de acesso do usuário.
	 * @return NivelAcesso
	 */
	public NivelAcesso getNivelAcesso() {
		return NivelAcesso.getValor(icAcesso);
	}
}