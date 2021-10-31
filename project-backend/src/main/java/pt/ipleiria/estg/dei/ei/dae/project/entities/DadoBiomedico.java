package pt.ipleiria.estg.dei.ei.dae.project.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    private Double maximum;

    @NotNull
    private Double minimum;

    @NotNull
    private String measuringUnit;

    public DadoBiomedico() {
    }

    public DadoBiomedico(String name, Double maximum, Double minimum, String unidade) {
        this.name = name;
        this.maximum = maximum;
        this.minimum = minimum;
        this.measuringUnit = unidade;
    }

    /*Getters*/
    public String getName() {
        return name;
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

    /*Setters*/
    public void setName(String name) {
        this.name = name;
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
}