package br.gov.caixa.siavs.service;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.mail.Session;

import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.service.basic.AbstractServiceBasic;
import br.com.spread.framework.tos.EmailTO;
import br.com.spread.framework.util.Email;
import br.gov.caixa.siavs.service.client.IServiceSIAVS;
import br.gov.caixa.siavs.vo.AbstractVOSIAVS;
import br.gov.caixa.siavs.vo.UsuarioVO;

/**
 * <b>Title:</b> AbstractServiceSIAVS <br>
 * <b>Description:</b> Classe abstrata que define o padrão das classes service para o sistema SIAVS. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: $, $Date: $
 */
public abstract class AbstractServiceSIAVS <T extends AbstractVOSIAVS> extends AbstractServiceBasic <T> implements IServiceSIAVS <T> {
	
	@Resource(mappedName = "java:jboss/mail/Default")
	private Session session;
	@Resource
	private SessionContext sessionContext;
	private Email email;
	
	/**
	 * Envia o email.
	 * @param emailTO EmailTO
	 * @throws SystemException
	 */
	protected void enviarEmail(EmailTO emailTO) throws SystemException {
		if(email == null){
			email = new Email().setSession(session);
		}
		
		email.enviar(emailTO);
	}
	
	/**
	 * Retorna o usuário logado.
	 */
	protected UsuarioVO getUsuarioLogado() {
		if(sessionContext.getCallerPrincipal() != null && !"anonymous".equalsIgnoreCase(sessionContext.getCallerPrincipal().getName())) {
			// Clona o objeto para não ter problemas na conversão, visto que o mesmo foi criado por outro ClassLoader
			return (UsuarioVO) br.gov.caixa.siavs.global.Util.cloneSerializable((Serializable) sessionContext.getCallerPrincipal());
		}
		
		return null;
	}	
}