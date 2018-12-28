package br.gov.caixa.siavs.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.to.UnidadeVinculadoraTO;
import br.gov.caixa.siavs.vo.GrupoUnidadeVO;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.UnidadeVO;

/**
 * <b>Title:</b> GrupoUnidadeDAO <br>
 * <b>Description:</b> Classe que persiste o grupoUnidade. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 14/06/2013$
 */
public class GrupoUnidadeDAO extends AbstractDAOSIAVS <GrupoUnidadeVO> {

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getNomeSequence
	 */
	@Override
	protected String getNomeSequence() {
		return this.getNomeSchema() + "AVSSQ004_NU_GRUPO_UNIDADE";
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#setIdentificador
	 */
	@Override
	protected void preencherIdentificador(GrupoUnidadeVO vo, Long identicador) {
		vo.setNuGrupoUnidade(identicador);
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlIncluir
	 */
	@Override
	protected String getSqlIncluir(GrupoUnidadeVO vo) {
		return new StringBuilder()
			.append("INSERT INTO " + this.getNomeTabela() + " (")
			.append("			 DT_INCLUSAO, ")
			.append("			 NU_GRUPO, ")
			.append("			 NU_UNIDADE, ")
			.append("			 NU_NATURAL")
			.append(")	  VALUES (?, ?, ?, ?)")
			.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosIncluir
	 */
	@Override
	protected void preencherParametrosIncluir(GrupoUnidadeVO vo) throws SQLException {
		this.setDate(vo.getDtInclusao());
		this.setLong(vo.getGrupo().getNuGrupo());
		this.setInteger(vo.getUnidade().getNuUnidade());
		this.setInteger(vo.getUnidade().getNuNatural());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlAlterar
	 */
	@Override
	protected String getSqlAlterar(GrupoUnidadeVO vo) {
		return new StringBuilder()
			.append("UPDATE " + this.getNomeTabela() + " SET ")
			.append("       DT_INCLUSAO = ?, ")
			.append("       NU_GRUPO = ?, ")
			.append("       NU_UNIDADE = ?, ")
			.append("       NU_NATURAL = ?")
			.append(" WHERE ")
			.append(" 		NU_GRUPO_UNIDADE = ?")
			.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosAlterar
	 */
	@Override
	protected void preencherParametrosAlterar(GrupoUnidadeVO vo) throws SQLException {
		this.setDate(vo.getDtInclusao());
		this.setLong(vo.getGrupo().getNuGrupo());
		this.setInteger(vo.getUnidade().getNuUnidade());
		this.setInteger(vo.getUnidade().getNuNatural());
		this.setLong(vo.getNuGrupoUnidade());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlExcluir
	 */
	@Override
	protected String getSqlExcluir(GrupoUnidadeVO vo) {
		StringBuilder sql = new StringBuilder("DELETE FROM " + this.getNomeTabela() + " WHERE 1 = 1 ");
		
		// Chave primária
		if (Util.notEmpty(vo.getNuGrupoUnidade())) {
			sql.append(" AND NU_GRUPO_UNIDADE = ? ");
		}
		else {
			// Chave única
			if (vo.getGrupo() != null && Util.notEmpty(vo.getGrupo().getNuGrupo())) {
				sql.append(" AND NU_GRUPO = ? ");
			}
			if (vo.getUnidade() != null) {
				if (Util.notEmpty(vo.getUnidade().getNuUnidade())) {
					sql.append(" AND NU_UNIDADE = ? ");
				}
				if (Util.notEmpty(vo.getUnidade().getNuNatural())) {
					sql.append(" AND NU_NATURAL = ? ");
				}				
			}
		}
		
		return sql.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosExcluir
	 */
	@Override
	protected void preencherParametrosExcluir(GrupoUnidadeVO vo) throws SQLException {
		// Chave primária
		if (Util.notEmpty(vo.getNuGrupoUnidade())) {
			this.setLong(vo.getNuGrupoUnidade());
		}
		else {
			// Chave única
			if (vo.getGrupo() != null && Util.notEmpty(vo.getGrupo().getNuGrupo())) {
				this.setLong(vo.getGrupo().getNuGrupo());
			}
			if (vo.getUnidade() != null) {
				if (Util.notEmpty(vo.getUnidade().getNuUnidade())) {
					this.setInteger(vo.getUnidade().getNuUnidade());
				}
				if (Util.notEmpty(vo.getUnidade().getNuNatural())) {
					this.setInteger(vo.getUnidade().getNuNatural());
				}				
			}
		}
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlConsultar
	 */
	@Override
	protected String getSqlConsultar(GrupoUnidadeVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
			// Chave primária
			if (Util.notEmpty(vo.getNuGrupoUnidade())) {
				sql.append(" AND GU.NU_GRUPO_UNIDADE = ? ");
			}
			// Chave única
			else if (vo.getUnidade() != null) {
				if (Util.notEmpty(vo.getUnidade().getNuUnidade())) {
					sql.append(" AND GU.NU_UNIDADE = ? ");
				}
				if (Util.notEmpty(vo.getUnidade().getNuNatural())) {
					sql.append(" AND GU.NU_NATURAL = ? ");
				}				
			}
			if(vo.getGrupo() != null && vo.getGrupo().getIcAtivo() != null){
				sql.append(" AND G.IC_ATIVO = ? ");
			}
		}
		sql.append(" ORDER BY GU.NU_GRUPO_UNIDADE");

		return sql.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosConsultar
	 */
	@Override
	protected void preencherParametrosConsultar(GrupoUnidadeVO vo) throws SQLException {
		if (vo != null) {
			// Chave primária
			if (Util.notEmpty(vo.getNuGrupoUnidade())) {
				this.setLong(vo.getNuGrupoUnidade());
			}
			// Chave única
			else if (vo.getUnidade() != null) {
				if (Util.notEmpty(vo.getUnidade().getNuUnidade())) {
					this.setInteger(vo.getUnidade().getNuUnidade());
				}
				if (Util.notEmpty(vo.getUnidade().getNuNatural())) {
					this.setInteger(vo.getUnidade().getNuNatural());
				}	
			}
			if(vo.getGrupo() != null && vo.getGrupo().getIcAtivo() != null){
				this.setBoolean(vo.getGrupo().getIcAtivo());
			}
		}	
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOConsultar
	 */
	@Override
	protected GrupoUnidadeVO preencherVOConsultar() throws SQLException {
		GrupoUnidadeVO vo = new GrupoUnidadeVO();
		vo.setNuGrupoUnidade(this.getLong("NU_GRUPO_UNIDADE"));
		vo.setDtInclusao(this.getDate("DT_INCLUSAO"));
		vo.setGrupo(new GrupoVO(this.getLong("NU_GRUPO")));
		vo.getGrupo().setNoGrupo(this.getString("NO_GRUPO"));
		vo.getGrupo().setIcAgencia(this.getBoolean("IC_AGENCIA"));
		vo.setUnidade(new UnidadeVO(this.getInteger("NU_UNIDADE"), this.getInteger("NU_NATURAL")));
		return vo;
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlListar
	 */
	@Override
	protected String getSqlListar(GrupoUnidadeVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());
		sql.append(" ORDER BY GU.NU_GRUPO_UNIDADE");
		return sql.toString();
	}
	
	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOListar
	 */
	@Override
	protected GrupoUnidadeVO preencherVOListar() throws SQLException {
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
		.append("		GU.NU_GRUPO_UNIDADE, ")
		.append("		GU.DT_INCLUSAO, ")
		.append("		GU.NU_GRUPO, ")
		.append("		GU.NU_UNIDADE, ")
		.append("		GU.NU_NATURAL, ")
		.append("		G.NO_GRUPO, ")
		.append("		G.IC_AGENCIA ")
		.append("  FROM " + this.getNomeTabela() + " GU, ")
		.append("       " + this.getNomeSchema() + "AVSTB001_GRUPO G ")		
		.append("WHERE	1 = 1 ")
		.append("       AND GU.NU_GRUPO = G.NU_GRUPO ")
		.toString();
	}

	/**
	 * Retorna o nome da tabela.
	 * @return String
	 */
	private String getNomeTabela() {
		return this.getNomeSchema() + "AVSTB004_GRUPO_UNIDADE";
	}

// ***********************************************************************************************************************************

	/**
	 * Lista o vínculo de unidade-grupo com a unidade vinculadora
	 * @param grupoVO
	 * @return List<UnidadeNodeTO>
	 * @throws SystemException
	 */
	public List<UnidadeVinculadoraTO> listarUnidadeVinculadora(GrupoVO grupoVO) throws SystemException {
		try {
			StringBuilder sql = new StringBuilder()
			.append(" SELECT  ")
			.append("   GU.NU_GRUPO_UNIDADE, ")
			.append("   GU.NU_GRUPO, ")
			.append("   GU.DT_INCLUSAO, ")
			.append("   U.NU_UNIDADE, ")
			.append("   U.NU_NATURAL,  ")
			.append("   U.NO_UNIDADE, ")
			.append("   UV.NU_UNIDADE_VINCULADORA, ")
			.append("   UV.NU_NATURAL_VINCULADORA ")
			.append(" FROM "+ this.getNomeSchema() + "AVSTB004_GRUPO_UNIDADE GU ")
			.append(" INNER JOIN "+ this.getNomeSchema() + "AVSVW001_UNIDADE U ")
			.append("   ON GU.NU_UNIDADE = U.NU_UNIDADE ")
			.append("   AND GU.NU_NATURAL = U.NU_NATURAL ")
			.append(" INNER JOIN "+ this.getNomeSchema() + "AVSVW003_UNIDADE_VINCULADA UV ")
			.append("   ON GU.NU_UNIDADE = UV.NU_UNIDADE_VINCULADA ")
			.append("   AND GU.NU_NATURAL = UV.NU_NATURAL_VINCULADA ")
			.append(" WHERE GU.NU_GRUPO = ? ")
			.append("   AND UV.NU_TP_VINCULO = 1 ") // hierárquico
			.append(" ORDER BY U.NU_UNIDADE ");
			
			criarPreparedStatement(sql.toString());
			this.setLong(grupoVO.getNuGrupo());
			executarConsulta();
			
			List<UnidadeVinculadoraTO> unidades = new ArrayList<UnidadeVinculadoraTO>();
			while (percorrerResultSet()) {
				UnidadeVinculadoraTO to = new UnidadeVinculadoraTO();
				// GrupoUnidadeVO
				GrupoUnidadeVO grupoUnidadeVO = new GrupoUnidadeVO();
				grupoUnidadeVO.setNuGrupoUnidade(this.getLong("NU_GRUPO_UNIDADE"));
				grupoUnidadeVO.setDtInclusao(this.getDate("DT_INCLUSAO"));
				grupoUnidadeVO.setGrupo(new GrupoVO());
				grupoUnidadeVO.getGrupo().setNuGrupo(this.getLong("NU_GRUPO"));
				grupoUnidadeVO.setUnidade(new UnidadeVO());
				grupoUnidadeVO.getUnidade().setNuUnidade(this.getInteger("NU_UNIDADE"));
				grupoUnidadeVO.getUnidade().setNuNatural(this.getInteger("NU_NATURAL"));
				grupoUnidadeVO.getUnidade().setNoUnidade(this.getString("NO_UNIDADE"));
				to.setGrupoUnidadeVO(grupoUnidadeVO);
				// UnidadeVO (Vinculadora)
				UnidadeVO unidadeVO = new UnidadeVO();
				unidadeVO.setNuUnidade(this.getInteger("NU_UNIDADE_VINCULADORA"));
				unidadeVO.setNuNatural(this.getInteger("NU_NATURAL_VINCULADORA"));
				to.setUnidadeVinculadora(unidadeVO);
				
				unidades.add(to);
			}
			
			return unidades;
		} catch (SQLException e) {
			throw new SystemException("Problema ao buscar unidades pendentes de designação.", e);
		} finally {
			this.fechar(null);
		}
	}
}
