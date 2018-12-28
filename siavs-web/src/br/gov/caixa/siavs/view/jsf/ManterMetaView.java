package br.gov.caixa.siavs.view.jsf;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.service.client.AvaliacaoSR;
import br.gov.caixa.siavs.service.client.MetaSR;
import br.gov.caixa.siavs.vo.MetaVO;

/**
 * <b>Title:</b> ManterMetaView <br>
 * <b>Description:</b> Classe que permite ao usuário manter as informações do
 * meta. <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 11/06/2013$
 */
@Named
@ConversationScoped
public class ManterMetaView extends AbstractViewSIAVS<MetaVO> {

    private static final long serialVersionUID = 1L;
    @Inject
    private AvaliacaoSR avaliacaoSR;
    @Inject
    private ManterGrupoView manterGrupoView;

    private boolean carregarListaMetas;

    /**
     * @return the carregarListaMetas
     */
    public boolean isCarregarListaMetas() {
        return carregarListaMetas;
    }

    /**
     * @param carregarListaMetas
     *            the carregarListaMetas to set
     */
    public void setCarregarListaMetas(boolean carregarListaMetas) {
        this.carregarListaMetas = carregarListaMetas;
    }

    public String irTelaInicio() {

        List<MetaVO> listaMeta = new ArrayList<MetaVO>();

        if (isCarregarListaMetas()) {
            try {
                // Define os anos
                Integer anoPrimeiraAvalicao = avaliacaoSR.consultarPrimeiroAnoAvaliacao(manterGrupoView.getVo());
                Integer anoAtual = new GregorianCalendar().get(GregorianCalendar.YEAR);
                if (!Util.notEmpty(anoPrimeiraAvalicao)) {
                    anoPrimeiraAvalicao = anoAtual;
                }

                // Lista as metas cadastradas para o grupo
                List<MetaVO> listaMetaCadastrada = this.getService().listar(new MetaVO(manterGrupoView.getVo(), null));

                // Monta os dados
                for (int r = anoAtual; r >= anoPrimeiraAvalicao; r--) {
                    MetaVO meta = new MetaVO(manterGrupoView.getVo(), r);
                    listaMeta.add(meta);

                    // Carrega as informações caso já tenham sido gravadas
                    for (MetaVO metaCadastrada : listaMetaCadastrada) {
                        if (meta.getAaCompetencia().equals(metaCadastrada.getAaCompetencia())
                                && meta.getGrupo().getNuGrupo().equals(metaCadastrada.getGrupo().getNuGrupo())) {
                            meta.setNuMeta(metaCadastrada.getNuMeta());
                            meta.setVrNotaMeta(metaCadastrada.getVrNotaMeta());
                            break;
                        }
                    }
                }
            } catch (Throwable e) {
                this.tratarExcecao(e, null);
            }

            // Armazena os registros
            this.setListaVo(listaMeta);

        }

        return super.irTelaInicio();
    }

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
     * @see br.gov.caixa.siavs.view.jsf.AbstractViewSIAVS#getTelaInicio()
     */
    @Override
    protected String getTelaInicio() {
        return this.getNomePadrao();
    }

    // ***********************************************************************************************************************************

    /**
     * @see br.com.spread.framework.view.basic.AbstractViewBasic#getListaVo()
     */
    @Override
    public List<MetaVO> getListaVo() {

        if (manterGrupoView == null || manterGrupoView.getVo() == null
                || !Util.notEmpty(manterGrupoView.getVo().getNuGrupo())) {
            this.adicionarMsgInfo("Deve ser selecionado um grupo para carregar as metas.");
            return new ArrayList<MetaVO>();
        }

        return super.getListaVo();
    }

    // ***********************************************************************************************************************************

    /**
     * Salva as metas
     */
    public void salvar() {
        try {
            ((MetaSR) this.getService()).salvar(super.getListaVo());
            this.tratarSucesso(null);
        } catch (Throwable e) {
            this.tratarExcecao(e, null);
        }
    }

    // ***********************************************************************************************************************************

}