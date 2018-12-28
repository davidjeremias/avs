package br.gov.caixa.siavs.vo;

import java.util.Date;

/**
 * <b>Title:</b> GrupoUnidadeVO <br>
 * <b>Description:</b> Permite ao Administrador vincular unidade, permitindo aos funcionários dessas unidades avaliarem os sistemas do grupo. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 07/06/2013$
 */
public class GrupoUnidadeVO extends AbstractVOSIAVS {

	private static final long serialVersionUID = 1L;

// ****************************************************************
	/**
	 * Identificador do Grupo Unidade
	 */
	private Long nuGrupoUnidade;
	/**
	 * Data de vinculação da unidade ao grupo
	 */
	private Date dtInclusao;
	/**
	 * Relacionamento com grupo.
	 */
	private GrupoVO grupo;
	/**
	 * Relacionamento com unidade.
	 */
	private UnidadeVO unidade;
	
// ****************************************************************
	/**
	 * Construtor padrão.
	 */
	public GrupoUnidadeVO(){}

	/**
	 * Construtor com chave primária.
	 * @param nuGrupoUnidade Long
	 */
	public GrupoUnidadeVO(Long nuGrupoUnidade){
		this.setNuGrupoUnidade(nuGrupoUnidade);
	}

	/**
	 * Construtor com chave única.
	 * @param grupo GrupoVO
	 * @param unidade UnidadeVO
	 */
	public GrupoUnidadeVO(GrupoVO grupo, UnidadeVO unidade){
		this.setGrupo(grupo);
		this.setUnidade(unidade);
	}
// ****************************************************************

	/**
	 * @return Long
	 */
	public Long getNuGrupoUnidade() {
		return nuGrupoUnidade;
	}

	/**
	 * @param nuGrupoUnidade Long
	 */
	public void setNuGrupoUnidade(Long nuGrupoUnidade) {
		this.nuGrupoUnidade = nuGrupoUnidade;
	}

	/**
	 * @return Date
	 */
	public Date getDtInclusao() {
		return dtInclusao;
	}

	/**
	 * @param dtInclusao Date
	 */
	public void setDtInclusao(Date dtInclusao) {
		this.dtInclusao = dtInclusao;
	}

	/**
	 * @return GrupoVO
	 */
	public GrupoVO getGrupo() {
		return grupo;
	}

	/**
	 * @param grupo GrupoVO
	 */
	public void setGrupo(GrupoVO grupo) {
		this.grupo = grupo;
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
		return this.getNuGrupoUnidade();
	}
}