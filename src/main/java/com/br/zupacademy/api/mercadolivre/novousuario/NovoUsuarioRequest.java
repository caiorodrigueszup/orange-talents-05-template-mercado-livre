package com.br.zupacademy.api.mercadolivre.novousuario;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NovoUsuarioRequest {
	
	@NotBlank
	@Email
	private String login;
	
	@NotBlank
	@Size(min = 6)
	private String senha;
	
	public NovoUsuarioRequest(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "Login: " + login + ", senha: " + senha;
	}
	
	public Usuario toModel(){
		return new Usuario(login, new SenhaLimpa(senha));
	}
}
