package dev.arthuroliv.esmaltei.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "esmalte_usuario")
public class EsmalteUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate validade;

    @Column(name = "is_favorito")
    private boolean isFavorito;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "esmalte_id")
    private Esmalte esmalte;

    @PrePersist
    public  void  prePersist(){
        criadoEm = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdade(){
        atualizadoEm = LocalDateTime.now();
    }




}
