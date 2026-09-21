package br.com.vitrine.domain.produto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Historico de pecas trocadas do aparelho.
 *
 * Tabela propria em vez de um campo de texto: o cliente de seminovo pergunta
 * "tela original?" antes de qualquer outra coisa, e essa resposta precisa ser
 * filtravel e exibivel item a item, nao um paragrafo solto.
 */
@Entity
@Table(name = "produto_pecas_trocadas", indexes = @Index(name = "idx_peca_produto", columnList = "produto_id"))
@Getter
@Setter
@NoArgsConstructor
public class PecaTrocada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(name = "fk_peca_produto"))
    private Produto produto;

    /** Ex.: "Tela", "Bateria", "Tampa traseira", "Conector de carga". */
    @NotBlank
    @Column(nullable = false, length = 80)
    private String peca;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrigemPeca origem = OrigemPeca.ORIGINAL;

    @Column(name = "trocada_em")
    private LocalDate trocadaEm;

    @Column(length = 200)
    private String observacao;

    public enum OrigemPeca {
        ORIGINAL("Original Apple"),
        ORIGINAL_RETIRADA("Original retirada de outro aparelho"),
        PARALELA("Paralela / compativel");

        private final String descricao;

        OrigemPeca(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }
}
