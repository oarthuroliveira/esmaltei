package dev.arthuroliv.esmaltei.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Email
    private String email;

    private String senha;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;


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
