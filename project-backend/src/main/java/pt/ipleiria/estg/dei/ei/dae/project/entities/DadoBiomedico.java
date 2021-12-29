package pt.ipleiria.estg.dei.ei.dae.project.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "DADOSBIOMEDICOS")
@NamedQueries({
        @NamedQuery(
                name = "getAllDadosBiomedicos",
                query = "SELECT d FROM DadoBiomedico d ORDER BY d.nome"
        )
})
public class DadoBiomedico {

    @Id
    @NotNull
    private String nome;

    @NotNull
    private String descricao;

    @NotNull
    private Double maximo;

    @NotNull
    private Double minimo;

    @NotNull
    private String unidadeMedida;

    @ElementCollection
    private List<String> qualificadores;



    //CRIAR OBSERVAÇÃO?  -> ligação entre doente e dado biomedico
    // quem?
    // quando?
    // a quem?
    // valor Quantitativo --> Tipo ObservaçaoVALOR
    // valor Qualitativo -->Tipo ObservaçaoCategoria


    public DadoBiomedico() {
    }

    public DadoBiomedico(String nome, String descricao, Double maximo, Double minimo, String unidadeMedida, List<String> qualificadores) {
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
    public void setNome(String name) {
        this.nome = name;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public void setMaximo(Double maximum) {
        this.maximo = maximum;
    }
    public void setMinimo(Double minimum) {
        this.minimo = minimum;
    }
    public void setUnidadeMedida(String unidade) {
        this.unidadeMedida = unidade;
    }
    public void setQualificadores(List<String> qualificadores) {
        this.qualificadores = qualificadores;
    }
}