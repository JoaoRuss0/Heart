package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.DadoBiomedico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DadoBiomedicoDTO implements Serializable {

    private String name;
    private String descricao;
    private Double maximum;
    private Double minimum;
    private String measuringUnit;
    private List<String> qualificadores;



    public DadoBiomedicoDTO() {
    }


    public DadoBiomedicoDTO(String name, String descricao, Double maximum, Double minimum, String measuringUnit, List<String> qualificadores) {
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
