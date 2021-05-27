package com.br.zupacademy.api.mercadolivre.novousuario;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.Assert;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Email
	private String login;
	
	@NotBlank
	@Size(min = 6)
	private String senha;
	
	@NotNull
	private LocalDateTime dataCriacao = LocalDateTime.now();
	
	@Deprecated
	public Usuario() {};

	public Usuario(@NotBlank @Email String login, @Valid @NotNull SenhaLimpa senhaLimpa){
		Assert.hasLength(login, "O email deve ser preenchido.");
		Assert.notNull(senhaLimpa, "A senha precisa estar preenchida");
		
		this.login = login;
		this.senha = senhaLimpa.hash();
		
	}

	@Override
	public String toString() {
		return "Id: " + id + ", login: " + login + ", senha: " + senha + ", dataCriacao=" + dataCriacao;
	}
}
