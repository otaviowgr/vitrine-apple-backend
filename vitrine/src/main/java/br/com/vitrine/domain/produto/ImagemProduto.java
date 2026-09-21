package br.com.vitrine.domain.produto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Uma foto do carrossel. O arquivo vive no Cloudinary/S3; aqui fica so o ponteiro.
 *
 * Guardar largura e altura evita layout shift: o Next/Image recebe as dimensoes
 * reais e reserva o espaco antes do download, mantendo a proporcao original.
 */
@Entity
@Table(name = "produto_imagens", indexes = @Index(name = "idx_imagem_produto", columnList = "produto_id"))
@Getter
@Setter
@NoArgsConstructor
public class ImagemProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(name = "fk_imagem_produto"))
    private Produto produto;

    @NotBlank
    @Column(nullable = false, length = 500)
    private String url;

    /** Ex.: "vitrine/produtos/iphone-13-pro-a1b2c3". Necessario para apagar na nuvem. */
    @Column(name = "public_id", length = 255)
    private String publicId;

    @Column(name = "texto_alternativo", length = 160)
    private String textoAlternativo;

    private Integer largura;

    private Integer altura;

    /** Posicao no carrossel. A de ordem 0 e a capa na listagem. */
    @Column(nullable = false)
    private Integer ordem = 0;

    public ImagemProduto(String url, String publicId, Integer largura, Integer altura) {
        this.url = url;
        this.publicId = publicId;
        this.largura = largura;
        this.altura = altura;
    }
}
