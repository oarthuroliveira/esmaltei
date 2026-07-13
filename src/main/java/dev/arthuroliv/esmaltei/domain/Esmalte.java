package dev.arthuroliv.esmaltei.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "esmaltes")
public class Esmalte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String marca;

    private String colecao;

    private byte[] imagem;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @PrePersist
    public  void  prePersist(){
        criadoEm = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdade(){
        atualizadoEm = LocalDateTime.now();
    }

}
