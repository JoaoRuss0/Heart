package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Prescricao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class ObservacaoDTO implements Serializable {

    private int id;
    private String doenteEmail;
    private String profissionalDeSaudeEmail;
    private String nomeDadoBiomedico;
    private String data;
    private int valorQuantitativo;
    private String valorQualitativo;
    private int prescricaoID;

    public ObservacaoDTO() {
    }

    public ObservacaoDTO(int id, String doenteEmail, String profissionalDeSaudeEmail, String nomeDadoBiomedico, GregorianCalendar data, int valorQuantitativo, String valorQualitativo, Prescricao prescricao) {
        this.id = id;
        this.doenteEmail = doenteEmail;
        this.profissionalDeSaudeEmail = profissionalDeSaudeEmail;
        this.nomeDadoBiomedico = nomeDadoBiomedico;
        this.data = gregorianToString(data);
        this.valorQuantitativo = valorQuantitativo;
        this.valorQualitativo = valorQualitativo;

        this.prescricaoID = (prescricao==null)? 0 : prescricao.getId();
    }


    /*Getters*/
    public int getId() {
        return id;
    }
    public String getDoenteEmail() {
        return doenteEmail;
    }
    public String getProfissionalDeSaudeEmail() {
        return profissionalDeSaudeEmail;
    }
    public String getNomeDadoBiomedico() {
        return nomeDadoBiomedico;
    }
    public String getData() {
        return data;
    }
    public int getValorQuantitativo() {
        return valorQuantitativo;
    }
    public String getValorQualitativo() {
        return valorQualitativo;
    }

    public int getPrescricaoID() {
        return prescricaoID;
    }

    /*Setters*/
    public void setId(int id) {
        this.id = id;
    }
    public void setDoenteEmail(String doenteEmail) {
        this.doenteEmail = doenteEmail;
    }
    public void setProfissionalDeSaudeEmail(String profissionalDeSaudeEmail) {
        this.profissionalDeSaudeEmail = profissionalDeSaudeEmail;
    }
    public void setNomeDadoBiomedico(String nomeDadoBiomedico) {
        this.nomeDadoBiomedico = nomeDadoBiomedico;
    }
    public void setData(String data) {
        this.data = data;
    }
    public void setValorQuantitativo(int valorQuantitativo) {
        this.valorQuantitativo = valorQuantitativo;
    }
    public void setValorQualitativo(String valorQualitativo) {
        this.valorQualitativo = valorQualitativo;
    }

    public void setPrescricaoID(int prescricaoID) {
        this.prescricaoID = prescricaoID;
    }

    private static String gregorianToString(GregorianCalendar calendar) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-mm-dd");
        fmt.setCalendar(calendar);
        String dateFormatted = fmt.format(calendar.getTime());

        return dateFormatted;
    }
}
