package br.gov.caixa.siavs.view.jsf;

import java.util.List;

import br.gov.caixa.siavs.vo.AvaliacaoVO;
import br.gov.caixa.siavs.vo.NoticiaVO;

/**
 * <b>Title:</b> AvaliaServicoBean <br>
 * <b>Description:</b> Bean que representa os dados da tela de AvaliaServico. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: $, $Date: $
 */
public class AvaliaServicoBean {

	private Boolean usouTentouUsar;
	private AvaliacaoVO avaliacaoVO;
	private List<NoticiaVO> listaNoticia;
	private Boolean jaAvaliado;

	/**
	 * @return Boolean
	 */
	public Boolean getUsouTentouUsar() {
		return usouTentouUsar;
	}

	/**
	 * @param usouTentouUsar Boolean
	 */
	public void setUsouTentouUsar(Boolean usouTentouUsar) {
		this.usouTentouUsar = usouTentouUsar;
	}

	/**
	 * @return AvaliacaoVO
	 */
	public AvaliacaoVO getAvaliacaoVO() {
		return avaliacaoVO;
	}

	/**
	 * @param avaliacaoVO AvaliacaoVO
	 */
	public void setAvaliacaoVO(AvaliacaoVO avaliacaoVO) {
		this.avaliacaoVO = avaliacaoVO;
	}

	/**
	 * @return List<NoticiaVO>
	 */
	public List<NoticiaVO> getListaNoticia() {
		return listaNoticia;
	}

	/**
	 * @param listaNoticia List<NoticiaVO>
	 */
	public void setListaNoticia(List<NoticiaVO> listaNoticia) {
		this.listaNoticia = listaNoticia;
	}

	/**
	 * @return Boolean
	 */
	public Boolean getJaAvaliado() {
		return jaAvaliado;
	}

	/**
	 * @param jaAvaliado Boolean
	 */
	public void setJaAvaliado(Boolean jaAvaliado) {
		this.jaAvaliado = jaAvaliado;
	}
}