package com.br.zupacademy.api.mercadolivre.cadastroproduto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import com.br.zupacademy.api.mercadolivre.cadastrocategoria.Categoria;
import com.br.zupacademy.api.mercadolivre.cadastroimagemparaproduto.ImagemProduto;
import com.br.zupacademy.api.mercadolivre.novousuario.Usuario;

@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private @NotBlank String nome;
	private @NotNull @Positive BigDecimal valor;
	private @Min(0) @NotNull Long quantidadeDisponivel;

	@OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
	private @Size(min = 3) @Valid @NotNull Set<CaracteristicaProduto> caracteristicas = new HashSet<CaracteristicaProduto>();

	private @NotBlank @Length(max = 1000) String descricao;

	@NotNull
	@Valid
	@ManyToOne
	private Categoria categoria;

	@ManyToOne
	@NotNull
	@Valid
	private Usuario usuario;

	@NotNull
	private LocalDateTime instanteDaCriacao = LocalDateTime.now();

	@NotNull
	@Valid
	@OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
	private Set<ImagemProduto> imagens = new HashSet<ImagemProduto>();

	@Deprecated
	public Produto() {
	}

	public Produto(@NotBlank String nome, @NotNull @Positive BigDecimal valor,
			@Min(0) @NotNull Long quantidadeDisponivel,
			@Size(min = 3) @Valid @NotNull Collection<NovaCaracteristicaRequest> caracteristicas,
			@NotBlank @Length(max = 1000) String descricao, @NotNull @Valid Categoria categoria,
			@NotNull @Valid Usuario usuario) {
		this.nome = nome;
		this.valor = valor;
		this.quantidadeDisponivel = quantidadeDisponivel;
		Set<CaracteristicaProduto> caracteristicasProduto = caracteristicas.stream().map(c -> c.toModel(this))
				.collect(Collectors.toSet());

		Assert.isTrue(caracteristicasProduto.size() >= 3,
				"É preciso de no mínimo 3 característica para cadastrar um produto.");
		
		Assert.isNull(usuario, "O usuário não pode ser nulo");
		Assert.isNull(categoria, "Você precisa informar uma categoria");

		this.caracteristicas = caracteristicasProduto;
		this.descricao = descricao;
		this.categoria = categoria;
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", valor=" + valor + ", quantidadeDisponivel="
				+ quantidadeDisponivel + ", caracteristicas=" + caracteristicas + ", descricao=" + descricao
				+ ", categoria=" + categoria + ", usuario=" + usuario + ", instanteDaCriacao=" + instanteDaCriacao
				+ ", imagens=" + imagens + "]";
	}

	public Long idUsuarioPertenceAProduto() {
		return usuario.getId();
	}

	public void associaImagens(Set<String> links) {
		Set<ImagemProduto> imagens = links.stream().map(link -> new ImagemProduto(this, link))
				.collect(Collectors.toSet());

		this.imagens.addAll(imagens);
	}

	public boolean pertenceAoUsuario(Usuario usuarioAutenticado) {
		return this.usuario.equals(usuarioAutenticado);
	}
}
