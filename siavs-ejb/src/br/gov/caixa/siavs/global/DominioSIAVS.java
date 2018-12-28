package br.gov.caixa.siavs.global;

/**
 * <b>Title:</b> Dominio <br>
 * <b>Description:</b> Classe com domínios utilizados no sistema. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: $, $Date: $
 */
public final class DominioSIAVS {

// ***********************************************************************************************************************************
	
	/**
	 * Id do Grupo agência.
	 */
	public static final Long ID_GRUPO_AGENCIA = 1l;
	/**
	 * Tipo de função do funcionário caixa que o identifica como gestor.
	 */
	public static final Integer TIPO_FUNCAO_GESTOR = 1;
	/**
	 * Complemento email caixa.
	 */
	public static final String EMAIL_CAIXA_COMPLEMENTO = "@mail.caixa";
	/**
	 * Remetente dos emails enviados.
	 */
	public static final String EMAIL_REMETENTE = "gedti02" + EMAIL_CAIXA_COMPLEMENTO;

// ***********************************************************************************************************************************
	
	/**
	 * <b>Title:</b> FrequenciaAvaliacao <br>
	 * <b>Description:</b> Define os tipos de Frequência de Avaliação utilizados no sistema. <br>
	 * <b>Company:</b> Spread
	 * @author leandro.vieira
	 * @version: $Revision: $, $Date: $
	 */
	public enum FrequenciaAvaliacao {
		
		DIARIO("1", "Diário"),
		SEMANAL("2", "Semanal"),
		QUINZENAL("3", "Quinzenal"),
		MENSAL("4", "Mensal"),
		;

// ***********************************************************************************************************************************
		
		/**
		 * Recupera o objeto através do valor.
		 * @param valor String
		 * @return FrequenciaAvaliacao
		 */
		public static FrequenciaAvaliacao getValor(String valor){
			if(valor == null){
				return null;
			}
			
			for (FrequenciaAvaliacao obj : values()) {
				if(obj.getCodigo().equals(valor)){
					return obj;
				}
			}
			return null;
		}
		
// ***********************************************************************************************************************************
		
		private String codigo;
		private String nome;
		
		/**
		 * Construtor padrão
		 * @param codigo Integer
		 * @param nome String
		 */
		private FrequenciaAvaliacao(String codigo, String nome) {
			this.setCodigo(codigo);
			this.setNome(nome);
		}
		
		/**
		 * @return codigo
		 */
		public String getCodigo() {
			return codigo;
		}

		/**
		 * @param codigo
		 */
		public void setCodigo(String codigo) {
			this.codigo = codigo;
		}

		/**
		 * @return nome
		 */
		public String getNome() {
			return nome;
		}

		/**
		 * @param nome
		 */
		public void setNome(String nome) {
			this.nome = nome;
		}
	}

	/**
	 * <b>Title:</b> NivelAcesso <br>
	 * <b>Description:</b> Define os níveis de acesso do usuário no sistema. <br>
	 * <b>Company:</b> Spread
	 * @author leandro.vieira
	 * @version: $Revision: $, $Date: $
	 */
	public enum NivelAcesso {
		
		AMINISTRADOR("1", "Administrador"),
		PUBLICADOR_TI("2", "Publicador TI"),
		PUBLICADOR("3", "Publicador"),
		;
		
		/**
		 * Recupera o objeto através do valor.
		 * @param valor String
		 * @return NivelAcesso
		 */
		public static NivelAcesso getValor(String valor){
			if(valor == null){
				return null;
			}
			
			for (NivelAcesso obj : values()) {
				if(obj.getCodigo().equals(valor)){
					return obj;
				}
			}
			return null;
		}
		
		private String codigo;
		private String nome;
		
		/**
		 * Construtor padrão
		 * @param codigo Integer
		 * @param nome String
		 */
		private NivelAcesso(String codigo, String nome) {
			this.setCodigo(codigo);
			this.setNome(nome);
		}
		
		/**
		 * @return codigo
		 */
		public String getCodigo() {
			return codigo;
		}
		
		/**
		 * @param codigo
		 */
		public void setCodigo(String codigo) {
			this.codigo = codigo;
		}
		
		/**
		 * @return nome
		 */
		public String getNome() {
			return nome;
		}
		
		/**
		 * @param nome
		 */
		public void setNome(String nome) {
			this.nome = nome;
		}
	}
	
	/**
	 * <b>Title:</b> TipoUnidade <br>
	 * <b>Description:</b> Define os tipos da unidade: Relacionamento ou Produção. <br>
	 * <b>Company:</b> Spread
	 * @author leandro.vieira
	 * @version: $Revision: $, $Date: $
	 */
	public enum TipoUnidade {
		
		RELACIONAMENTO("1"),
		PRODUCAO("2"),
		;
		
		/**
		 * Recupera o objeto através do valor.
		 * @param valor String
		 * @return NivelAcesso
		 */
		public static TipoUnidade getValor(String valor){
			if(valor == null){
				return null;
			}
			
			for (TipoUnidade obj : values()) {
				if(obj.getCodigo().equals(valor)){
					return obj;
				}
			}
			return null;
		}
		
		private String codigo;
		
		/**
		 * Construtor padrão
		 * @param codigo Integer
		 * @param nome String
		 */
		private TipoUnidade(String codigo) {
			this.setCodigo(codigo);
		}
		
		/**
		 * @return codigo
		 */
		public String getCodigo() {
			return codigo;
		}
		
		/**
		 * @param codigo
		 */
		public void setCodigo(String codigo) {
			this.codigo = codigo;
		}
	}
}