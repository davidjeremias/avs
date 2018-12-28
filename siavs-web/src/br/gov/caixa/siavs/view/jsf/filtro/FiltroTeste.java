package br.gov.caixa.siavs.view.jsf.filtro;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import br.gov.caixa.siavs.service.client.AgendamentoSR;

/**
 * Classe utilizada para permitir relizar testes dos casos de uso com rotinas.
 * 
 * @author leandro.vieira
 */
@WebFilter(filterName = "FiltroTeste", urlPatterns = {"/teste/*"})
public class FiltroTeste implements Filter {  

// ***********************************************************************************************************************************
	
	@Inject
	private AgendamentoSR agendamentoSR;
	
	/**
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		
		try {
			if(httpServletRequest.getRequestURI().endsWith("/sorteiaAgencia")) {
				agendamentoSR.sorteiaAgencia();
			}
			else if(httpServletRequest.getRequestURI().endsWith("/enviaEmailPosicionamento")) {
				agendamentoSR.enviaEmailPosicionamento();
			}
			else if(httpServletRequest.getRequestURI().endsWith("/enviaEmailConvocacao")) {
				agendamentoSR.enviaEmailConvocacao();
			}
			else if(httpServletRequest.getRequestURI().endsWith("/enviaEmailConvocacaoAgencia")) {
				agendamentoSR.enviaEmailConvocacaoAgencia();
			}
			else if(httpServletRequest.getRequestURI().endsWith("/enviaPendenciaDesignacao")) {
				agendamentoSR.enviaPendenciaDesignacao();
			}
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	/**
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}
}