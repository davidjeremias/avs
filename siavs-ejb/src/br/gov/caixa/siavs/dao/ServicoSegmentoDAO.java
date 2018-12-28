package br.gov.caixa.siavs.dao;

import java.sql.SQLException;

import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.vo.SegmentoVO;
import br.gov.caixa.siavs.vo.ServicoSegmentoVO;
import br.gov.caixa.siavs.vo.ServicoVO;

/**
 * <b>Title:</b> ServicoSegmentoDAO <br>
 * <b>Description:</b> Classe que persiste o servicoSegmento. <br>
 * <b>Company:</b> Spread
 * @author ${classe.autor}
 * @version: $Revision: ${classe.versao} $, $Date: 14/06/2013$
 */
public class ServicoSegmentoDAO extends AbstractDAOSIAVS <ServicoSegmentoVO> {
// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlIncluir
	 */
	@Override
	protected String getSqlIncluir(ServicoSegmentoVO vo) {
		return new StringBuilder()
			.append("INSERT INTO " + this.getNomeTabela() + " (")
			.append("			 NU_SERVICO, ")
			.append("			 NU_SEGMENTO")
			.append(")	  VALUES (?, ?)")
			.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosIncluir
	 */
	@Override
	protected void preencherParametrosIncluir(ServicoSegmentoVO vo) throws SQLException {
		this.setLong(vo.getServico().getNuServico());
		this.setLong(vo.getSegmento().getNuSegmento());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlExcluir
	 */
	@Override
	protected String getSqlExcluir(ServicoSegmentoVO vo) {
		StringBuilder sql = new StringBuilder("DELETE FROM " + this.getNomeTabela() + " WHERE 1 = 1 ");

		if (vo != null) {
			if(vo.getServico() != null && Util.notEmpty(vo.getServico().getNuServico())){
				sql.append(" AND NU_SERVICO = ? ");
			}
			if(vo.getSegmento() != null && Util.notEmpty(vo.getSegmento().getNuSegmento())){
				sql.append(" AND NU_SEGMENTO = ? ");
			}
		}

		return sql.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosExcluir
	 */
	@Override
	protected void preencherParametrosExcluir(ServicoSegmentoVO vo) throws SQLException {
		if (vo != null) {
			if(vo.getServico() != null && Util.notEmpty(vo.getServico().getNuServico())){
				this.setLong(vo.getServico().getNuServico());
			}
			if(vo.getSegmento() != null && Util.notEmpty(vo.getSegmento().getNuSegmento())){
				this.setLong(vo.getSegmento().getNuSegmento());
			}
		}
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlListar
	 */
	@Override
	protected String getSqlListar(ServicoSegmentoVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
			if(vo.getServico() != null && Util.notEmpty(vo.getServico().getNuServico())){
				sql.append(" AND NU_SERVICO = ? ");
			}
			if(vo.getSegmento() != null && Util.notEmpty(vo.getSegmento().getNuSegmento())){
				sql.append(" AND NU_SEGMENTO = ? ");
			}
		}
		sql.append(" ORDER BY NU_SERVICO, NU_SEGMENTO");

		return sql.toString();
	}
	
	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosListar
	 */
	@Override
	protected void preencherParametrosListar(ServicoSegmentoVO vo) throws SQLException {
		if (vo != null) {
			if(vo.getServico() != null && Util.notEmpty(vo.getServico().getNuServico())){
				this.setLong(vo.getServico().getNuServico());
			}
			if(vo.getSegmento() != null && Util.notEmpty(vo.getSegmento().getNuSegmento())){
				this.setLong(vo.getSegmento().getNuSegmento());
			}
		}
	}
	
	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOListar
	 */
	@Override
	protected ServicoSegmentoVO preencherVOListar() throws SQLException {
		return new ServicoSegmentoVO(new ServicoVO(this.getLong("NU_SERVICO")), new SegmentoVO(this.getLong("NU_SEGMENTO")));
	}

// ***********************************************************************************************************************************
	
	/**
	 * Retorna o sql padrão de select.
	 * @return String
	 */
	private String getSqlSelect() {
		return new StringBuilder()
		.append("SELECT ")
		.append("		NU_SERVICO, ")
		.append("		NU_SEGMENTO")
		.append("  FROM " + this.getNomeTabela() + " ")
		.append("WHERE	1 = 1 ")
		.toString();
	}

	/**
	 * Retorna o nome da tabela.
	 * @return String
	 */
	private String getNomeTabela() {
		return this.getNomeSchema() + "AVSTB006_SERVICO_SEGMENTO";
	}
}