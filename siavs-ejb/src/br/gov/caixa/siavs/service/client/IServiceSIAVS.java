/**
 *  IServiceSIAVS.java
 */
package br.gov.caixa.siavs.service.client;

import br.com.spread.framework.service.basic.IServiceBasic;
import br.gov.caixa.siavs.vo.AbstractVOSIAVS;

/**
 * <b>Title:</b> IServiceSIAVS <br>
 * <b>Description:</b> Interface que define o padrão das classes service para o sistema de SIAVS. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: $, $Date: $
 */
public interface IServiceSIAVS <T extends AbstractVOSIAVS> extends IServiceBasic <T> {
}