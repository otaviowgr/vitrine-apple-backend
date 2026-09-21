package br.com.vitrine.domain.vendedor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Ficha publica do vendedor: e o que aparece no modal de orcamento da vitrine.
 *
 * Separada de Usuario de proposito. A vitrine precisa de nome, foto e WhatsApp;
 * nao precisa (e nao deve) tocar em e-mail, hash de senha ou role.
 */
@Entity
@Table(name = "vendedores")
@Getter
@Setter
@NoArgsConstructor
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nome;

    /**
     * WhatsApp em formato E.164 sem simbolos: 55 + DDD + numero.
     * Ex.: 5548999998888. E exatamente o formato que a URL wa.me espera,
     * entao o front nao precisa sanitizar nada.
     */
    @NotBlank
    @Pattern(regexp = "^55\\d{10,11}$", message = "Use 55 + DDD + numero, somente digitos. Ex.: 5548999998888")
    @Column(nullable = false, length = 13)
    private String whatsapp;

    /** URL segura (https) devolvida pelo Cloudinary/S3. */
    @Column(name = "foto_url", length = 500)
    private String fotoUrl;

    /** ID do asset na nuvem, guardado para conseguir apagar a imagem junto com o registro. */
    @Column(name = "foto_public_id", length = 255)
    private String fotoPublicId;

    /** Linha de apoio no card do modal. Ex.: "Atende Criciuma e regiao". */
    @Column(length = 120)
    private String apresentacao;

    /**
     * Vendedor inativo some da vitrine mas continua no banco,
     * preservando o historico de quem cadastrou cada produto.
     */
    @Column(nullable = false)
    private boolean ativo = true;

    /** Ordem de exibicao no modal. Menor valor aparece primeiro. */
    @Column(name = "ordem_exibicao")
    private Integer ordemExibicao = 0;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;
}
