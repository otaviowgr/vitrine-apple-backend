package br.com.vitrine.dto.produto;

import br.com.vitrine.domain.produto.Produto;

import java.math.BigDecimal;
import java.util.List;

public record ProdutoResponse(
        Long id,
        String nome,
        String slug,
        String categoria,
        String categoriaRotulo,
        String condicao,
        String condicaoRotulo,
        BigDecimal preco,
        BigDecimal precoPromocional,
        BigDecimal precoVigente,
        Integer armazenamentoGb,
        String cor,
        String marcasDeUso,
        String descricao,
        Integer saudeBateria,
        String garantia,
        String status,
        boolean destaque,
        List<ImagemProdutoResponse> imagens,
        List<PecaTrocadaResponse> pecasTrocadas
) {
    public static ProdutoResponse de(Produto produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getSlug(),
                produto.getCategoria().name(),
                produto.getCategoria().getRotulo(),
                produto.getCondicao().name(),
                produto.getCondicao().getRotulo(),
                produto.getPreco(),
                produto.getPrecoPromocional(),
                produto.getPrecoVigente(),
                produto.getArmazenamentoGb(),
                produto.getCor(),
                produto.getMarcasDeUso(),
                produto.getDescricao(),
                produto.getSaudeBateria(),
                produto.getGarantia(),
                produto.getStatus().name(),
                produto.isDestaque(),
                produto.getImagens().stream().map(ImagemProdutoResponse::de).toList(),
                produto.getPecasTrocadas().stream().map(PecaTrocadaResponse::de).toList()
        );
    }
}
