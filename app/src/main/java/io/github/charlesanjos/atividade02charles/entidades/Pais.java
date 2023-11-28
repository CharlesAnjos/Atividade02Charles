package io.github.charlesanjos.atividade02charles.entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pais {

  @SerializedName("numericCode")
  @Expose
  private int id;

  @SerializedName("name")
  @Expose
  private String nome;

  @SerializedName("region")
  @Expose
  private String regiao;

  @SerializedName("population")
  @Expose
  private int populacao;

  @SerializedName("flag")
  @Expose
  private String bandeira;

  @SerializedName("independent")
  @Expose
  private boolean independent;

  public Pais(int id, String nome, String regiao, int populacao, String bandeira, boolean independent) {
    this.id = id;
    this.nome = nome;
    this.regiao = regiao;
    this.populacao = populacao;
    this.bandeira = bandeira;
    this.independent = independent;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean isIndependent() {
    return independent;
  }

  public void setIndependent(boolean independent) {
    this.independent = independent;
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

  public void setBandeira(String bandeira) {
    this.bandeira = bandeira;
  }
}
