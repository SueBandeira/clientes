package com.manger.clientes.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Endereco {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String cep;
  private String logradouro;
  private String complemento;
  private String bairro;
  private String cidade;
  private String uf;
  private String estado;

  //---------- Construtores ------------//
  public Endereco () {}

  public Endereco(DadosEndereco dtoEndereco) {
    this.cep = dtoEndereco.cep();
    this.logradouro = dtoEndereco.logradouro();
    this.complemento = dtoEndereco.complemento();
    this.bairro = dtoEndereco.bairro();
    this.setCidade(dtoEndereco.cidade());
    this.setUf(dtoEndereco.uf());
    this.estado = dtoEndereco.estado();
  }

  //---------- Getters e Setters ------------//
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCep() {
    return cep;
  }

  public void setCep(String cep) {
    this.cep = cep;
  }

  public String getLogradouro() {
    return logradouro;
  }

  public void setLogradouro(String logradouro) {
    this.logradouro = logradouro;
  }

  public String getComplemento() {
    return complemento;
  }

  public void setComplemento(String complemento) {
    this.complemento = complemento;
  }

  public String getBairro() {
    return bairro;
  }

  public void setBairro(String bairro) {
    this.bairro = bairro;
  }

  public String getCidade() {
    return cidade;
  }

  public void setCidade(String cidade) {
    //Criar uma classe de normalização para chamar no set em vez de criar toda a lógica nele.
    cidade = cidade.trim();
    cidade = cidade.replaceAll("\\s{2,}", " ");

    String[] palavras = cidade.split(" ");

    List<String> preposicoes = List.of("do", "dos", "da", "das", "de", "e");
    String novaCidade = "";

    for (int i = 0; i < palavras.length; i++) {
      String palavra = palavras[i].toLowerCase();

      if (preposicoes.contains(palavra)) {
        palavras[i] = palavra;
      } else {
        palavras[i] = palavra.substring(0, 1).toUpperCase().concat(palavra.substring(1).toLowerCase());
      }
      novaCidade = novaCidade + " " + palavras[i];
    }

    this.cidade = novaCidade.substring(1);
  }

  public String getUf() {
    return uf;
  }

  public void setUf(String uf) {
    this.uf = uf.toUpperCase();
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  @Override
  public String toString() {
    return "Endereco{" +
        "id=" + id +
        ", cep='" + cep + '\'' +
        ", logradouro='" + logradouro + '\'' +
        ", complemento='" + complemento + '\'' +
        ", bairro='" + bairro + '\'' +
        ", cidade='" + cidade + '\'' +
        ", uf='" + uf + '\'' +
        ", estado='" + estado + '\'' +
        '}';
  }
}
