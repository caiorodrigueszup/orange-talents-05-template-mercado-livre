package com.br.zupacademy.api.mercadolivre.novousuario;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.br.zupacademy.api.mercadolivre.compartilhado.UniqueValue;

public class NovoUsuarioRequest {
	
	@NotBlank
	@Email
	@UniqueValue(domainClass = Usuario.class, fieldName = "email")
	private String email;
	
	@NotBlank
	@Size(min = 6)
	private String senha;
	
	public NovoUsuarioRequest(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "Email: " + email + ", senha: " + senha;
	}
	
	public Usuario toModel(){
		return new Usuario(email, new SenhaLimpa(senha));
	}
}
