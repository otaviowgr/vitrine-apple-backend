package br.com.vitrine.domain.produto;

/**
 * Ciclo de vida do item no estoque.
 *
 * O botao "Vendido" do painel troca o status em vez de apagar a linha:
 * o registro continua servindo para o dashboard e para o historico do vendedor.
 * DELETE fisico so no caso de cadastro errado.
 */
public enum StatusProduto {

    DISPONIVEL("Disponivel"),
    RESERVADO("Reservado"),
    VENDIDO("Vendido"),
    OCULTO("Oculto na vitrine");

    private final String rotulo;

    StatusProduto(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }

    /** Apenas DISPONIVEL e RESERVADO aparecem para o cliente. */
    public boolean isVisivelNaVitrine() {
        return this == DISPONIVEL || this == RESERVADO;
    }
}
