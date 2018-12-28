package br.gov.caixa.siavs.global;

/**
 * <b>Title:</b> Mensagem <br>
 * <b>Description:</b> Classe com mensagens utilizadas pelas regras do sistema. <br>
 * <b>Company:</b> Spread
 * 
 * @author leandro.vieira
 * @version: $Revision: $, $Date: $
 */
public final class Mensagem {

	private static final String C1 = "-C1-";
	private static final String C2 = "-C2-";
	
	private static final String[] arrayValoresSubstuicao = {C1, C2};

	/**
	 * <b>Title:</b> Mensagem <br>
	 * <b>Description:</b> Classe com mensagens utilizadas pelas regras do sistema. <br>
	 * <b>Company:</b> Spread
	 * @author leandro.vieira
	 * @version: $Revision: $, $Date: $
	 */
	public enum Msg {

		MN_CAMPO_OBRIGATORIO("Preencha o campo " + C1 + "."),
		MN_CAMPO_PROIBIDO("Não dever ser preenchido o campo " + C1 + "."),
		MN_INCLUSAO_PROIBIDA("O registro de " + C1 + " não pode ser incluído " + C2 + "."),
		MN_ALTERACAO_PROIBIDA("O registro de " + C1 + " não pode ser alterado " + C2 + "."),
		MN_EXCLUSAO_PROIBIDA("O registro de " + C1 + " não pode ser excluído " + C2 + "."),
		MN_DADOS_INVALIDOS("Dados informados inválidos."),
		MN002("Campo obrigatório."),	
		MN008("Não possui vínculo com nenhum segmento."),
		MN028("O usuário já está cadastrado."),
		MN015("O " + C1 + " não poderá ser excluído, existe " + C2 + " vinculado."),
		MN027("Para registrar a mensagem é necessário inserir no mínimo cinco caracteres."),
		MN030("O campo " + C1 + " deve conter no mínimo 5 caracteres."),
		;

// ***********************************************************************************************************************************

		/**
		 * Monta a msg com os parâmetros passados
		 * @param valoresSubstituicao String ...
		 * @return String complemento
		 */
		public String montar(String ... valoresSubstituicao) {
			String msg = this.toString();
			
			for (int r = 0; r < valoresSubstituicao.length; r++) {
				msg = msg.replaceAll(arrayValoresSubstuicao[r], valoresSubstituicao[r]);
			}
			
			return msg;
		}

		@Override
		public String toString() {
			return this.getMsg();
		}

// ***********************************************************************************************************************************

		private String msg;

		/**
		 * Construtor padrão
		 * @param msg String
		 */
		private Msg(String msg) {
			this.setMsg(msg);
		}

		/**
		 * @return String
		 */
		public String getMsg() {
			return msg;
		}

		/**
		 * @param msg String
		 */
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
}