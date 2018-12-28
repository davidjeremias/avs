package br.gov.caixa.siavs.view.jsf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.service.client.GrupoUnidadeSR;
import br.gov.caixa.siavs.service.client.UnidadeSR;
import br.gov.caixa.siavs.to.UnidadeVinculadoraTO;
import br.gov.caixa.siavs.vo.GrupoUnidadeVO;
import br.gov.caixa.siavs.vo.UnidadeVO;

/**
 * <b>Title:</b> VinculaServicoView <br>
 * <b>Description:</b> Classe que permite ao usuário vincular unidade ao grupo. <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 11/06/2013$
 */
@Named
@ConversationScoped
public class VinculaUnidadeView extends AbstractViewSIAVS<GrupoUnidadeVO> {

	private static final long serialVersionUID = 1L;
// ***********************************************************************************************************************************

	@Inject
	private ManterGrupoView manterGrupoView;

	@Inject
	private UnidadeSR unidadeSR;

	private List<UnidadeVinculadoraTO> unidadesVinculadoras;

	private boolean vinculadas;

	private String nuUnidadeFiltro;

	private String unidadeSelecionada;

	private List<UnidadeVO> unidadesSelecao = new ArrayList<UnidadeVO>();

	private UnidadeVinculadoraTO unidadeVinculadora;

// ***********************************************************************************************************************************

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getExibirTitulo()
	 */
	@Override
	public Boolean getExibirTitulo() {
		return false;
	}

	/**
	 * @see br.com.spread.framework.view.jsf.AbstractViewJSF#encerrarConversacao()
	 */
	@Override
	public Boolean encerrarConversacao() {
		return false;
	}

	/**
	 * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getTitulo()
	 */
	@Override
	public String getTitulo() {
		return "Unidade";
	}

	/**
	 * @see br.com.spread.framework.view.jsf.AbstractViewJSF#getTelaInicio
	 */
	@Override
	protected String getTelaInicio() {
		listarUnidadeTreeNode();
		listarTodasUnidade();
		return "vinculaUnidade";
	}

// ***********************************************************************************************************************************

	/**
	 * Salva os registros.
	 */
	public void salvar() {
		try {
		}
		catch (Throwable e) {
			this.tratarExcecao(e, null);
		}
	}

// ***********************************************************************************************************************************

	@Override
	public String irTelaInicio() {
		String retorno = super.irTelaInicio();
		//
		return retorno;
	}

	public List<UnidadeVinculadoraTO> getUnidadesVinculadoras() {
		return unidadesVinculadoras;
	}

	public void listarUnidadeTreeNode() {
		try {
			// executa consulta
			this.unidadesVinculadoras = ((GrupoUnidadeSR) this.getService())
				.listarUnidadeTreeNode(this.manterGrupoView.getVo());
		}
		catch (SystemException e) {
			this.tratarExcecao(e, getOperacao());
		}
		catch (BusinessException e) {
			this.tratarExcecao(e, getOperacao());
		}
	}

	public boolean isVinculadas() {
		return vinculadas;
	}

	public void setVinculadas(boolean vinculadas) {
		this.vinculadas = vinculadas;
	}

	public String getUnidadeSelecionada() {
		return unidadeSelecionada;
	}

	public void setUnidadeSelecionada(String unidadeSelecionada) {
		this.unidadeSelecionada = unidadeSelecionada;
	}

	public List<UnidadeVO> getUnidadesSelecao() {
		return unidadesSelecao;
	}

	public String getNuUnidadeFiltro() {
		return nuUnidadeFiltro;
	}

	public void setNuUnidadeFiltro(String nuUnidadeFiltro) {
		this.nuUnidadeFiltro = nuUnidadeFiltro;
	}

	public void listarTodasUnidade() {
		try {
			this.unidadesSelecao = this.unidadeSR.listar(new UnidadeVO());
		}
		catch (BusinessException e) {
			this.tratarExcecao(e, getOperacao());
		}
		catch (SystemException e) {
			this.tratarExcecao(e, getOperacao());
		}
	}

	public void salvarVinculoUnidade() {
		String[] unidade = this.unidadeSelecionada.split("@");
		GrupoUnidadeVO vo = new GrupoUnidadeVO();
		vo.setGrupo(this.manterGrupoView.getVo());
		vo.setUnidade(new UnidadeVO(new Integer(unidade[0]), new Integer(unidade[1])));
		vo.setDtInclusao(new Date());
		//
		if (this.vinculadas) {
			incluirVinculadas(vo);
		}
		else {
			incluir(vo);
		}
		//
		recarregar();
	}

	private void incluirVinculadas(GrupoUnidadeVO vo) {
		try {
			List<UnidadeVO> naoVinculadas = ((GrupoUnidadeSR) this.getService()).incluirVinculadas(vo);
			//
			String msg = this.getMensagem("MN001");
			if (naoVinculadas != null && naoVinculadas.size() > 0) {
				msg = getMsgNaoVinculadas(msg, naoVinculadas);
			}
			this.adicionarMsgInfo(msg);
		}
		catch (BusinessException e) {
			this.tratarExcecao(e, getOperacao());
		}
		catch (SystemException e) {
			this.tratarExcecao(e, getOperacao());
		}
	}

	private String getMsgNaoVinculadas(String msg, List<UnidadeVO> naoVinculadas) {
		StringBuilder codigos = new StringBuilder();
		for (UnidadeVO unidadeVO : naoVinculadas) {
			codigos.append(unidadeVO.getNuUnidade() + " ");
		}
		return msg + "\n" + "A(s) unidade(s) \"" + codigos.toString().trim() +
			"\" está(ão) vinculada(s) a outro grupo.";
	}

	private void recarregar() {
		this.nuUnidadeFiltro = null;
		this.unidadeSelecionada = null;
		this.vinculadas = false;
		listarUnidadeTreeNode();
	}

	public UnidadeVinculadoraTO getUnidadeVinculadora() {
		return unidadeVinculadora;
	}

	public void setUnidadeVinculadora(UnidadeVinculadoraTO unidadeVinculadora) {
		this.unidadeVinculadora = unidadeVinculadora;
		this.setVo(null);
	}

	public String excluir() {
		GrupoUnidadeVO vo = this.getVo();
		try {
			if (vo != null) {
				super.doExcluir(vo);
			}
			else {
				((GrupoUnidadeSR) getService()).excluirUnidadeTreeNode(this.unidadeVinculadora);
			}

			recarregar();
		}
		catch (BusinessException e) {
			this.tratarExcecao(e, getOperacao());
		}
		catch (SystemException e) {
			this.tratarExcecao(e, getOperacao());
		}

		return getRetorno();
	}

	public List<UnidadeVO> listarUnidadeNaoVinculada() {
		try {
			return this.unidadeSR.listarUnidadeNaoVinculada(this.manterGrupoView.getVo());
		}
		catch (BusinessException e) {
			this.tratarExcecao(e, getOperacao());
		}
		catch (SystemException e) {
			this.tratarExcecao(e, getOperacao());
		}
		return null;
	}
}
