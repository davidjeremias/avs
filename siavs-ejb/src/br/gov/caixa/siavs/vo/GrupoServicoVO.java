package br.gov.caixa.siavs.vo;

import java.util.LinkedList;
import java.util.List;

/**
 * <b>Title:</b> GrupoServicoVO <br>
 * <b>Description:</b> Relacionamento entre grupo e serviço. <br>
 * <b>Company:</b> Spread
 * 
 * @author ${classe.autor}
 * @version: $Revision: ${classe.versao} $, $Date: 14/06/2013$
 */
public class GrupoServicoVO extends AbstractVOSIAVS implements Comparable<GrupoServicoVO> {

	private static final long serialVersionUID = 1L;

	/**
	 * Relacionamento com grupo.
	 */
	private GrupoVO grupo;

	/**
	 * Relacionamento com serviço.
	 */
	private ServicoVO servico;

	/**
	 * Construtor padrão.
	 */
	public GrupoServicoVO() {

	}

	/**
	 * 
	 * @param grupo
	 * @param servico
	 */
	public GrupoServicoVO(GrupoVO grupo, ServicoVO servico) {
		this.setGrupo(grupo);
		this.setServico(servico);
	}

	/**
	 * @return GrupoVO
	 */
	public GrupoVO getGrupo() {
		return grupo;
	}

	/**
	 * @param grupo
	 *            GrupoVO
	 */
	public void setGrupo(GrupoVO grupo) {
		this.grupo = grupo;
	}

	/**
	 * @return ServicoVO
	 */
	public ServicoVO getServico() {
		return servico;
	}

	/**
	 * @param servico
	 *            ServicoVO
	 */
	public void setServico(ServicoVO servico) {
		this.servico = servico;
	}

	/// ****************************************************************

	/**
	 * @see br.com.spread.framework.vo.AbstractVO#getPk
	 */
	@Override
	public Object getPk() {
		List<Object> listaPk = new LinkedList<Object>();
		listaPk.add(this.getGrupo());
		listaPk.add(this.getServico());
		return listaPk;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((grupo == null) ? 0 : grupo.hashCode());
		result = prime * result + ((servico == null) ? 0 : servico.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		GrupoServicoVO other = (GrupoServicoVO) obj;
		if (grupo == null) {
			if (other.grupo != null)
				return false;
		} else if (!grupo.equals(other.grupo))
			return false;
		if (servico == null) {
			if (other.servico != null)
				return false;
		} else if (!servico.equals(other.servico))
			return false;
		return true;
	}

	@Override
	public int compareTo(GrupoServicoVO o) {
		if (o != null) {
			return o.getServico().getDeServico().compareTo(getServico().getDeServico());
		}
		return 0;
	}

}