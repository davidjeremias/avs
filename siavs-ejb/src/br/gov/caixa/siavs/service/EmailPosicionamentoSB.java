package br.gov.caixa.siavs.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.global.Dominio.Operacao;
import br.com.spread.framework.global.Dominio.TipoConteudoWeb;
import br.com.spread.framework.tos.EmailTO;
import br.com.spread.framework.util.Arquivo;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.dao.EmailPosicionamentoDAO;
import br.gov.caixa.siavs.global.DominioSIAVS;
import br.gov.caixa.siavs.global.Mensagem.Msg;
import br.gov.caixa.siavs.service.client.EmailPosicionamentoSL;
import br.gov.caixa.siavs.service.client.EmailPosicionamentoSR;
import br.gov.caixa.siavs.service.client.FuncionarioCaixaSL;
import br.gov.caixa.siavs.vo.EmailPosicionamentoVO;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.ServicoVO;

/**
 * <b>Title:</b> EmailPosicionamentoSB <br>
 * <b>Description:</b> Serviço relacionado a email posicionamento. <br>
 * <b>Company:</b> Spread
 * @author joana.espindola
 * @version: $Revision: 1.0 $, $Date: 07/06/2013$
 */
@Stateless
@Remote(EmailPosicionamentoSR.class)
@Local(EmailPosicionamentoSL.class)
public class EmailPosicionamentoSB extends AbstractServiceSIAVS<EmailPosicionamentoVO> implements EmailPosicionamentoSR, EmailPosicionamentoSL {
	
	@Inject
	private FuncionarioCaixaSL funcionarioCaixaSL;
	private Date dataAtualA = new Date();
	@Inject
	private void setDao(EmailPosicionamentoDAO dao) {
		super.setDao(dao);
	}

	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#validar
	 */
	@Override
	public void validar(EmailPosicionamentoVO vo, Operacao operacao) throws SystemException, BusinessException {
		if (operacao.equals(Operacao.ALTERAR) || operacao.equals(Operacao.EXCLUIR) || operacao.equals(Operacao.CONSULTAR)) {
			if (!Util.notEmpty(vo.getNuEmailPosicionamento())) {
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("identificador do emailPosicionamento"), "nuEmailPosicionamento");
			}
		}

		vo.setDtCadastro(dataAtualA);
		//operacao alterar
		//setOperacao("ALTERAR");
	//	operacao.setNome("ALTERAR");
		//operacao.se
	
