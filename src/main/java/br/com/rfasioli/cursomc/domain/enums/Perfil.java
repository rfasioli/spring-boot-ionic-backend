package br.com.rfasioli.cursomc.domain.enums;

public enum Perfil {
	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	private int codigo;
	private String descricao;
	
	private Perfil(int cod, String descricao) {
		this.codigo = cod;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		
		for (Perfil estado : Perfil.values()) {
			if(cod.equals(estado.getCodigo())) {
				return estado;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
