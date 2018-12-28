package br.gov.caixa.siavs.dao;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import br.com.spread.framework.dao.jdbc.AbstractDAOJDBC;
import br.com.spread.framework.global.Dominio.BD;
import br.gov.caixa.siavs.vo.AbstractVOSIAVS;

/**
 * <b>Title:</b> AbstractDAOSIAVS <br>
 * <b>Description:</b> Classe abstrata que define o padrão das classes DAO para o sistema SIAVS. <br>
 * @author leandro.vieira
 * @version: $Revision: $$, $Date: $$
 */
public abstract class AbstractDAOSIAVS <T extends AbstractVOSIAVS> extends AbstractDAOJDBC<T> {
	
	@Resource(mappedName="java:jboss/datasources/siavsDS")
	private DataSource dataSource;
	
	/**
	 * Inicializa a classe.
	 */
	@PostConstruct
	private void inicializar() {
		this.setTipoBanco(BD.POSTGRE_SQL);
		this.setDataSource(dataSource);
		this.setNomeSchema("AVSSM001");
	}
}