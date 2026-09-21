package br.com.vitrine.domain.produto;

/**
 * Condicao do aparelho. Vira filtro na vitrine e selo no card.
 */
public enum Condicao {

    LACRADO("Lacrado"),
    NOVO("Novo"),
    SEMINOVO("Seminovo"),
    VITRINE("Ex-vitrine");

    private final String rotulo;

    Condicao(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }
}
