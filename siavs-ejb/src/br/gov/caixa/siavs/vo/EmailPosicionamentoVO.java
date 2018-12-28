package br.gov.caixa.siavs.vo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.spread.framework.util.Util;

/**
 * <b>Title:</b> EmailPosicionamentoVO <br>
 * <b>Description:</b> Permite manter as informações do e-mail de posicionamento. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 04/07/2013$
 */
public class EmailPosicionamentoVO extends AbstractVOSIAVS {

	private static final long serialVersionUID = 1L;

// ****************************************************************
	
	/**
	 * Identificador do e-mail de posicionamento
	 */
	private Long nuEmailPosicionamento;
	/**
	 * Assunto do e-mail de posicionamento
	 */
	private String deAssunto;
	/**
	 * Conteúdo do e-mail que será enviado.
	 */
	private String deConteudo;
	/**
	 * Data e a hora que o email foi registrado para envio.
	 */
	private Date dtCadastro;
	/**
	 * Relacionamento com grupo.
	 */
	private GrupoVO grupo;
	/**
	 * Lista de grupos.
	 */
	private List<?> listaGrupo;
	/**
	 * Relacionamento com serviço.
	 */
	private ServicoVO servico;
	/**
	 * Data e a hora que o email foi enviado.
	 */
	private Date dtEnvio;
	/**
	 * Nome do arquivo em anexo.
	 */
	private String noAnexo;
	/**
	 * Arquivo em anexo.
	 */
	private byte[] arAnexo;
	
// ****************************************************************
	/**
	 * Construtor padrão.
	 */
	public EmailPosicionamentoVO() {}

	/**
	 * Construtor com chave primária.
	 * @param nuEmailPosicionamento Long
	 */
	public EmailPosicionamentoVO(Long nuEmailPosicionamento) {
		this.setNuEmailPosicionamento(nuEmailPosicionamento);
	}
// ****************************************************************

	/**
	 * @return Long
	 */
	public Long getNuEmailPosicionamento() {
		return nuEmailPosicionamento;
	}

	/**
	 * @param nuEmailPosicionamento Long
	 */
	public void setNuEmailPosicionamento(Long nuEmailPosicionamento) {
		this.nuEmailPosicionamento = nuEmailPosicionamento;
	}

	/**
	 * @return String
	 */
	public String getDeAssunto() {
		if(!Util.notEmpty(deAssunto)){
			//esse trecho foi colocado aqui para atender ao redMine 5711 conforme DI 
			return deAssunto = "SIAVS - Posicionamento do Serviço <<Nome do Serviço>>: <<Assunto>>";
		}
			return deAssunto;
	}

	/**
	 * @param deAssunto String
	 */
	public void setDeAssunto(String deAssunto) {
		this.deAssunto = deAssunto;
	}

	/**
	 * @return String
	 */
	public String getDeConteudo() {
		return deConteudo;
	}

	/**
	 * @param deConteudo String
	 */
	public void setDeConteudo(String deConteudo) {
		this.deConteudo = deConteudo;
	}

	/**
	 * @return Date
	 */
	public Date getDtCadastro() {
		return dtCadastro;
	}
	
	/**
	 * @return Date
	 */
	public String getDtCadastroFormatadoHoraMinuto() {

		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String retorno = "";
			retorno = df.format(this.getDtCadastro());
			return retorno;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * @param dtCadastro Date
	 */
	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	/**
	 * @return GrupoVO
	 */
	public GrupoVO getGrupo() {
		return grupo;
	}

	/**
	 * @param grupo GrupoVO
	 */
	public void setGrupo(GrupoVO grupo) {
		this.grupo = grupo;
	}

	/**
	 * @return ServicoVO
	 */
	public ServicoVO getServico() {
		return servico;
	}

	/**
	 * @param servico ServicoVO
	 */
	public void setServico(ServicoVO servico) {
		this.servico = servico;
	}
	
	/**
	 * @return Date
	 */
	public Date getDtEnvio() {
		return dtEnvio;
	}
	
	/**
	 * @param dtEnvio Date
	 */
	public void setDtEnvio(Date dtEnvio) {
		this.dtEnvio = dtEnvio;
	}

	/**
	 * @return String
	 */
	public String getNoAnexo() {
		return noAnexo;
	}

	/**
	 * @param noAnexo String
	 */
	public void setNoAnexo(String noAnexo) {
		this.noAnexo = noAnexo;
	}
	
	/**
	 * @return byte[]
	 */
	public byte[] getArAnexo() {
		return arAnexo;
	}

	/**
	 * @param arAnexo byte[]
	 */
	public void setArAnexo(byte[] arAnexo) {
		this.arAnexo = arAnexo;
	}
	
// ****************************************************************

	/**
	 * @see br.com.spread.framework.vo.AbstractVO#getPk
	 */
	@Override
	public Object getPk() {
		return this.getNuEmailPosicionamento();
	}

	public List<?> getListaGrupo() {
		return listaGrupo;
	}

	public void setListaGrupo(List<?> listaGrupo) {
		this.listaGrupo = listaGrupo;
	}
}