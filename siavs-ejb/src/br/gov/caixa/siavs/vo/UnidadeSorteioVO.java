package br.gov.caixa.siavs.vo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * <b>Title:</b> UnidadeSorteioVO <br>
 * <b>Description:</b> Permite armazenar os dados das agências sorteadas. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 15/07/2013$
 */
public class UnidadeSorteioVO extends AbstractVOSIAVS {

	private static final long serialVersionUID = 1L;

// ****************************************************************
	/**
	 * Data para qual a unidade foi sorteada.
	 */
	private Date dtSorteio;
	/**
	 * Relacionamento com unidade.
	 */
	private UnidadeVO unidade;
// ****************************************************************
	/**
	 * Construtor padrão.
	 */
	public UnidadeSorteioVO(){}

	/**
	 * Construtor com chave primária.
	 * @param unidade UnidadeVO
	 * @param dtSorteio Date
	 */
	public UnidadeSorteioVO(UnidadeVO unidade, Date dtSorteio){
		this.setUnidade(unidade);
		this.setDtSorteio(dtSorteio);
	}	
// ****************************************************************

	/**
	 * @return Date
	 */
	public Date getDtSorteio() {
		return dtSorteio;
	}

	/**
	 * @param dtSorteio Date
	 */
	public void setDtSorteio(Date dtSorteio) {
		this.dtSorteio = dtSorteio;
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
		if(this.getDtSorteio() == null || this.getUnidade() == null){
			return null;
		}
		
		List<Object> listaPk = new LinkedList<Object>();
		listaPk.add(this.getDtSorteio());
		listaPk.add(this.getUnidade());
		return listaPk;
	}
}