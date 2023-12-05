package io.github.charlesanjos.atividade02charles.entidades;

import java.util.ArrayList;

public class Partida {
  private String nome;
  private ArrayList<Pais> paises;
  private int pontos;

  public Partida(String nome, ArrayList<Pais> paises) {
    this.setNome(nome);
    this.setPaises(paises);
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
}
