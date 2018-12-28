package br.gov.caixa.siavs.dao;

import java.sql.SQLException;

import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.vo.GrupoVO;

/**
 * <b>Title:</b> GrupoDAO <br>
 * <b>Description:</b> Classe que persiste o grupo. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 14/06/2013$
 */
public class GrupoDAO extends AbstractDAOSIAVS <GrupoVO> {

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getNomeSequence
	 */
	@Override
	protected String getNomeSequence() {
		return this.getNomeSchema() + "AVSSQ001_NU_GRUPO";
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#setIdentificador
	 */
	@Override
	protected void preencherIdentificador(GrupoVO vo, Long identicador) {
		vo.setNuGrupo(identicador);
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlIncluir
	 */
	@Override
	protected String getSqlIncluir(GrupoVO vo) {
		return new StringBuilder()
			.append("INSERT INTO " + this.getNomeTabela() + " (")
			.append("			 NO_GRUPO, ")
			.append("			 IC_ENVIA_EMAIL_CONVOCACAO, ")
			.append("			 IC_FREQUENCIA_AVALIACAO, ")
			.append("			 IC_ATIVO, ")
			.append("			 IC_AGENCIA")
			.append(")	  VALUES (?, ?, ?, ?, ?)")
			.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosIncluir
	 */
	@Override
	protected void preencherParametrosIncluir(GrupoVO vo) throws SQLException {
		this.setString(vo.getNoGrupo());
		this.setBoolean(vo.getIcEnviaEmailConvocacao());
		this.setString(vo.getIcFrequenciaAvaliacao());
		this.setBoolean(vo.getIcAtivo());
		this.setBoolean(vo.getIcAgencia());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlAlterar
	 */
	@Override
	protected String getSqlAlterar(GrupoVO vo) {
		return new StringBuilder()
			.append("UPDATE " + this.getNomeTabela() + " SET ")
			.append("       NO_GRUPO = ?, ")
			.append("       IC_ENVIA_EMAIL_CONVOCACAO = ?, ")
			.append("       IC_FREQUENCIA_AVALIACAO = ?, ")
			.append("       IC_ATIVO = ?, ")
			.append("       IC_AGENCIA = ?")
			.append(" WHERE ")
			.append(" 		NU_GRUPO = ?")
			.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosAlterar
	 */
	@Override
	protected void preencherParametrosAlterar(GrupoVO vo) throws SQLException {
		this.setString(vo.getNoGrupo());
		this.setBoolean(vo.getIcEnviaEmailConvocacao());
		this.setString(vo.getIcFrequenciaAvaliacao());
		this.setBoolean(vo.getIcAtivo());
		this.setBoolean(vo.getIcAgencia());
		this.setLong(vo.getNuGrupo());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlExcluir
	 */
	@Override
	protected String getSqlExcluir(GrupoVO vo) {
		return new StringBuilder("DELETE FROM " + this.getNomeTabela() + " WHERE NU_GRUPO = ?").toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosExcluir
	 */
	@Override
	protected void preencherParametrosExcluir(GrupoVO vo) throws SQLException {
		this.setLong(vo.getNuGrupo());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlConsultar
	 */
	@Override
	protected String getSqlConsultar(GrupoVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
			// Chave primária
			if (Util.notEmpty(vo.getNuGrupo())) {
				sql.append(" AND NU_GRUPO = ? ");
			}
			// Chave única
			else {
				if (Util.notEmpty(vo.getNoGrupo())) {
					sql.append(" AND " + this.getNomeSchema() + "NORMALIZAR(NO_GRUPO) = " + this.getNomeSchema() + "NORMALIZAR(?) ");
				}
			}
		}
		sql.append(" ORDER BY NU_GRUPO");

		return sql.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosConsultar
	 */
	@Override
	protected void preencherParametrosConsultar(GrupoVO vo) throws SQLException {
		if (vo != null) {
			// Chave primária
			if (Util.notEmpty(vo.getNuGrupo())) {
				this.setLong(vo.getNuGrupo());
			}
			// Chave única
			else {
				if (Util.notEmpty(vo.getNoGrupo())) {
					this.setString(vo.getNoGrupo());
				}
			}
		}	
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOConsultar
	 */
	@Override
	protected GrupoVO preencherVOConsultar() throws SQLException {
		GrupoVO vo = new GrupoVO();
		vo.setNuGrupo(this.getLong("NU_GRUPO"));
		vo.setNoGrupo(this.getString("NO_GRUPO"));
		vo.setIcEnviaEmailConvocacao(this.getBoolean("IC_ENVIA_EMAIL_CONVOCACAO"));
		vo.setIcFrequenciaAvaliacao(this.getString("IC_FREQUENCIA_AVALIACAO"));
		vo.setIcAtivo(this.getBoolean("IC_ATIVO"));
		vo.setIcAgencia(this.getBoolean("IC_AGENCIA"));
		return vo;
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlListar
	 */
	@Override
	protected String getSqlListar(GrupoVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
			if (Util.notEmpty(vo.getNoGrupo())) {
				sql.append(" AND " + this.getNomeSchema() + "NORMALIZAR(NO_GRUPO) like '%' || " + this.getNomeSchema() + "NORMALIZAR(?) || '%' ");
			}
			if(vo.getIcAtivo() != null){
				sql.append(" AND IC_ATIVO = ? ");
			}
		}
		sql.append(" ORDER BY NU_GRUPO");

		return sql.toString();
	}
	
	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosListar
	 */
	@Override
	protected void preencherParametrosListar(GrupoVO vo) throws SQLException {
		if (vo != null) {
			if (Util.notEmpty(vo.getNoGrupo())) {
				this.setString(vo.getNoGrupo());
			}
			if(vo.getIcAtivo() != null){
				this.setBoolean(vo.getIcAtivo());
			}
		}
	}
	
	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOListar
	 */
	@Override
	protected GrupoVO preencherVOListar() throws SQLException {
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
		.append("		NU_GRUPO, ")
		.append("		NO_GRUPO, ")
		.append("		IC_ENVIA_EMAIL_CONVOCACAO, ")
		.append("		IC_FREQUENCIA_AVALIACAO, ")
		.append("		IC_ATIVO, ")
		.append("		IC_AGENCIA")
		.append("  FROM " + this.getNomeTabela() + " ")
		.append("WHERE	1 = 1 ")
		.toString();
	}

	/**
	 * Retorna o nome da tabela.
	 * @return String
	 */
	private String getNomeTabela() {
		return this.getNomeSchema() + "AVSTB001_GRUPO";
	}
}