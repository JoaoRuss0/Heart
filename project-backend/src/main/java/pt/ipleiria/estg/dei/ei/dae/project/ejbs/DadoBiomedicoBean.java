package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.DadoBiomedico;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Stateless
public class DadoBiomedicoBean {

    @PersistenceContext
    EntityManager entityManager;

    public DadoBiomedico create(String name, Double maximum, Double minimum, String measuringUnit) throws Exception {
        DadoBiomedico dadoBiomedico = find(name);

        if(dadoBiomedico != null) {
            throw new Exception("Found Dado Biomedico '" + name + "'.");
        }

        dadoBiomedico = new DadoBiomedico(name, maximum, minimum, measuringUnit);
        entityManager.persist(dadoBiomedico);

        return dadoBiomedico;
    }

    public List<DadoBiomedico> getAll() {
        return entityManager.createNamedQuery("getAllDadosBiomedicos", DadoBiomedico.class).getResultList();
    }

    public DadoBiomedico findOrFail(String name) {
        DadoBiomedico dadoBiomedico = find(name);

        if(dadoBiomedico == null) {
            throw new NotFoundException("Dado Biomedico '" + name + "' was not found.");
        }

        return dadoBiomedico;
    }

    public DadoBiomedico find(String name) {
        return entityManager.find(DadoBiomedico.class, name);
    }
}
