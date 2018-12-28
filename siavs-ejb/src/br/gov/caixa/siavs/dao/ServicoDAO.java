package br.gov.caixa.siavs.dao;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.vo.GrupoServicoVO;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.NoticiaVO;
import br.gov.caixa.siavs.vo.SegmentoVO;
import br.gov.caixa.siavs.vo.ServicoSegmentoVO;
import br.gov.caixa.siavs.vo.ServicoVO;
import br.gov.caixa.siavs.vo.UnidadeVO;

/**
 * <b>Title:</b> ServicoDAO <br>
 * <b>Description:</b> Classe que persiste o serviço. <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 14/06/2013$
 */
public class ServicoDAO extends AbstractDAOSIAVS<ServicoVO> {

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getNomeSequence
	 */
	@Override
	protected String getNomeSequence() {
		return this.getNomeSchema() + "AVSSQ002_NU_SERVICO";
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#setIdentificador
	 */
	@Override
	protected void preencherIdentificador(ServicoVO vo, Long identicador) {
		vo.setNuServico(identicador);
	}

	// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlIncluir
	 */
	@Override
	protected String getSqlIncluir(ServicoVO vo) {
		return new StringBuilder()
				.append("INSERT INTO " + this.getNomeTabela() + " (")
				.append("			 DE_SERVICO, ").append("			 NO_SISTEMA, ")
				.append("			 IC_ATIVO, ").append("			 IC_CORPORATIVO, ")
				.append("			 NU_UNIDADE_RELACIONAMENTO, ")
				.append("			 NU_NATURAL_RELACIONAMENTO, ")
				.append("			 NU_UNIDADE_PRODUCAO, ")
				.append("			 NU_NATURAL_PRODUCAO, ").append("			 IC_AGENCIA, ")
				.append("			 NO_GESTOR")
				.append(")	  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)").toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosIncluir
	 */
	@Override
	protected void preencherParametrosIncluir(ServicoVO vo) throws SQLException {
		this.setString(vo.getDeServico());
		this.setString(vo.getNoSistema());
		this.setBoolean(vo.getIcAtivo());
		this.setBoolean(vo.getIcCorporativo());
		this.setInteger(vo.getUnidadeRelacionamento() != null ? vo
				.getUnidadeRelacionamento().getNuUnidade() : null);
		this.setInteger(vo.getUnidadeRelacionamento() != null ? vo
				.getUnidadeRelacionamento().getNuNatural() : null);
		this.setInteger(vo.getUnidadeProducao() != null ? vo
				.getUnidadeProducao().getNuUnidade() : null);
		this.setInteger(vo.getUnidadeProducao() != null ? vo
				.getUnidadeProducao().getNuNatural() : null);
		this.setBoolean(vo.getIcAgencia());
		this.setString(vo.getNoGestor());
	}

	// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlAlterar
	 */
	@Override
	protected String getSqlAlterar(ServicoVO vo) {
		return new StringBuilder()
				.append("UPDATE " + this.getNomeTabela() + " SET ")
				.append("       DE_SERVICO = ?, ")
				.append("       NO_SISTEMA = ?, ")
				.append("       IC_ATIVO = ?, ")
				.append("       IC_CORPORATIVO = ?, ")
				.append("       NU_UNIDADE_RELACIONAMENTO = ?, ")
				.append("       NU_NATURAL_RELACIONAMENTO = ?, ")
				.append("       NU_UNIDADE_PRODUCAO = ?, ")
				.append("       NU_NATURAL_PRODUCAO = ?, ")
				.append("       IC_AGENCIA = ?, ")
				.append("       NO_GESTOR = ?").append(" WHERE ")
				.append(" 		NU_SERVICO = ?").toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosAlterar
	 */
	@Override
	protected void preencherParametrosAlterar(ServicoVO vo) throws SQLException {
		this.setString(vo.getDeServico());
		this.setString(vo.getNoSistema());
		this.setBoolean(vo.getIcAtivo());
		this.setBoolean(vo.getIcCorporativo());
		this.setInteger(vo.getUnidadeRelacionamento() != null ? vo
				.getUnidadeRelacionamento().getNuUnidade() : null);
		this.setInteger(vo.getUnidadeRelacionamento() != null ? vo
				.getUnidadeRelacionamento().getNuNatural() : null);
		this.setInteger(vo.getUnidadeProducao() != null ? vo
				.getUnidadeProducao().getNuUnidade() : null);
		this.setInteger(vo.getUnidadeProducao() != null ? vo
				.getUnidadeProducao().getNuNatural() : null);
		this.setBoolean(vo.getIcAgencia());
		this.setString(vo.getNoGestor());
		this.setLong(vo.getNuServico());
	}

	// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlExcluir
	 */
	@Override
	protected String getSqlExcluir(ServicoVO vo) {
		return new StringBuilder("DELETE FROM " + this.getNomeTabela()
				+ " WHERE NU_SERVICO = ?").toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosExcluir
	 */
	@Override
	protected void preencherParametrosExcluir(ServicoVO vo) throws SQLException {
		this.setLong(vo.getNuServico());
	}

	// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlConsultar
	 */
	@Override
	protected String getSqlConsultar(ServicoVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
			// Chave primária
			if (Util.notEmpty(vo.getNuServico())) {
				sql.append(" AND NU_SERVICO = ? ");
			}
			// Chave única
			else {
				if (Util.notEmpty(vo.getDeServico())) {
					sql.append(" AND " + this.getNomeSchema()
							+ "NORMALIZAR(DE_SERVICO) = "
							+ this.getNomeSchema() + "NORMALIZAR(?) ");
				}
			}
		}
		sql.append(" ORDER BY NU_SERVICO");

		return sql.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosConsultar
	 */
	@Override
	protected void preencherParametrosConsultar(ServicoVO vo)
			throws SQLException {
		if (vo != null) {
			// Chave primária
			if (Util.notEmpty(vo.getNuServico())) {
				this.setLong(vo.getNuServico());
			}
			// Chave única
			else {
				if (Util.notEmpty(vo.getDeServico())) {
					this.setString(vo.getDeServico());
				}
			}
		}
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOConsultar
	 */
	@Override
	protected ServicoVO preencherVOConsultar() throws SQLException {
		ServicoVO vo = new ServicoVO();
		vo.setNuServico(this.getLong("NU_SERVICO"));
		vo.setDeServico(this.getString("DE_SERVICO"));
		vo.setNoSistema(this.getString("NO_SISTEMA"));
		vo.setIcAtivo(this.getBoolean("IC_ATIVO"));
		vo.setIcCorporativo(this.getBoolean("IC_CORPORATIVO"));
		vo.setUnidadeRelacionamento(new UnidadeVO(this
				.getInteger("NU_UNIDADE_RELACIONAMENTO"), this
				.getInteger("NU_NATURAL_RELACIONAMENTO")));
		vo.setUnidadeProducao(new UnidadeVO(this
				.getInteger("NU_UNIDADE_PRODUCAO"), this
				.getInteger("NU_NATURAL_PRODUCAO")));
		vo.setIcAgencia(this.getBoolean("IC_AGENCIA"));
		vo.setNoGestor(this.getString("NO_GESTOR"));
		return vo;
	}

	// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlListar
	 */
	@Override
	protected String getSqlListar(ServicoVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
			if (Util.notEmpty(vo.getDeServico())) {
				sql.append(" AND " + this.getNomeSchema()
						+ "NORMALIZAR(DE_SERVICO) like '%' || "
						+ this.getNomeSchema() + "NORMALIZAR(?) || '%' ");
			}
			if (Util.notEmpty(vo.getNoSistema())) {
				sql.append(" AND " + this.getNomeSchema()
						+ "NORMALIZAR(NO_SISTEMA) like '%' || "
						+ this.getNomeSchema() + "NORMALIZAR(?) || '%' ");
			}
//			if (vo.getIcAtivo()) {
//				sql.append(" AND IC_ATIVO = ? ");
//			}
		}
		sql.append(" ORDER BY DE_SERVICO");

		return sql.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosListar
	 */
	@Override
	protected void preencherParametrosListar(ServicoVO vo) throws SQLException {
		if (vo != null) {
			if (Util.notEmpty(vo.getDeServico())) {
				this.setString(vo.getDeServico());
			}
			if (Util.notEmpty(vo.getNoSistema())) {
				this.setString(vo.getNoSistema());
			}
			if (Util.notEmpty(vo.getNoGestor())) {
				this.setString(vo.getNoGestor());
			}
//			if (vo.getIcAtivo()) {
//				this.setBoolean(vo.getIcAtivo());
//			}
		}
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOListar
	 */
	@Override
	protected ServicoVO preencherVOListar() throws SQLException {
		return this.preencherVOConsultar();
	}

	// ***********************************************************************************************************************************

	/**
	 * Retorna o sql padrão de select.
	 * 
	 * @return String
	 */
	private String getSqlSelect() {
		return new StringBuilder().append("SELECT ").append("		NU_SERVICO, ")
				.append("		DE_SERVICO, ").append("		NO_SISTEMA, ")
				.append("		IC_ATIVO, ").append("		IC_CORPORATIVO, ")
				.append("		NU_UNIDADE_RELACIONAMENTO, ")
				.append("		NU_NATURAL_RELACIONAMENTO, ")
				.append("		NU_UNIDADE_PRODUCAO, ")
				.append("		NU_NATURAL_PRODUCAO, ").append("		IC_AGENCIA, ")
				.append("		NO_GESTOR")
				.append("  FROM " + this.getNomeTabela() + " ")
				.append("WHERE	1 = 1 ").toString();
	}

	/**
	 * Retorna o nome da tabela.
	 * 
	 * @return String
	 */
	private String getNomeTabela() {
		return this.getNomeSchema() + "AVSTB002_SERVICO";
	}

	// ***********************************************************************************************************************************

	/**
	 * Lista os serviços que já tiveram avaliação para o grupo.
	 * 
	 * @param grupoVO
	 *            GrupoVO
	 * @return List<ServicoVO>
	 * @throws SystemException
	 */
	public List<ServicoVO> listarServicosAvaliados(GrupoVO grupoVO)
			throws SystemException {
		try {
			StringBuilder sql = new StringBuilder()
					.append("SELECT DISTINCT ")
					.append("		S.NU_SERVICO, ")
					.append("		S.DE_SERVICO ")
					.append("FROM   " + this.getNomeTabela() + " S, ")
					.append("       " + this.getNomeSchema()
							+ "AVSTB010_AVALIACAO A ").append("WHERE  1 = 1 ")
					.append("       AND S.NU_SERVICO = A.NU_SERVICO ")
					.append("       AND A.NU_GRUPO = ? ")
					.append(" ORDER BY S.DE_SERVICO");

			this.criarPreparedStatement(sql.toString());

			this.setLong(grupoVO.getNuGrupo());

			this.executarConsulta();

			List<ServicoVO> lista = new LinkedList<ServicoVO>();

			while (this.percorrerResultSet()) {
				ServicoVO voTemp = new ServicoVO(this.getLong("NU_SERVICO"));
				lista.add(voTemp);

				voTemp.setDeServico(this.getString("DE_SERVICO"));
			}

			return lista;
		} catch (SQLException e) {
			throw new SystemException(
					"Erro ao recuperar a lista de serviços avaliados pelo grupo.",
					e);
		} finally {
			this.fechar(null);
		}
	}

	/**
	 * 
	 * @param servicoVO
	 * @return
	 * @throws SystemException
	 */
	public List<ServicoSegmentoVO> listarSegmentosPorServico(ServicoVO servicoVO)
			throws SystemException {
		try {
			StringBuilder sql = new StringBuilder()
					.append("SELECT DISTINCT ")
					.append("		S.NU_SERVICO, ")
					.append("		S.NU_SEGMENTO ")
					.append("FROM   " + this.getNomeSchema()
							+ "AVSTB006_SERVICO_SEGMENTO S ")
					.append("WHERE  S.NU_SERVICO = ? ")
					.append("       ORDER BY S.NU_SEGMENTO");

			this.criarPreparedStatement(sql.toString());

			this.setLong(servicoVO.getNuServico());

			this.executarConsulta();

			List<ServicoSegmentoVO> lista = new LinkedList<ServicoSegmentoVO>();

			while (this.percorrerResultSet()) {
				ServicoSegmentoVO voTemp = new ServicoSegmentoVO(new ServicoVO(
						this.getLong("NU_SERVICO")), new SegmentoVO(
						this.getLong("NU_SEGMENTO")));
				lista.add(voTemp);
			}

			return lista;
		} catch (SQLException e) {
			throw new SystemException(
					"Erro ao recuperar a lista de serviços avaliados pelo grupo.",
					e);
		} finally {
			this.fechar(null);
		}
	}

	/**
	 * 
	 * @param servicoVO
	 * @return
	 * @throws SystemException
	 */
	public List<GrupoServicoVO> listarGruposServicoPorServico(
			ServicoVO servicoVO) throws SystemException {
		try {
			StringBuilder sql = new StringBuilder()
					.append("SELECT DISTINCT ")
					.append("		S.NU_SERVICO, ")
					.append("		S.NU_GRUPO ")
					.append("FROM   " + this.getNomeSchema()
							+ "AVSTB003_GRUPO_SERVICO S ")
					.append("WHERE  S.NU_SERVICO = ? ")
					.append("       ORDER BY S.NU_GRUPO");

			this.criarPreparedStatement(sql.toString());

			this.setLong(servicoVO.getNuServico());

			this.executarConsulta();

			List<GrupoServicoVO> lista = new LinkedList<GrupoServicoVO>();

			while (this.percorrerResultSet()) {
				GrupoServicoVO voTemp = new GrupoServicoVO(new GrupoVO(
						this.getLong("NU_GRUPO")), new ServicoVO(
						this.getLong("NU_SERVICO")));
				lista.add(voTemp);
			}

			return lista;
		} catch (SQLException e) {
			throw new SystemException(
					"Erro ao recuperar a lista de serviços avaliados pelo grupo.",
					e);
		} finally {
			this.fechar(null);
		}
	}

	/**
	 * 
	 * @param servicoVO
	 * @return
	 * @throws SystemException
	 */
	public List<NoticiaVO> listarNoticiasPorServico(ServicoVO servicoVO)
			throws SystemException {
		try {
			StringBuilder sql = new StringBuilder()
					.append("SELECT DISTINCT ")
					.append("		S.NU_NOTICIA, ")
					.append("		S.NU_SERVICO ")
					.append("FROM   " + this.getNomeSchema()
							+ "avstb011_noticia S ")
					.append("WHERE  S.NU_SERVICO = ? ");

			this.criarPreparedStatement(sql.toString());

			this.setLong(servicoVO.getNuServico());

			this.executarConsulta();

			List<NoticiaVO> lista = new LinkedList<NoticiaVO>();

			while (this.percorrerResultSet()) {
				NoticiaVO voTemp = new NoticiaVO(this.getLong("NU_NOTICIA"));
				lista.add(voTemp);
			}

			return lista;
		} catch (SQLException e) {
			throw new SystemException(
					"Erro ao recuperar a lista de serviços avaliados pelo grupo.",
					e);
		} finally {
			this.fechar(null);
		}
	}
}