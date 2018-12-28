package br.gov.caixa.siavs.vo;

import java.util.List;

/**
 * <b>Title:</b> ServicoVO <br>
 * <b>Description:</b> Permite ao Administrador manter as informações do Serviço. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 10/06/2013$
 */
public class ServicoVO extends AbstractVOSIAVS implements Comparable<ServicoVO>{

	private static final long serialVersionUID = 1L;

// ****************************************************************
	/**
	 * Identificador do serviço
	 */
	private Long nuServico;
	/**
	 * Descrição do serviço
	 */
	private String deServico;
	/**
	 * Nome do sistema
	 */
	private String noSistema;
	/**
	 * Ativa ou inativa o serviço; Valores possíveis: 0 - Não; 1 - Sim
	 */
	private Boolean icAtivo;
	/**
	 * Se o serviço é corporativo. Valores possíveis: 0 - Não; 1 - Sim
	 */
	private Boolean icCorporativo;
	/**
	 * Unidade responsável pelo relacionamento. Valores possíveis: 1 - GECBR; 2 - GECSP; 3 - GECRJ;
	 */
	private UnidadeVO unidadeRelacionamento;
	/**
	 * Unidade onde o sistema está hospedado. Valores possíveis: 1 - CEPTI RJ; 2 - CEPTI SP; 3 - CEPTI BR;
	 */
	private UnidadeVO unidadeProducao;
	/**
	 * Se o serviço é do grupo agência; Valores possíveis: 0 - Não; 1 - Sim
	 */
	private Boolean icAgencia;
	/**
	 * Nome do gestor do serviço.
	 */
	private String noGestor;
	/**
	 * Relacionamento com segmento.
	 */
	private List<ServicoSegmentoVO> listaSegmento;
// ****************************************************************
	/**
	 * Construtor padrão.
	 */
	public ServicoVO(){}

	/**
	 * Construtor com chave primária.
	 * @param nuServico Long
	 */
	public ServicoVO(Long nuServico){
		this.setNuServico(nuServico);
	}

	/**
	 * Construtor com chave única.
	 * @param deServico String
	 */
	public ServicoVO(String deServico){
		this.setDeServico(deServico);
	}
// ****************************************************************

	/**
	 * @return Long
	 */
	public Long getNuServico() {
		return nuServico;
	}

	/**
	 * @param nuServico Long
	 */
	public void setNuServico(Long nuServico) {
		this.nuServico = nuServico;
	}

	/**
	 * @return String
	 */
	public String getDeServico() {
		return deServico;
	}

	/**
	 * @param deServico String
	 */
	public void setDeServico(String deServico) {
		this.deServico = deServico;
	}

	/**
	 * @return String
	 */
	public String getNoSistema() {
		return noSistema;
	}

	/**
	 * @param noSistema String
	 */
	public void setNoSistema(String noSistema) {
		this.noSistema = noSistema;
	}

	/**
	 * @return Boolean
	 */
	public Boolean getIcAtivo() {
		if(icAtivo == null) {
			this.setIcAtivo(true);
		}
		return icAtivo;
	}

	/**
	 * @param icAtivo Boolean
	 */
	public void setIcAtivo(Boolean icAtivo) {
		this.icAtivo = icAtivo;
	}

	/**
	 * @return Boolean
	 */
	public Boolean getIcCorporativo() {
		if(icCorporativo == null) {
			this.setIcCorporativo(false);
		}
		return icCorporativo;
	}

	/**
	 * @param icCorporativo Boolean
	 */
	public void setIcCorporativo(Boolean icCorporativo) {
		this.icCorporativo = icCorporativo;
	}

	/**
	 * @return UnidadeVO
	 */
	public UnidadeVO getUnidadeRelacionamento() {
		return unidadeRelacionamento;
	}

	/**
	 * @param unidadeRelacionamento UnidadeVO
	 */
	public void setUnidadeRelacionamento(UnidadeVO unidadeRelacionamento) {
		this.unidadeRelacionamento = unidadeRelacionamento;
	}

	/**
	 * @return UnidadeVO
	 */
	public UnidadeVO getUnidadeProducao() {
		return unidadeProducao;
	}

	/**
	 * @param unidadeProducao UnidadeVO
	 */
	public void setUnidadeProducao(UnidadeVO unidadeProducao) {
		this.unidadeProducao = unidadeProducao;
	}

	/**
	 * @return Boolean
	 */
	public Boolean getIcAgencia() {
		if(icAgencia == null) {
			this.setIcAgencia(false);
		}
		return icAgencia;
	}

	/**
	 * @param icAgencia Boolean
	 */
	public void setIcAgencia(Boolean icAgencia) {
		this.icAgencia = icAgencia;
	}

	/**
	 * @return String
	 */
	public String getNoGestor() {
		return noGestor;
	}

	/**
	 * @param noGestor String
	 */
	public void setNoGestor(String noGestor) {
		this.noGestor = noGestor;
	}

	/**
	 * @return List<ServicoSegmentoVO>
	 */
	public List<ServicoSegmentoVO> getListaSegmento() {
		return listaSegmento;
	}

	/**
	 * @param listaSegmento List<ServicoSegmentoVO>
	 */
	public void setListaSegmento(List<ServicoSegmentoVO> listaSegmento) {
		this.listaSegmento = listaSegmento;
	}

// ****************************************************************

	/**
	 * @see br.com.spread.framework.vo.AbstractVO#getPk
	 */
	@Override
	public Object getPk() {
		return this.getNuServico();
	}
	
// ****************************************************************

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nuServico == null) ? 0 : nuServico.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServicoVO other = (ServicoVO) obj;
		if (nuServico == null) {
			if (other.nuServico != null)
				return false;
		}
		else if (!nuServico.equals(other.nuServico))
			return false;
		return true;
	}

	@Override
	public int compareTo(ServicoVO o) {
		if (o != null){
			return getDeServico().compareTo(o.getDeServico());
		}
		return 0;
	}
}