		if (operacao.equals(Operacao.INCLUIR) || operacao.equals(Operacao.ALTERAR)) {		
			if(vo.getGrupo() == null || !Util.notEmpty(vo.getGrupo().getNuGrupo().toString())){
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("grupo"), "grupo");
			}			
			if (!Util.notEmpty(vo.getDeAssunto())) {
				throw new BusinessException(Msg.MN_CAMPO_OBRIGATORIO.montar("assunto"), "deAssunto");
			}
			String noHTMLString = StringEscapeUtils.unescapeHtml(vo.getDeConteudo().replaceAll("\\<.*?\\>", "").trim());
			if(noHTMLString.length() < 5) {
				throw new BusinessException(Msg.MN027.toString(), "deConteudo");
			} 			
		}
	}
	
	/**
	 * @see br.com.spread.framework.service.basic.AbstractServiceBasic#posOperacao(br.com.spread.framework.vo.AbstractVO, br.com.spread.framework.global.Dominio.Operacao)
	 */
	@Override
	public EmailPosicionamentoVO posOperacao(EmailPosicionamentoVO vo, Operacao operacao) throws SystemException, BusinessException {
		//if (!vo.getGrupo().getNuGrupo().equals(0)){
		if(Operacao.INCLUIR.equals(operacao)) {
		//	if (!vo.getGrupo().getNuGrupo().equals("0")){
			// RN026
		//comentar na fábrica pois não envia na fábrica com configuração da caixa.
			this.enviaEmailPosicionamento(vo, Arrays.asList(this.getUsuarioLogado().getName() + DominioSIAVS.EMAIL_CAIXA_COMPLEMENTO));
		//	}
		}
		//}
		return super.posOperacao(vo, operacao);
	}

	/**
	 * @see br.gov.caixa.siavs.service.client.EmailPosicionamentoSR#buscarEmails(br.gov.caixa.siavs.vo.EmailPosicionamentoVO, java.util.Date, java.util.Date)
	 */
	@Override
	public List<EmailPosicionamentoVO> buscarEmails(EmailPosicionamentoVO voFiltro, Date dtInicio, Date dtFim) throws SystemException, BusinessException {
		if(dtInicio != null || dtFim != null){
			Date dataAtual = new Date();
			
			if((dtInicio != null && dtInicio.after(dataAtual)) || (dtFim != null && dtFim.after(dataAtual))){
				throw new BusinessException("Deve ser informada uma data igual ou inferior a data atual.", "dtInicio");
			}
			if(dtInicio != null && dtFim != null && dtFim.before(dtInicio)){
				throw new BusinessException("A data final deve ser maior ou igual a data inicial.", "dtFinal");
			}
		}
		
		return ((EmailPosicionamentoDAO) this.getDao()).buscarEmails(voFiltro, dtInicio, dtFim);
	}
	
	/**
	 * inclui EmailPosicionamento varias vezes caso o usuário escolha a opção todos.
	 * param vo
	 * param grupoVO
	 */
	public void incluiEmailPosicionamentoEmLote(EmailPosicionamentoVO vo, List<GrupoVO> grupoVO) throws BusinessException, SystemException{
		for (GrupoVO gVO : grupoVO) {
			if(gVO.getNuGrupo().longValue() == 0)
				continue;
			
			vo.setGrupo(gVO);
			/*
			 //TODO quando houver um RedMine solicitando que tenha o campo TODOS para a combo servico, descomente o codigo abaixo.
			 if(Util.notEmpty(servicoVO) || !servicoVO.equals(null)){
				for (ServicoVO sVO : servicoVO) {
					vo.setServico(sVO);
				}
			}*/
		//	if(!vo.getGrupo().getNuGrupo().equals(0)){
			super.incluir(vo);
		//	}
		}
	}

	/**
	 * @see br.gov.caixa.siavs.service.client.EmailPosicionamentoSL#enviarEmail()
	 */
	@Override
	public void enviarEmail() throws SystemException, BusinessException {
		List<EmailPosicionamentoVO> emails = ((EmailPosicionamentoDAO) this.getDao()).listarEmailsEnvio();

		if (Util.notEmpty(emails)) {
			for (EmailPosicionamentoVO vo : emails) {
				enviaEmailPosicionamento(vo);
			}
		}		
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private void enviaEmailPosicionamento(EmailPosicionamentoVO vo) throws SystemException, BusinessException {
		ServicoVO servico = null;
		
		if (vo.getGrupo().getIcAgencia()) {
			servico = vo.getServico();
		}
		
		List<String> listDestinatario = funcionarioCaixaSL.montaListaDestinatariosGrupoServico(vo.getGrupo(), servico);
		
		if (Util.notEmpty(listDestinatario)) {
			this.enviaEmailPosicionamento(vo, listDestinatario);
			vo.setDtEnvio(new Date());
			this.alterar(vo);
		}
	}
	
	/**
	 * Envia o email para a lista de destinatários informada.
	 * @param vo EmailPosicionamentoVO
	 * @param listDestinatario Lista de destinatários do email
	 * @throws SystemException Para exceções do sistema
	 */
	private void enviaEmailPosicionamento(EmailPosicionamentoVO vo, List<String> listDestinatario) throws SystemException {
		if (Util.notEmpty(listDestinatario)) {
			EmailTO email = new EmailTO()
				.setRemetente(DominioSIAVS.EMAIL_REMETENTE)
				.setAssunto(vo.getDeAssunto())
				.setConteudo(vo.getDeConteudo())
				// anexo
				.setAnexo(vo.getArAnexo())
				.setNomeAnexo(vo.getNoAnexo())
				.setTipoConteudoWeb(TipoConteudoWeb.getValor(Arquivo.recuperarExtensaoArquivo(vo.getNoAnexo())));
			// Adaptação porque o tipo de conteudo zip não está sendo tratado no método getValor
			if(email.getTipoConteudoWeb() == null) {
				email.setTipoConteudoWeb(TipoConteudoWeb.ZIP);
			}

			// Solicitação feita de enviar um email para cada destinatário
			for (String destinatario : listDestinatario) {
				email.setListaDestinatario(Arrays.asList(destinatario));
				this.enviarEmail(email);
			}
		}
	}
}