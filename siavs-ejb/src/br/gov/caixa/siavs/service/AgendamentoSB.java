package br.gov.caixa.siavs.service;

import java.util.concurrent.TimeUnit;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Remote;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.TransactionTimeout;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.gov.caixa.siavs.service.client.AgendamentoSR;
import br.gov.caixa.siavs.service.client.EmailConvocacaoSL;
import br.gov.caixa.siavs.service.client.EmailPosicionamentoSL;
import br.gov.caixa.siavs.service.client.UnidadeSL;
import br.gov.caixa.siavs.service.client.UnidadeSorteioSL;
import br.gov.caixa.siavs.vo.AbstractVOSIAVS;

/**
 * <b>Title:</b> AgendamentoSB <br>
 * <b>Description:</b> Serviço responsável pelas atividades agendadas. <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 15/07/2013$
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
@Lock(LockType.READ)
@Remote(AgendamentoSR.class)
public class AgendamentoSB extends AbstractServiceSIAVS<AbstractVOSIAVS> implements AgendamentoSR {

	@Inject
	private UnidadeSorteioSL unidadeSorteioSL;

	@Inject
	private EmailPosicionamentoSL emailPosicionamentoSL;

	@Inject
	private EmailConvocacaoSL emailConvocacaoSL;

	@Inject
	private UnidadeSL unidadeSL;

	private static Logger log;

	static {
		log = Logger.getLogger(AgendamentoSB.class);
	}

	/**
	 * Executa a rotina de sorteio de agência. Todos os domingos ao meio dia
	 * 
	 * @throws SystemException
	 * @throws BusinessException
	 */
	//
	@Schedule(hour = "12", dayOfWeek = "Sun")
	@Lock(LockType.WRITE)
	@TransactionTimeout(unit = TimeUnit.HOURS, value = 4L)
	public void sorteiaAgencia() throws SystemException, BusinessException {
		try {
			log.error("Sorteia Agencia. Iniciando...");
			unidadeSorteioSL.sorteio();
			log.error("Sorteia Agencia. Concluído!");
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * Envia os email de posicionamento. Todos os dias as 20:00
	 * 
	 * @throws SystemException
	 * @throws BusinessException
	 */
	@Schedule(hour = "20")
	@Lock(LockType.WRITE)
	@TransactionTimeout(unit = TimeUnit.HOURS, value = 4L)
	public void enviaEmailPosicionamento() throws SystemException, BusinessException {
		try {
			emailPosicionamentoSL.enviarEmail();
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * Envia os email de convocação. Todos os dias as 05:00
	 * 
	 * @throws SystemException
	 * @throws BusinessException
	 */
	@Schedule(hour = "5")
	@Lock(LockType.WRITE)
	@TransactionTimeout(unit = TimeUnit.HOURS, value = 4L)
	public void enviaEmailConvocacao() throws SystemException, BusinessException {
		try {
			emailConvocacaoSL.enviarEmail();
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * Envia os email de convocação para agência.
	 * 
	 * Todos os dias as 05:00 e as 15:00
	 * 
	 * @throws SystemException
	 * @throws BusinessException
	 */
	@Schedules({ @Schedule(hour = "5"), // Todos os dias as 05:00
			@Schedule(hour = "15") // Todos os dias as 15:00
	})
	@Lock(LockType.WRITE)
	@TransactionTimeout(unit = TimeUnit.HOURS, value = 4L)
	public void enviaEmailConvocacaoAgencia() throws SystemException, BusinessException {
		try {
			emailConvocacaoSL.enviarEmailAgencia();
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * Envia os email de pendência de designação. Todos os dias as 05:00
	 * 
	 * @throws SystemException
	 * @throws BusinessException
	 */
	@Schedule(hour = "5")
	@Lock(LockType.WRITE)
	@TransactionTimeout(unit = TimeUnit.HOURS, value = 4L)
	public void enviaPendenciaDesignacao() throws SystemException, BusinessException {
		try {
			unidadeSL.enviarEmailPendenciaDesignacao();
		} catch (Exception e) {
			log.error(e);
		}
	}
}