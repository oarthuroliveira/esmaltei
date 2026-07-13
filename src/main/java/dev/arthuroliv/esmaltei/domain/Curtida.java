package dev.arthuroliv.esmaltei.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "curtidas")
public class Curtida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postagem_id")
    private Postagem postagem;

    @PrePersist
    public  void  prePersist(){
        criadoEm = LocalDateTime.now();
    }
}
