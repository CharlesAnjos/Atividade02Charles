package io.github.charlesanjos.atividade02charles.entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pais {

  @SerializedName("name")
  @Expose
  private String nome;

  @SerializedName("region")
  @Expose
  private String regiao;

  @SerializedName("population")
  @Expose
  private int populacao;

  @SerializedName("flags")
  @Expose
  private String bandeira;

  public Pais(String nome, String regiao, int populacao, String bandeira) {
    this.nome = nome;
    this.regiao = regiao;
    this.populacao = populacao;
    this.bandeira = bandeira;
  }
  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public int getPopulacao() {
    return populacao;
  }

  public void setPopulacao(int populacao) {
    this.populacao = populacao;
  }

  public String getRegiao() {
    return regiao;
  }

  public void setRegiao(String regiao) {
    this.regiao = regiao;
  }

  public String getBandeira() {
    return bandeira;
  }

  public void setBandeira(String bandera) {
    this.bandeira = bandeira;
  }
}
