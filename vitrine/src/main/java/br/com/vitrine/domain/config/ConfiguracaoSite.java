package br.com.vitrine.domain.config;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Linha unica (id = 1) com o que o dono precisa mudar sem mexer em codigo:
 * o WhatsApp da assistencia tecnica e os textos do banner principal.
 *
 * Sem isso, trocar o numero da manutencao vira deploy. Com isso, vira um
 * formulario no painel master.
 */
@Entity
@Table(name = "configuracao_site")
@Getter
@Setter
@NoArgsConstructor
public class ConfiguracaoSite {

    @Id
    private Long id = 1L;

    /** WhatsApp exclusivo da assistencia tecnica, mesmo formato do vendedor. */
    @Pattern(regexp = "^55\\d{10,11}$")
    @Column(name = "whatsapp_assistencia", length = 13)
    private String whatsappAssistencia;

    @Column(name = "mensagem_assistencia", length = 300)
    private String mensagemAssistencia = "Ola! Preciso de um orcamento de assistencia tecnica.";

    @Column(name = "hero_titulo", length = 120)
    private String heroTitulo;

    @Column(name = "hero_subtitulo", length = 240)
    private String heroSubtitulo;

    @Column(name = "hero_texto_botao", length = 40)
    private String heroTextoBotao = "Ver aparelho";

    /** Produto que ocupa o banner principal. Nulo cai no destaque mais recente. */
    @Column(name = "hero_produto_id")
    private Long heroProdutoId;

    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;
}
