package br.com.vitrine.dto.vendedor;

import br.com.vitrine.domain.vendedor.Vendedor;

public record VendedorResponse(
        Long id,
        String nome,
        String whatsapp,
        String fotoUrl,
        String apresentacao
) {
    public static VendedorResponse de(Vendedor vendedor) {
        return new VendedorResponse(
                vendedor.getId(),
                vendedor.getNome(),
                vendedor.getWhatsapp(),
                vendedor.getFotoUrl(),
                vendedor.getApresentacao()
        );
    }
}
