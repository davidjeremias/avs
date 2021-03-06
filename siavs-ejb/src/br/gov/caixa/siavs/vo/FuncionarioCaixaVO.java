package br.gov.caixa.siavs.vo;

import br.com.spread.framework.util.Util;

/**
 * <b>Title:</b> FuncionarioCaixaVO <br>
 * <b>Description:</b> Permite recuperar as informações do Funcionário Caixa. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 07/06/2013$
 */
public class FuncionarioCaixaVO extends AbstractVOSIAVS {

	private static final long serialVersionUID = 1L;

// ****************************************************************
	/**
	 * Matrícula do funcionário
	 */
	private String nuMatricula;
	/**
	 * Nome do funcionário
	 */
	private String noFuncionario;
	/**
	 * Tipo de função do funcionário.
	 */
	private Integer nuTipoFuncao;
	/**
	 * Relacionamento com unidade.
	 */
	private UnidadeVO unidade;
// ****************************************************************
	/**
	 * Construtor padrão.
	 */
	public FuncionarioCaixaVO(){}

	/**
	 * Construtor com chave primária.
	 * @param nuMatricula String
	 */
	public FuncionarioCaixaVO(String nuMatricula){
		this.setNuMatricula(nuMatricula);
	}
// ****************************************************************

	/**
	 * @return String
	 */
	public String getNuMatricula() {
		return nuMatricula;
	}

	/**
	 * @param nuMatricula String
	 */
	public void setNuMatricula(String nuMatricula) {
		this.nuMatricula = nuMatricula;
	}
	
	/**
	 * @return String
	 */
	public String getDeMatricula() {
		return Util.formatarMatriculaUsuarioCaixa(this.getNuMatricula());
		//'C'+
	}
	
	
	/**
	 * @param deMatricula String
	 */
	public void setDeMatricula(String deMatricula) {
		this.setNuMatricula(Util.desformatarMatriculaUsuarioCaixa(deMatricula));
	}
	
	/**
	 * @return String
	 */
	public String getNoFuncionario() {
		return noFuncionario;
	}

	/**
	 * @param noFuncionario String
	 */
	public void setNoFuncionario(String noFuncionario) {
		this.noFuncionario = noFuncionario;
	}

	/**
	 * @return Integer
	 */
	public Integer getNuTipoFuncao() {
		return nuTipoFuncao;
	}

	/**
	 * @param nuTipoFuncao Integer
	 */
	public void setNuTipoFuncao(Integer nuTipoFuncao) {
		this.nuTipoFuncao = nuTipoFuncao;
	}
	
	/**
	 * @return UnidadeVO
	 */
	public UnidadeVO getUnidade() {
		return unidade;
	}

	/**
	 * @param unidade UnidadeVO
	 */
	public void setUnidade(UnidadeVO unidade) {
		this.unidade = unidade;
	}

// ****************************************************************

	/**
	 * @see br.com.spread.framework.vo.AbstractVO#getPk
	 */
	@Override
	public Object getPk() {
		return this.getNuMatricula();
	}
	
// ****************************************************************

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nuMatricula == null) ? 0 : nuMatricula.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FuncionarioCaixaVO other = (FuncionarioCaixaVO) obj;
		if (nuMatricula == null) {
			if (other.nuMatricula != null)
				return false;
		}
		else if (!nuMatricula.equals(other.nuMatricula))
			return false;
		return true;
	}
}