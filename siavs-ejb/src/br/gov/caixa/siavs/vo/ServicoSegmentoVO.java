package br.gov.caixa.siavs.vo;

import java.util.LinkedList;
import java.util.List;

/**
 * <b>Title:</b> ServicoSegmentoVO <br>
 * <b>Description:</b> Relacionamento entre serviço e segmento. <br>
 * <b>Company:</b> Spread
 * @author ${classe.autor}
 * @version: $Revision: ${classe.versao} $, $Date: 10/06/2013$
 */
public class ServicoSegmentoVO extends AbstractVOSIAVS {

	private static final long serialVersionUID = 1L;

// ****************************************************************
	/**
	 * Relacionamento com serviço.
	 */
	private ServicoVO servico;
	/**
	 * Relacionamento com segmento.
	 */
	private SegmentoVO segmento;
// ****************************************************************
	/**
	 * Construtor padrão.
	 */
	public ServicoSegmentoVO(){}
	
	/**
	 * Construtor com chave primária.
	 * @param servico ServicoVO
	 * @param segmento SegmentoVO
	 */
	public ServicoSegmentoVO(ServicoVO servico, SegmentoVO segmento){
		this.setServico(servico);
		this.setSegmento(segmento);
	}
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
	 * @return SegmentoVO
	 */
	public SegmentoVO getSegmento() {
		return segmento;
	}

	/**
	 * @param segmento SegmentoVO
	 */
	public void setSegmento(SegmentoVO segmento) {
		this.segmento = segmento;
	}

// ****************************************************************

	/**
	 * @see br.com.spread.framework.vo.AbstractVO#getPk
	 */
	@Override
	public Object getPk() {
		List<Object> listaPk = new LinkedList<Object>();
		listaPk.add(this.getServico());
		listaPk.add(this.getSegmento());
		return listaPk;
	}
}