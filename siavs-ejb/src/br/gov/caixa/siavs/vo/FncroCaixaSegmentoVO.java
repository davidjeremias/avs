package br.gov.caixa.siavs.vo;

import java.util.LinkedList;
import java.util.List;

/**
 * <b>Title:</b> FncroCaixaSegmento <br>
 * <b>Description:</b> Relacionamento entre funcionário caixa local e segmento. <br>
 * <b>Company:</b> Spread
 * @author ${classe.autor}
 * @version: $Revision: ${classe.versao} $, $Date: 10/06/2013$
 */
public class FncroCaixaSegmentoVO extends AbstractVOSIAVS {

	private static final long serialVersionUID = 1L;

// ****************************************************************
	/**
	 * Relacionamento com funcionário caixa local.
	 */
	private FuncionarioCaixaLocalVO funcionarioCaixaLocal;
	/**
	 * Relacionamento com segmento.
	 */
	private SegmentoVO segmento;
// ****************************************************************
	/**
	 * Construtor padrão.
	 */
	public FncroCaixaSegmentoVO(){}
	
	/**
	 * Construtor com chave primária.
	 * @param funcionarioCaixaLocal FuncionarioCaixaLocalVO
	 * @param segmento SegmentoVO
	 */
	public FncroCaixaSegmentoVO(FuncionarioCaixaLocalVO funcionarioCaixaLocal, SegmentoVO segmento){
		this.setFuncionarioCaixaLocal(funcionarioCaixaLocal);
		this.setSegmento(segmento);
	}
// ****************************************************************

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
		listaPk.add(this.getFuncionarioCaixaLocal());
		listaPk.add(this.getSegmento());
		return listaPk;
	}
}