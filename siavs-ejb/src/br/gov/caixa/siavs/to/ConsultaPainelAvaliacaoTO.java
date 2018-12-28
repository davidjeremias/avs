package br.gov.caixa.siavs.to;

import java.util.Date;
import java.util.Map;

import br.gov.caixa.siavs.vo.ServicoVO;

/**
 * <b>Title:</b> ConsultaPainelAvaliacaoTO <br>
 * <b>Description:</b> <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: $, $Date: $
 */
public class ConsultaPainelAvaliacaoTO extends AbstractTOSIAVS {

	private static final long serialVersionUID = 1L;

// ****************************************************************

	private ServicoVO servico;
	private Double mediaNotaAvaliacao;
	private Long qtdeAvaliacao;
	private Map<Long, Integer> mapProblema;
	private Date data;

// ****************************************************************
	
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
	 * @return Double
	 */
	public Double getMediaNotaAvaliacao() {
		return mediaNotaAvaliacao;
	}

	/**
	 * @param mediaNotaAvaliacao Double
	 */
	public void setMediaNotaAvaliacao(Double mediaNotaAvaliacao) {
		this.mediaNotaAvaliacao = mediaNotaAvaliacao;
	}

	/**
	 * @return Long
	 */
	public Long getQtdeAvaliacao() {
		return qtdeAvaliacao;
	}

	/**
	 * @param qtdeAvaliacao Long
	 */
	public void setQtdeAvaliacao(Long qtdeAvaliacao) {
		this.qtdeAvaliacao = qtdeAvaliacao;
	}

	/**
	 * @return Map<Long,Integer>
	 */
	public Map<Long, Integer> getMapProblema() {
		return mapProblema;
	}

	/**
	 * @param mapProblema Map<Long,Integer>
	 */
	public void setMapProblema(Map<Long, Integer> mapProblema) {
		this.mapProblema = mapProblema;
	}

	/**
	 * Retorna grupamento.
	 */
	public Date getData() {
		return this.data;
	}

	/**
	 * @param data Preenche grupamento
	 */
	public void setData(Date data) {
		this.data = data;
	}
}