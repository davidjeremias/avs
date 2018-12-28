package br.gov.caixa.siavs.dao;

import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.vo.UnidadeSorteioVO;

/**
 * <b>Title:</b> UnidadeSorteioDAO <br>
 * <b>Description:</b> Classe que persiste o unidadeSorteio. <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 15/07/2013$
 */
public class UnidadeSorteioDAO extends AbstractDAOSIAVS<UnidadeSorteioVO> {
    /**
     * Realiza o sorteio das unidades.
     * 
     * @throws SystemException
     */
    public void sorteio() throws SystemException {
        try {
            StringBuilder sql = new StringBuilder("SELECT AVSSM001.AVSFN002_SORTEIO()");
            this.criarPreparedStatement(sql.toString());
            this.executarConsulta();
        } finally {
            this.fechar(null);
        }
    }
}