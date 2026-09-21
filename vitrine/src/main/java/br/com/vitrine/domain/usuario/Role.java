package br.com.vitrine.domain.usuario;

/**
 * Niveis de acesso do painel.
 *
 * MASTER   - dono da loja: tudo que ADMIN faz + gestao de equipe + dashboard.
 * ADMIN    - gerencia todo o estoque, inclusive produtos cadastrados por terceiros.
 * VENDEDOR - CRUD do estoque e visualizacao do proprio desempenho.
 *
 * O prefixo ROLE_ e adicionado no adapter de UserDetails
 * (br.com.vitrine.security.UsuarioDetails), nao no banco.
 */
public enum Role {

    MASTER("Dono"),
    ADMIN("Administrador"),
    VENDEDOR("Vendedor");

    private final String descricao;

    Role(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getAuthority() {
        return "ROLE_" + name();
    }
}
