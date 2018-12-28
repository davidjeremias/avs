package br.gov.caixa.siavs.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.spread.framework.exception.BusinessException;
import br.com.spread.framework.exception.SystemException;
import br.com.spread.framework.tos.EmailTO;
import br.com.spread.framework.tos.EmailTO.FormatoEmail;
import br.com.spread.framework.util.Util;
import br.gov.caixa.siavs.dao.UnidadeDAO;
import br.gov.caixa.siavs.global.DominioSIAVS;
import br.gov.caixa.siavs.global.DominioSIAVS.TipoUnidade;
import br.gov.caixa.siavs.service.client.UnidadeSL;
import br.gov.caixa.siavs.service.client.UnidadeSR;
import br.gov.caixa.siavs.vo.GrupoVO;
import br.gov.caixa.siavs.vo.UnidadeVO;

/**
 * <b>Title:</b> UnidadeSB <br>
 * <b>Description:</b> Serviço relacionado a unidade. <br>
 * <b>Company:</b> Spread
 * @author leandro.vieira
 * @version: $Revision: 1.0 $, $Date: 08/07/2013$
 */
@Stateless
@Remote(UnidadeSR.class)
@Local(UnidadeSL.class)
public class UnidadeSB extends AbstractServiceSIAVS<UnidadeVO> implements UnidadeSR, UnidadeSL {

	@Inject
	private void setDao(UnidadeDAO dao) {
		super.setDao(dao);
	}

	/**
	 * @see br.gov.caixa.siavs.service.client.UnidadeSL#enviarEmailPendenciaDesignacao()
	 */
	@Override
	public void enviarEmailPendenciaDesignacao() throws SystemException, BusinessException {
		List<UnidadeVO> lista = ((UnidadeDAO)getDao()).listarUnidadePendenciaDesignacao(new Date());

		if(Util.notEmpty(lista)) {
			for (UnidadeVO unidade : lista) {

				this.enviarEmail(
					new EmailTO()
					.setRemetente(DominioSIAVS.EMAIL_REMETENTE)
					.setListaDestinatario(Arrays.asList(unidade.getDeEmail()))
					.setAssunto("")
					.setConteudo(this.getTextoEmailPendenciaDesignacao(unidade))
					.setFormato(FormatoEmail.HTML)
				);
			}
		}
	}

	/**
	 * Retorna o email com o texto do email de pendência de designação.
	 * @param unidade UnidadeVO
	 * @return String
	 */
	private String getTextoEmailPendenciaDesignacao(UnidadeVO unidade) {
		return new StringBuilder()
		.append(" <p> ")
		.append("   À <br /> ")
		.append("   Agência " + unidade.getNuUnidadeFormatado() + " - " + unidade.getNoUnidade())
		.append(" </p> ")
		.append(" <p> ")
		.append("   Senhor Gerente ")  
		.append(" </p> ")
		.append(" <ol> ")
		.append("   <li>Informamos que essa agência possui Serviços de TI (sistemas) sem empregados responsáveis pela avaliação.</li> ")
		.append("   <li>Para que as avaliações sejam efetivas é importante que as vinculações dos empregados com os respectivos segmentos sejam <b>atualizadas por qualquer Gerente  ou Supervisor.</b></li> ")
		.append("   <li>Para isto basta acessar o link <a href=\"http://siavs.caixa\">http://siavs.caixa</a> e seguir o roteiro abaixo:<br /> ")
		.append("       <ul> ")
		.append("           <li>No menu superior escolha \"<b>Designa Avaliador</b>\"  </li> ")
		.append("           <li>Selecione o <b>nome do empregado</b>  </li> ")
		.append("           <li>Na barra lateral direita, clique no ícone \"<b>Alterar</b>\" </li> ")
		.append("           <li>Selecione os segmentos respectivos e clique em \"<b>Salvar</b>\" </li> ")
		.append("       </ul> ")
		.append("   </li> ")
		.append("   <li>Repetir o procedimento para todos os empregados da Agência indicados para participarem das avaliações. </li> ")
		.append("   <ol> ")
		.append("       <li>Todos os segmentos deverão ter pelo menos 01 empregado vinculado. </li> ")
		.append("   </ol> ")
		.append("   <li>Em caso de dúvidas gentileza enviar email para a <b>Caixa Postal GEDTI02.</b> </li> ")
		.append("   <li>Agradecemos sua importante participação neste processo que vai culminar com a constante melhoria da disponibilidade dos serviços de TI ofertados à rede de negócios Caixa. </li> ")
		.append(" </ol> ")
		.append(" <p> ")
		.append("   Atenciosamente,  ")  
		.append(" </p> ")
		.append(" <p> ")
		.append("   <b>GEDTI - Gerência Nacional de Estratégia e Desempenho de TI</b> ")
		.append(" </p> ")
		.toString();
	}

	/**
	 * @see br.gov.caixa.siavs.service.client.UnidadeSL#listarUnidadeVinculada(br.gov.caixa.siavs.vo.UnidadeVO)
	 */
	@Override
	public List<UnidadeVO> listarUnidadeVinculada(UnidadeVO vo) throws SystemException, BusinessException {
		return ((UnidadeDAO)this.getDao()).listarUnidadeVinculada(vo);
	}

	/**
	 * @see br.gov.caixa.siavs.service.client.UnidadeSR#listarUnidadeNaoVinculada(br.gov.caixa.siavs.vo.GrupoVO)
	 */
	@Override
	public List<UnidadeVO> listarUnidadeNaoVinculada(GrupoVO grupoVO) throws SystemException, BusinessException {
		return ((UnidadeDAO)this.getDao()).listarUnidadeNaoVinculada(grupoVO);
	}

	/**
	 * @see br.gov.caixa.siavs.service.client.UnidadeSR#listarUnidadeRelProd(br.gov.caixa.siavs.global.DominioSIAVS.TipoUnidade)
	 */
	@Override
	public List<UnidadeVO> listarUnidadeRelProd(TipoUnidade tipoUnidade) throws SystemException, BusinessException {
		return ((UnidadeDAO) this.getDao()).listarUnidadeRelProd(tipoUnidade);
	}
    
	@Override
	public UnidadeVO consultaNuTipoUnidade(Integer nuUnidade, Integer nuNatural) throws SystemException {
		return (UnidadeVO) ((UnidadeDAO) this.getDao()).consultarTipoUnidade(nuUnidade, nuNatural);
	}
}