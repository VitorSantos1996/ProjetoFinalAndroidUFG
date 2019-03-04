package com.example.vitorpereira.projetofinal_android_ufg;

import java.io.Serializable;

public class Receita implements Serializable {

    private String nome;
    private String ingredientes;
    private String tempoDePreparo;
    private String categoria;
    private String imagem;
    private int qtdPorcoes;

    public Receita() {
    }

    public Receita(String nome, String categoria, String ingredientes) {
        this.nome = nome;
        this.categoria = categoria;
        this.ingredientes = ingredientes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getTempoDePreparo() {
        return tempoDePreparo;
    }

    public void setTempoDePreparo(String tempoDePreparo) {
        this.tempoDePreparo = tempoDePreparo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getQtdPorcoes() {
        return qtdPorcoes;
    }

    public void setQtdPorcoes(int qtdPorcoes) {
        this.qtdPorcoes = qtdPorcoes;
    }
}
