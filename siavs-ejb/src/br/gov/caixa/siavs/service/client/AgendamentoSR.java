package br.gov.caixa.siavs.service.client;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.vo.AbstractVOSIAVS;

/**
 * Interface remota para o serviço de agendamento. Utilizado para testar os
 * agendamentos.
 * 
 * @author leandro.vieira
 */
public interface AgendamentoSR extends IServiceSIAVS<AbstractVOSIAVS> {

    /**
     * 
     * @throws SystemException
     * @throws BusinessException
     */
    public void sorteiaAgencia() throws SystemException, BusinessException;

    /**
     * Envia os email de posicionamento.
     * 
     * @throws SystemException
     * @throws BusinessException
     */
    public void enviaEmailPosicionamento() throws SystemException, BusinessException;

    /**
     * Envia os email de convocação.
     * 
     * @throws SystemException
     * @throws BusinessException
     */
    public void enviaEmailConvocacao() throws SystemException, BusinessException;

    /**
     * Envia os email de convocação para agência.
     * 
     * @throws SystemException
     * @throws BusinessException
     */
    public void enviaEmailConvocacaoAgencia() throws SystemException, BusinessException;

    /**
     * Envia os email de pendência de designação.
     * 
     * @throws SystemException
     * @throws BusinessException
     */
    public void enviaPendenciaDesignacao() throws SystemException, BusinessException;
}