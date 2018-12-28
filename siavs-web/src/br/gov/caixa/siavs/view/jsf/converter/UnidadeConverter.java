package br.gov.caixa.siavs.view.jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.vo.UnidadeVO;

/**
 * Classe para converter o objeto de unidade no jsf.
 * 
 * @author leandro.vieira
 */
@Named
public class UnidadeConverter implements Converter {

	private static final String SEPARADOR = "§";

// ***********************************************************************************************************************************
	
	/**
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(! Util.notEmpty(value) || value.indexOf(SEPARADOR) == -1) {
			return null;
		}
		
		String[] temp = value.split(SEPARADOR);
		return new UnidadeVO(new Integer(temp[0]), new Integer(temp[1]));
	}

	/**
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value == null || !(value instanceof UnidadeVO)) {
			return null;
		}
		
		UnidadeVO unidade = (UnidadeVO) value;
		return unidade.getNuUnidade() + SEPARADOR + unidade.getNuNatural();
	}
}