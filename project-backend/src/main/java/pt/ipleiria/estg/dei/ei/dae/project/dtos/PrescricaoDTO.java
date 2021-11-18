package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Doente;

import java.io.Serializable;

public class PrescricaoDTO implements Serializable {

    private int id;
    private String causa;


    public PrescricaoDTO() {

    }

    public PrescricaoDTO(int id, String causa) {
        this.id = id;
        this.causa = causa;

    }

    /*Getters*/

    public int getId() {
        return id;
    }

    public String getCausa() {
        return causa;
    }



    /*Setters*/

    public void setId(int id) {
        this.id = id;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }
}
