package com.example.vitorpereira.projetofinal_android_ufg;

import java.io.Serializable;

public class Comentario implements Serializable {

    private String uId;
    private String textoComentario;
    private int imagem;
    private String urlImagem;

    public Comentario() {
    }

    public Comentario(String uId, String textoComentario, int imagem, String urlImagem) {
        this.uId = uId;
        this.textoComentario = textoComentario;
        this.imagem = imagem;
        this.urlImagem = urlImagem;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getTextoComentario() {
        return textoComentario;
    }

    public void setTextoComentario(String textoComentario) {
        this.textoComentario = textoComentario;
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

