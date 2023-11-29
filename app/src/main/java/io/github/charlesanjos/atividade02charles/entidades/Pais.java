package io.github.charlesanjos.atividade02charles.entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pais {

  @SerializedName("name")
  @Expose
  private PaisNome nome;

  @SerializedName("region")
  @Expose
  private String regiao;

  @SerializedName("population")
  @Expose
  private int populacao;

  @SerializedName("flags")
  @Expose
  private PaisBandeira bandeira;

  public Pais(String nome, String regiao, int populacao, String bandeira) {
    this.nome.setPaisNome(nome);
    this.regiao = regiao;
    this.populacao = populacao;
    this.bandeira.setPaisBandeira(bandeira);
  }
  public String getNome() {
    return nome.getPaisNome();
  }

  public void setNome(String nome) {
    this.nome.setPaisNome(nome);
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
    return bandeira.getPaisBandeira();
  }

  public void setBandeira(String bandera) {
    this.bandeira = bandeira;
  }

  private class PaisNome {
    @SerializedName("common")
    @Expose
    private String paisNome;

    public String getPaisNome() {
      return paisNome;
    }

    public void setPaisNome(String paisNome) {
      this.paisNome = paisNome;
    }
  }
  public class PaisBandeira {
    @SerializedName("png")
    @Expose
    private String paisBandeira;

    public String getPaisBandeira() {
      return paisBandeira;
    }

    public void setPaisBandeira(String paisBandeira) {
      this.paisBandeira = paisBandeira;
    }
  }
}
