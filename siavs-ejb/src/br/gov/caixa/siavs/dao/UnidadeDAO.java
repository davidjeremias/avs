package br.gov.caixa.siavs.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.util.Data;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.global.DominioSIAVS.TipoUnidade;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.UnidadeVO;

/**
 * <b>Title:</b> UnidadeDAO <br>
 * <b>Description:</b> Classe que persiste o unidade. <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 14/06/2013$
 */
public class UnidadeDAO extends AbstractDAOSIAVS<UnidadeVO> {

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlConsultar
	 */
	@Override
	protected String getSqlConsultar(UnidadeVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
			// Chave primária
			if (Util.notEmpty(vo.getNuUnidade())) {
				sql.append(" AND NU_UNIDADE = ? ");
			}
			if (Util.notEmpty(vo.getNuNatural())) {
				sql.append(" AND NU_NATURAL = ? ");
			}
		}
		sql.append(" ORDER BY NU_UNIDADE, NU_NATURAL");

		return sql.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosConsultar
	 */
	@Override
	protected void preencherParametrosConsultar(UnidadeVO vo) throws SQLException {
		if (vo != null) {
			// Chave primária
			if (Util.notEmpty(vo.getNuUnidade())) {
				this.setInteger(vo.getNuUnidade());
			}
			if (Util.notEmpty(vo.getNuNatural())) {
				this.setInteger(vo.getNuNatural());
			}
		}
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOConsultar
	 */
	@Override
	protected UnidadeVO preencherVOConsultar() throws SQLException {
		UnidadeVO vo = new UnidadeVO();
		vo.setNuUnidade(this.getInteger("NU_UNIDADE"));
		vo.setNuNatural(this.getInteger("NU_NATURAL"));
		vo.setNoUnidade(this.getString("NO_UNIDADE"));
		vo.setSgUf(this.getString("SG_UF"));
		vo.setNuTelefone(this.getString("NU_TELEFONE"));
		vo.setSgCgc(this.getString("SG_CGC"));
		vo.setDeEmail(this.getString("DE_EMAIL"));
		vo.setSgUnidade(this.getString("SG_UNIDADE"));
		return vo;
	}

	// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlListar
	 */
	@Override
	protected String getSqlListar(UnidadeVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
			if (Util.notEmpty(vo.getSgUnidade())) {
				sql.append(" AND SG_UNIDADE = ? ");
			}
		}
		sql.append(" ORDER BY NU_UNIDADE, NU_NATURAL");

		return sql.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosListar
	 */
	@Override
	protected void preencherParametrosListar(UnidadeVO vo) throws SQLException {
		if (vo != null) {
			if (Util.notEmpty(vo.getSgUnidade())) {
				this.setString(vo.getSgUnidade());
			}
		}
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOListar
	 */
	@Override
	protected UnidadeVO preencherVOListar() throws SQLException {
		return this.preencherVOConsultar();
	}

	// ***********************************************************************************************************************************

	/**
	 * Retorna o sql padrão de select.
	 * 
	 * @return String
	 */
	private String getSqlSelect() {
		return new StringBuilder().append("SELECT ").append("		NU_UNIDADE, ").append("		NU_NATURAL, ")
				.append("		NO_UNIDADE, ").append("		SG_UF, ").append("		NU_TELEFONE, ")
				.append("		SG_CGC, ").append("		DE_EMAIL, ").append("		SG_UNIDADE ")
				.append("  FROM " + this.getNomeTabela() + " ").append("WHERE	1 = 1 ").toString();
	}

	/**
	 * Retorna o nome da tabela.
	 * 
	 * @return String
	 */
	private String getNomeTabela() {
		return this.getNomeSchema() + "AVSVW001_UNIDADE";
	}

	// ***********************************************************************************************************************************

	/**
	 * Lista as unidades pendentes de designação.
	 * 
	 * @return List<UnidadeVO>
	 * @throws SystemException
	 */
	public List<UnidadeVO> listarUnidadePendenciaDesignacao(Date dtSorteio) throws SystemException {
		try {
			StringBuilder sql = new StringBuilder().append(" SELECT  ").append(" 	UN.NU_UNIDADE,  ")
					.append(" 	UN.NU_NATURAL,  ").append(" 	UN.NO_UNIDADE,  ").append(" 	UN.SG_UF,  ")
					.append(" 	UN.NU_TELEFONE,  ").append(" 	UN.SG_CGC,  ").append(" 	UN.DE_EMAIL,  ")
					.append(" 	UN.SG_UNIDADE ").append(" FROM AVSSM001.AVSVW001_UNIDADE UN ")
					.append(" INNER JOIN AVSSM001.AVSTB016_UNIDADE_SORTEIO US ")
					.append(" 	ON UN.NU_UNIDADE = US.NU_UNIDADE ").append(" 	AND UN.NU_NATURAL = US.NU_NATURAL ")
					.append(" WHERE UN.NU_UNIDADE NOT IN ( ").append(" 	SELECT DISTINCT FC.NU_UNIDADE ")
					.append(" 	FROM AVSSM001.AVSTB007_FNCRO_CAIXA_LOCAL FCL ")
					.append(" 	INNER JOIN AVSSM001.AVSTB008_FNCRO_CAIXA_SEGMENTO FCS ")
					.append(" 	ON FCL.NU_FNCRO_CAIXA_LOCAL = FCS.NU_FNCRO_CAIXA_LOCAL ")
					.append(" 	INNER JOIN AVSSM001.AVSVW002_FUNCIONARIO_CAIXA FC ")
					.append(" 	ON FCL.NU_MATRICULA = FC.NU_MATRICULA::CHARACTER VARYING ")
					.append(" 	AND FCL.NU_UNIDADE = FC.NU_UNIDADE ").append(" 	AND FCL.NU_NATURAL = FC.NU_NATURAL ")
					.append("       ) ").append("   AND TO_CHAR(US.DT_SORTEIO, 'DD/MM/YYYY') = ? ");

			this.criarPreparedStatement(sql.toString());
			this.setString(Data.formatar(dtSorteio));
			this.executarConsulta();

			List<UnidadeVO> unidades = new ArrayList<UnidadeVO>();
			while (percorrerResultSet()) {
				UnidadeVO vo = new UnidadeVO();
				vo.setNuUnidade(this.getInteger("NU_UNIDADE"));
				vo.setNuNatural(this.getInteger("NU_NATURAL"));
				vo.setNoUnidade(this.getString("NO_UNIDADE"));
				vo.setSgUf(this.getString("SG_UF"));
				vo.setNuTelefone(this.getString("NU_TELEFONE"));
				vo.setSgCgc(this.getString("SG_CGC"));
				vo.setDeEmail(this.getString("DE_EMAIL"));
				vo.setSgUnidade(this.getString("SG_UNIDADE"));

				unidades.add(vo);
			}

			return unidades;
		} catch (SQLException e) {
			throw new SystemException("Problema ao buscar unidades pendentes de designação.", e);
		} finally {
			this.fechar(null);
		}
	}

	/**
	 * Lista as unidades vinculadas a partir da unidade vinculadora
	 * 
	 * @param vo
	 * @return List<UnidadeVO>
	 * @throws SystemException
	 */
	public List<UnidadeVO> listarUnidadeVinculada(UnidadeVO vo) throws SystemException {
		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT nu_unidade_vinculadora, nu_natural_vinculadora, ");
			sql.append("       nu_unidade_vinculada, nu_natural_vinculada, nu_tp_unidade, nu_tp_vinculo");
			sql.append("  from avssm001.avsvw003_unidade_vinculada");
			sql.append(" where nu_unidade_vinculadora = ?");
			sql.append("   and nu_natural_vinculadora = ?");
					
			criarPreparedStatement(sql.toString());
			this.setInteger(vo.getNuUnidade());
			this.setInteger(vo.getNuNatural());
			executarConsulta();

			List<UnidadeVO> unidades = new ArrayList<UnidadeVO>();
			while (percorrerResultSet()) {
				UnidadeVO voRetorno = new UnidadeVO();
				voRetorno.setNuUnidade(this.getInteger("NU_UNIDADE"));
				voRetorno.setNuNatural(this.getInteger("NU_NATURAL"));
				voRetorno.setNoUnidade(this.getString("NO_UNIDADE"));
				voRetorno.setSgUf(this.getString("SG_UF"));
				voRetorno.setNuTelefone(this.getString("NU_TELEFONE"));
				voRetorno.setSgCgc(this.getString("SG_CGC"));
				voRetorno.setDeEmail(this.getString("DE_EMAIL"));
				voRetorno.setSgUnidade(this.getString("SG_UNIDADE"));

				unidades.add(voRetorno);
			}

			return unidades;
		} catch (SQLException e) {
			throw new SystemException("Problema ao buscar unidades vinculadas.", e);
		} finally {
			this.fechar(null);
		}
	}

	/**
	 * Lista unidades não vinculadas ao grupo por estarem em outro grupo.
	 * 
	 * @param grupoVO
	 * @return List<UnidadeVO>
	 * @throws SystemException
	 */
	public List<UnidadeVO> listarUnidadeNaoVinculada(GrupoVO grupoVO) throws SystemException {
		try {
			StringBuilder sql = new StringBuilder().append(" SELECT   ").append("  	UN.NU_UNIDADE,  ")
					.append("  	G.NO_GRUPO  ").append(" FROM  ").append(" 	AVSSM001.AVSTB001_GRUPO G ")
					.append(" 	JOIN avssm001.avstb004_grupo_unidade GU ON G.nu_grupo = GU.nu_grupo ")
					.append(" 	JOIN avssm001.avsvw001_unidade UN ON GU.nu_unidade = UN.nu_unidade ").append(" where ")
					.append(" 	UN.nu_unidade IN (SELECT   ").append("  						UN.NU_UNIDADE  ")
					.append("  					FROM AVSSM001.AVSVW003_UNIDADE_VINCULADA UNV  ")
					.append("  						INNER JOIN AVSSM001.AVSVW001_UNIDADE UN  ON UNV.NU_UNIDADE_VINCULADA = UN.NU_UNIDADE ")
					.append("  						AND UNV.NU_NATURAL_VINCULADA = UN.NU_NATURAL  ")
					.append("  						INNER JOIN AVSSM001.AVSTB004_GRUPO_UNIDADE GU ON UNV.NU_UNIDADE_VINCULADORA = GU.NU_UNIDADE ")
					.append(" 						AND UNV.NU_NATURAL_VINCULADORA = GU.NU_NATURAL ")
					.append(" 						INNER JOIN AVSSM001.AVSTB001_GRUPO G ON GU.NU_GRUPO = G.NU_GRUPO ")
					.append("  					WHERE ").append(" 	   					GU.NU_GRUPO = ?  ")
					.append("    					AND UNV.NU_TP_VINCULO = 1 ") // hierárquico
					.append("    					AND UN.NU_UNIDADE IN (SELECT ")
					.append(" 												GU2.NU_UNIDADE ")
					.append(" 	 										  FROM ")
					.append("    											AVSSM001.AVSTB004_GRUPO_UNIDADE GU2	")
					.append(" 	 										  WHERE ")
					.append("    											GU2.NU_GRUPO <> GU.NU_GRUPO	")
					.append("    ) )");

			criarPreparedStatement(sql.toString());
			this.setLong(grupoVO.getNuGrupo());
			executarConsulta();

			List<UnidadeVO> unidades = new ArrayList<UnidadeVO>();
			while (percorrerResultSet()) {
				UnidadeVO voRetorno = new UnidadeVO();
				voRetorno.setNuUnidade(this.getInteger("NU_UNIDADE"));
				voRetorno.setNomeGrupo(this.getString("NO_GRUPO"));

				unidades.add(voRetorno);
			}
			return unidades;
		} catch (SQLException e) {
			throw new SystemException("Problema ao buscar unidades não vinculadas.", e);
		} finally {
			this.fechar(null);
		}
	}

	/**
	 * Lista as unidade de relacionamento ou de produção dependendo do tipo.
	 * 
	 * @param tipoUnidade
	 *            Tipo da unidade
	 * @return List<UnidadeVO>
	 * @throws SystemException
	 */
	public List<UnidadeVO> listarUnidadeRelProd(TipoUnidade tipoUnidade) throws SystemException {
		try {
			StringBuilder sql = new StringBuilder().append("SELECT U.NU_UNIDADE, ").append("       U.NU_NATURAL, ")
					.append("       U.NO_UNIDADE, ").append("       U.SG_UNIDADE, ").append("       U.SG_UF, ")
					.append("       U.SG_TIPO_UNIDADE ").append("FROM   " + this.getNomeTabela() + " U ")
					.append("       INNER JOIN " + this.getNomeSchema() + "AVSTB018_UNIDADE_REL_PROD RP ")
					.append("               ON RP.NU_UNIDADE = U.NU_UNIDADE ")
					.append("                  AND RP.NU_NATURAL = U.NU_NATURAL ").append("WHERE  RP.CL_TIPO = ? ");

			criarPreparedStatement(sql.toString());
			this.setString(tipoUnidade.getCodigo());

			this.executarConsulta();

			List<UnidadeVO> listaUnidade = new LinkedList<UnidadeVO>();

			while (this.percorrerResultSet()) {
				UnidadeVO vo = new UnidadeVO();
				vo.setNuUnidade(this.getInteger("NU_UNIDADE"));
				vo.setNuNatural(this.getInteger("NU_NATURAL"));
				vo.setNoUnidade(this.getString("NO_UNIDADE"));
				vo.setSgUnidade(this.getString("SG_UNIDADE"));
				vo.setSgUf(this.getString("SG_UF"));
				vo.setSgTipoUnidade(this.getString("SG_TIPO_UNIDADE"));

				listaUnidade.add(vo);
			}

			return listaUnidade;
		} catch (SQLException e) {
			throw new SystemException("Problema ao buscar lista de unidades de relacionamento ou produção.", e);
		} finally {
			this.fechar(null);
		}
	}

	public UnidadeVO consultarTipoUnidade(Integer unidade, Integer natural) throws SystemException {

		StringBuilder sql = new StringBuilder().append("SELECT U.NU_UNIDADE, ").append("       U.NU_NATURAL, ")
				.append("       U.NO_UNIDADE, ").append("       U.SG_UNIDADE, ").append("       U.SG_TIPO_UNIDADE, ")
				.append("       U.NU_TP_UNIDADE_U21 ").append("FROM   " + this.getNomeTabela() + " U ")
				.append("WHERE  1 = 1  ").append(" AND U.NU_UNIDADE = ").append(unidade).append(" AND U.NU_NATURAL = ")
				.append(natural);

		criarPreparedStatement(sql.toString());

		this.executarConsulta();

		UnidadeVO vo = new UnidadeVO();

		while (this.percorrerResultSet()) {
			try {
				vo.setNuUnidade(this.getInteger("NU_UNIDADE"));
				vo.setNuNatural(this.getInteger("NU_NATURAL"));
				vo.setNoUnidade(this.getString("NO_UNIDADE"));
				vo.setSgUnidade(this.getString("SG_UNIDADE"));
				vo.setSgTipoUnidade(this.getString("SG_TIPO_UNIDADE"));
				vo.setNuTipoUnidade(this.getInteger("NU_TP_UNIDADE_U21"));

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return vo;
	}
}