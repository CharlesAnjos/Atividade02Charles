package io.github.charlesanjos.atividade02charles.entidades;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Partida {
  private String nome;
  private ArrayList<Pais> paises;
  private int pontos;
  private String dataPath;
  private int totalPaises;

  public Partida() {
  }

  public Partida(String nome, ArrayList<Pais> paises, String dataPath) {
    this.nome = nome;
    this.paises = paises;
    this.dataPath = dataPath;
  }

  public int getTotalPaises() {
    return totalPaises;
  }

  public void setTotalPaises(int totalPaises) {
    this.totalPaises = totalPaises;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public ArrayList<Pais> getPaises() {
    return paises;
  }

  public void setPaises(ArrayList<Pais> paises) {
    this.paises = paises;
  }

  public int getPontos() {
    return pontos;
  }

  public void setPontos(int pontos) {
    this.pontos = pontos;
  }

  public String getDataPath() {
    return dataPath;
  }

  public void setDataPath(String dataPath) {
    this.dataPath = dataPath;
  }

  @NonNull
  @Override
  public String toString() {
    return "Partida{" +
        "nome='" + this.getNome() + '\'' +
        ", paises=" + this.getPaises() +
        ", pontos=" + this.getPontos() +
        ", totalPaises=" + this.getTotalPaises() +
        ", dataPath=" + this.getDataPath() +
        '}';
  }
}
