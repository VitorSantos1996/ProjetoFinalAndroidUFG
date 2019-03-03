package com.example.vitorpereira.projetofinal_android_ufg;

public class Receita {

    private String uId;
    private String nomeReceita;
    private String descricaoReceita;
    private int imagem;
    private String urlImagem;

    public Receita() {
    }

    @Override
    public String toString() {
        return "Receita{" +
                "uId='" + uId + '\'' +
                ", nomeReceita='" + nomeReceita + '\'' +
                ", descricaoReceita='" + descricaoReceita + '\'' +
                ", imagem=" + imagem +
                ", urlImagem='" + urlImagem + '\'' +
                '}';
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getNomeReceita() {
        return nomeReceita;
    }

    public void setNomeReceita(String nomeReceita) {
        this.nomeReceita = nomeReceita;
    }

    public String getDescricaoReceita() {
        return descricaoReceita;
    }

    public void setDescricaoReceita(String descricaoReceita) {
        this.descricaoReceita = descricaoReceita;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }
}

