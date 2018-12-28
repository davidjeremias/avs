package br.gov.caixa.siavs.vo;

import java.util.List;

/**
 * <b>Title:</b> GrupoVO <br>
 * <b>Description:</b> Permite manter as informações do Grupo. <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 07/06/2013$
 */
public class GrupoVO extends AbstractVOSIAVS {

    private static final long serialVersionUID = 1L;

    // ****************************************************************
    /**
     * Identificador do Grupo
     */
    private Long nuGrupo;
    /**
     * Apresenta o nome do grupo
     */
    private String noGrupo;
    /**
     * Se habilita ou não o envio de email de convocação aos usuários vinculados
     * ao grupo. Valores possíveis: 0 - Não; 1 - Sim
     */
    private Boolean icEnviaEmailConvocacao;
    /**
     * Frequência de avaliação do grupo; Valores possíveis: 1 - Diário; 2 -
     * Semanal; 3 - Quinzenal; 4 - Mensal;
     */
    private String icFrequenciaAvaliacao;
    /**
     * Ativa ou inativa o grupo; Valores possíveis: 0 - Não; 1 - Sim
     */
    private Boolean icAtivo;
    /**
     * Se o Grupo é Agência. Será apenas o primeiro Grupo cadastrado. Valores
     * possíveis: 0 - Não; 1 - Sim
     */
    private Boolean icAgencia;
    /**
     * Relacionamento com serviço.
     */
    private List<ServicoVO> listaServico;
    /**
     * Relacionamento com grupoUnidade.
     */
    private List<GrupoUnidadeVO> listaGrupoUnidade;

    // ****************************************************************
    /**
     * Construtor padrão.
     */
    public GrupoVO() {
    }

    /**
     * Construtor com chave primária.
     * 
     * @param nuGrupo
     *            Long
     */
    public GrupoVO(Long nuGrupo) {
        this.setNuGrupo(nuGrupo);
    }

    /**
     * Construtor com chave única.
     * 
     * @param noGrupo
     *            String
     */
    public GrupoVO(String noGrupo) {
        this.setNoGrupo(noGrupo);
    }

    // ****************************************************************

    /**
     * @return Long
     */
    public Long getNuGrupo() {
        return nuGrupo;
    }

    /**
     * @param nuGrupo
     *            Long
     */
    public void setNuGrupo(Long nuGrupo) {
        this.nuGrupo = nuGrupo;
    }

    /**
     * @return String
     */
    public String getNoGrupo() {
        return noGrupo;
    }

    /**
     * @param noGrupo
     *            String
     */
    public void setNoGrupo(String noGrupo) {
        this.noGrupo = noGrupo;
    }

    /**
     * @return Boolean
     */
    public Boolean getIcEnviaEmailConvocacao() {
        if (icEnviaEmailConvocacao == null) {
            this.setIcEnviaEmailConvocacao(false);
        }
        return icEnviaEmailConvocacao;
    }

    /**
     * @param icEnviaEmailConvocacao
     *            Boolean
     */
    public void setIcEnviaEmailConvocacao(Boolean icEnviaEmailConvocacao) {
        this.icEnviaEmailConvocacao = icEnviaEmailConvocacao;
    }

    /**
     * @return String
     */
    public String getIcFrequenciaAvaliacao() {
        if (icFrequenciaAvaliacao == null) {
            this.setIcFrequenciaAvaliacao("0");
        }
        return icFrequenciaAvaliacao;
    }

    /**
     * @param icFrequenciaAvaliacao
     *            String
     */
    public void setIcFrequenciaAvaliacao(String icFrequenciaAvaliacao) {
        this.icFrequenciaAvaliacao = icFrequenciaAvaliacao;
    }

    /**
     * @return Boolean
     */
    public Boolean getIcAtivo() {
        if (icAtivo == null) {
            this.setIcAtivo(true);
        }
        return icAtivo;
    }

    /**
     * @param icAtivo
     *            Boolean
     */
    public void setIcAtivo(Boolean icAtivo) {
        this.icAtivo = icAtivo;
    }

    /**
     * @return Boolean
     */
    public Boolean getIcAgencia() {
        if (icAgencia == null) {
            this.setIcAgencia(false);
        }
        return icAgencia;
    }

    /**
     * @param icAgencia
     *            Boolean
     */
    public void setIcAgencia(Boolean icAgencia) {
        this.icAgencia = icAgencia;
    }

    /**
     * @return List<ServicoVO>
     */
    public List<ServicoVO> getListaServico() {
        return listaServico;
    }

    /**
     * @param listaServico
     *            List<ServicoVO>
     */
    public void setListaServico(List<ServicoVO> listaServico) {
        this.listaServico = listaServico;
    }

    /**
     * @return List<GrupoUnidadeVO>
     */
    public List<GrupoUnidadeVO> getListaGrupoUnidade() {
        return listaGrupoUnidade;
    }

    /**
     * @param listaGrupoUnidade
     *            List<GrupoUnidadeVO>
     */
    public void setListaGrupoUnidade(List<GrupoUnidadeVO> listaGrupoUnidade) {
        this.listaGrupoUnidade = listaGrupoUnidade;
    }

    // ****************************************************************

    /**
     * @see br.com.spread.framework.vo.AbstractVO#getPk
     */
    @Override
    public Object getPk() {
        return this.getNuGrupo();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GrupoVO [nuGrupo=" + nuGrupo + ", noGrupo=" + noGrupo + ", icEnviaEmailConvocacao="
                + icEnviaEmailConvocacao + ", icFrequenciaAvaliacao=" + icFrequenciaAvaliacao + ", icAtivo=" + icAtivo
                + ", icAgencia=" + icAgencia + ", listaServico=" + listaServico + ", listaGrupoUnidade="
                + listaGrupoUnidade + "]";
    }

}