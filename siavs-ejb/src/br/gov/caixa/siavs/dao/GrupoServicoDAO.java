package br.gov.caixa.siavs.dao;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.global.DominioSIAVS;
import br.gov.caixa.siavs.vo.FuncionarioCaixaLocalVO;
import br.gov.caixa.siavs.vo.GrupoServicoVO;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.ServicoVO;

/**
 * <b>Title:</b> GrupoServicoDAO <br>
 * <b>Description:</b> Classe que persiste o grupoServico. <br>
 * <b>Company:</b> Spread
 * @author ${classe.autor}
 * @version: $Revision: ${classe.versao} $, $Date: 14/06/2013$
 */
public class GrupoServicoDAO extends AbstractDAOSIAVS <GrupoServicoVO> {
// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlIncluir
	 */
	@Override
	protected String getSqlIncluir(GrupoServicoVO vo) {
		return new StringBuilder()
			.append("INSERT INTO " + this.getNomeTabela() + " (")
			.append("			 NU_GRUPO, ")
			.append("			 NU_SERVICO")
			.append(")	  VALUES (?, ?)")
			.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosIncluir
	 */
	@Override
	protected void preencherParametrosIncluir(GrupoServicoVO vo) throws SQLException {
		this.setLong(vo.getGrupo().getNuGrupo());
		this.setLong(vo.getServico().getNuServico());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlExcluir
	 */
	@Override
	protected String getSqlExcluir(GrupoServicoVO vo) {
		StringBuilder sql = new StringBuilder("DELETE FROM " + this.getNomeTabela() + " WHERE 1 = 1 ");

		if (vo != null) {
			if(vo.getGrupo() != null && Util.notEmpty(vo.getGrupo().getNuGrupo())){
				sql.append(" AND NU_GRUPO = ? ");
			}
			if(vo.getServico() != null && Util.notEmpty(vo.getServico().getNuServico())){
				sql.append(" AND NU_SERVICO = ? ");
			}
		}

		return sql.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosExcluir
	 */
	@Override
	protected void preencherParametrosExcluir(GrupoServicoVO vo) throws SQLException {
		if (vo != null) {
			if(vo.getGrupo() != null && Util.notEmpty(vo.getGrupo().getNuGrupo())){
				this.setLong(vo.getGrupo().getNuGrupo());
			}
			if(vo.getServico() != null && Util.notEmpty(vo.getServico().getNuServico())){
				this.setLong(vo.getServico().getNuServico());
			}
		}
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlListar
	 */
	@Override
	protected String getSqlListar(GrupoServicoVO vo) {
		StringBuilder sql = new StringBuilder(this.getSqlSelect());

		if (vo != null) {
			if(vo.getGrupo() != null && Util.notEmpty(vo.getGrupo().getNuGrupo())){
				sql.append(" AND GS.NU_GRUPO = ? ");
			}
			if(vo.getServico() != null){
				if(Util.notEmpty(vo.getServico().getNuServico())){
					sql.append(" AND GS.NU_SERVICO = ? ");
				}
				if(vo.getServico().getIcAtivo() != null){
					sql.append(" AND S.IC_ATIVO = ? ");
				}
			}
		}
		sql.append(" ORDER BY S.DE_SERVICO");

		return sql.toString();
	}
	
	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosListar
	 */
	@Override
	protected void preencherParametrosListar(GrupoServicoVO vo) throws SQLException {
		if (vo != null) {
			if(vo.getGrupo() != null && Util.notEmpty(vo.getGrupo().getNuGrupo())){
				this.setLong(vo.getGrupo().getNuGrupo());
			}
			if(vo.getServico() != null){
				if(Util.notEmpty(vo.getServico().getNuServico())){
					this.setLong(vo.getServico().getNuServico());
				}
				if(vo.getServico().getIcAtivo() != null){
					this.setBoolean(vo.getServico().getIcAtivo());
				}
			}			
		}
	}
	
	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOListar
	 */
	@Override
	protected GrupoServicoVO preencherVOListar() throws SQLException {
		GrupoServicoVO vo = new GrupoServicoVO(new GrupoVO(this.getLong("NU_GRUPO")), new ServicoVO(this.getLong("NU_SERVICO")));
		vo.getServico().setDeServico(this.getString("DE_SERVICO"));
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
		.append("		GS.NU_GRUPO, ")
		.append("		GS.NU_SERVICO, ")
		.append("		S.DE_SERVICO ")
		.append("  FROM " + this.getNomeTabela() + " GS, ")
		.append("       " + this.getNomeSchema() + "AVSTB002_SERVICO S ")
		.append("WHERE	1 = 1 ")
		.append("       AND GS.NU_SERVICO = S.NU_SERVICO ")
		.toString();
	}

	/**
	 * Retorna o nome da tabela.
	 * @return String
	 */
	private String getNomeTabela() {
		return this.getNomeSchema() + "AVSTB003_GRUPO_SERVICO";
	}
	
// ***********************************************************************************************************************************

	/**
	 * Lista os serviços que podem ser avaliados por determinado usuário.
	 * @param funcionarioCaixaLocalVO FuncionarioCaixaLocalVO
	 * @return List<ServicoVO>
	 * @throws SystemException
	 */
	public List<ServicoVO> listar(FuncionarioCaixaLocalVO funcionarioCaixaLocalVO) throws SystemException {
		try{
			
			StringBuilder sql = new StringBuilder();
			
			sql.append(" SELECT DISTINCT S.NU_SERVICO, S.DE_SERVICO                  ");
			sql.append("   FROM AVSSM001.AVSTB002_SERVICO S,                         ");
			sql.append("        AVSSM001.AVSTB003_GRUPO_SERVICO GS,                  ");
			sql.append("        AVSSM001.AVSTB001_GRUPO G,                           ");
			sql.append("        AVSSM001.AVSTB006_SERVICO_SEGMENTO SS,               ");
			sql.append("        AVSSM001.AVSTB008_FNCRO_CAIXA_SEGMENTO FS,           ");
			sql.append("        avssm001.avstb007_fncro_caixa_local tb007,           ");
			sql.append("        avssm001.avsvw002_funcionario_caixa vw002            ");
			sql.append("  WHERE S.NU_SERVICO = GS.NU_SERVICO                         ");
			sql.append("    AND GS.NU_GRUPO = G.NU_GRUPO                             ");
			sql.append("    AND S.NU_SERVICO = SS.NU_SERVICO                         ");
			sql.append("    AND SS.NU_SEGMENTO = FS.NU_SEGMENTO                      ");
			sql.append("    AND FS.NU_FNCRO_CAIXA_LOCAL = tb007.NU_FNCRO_CAIXA_LOCAL ");
			sql.append("    and vw002.nu_unidade = tb007.nu_unidade                  ");
			sql.append("    and vw002.nu_natural = tb007.nu_natural                  ");
			sql.append("    and vw002.nu_matricula = tb007.nu_matricula::int         ");
			sql.append("    AND G.NU_GRUPO = ?                                       ");
			sql.append("    AND vw002.nu_matricula = ?                               ");
			sql.append("  ORDER BY S.DE_SERVICO                                      ");
			
			this.criarPreparedStatement(sql.toString());

			this.setLong(DominioSIAVS.ID_GRUPO_AGENCIA);
			this.setLong(Long.parseLong(funcionarioCaixaLocalVO.getFuncionarioCaixa().getNuMatricula()));
			
			this.executarConsulta();
			
			List<ServicoVO> lista = new LinkedList<ServicoVO>();
			
			while (this.percorrerResultSet()) {
				ServicoVO voTemp = new ServicoVO(this.getLong("NU_SERVICO"));
				lista.add(voTemp);
				
				voTemp.setDeServico(this.getString("DE_SERVICO"));
			}
			
			return lista;
		}
		catch (SQLException e) {
			throw new SystemException("Erro ao recuperar a lista de serviços para avaliação.", e);
		}
		finally {
			this.fechar(null);
		}
	}
}