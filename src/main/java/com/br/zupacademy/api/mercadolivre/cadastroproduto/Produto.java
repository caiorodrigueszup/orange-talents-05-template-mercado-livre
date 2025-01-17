package com.br.zupacademy.api.mercadolivre.cadastroproduto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
import com.br.zupacademy.api.mercadolivre.novaopiniaoproduto.Opiniao;
import com.br.zupacademy.api.mercadolivre.novapergunta.Pergunta;
import com.br.zupacademy.api.mercadolivre.novousuario.Usuario;

@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private @NotBlank String nome;
	private @NotNull @Positive BigDecimal valor;
	private @Min(0) @NotNull Integer quantidadeDisponivel;

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

	@OneToMany(mappedBy = "produto")
	@OrderBy("titulo asc")
	private SortedSet<Pergunta> perguntas = new TreeSet<Pergunta>();

	@OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
	private Set<Opiniao> opinioes = new HashSet<Opiniao>();
	
	@Deprecated
	public Produto() {
	}

	public Produto(@NotBlank String nome, @NotNull @Positive BigDecimal valor,
			@Min(0) @NotNull Integer quantidadeDisponivel,
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
		
		Assert.notNull(usuario, "O usuário não pode ser nulo");
		Assert.notNull(categoria, "Você precisa informar uma categoria");

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
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public String getNome() {
		return nome;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public Set<ImagemProduto> getImagens() {
		return imagens;
	}
	
	public Set<CaracteristicaProduto> getCaracteristicas() {
		return caracteristicas;
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

	public <T> Set<T> mapeiaCaracteristicas(
			Function<CaracteristicaProduto, T> mapFunction) {
		return this.caracteristicas.stream().map(mapFunction).collect(Collectors.toSet());
	}

	public <T> Set<T> mapeiaImagens(Function<ImagemProduto, T> mapFunction) {
		return this.imagens.stream().map(mapFunction).collect(Collectors.toSet());
	}

	public <T extends Comparable<T>> SortedSet<T> mapeiaPerguntas(Function<Pergunta, T> funcaoMapeadora) {
		return this.perguntas.stream().map(funcaoMapeadora).collect(Collectors.toCollection(TreeSet::new));
	}


	public Opinioes getOpinioes() {
		return new Opinioes(this.opinioes);
	}

    public boolean temEstoqueParaAbater(@Positive Integer quantidadePedida) {
		Assert.isTrue(quantidadePedida > 0, "Quantidade deve ser maior que 0.");

		if (quantidadeDisponivel < quantidadePedida) {
			return false;
		}
		quantidadeDisponivel -= quantidadePedida;
		return true;
    }
}
