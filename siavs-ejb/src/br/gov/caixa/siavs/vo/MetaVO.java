package br.gov.caixa.siavs.vo;

/**
 * <b>Title:</b> MetaVO <br>
 * <b>Description:</b> Permite ao Administrador manter as informações das metas
 * anuais estipuladas para avaliação do serviço. <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 12/06/2013$
 */
public class MetaVO extends AbstractVOSIAVS {

    private static final long serialVersionUID = 1L;

    // ****************************************************************
    /**
     * Identificador da meta.
     */
    private Long nuMeta;
    /**
     * Ano de competência da da meta. Identificador da meta.
     */
    private Integer aaCompetencia;
    /**
     * Valor da nota que é a meta anual; Valores possíveis: 0 a 10;
     */
    private Integer vrNotaMeta;
    /**
     * Relacionamento com grupo.
     */
    private GrupoVO grupo;

    // ****************************************************************
    /**
     * Construtor padrão.
     */
    public MetaVO() {
    }

    /**
     * Construtor com chave primária.
     * 
     * @param nuMeta
     *            Long
     */
    public MetaVO(Long nuMeta) {
        this.setNuMeta(nuMeta);
    }

    /**
     * Construtor com chave única.
     * 
     * @param grupo
     *            GrupoVO
     * @param aaCompetencia
     *            Integer
     */
    public MetaVO(GrupoVO grupo, Integer aaCompetencia) {
        this.setGrupo(grupo);
        this.setAaCompetencia(aaCompetencia);
    }

    // ****************************************************************

    /**
     * @return Long
     */
    public Long getNuMeta() {
        return nuMeta;
    }

    /**
     * @param nuMeta
     *            Long
     */
    public void setNuMeta(Long nuMeta) {
        this.nuMeta = nuMeta;
    }

    /**
     * @return Integer
     */
    public Integer getAaCompetencia() {
        return aaCompetencia;
    }

    /**
     * @param aaCompetencia
     *            Integer
     */
    public void setAaCompetencia(Integer aaCompetencia) {
        this.aaCompetencia = aaCompetencia;
    }

    /**
     * @return Integer
     */
    public Integer getVrNotaMeta() {
        return vrNotaMeta;
    }

    /**
     * @param vrNotaMeta
     *            Integer
     */
    public void setVrNotaMeta(Integer vrNotaMeta) {
        this.vrNotaMeta = vrNotaMeta;
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

    // ****************************************************************

    /**
     * @see br.com.spread.framework.vo.AbstractVO#getPk
     */
    @Override
    public Object getPk() {
        return this.getNuMeta();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MetaVO [nuMeta=" + nuMeta + ", aaCompetencia=" + aaCompetencia + ", vrNotaMeta=" + vrNotaMeta
                + ", grupo=" + grupo + "]";
    }

}