package br.gov.caixa.siavs.dao;

import java.sql.SQLException;

import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.vo.ProblemaVO;

/**
 * <b>Title:</b> ProblemaDAO <br>
 * <b>Description:</b> Classe que persiste o problema. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 28/06/2013$
 */
public class ProblemaDAO extends AbstractDAOSIAVS <ProblemaVO> {

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlConsultar
	 */
	@Override
	protected String getSqlConsultar(ProblemaVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
			// Chave primária
			if (Util.notEmpty(vo.getNuProblema())) {
				sql.append(" AND NU_PROBLEMA = ? ");
			}
			// Chave única
			else {
				if (Util.notEmpty(vo.getDeProblema())) {
					sql.append(" AND " + this.getNomeSchema() + "NORMALIZAR(DE_PROBLEMA) = " + this.getNomeSchema() + "NORMALIZAR(?) ");
				}
			}
		}
		sql.append(" ORDER BY NU_PROBLEMA");

		return sql.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosConsultar
	 */
	@Override
	protected void preencherParametrosConsultar(ProblemaVO vo) throws SQLException {
		if (vo != null) {
			// Chave primária
			if (Util.notEmpty(vo.getNuProblema())) {
				this.setLong(vo.getNuProblema());
			}
			// Chave única
			else {
				if (Util.notEmpty(vo.getDeProblema())) {
					this.setString(vo.getDeProblema());
				}
			}
		}	
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOConsultar
	 */
	@Override
	protected ProblemaVO preencherVOConsultar() throws SQLException {
		ProblemaVO vo = new ProblemaVO();
		vo.setNuProblema(this.getLong("NU_PROBLEMA"));
		vo.setDeProblema(this.getString("DE_PROBLEMA"));
		vo.setIcGenerico(this.getBoolean("IC_GENERICO"));
		return vo;
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlListar
	 */
	@Override
	protected String getSqlListar(ProblemaVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
			if (Util.notEmpty(vo.getDeProblema())) {
				sql.append(" AND " + this.getNomeSchema() + "NORMALIZAR(DE_PROBLEMA) like '%' || " + this.getNomeSchema() + "NORMALIZAR(?) || '%' ");
			}
		}
		sql.append(" ORDER BY DE_PROBLEMA");

		return sql.toString();
	}
	
	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosListar
	 */
	@Override
	protected void preencherParametrosListar(ProblemaVO vo) throws SQLException {
		if (vo != null) {
			if (Util.notEmpty(vo.getDeProblema())) {
				this.setString(vo.getDeProblema());
			}
		}
	}
	
	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOListar
	 */
	@Override
	protected ProblemaVO preencherVOListar() throws SQLException {
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
		.append("		NU_PROBLEMA, ")
		.append("		DE_PROBLEMA, ")
		.append("		IC_GENERICO")
		.append("  FROM " + this.getNomeTabela() + " ")
		.append("WHERE	1 = 1 ")
		.toString();
	}

	/**
	 * Retorna o nome da tabela.
	 * @return String
	 */
	private String getNomeTabela() {
		return this.getNomeSchema() + "AVSTB009_PROBLEMA";
	}
}