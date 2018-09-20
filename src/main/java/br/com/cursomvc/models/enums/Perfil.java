package br.com.cursomvc.models.enums;

public enum Perfil {
	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_USER");
	
	private int codigo;
	private String descricao;

	private Perfil(int codigo, String descricao) {  //enum tem o construtor private
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		for(Perfil perfil : Perfil.values()) {
			if(cod.equals(perfil.getCodigo())){
				return perfil;
			} 
		}
		throw new IllegalArgumentException("ID inválido: " + cod);
	}
}
