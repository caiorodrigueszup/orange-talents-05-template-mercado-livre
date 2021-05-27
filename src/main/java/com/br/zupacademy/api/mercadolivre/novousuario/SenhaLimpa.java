package com.br.zupacademy.api.mercadolivre.novousuario;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

public class SenhaLimpa {
	@NotBlank
	@Size(min = 6)
	private String senha;
	
	public SenhaLimpa(@NotBlank @Size(min = 6) String senha) {
		Assert.hasLength(senha, "A senha precisa ser preenchida");
		Assert.isTrue(senha.length() >= 6, "A senha precisa ter no mínimo 6 caracteres");
		
		this.senha = senha;
	}
	
	public String hash() {
		return new BCryptPasswordEncoder().encode(senha);
	}
}
