package br.gov.caixa.siavs.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.global.Dominio.Operacao;
import br.com.spread.framework.tos.EmailTO;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.dao.EmailConvocacaoDAO;
import br.gov.caixa.siavs.global.DominioSIAVS;
import br.gov.caixa.siavs.global.DominioSIAVS.FrequenciaAvaliacao;
import br.gov.caixa.siavs.global.Mensagem.Msg;
import br.gov.caixa.siavs.service.client.EmailConvocacaoSL;
import br.gov.caixa.siavs.service.client.EmailConvocacaoSR;
import br.gov.caixa.siavs.service.client.FuncionarioCaixaSL;
import br.gov.caixa.siavs.vo.EmailConvocacaoVO;
import br.gov.caixa.siavs.vo.GrupoVO;

/**
 * <b>Title:</b> EmailConvocacaoSB <br>
 * <b>Description:</b> Serviço relacionado a emailConvocacao. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 04/07/2013$
 */
@Stateless
@Remote(EmailConvocacaoSR.class)
@Local(EmailConvocacaoSL.class)
public class EmailConvocacaoSB extends AbstractServiceSIAVS<EmailConvocacaoVO> implements EmailConvocacaoSR, EmailConvocacaoSL {
	
	@Inject
	private FuncionarioCaixaSL funcionarioCaixaSL;
	
	@Inject
	private void setDao(EmailConvocacaoDAO dao) {
		super.setDao(dao);
	}

	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#validar
	 */
	public void validar(EmailConvocacaoVO vo, Operacao operacao) throws SystemException, BusinessException {
		if(operacao.equals(Operacao.INCLUIR) || operacao.equals(Operacao.ALTERAR)) {
			if(! Util.notEmpty(vo.getDeAssunto())){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("assunto"), "deAssunto");
			}
			
			if(! Util.notEmpty(vo.getDeConteudo())){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("escrita do email"), "deConteudo");
			}

			String noHTMLString = vo.getDeConteudo().replaceAll("\\<.*?\\>", "").trim();  
			if(!(noHTMLString.length() >= 5)){
				throw new BusinessException("Para registrar a mensagem é necessário inserir no mínimo cinco caracteres.", "deConteudo");
			}
		}
	}
	
	/**
	 * @see br.gov.caixa.siavs.service.client.EmailConvocacaoSL#enviarEmail()
	 */
	@Override
	public void enviarEmail() throws SystemException, BusinessException {
		// Lista todos os emails de convocação
		List<EmailConvocacaoVO> emails = this.getDao().listar(null);
		
		if(Util.notEmpty(emails)) {
			Calendar data = GregorianCalendar.getInstance();
			
			for (EmailConvocacaoVO vo : emails) {
				// Se for grupo agência
				if( DominioSIAVS.ID_GRUPO_AGENCIA == vo.getGrupo().getNuGrupo()){
					continue;
				}
				
				// Se for diário "OU"
				if(	FrequenciaAvaliacao.DIARIO.getCodigo().equals(vo.getGrupo().getIcFrequenciaAvaliacao()) || 
				// Se for semanal e a data atual for segunda "OU"
					(FrequenciaAvaliacao.SEMANAL.getCodigo().equals(vo.getGrupo().getIcFrequenciaAvaliacao()) 
						&& data.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) ||
				// Se for quinzenal e for a primeira ou a terceira segunda-feira do mês "OU"
					(FrequenciaAvaliacao.QUINZENAL.getCodigo().equals(vo.getGrupo().getIcFrequenciaAvaliacao()) 
						&& data.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && (data.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 1 || data.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 3 )) ||  
				// Se for mensal e for a primeira segunda-feira do mês
					(FrequenciaAvaliacao.MENSAL.getCodigo().equals(vo.getGrupo().getIcFrequenciaAvaliacao()) 
						&& data.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && data.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 1)  
				) {
					
					this.enviarEmail(vo, funcionarioCaixaSL.montaListaDestinatariosGrupo(vo.getGrupo()));
				}
			}
		}
	}

	/**
	 * @see br.gov.caixa.siavs.service.client.EmailConvocacaoSL#enviarEmailAgencia()
	 */
	@Override
	public void enviarEmailAgencia() throws SystemException, BusinessException {
		// Carrega o email de convocação para o grupo agência
		EmailConvocacaoVO emailConvocacaoVO = this.consultar(new EmailConvocacaoVO(new GrupoVO(DominioSIAVS.ID_GRUPO_AGENCIA)));
		
		if(emailConvocacaoVO != null){
			this.enviarEmail(emailConvocacaoVO, funcionarioCaixaSL.montaListaDestinatariosConvocacaoAgencia(emailConvocacaoVO.getGrupo(), new Date()));
		}
	}

	/**
	 * Envia o email.
	 * @param vo EmailConvocacaoVO
	 * @param listDestinatario List<String>
	 * @throws SystemException
	 */
	private void enviarEmail(EmailConvocacaoVO vo, List<String> listDestinatario) throws SystemException {
		if (Util.notEmpty(listDestinatario)) {
			EmailTO emailTO = new EmailTO()
			.setRemetente(DominioSIAVS.EMAIL_REMETENTE)
			.setAssunto(vo.getDeAssunto())
			.setConteudo(vo.getDeConteudo());
			
			// Envia um email para cada destinatário
			for (String destinatario : listDestinatario) {
				this.enviarEmail(emailTO.setListaDestinatario(Arrays.asList(destinatario)));
			}
		}
	}
}