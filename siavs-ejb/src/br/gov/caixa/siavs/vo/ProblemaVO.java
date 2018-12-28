package br.gov.caixa.siavs.vo;

/**
 * <b>Title:</b> ProblemaVO <br>
 * <b>Description:</b> Permite manter as informações do Problema. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 28/06/2013$
 */
public class ProblemaVO extends AbstractVOSIAVS {

	private static final long serialVersionUID = 1L;

// ****************************************************************
	/**
	 * Identificador do Problema
	 */
	private Long nuProblema;
	/**
	 * Descrição do problema.
	 */
	private String deProblema;
	/**
	 * Se é um problema genérico, que necessita de mais informações para descrevê-lo.
	 */
	private Boolean icGenerico;
// ****************************************************************
	/**
	 * Construtor padrão.
	 */
	public ProblemaVO(){}

	/**
	 * Construtor com chave primária.
	 * @param nuProblema Long
	 */
	public ProblemaVO(Long nuProblema){
		this.setNuProblema(nuProblema);
	}

	/**
	 * Construtor com chave única.
	 * @param deProblema String
	 */
	public ProblemaVO(String deProblema){
		this.setDeProblema(deProblema);
	}
// ****************************************************************

	/**
	 * @return Long
	 */
	public Long getNuProblema() {
		return nuProblema;
	}

	/**
	 * @param nuProblema Long
	 */
	public void setNuProblema(Long nuProblema) {
		this.nuProblema = nuProblema;
	}

	/**
	 * @return String
	 */
	public String getDeProblema() {
		return deProblema;
	}

	/**
	 * @param deProblema String
	 */
	public void setDeProblema(String deProblema) {
		this.deProblema = deProblema;
	}

	/**
	 * @return Boolean
	 */
	public Boolean getIcGenerico() {
		if(icGenerico == null) {
			this.setIcGenerico(false);
		}
		return icGenerico;
	}

	/**
	 * @param icGenerico Boolean
	 */
	public void setIcGenerico(Boolean icGenerico) {
		this.icGenerico = icGenerico;
	}

// ****************************************************************

	/**
	 * @see br.com.spread.framework.vo.AbstractVO#getPk
	 */
	@Override
	public Object getPk() {
		return this.getNuProblema();
	}
	
// ****************************************************************

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nuProblema == null) ? 0 : nuProblema.hashCode());
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
		ProblemaVO other = (ProblemaVO) obj;
		if (nuProblema == null) {
			if (other.nuProblema != null)
				return false;
		}
		else if (!nuProblema.equals(other.nuProblema))
			return false;
		return true;
	}
}