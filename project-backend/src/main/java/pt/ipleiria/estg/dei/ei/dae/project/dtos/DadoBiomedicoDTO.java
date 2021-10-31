package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import java.io.Serializable;

public class DadoBiomedicoDTO implements Serializable {

    private String name;
    private Double maximum;
    private Double minimum;
    private String measuringUnit;

    public DadoBiomedicoDTO() {
    }

    public DadoBiomedicoDTO(String name, Double maximum, Double minimum, String unidade) {
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
