package br.gov.caixa.siavs.global.enums;

import br.com.spread.framework.util.Util;

public enum EnumTipoUnidade {

	CL(5),
	AGENCIA(8),
	PAB(9),
	PAA(10),
	DI(12),
	GI(14),
	RE(16),
	CI(19),
	PAP(20),
	PR(21),
	SN(22),
	GN(23),
	AS(25),
	GB(28),
	RF(29),
	CE(30),
	OUVID(37),
	VP(38),
	COADM(39),
	COFIS(40),
	PE(41),
	SR(42),
	ER(44),
	COAUD(45),
	MN(46),
	CR(48),
	CN(50),
	EX(52);
	
	private final Integer codigo;
	
	private EnumTipoUnidade(Integer codigo){
		this.codigo = codigo;
	}
	
	/**
	 * Retorna o objeto através do código.
	 */
	public static EnumTipoUnidade getValor(Integer valor){
		if(!Util.notEmpty(valor)){
			return null;
		}
		
		for (EnumTipoUnidade obj : values()) {
			if(obj.getCodigo() == valor){
				return obj;
			}
		}
		return null;
	}
	
	 public Integer getCodigo() {
		 return codigo;
	 }
}
