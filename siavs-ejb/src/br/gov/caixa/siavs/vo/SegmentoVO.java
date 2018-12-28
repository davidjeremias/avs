package br.gov.caixa.siavs.vo;

/**
 * <b>Title:</b> SegmentoVO <br>
 * <b>Description:</b> Permite ao Administrador manter as informações gerais dos segmentos existentes nas agências da CAIXA. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 07/06/2013$
 */
public class SegmentoVO extends AbstractVOSIAVS {

	private static final long serialVersionUID = 1L;

// ****************************************************************
	/**
	 * Identificador do Segmento
	 */
	private Long nuSegmento;
	/**
	 * Nome do segmento
	 */
	private String noSegmento;
// ****************************************************************
	/**
	 * Construtor padrão.
	 */
	public SegmentoVO(){}

	/**
	 * Construtor com chave primária.
	 * @param nuSegmento Long
	 */
	public SegmentoVO(Long nuSegmento){
		this.setNuSegmento(nuSegmento);
	}

	/**
	 * Construtor com chave única.
	 * @param noSegmento String
	 */
	public SegmentoVO(String noSegmento){
		this.setNoSegmento(noSegmento);
	}
// ****************************************************************

	/**
	 * @return Long
	 */
	public Long getNuSegmento() {
		return nuSegmento;
	}

	/**
	 * @param nuSegmento Long
	 */
	public void setNuSegmento(Long nuSegmento) {
		this.nuSegmento = nuSegmento;
	}

	/**
	 * @return String
	 */
	public String getNoSegmento() {
		return noSegmento;
	}

	/**
	 * @param noSegmento String
	 */
	public void setNoSegmento(String noSegmento) {
		this.noSegmento = noSegmento;
	}

// ****************************************************************

	/**
	 * @see br.com.spread.framework.vo.AbstractVO#getPk
	 */
	@Override
	public Object getPk() {
		return this.getNuSegmento();
	}
}