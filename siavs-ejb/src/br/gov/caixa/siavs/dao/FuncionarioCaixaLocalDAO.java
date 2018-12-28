package br.gov.caixa.siavs.dao;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.vo.FuncionarioCaixaLocalVO;
import br.gov.caixa.siavs.vo.FuncionarioCaixaVO;
import br.gov.caixa.siavs.vo.UnidadeVO;

/**
 * <b>Title:</b> FuncionarioCaixaLocalDAO <br>
 * <b>Description:</b> Classe que persiste o funcionário caixa local. <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 14/06/2013$
 */
public class FuncionarioCaixaLocalDAO extends AbstractDAOSIAVS<FuncionarioCaixaLocalVO> {

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getNomeSequence
	 */
	@Override
	protected String getNomeSequence() {
		return this.getNomeSchema() + "AVSSQ007_NU_FNCRO_CAIXA_LOCAL";
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#setIdentificador
	 */
	@Override
	protected void preencherIdentificador(FuncionarioCaixaLocalVO vo, Long identicador) {
		vo.setNuFncroCaixaLocal(identicador);
	}

	// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlIncluir
	 */
	@Override
	protected String getSqlIncluir(FuncionarioCaixaLocalVO vo) {
		return new StringBuilder().append("INSERT INTO " + this.getNomeTabela() + " (")
				.append("			 NU_UNIDADE, ").append("			 NU_NATURAL, ")
				.append("			 NU_MATRICULA").append(")	  VALUES (?, ?, ?)").toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosIncluir
	 */
	@Override
	protected void preencherParametrosIncluir(FuncionarioCaixaLocalVO vo) throws SQLException {
		this.setInteger(vo.getUnidade().getNuUnidade());
		this.setInteger(vo.getUnidade().getNuNatural());
		this.setString(vo.getFuncionarioCaixa().getNuMatricula());
	}

	// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlConsultar
	 */
	@Override
	protected String getSqlConsultar(FuncionarioCaixaLocalVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
			// Chave primária
			if (Util.notEmpty(vo.getNuFncroCaixaLocal())) {
				sql.append(" AND NU_FNCRO_CAIXA_LOCAL = ? ");
			}
			// Chave única
			else {
				if (vo.getUnidade() != null) {
					if (Util.notEmpty(vo.getUnidade().getNuUnidade())) {
						sql.append(" AND NU_UNIDADE = ? ");
					}
					if (Util.notEmpty(vo.getUnidade().getNuNatural())) {
						sql.append(" AND NU_NATURAL = ? ");
					}
				}
				if (vo.getFuncionarioCaixa() != null && Util.notEmpty(vo.getFuncionarioCaixa().getNuMatricula())) {
					sql.append(" AND NU_MATRICULA = ? ");
				}
			}
		}
		sql.append(" ORDER BY NU_FNCRO_CAIXA_LOCAL");

		return sql.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosConsultar
	 */
	@Override
	protected void preencherParametrosConsultar(FuncionarioCaixaLocalVO vo) throws SQLException {
		if (vo != null) {
			// Chave primária
			if (Util.notEmpty(vo.getNuFncroCaixaLocal())) {
				this.setLong(vo.getNuFncroCaixaLocal());
			}
			// Chave única
			else {
				if (vo.getUnidade() != null) {
					if (Util.notEmpty(vo.getUnidade().getNuUnidade())) {
						this.setInteger(vo.getUnidade().getNuUnidade());
					}
					if (Util.notEmpty(vo.getUnidade().getNuNatural())) {
						this.setInteger(vo.getUnidade().getNuNatural());
					}
				}
				if (vo.getFuncionarioCaixa() != null && Util.notEmpty(vo.getFuncionarioCaixa().getNuMatricula())) {
					this.setString(vo.getFuncionarioCaixa().getNuMatricula());
				}
			}
		}
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOConsultar
	 */
	@Override
	protected FuncionarioCaixaLocalVO preencherVOConsultar() throws SQLException {
		FuncionarioCaixaLocalVO vo = new FuncionarioCaixaLocalVO();
		vo.setNuFncroCaixaLocal(this.getLong("NU_FNCRO_CAIXA_LOCAL"));
		vo.setUnidade(new UnidadeVO(this.getInteger("NU_UNIDADE"), this.getInteger("NU_NATURAL")));
		vo.setFuncionarioCaixa(new FuncionarioCaixaVO(this.getString("NU_MATRICULA")));
		vo.getFuncionarioCaixa().setUnidade(vo.getUnidade());
		return vo;
	}

	// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlListar
	 */
	@Override
	protected String getSqlListar(FuncionarioCaixaLocalVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
			if (vo.getFuncionarioCaixa() != null && Util.notEmpty(vo.getFuncionarioCaixa().getNuMatricula())) {
				sql.append(" AND " + this.getNomeSchema() + "NORMALIZAR(F.NU_MATRICULA) = " + this.getNomeSchema()
						+ "NORMALIZAR(?) ");
			}
		}

		sql.append(" ORDER BY NU_FNCRO_CAIXA_LOCAL");

		return sql.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosListar
	 */
	@Override
	protected void preencherParametrosListar(FuncionarioCaixaLocalVO vo) throws SQLException {
		if (vo != null) {
			if (vo.getFuncionarioCaixa() != null && Util.notEmpty(vo.getFuncionarioCaixa().getNuMatricula())) {
				this.setString(vo.getFuncionarioCaixa().getNuMatricula());
			}
		}
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOListar
	 */
	@Override
	protected FuncionarioCaixaLocalVO preencherVOListar() throws SQLException {
		return this.preencherVOConsultar();
	}

	// ***********************************************************************************************************************************

	/**
	 * Retorna o sql padrão de select.
	 * 
	 * @return String
	 */
	private String getSqlSelect() {
		return new StringBuilder().append("SELECT ").append("		NU_FNCRO_CAIXA_LOCAL, ")
				.append("		NU_UNIDADE, ").append("		NU_NATURAL, ").append("		NU_MATRICULA")
				.append("  FROM " + this.getNomeTabela() + " ").append("WHERE	1 = 1 ").toString();
	}

	/**
	 * Retorna o nome da tabela.
	 * 
	 * @return String
	 */
	private String getNomeTabela() {
		return this.getNomeSchema() + "AVSTB007_FNCRO_CAIXA_LOCAL";
	}

	// ***********************************************************************************************************************************

	/**
	 * Lista os funcionários da caixa juntamente com as informações dos
	 * funcionários locais.
	 * 
	 * @param vo
	 *            FuncionarioCaixaLocalVO
	 * @return List<FuncionarioCaixaLocalVO>
	 * @throws SystemException
	 */
	public List<FuncionarioCaixaLocalVO> listarTodos(FuncionarioCaixaLocalVO vo) throws SystemException {
		try {
			StringBuilder sql = new StringBuilder().append("SELECT DISTINCT ").append("		F.NU_MATRICULA, ")
					.append("       F.NU_UNIDADE, ").append("       F.NU_NATURAL, ").append("       F.NO_EMPREGADO, ")
					.append("		F.NU_TIPO_FUNCAO, ").append("       U.NO_UNIDADE, ")
					.append("       FL.NU_FNCRO_CAIXA_LOCAL ")
					.append("FROM   " + this.getNomeSchema() + "AVSVW002_FUNCIONARIO_CAIXA F ")
					.append("       JOIN " + this.getNomeSchema() + "AVSVW001_UNIDADE U ")
					.append("         ON F.NU_UNIDADE = U.NU_UNIDADE ")
					.append("            AND F.NU_NATURAL = U.NU_NATURAL ")
					.append("       LEFT JOIN " + this.getNomeTabela() + " FL ")
					.append("              ON F.NU_MATRICULA::CHARACTER VARYING = FL.NU_MATRICULA ")
					.append("                 AND F.NU_UNIDADE = FL.NU_UNIDADE ")
					.append("                 AND F.NU_NATURAL = FL.NU_NATURAL ").append("WHERE  1 = 1");

			if (vo != null) {
				if (vo.getUnidade() != null) {
					if (Util.notEmpty(vo.getUnidade().getNuUnidade())) {
						sql.append(" AND F.NU_UNIDADE = ? ");
					}
					if (Util.notEmpty(vo.getUnidade().getNuNatural())) {
						sql.append(" AND F.NU_NATURAL = ? ");
					}
				}
				if (vo.getFuncionarioCaixa() != null && Util.notEmpty(vo.getFuncionarioCaixa().getNuMatricula())) {
					sql.append(" AND F.NU_MATRICULA = ? ");
				}
			}

			sql.append("ORDER BY NO_EMPREGADO ");

			this.criarPreparedStatement(sql.toString());

			if (vo != null) {
				if (vo.getUnidade() != null) {
					if (Util.notEmpty(vo.getUnidade().getNuUnidade())) {
						this.setInteger(vo.getUnidade().getNuUnidade());
					}
					if (Util.notEmpty(vo.getUnidade().getNuNatural())) {
						this.setInteger(vo.getUnidade().getNuNatural());
					}
				}

				if (vo.getFuncionarioCaixa() != null && Util.notEmpty(vo.getFuncionarioCaixa().getNuMatricula())) {
					this.setInteger(Integer.parseInt(vo.getFuncionarioCaixa().getNuMatricula()));
				}
			}

			this.executarConsulta();

			List<FuncionarioCaixaLocalVO> listaVO = new LinkedList<FuncionarioCaixaLocalVO>();

			while (this.percorrerResultSet()) {
				FuncionarioCaixaLocalVO voTemp = this.preencherVOConsultar();
				listaVO.add(voTemp);

				voTemp.getFuncionarioCaixa().setNoFuncionario(this.getString("NO_EMPREGADO"));
				voTemp.getFuncionarioCaixa().setNuTipoFuncao(this.getInteger("NU_TIPO_FUNCAO"));
				voTemp.getUnidade().setNoUnidade(this.getString("NO_UNIDADE"));
			}

			return listaVO;
		} catch (SQLException e) {
			throw new SystemException("Erro ao recuperar o primeiro ano da avaliação.", e);
		} finally {
			this.fechar(null);
		}
	}

	public void criarNovo(FuncionarioCaixaVO vo) throws SystemException {

		StringBuilder sb = new StringBuilder();

		sb.append(" INSERT INTO avssm001.avstb007_fncro_caixa_local(nu_unidade, nu_natural, nu_matricula)");
		sb.append("      select nu_unidade_u24, nu_natural_u24, b.nu_matricula ");
		sb.append("        from icosm001.icotbh01_empro_cxa as b");
		sb.append("        left join avssm001.avstb007_fncro_caixa_local as c on c.nu_matricula::int = b.nu_matricula");
		sb.append("         and c.nu_unidade = b.nu_unidade_u24 and nu_natural_u24 = c.nu_natural");
		sb.append("       where b.nu_matricula = cast (? as int) ");
		sb.append(" 		and nu_fncro_caixa_local is null");

		try {

			this.criarPreparedStatement(sb.toString());

			this.setString(vo.getNuMatricula());
			
			this.getPreparedStatement().executeUpdate();
			
		} catch (SQLException e) {
			throw new SystemException("Erro ao cadastrar o funcionario local.", e);
		} finally {
			this.fechar(null);
		}
	}
}
