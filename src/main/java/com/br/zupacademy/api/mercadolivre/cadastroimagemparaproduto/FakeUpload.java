package com.br.zupacademy.api.mercadolivre.cadastroimagemparaproduto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Primary
public class FakeUpload implements Uploader {

	
	public Set<String> envia(List<MultipartFile> imagens) {
		return imagens.stream()
				.map(imagem ->
					"http://s3.com.br/"
					+ imagem.getOriginalFilename()
				).collect(Collectors.toSet());
	}
}
