package br.gov.caixa.siavs.dao;

import java.sql.SQLException;

import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.vo.SegmentoVO;

/**
 * <b>Title:</b> SegmentoDAO <br>
 * <b>Description:</b> Classe que persiste o segmento. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 14/06/2013$
 */
public class SegmentoDAO extends AbstractDAOSIAVS <SegmentoVO> {

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getNomeSequence
	 */
	@Override
	protected String getNomeSequence() {
		return this.getNomeSchema() + "AVSSQ005_NU_SEGMENTO";
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#setIdentificador
	 */
	@Override
	protected void preencherIdentificador(SegmentoVO vo, Long identicador) {
		vo.setNuSegmento(identicador);
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlIncluir
	 */
	@Override
	protected String getSqlIncluir(SegmentoVO vo) {
		return new StringBuilder()
			.append("INSERT INTO " + this.getNomeTabela() + " (")
			.append("			 NO_SEGMENTO")
			.append(")	  VALUES (?)")
			.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosIncluir
	 */
	@Override
	protected void preencherParametrosIncluir(SegmentoVO vo) throws SQLException {
		this.setString(vo.getNoSegmento());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlAlterar
	 */
	@Override
	protected String getSqlAlterar(SegmentoVO vo) {
		return new StringBuilder()
			.append("UPDATE " + this.getNomeTabela() + " SET ")
			.append("       NO_SEGMENTO = ?")
			.append(" WHERE ")
			.append(" 		NU_SEGMENTO = ?")
			.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosAlterar
	 */
	@Override
	protected void preencherParametrosAlterar(SegmentoVO vo) throws SQLException {
		this.setString(vo.getNoSegmento());
		this.setLong(vo.getNuSegmento());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlExcluir
	 */
	@Override
	protected String getSqlExcluir(SegmentoVO vo) {
		return new StringBuilder("DELETE FROM " + this.getNomeTabela() + " WHERE NU_SEGMENTO = ?").toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosExcluir
	 */
	@Override
	protected void preencherParametrosExcluir(SegmentoVO vo) throws SQLException {
		this.setLong(vo.getNuSegmento());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlConsultar
	 */
	@Override
	protected String getSqlConsultar(SegmentoVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
			// Chave primária
			if (Util.notEmpty(vo.getNuSegmento())) {
				sql.append(" AND NU_SEGMENTO = ? ");
			}
			// Chave única
			else {
				if (Util.notEmpty(vo.getNoSegmento())) {
					sql.append(" AND " + this.getNomeSchema() + "NORMALIZAR(NO_SEGMENTO) = " + this.getNomeSchema() + "NORMALIZAR(?) ");
				}
			}
		}
		sql.append(" ORDER BY NU_SEGMENTO");

		return sql.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosConsultar
	 */
	@Override
	protected void preencherParametrosConsultar(SegmentoVO vo) throws SQLException {
		if (vo != null) {
			// Chave primária
			if (Util.notEmpty(vo.getNuSegmento())) {
				this.setLong(vo.getNuSegmento());
			}
			// Chave única
			else {
				if (Util.notEmpty(vo.getNoSegmento())) {
					this.setString(vo.getNoSegmento());
				}
			}
		}	
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOConsultar
	 */
	@Override
	protected SegmentoVO preencherVOConsultar() throws SQLException {
		SegmentoVO vo = new SegmentoVO();
		vo.setNuSegmento(this.getLong("NU_SEGMENTO"));
		vo.setNoSegmento(this.getString("NO_SEGMENTO"));
		return vo;
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlListar
	 */
	@Override
	protected String getSqlListar(SegmentoVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
				if (Util.notEmpty(vo.getNoSegmento())) {
				sql.append(" AND " + this.getNomeSchema() + "NORMALIZAR(NO_SEGMENTO) like '%' || " + this.getNomeSchema() + "NORMALIZAR(?) || '%' ");
			}
		}
		sql.append(" ORDER BY NO_SEGMENTO");

		return sql.toString();
	}
	
	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosListar
	 */
	@Override
	protected void preencherParametrosListar(SegmentoVO vo) throws SQLException {
		if (vo != null) {
			if (Util.notEmpty(vo.getNoSegmento())) {
				this.setString(vo.getNoSegmento());
			}
		}
	}
	
	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOListar
	 */
	@Override
	protected SegmentoVO preencherVOListar() throws SQLException {
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
		.append("		NU_SEGMENTO, ")
		.append("		NO_SEGMENTO")
		.append("  FROM " + this.getNomeTabela() + " ")
		.append("WHERE	1 = 1 ")
		.toString();
	}

	/**
	 * Retorna o nome da tabela.
	 * @return String
	 */
	private String getNomeTabela() {
		return this.getNomeSchema() + "AVSTB005_SEGMENTO";
	}
}