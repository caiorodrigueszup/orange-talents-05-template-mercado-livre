package com.br.zupacademy.api.mercadolivre.cadastroimagemparaproduto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class ImagensRequest {
	
	@NotNull
	@Size(min = 1)
	private List<MultipartFile> imagens = new ArrayList<MultipartFile>();

	public ImagensRequest(@NotNull @Size(min = 1) List<MultipartFile> imagens, @NotNull Long idProduto) {
		this.imagens = imagens;
	}
	
	@Override
	public String toString() {
		return "Imagens: " + imagens;
	}
	
	public List<MultipartFile> getImagens() {
		return imagens;
	}
}
