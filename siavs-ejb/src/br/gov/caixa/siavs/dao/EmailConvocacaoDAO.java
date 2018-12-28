package br.gov.caixa.siavs.dao;

import java.sql.SQLException;

import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.vo.EmailConvocacaoVO;
import br.gov.caixa.siavs.vo.GrupoVO;

/**
 * <b>Title:</b> EmailConvocacaoDAO <br>
 * <b>Description:</b> Classe que persiste o emailConvocacao. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 04/07/2013$
 */
public class EmailConvocacaoDAO extends AbstractDAOSIAVS <EmailConvocacaoVO> {

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getNomeSequence
	 */
	@Override
	protected String getNomeSequence() {
		return this.getNomeSchema() + "AVSSQ014_NU_EMAIL_CONVOCACAO";
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#setIdentificador
	 */
	@Override
	protected void preencherIdentificador(EmailConvocacaoVO vo, Long identicador) {
		vo.setNuEmailConvocacao(identicador);
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlIncluir
	 */
	@Override
	protected String getSqlIncluir(EmailConvocacaoVO vo) {
		return new StringBuilder()
			.append("INSERT INTO " + this.getNomeTabela() + " (")
			.append("			 DE_ASSUNTO, ")
			.append("			 DE_CONTEUDO, ")
			.append("			 NU_GRUPO")
			.append(")	  VALUES (?, ?, ?)")
			.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosIncluir
	 */
	@Override
	protected void preencherParametrosIncluir(EmailConvocacaoVO vo) throws SQLException {
		this.setString(vo.getDeAssunto());
		this.setString(vo.getDeConteudo());
		this.setLong(vo.getGrupo().getNuGrupo());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlAlterar
	 */
	@Override
	protected String getSqlAlterar(EmailConvocacaoVO vo) {
		return new StringBuilder()
			.append("UPDATE " + this.getNomeTabela() + " SET ")
			.append("       DE_ASSUNTO = ?, ")
			.append("       DE_CONTEUDO = ?, ")
			.append("       NU_GRUPO = ?")
			.append(" WHERE ")
			.append(" 		NU_EMAIL_CONVOCACAO = ?")
			.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosAlterar
	 */
	@Override
	protected void preencherParametrosAlterar(EmailConvocacaoVO vo) throws SQLException {
		this.setString(vo.getDeAssunto());
		this.setString(vo.getDeConteudo());
		this.setLong(vo.getGrupo().getNuGrupo());
		this.setLong(vo.getNuEmailConvocacao());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlExcluir
	 */
	@Override
	protected String getSqlExcluir(EmailConvocacaoVO vo) {
		return new StringBuilder("DELETE FROM " + this.getNomeTabela() + " WHERE NU_GRUPO = ?").toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosExcluir
	 */
	@Override
	protected void preencherParametrosExcluir(EmailConvocacaoVO vo) throws SQLException {
		this.setLong(vo.getGrupo().getNuGrupo());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlConsultar
	 */
	@Override
	protected String getSqlConsultar(EmailConvocacaoVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
			// Chave primária
			if (Util.notEmpty(vo.getNuEmailConvocacao())) {
				sql.append(" AND E.NU_EMAIL_CONVOCACAO = ? ");
			}
			// Chave única
			else {
				if (Util.notEmpty(vo.getGrupo().getNuGrupo())) {
					sql.append(" AND E.NU_GRUPO = ? ");
				}
			}
		}
		sql.append(" ORDER BY E.NU_EMAIL_CONVOCACAO");

		return sql.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosConsultar
	 */
	@Override
	protected void preencherParametrosConsultar(EmailConvocacaoVO vo) throws SQLException {
		if (vo != null) {
			// Chave primária
			if (Util.notEmpty(vo.getNuEmailConvocacao())) {
				this.setLong(vo.getNuEmailConvocacao());
			}
			// Chave única
			else {
				if (Util.notEmpty(vo.getGrupo().getNuGrupo())) {
					this.setLong(vo.getGrupo().getNuGrupo());
				}
			}
		}	
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOConsultar
	 */
	@Override
	protected EmailConvocacaoVO preencherVOConsultar() throws SQLException {
		EmailConvocacaoVO vo = new EmailConvocacaoVO();
		vo.setNuEmailConvocacao(this.getLong("NU_EMAIL_CONVOCACAO"));
		vo.setDeAssunto(this.getString("DE_ASSUNTO"));
		vo.setDeConteudo(this.getString("DE_CONTEUDO"));
		vo.setGrupo(new GrupoVO(this.getLong("NU_GRUPO")));
		vo.getGrupo().setIcFrequenciaAvaliacao(this.getString("IC_FREQUENCIA_AVALIACAO"));
		return vo;
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlListar
	 */
	@Override
	protected String getSqlListar(EmailConvocacaoVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
			if (Util.notEmpty(vo.getDeAssunto())) {
				sql.append(" AND " + this.getNomeSchema() + "NORMALIZAR(E.DE_ASSUNTO) like '%' || " + this.getNomeSchema() + "NORMALIZAR(?) || '%' ");
			}
			if (Util.notEmpty(vo.getDeConteudo())) {
				sql.append(" AND " + this.getNomeSchema() + "NORMALIZAR(E.DE_CONTEUDO) like '%' || " + this.getNomeSchema() + "NORMALIZAR(?) || '%' ");
			}
		}
		sql.append(" ORDER BY E.NU_EMAIL_CONVOCACAO");

		return sql.toString();
	}
	
	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosListar
	 */
	@Override
	protected void preencherParametrosListar(EmailConvocacaoVO vo) throws SQLException {
		if (vo != null) {
			if (Util.notEmpty(vo.getDeAssunto())) {
				this.setString(vo.getDeAssunto());
			}
			if (Util.notEmpty(vo.getDeConteudo())) {
				this.setString(vo.getDeConteudo());
			}
		}
	}
	
	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOListar
	 */
	@Override
	protected EmailConvocacaoVO preencherVOListar() throws SQLException {
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
		.append("		E.NU_EMAIL_CONVOCACAO, ")
		.append("		E.DE_ASSUNTO, ")
		.append("		E.DE_CONTEUDO, ")
		.append("		E.NU_GRUPO, ")
		.append("		G.IC_FREQUENCIA_AVALIACAO ")
		.append("  FROM " + this.getNomeTabela() + " E, ")
		.append("       " + this.getNomeSchema() + "AVSTB001_GRUPO G ")
		.append("WHERE	1 = 1 ")
		.append("       AND E.NU_GRUPO = G.NU_GRUPO ")
		.toString();
	}

	/**
	 * Retorna o nome da tabela.
	 * @return String
	 */
	private String getNomeTabela() {
		return this.getNomeSchema() + "AVSTB014_EMAIL_CONVOCACAO";
	}

// ***********************************************************************************************************************************

}