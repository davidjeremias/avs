package br.gov.caixa.siavs.dao;

import java.sql.SQLException;

import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.MetaVO;

/**
 * <b>Title:</b> MetaDAO <br>
 * <b>Description:</b> Classe que persiste o meta. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 14/06/2013$
 */
public class MetaDAO extends AbstractDAOSIAVS <MetaVO> {

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getNomeSequence
	 */
	@Override
	protected String getNomeSequence() {
		return this.getNomeSchema() + "AVSSQ013_NU_META";
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#setIdentificador
	 */
	@Override
	protected void preencherIdentificador(MetaVO vo, Long identicador) {
		vo.setNuMeta(identicador);
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlIncluir
	 */
	@Override
	protected String getSqlIncluir(MetaVO vo) {
		return new StringBuilder()
			.append("INSERT INTO " + this.getNomeTabela() + " (")
			.append("			 AA_COMPETENCIA, ")
			.append("			 VR_NOTA_META, ")
			.append("			 NU_GRUPO")
			.append(")	  VALUES (?, ?, ?)")
			.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosIncluir
	 */
	@Override
	protected void preencherParametrosIncluir(MetaVO vo) throws SQLException {
		this.setInteger(vo.getAaCompetencia());
		this.setInteger(vo.getVrNotaMeta());
		this.setLong(vo.getGrupo().getNuGrupo());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlAlterar
	 */
	@Override
	protected String getSqlAlterar(MetaVO vo) {
		return new StringBuilder()
			.append("UPDATE " + this.getNomeTabela() + " SET ")
			.append("       AA_COMPETENCIA = ?, ")
			.append("       VR_NOTA_META = ?, ")
			.append("       NU_GRUPO = ?")
			.append(" WHERE ")
			.append(" 		NU_META = ?")
			.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosAlterar
	 */
	@Override
	protected void preencherParametrosAlterar(MetaVO vo) throws SQLException {
		this.setInteger(vo.getAaCompetencia());
		this.setInteger(vo.getVrNotaMeta());
		this.setLong(vo.getGrupo().getNuGrupo());
		this.setLong(vo.getNuMeta());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlExcluir
	 */
	@Override
	protected String getSqlExcluir(MetaVO vo) {
		return new StringBuilder("DELETE FROM " + this.getNomeTabela() + " WHERE NU_GRUPO = ?").toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosExcluir
	 */
	@Override
	protected void preencherParametrosExcluir(MetaVO vo) throws SQLException {
		this.setLong(vo.getGrupo().getNuGrupo());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlConsultar
	 */
	@Override
	protected String getSqlConsultar(MetaVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
			// Chave primária
			if (Util.notEmpty(vo.getNuMeta())) {
				sql.append(" AND NU_META = ? ");
			}
			// Chave única
			else {
				if (vo.getGrupo() != null && Util.notEmpty(vo.getGrupo().getNuGrupo())) {
					sql.append(" AND NU_GRUPO = ? ");
				}
				if (Util.notEmpty(vo.getAaCompetencia())) {
					sql.append(" AND AA_COMPETENCIA = ? ");
				}
			}
		}
		sql.append(" ORDER BY NU_META");

		return sql.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosConsultar
	 */
	@Override
	protected void preencherParametrosConsultar(MetaVO vo) throws SQLException {
		if (vo != null) {
			// Chave primária
			if (Util.notEmpty(vo.getNuMeta())) {
				this.setLong(vo.getNuMeta());
			}
			// Chave única
			else {
				if (vo.getGrupo() != null && Util.notEmpty(vo.getGrupo().getNuGrupo())) {
					this.setLong(vo.getGrupo().getNuGrupo());
				}
				if (Util.notEmpty(vo.getAaCompetencia())) {
					this.setInteger(vo.getAaCompetencia());
				}
			}
		}	
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOConsultar
	 */
	@Override
	protected MetaVO preencherVOConsultar() throws SQLException {
		MetaVO vo = new MetaVO();
		vo.setNuMeta(this.getLong("NU_META"));
		vo.setAaCompetencia(this.getInteger("AA_COMPETENCIA"));
		vo.setVrNotaMeta(this.getInteger("VR_NOTA_META"));
		vo.setGrupo(new GrupoVO(this.getLong("NU_GRUPO")));
		return vo;
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlListar
	 */
	@Override
	protected String getSqlListar(MetaVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
			if (vo.getGrupo() != null && Util.notEmpty(vo.getGrupo().getNuGrupo())) {
				sql.append(" AND NU_GRUPO = ? ");
			}
		}
		sql.append(" ORDER BY NU_META");

		return sql.toString();
	}
	
	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosListar
	 */
	@Override
	protected void preencherParametrosListar(MetaVO vo) throws SQLException {
		if (vo != null) {
			if (vo.getGrupo() != null && Util.notEmpty(vo.getGrupo().getNuGrupo())) {
				this.setLong(vo.getGrupo().getNuGrupo());
			}
		}
	}
	
	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOListar
	 */
	@Override
	protected MetaVO preencherVOListar() throws SQLException {
		return this.preencherVOConsultar();
	}

// ***********************************************************************************************************************************
	
	/**
	 * Retorna o sql padrão de select.
	 * @return String
	 */
	private String getSqlSelect() {
		return new StringBuilder()
		.append("SELECT ")
		.append("		NU_META, ")
		.append("		AA_COMPETENCIA, ")
		.append("		VR_NOTA_META, ")
		.append("		NU_GRUPO")
		.append("  FROM " + this.getNomeTabela() + " ")
		.append("WHERE	1 = 1 ")
		.toString();
	}

	/**
	 * Retorna o nome da tabela.
	 * @return String
	 */
	private String getNomeTabela() {
		return this.getNomeSchema() + "AVSTB013_META";
	}
}