package dev.arthuroliv.esmaltei.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "seguidores")
public class Seguidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario seguidor;

    @ManyToOne
    private Usuario seguido;


    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @PrePersist
    public  void  prePersist(){
        criadoEm = LocalDateTime.now();
    }
}
