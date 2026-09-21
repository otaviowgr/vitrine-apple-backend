package br.com.vitrine.controller.publico;

import br.com.vitrine.domain.produto.Categoria;
import br.com.vitrine.domain.produto.Condicao;
import br.com.vitrine.dto.produto.ProdutoResponse;
import br.com.vitrine.repository.ProdutoRepository;
import br.com.vitrine.repository.ProdutoSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProdutoPublicoController {

    private final ProdutoRepository produtoRepository;

    @GetMapping("/api/produtos")
    public List<ProdutoResponse> listar(
            @RequestParam(required = false) Categoria categoria,
            @RequestParam(required = false) Condicao condicao,
            @RequestParam(required = false) Integer armazenamento
    ) {
        return produtoRepository
                .findAll(ProdutoSpecifications.filtrar(categoria, condicao, armazenamento))
                .stream()
                .map(ProdutoResponse::de)
                .toList();
    }

    @GetMapping("/api/produtos/{slug}")
    public ProdutoResponse buscarPorSlug(@PathVariable String slug) {
        return produtoRepository.findBySlug(slug)
                .map(ProdutoResponse::de)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));
    }
}
