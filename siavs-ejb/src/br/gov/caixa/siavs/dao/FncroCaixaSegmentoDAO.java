package br.gov.caixa.siavs.dao;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.vo.FncroCaixaSegmentoVO;
import br.gov.caixa.siavs.vo.FuncionarioCaixaLocalVO;
import br.gov.caixa.siavs.vo.SegmentoVO;

/**
 * <b>Title:</b> FncroCaixaSegmentoDAO <br>
 * <b>Description:</b> Classe que persiste o fncroCaixaSegmento. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: ${classe.versao} $, $Date: 14/06/2013$
 */
public class FncroCaixaSegmentoDAO extends AbstractDAOSIAVS <FncroCaixaSegmentoVO> {
// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlIncluir
	 */
	@Override
	protected String getSqlIncluir(FncroCaixaSegmentoVO vo) {
		return new StringBuilder()
			.append("INSERT INTO " + this.getNomeTabela() + " (")
			.append("			 NU_FNCRO_CAIXA_LOCAL, ")
			.append("			 NU_SEGMENTO")
			.append(")	  VALUES (?, ?)")
			.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosIncluir
	 */
	@Override
	protected void preencherParametrosIncluir(FncroCaixaSegmentoVO vo) throws SQLException {
		this.setLong(vo.getFuncionarioCaixaLocal().getNuFncroCaixaLocal());
		this.setLong(vo.getSegmento().getNuSegmento());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlExcluir
	 */
	@Override
	protected String getSqlExcluir(FncroCaixaSegmentoVO vo) {
		StringBuilder sql = new StringBuilder("DELETE FROM " + this.getNomeTabela() + " WHERE 1 = 1 ");

		if (vo != null) {
			if(vo.getFuncionarioCaixaLocal() != null && Util.notEmpty(vo.getFuncionarioCaixaLocal().getNuFncroCaixaLocal())){
				sql.append(" AND NU_FNCRO_CAIXA_LOCAL = ? ");
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
	protected void preencherParametrosExcluir(FncroCaixaSegmentoVO vo) throws SQLException {
		if (vo != null) {
			if(vo.getFuncionarioCaixaLocal() != null && Util.notEmpty(vo.getFuncionarioCaixaLocal().getNuFncroCaixaLocal())){
				this.setLong(vo.getFuncionarioCaixaLocal().getNuFncroCaixaLocal());
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
	protected String getSqlListar(FncroCaixaSegmentoVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
			if(vo.getFuncionarioCaixaLocal() != null && Util.notEmpty(vo.getFuncionarioCaixaLocal().getNuFncroCaixaLocal())){
				sql.append(" AND FS.NU_FNCRO_CAIXA_LOCAL = ? ");
			}
			if(vo.getSegmento() != null && Util.notEmpty(vo.getSegmento().getNuSegmento())){
				sql.append(" AND FS.NU_SEGMENTO = ? ");
			}
		}
		sql.append(" ORDER BY S.NO_SEGMENTO");

		return sql.toString();
	}
	
	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosListar
	 */
	@Override
	protected void preencherParametrosListar(FncroCaixaSegmentoVO vo) throws SQLException {
		if (vo != null) {
			if(vo.getFuncionarioCaixaLocal() != null && Util.notEmpty(vo.getFuncionarioCaixaLocal().getNuFncroCaixaLocal())){
				this.setLong(vo.getFuncionarioCaixaLocal().getNuFncroCaixaLocal());
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
	protected FncroCaixaSegmentoVO preencherVOListar() throws SQLException {
		FncroCaixaSegmentoVO vo = new FncroCaixaSegmentoVO(new FuncionarioCaixaLocalVO(this.getLong("NU_FNCRO_CAIXA_LOCAL")), new SegmentoVO(this.getLong("NU_SEGMENTO")));;
		vo.getSegmento().setNoSegmento(this.getString("NO_SEGMENTO"));
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
		.append("		FS.NU_FNCRO_CAIXA_LOCAL, ")
		.append("       FS.NU_SEGMENTO, ")
		.append("       S.NO_SEGMENTO ")
		.append("FROM   " + this.getNomeTabela() + " FS, ")
		.append("       " + this.getNomeSchema() + "AVSTB005_SEGMENTO S ")
		.append("WHERE  1 = 1 ")
		.append("       AND FS.NU_SEGMENTO = S.NU_SEGMENTO ")
		.toString();
	}

	/**
	 * Retorna o nome da tabela.
	 * @return String
	 */
	private String getNomeTabela() {
		return this.getNomeSchema() + "AVSTB008_FNCRO_CAIXA_SEGMENTO";
	}

	public List<FncroCaixaSegmentoVO> listarSegmetosComUsuarioCaixaLocalVinculado(String deMatricula, Integer nuUnidade, Integer nuNatural) throws SystemException {
		
		try {
			StringBuilder sql = new StringBuilder()
			.append("SELECT ")
			.append("       FCL.NU_FNCRO_CAIXA_LOCAL, ")
			.append("       FCS.NU_SEGMENTO ")
			.append("FROM ")
			.append(" 		AVSSM001.AVSTB007_FNCRO_CAIXA_LOCAL FCL  ")
			.append(" 		JOIN AVSSM001.AVSTB008_FNCRO_CAIXA_SEGMENTO FCS ON FCL.NU_FNCRO_CAIXA_LOCAL = FCS.NU_FNCRO_CAIXA_LOCAL  ")
			.append("WHERE  1 = 1  ")
			.append(" AND FCL.NU_UNIDADE = ").append(nuUnidade)
			.append(" AND FCL.NU_NATURAL = ").append(nuNatural)
			.append(" AND FCL.NU_MATRICULA = ").append("'").append(deMatricula).append("'");
	
			criarPreparedStatement(sql.toString());
		
			this.executarConsulta();
	
			List<FncroCaixaSegmentoVO> listaFncroCaixaSegmentoVO = new LinkedList<FncroCaixaSegmentoVO>();

			while (this.percorrerResultSet()) {
					
					FncroCaixaSegmentoVO vo = new FncroCaixaSegmentoVO();
					FuncionarioCaixaLocalVO fcvo = new FuncionarioCaixaLocalVO();
					SegmentoVO svo = new SegmentoVO();
					
					vo.setFuncionarioCaixaLocal(fcvo);
					vo.setSegmento(svo);
					vo.getFuncionarioCaixaLocal().setNuFncroCaixaLocal(this.getLong("NU_FNCRO_CAIXA_LOCAL"));
					vo.getSegmento().setNuSegmento(this.getLong("NU_SEGMENTO"));
					
					listaFncroCaixaSegmentoVO.add(vo);
			}
			
			return listaFncroCaixaSegmentoVO;
		
		} catch (SQLException e) {
			throw new SystemException("Problema ao buscar lista de unidades de relacionamento ou produção.", e);
		}
		finally {
			this.fechar(null);
		}
	}
}