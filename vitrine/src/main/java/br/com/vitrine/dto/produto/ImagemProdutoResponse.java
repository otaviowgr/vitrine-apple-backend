package br.com.vitrine.dto.produto;

import br.com.vitrine.domain.produto.ImagemProduto;

public record ImagemProdutoResponse(
        Long id,
        String url,
        String textoAlternativo,
        Integer largura,
        Integer altura,
        Integer ordem
) {
    public static ImagemProdutoResponse de(ImagemProduto imagem) {
        return new ImagemProdutoResponse(
                imagem.getId(),
                imagem.getUrl(),
                imagem.getTextoAlternativo(),
                imagem.getLargura(),
                imagem.getAltura(),
                imagem.getOrdem()
        );
    }
}
