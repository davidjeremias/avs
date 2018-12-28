package br.gov.caixa.siavs.dao;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.util.Data;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.to.ConsultaPainelAvaliacaoTO;
import br.gov.caixa.siavs.vo.AvaliacaoVO;
import br.gov.caixa.siavs.vo.FuncionarioCaixaLocalVO;
import br.gov.caixa.siavs.vo.FuncionarioCaixaVO;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.ProblemaVO;
import br.gov.caixa.siavs.vo.ServicoVO;
import br.gov.caixa.siavs.vo.UnidadeVO;

/**
 * <b>Title:</b> AvaliacaoDAO <br>
 * <b>Description:</b> Classe que persiste a avaliação. <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 14/06/2013$
 */
public class AvaliacaoDAO extends AbstractDAOSIAVS<AvaliacaoVO> {

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getNomeSequence
	 */
	@Override
	protected String getNomeSequence() {
		return this.getNomeSchema() + "AVSSQ010_NU_AVALIACAO";
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#setIdentificador
	 */
	@Override
	protected void preencherIdentificador(AvaliacaoVO vo, Long identicador) {
		vo.setNuAvaliacao(identicador);
	}

	// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlIncluir
	 */
	@Override
	protected String getSqlIncluir(AvaliacaoVO vo) {
		return new StringBuilder().append("INSERT INTO " + this.getNomeTabela() + " (")
				.append("			 VR_NOTA_AVALIACAO, ").append("			 DE_COMENTARIO, ")
				.append("			 DT_AVALIACAO, ").append("			 NU_SERVICO, ")
				.append("			 NU_PROBLEMA, ").append("			 NU_GRUPO, ")
				.append("			 NU_FNCRO_CAIXA_LOCAL").append(")	  VALUES (?, ?, ?, ?, ?, ?, ?)").toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosIncluir
	 */
	@Override
	protected void preencherParametrosIncluir(AvaliacaoVO vo) throws SQLException {
		this.setDouble(vo.getVrNotaAvaliacao());
		this.setString(vo.getDeComentario());
		this.setTimestamp(new Date());
		this.setLong(vo.getServico().getNuServico());
		this.setLong(vo.getProblema().getNuProblema());
		this.setLong(vo.getGrupo().getNuGrupo());
		this.setLong(vo.getFuncionarioCaixaLocal().getNuFncroCaixaLocal());
	}

	// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlListar
	 */
	@Override
	protected String getSqlListar(AvaliacaoVO vo) {
		StringBuilder sql = new StringBuilder().append("SELECT ").append("		NU_AVALIACAO, ")
				.append("		VR_NOTA_AVALIACAO, ").append("		DE_COMENTARIO, ").append("		DT_AVALIACAO, ")
				.append("		NU_SERVICO, ").append("		NU_PROBLEMA, ").append("		NU_GRUPO, ")
				.append("		NU_FNCRO_CAIXA_LOCAL ").append("  FROM " + this.getNomeTabela() + " ")
				.append("WHERE	1 = 1 ");

		if (vo != null) {
			if (vo.getGrupo() != null && Util.notEmpty(vo.getGrupo().getNuGrupo())) {
				sql.append(" AND NU_GRUPO = ? ");
			}
			if (vo.getServico() != null && Util.notEmpty(vo.getServico().getNuServico())) {
				sql.append(" AND NU_SERVICO = ? ");
			}
			if (vo.getFuncionarioCaixaLocal() != null
					&& Util.notEmpty(vo.getFuncionarioCaixaLocal().getNuFncroCaixaLocal())) {
				sql.append(" AND NU_FNCRO_CAIXA_LOCAL = ? ");
			}
			if (vo.getDtAvaliacao() != null) {
				sql.append(" AND TO_CHAR(DT_AVALIACAO, 'DD/MM/YYYY') = ? ");
			}
		}

		sql.append(" ORDER BY NU_AVALIACAO");

		return sql.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosListar
	 */
	@Override
	protected void preencherParametrosListar(AvaliacaoVO vo) throws SQLException {
		if (vo != null) {
			if (vo.getGrupo() != null && Util.notEmpty(vo.getGrupo().getNuGrupo())) {
				this.setLong(vo.getGrupo().getNuGrupo());
			}
			if (vo.getServico() != null && Util.notEmpty(vo.getServico().getNuServico())) {
				this.setLong(vo.getServico().getNuServico());
			}
			if (vo.getFuncionarioCaixaLocal() != null
					&& Util.notEmpty(vo.getFuncionarioCaixaLocal().getNuFncroCaixaLocal())) {
				this.setLong(vo.getFuncionarioCaixaLocal().getNuFncroCaixaLocal());
			}
			if (vo.getDtAvaliacao() != null) {
				this.setString(Data.formatar(vo.getDtAvaliacao()));
			}
		}
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOListar
	 */
	@Override
	protected AvaliacaoVO preencherVOListar() throws SQLException {
		AvaliacaoVO vo = new AvaliacaoVO();
		vo.setNuAvaliacao(this.getLong("NU_AVALIACAO"));
		vo.setVrNotaAvaliacao(this.getDouble("VR_NOTA_AVALIACAO"));
		vo.setDeComentario(this.getString("DE_COMENTARIO"));
		vo.setDtAvaliacao(this.getTimestamp("DT_AVALIACAO"));
		vo.setServico(new ServicoVO(this.getLong("NU_SERVICO")));
		vo.setProblema(new ProblemaVO(this.getLong("NU_PROBLEMA")));
		vo.setGrupo(new GrupoVO(this.getLong("NU_GRUPO")));
		vo.setFuncionarioCaixaLocal(new FuncionarioCaixaLocalVO(this.getLong("NU_FNCRO_CAIXA_LOCAL")));
		return vo;
	}

	// ***********************************************************************************************************************************

	/**
	 * Retorna o nome da tabela.
	 * 
	 * @return String
	 */
	private String getNomeTabela() {
		return this.getNomeSchema() + "AVSTB010_AVALIACAO";
	}

	// ***********************************************************************************************************************************

	/**
	 * Consulta o ano da primeira avaliação.
	 * 
	 * @param grupo
	 *            GrupoVO
	 * @return Integer
	 * @throws SystemException
	 */
	public Integer consultarPrimeiroAnoAvaliacao(GrupoVO grupo) throws SystemException {
		try {
			this.criarPreparedStatement("SELECT MIN(EXTRACT(YEAR FROM DT_AVALIACAO)) AS ANO FROM "
					+ this.getNomeTabela() + " WHERE NU_GRUPO = ?");
			this.setLong(grupo.getNuGrupo());
			this.executarConsulta();

			if (this.percorrerResultSet()) {
				return this.getInteger("ANO");
			}
			return null;
		} catch (SQLException e) {
			throw new SystemException("Erro ao recuperar o primeiro ano da avaliação.", e);
		} finally {
			this.fechar(null);
		}
	}

	/**
	 * Lista as avaliações para o painel de avaliações.
	 * 
	 * @param grupo
	 *            GrupoVO
	 * @param dtInicial
	 *            Date
	 * @param dtFinal
	 *            Date
	 * @param listaServico
	 *            List<ServicoVO>
	 * @param listaProblema
	 *            List<ProblemaVO>
	 * @return List<ConsultaPainelAvaliacaoTO>
	 * @throws SystemException
	 */
	public List<ConsultaPainelAvaliacaoTO> listarAvaliacoesPainel(GrupoVO grupo, Date dtInicial, Date dtFinal,
			List<ServicoVO> listaServico, List<ProblemaVO> listaProblema) throws SystemException {
		try {
			StringBuilder sql = new StringBuilder().append("SELECT ").append("		S.NU_SERVICO, ")
					.append("       S.DE_SERVICO, ").append("       COUNT(A.NU_AVALIACAO)    QTDE_AVAL, ")
					.append("       AVG(A.VR_NOTA_AVALIACAO) MEDIA_AVAL, ");

			if (Util.notEmpty(listaProblema)) {
				for (ProblemaVO problemaVO : listaProblema) {
					sql.append("       COUNT(CASE WHEN A.NU_PROBLEMA = " + problemaVO.getNuProblema()
							+ " THEN 1 END) QTDE_" + problemaVO.getNuProblema() + ", ");
				}
			}

			sql.append("       1 ").append("FROM   " + this.getNomeSchema() + "AVSTB002_SERVICO S, ")
					.append("       " + this.getNomeTabela() + " A ").append("WHERE  1 = 1 ")
					.append("		AND S.NU_SERVICO = A.NU_SERVICO ").append("		AND A.NU_GRUPO = ? ")
					.append("       AND A.DT_AVALIACAO BETWEEN ")
					.append("       		TO_TIMESTAMP(? || ' 00:00:00', 'DD/MM/YYYY HH24:MI:SS') AND ")
					.append("       		TO_TIMESTAMP(? || ' 23:59:59', 'DD/MM/YYYY HH24:MI:SS') ");

			if (Util.notEmpty(listaServico)) {
				sql.append(" AND S.NU_SERVICO IN (");

				for (Iterator<ServicoVO> iterator = listaServico.iterator(); iterator.hasNext();) {
					sql.append(iterator.next().getNuServico());
					if (iterator.hasNext()) {
						sql.append(", ");
					}
				}

				sql.append(") ");
			}

			sql.append(" GROUP BY S.NU_SERVICO ").append(" ORDER BY MEDIA_AVAL, S.DE_SERVICO ");

			this.criarPreparedStatement(sql.toString());

			this.setLong(grupo.getNuGrupo());
			this.setString(Data.formatar(dtInicial));
			this.setString(Data.formatar(dtFinal));

			this.executarConsulta();

			List<ConsultaPainelAvaliacaoTO> listaTO = new LinkedList<ConsultaPainelAvaliacaoTO>();

			while (this.percorrerResultSet()) {
				ConsultaPainelAvaliacaoTO to = new ConsultaPainelAvaliacaoTO();
				listaTO.add(to);

				to.setServico(new ServicoVO(this.getLong("NU_SERVICO")));
				to.getServico().setDeServico(this.getString("DE_SERVICO"));
				to.setQtdeAvaliacao(this.getLong("QTDE_AVAL"));
				to.setMediaNotaAvaliacao(this.getDouble("MEDIA_AVAL"));

				to.setMapProblema(new LinkedHashMap<Long, Integer>());
				if (Util.notEmpty(listaProblema)) {
					for (ProblemaVO problemaVO : listaProblema) {
						to.getMapProblema().put(problemaVO.getNuProblema(),
								this.getInteger("QTDE_" + problemaVO.getNuProblema()));
					}
				}
			}

			return listaTO;
		} catch (SQLException e) {
			throw new SystemException("Erro ao recuperar a lista de avaliações para o painel.", e);
		} finally {
			this.fechar(null);
		}
	}

	/**
	 * Lista as avaliações por período.
	 * 
	 * @param grupo
	 *            GrupoVO
	 * @param listaServico
	 *            List<ServicoVO>
	 * @param dtInicial
	 *            Date
	 * @param dtFinal
	 *            Date
	 * @return List<AvaliacaoVO>
	 * @throws SystemException
	 */
	public List<AvaliacaoVO> listarPorPeriodo(GrupoVO grupo, List<ServicoVO> listaServico, Date dtInicial, Date dtFinal)
			throws SystemException {
		try {
			StringBuilder sql = new StringBuilder().append("SELECT ").append("		A.NU_AVALIACAO, ")
					.append("       A.VR_NOTA_AVALIACAO, ").append("       A.DE_COMENTARIO, ")
					.append("       A.DT_AVALIACAO, ").append("       A.NU_SERVICO, ").append("       A.NU_PROBLEMA, ")
					.append("       A.NU_GRUPO, ").append("       A.NU_FNCRO_CAIXA_LOCAL, ")
					.append("       P.DE_PROBLEMA, ").append("       U.NU_UNIDADE, ").append("       U.NO_UNIDADE, ")
					.append("       U.SG_UF, ").append("       U.NU_TELEFONE, ").append("       F.NU_MATRICULA, ")
					.append("       F.NO_EMPREGADO, ").append("       G.NO_GRUPO, ").append("       S.DE_SERVICO ")
					.append("FROM   " + this.getNomeTabela() + " A ")
					.append("       LEFT JOIN " + this.getNomeSchema()
							+ "AVSTB009_PROBLEMA P ON A.NU_PROBLEMA = P.NU_PROBLEMA ")
					.append("       left JOIN " + this.getNomeSchema()
							+ "AVSTB007_FNCRO_CAIXA_LOCAL FL ON A.NU_FNCRO_CAIXA_LOCAL = FL.NU_FNCRO_CAIXA_LOCAL ")
					.append("       left JOIN " + this.getNomeSchema()
							+ "AVSVW001_UNIDADE U ON FL.NU_UNIDADE = U.NU_UNIDADE ")
					.append("       	AND FL.NU_NATURAL = U.NU_NATURAL ")
					.append("       left JOIN " + this.getNomeSchema()
							+ "AVSVW002_FUNCIONARIO_CAIXA F ON FL.NU_MATRICULA = F.NU_MATRICULA::CHARACTER VARYING ")
					.append("       JOIN " + this.getNomeSchema() + "AVSTB001_GRUPO G ON A.NU_GRUPO = G.NU_GRUPO ")
					.append("       JOIN " + this.getNomeSchema()
							+ "AVSTB002_SERVICO S ON A.NU_SERVICO = S.NU_SERVICO ")
					.append("WHERE  1 = 1 ").append(" 		AND A.NU_GRUPO = ? ");

			if (Util.notEmpty(listaServico)) {
				sql.append(" AND A.NU_SERVICO IN (");

				for (Iterator<ServicoVO> iterator = listaServico.iterator(); iterator.hasNext();) {
					sql.append(iterator.next().getNuServico());
					if (iterator.hasNext()) {
						sql.append(", ");
					}
				}

				sql.append(") ");
			}

			sql.append("       AND A.DT_AVALIACAO BETWEEN ")
					.append("       		TO_TIMESTAMP(? || ' 00:00:00', 'DD/MM/YYYY HH24:MI:SS') AND ")
					.append("       		TO_TIMESTAMP(? || ' 23:59:59', 'DD/MM/YYYY HH24:MI:SS') ")
					.append("ORDER  BY S.DE_SERVICO, A.VR_NOTA_AVALIACAO DESC, A.DT_AVALIACAO ");

			this.criarPreparedStatement(sql.toString());

			this.setLong(grupo.getNuGrupo());
			this.setString(Data.formatar(dtInicial));
			this.setString(Data.formatar(dtFinal));

			this.executarConsulta();

			List<AvaliacaoVO> lista = new LinkedList<AvaliacaoVO>();

			while (this.percorrerResultSet()) {
				AvaliacaoVO voTemp = new AvaliacaoVO();
				lista.add(voTemp);

				voTemp.setNuAvaliacao(this.getLong("NU_AVALIACAO"));
				voTemp.setVrNotaAvaliacao(this.getDouble("VR_NOTA_AVALIACAO"));
				voTemp.setDeComentario(this.getString("DE_COMENTARIO"));
				voTemp.setDtAvaliacao(this.getTimestamp("DT_AVALIACAO"));

				voTemp.setServico(new ServicoVO(this.getLong("NU_SERVICO")));
				voTemp.getServico().setDeServico(this.getString("DE_SERVICO"));

				voTemp.setGrupo(new GrupoVO(this.getLong("NU_GRUPO")));
				voTemp.getGrupo().setNoGrupo(this.getString("NO_GRUPO"));

				voTemp.setProblema(new ProblemaVO(this.getLong("NU_PROBLEMA")));
				voTemp.getProblema().setDeProblema(this.getString("DE_PROBLEMA"));

				voTemp.setFuncionarioCaixaLocal(new FuncionarioCaixaLocalVO(this.getLong("NU_FNCRO_CAIXA_LOCAL")));

				voTemp.getFuncionarioCaixaLocal().setUnidade(new UnidadeVO(this.getInteger("NU_UNIDADE"), null));
				voTemp.getFuncionarioCaixaLocal().getUnidade().setNoUnidade(this.getString("NO_UNIDADE"));
				voTemp.getFuncionarioCaixaLocal().getUnidade().setNuTelefone(this.getString("NU_TELEFONE"));
				voTemp.getFuncionarioCaixaLocal().getUnidade().setSgUf(this.getString("SG_UF"));

				voTemp.getFuncionarioCaixaLocal()
						.setFuncionarioCaixa(new FuncionarioCaixaVO(this.getString("NU_MATRICULA")));
				voTemp.getFuncionarioCaixaLocal().getFuncionarioCaixa()
						.setNoFuncionario(this.getString("NO_EMPREGADO"));
			}

			return lista;
		} catch (SQLException e) {
			throw new SystemException("Erro ao recuperar a lista de avaliações por período.", e);
		} finally {
			this.fechar(null);
		}
	}

	/**
	 * Lista as médias por período.
	 * 
	 * @param servico
	 *            Serviço que deve ser listado
	 * @param ultimaQuinzena
	 *            Se é a última quinzena ou será o último semestre
	 * @return List<ConsultaPainelAvaliacaoTO>
	 * @throws SystemException
	 */
	public List<ConsultaPainelAvaliacaoTO> listarMediasPorPeriodo(ServicoVO servico, boolean ultimaQuinzena,
			Date dtReferencia) throws SystemException {

		Calendar dtInicial = Calendar.getInstance();
		if (dtReferencia != null) {
			dtInicial.setTime(dtReferencia);
		}
		
		Calendar dtFinal = Calendar.getInstance();
		if (dtReferencia != null) {
			dtFinal.setTime(dtReferencia);
		}
		
		String mascara = "MM/yyyy";

		if (ultimaQuinzena) {
			// Última quinzena
			dtInicial.add(Calendar.DATE, -14);
			mascara = "dd/" + mascara;
		} else {
			// Último semestre
			dtInicial.add(Calendar.MONTH, -5);
		}

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT S.NU_SERVICO, ");
		sql.append("       AVG(A.VR_NOTA_AVALIACAO) MEDIA_AVAL, ");
		sql.append("       TO_CHAR(DT_AVALIACAO, '" + mascara + "') PERIODO ");
		sql.append("  FROM " + this.getNomeSchema() + "AVSTB002_SERVICO S, ");
		sql.append("       " + this.getNomeTabela() + " A ");
		sql.append(" WHERE S.NU_SERVICO = A.NU_SERVICO ");
		sql.append("   AND A.DT_AVALIACAO BETWEEN TO_TIMESTAMP(? || ' 00:00:00', 'dd/MM/yyyy HH24:MI:SS') ");
		sql.append("         AND TO_TIMESTAMP(? || ' 23:59:59', 'dd/MM/yyyy HH24:MI:SS') ");
		sql.append("   AND S.NU_SERVICO = ? ");
		sql.append(" GROUP BY S.NU_SERVICO, PERIODO ");
		sql.append(" ORDER BY TO_DATE(TO_CHAR(DT_AVALIACAO, '" + mascara + "'), 'dd/MM/yyyy')");

		try {
			this.criarPreparedStatement(sql.toString());

			this.setString(Data.formatar(dtInicial.getTime()));
			this.setString(Data.formatar(dtFinal.getTime()));
			this.setLong(servico.getNuServico());

			this.executarConsulta();

			List<ConsultaPainelAvaliacaoTO> listaTO = new LinkedList<ConsultaPainelAvaliacaoTO>();

			while (this.percorrerResultSet()) {
				ConsultaPainelAvaliacaoTO to = new ConsultaPainelAvaliacaoTO();
				listaTO.add(to);

				to.setServico(new ServicoVO(this.getLong("NU_SERVICO")));
				to.setMediaNotaAvaliacao(this.getDouble("MEDIA_AVAL"));
				to.setData(Data.toDate(this.getString("PERIODO"), mascara));
			}

			return listaTO;
		} catch (SQLException e) {
			throw new SystemException("Erro ao recuperar a lista de médias por período.", e);
		} finally {
			this.fechar(null);
		}
	}
}