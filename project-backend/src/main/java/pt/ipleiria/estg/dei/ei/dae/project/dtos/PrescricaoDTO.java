package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Observacao;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Prescricao;
import pt.ipleiria.estg.dei.ei.dae.project.entities.ProfissionalDeSaude;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class PrescricaoDTO implements Serializable {

    private int id;
    private String causa;
    private String doenteEmail;
    private String dataInicio;
    private String dataFinal;
    private Prescricao.tipoPrescricao tipoPrescricao;
    private String profissionalDeSaudeEmail;
    private int observacaoID;

    public PrescricaoDTO() {
    }

    public PrescricaoDTO(int id, String causa, String doenteEmail, GregorianCalendar dataInicio, GregorianCalendar dataFinal, Prescricao.tipoPrescricao tipoPrescricao, ProfissionalDeSaude profissionalDeSaude, Observacao observacao) {
        this.id = id;
        this.causa = causa;
        this.doenteEmail = doenteEmail;
        this.dataInicio = gregorianToString(dataInicio);
        this.dataFinal = gregorianToString(dataFinal);
        this.tipoPrescricao = tipoPrescricao;
        this.profissionalDeSaudeEmail = profissionalDeSaude.getEmail();
        this.observacaoID = observacao.getId();
    }

    public PrescricaoDTO( String causa, String doenteEmail, String dataInicio, String dataFinal, Prescricao.tipoPrescricao tipoPrescricao, int observacaoID) {
        this.causa = causa;
        this.doenteEmail = doenteEmail;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.tipoPrescricao = tipoPrescricao;
        this.observacaoID = observacaoID;

    }

    /*Getters*/

    public int getId() {
        return id;
    }

    public String getCausa() {
        return causa;
    }

    public String getProfissionalDeSaudeEmail() {
        return profissionalDeSaudeEmail;
    }

    public int getObservacaoID() {
        return observacaoID;
    }
    /*Setters*/

    public void setId(int id) {
        this.id = id;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public String getDoenteEmail() {
        return doenteEmail;
    }

    public void setDoenteEmail(String doenteEmail) {
        this.doenteEmail = doenteEmail;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public Prescricao.tipoPrescricao getTipoPrescricao() {
        return tipoPrescricao;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }

    public void setTipoPrescricao(Prescricao.tipoPrescricao tipoPrescricao) {
        this.tipoPrescricao = tipoPrescricao;
    }

    public void setProfissionalDeSaudeEmail(String profissionalDeSaudeEmail) {
        this.profissionalDeSaudeEmail = profissionalDeSaudeEmail;
    }

    public void setObservacaoID(int observacaoID) {
        this.observacaoID = observacaoID;
    }

    private static String gregorianToString(GregorianCalendar calendar) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-mm-dd");
        fmt.setCalendar(calendar);
        String dateFormatted = fmt.format(calendar.getTime());

        return dateFormatted;
    }
}
