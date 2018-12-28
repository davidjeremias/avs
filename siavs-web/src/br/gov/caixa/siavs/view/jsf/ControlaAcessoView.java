package br.gov.caixa.siavs.view.jsf;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.global.DominioSIAVS;
import br.gov.caixa.siavs.global.DominioSIAVS.NivelAcesso;
import br.gov.caixa.siavs.service.client.FuncionarioCaixaSR;
import br.gov.caixa.siavs.service.client.GrupoSR;
import br.gov.caixa.siavs.service.client.GrupoServicoSR;
import br.gov.caixa.siavs.service.client.GrupoUnidadeSR;
import br.gov.caixa.siavs.service.client.ServicoSR;
import br.gov.caixa.siavs.vo.FuncionarioCaixaLocalVO;
import br.gov.caixa.siavs.vo.FuncionarioCaixaVO;
import br.gov.caixa.siavs.vo.GrupoServicoVO;
import br.gov.caixa.siavs.vo.GrupoUnidadeVO;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.ServicoVO;
import br.gov.caixa.siavs.vo.UsuarioVO;

/**
 * <b>Title:</b> ControlaAcessoView <br>
 * <b>Description:</b> Classe responsável pelo acesso aos dados dos usuário.
 * <br>
 * <b>Company:</b> Spread
 *
 * @author leandro.vieira
 * @version: $Revision: $, $Date: $
 */
@Named(value = "controle")
@SessionScoped
public class ControlaAcessoView extends AbstractViewSIAVS<UsuarioVO> {

	private static final long serialVersionUID = 1L;

	// ***********************************************************************************************************************************

	private GrupoVO grupo;

	@Inject
	private GrupoSR grupoSR;
	@Inject
	private ServicoSR servicoSR;
	@Inject
	private GrupoServicoSR grupoServicoSR;
	@Inject
	private GrupoUnidadeSR grupoUnidadeSR;



	// ***********************************************************************************************************************************



	/**
	 * @see br.com.spread.framework.view.basic.AbstractViewBasic#getVo()
	 */
	@Override
	public UsuarioVO getVo() {

		if (super.getVo() == null) {
			this.limparVO();
			this.setVo(super.getUsuarioLogado());
		}
		return super.getVo();
	}

	// ***********************************************************************************************************************************

	/**
	 * Retorna o grupo ao qual a unidade do usuário está vinculado.
	 *
	 * @return GrupoVO
	 */
	public GrupoVO getGrupoUnidadeUsuario() {
		if (grupo == null && this.getUsuarioLogado() != null) {
			try {
				GrupoVO grupoVO = new GrupoVO();
				grupoVO.setIcAtivo(true);
				grupoVO.setNuGrupo(1L);
				grupoVO.setNoGrupo("Agência");
				grupoVO.setIcAgencia(true);
				// Recupera o grupo vinculado a unidade do usuário
				GrupoUnidadeVO grupoUnidadeVO = new GrupoUnidadeVO(grupoVO, this.getUsuarioLogado().getFuncionarioCaixaLocal().getUnidade());

				if (grupoUnidadeVO != null) {
					this.setGrupo(grupoUnidadeVO.getGrupo());
				}
			} catch (Throwable e) {
				this.tratarExcecao(e, null);
			}
		}

		GrupoVO grupoTemp = null;
		if (grupo != null) {
			if (!grupo.getNuGrupo().equals("0")) {
				grupoTemp = new GrupoVO(grupo.getNuGrupo());
				grupoTemp.setNoGrupo(grupo.getNoGrupo());
				grupoTemp.setIcAgencia(grupo.getIcAgencia());
			}
		}

		return grupoTemp;
	}

	/**
	 * Retorna os dados do funcionário caixa logado.
	 *
	 * @return FuncionarioCaixaLocalVO
	 */
	public FuncionarioCaixaLocalVO getFuncionarioCaixaLocalVO() {
		return this.getUsuarioLogado().getFuncionarioCaixaLocal();
	}

	/**
	 * Se o usuário está logado.
	 *
	 * @return boolean
	 */
	public boolean isUsuarioLogado() {


		return this.getUsuarioLogado() != null;
	}

	/**
	 * Retorna o nome do usuário logado.
	 *
	 * @return String
	 */
	public String getNome() {
		return this.getUsuarioLogado() != null ? this.getUsuarioLogado().getFuncionarioCaixaLocal().getFuncionarioCaixa().getNoFuncionario() : "";
	}

	/**
	 * Retorna a data.
	 *
	 * @return String
	 */
	public Date getData() {
		return new Date();
	}

	/**
	 * Invalida a sessão.
	 *
	 * @return String
	 */
	public String sair() {
		this.getHttpSession().invalidate();
		this.setVo(null);
		this.setGrupo(null);
		return "home";
	}

