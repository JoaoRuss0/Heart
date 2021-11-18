package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Doente;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Prescricao;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import java.lang.reflect.Array;
import java.util.List;

@Stateless
public class PrescricaoBean {

    @PersistenceContext
    EntityManager entityManager;

    @EJB
    DoenteBean doenteBean;

    public Prescricao create(String causa, String doenteEmail) throws Exception {

        Doente doente = doenteBean.find(doenteEmail);

        if(doente == null){
            throw  new NotFoundException("Doente with email " + doenteEmail + " does not exist.");
        }



        Prescricao prescricao = new Prescricao(causa, doente);
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

    private Prescricao find(int id) {
        return entityManager.find(Prescricao.class, id);
    }

}
