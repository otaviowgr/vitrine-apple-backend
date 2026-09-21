package br.com.vitrine.repository;

import br.com.vitrine.domain.vendedor.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

    List<Vendedor> findByAtivoTrueOrderByOrdemExibicaoAsc();
}
