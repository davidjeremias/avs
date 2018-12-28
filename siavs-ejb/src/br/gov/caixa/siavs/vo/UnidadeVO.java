package br.gov.caixa.siavs.vo;

import java.util.LinkedList;
import java.util.List;

import br.com.spread.framework.util.Numero;
import br.com.spread.framework.util.Util;

/**
 * <b>Title:</b> UnidadeVO <br>
 * <b>Description:</b> Permite recuperar as informações da Unidade. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 07/06/2013$
 */
public class UnidadeVO extends AbstractVOSIAVS {

	private static final long serialVersionUID = 1L;

// ****************************************************************
	/**
	 * Compõe o identificador da Unidade
	 */
	private Integer nuUnidade;
	/**
	 * Compõe o identificador da unidade.
	 */
	private Integer nuNatural;
	/**
	 * Nome da unidade
	 */
	private String noUnidade;
	/**
	 * UF da unidade
	 */
	private String sgUf;
	/**
	 * Número de telefone
	 */
	private String nuTelefone;
	/**
	 * CGC da unidade
	 */
	private String sgCgc;
	/**
	 * Email da unidade
	 */
	private String deEmail;
	/**
	 * Sigla da unidade
	 */
	private String sgUnidade;
	/**
	 * Número do tipo da unidade
	 */	
	private int nuTipoUnidade;
	/**
	 * Sigla do tipo de unidade.
	 */
	private String sgTipoUnidade;
	/**
	 * Sigla da localização.
	 */
	private String sgLocalizacao;
	/**
	 * Nome do Grupo
	 */
	private String nomeGrupo;
	
	
// ****************************************************************
	/**
	 * Construtor padrão.
	 */
	public UnidadeVO(){}

	/**
	 * Construtor com chave primária.
	 * @param nuUnidade Integer
	 * @param nuNatural Integer
	 */
	public UnidadeVO(Integer nuUnidade, Integer nuNatural){
		this.setNuUnidade(nuUnidade);
		this.setNuNatural(nuNatural);
	}
// ****************************************************************

	/**
	 * @return Integer
	 */
	public Integer getNuUnidade() {
		return nuUnidade;
	}

	/**
	 * @param nuUnidade Integer
	 */
	public void setNuUnidade(Integer nuUnidade) {
		this.nuUnidade = nuUnidade;
	}

	/**
	 * @return Integer
	 */
	public Integer getNuNatural() {
		return nuNatural;
	}

	/**
	 * @param nuNatural Integer
	 */
	public void setNuNatural(Integer nuNatural) {
		this.nuNatural = nuNatural;
	}

	/**
	 * @return String
	 */
	public String getNoUnidade() {
		return noUnidade;
	}

	/**
	 * @param noUnidade String
	 */
	public void setNoUnidade(String noUnidade) {
		this.noUnidade = noUnidade;
	}

	/**
	 * @return String
	 */
	public String getSgUf() {
		return sgUf;
	}

	/**
	 * @param sgUf String
	 */
	public void setSgUf(String sgUf) {
		this.sgUf = sgUf;
	}

	/**
	 * @return String
	 */
	public String getNuTelefone() {
		return nuTelefone;
	}

	/**
	 * @param nuTelefone String
	 */
	public void setNuTelefone(String nuTelefone) {
		this.nuTelefone = nuTelefone;
	}

	/**
	 * @return String
	 */
	public String getSgCgc() {
		return sgCgc;
	}

	/**
	 * @param sgCgc String
	 */
	public void setSgCgc(String sgCgc) {
		this.sgCgc = sgCgc;
	}

	/**
	 * @return String
	 */
	public String getDeEmail() {
		return deEmail;
	}

	/**
	 * @param deEmail String
	 */
	public void setDeEmail(String deEmail) {
		this.deEmail = deEmail;
	}

	/**
	 * @return String
	 */
	public String getSgUnidade() {
		return sgUnidade;
	}

	/**
	 * @param sgUnidade String
	 */
	public void setSgUnidade(String sgUnidade) {
		this.sgUnidade = sgUnidade;
	}
	
	/**
	 * @return String
	 */
	public String getSgTipoUnidade() {
		return sgTipoUnidade;
	}
	
	
	/**
	 * @param sgTipoUnidade String
	 */
	public void setSgTipoUnidade(String sgTipoUnidade) {
		this.sgTipoUnidade = sgTipoUnidade;
	}
	
	/**
	 * @return String
	 */
	public String getSgLocalizacao() {
		return sgLocalizacao;
	}
	
	/**
	 * @param sgLocalizacao String
	 */
	public void setSgLocalizacao(String sgLocalizacao) {
		this.sgLocalizacao = sgLocalizacao;
	}
	
// ****************************************************************
	
	public String getNuUnidadeFormatado() {
		if (this.nuUnidade != null) {
			return String.format("%04d", this.nuUnidade);
		}
		return null;
	}
	
// ****************************************************************

	/**
	 * @see br.com.spread.framework.vo.AbstractVO#getPk
	 */
	@Override
	public Object getPk() {
		if(this.getNuUnidade() == null || this.getNuNatural() == null){
			return null;
		}
		
		List<Object> listaPk = new LinkedList<Object>();
		listaPk.add(this.getNuUnidade());
		listaPk.add(this.getNuNatural());
		return listaPk;
	}

// ****************************************************************

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder retorno = new StringBuilder("");
		
		if(Util.notEmpty(this.getNuUnidade())){
			retorno
			.append(Numero.inserirZerosEsquerda(this.getNuUnidade(), 4))
			.append(" - ");
		}
		
		if(Util.notEmpty(this.getSgUnidade())){
			retorno.append(this.getSgUnidade());
			
			if(Util.notEmpty(this.getSgUf())) {
				retorno
				.append("/")
				.append(this.getSgUf())
				;
			}
		}
		else {
			retorno
			.append(this.getSgTipoUnidade())
			.append(" ")
			.append(this.getNoUnidade())
			;
		}
		
		return retorno.toString();
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nuNatural == null) ? 0 : nuNatural.hashCode());
		result = prime * result + ((nuUnidade == null) ? 0 : nuUnidade.hashCode());
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
		UnidadeVO other = (UnidadeVO) obj;
		if (nuNatural == null) {
			if (other.nuNatural != null)
				return false;
		}
		else if (!nuNatural.equals(other.nuNatural))
			return false;
		if (nuUnidade == null) {
			if (other.nuUnidade != null)
				return false;
		}
		else if (!nuUnidade.equals(other.nuUnidade))
			return false;
		return true;
	}

	public String getNomeGrupo() {
		return nomeGrupo;
	}

	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}

	public int getNuTipoUnidade() {
		return nuTipoUnidade;
	}

	public void setNuTipoUnidade(int nuTipoUnidade) {
		this.nuTipoUnidade = nuTipoUnidade;
	}
}