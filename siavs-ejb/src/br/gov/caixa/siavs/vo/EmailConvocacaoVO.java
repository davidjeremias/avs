package br.gov.caixa.siavs.vo;

/**
 * <b>Title:</b> EmailConvocacaoVO <br>
 * <b>Description:</b> Permite manter as informações do e-mail de convocação. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 04/07/2013$
 */
public class EmailConvocacaoVO extends AbstractVOSIAVS {

	private static final long serialVersionUID = 1L;

// ****************************************************************
	/**
	 * Identificador do email de convocação.
	 */
	private Long nuEmailConvocacao;
	/**
	 * Assunto do e-mail que será enviado.
	 */
	private String deAssunto;
	/**
	 * Conteúdo do e-mail que será enviado.
	 */
	private String deConteudo;
	/**
	 * Relacionamento com grupo.
	 */
	private GrupoVO grupo;
// ****************************************************************
	/**
	 * Construtor padrão.
	 */
	public EmailConvocacaoVO(){}

	/**
	 * Construtor com chave primária.
	 * @param nuEmailConvocacao Long
	 */
	public EmailConvocacaoVO(Long nuEmailConvocacao){
		this.setNuEmailConvocacao(nuEmailConvocacao);
	}

	/**
	 * Construtor com chave única.
	 * @param grupo GrupOVO
	 */
	
	public EmailConvocacaoVO(GrupoVO grupo){
		this.setGrupo(grupo);
	}
	
// ****************************************************************

	/**
	 * @return Long
	 */
	public Long getNuEmailConvocacao() {
		return nuEmailConvocacao;
	}

	/**
	 * @param nuEmailConvocacao Long
	 */
	public void setNuEmailConvocacao(Long nuEmailConvocacao) {
		this.nuEmailConvocacao = nuEmailConvocacao;
	}

	/**
	 * @return String
	 */
	public String getDeAssunto() {
		return deAssunto;
	}

	/**
	 * @param deAssunto String
	 */
	public void setDeAssunto(String deAssunto) {
		this.deAssunto = deAssunto;
	}

	/**
	 * @return String
	 */
	public String getDeConteudo() {
		return deConteudo;
	}

	/**
	 * @param deConteudo String
	 */
	public void setDeConteudo(String deConteudo) {
		this.deConteudo = deConteudo;
	}

	/**
	 * @return GrupoVO
	 */
	public GrupoVO getGrupo() {
		return grupo;
	}

	/**
	 * @param grupo GrupoVO
	 */
	public void setGrupo(GrupoVO grupo) {
		this.grupo = grupo;
	}

// ****************************************************************

	/**
	 * @see br.com.spread.framework.vo.AbstractVO#getPk
	 */
	@Override
	public Object getPk() {
		return this.getNuEmailConvocacao();
	}
}