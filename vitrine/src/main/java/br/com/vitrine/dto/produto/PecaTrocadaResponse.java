package br.com.vitrine.dto.produto;

import br.com.vitrine.domain.produto.PecaTrocada;

import java.time.LocalDate;

public record PecaTrocadaResponse(
        Long id,
        String peca,
        String origem,
        LocalDate trocadaEm,
        String observacao
) {
    public static PecaTrocadaResponse de(PecaTrocada peca) {
        return new PecaTrocadaResponse(
                peca.getId(),
                peca.getPeca(),
                peca.getOrigem().name(),
                peca.getTrocadaEm(),
                peca.getObservacao()
        );
    }
}
