package com.manger.clientes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nome;

  private String senha;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
  private List<Cliente> clientes = new ArrayList<>();

  //---------- Construtores ------------//
  public Usuario () {}

  public Usuario(String nome, String senha) {
    this.nome = nome;
    this.senha = senha;
  }

  //---------- Getters e Setters ------------//
  public Long getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }


}
