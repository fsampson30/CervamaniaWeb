
package model;


public class Classificacao {

    private String codigo_cerveja;
    private String nome_cerveja;
    private double estrelas;
    private String comentarios;

    public String getCodigo_cerveja() {
        return codigo_cerveja;
    }

    public void setCodigo_cerveja(String codigo_cerveja) {
        this.codigo_cerveja = codigo_cerveja;
    }

    public double getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(double estrelas) {
        this.estrelas = estrelas;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getNome_cerveja() {
        return nome_cerveja;
    }

    public void setNome_cerveja(String nome_cerveja) {
        this.nome_cerveja = nome_cerveja;
    }
}
