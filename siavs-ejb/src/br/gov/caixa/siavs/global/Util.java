package br.gov.caixa.siavs.global;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Funções utéis para o sistema.
 * 
 * @author leandro.vieira
 */
public class Util {

	/**
	 * Clona um objeto serializável.
	 * @param obj Objeto que deve ser clonado.
	 * @return Retorna o objeto clonado.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T cloneSerializable(T obj) {
		ObjectOutputStream out = null;
		ObjectInputStream in = null;

		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			out = new ObjectOutputStream(bout);

			out.writeObject(obj);
			out.close();

			ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
			in = new ObjectInputStream(bin);
			Object copy = in.readObject();

			in.close();

			return (T) copy;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			}
			catch (IOException ignore) {
			}
		}

		return null;
	}
}
