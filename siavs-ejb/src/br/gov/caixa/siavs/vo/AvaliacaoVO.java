package br.gov.caixa.siavs.vo;

import java.util.Date;

/**
 * <b>Title:</b> AvaliacaoVO <br>
 * <b>Description:</b> Permite ao Avaliador realizar avaliação dos serviços relacionados ao seu grupo. Para o grupo agência, serão os serviços do segmento do avaliador <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 07/06/2013$
 */
public class AvaliacaoVO extends AbstractVOSIAVS {

	private static final long serialVersionUID = 1L;

// ****************************************************************
	/**
	 * Identificador da Avaliação do serviço
	 */
	private Long nuAvaliacao;
	/**
	 * Nota de avaliação
	 */
	private Double vrNotaAvaliacao;
	/**
	 * Comentário sobre a avaliação.
	 */
	private String deComentario;
	/**
	 * Data e hora em que a avaliação foi feita
	 */
	private Date dtAvaliacao;
	/**
	 * Relacionamento com serviço.
	 */
	private ServicoVO servico;
	/**
	 * Relacionamento com problema.
	 */
	private ProblemaVO problema;
	/**
	 * Permite armazernar o Grupo ao qual o Serviço estava vinculado quando foi feita a Avaliação. Essa informação é armazenada separadamente porque o serviço pode mudar de grupo.
	 */
	private GrupoVO grupo;
	/**
	 * Relacionamento com funcionário caixa local.
	 */
	private FuncionarioCaixaLocalVO funcionarioCaixaLocal;
// ****************************************************************
	/**
	 * Construtor padrão.
	 */
	public AvaliacaoVO(){}

	/**
	 * Construtor com chave primária.
	 * @param nuAvaliacao Long
	 */
	public AvaliacaoVO(Long nuAvaliacao){
		this.setNuAvaliacao(nuAvaliacao);
	}
// ****************************************************************

	/**
	 * @return Long
	 */
	public Long getNuAvaliacao() {
		return nuAvaliacao;
	}

	/**
	 * @param nuAvaliacao Long
	 */
	public void setNuAvaliacao(Long nuAvaliacao) {
		this.nuAvaliacao = nuAvaliacao;
	}

	/**
	 * @return Double
	 */
	public Double getVrNotaAvaliacao() {
		return vrNotaAvaliacao;
	}

	/**
	 * @param vrNotaAvaliacao Double
	 */
	public void setVrNotaAvaliacao(Double vrNotaAvaliacao) {
		this.vrNotaAvaliacao = vrNotaAvaliacao;
	}

	/**
	 * @return String
	 */
	public String getDeComentario() {
		return deComentario;
	}

	/**
	 * @param deComentario String
	 */
	public void setDeComentario(String deComentario) {
		this.deComentario = deComentario;
	}

	/**
	 * @return Date
	 */
	public Date getDtAvaliacao() {
		return dtAvaliacao;
	}

	/**
	 * @param dtAvaliacao Date
	 */
	public void setDtAvaliacao(Date dtAvaliacao) {
		this.dtAvaliacao = dtAvaliacao;
	}

	/**
	 * @return ServicoVO
	 */
	public ServicoVO getServico() {
		return servico;
	}

	/**
	 * @param servico ServicoVO
	 */
	public void setServico(ServicoVO servico) {
		this.servico = servico;
	}

	/**
	 * @return ProblemaVO
	 */
	public ProblemaVO getProblema() {
		return problema;
	}

	/**
	 * @param problema ProblemaVO
	 */
	public void setProblema(ProblemaVO problema) {
		this.problema = problema;
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

	/**
	 * @return FuncionarioCaixaLocalVO
	 */
	public FuncionarioCaixaLocalVO getFuncionarioCaixaLocal() {
		return funcionarioCaixaLocal;
	}

	/**
	 * @param funcionarioCaixaLocal FuncionarioCaixaLocalVO
	 */
	public void setFuncionarioCaixaLocal(FuncionarioCaixaLocalVO funcionarioCaixaLocal) {
		this.funcionarioCaixaLocal = funcionarioCaixaLocal;
	}

// ****************************************************************

	/**
	 * @see br.com.spread.framework.vo.AbstractVO#getPk
	 */
	@Override
	public Object getPk() {
		return this.getNuAvaliacao();
	}
}