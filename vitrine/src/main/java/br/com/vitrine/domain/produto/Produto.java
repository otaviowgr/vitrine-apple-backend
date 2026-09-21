package br.com.vitrine.domain.produto;

import br.com.vitrine.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Item do estoque exibido na vitrine.
 *
 * Os indices abaixo cobrem as tres consultas quentes do site:
 * listagem por status, filtro por categoria e o destaque da home.
 */
@Entity
@Table(
        name = "produtos",
        indexes = {
                @Index(name = "idx_produto_status", columnList = "status"),
                @Index(name = "idx_produto_categoria", columnList = "categoria"),
                @Index(name = "idx_produto_destaque", columnList = "destaque")
        },
        uniqueConstraints = @UniqueConstraint(name = "uk_produto_slug", columnNames = "slug")
)
@Getter
@Setter
@NoArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 140)
    private String nome;

    /** Gerado a partir do nome no service. Usado na URL: /produto/iphone-13-pro-256gb-grafite */
    @Column(nullable = false, length = 170)
    private String slug;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Categoria categoria;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Condicao condicao;

    @NotNull
    @DecimalMin(value = "0.01")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    /** Preenchido apenas em promocao. Quando existe, a vitrine risca o preco cheio. */
    @Column(name = "preco_promocional", precision = 10, scale = 2)
    private BigDecimal precoPromocional;

    /**
     * Armazenamento em GB (1 TB = 1024). Guardar como numero deixa o filtro
     * "128 / 256 / 512" trivial; a formatacao fica no front.
     * Nulo para cabos, capas e demais acessorios.
     */
    @Column(name = "armazenamento_gb")
    private Integer armazenamentoGb;

    @Column(length = 40)
    private String cor;

    /** Texto livre. Ex.: "Micro risco na lateral direita, tela sem marcas." */
    @Column(name = "marcas_de_uso", columnDefinition = "TEXT")
    private String marcasDeUso;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    /** 0 a 100. Nulo quando nao se aplica (cabos, produtos lacrados sem leitura). */
    @Min(0)
    @Max(100)
    @Column(name = "saude_bateria")
    private Integer saudeBateria;

    @Column(length = 60)
    private String garantia;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusProduto status = StatusProduto.DISPONIVEL;

    /** Marca o aparelho que ocupa o banner principal da home. */
    @Column(nullable = false)
    private boolean destaque = false;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordem ASC")
    private List<ImagemProduto> imagens = new ArrayList<>();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("trocadaEm DESC")
    private List<PecaTrocada> pecasTrocadas = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cadastrado_por_id", foreignKey = @ForeignKey(name = "fk_produto_usuario"))
    private Usuario cadastradoPor;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    /** Preenchido pelo service quando o status vira VENDIDO. Base do dashboard. */
    @Column(name = "vendido_em")
    private LocalDateTime vendidoEm;

    // ---------- helpers de relacionamento ----------
    // Mantem os dois lados sincronizados. Sem isso, o orphanRemoval nao dispara.

    public void adicionarImagem(ImagemProduto imagem) {
        imagem.setProduto(this);
        imagem.setOrdem(this.imagens.size());
        this.imagens.add(imagem);
    }

    public void removerImagem(ImagemProduto imagem) {
        this.imagens.remove(imagem);
        imagem.setProduto(null);
    }

    public void adicionarPeca(PecaTrocada peca) {
        peca.setProduto(this);
        this.pecasTrocadas.add(peca);
    }

    /** Preco que a vitrine e a mensagem do WhatsApp devem usar. */
    public BigDecimal getPrecoVigente() {
        return precoPromocional != null ? precoPromocional : preco;
    }

    public boolean isDisponivel() {
        return StatusProduto.DISPONIVEL.equals(this.status);
    }
}
