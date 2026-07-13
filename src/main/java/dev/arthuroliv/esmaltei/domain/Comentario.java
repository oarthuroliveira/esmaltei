package dev.arthuroliv.esmaltei.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

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

    @PreUpdate
    public void preUpdade(){
        atualizadoEm = LocalDateTime.now();
    }

}
