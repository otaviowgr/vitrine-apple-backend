package br.com.vitrine.domain.usuario;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Conta de acesso ao painel administrativo.
 *
 * Regra de negocio: existe exatamente um usuario com role MASTER (o dono).
 * Somente ele enxerga as rotas /api/admin/vendedores e /api/admin/usuarios.
 */
@Entity
@Table(
        name = "usuarios",
        uniqueConstraints = @UniqueConstraint(name = "uk_usuario_email", columnNames = "email")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nome;

    @Email
    @NotBlank
    @Column(nullable = false, length = 160)
    private String email;

    /** Hash BCrypt. Nunca expor em DTO de resposta. */
    @NotBlank
    @Column(name = "senha_hash", nullable = false, length = 100)
    private String senhaHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role = Role.VENDEDOR;

    @Column(nullable = false)
    private boolean ativo = true;

    /**
     * Vinculo opcional com a ficha publica de vendedor.
     * Um usuario ADMIN interno pode existir sem aparecer na vitrine.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id", foreignKey = @ForeignKey(name = "fk_usuario_vendedor"))
    private br.com.vitrine.domain.vendedor.Vendedor vendedor;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "ultimo_acesso")
    private LocalDateTime ultimoAcesso;

    public boolean isMaster() {
        return Role.MASTER.equals(this.role);
    }
}
