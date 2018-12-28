package br.gov.caixa.siavs.dao;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.NoticiaVO;
import br.gov.caixa.siavs.vo.ServicoVO;

/**
 * <b>Title:</b> NoticiaDAO <br>
 * <b>Description:</b> Classe que persiste o notícia. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 17/06/2013$
 */
public class NoticiaDAO extends AbstractDAOSIAVS <NoticiaVO> {

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getNomeSequence
	 */
	@Override
	protected String getNomeSequence() {
		return this.getNomeSchema() + "AVSSQ011_NU_NOTICIA";
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#setIdentificador
	 */
	@Override
	protected void preencherIdentificador(NoticiaVO vo, Long identicador) {
		vo.setNuNoticia(identicador);
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlIncluir
	 */
	@Override
	protected String getSqlIncluir(NoticiaVO vo) {
		return new StringBuilder()
			.append("INSERT INTO " + this.getNomeTabela() + " (")
			.append("			 DE_NOTICIA, ")
			.append("			 TS_INICIO_PUBLICACAO, ")
			.append("			 TS_FIM_PUBLICACAO, ")
			.append("			 NU_SERVICO")
			.append(")	  VALUES (?, ?, ?, ?)")
			.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosIncluir
	 */
	@Override
	protected void preencherParametrosIncluir(NoticiaVO vo) throws SQLException {
		this.setString(vo.getDeNoticia());
		this.setTimestamp(vo.getTsInicioPublicacao());
		this.setTimestamp(vo.getTsFimPublicacao());
		this.setLong(vo.getServico().getNuServico());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlAlterar
	 */
	@Override
	protected String getSqlAlterar(NoticiaVO vo) {
		return new StringBuilder()
			.append("UPDATE " + this.getNomeTabela() + " SET ")
			.append("       DE_NOTICIA = ?, ")
			.append("       TS_INICIO_PUBLICACAO = ?, ")
			.append("       TS_FIM_PUBLICACAO = ?, ")
			.append("       NU_SERVICO = ?")
			.append(" WHERE ")
			.append(" 		NU_NOTICIA = ?")
			.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosAlterar
	 */
	@Override
	protected void preencherParametrosAlterar(NoticiaVO vo) throws SQLException {
		this.setString(vo.getDeNoticia());
		this.setTimestamp(vo.getTsInicioPublicacao());
		this.setTimestamp(vo.getTsFimPublicacao());
		this.setLong(vo.getServico().getNuServico());
		this.setLong(vo.getNuNoticia());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlExcluir
	 */
	@Override
	protected String getSqlExcluir(NoticiaVO vo) {
		return new StringBuilder("DELETE FROM " + this.getNomeTabela() + " WHERE NU_NOTICIA = ?").toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosExcluir
	 */
	@Override
	protected void preencherParametrosExcluir(NoticiaVO vo) throws SQLException {
		this.setLong(vo.getNuNoticia());
	}


// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlListar
	 */
	@Override
	protected String getSqlListar(NoticiaVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());
		sql.append(" ORDER BY DE_SERVICO, TS_INICIO_PUBLICACAO ");

		return sql.toString();
	}
	
	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOListar
	 */
	@Override
	protected NoticiaVO preencherVOListar() throws SQLException {
		NoticiaVO vo = this.preencherNoticia();
		vo.getServico().setDeServico(this.getString("DE_SERVICO"));
		vo.getServico().setIcAtivo(this.getBoolean("IC_ATIVO"));
		return vo;		
	}

// ***********************************************************************************************************************************
	
	/**
	 * Retorna o sql padrão de select.
	 * @return String
	 */
	private String getSqlSelect() {
		return new StringBuilder()
		.append("SELECT ")
		.append("		N.NU_NOTICIA, ")
		.append("       N.DE_NOTICIA, ")
		.append("       N.TS_INICIO_PUBLICACAO, ")
		.append("       N.TS_FIM_PUBLICACAO, ")
		.append("       N.NU_SERVICO, ")
		.append("       S.DE_SERVICO, ")
		.append("       S.IC_ATIVO ")
		.append("FROM   " + this.getNomeTabela() + " N, ")
		.append("       " + this.getNomeSchema() + "AVSTB002_SERVICO S ")
		.append("WHERE  1 = 1 ")
		.append("       AND N.NU_SERVICO = S.NU_SERVICO ")
		.toString();
	}

	/**
	 * Retorna o nome da tabela.
	 * @return String
	 */
	private String getNomeTabela() {
		return this.getNomeSchema() + "AVSTB011_NOTICIA";
	}
	
// ***********************************************************************************************************************************

	/**
	 * Lista as notícias pelos serviços e onde o intervalo contemple a data atual.
	 * @param arrayNuServico Long[]
	 * @return List<NoticiaVO>
	 * @throws SystemException
	 */
	public List<NoticiaVO> listar(Long[] arrayNuServico) throws SystemException {
		try{
			StringBuilder sql = new StringBuilder()
			.append("SELECT ")
			.append("		NU_NOTICIA, ")
			.append("       DE_NOTICIA, ")
			.append("       TS_INICIO_PUBLICACAO, ")
			.append("       TS_FIM_PUBLICACAO, ")
			.append("       NU_SERVICO ")
			.append("FROM   " + this.getNomeTabela() + " ")
			.append("WHERE  CURRENT_TIMESTAMP BETWEEN ")
			.append("			TO_TIMESTAMP(TO_CHAR(TS_INICIO_PUBLICACAO, 'DD/MM/YYYY') || ' 00:00:00', 'DD/MM/YYYY HH24:MI:SS') AND ")
			.append("			TO_TIMESTAMP(TO_CHAR(TS_FIM_PUBLICACAO, 'DD/MM/YYYY') || ' 23:59:59', 'DD/MM/YYYY HH24:MI:SS') ")
			.append(" AND NU_SERVICO IN (").append(Util.agruparArrayStrings(arrayNuServico, ", ", null)[0]).append(")")
			.append(" ORDER BY NU_SERVICO, TS_INICIO_PUBLICACAO, TS_FIM_PUBLICACAO");
			
			this.criarPreparedStatement(sql.toString());
			this.executarConsulta();
			
			List<NoticiaVO> listaNoticia = new LinkedList<NoticiaVO>();
			while(this.percorrerResultSet()) {
				try {
					listaNoticia.add(this.preencherNoticia());
				}
				catch (SQLException e) {
					throw new SystemException("Erro ao recuperar os dados da consulta. Ocorreu um erro durante o preenchimento.", e);
				}
			}
			
			return listaNoticia;
		}
		finally {
			this.fechar(null);
		}
	}

	/**
	 * Preenche apenas os dados da Notícia.
	 * @return NoticiaVO
	 * @throws SQLException
	 */
	private NoticiaVO preencherNoticia() throws SQLException {
		NoticiaVO vo = new NoticiaVO();
		vo.setNuNoticia(this.getLong("NU_NOTICIA"));
		vo.setDeNoticia(this.getString("DE_NOTICIA"));
		vo.setTsInicioPublicacao(this.getTimestamp("TS_INICIO_PUBLICACAO"));
		vo.setTsFimPublicacao(this.getTimestamp("TS_FIM_PUBLICACAO"));
		vo.setServico(new ServicoVO(this.getLong("NU_SERVICO")));
		return vo;
	}

	/**
	 * Lista as notícias filtrando pelos serviços vinculados ao grupo.
	 * @param grupoVO GrupoVO
	 * @return List<NoticiaVO>
	 * @throws SystemException
	 */
	public List<NoticiaVO> listarPorGrupo(GrupoVO grupoVO) throws SystemException {
		try{
			StringBuilder sql = new StringBuilder()
			.append("SELECT ")
			.append("		N.NU_NOTICIA, ")
			.append("       N.DE_NOTICIA, ")
			.append("       N.TS_INICIO_PUBLICACAO, ")
			.append("       N.TS_FIM_PUBLICACAO, ")
			.append("       N.NU_SERVICO, ")
			.append("       S.DE_SERVICO, ")
			.append("       S.IC_ATIVO ")
			.append("FROM   " + this.getNomeTabela() + " N, ")
			.append("       " + this.getNomeSchema() + "AVSTB002_SERVICO S, ")
			.append("       " + this.getNomeSchema() + "AVSTB003_GRUPO_SERVICO GS ")
			.append("WHERE  1 = 1 ")
			.append("       AND N.NU_SERVICO = S.NU_SERVICO ")
			.append("       AND S.NU_SERVICO = GS.NU_SERVICO ")
			.append("       AND GS.NU_GRUPO = ? ")
			.append(" ORDER BY DE_SERVICO, TS_INICIO_PUBLICACAO ");			
			
			this.criarPreparedStatement(sql.toString());
			
			this.setLong(grupoVO.getNuGrupo());
			
			this.executarConsulta();
			
			List<NoticiaVO> listaNoticia = new LinkedList<NoticiaVO>();
			while(this.percorrerResultSet()) {
				try {
					listaNoticia.add(this.preencherVOListar());
				}
				catch (SQLException e) {
					throw new SystemException("Erro ao recuperar os dados da consulta. Ocorreu um erro durante o preenchimento.", e);
				}
			}
			
			return listaNoticia;
		}
		catch (SQLException e) {
			throw new SystemException("Erro ao recuperar a lista de notícias por grupo.", e);
		}
		finally {
			this.fechar(null);
		}
	}
	
// ***********************************************************************************************************************************
}