	/**
	 * Retorna se o usuário é administrador
	 *
	 * @return boolean
	 */
	public boolean isAdministrador() {
		return this.getVo() != null && NivelAcesso.AMINISTRADOR.getCodigo().equals(this.getVo().getIcAcesso());
	}

	/**
	 * Retorna se o usuário é publicador.
	 *
	 * @return boolean
	 */
	public boolean isPublicador() {
		return this.getVo() != null && NivelAcesso.PUBLICADOR.getCodigo().equals(this.getVo().getIcAcesso());
	}

	/**
	 * Retorna se o usuário é publicador de TI.
	 *
	 * @return boolean
	 */
	public boolean isPublicadorTI() {
		return this.getVo() != null && NivelAcesso.PUBLICADOR_TI.getCodigo().equals(this.getVo().getIcAcesso());
	}

	/**
	 * Retorna se o usuário é Avaliador.
	 *
	 * @return boolean
	 */
	public boolean isAvaliador() {
		return this.getGrupoUnidadeUsuario() != null;
	}

	/**
	 * Retorna se o usuário é Gestor PV.
	 *
	 * @return boolean
	 */
	public boolean isGestorPV() {
		return this.getFuncionarioCaixaLocalVO() != null
				&& (this.getFuncionarioCaixaLocalVO().getUnidade().getNuTipoUnidade() == 8
						|| this.getFuncionarioCaixaLocalVO().getUnidade().getNuTipoUnidade() == 9
						|| this.getFuncionarioCaixaLocalVO().getUnidade().getNuTipoUnidade() == 56)
				&& DominioSIAVS.TIPO_FUNCAO_GESTOR == this.getFuncionarioCaixaLocalVO().getFuncionarioCaixa()
						.getNuTipoFuncao();
	}

	/**
	 * Retorna a lista com os serviços disponíveis dependendo do usuário.
	 *
	 * @param grupoVO
	 *            GrupoVO
	 * @return List<ServicoVO>
	 */
	public List<ServicoVO> getListaServico(GrupoVO grupoVO) {
		List<ServicoVO> listaServico = new LinkedList<ServicoVO>();

		try {
			// Lista os serviços ativos
			ServicoVO servicoVO = new ServicoVO();
			servicoVO.setIcAtivo(true);

			if (this.isPublicador() || grupoVO != null) {
				GrupoVO grupoTemp = grupoVO;

				if (grupoTemp == null) {
					grupoTemp = this.getGrupoUnidadeUsuario();
				}

				if (grupoTemp != null && Util.notEmpty(grupoTemp.getNuGrupo())) {
					// Lista apenas os serviços vinculados ao grupo "OU" for
					// informado algum grupo
					List<GrupoServicoVO> listaGrupoServico = grupoServicoSR
							.listar(new GrupoServicoVO(grupoTemp, servicoVO));
					for (GrupoServicoVO grupoServicoVO : listaGrupoServico) {
						listaServico.add(grupoServicoVO.getServico());
					}
				}
			} else {
				listaServico = servicoSR.listar(servicoVO);
			}
		} catch (Throwable e) {
			this.tratarExcecao(e, null);
		}

		return listaServico;
	}

	/**
	 * Retorna a lista com os grupos disponíveis dependendo do usuário.
	 *
	 * @return List<GrupoVO>
	 */
	public List<GrupoVO> getListaGrupo() {
		List<GrupoVO> listaGrupo = new LinkedList<GrupoVO>();

		try {
			// Lista os grupos ativos
			GrupoVO grupoVO = new GrupoVO();
			grupoVO.setIcAtivo(true);
			// tirar o grupo todos, pois não existe so referencia a outros todos
			// if(!grupoVO.getNuGrupo().equals(0)){
			if (this.isPublicador()) {
				if (this.getGrupoUnidadeUsuario() != null) {
					listaGrupo.add(this.getGrupoUnidadeUsuario());
				}
			} else {
				listaGrupo = grupoSR.listar(grupoVO);
			}
			// }
		} catch (Throwable e) {
			this.tratarExcecao(e, null);
		}
		// teste remover um grupo igual a 0
		// listaGrupo.remove(2);
		return listaGrupo;

	}

	// ***********************************************************************************************************************************

	/**
	 * @param grupo
	 *            GrupoVO
	 */
	private void setGrupo(GrupoVO grupo) {
		this.grupo = grupo;
	}

	/**
	 * Retorna alguma mensagem de erro na sessão.
	 *
	 * @return String
	 */
	public String getMsgErro() {
		try {
			return (String) this.getHttpSession().getAttribute("msgErro");
		} finally {
			this.getHttpSession().removeAttribute("msgErro");
		}
	}

}