package br.gov.caixa.siavs.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.util.Data;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.vo.FuncionarioCaixaVO;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.ServicoVO;
import br.gov.caixa.siavs.vo.UnidadeVO;

/**
 * <b>Title:</b> FuncionarioCaixaDAO <br>
 * <b>Description:</b> Classe que persiste o funcionário caixa. <br>
 * <b>Company:</b> Spread
 *
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 14/06/2013$
 */
public class FuncionarioCaixaDAO extends AbstractDAOSIAVS<FuncionarioCaixaVO> {

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlConsultar
	 */
	@Override
	protected String getSqlConsultar(FuncionarioCaixaVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
			// Chave primária
			if (Util.notEmpty(vo.getNuMatricula())) {
				sql.append(" AND F.NU_MATRICULA = cast( ? as integer)");
			}
		}
		sql.append(" ORDER BY F.NU_MATRICULA");

		return sql.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosConsultar
	 */
	@Override
	protected void preencherParametrosConsultar(FuncionarioCaixaVO vo) throws SQLException {
		if (vo != null) {
			// Chave primária
			if (Util.notEmpty(vo.getNuMatricula())) {
				this.setString(vo.getNuMatricula());
			}
		}
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOConsultar
	 */
	@Override
	protected FuncionarioCaixaVO preencherVOConsultar() throws SQLException {
		FuncionarioCaixaVO vo = new FuncionarioCaixaVO();

		vo.setNuMatricula(this.getString("NU_MATRICULA"));
		vo.setNoFuncionario(this.getString("NO_EMPREGADO"));
		vo.setNuTipoFuncao(this.getInteger("NU_TIPO_FUNCAO"));
		vo.setUnidade(new UnidadeVO(this.getInteger("NU_UNIDADE"), this.getInteger("NU_NATURAL")));
		vo.getUnidade().setNoUnidade(this.getString("NO_UNIDADE"));
		vo.getUnidade().setSgUnidade(this.getString("SG_UNIDADE"));
		vo.getUnidade().setDeEmail(this.getString("DE_EMAIL"));
		vo.getUnidade().setNuTipoUnidade(this.getInteger("NU_TIPO_UNIDADE"));
		vo.getUnidade().setSgTipoUnidade(this.getString("SG_TIPO_UNIDADE"));

		return vo;
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlListar
	 */
	@Override
	protected String getSqlListar(FuncionarioCaixaVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		sql.append(" ORDER BY NU_MATRICULA");

		return sql.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosListar
	 */
	@Override
	protected void preencherParametrosListar(FuncionarioCaixaVO vo) throws SQLException {
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOListar
	 */
	@Override
	protected FuncionarioCaixaVO preencherVOListar() throws SQLException {
		return this.preencherVOConsultar();
	}

// ***********************************************************************************************************************************

	/**
	 * Retorna o sql padrão de select.
	 * @return String
	 */
	private String getSqlSelect() {


		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT F.NU_MATRICULA, ");
		sb.append("        F.NO_EMPREGADO, ");
		sb.append("        F.NU_TIPO_FUNCAO, ");
		sb.append("        F.NU_UNIDADE, ");
		sb.append("        F.NU_NATURAL, ");
		sb.append("        U.NO_UNIDADE, ");
		sb.append("        U.SG_UNIDADE, ");
		sb.append("        U.DE_EMAIL,");
		sb.append("        tp.nu_tipo_unidade,");
		sb.append("        tp.sg_tipo_unidade, ");
		sb.append("        tp.de_tipo_unidade ");
		sb.append("   FROM avssm001.AVSVW002_FUNCIONARIO_CAIXA F, ");
		sb.append("        avssm001.AVSVW001_UNIDADE U,");
		sb.append("        avssm001.avsvw005_tp_unidade TP        ");
		sb.append("  WHERE F.NU_UNIDADE = U.NU_UNIDADE ");
		sb.append("    AND F.NU_NATURAL = U.NU_NATURAL");
		sb.append("    and u.nu_tp_unidade_u21 = tp.nu_tipo_unidade");

		return sb.toString();
	}

	/**
	 * Retorna o nome da tabela.
	 * @return String
	 */
	private String getNomeTabela() {
		return this.getNomeSchema() + "AVSVW002_FUNCIONARIO_CAIXA";
	}

	/**
	 * Lista os funcionarios vinculados ao grupo/serviço
	 * @param grupoVO
	 * @param servicoVO
	 * @return List<FuncionarioCaixaVO>
	 * @throws SystemException
	 */
	public List<FuncionarioCaixaVO> listarFuncionarioGrupoServico(
			GrupoVO grupoVO, ServicoVO servicoVO) throws SystemException {
		try {
			StringBuilder sb = new StringBuilder()
			.append(" SELECT DISTINCT ")
			.append("		F.NU_MATRICULA, ")
			.append("		F.NO_EMPREGADO, ")
			.append("		F.NU_TIPO_FUNCAO, ")
			.append("		F.NU_UNIDADE, ")
			.append("		F.NU_NATURAL ")
			.append(" FROM " + this.getNomeTabela() + " F ")
			.append(" INNER JOIN " + this.getNomeSchema() + "AVSTB004_GRUPO_UNIDADE GU ")
			.append("     ON F.NU_UNIDADE = GU.NU_UNIDADE ")
			.append("     AND F.NU_NATURAL = GU.NU_NATURAL ");
			if (servicoVO != null && servicoVO.getNuServico() != null) {
				sb.append(" INNER JOIN " + this.getNomeSchema() + "AVSTB007_FNCRO_CAIXA_LOCAL FCL ")
				.append("     ON F.NU_MATRICULA::CHARACTER VARYING = FCL.NU_MATRICULA ")
				.append("     AND F.NU_UNIDADE = FCL.NU_UNIDADE ")
				.append("     AND F.NU_NATURAL = FCL.NU_NATURAL ")
				.append(" INNER JOIN " + this.getNomeSchema() + "AVSTB008_FNCRO_CAIXA_SEGMENTO FCS ")
				.append("     ON FCS.NU_FNCRO_CAIXA_LOCAL = FCL.NU_FNCRO_CAIXA_LOCAL ")
				.append(" INNER JOIN " + this.getNomeSchema() + "AVSTB006_SERVICO_SEGMENTO SS ")
				.append("     ON FCS.NU_SEGMENTO = SS.NU_SEGMENTO ");
			}
			sb.append(" WHERE GU.NU_GRUPO = ? ");
			if (servicoVO != null && servicoVO.getNuServico() != null) {
				sb.append("   AND SS.NU_SERVICO = ? ");
			}

			this.criarPreparedStatement(sb.toString());
			this.setLong(grupoVO.getNuGrupo());
			if (servicoVO != null && servicoVO.getNuServico() != null) {
				this.setLong(servicoVO.getNuServico());
			}
			this.executarConsulta();

			List<FuncionarioCaixaVO> lista = new ArrayList<FuncionarioCaixaVO>();
			while (this.percorrerResultSet()) {
				FuncionarioCaixaVO voTemp = new FuncionarioCaixaVO();
				voTemp.setNuMatricula(this.getString("NU_MATRICULA"));
				voTemp.setNoFuncionario(this.getString("NO_EMPREGADO"));
				voTemp.setNuTipoFuncao(this.getInteger("NU_TIPO_FUNCAO"));
				lista.add(voTemp);
			}
			return lista;
		} catch (SQLException e) {
			throw new SystemException("Erro ao listar funcionários do grupo/serviço.", e);
		} finally {
			this.fechar(null);
		}
	}

// ***********************************************************************************************************************************

	/**
	 * Lista os funcionários do grupo agência que deverão receber email de convocação de avaliação.
	 * @param data Date
	 * @param grupoVO GrupoVO
	 * @return List<FuncionarioCaixaVO>
	 * @throws SystemException
	 */
	public List<FuncionarioCaixaVO> listarFuncionariosConvocacaoAgencia(Date data, GrupoVO grupoVO) throws SystemException {
		try {
			StringBuilder sql = new StringBuilder()
			.append("SELECT ")
			.append("		F.NU_MATRICULA ")
			.append("FROM   " + this.getNomeSchema() + "AVSVW002_FUNCIONARIO_CAIXA F ")
			.append("       JOIN " + this.getNomeSchema() + "AVSVW001_UNIDADE U ")
			.append("         ON F.NU_UNIDADE = U.NU_UNIDADE ")
			.append("            AND F.NU_NATURAL = U.NU_NATURAL ")
			.append("       JOIN " + this.getNomeSchema() + "AVSTB004_GRUPO_UNIDADE GU ")
			.append("         ON U.NU_UNIDADE = GU.NU_UNIDADE ")
			.append("            AND U.NU_NATURAL = GU.NU_NATURAL ")
			.append("       JOIN " + this.getNomeSchema() + "AVSTB016_UNIDADE_SORTEIO US ")
			.append("         ON U.NU_UNIDADE = US.NU_UNIDADE ")
			.append("            AND U.NU_NATURAL = US.NU_NATURAL ")
			// Faz o vínculo com funcionário local para verificar se já foi avaliado no dia
			.append("       LEFT JOIN " + this.getNomeSchema() + "AVSTB007_FNCRO_CAIXA_LOCAL FL ")
			.append("              ON F.NU_MATRICULA::CHARACTER VARYING = FL.NU_MATRICULA ")
			.append("                 AND F.NU_UNIDADE = FL.NU_UNIDADE ")
			.append("                 AND F.NU_NATURAL = FL.NU_NATURAL ")
			.append("WHERE  1 = 1 ")
			.append("       AND GU.NU_GRUPO = ? ")
			.append("       AND TO_CHAR(US.DT_SORTEIO, 'DD/MM/YYYY') = ? ")
			// Se já avaliou no dia não é necessário carregar novamente
			.append("       AND FL.NU_FNCRO_CAIXA_LOCAL NOT IN(SELECT NU_FNCRO_CAIXA_LOCAL ")
			.append("                                          	FROM   " + this.getNomeSchema() + "AVSTB010_AVALIACAO ")
			.append("                                          	WHERE ")
			.append("           								TO_CHAR(DT_AVALIACAO, 'DD/MM/YYYY') = ? ")
			.append("           								) ")
			.append("ORDER  BY NU_MATRICULA ");

			this.criarPreparedStatement(sql.toString());

			this.setLong(grupoVO.getNuGrupo());
			this.setString(Data.formatar(data));
			this.setString(Data.formatar(data));

			this.executarConsulta();

			List<FuncionarioCaixaVO> lista = new LinkedList<FuncionarioCaixaVO>();
			while (this.percorrerResultSet()) {
				lista.add(new FuncionarioCaixaVO(this.getString("NU_MATRICULA")));
			}
			return lista;
		}
		catch (SQLException e) {
			throw new SystemException("Erro ao listar os funcionários para a convocação.", e);
		}
		finally {
			this.fechar(null);
		}
	}

	/**
	 * Lista todos os funcionários vinculados a um determinado grupo.
	 * @param grupoVO GrupoVO
	 * @return List<FuncionarioCaixaVO>
	 * @throws SystemException
	 */
	public List<FuncionarioCaixaVO> listarFuncionariosGrupo(GrupoVO grupoVO) throws SystemException {
		try {
			StringBuilder sql = new StringBuilder()
			.append("SELECT ")
			.append("		F.NU_MATRICULA ")
			.append("FROM   " + this.getNomeSchema() + "AVSVW002_FUNCIONARIO_CAIXA F ")
			.append("       JOIN " + this.getNomeSchema() + "AVSVW001_UNIDADE U ")
			.append("         ON F.NU_UNIDADE = U.NU_UNIDADE ")
			.append("            AND F.NU_NATURAL = U.NU_NATURAL ")
			.append("       JOIN " + this.getNomeSchema() + "AVSTB004_GRUPO_UNIDADE GU ")
			.append("         ON U.NU_UNIDADE = GU.NU_UNIDADE ")
			.append("            AND U.NU_NATURAL = GU.NU_NATURAL ")
			.append("WHERE  1 = 1 ")
			.append("       AND GU.NU_GRUPO = ? ")
			.append("ORDER  BY F.NU_MATRICULA ");

			this.criarPreparedStatement(sql.toString());

			this.setLong(grupoVO.getNuGrupo());

			this.executarConsulta();

			List<FuncionarioCaixaVO> lista = new LinkedList<FuncionarioCaixaVO>();
			while (this.percorrerResultSet()) {
				lista.add(new FuncionarioCaixaVO(this.getString("NU_MATRICULA")));
			}
			return lista;
		}
		catch (SQLException e) {
			throw new SystemException("Erro ao listar os funcionários do grupo.", e);
		}
		finally {
			this.fechar(null);
		}
	}

	public static void main(String[] args) {

		FuncionarioCaixaDAO dao = new FuncionarioCaixaDAO();

		StringBuilder sb = new StringBuilder()
		.append(" SELECT DISTINCT ")
		.append("		F.NU_MATRICULA, ")
		.append("		F.NO_EMPREGADO, ")
		.append("		F.NU_TIPO_FUNCAO, ")
		.append("		F.NU_UNIDADE, ")
		.append("		F.NU_NATURAL ")
		.append(" FROM " + dao.getNomeTabela() + " F ")
		.append(" INNER JOIN " + dao.getNomeSchema() + "AVSTB004_GRUPO_UNIDADE GU ")
		.append("     ON F.NU_UNIDADE = GU.NU_UNIDADE ")
		.append("     AND F.NU_NATURAL = GU.NU_NATURAL ");
//		if (servicoVO != null && servicoVO.getNuServico() != null) {
//			sb.append(" INNER JOIN " + dao.getNomeSchema() + "AVSTB007_FNCRO_CAIXA_LOCAL FCL ")
//			.append("     ON F.NU_MATRICULA::CHARACTER VARYING = FCL.NU_MATRICULA ")
//			.append("     AND F.NU_UNIDADE = FCL.NU_UNIDADE ")
//			.append("     AND F.NU_NATURAL = FCL.NU_NATURAL ")
//			.append(" INNER JOIN " + dao.getNomeSchema() + "AVSTB008_FNCRO_CAIXA_SEGMENTO FCS ")
//			.append("     ON FCS.NU_FNCRO_CAIXA_LOCAL = FCL.NU_FNCRO_CAIXA_LOCAL ")
//			.append(" INNER JOIN " + dao.getNomeSchema() + "AVSTB006_SERVICO_SEGMENTO SS ")
//			.append("     ON FCS.NU_SEGMENTO = SS.NU_SEGMENTO ");
//		}
		sb.append(" WHERE GU.NU_GRUPO = ? ");
//		if (servicoVO != null && servicoVO.getNuServico() != null) {
//			sb.append("   AND SS.NU_SERVICO = ? ");
//		}

		System.out.println(sb.toString());

	}
}