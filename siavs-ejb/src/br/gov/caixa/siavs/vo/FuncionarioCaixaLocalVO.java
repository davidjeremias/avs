package br.gov.caixa.siavs.vo;

import java.util.List;

/**
 * <b>Title:</b> FuncionarioCaixaLocalVO <br>
 * <b>Description:</b> Armazena informações do Funcionário Caixa e da Unidade ao qual o funcionário está vinculado na época do registro. Essas informações devem ser mantidas independentes para o SIAVS. Se o funcionário mudar de unidade, um novo registro será criado. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 07/06/2013$
 */
public class FuncionarioCaixaLocalVO extends AbstractVOSIAVS {

	private static final long serialVersionUID = 1L;

// ****************************************************************
	/**
	 * Identificador do funcionário Caixa no SIAVS
	 */
	private Long nuFncroCaixaLocal;
	/**
	 * Armazena uma cópia da Unidade que o usuário estava vinculado quando foi cadastrado no SIAVS.
	 */
	private UnidadeVO unidade;
	/**
	 * Relacionamento com funcionário caixa.
	 */
	private FuncionarioCaixaVO funcionarioCaixa;
	/**
	 * Relacionamento com segmento.
	 */
	private List<FncroCaixaSegmentoVO> listaSegmento;
// ****************************************************************
	/**
	 * Construtor padrão.
	 */
	public FuncionarioCaixaLocalVO(){}

	/**
	 * Construtor com chave primária.
	 * @param nuFncroCaixaLocal Long
	 */
	public FuncionarioCaixaLocalVO(Long nuFncroCaixaLocal){
		this.setNuFncroCaixaLocal(nuFncroCaixaLocal);
	}

	/**
	 * Construtor com chave única.
	 * @param unidade UnidadeVO
	 * @param funcionarioCaixa FuncionarioCaixaVO
	 */
	public FuncionarioCaixaLocalVO(UnidadeVO unidade, FuncionarioCaixaVO funcionarioCaixa){
		this.setUnidade(unidade);
		this.setFuncionarioCaixa(funcionarioCaixa);
	}
// ****************************************************************

	/**
	 * @return Long
	 */
	public Long getNuFncroCaixaLocal() {
		return nuFncroCaixaLocal;
	}

	/**
	 * @param nuFncroCaixaLocal Long
	 */
	public void setNuFncroCaixaLocal(Long nuFncroCaixaLocal) {
		this.nuFncroCaixaLocal = nuFncroCaixaLocal;
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

	/**
	 * @return FuncionarioCaixaVO
	 */
	public FuncionarioCaixaVO getFuncionarioCaixa() {
		return funcionarioCaixa;
	}

	/**
	 * @param funcionarioCaixa FuncionarioCaixaVO
	 */
	public void setFuncionarioCaixa(FuncionarioCaixaVO funcionarioCaixa) {
		this.funcionarioCaixa = funcionarioCaixa;
	}

	/**
	 * @return List<FncroCaixaSegmentoVO>
	 */
	public List<FncroCaixaSegmentoVO> getListaSegmento() {
		return listaSegmento;
	}

	/**
	 * @param listaSegmento List<FncroCaixaSegmentoVO>
	 */
	public void setListaSegmento(List<FncroCaixaSegmentoVO> listaSegmento) {
		this.listaSegmento = listaSegmento;
	}

// ****************************************************************

	/**
	 * @see br.com.spread.framework.vo.AbstractVO#getPk
	 */
	@Override
	public Object getPk() {
		return this.getNuFncroCaixaLocal();
	}

// ****************************************************************

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcionarioCaixa == null) ? 0 : funcionarioCaixa.hashCode());
		result = prime * result + ((unidade == null) ? 0 : unidade.hashCode());
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
		FuncionarioCaixaLocalVO other = (FuncionarioCaixaLocalVO) obj;
		if (funcionarioCaixa == null) {
			if (other.funcionarioCaixa != null)
				return false;
		}
		else if (!funcionarioCaixa.equals(other.funcionarioCaixa))
			return false;
		if (unidade == null) {
			if (other.unidade != null)
				return false;
		}
		else if (!unidade.equals(other.unidade))
			return false;
		return true;
	}
}