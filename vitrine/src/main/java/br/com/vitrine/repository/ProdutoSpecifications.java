package br.com.vitrine.repository;

import br.com.vitrine.domain.produto.Categoria;
import br.com.vitrine.domain.produto.Condicao;
import br.com.vitrine.domain.produto.Produto;
import br.com.vitrine.domain.produto.StatusProduto;
import org.springframework.data.jpa.domain.Specification;

public final class ProdutoSpecifications {

    private ProdutoSpecifications() {
    }

    public static Specification<Produto> filtrar(Categoria categoria, Condicao condicao, Integer armazenamentoGb) {
        return Specification.allOf(
                (root, query, cb) -> root.get("status").in(StatusProduto.DISPONIVEL, StatusProduto.RESERVADO),
                (root, query, cb) -> categoria == null ? null : cb.equal(root.get("categoria"), categoria),
                (root, query, cb) -> condicao == null ? null : cb.equal(root.get("condicao"), condicao),
                (root, query, cb) -> armazenamentoGb == null ? null : cb.equal(root.get("armazenamentoGb"), armazenamentoGb)
        );
    }
}
