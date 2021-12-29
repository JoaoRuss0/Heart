package pt.ipleiria.estg.dei.ei.dae.project.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "DADOSBIOMEDICOS")
@NamedQueries({
        @NamedQuery(
                name = "getAllDadosBiomedicos",
                query = "SELECT d FROM DadoBiomedico d ORDER BY d.name"
        )
})
public class DadoBiomedico {

    @Id
    @NotNull
    private String name;

    @NotNull
    private String descricao;

    @NotNull
    private Double maximum;

    @NotNull
    private Double minimum;

    @NotNull
    private String measuringUnit;

    @ElementCollection
    private List<String> qualificadores;

    /*static final List<String> qualificadores = new ArrayList<String>(Arrays.asList("muito baixo",
            "baixo",
            "normal",
            "alto",
            "muito alto"));*/

    //CRIAR OBSERVAÇÃO?  -> ligação entre doente e dado biomedico
    // quem?
    // quando?
    // a quem?
    // valor Quantitativo --> Tipo ObservaçaoVALOR
    // valor Qualitativo -->Tipo ObservaçaoCategoria


    public DadoBiomedico() {
    }

    public DadoBiomedico(String name, String descricao, Double maximum, Double minimum, String measuringUnit, List<String> qualificadores) {
        this.name = name;
        this.descricao = descricao;
        this.maximum = maximum;
        this.minimum = minimum;
        this.measuringUnit = measuringUnit;
        this.qualificadores = qualificadores;
    }

    /*Getters*/
    public String getName() {
        return name;
    }
    public String getDescricao() {
        return descricao;
    }
    public Double getMaximum() {
        return maximum;
    }
    public Double getMinimum() {
        return minimum;
    }
    public String getMeasuringUnit() {
        return measuringUnit;
    }
    public List<String> getQualificadores() {
        return qualificadores;
    }

    /*Setters*/
    public void setName(String name) {
        this.name = name;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public void setMaximum(Double maximum) {
        this.maximum = maximum;
    }
    public void setMinimum(Double minimum) {
        this.minimum = minimum;
    }
    public void setMeasuringUnit(String unidade) {
        this.measuringUnit = unidade;
    }
    public void setQualificadores(List<String> qualificadores) {
        this.qualificadores = qualificadores;
    }
}