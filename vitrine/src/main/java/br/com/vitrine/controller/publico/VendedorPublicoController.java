package br.com.vitrine.controller.publico;

import br.com.vitrine.dto.vendedor.VendedorResponse;
import br.com.vitrine.repository.VendedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VendedorPublicoController {

    private final VendedorRepository vendedorRepository;

    @GetMapping("/api/vendedores")
    public List<VendedorResponse> listar() {
        return vendedorRepository.findByAtivoTrueOrderByOrdemExibicaoAsc()
                .stream()
                .map(VendedorResponse::de)
                .toList();
    }
}
