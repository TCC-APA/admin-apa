package br.com.rest.model.enumentity;

public enum TiposPerfilEnum {
	PRAGMATICO('P'),TEORICO('T'),ATIVO('A'),REFLEXIVO('R');
 
    private Character codigo;
    
    TiposPerfilEnum(Character codigo) {
        this.codigo = codigo;
    }
    
    public Character getCodigo() {
    	return codigo;
    }

}
