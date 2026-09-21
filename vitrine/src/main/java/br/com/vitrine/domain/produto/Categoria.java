package br.com.vitrine.domain.produto;

/**
 * Categorias da vitrine. O rotulo vai para os chips de filtro no front,
 * entao o texto aqui e o texto que o cliente le.
 */
public enum Categoria {

    IPHONE("iPhone", true),
    IPAD("iPad", true),
    MACBOOK("MacBook", true),
    APPLE_WATCH("Apple Watch", false),
    AIRPODS("AirPods", false),
    ACESSORIO("Acessorios", false),
    CABO_CARREGADOR("Cabos e carregadores", false);

    private final String rotulo;

    /** Define se o formulario do painel deve pedir armazenamento e saude da bateria. */
    private final boolean exigeArmazenamento;

    Categoria(String rotulo, boolean exigeArmazenamento) {
        this.rotulo = rotulo;
        this.exigeArmazenamento = exigeArmazenamento;
    }

    public String getRotulo() {
        return rotulo;
    }

    public boolean isExigeArmazenamento() {
        return exigeArmazenamento;
    }
}
