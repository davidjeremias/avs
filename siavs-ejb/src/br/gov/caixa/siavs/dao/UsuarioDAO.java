package br.gov.caixa.siavs.dao;

import java.sql.SQLException;

import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.vo.FuncionarioCaixaLocalVO;
import br.gov.caixa.siavs.vo.FuncionarioCaixaVO;
import br.gov.caixa.siavs.vo.UnidadeVO;
import br.gov.caixa.siavs.vo.UsuarioVO;

/**
 * <b>Title:</b> UsuarioDAO <br>
 * <b>Description:</b> Classe que persiste o usuario. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 08/07/2013$
 */
public class UsuarioDAO extends AbstractDAOSIAVS <UsuarioVO> {

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getNomeSequence
	 */
	@Override
	protected String getNomeSequence() {
		return this.getNomeSchema() + "AVSSQ012_NU_USUARIO";
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#setIdentificador
	 */
	@Override
	protected void preencherIdentificador(UsuarioVO vo, Long identicador) {
		vo.setNuUsuario(identicador);
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlIncluir
	 */
	@Override
	protected String getSqlIncluir(UsuarioVO vo) {
		return new StringBuilder()
			.append("INSERT INTO " + this.getNomeTabela() + " (")
			.append("			 IC_ACESSO, ")
			.append("			 NU_FNCRO_CAIXA_LOCAL")
			.append(")	  VALUES (?, ?)")
			.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosIncluir
	 */
	@Override
	protected void preencherParametrosIncluir(UsuarioVO vo) throws SQLException {
		this.setString(vo.getIcAcesso());
		this.setLong(vo.getFuncionarioCaixaLocal().getNuFncroCaixaLocal());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlAlterar
	 */
	@Override
	protected String getSqlAlterar(UsuarioVO vo) {
		return new StringBuilder()
			.append("UPDATE " + this.getNomeTabela() + " SET ")
			.append("       IC_ACESSO = ?, ")
			.append("       NU_FNCRO_CAIXA_LOCAL = ?")
			.append(" WHERE ")
			.append(" 		NU_USUARIO = ?")
			.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosAlterar
	 */
	@Override
	protected void preencherParametrosAlterar(UsuarioVO vo) throws SQLException {
		this.setString(vo.getIcAcesso());
		this.setLong(vo.getFuncionarioCaixaLocal().getNuFncroCaixaLocal());
		this.setLong(vo.getNuUsuario());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlExcluir
	 */
	@Override
	protected String getSqlExcluir(UsuarioVO vo) {
		return new StringBuilder("DELETE FROM " + this.getNomeTabela() + " WHERE NU_USUARIO = ?").toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosExcluir
	 */
	@Override
	protected void preencherParametrosExcluir(UsuarioVO vo) throws SQLException {
		this.setLong(vo.getNuUsuario());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlConsultar
	 */
	@Override
	protected String getSqlConsultar(UsuarioVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
			
			// Chave primária
			if (Util.notEmpty(vo.getNuUsuario())) {
				sql.append(" AND US.NU_USUARIO = ? ");
			}
			
			// Chave única
			else if (vo.getFuncionarioCaixaLocal() != null && 
					 vo.getFuncionarioCaixaLocal().getFuncionarioCaixa() != null && Util.notEmpty(vo.getFuncionarioCaixaLocal().getFuncionarioCaixa().getNuMatricula()) &&
						 vo.getFuncionarioCaixaLocal().getUnidade() != null && Util.notEmpty(vo.getFuncionarioCaixaLocal().getUnidade().getNuUnidade())) {
				sql
				.append(" AND FL.NU_MATRICULA = ? ")
				.append(" AND FL.NU_UNIDADE = ? ");
				
				if (vo.getFuncionarioCaixaLocal().getUnidade() != null && Util.notEmpty(vo.getFuncionarioCaixaLocal().getUnidade().getNuNatural())) {
					sql.append(" AND FL.NU_NATURAL = ? ");
				}
			}	
			
			// Chave única
			else if (vo.getFuncionarioCaixaLocal() != null && Util.notEmpty(vo.getFuncionarioCaixaLocal().getNuFncroCaixaLocal())) {
				sql.append(" AND F.NU_FNCRO_CAIXA_LOCAL = ? ");
			}	
		
		}
		
		sql.append(" ORDER BY upper(F.NO_EMPREGADO)");

		return sql.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosConsultar
	 */
	@Override
	protected void preencherParametrosConsultar(UsuarioVO vo) throws SQLException {
		if (vo != null) {
			// Chave primária
			if (Util.notEmpty(vo.getNuUsuario())) {
				this.setLong(vo.getNuUsuario());
			}
			// Chave única
			else if (vo.getFuncionarioCaixaLocal() != null && 
					 vo.getFuncionarioCaixaLocal().getFuncionarioCaixa() != null && Util.notEmpty(vo.getFuncionarioCaixaLocal().getFuncionarioCaixa().getNuMatricula()) &&
					 vo.getFuncionarioCaixaLocal().getUnidade() != null && Util.notEmpty(vo.getFuncionarioCaixaLocal().getUnidade().getNuUnidade())) {
				
				this.setString(vo.getFuncionarioCaixaLocal().getFuncionarioCaixa().getNuMatricula());
				this.setInteger(vo.getFuncionarioCaixaLocal().getUnidade().getNuUnidade());
				
				if (vo.getFuncionarioCaixaLocal().getUnidade() != null && Util.notEmpty(vo.getFuncionarioCaixaLocal().getUnidade().getNuNatural())) {
					this.setInteger(vo.getFuncionarioCaixaLocal().getUnidade().getNuNatural());
				}
			}
			// Chave única
			else if (vo.getFuncionarioCaixaLocal() != null && Util.notEmpty(vo.getFuncionarioCaixaLocal().getNuFncroCaixaLocal())) {
				this.setLong(vo.getFuncionarioCaixaLocal().getNuFncroCaixaLocal());
			}			
		}	
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOConsultar
	 */
	@Override
	protected UsuarioVO preencherVOConsultar() throws SQLException {
		UsuarioVO vo = new UsuarioVO();
		
		vo.setNuUsuario(this.getLong("NU_USUARIO"));
		vo.setIcAcesso(this.getString("IC_ACESSO"));
		vo.setFuncionarioCaixaLocal(new FuncionarioCaixaLocalVO(this.getLong("NU_FNCRO_CAIXA_LOCAL")));
		vo.getFuncionarioCaixaLocal().setFuncionarioCaixa(new FuncionarioCaixaVO(this.getString("NU_MATRICULA")));
		vo.getFuncionarioCaixaLocal().getFuncionarioCaixa().setNoFuncionario(this.getString("NO_EMPREGADO"));
		vo.getFuncionarioCaixaLocal().getFuncionarioCaixa().setNuTipoFuncao(this.getInteger("NU_TIPO_FUNCAO"));
		vo.getFuncionarioCaixaLocal().setUnidade(new UnidadeVO(this.getInteger("NU_UNIDADE"), this.getInteger("NU_NATURAL")));
		vo.getFuncionarioCaixaLocal().getUnidade().setNoUnidade(this.getString("NO_UNIDADE"));
		vo.getFuncionarioCaixaLocal().getUnidade().setDeEmail(this.getString("DE_EMAIL"));
		vo.getFuncionarioCaixaLocal().getUnidade().setNuTipoUnidade(this.getInteger("NU_TP_UNIDADE_U21"));
		
		return vo;
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlListar
	 */
	@Override
	protected String getSqlListar(UsuarioVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());
		sql.append(" ORDER BY upper(F.NO_EMPREGADO)");
		return sql.toString();
	}
	
	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosListar
	 */
	@Override
	protected void preencherParametrosListar(UsuarioVO vo) throws SQLException {
	}
	
	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOListar
	 */
	@Override
	protected UsuarioVO preencherVOListar() throws SQLException {
		return this.preencherVOConsultar();
	}

// ***********************************************************************************************************************************
	
	/**
	 * Retorna o sql padrão de select.
	 * @return String
	 */
	private String getSqlSelect() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(" SELECT FL.NU_MATRICULA, US.NU_USUARIO, US.IC_ACESSO, FL.NU_FNCRO_CAIXA_LOCAL, FL.NU_MATRICULA,");
		sb.append("        FL.NU_UNIDADE, FL.NU_NATURAL, F.NO_EMPREGADO, F.NU_TIPO_FUNCAO,");
		sb.append("        U.NO_UNIDADE, U.DE_EMAIL, U.NU_TP_UNIDADE_u21");
		sb.append("   FROM AVSSM001.AVSTB012_USUARIO US");
		sb.append("  INNER JOIN AVSSM001.AVSTB007_FNCRO_CAIXA_LOCAL FL");
		sb.append("     on US.NU_FNCRO_CAIXA_LOCAL = FL.NU_FNCRO_CAIXA_LOCAL");
		sb.append("  INNER JOIN AVSSM001.AVSVW002_FUNCIONARIO_CAIXA F");
		sb.append("     ON F.NU_MATRICULA::int = FL.NU_MATRICULA::int");
		sb.append("    AND F.NU_UNIDADE = FL.NU_UNIDADE");
		sb.append("    AND F.NU_NATURAL = FL.NU_NATURAL");
		sb.append("  INNER JOIN AVSSM001.AVSVW001_UNIDADE U");
		sb.append("     ON F.NU_UNIDADE = U.NU_UNIDADE");
		sb.append("    AND F.NU_NATURAL = U.NU_NATURAL");
		sb.append("    and Fl.NU_UNIDADE = U.NU_UNIDADE");
		sb.append("    AND Fl.NU_NATURAL = U.NU_NATURAL");
		sb.append("  INNER JOIN AVSSM001.AVSVW001_UNIDADE U2");
		sb.append("     ON Fl.NU_UNIDADE = U2.NU_UNIDADE");
		sb.append("    AND Fl.NU_NATURAL = U2.NU_NATURAL");
		
		return sb.toString();
		
	}

	/**
	 * Retorna o nome da tabela.
	 * @return String
	 */
	private String getNomeTabela() {
		return this.getNomeSchema() + "AVSTB012_USUARIO";
	}
}