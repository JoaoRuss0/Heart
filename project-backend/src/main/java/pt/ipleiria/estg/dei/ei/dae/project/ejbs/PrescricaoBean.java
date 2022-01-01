package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Doente;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Prescricao;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Stateless
public class PrescricaoBean {

    @PersistenceContext
    EntityManager entityManager;

    @EJB
    DoenteBean doenteBean;

    public Prescricao create(String causa, String doenteEmail, String dataInicio, String dataFinal, Prescricao.tipoPrescricao tipoPrescricao) throws Exception {
        Doente doente = doenteBean.find(doenteEmail);

        if(doente == null){
            throw  new NotFoundException("Doente with email " + doenteEmail + " does not exist.");
        }


        Prescricao prescricao = new Prescricao(causa, stringToGregorian(dataInicio), stringToGregorian(dataFinal), tipoPrescricao, doente);

        doente.adicionarPrescricao(prescricao);
        entityManager.persist(prescricao);

        return prescricao;
    }

    public List<Prescricao> getAll() {
        return entityManager.createNamedQuery("getAllPrescricoes", Prescricao.class).getResultList();
    }

    private int findOrFail(int id){
        Prescricao prescricao = find(id);

        if(prescricao == null){
            throw new NotFoundException("Prescricao with id = '" + id + "' not found.");
        }

        return id;
    }

    public Prescricao find(int id) {
        return entityManager.find(Prescricao.class, id);
    }


    public Prescricao updatePrescricao(Prescricao prescricao, String causa, String dataInicio, String dataFinal, Prescricao.tipoPrescricao tipoPrescricao) throws ParseException {
        prescricao.setCausa(causa);
        prescricao.setTipoPrescricao(tipoPrescricao);
        prescricao.setDataInicio(stringToGregorian(dataInicio));
        prescricao.setDataFinal(stringToGregorian(dataFinal));
        entityManager.merge(prescricao);
        return prescricao;
    }


    public void deletePrescricao(int id) throws Exception {
        Prescricao prescricao = this.find(id);

        if(prescricao == null) {
            throw new NotFoundException("Prescricao with code " + id + " does not exist!");
        }
        entityManager.remove(prescricao);
    }

    private GregorianCalendar stringToGregorian(String data) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        Date date = df.parse(data);
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return (GregorianCalendar) cal;
    }

}
