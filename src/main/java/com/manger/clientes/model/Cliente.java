package com.manger.clientes.model;

import com.manger.clientes.DTO.Endereco;
import jakarta.persistence.*;

@Entity
public class Cliente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nome;
  private String telefone;
  private String email;

  @ManyToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "endereco_id")
  private Endereco endereco;

  //---------- Construtores ------------//
  public Cliente() {}

  public Cliente(String nome, String telefone, String email, Endereco endereco) {
    this.nome = nome;
    this.telefone = telefone;
    this.email = email;
    this.endereco = endereco;
  }

  //---------- Getters e Setters ------------//
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Endereco getEndereco() {
    return endereco;
  }

  public void setEndereco(Endereco endereco) {
    this.endereco = endereco;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  @Override
  public String toString() {
    return "\nCliente{" +
        "id: '" + id + '\'' +
        "nome: '" + nome + '\'' +
        ", CEP:'" + endereco.getCep() + '\'' +
        ", Endereço:'" + endereco + '\'' +
        ", telefone:'" + telefone + '\'' +
        ", email: '" + email + '\'' +
        ", usuário: " + usuario +
        '}';
  }
}
