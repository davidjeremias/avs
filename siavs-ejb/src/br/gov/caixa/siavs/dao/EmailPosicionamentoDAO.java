package br.gov.caixa.siavs.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.util.Data;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.vo.EmailPosicionamentoVO;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.ServicoVO;

/**
 * <b>Title:</b> EmailPosicionamentoDAO <br>
 * <b>Description:</b> Classe que persiste o email posicionamento. <br>
 * <b>Company:</b> Spread
 * @author joana.espindola
 * @version: $Revision: 1.0 $, $Date: 04/07/2013$
 */
public class EmailPosicionamentoDAO extends AbstractDAOSIAVS<EmailPosicionamentoVO> {

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getNomeSequence
	 */
	@Override
	protected String getNomeSequence() {
		return this.getNomeSchema() + "AVSSQ015_NU_EMAIL_POSICIONAMENTO";
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#setIdentificador
	 */
	@Override
	protected void preencherIdentificador(EmailPosicionamentoVO vo, Long identicador) {
		vo.setNuEmailPosicionamento(identicador);
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlIncluir
	 */
	@Override
	protected String getSqlIncluir(EmailPosicionamentoVO vo) {
		return new StringBuilder()
				.append("INSERT INTO " + this.getNomeTabela() + " (")
				.append("			 DE_ASSUNTO, ")
				.append("			 DE_CONTEUDO, ")
				.append("			 DT_CADASTRO, ")
				.append("			 NU_GRUPO, ")
				.append("			 NU_SERVICO, ")
				.append("			 NO_ANEXO, ")
				.append("			 AR_ANEXO ")
				.append(")	  VALUES (?, ?, ?, ?, ?, ?, ?)")
				.toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosIncluir
	 */
	@Override
	protected void preencherParametrosIncluir(EmailPosicionamentoVO vo) throws SQLException {
		this.setString(vo.getDeAssunto());
		this.setString(vo.getDeConteudo());
		this.setTimestamp(new Date());
	//	if (vo.getGrupo().getNuGrupo().equals(0)){
		//this.setLong(vo.getGrupo().getNuGrupo()+1);
		//}else{
		this.setLong(vo.getGrupo().getNuGrupo());
		//}
		this.setLong(vo.getServico().getNuServico());
		this.setString(vo.getNoAnexo());
		this.setBinario(vo.getArAnexo());
	}

// ***********************************************************************************************************************************
	
	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlAlterar(br.com.spread.framework.vo.AbstractVO)
	 */
	@Override
	protected String getSqlAlterar(EmailPosicionamentoVO vo) {
		return new StringBuilder()
		.append("UPDATE " + this.getNomeTabela() + " SET ")
		.append("       DT_ENVIO = ? ")
		.append(" WHERE ")
		.append(" 		NU_EMAIL_POSICIONAMENTO = ?")
		.toString();
	}
	
	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosAlterar(br.com.spread.framework.vo.AbstractVO)
	 */
	@Override
	protected void preencherParametrosAlterar(EmailPosicionamentoVO vo) throws SQLException {
		this.setTimestamp(vo.getDtEnvio());
		this.setLong(vo.getNuEmailPosicionamento());
	}
	
// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#getSqlExcluir
	 */
	@Override
	protected String getSqlExcluir(EmailPosicionamentoVO vo) {
		return new StringBuilder("DELETE FROM " + this.getNomeTabela() + " WHERE NU_EMAIL_POSICIONAMENTO = ?").toString();
	}

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherParametrosExcluir
	 */
	@Override
	protected void preencherParametrosExcluir(EmailPosicionamentoVO vo) throws SQLException {
		this.setLong(vo.getNuEmailPosicionamento());
	}

// ***********************************************************************************************************************************

	/**
	 * @see br.com.spread.framework.dao.jdbc.AbstractDAOJDBC#preencherVOListar
	 */
	@Override
	protected EmailPosicionamentoVO preencherVOListar() throws SQLException {
		EmailPosicionamentoVO vo = new EmailPosicionamentoVO();
		vo.setNuEmailPosicionamento(this.getLong("NU_EMAIL_POSICIONAMENTO"));
		vo.setDeAssunto(this.getString("DE_ASSUNTO"));
		vo.setDeConteudo(this.getString("DE_CONTEUDO"));
		vo.setDtEnvio(this.getTimestamp("DT_ENVIO"));
		vo.setDtCadastro(this.getTimestamp("DT_CADASTRO"));
		vo.setNoAnexo(this.getString("NO_ANEXO"));
		vo.setArAnexo(this.getBinario("AR_ANEXO"));
		vo.setGrupo(new GrupoVO(this.getLong("NU_GRUPO")));
		vo.setServico(new ServicoVO(this.getLong("NU_SERVICO")));

		return vo;
	}

// ***********************************************************************************************************************************
	
	/**
	 * Retorna o nome da tabela.
	 * @return String
	 */
	private String getNomeTabela() {
		return this.getNomeSchema() + "AVSTB015_EMAIL_POSICIONAMENTO";
	}

	/**
	 * Retorna o sql padrão de select.
	 * @return String
	 */
	private String getSqlSelect() {
		return new StringBuilder()
			.append("SELECT ")
			.append("		NU_EMAIL_POSICIONAMENTO, ")
			.append("		DE_ASSUNTO, ")
			.append("		DE_CONTEUDO, ")
			.append("		DT_CADASTRO, ")
			.append("		NU_GRUPO, ")
			.append("		NU_SERVICO, ")
			.append("		DT_ENVIO, ")
			.append("		NO_ANEXO, ")
			.append("		AR_ANEXO ")
			.append("  FROM " + this.getNomeTabela() + " ")
			.append("WHERE	1 = 1 ")
			.toString();
	}
	
// ***********************************************************************************************************************************
	
	/**
	 * Busca os emails de filtrando por grupo, serviço e período de datas.
	 * @param vo EmailPosicionamentoVO
	 * @param dtInicio Date
	 * @param dtFim Date
	 * @return List<EmailPosicionamentoVO>
	 * @throws SystemException
	 */
	public List<EmailPosicionamentoVO> buscarEmails(EmailPosicionamentoVO vo, Date dtInicio, Date dtFim) throws SystemException {
		try {
			StringBuilder sql = new StringBuilder()
			.append("SELECT ")
			.append("		E.NU_EMAIL_POSICIONAMENTO, ")
			.append("		E.DE_ASSUNTO, ")
			.append("		E.DE_CONTEUDO, ")
			.append("		E.DT_CADASTRO, ")
			.append("		E.NU_GRUPO, ")
			.append("		E.NU_SERVICO, ")
			.append("		E.DT_ENVIO, ")
			.append("		E.NO_ANEXO, ")
			.append("		E.AR_ANEXO, ")
			.append("		G.NO_GRUPO, ")
			.append("		S.DE_SERVICO ")
			.append("  FROM " + this.getNomeTabela() + " E ")
			.append("       JOIN " + this.getNomeSchema() + "AVSTB001_GRUPO G ")
			.append("       	ON E.NU_GRUPO = G.NU_GRUPO ")
			.append("       LEFT JOIN " + this.getNomeSchema() + "AVSTB002_SERVICO S ")
			.append("       	ON E.NU_SERVICO = S.NU_SERVICO ")
			.append("WHERE	1 = 1 ");

			if(vo!= null){
				if (vo.getGrupo() != null && Util.notEmpty(vo.getGrupo().getNuGrupo())) {
					sql.append(" AND E.NU_GRUPO = ? ");
				}
				
				if (vo.getServico() != null && Util.notEmpty(vo.getServico().getNuServico())) {
					sql.append(" AND E.NU_SERVICO = ? ");
				}
			}

			if (dtInicio != null) {
				sql.append(" AND E.DT_CADASTRO > ").append(" TO_TIMESTAMP(? || ' 00:00:00', 'DD/MM/YYYY HH24:MI:SS') ");
			}
			if (dtFim != null) {
				sql.append(" AND E.DT_CADASTRO <= ").append(" TO_TIMESTAMP(? || ' 23:59:59', 'DD/MM/YYYY HH24:MI:SS') ");
			}
			sql.append(" ORDER BY E.NU_EMAIL_POSICIONAMENTO");

			this.criarPreparedStatement(sql.toString());

			if(vo != null){
				if (vo.getGrupo() != null && Util.notEmpty(vo.getGrupo().getNuGrupo())) {
					this.setLong(vo.getGrupo().getNuGrupo());
				}
				
				if (vo.getServico() != null && Util.notEmpty(vo.getServico().getNuServico())) {
					this.setLong(vo.getServico().getNuServico());
				}
			}

			if (dtInicio != null) {
				this.setString(Data.formatar(dtInicio));
			}
			if (dtFim != null) {
				this.setString(Data.formatar(dtFim));
			}

			this.executarConsulta();

			List<EmailPosicionamentoVO> listaEmail = new ArrayList<EmailPosicionamentoVO>();

			while (this.percorrerResultSet()) {
				EmailPosicionamentoVO voResposta = this.preencherVOListar();
				listaEmail.add(voResposta);

				voResposta.getGrupo().setNoGrupo(this.getString("NO_GRUPO"));
				voResposta.getServico().setDeServico(this.getString("DE_SERVICO"));
			}

			return listaEmail;
		}
		catch (SQLException e) {
			throw new SystemException("Erro ao pesquisar email posicionamento.", e);
		}
		finally {
			this.fechar(null);
		}
	}

	/**
	 * Lista os emails para envio.
	 * @return List<EmailPosicionamentoVO>
	 * @throws SystemException
	 */
	public List<EmailPosicionamentoVO> listarEmailsEnvio() throws SystemException {
		try {
			StringBuilder sql = new StringBuilder(this.getSqlSelect())
			.append(" AND DT_ENVIO IS NULL ");
			
			this.criarPreparedStatement(sql.toString());
			this.executarConsulta();

			List<EmailPosicionamentoVO> listaEmail = new ArrayList<EmailPosicionamentoVO>();
			
			while (this.percorrerResultSet()) {
				listaEmail.add(this.preencherVOListar());
			}
			
			return listaEmail;
		}
		catch (SQLException e) {
			throw new SystemException("Erro ao pesquisar email posicionamento.", e);
		}
		finally {
			this.fechar(null);
		}
	}
}