package com.br.zupacademy.api.mercadolivre.novapergunta;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.br.zupacademy.api.mercadolivre.cadastroproduto.Produto;
import com.br.zupacademy.api.mercadolivre.novousuario.Usuario;

@Entity
public class Pergunta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private @NotBlank String titulo;

	@ManyToOne
	private @NotNull @Valid Usuario usuario;
	@ManyToOne
	private @NotNull @Valid Produto produto;

	private LocalDate instante;

	@Deprecated
	public Pergunta() {
	};

	public Pergunta(@NotBlank String titulo, @NotNull @Valid Usuario usuario, @NotNull @Valid Produto produto) {
		this.titulo = titulo;
		this.usuario = usuario;
		this.produto = produto;
		this.instante = LocalDate.now();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Usuario donoDoProduto() {
		return produto.getUsuario();
	}

	public String nomeDoProduto() {
		return produto.getNome();
	}

	public String getTitulo() {
		return titulo;
	}

	@Override
	public String toString() {
		return "Pergunta [id=" + id + ", titulo=" + titulo + ", usuario=" + usuario + ", produto=" + produto
				+ ", instante=" + instante + "]";
	}

}
