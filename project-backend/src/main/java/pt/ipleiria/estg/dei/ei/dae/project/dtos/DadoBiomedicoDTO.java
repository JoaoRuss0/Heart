package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import java.io.Serializable;
import java.util.List;

public class DadoBiomedicoDTO implements Serializable {

    private String nome;
    private String descricao;
    private Double maximo;
    private Double minimo;
    private String unidadeMedida;
    private List<String> qualificadores;

    public DadoBiomedicoDTO() {
    }

    public DadoBiomedicoDTO(String nome, String descricao, Double maximo, Double minimo, String unidadeMedida, List<String> qualificadores) {
        this.nome = nome;
        this.descricao = descricao;
        this.maximo = maximo;
        this.minimo = minimo;
        this.unidadeMedida = unidadeMedida;
        this.qualificadores = qualificadores;
    }

    /*Getters*/
    public String getNome() {
        return nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public Double getMaximo() {
        return maximo;
    }
    public Double getMinimo() {
        return minimo;
    }
    public String getUnidadeMedida() {
        return unidadeMedida;
    }
    public List<String> getQualificadores() {
        return qualificadores;
    }

    /*Setters*/
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public void setMaximo(Double maximo) {
        this.maximo = maximo;
    }
    public void setMinimo(Double minimo) {
        this.minimo = minimo;
    }
    public void setUnidadeMedida(String unidade) {
        this.unidadeMedida = unidade;
    }
    public void setQualificadores(List<String> qualificadores) {
        this.qualificadores = qualificadores;
    }
}
