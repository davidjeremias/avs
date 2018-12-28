package br.gov.caixa.siavs.to;

import java.io.Serializable;
import java.util.List;

import br.gov.caixa.siavs.vo.GrupoUnidadeVO;
import br.gov.caixa.siavs.vo.UnidadeVO;

/**
 * <b>Title:</b> UnidadeVinculadoraTO <br>
 * <b>Description:</b> <br>
 * <b>Company:</b> Spread
 * 
 * @author werbth.rocha
 * @version: $Revision: $, $Date: $
 */
public class UnidadeVinculadoraTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private GrupoUnidadeVO grupoUnidadeVO;
	private UnidadeVO unidadeVinculadora;
	private boolean root;
	private List<UnidadeVinculadoraTO> unidadesVinculadas;

	public GrupoUnidadeVO getGrupoUnidadeVO() {
		return grupoUnidadeVO;
	}

	public void setGrupoUnidadeVO(GrupoUnidadeVO grupoUnidadeVO) {
		this.grupoUnidadeVO = grupoUnidadeVO;
	}

	public UnidadeVO getUnidadeVinculadora() {
		return unidadeVinculadora;
	}

	public void setUnidadeVinculadora(UnidadeVO unidadeVinculadora) {
		this.unidadeVinculadora = unidadeVinculadora;
	}
	
	public boolean isRoot() {
		return root;
	}
	
	public void setRoot(boolean root) {
		this.root = root;
	}
	
	public List<UnidadeVinculadoraTO> getUnidadesVinculadas() {
		return unidadesVinculadas;
	}
	
	public void setUnidadesVinculadas(List<UnidadeVinculadoraTO> unidadesVinculadas) {
		this.unidadesVinculadas = unidadesVinculadas;
	}
}
