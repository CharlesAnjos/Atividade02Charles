package io.github.charlesanjos.atividade02charles.entidades;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Partida {
  private String nome;
  private ArrayList<Pais> paises;
  private int pontos;
  private String dataPath;

  public Partida() {
  }

  public Partida(String nome, ArrayList<Pais> paises, int pontos, String dataPath) {
    this.nome = nome;
    this.paises = paises;
    this.pontos = pontos;
    this.dataPath = dataPath;
  }

  public int getTotalPontos(){
    return paises.size();
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
        "nome='" + nome + '\'' +
        ", paises=" + paises +
        ", pontos=" + pontos +
        '}';
  }
}
