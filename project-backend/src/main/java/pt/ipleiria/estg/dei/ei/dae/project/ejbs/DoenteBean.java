package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Doente;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Stateless
public class DoenteBean {

    @PersistenceContext
    EntityManager entityManager;

    public Doente create(String name, String email, String password, int idade, double peso, double altura) throws Exception {

        Doente doente = find(email);

        if(doente != null) {
            throw new Exception("Found doente with email = '" + email + "'.");
        }

        doente = new Doente(name, email, password, idade, peso, altura);
        entityManager.persist(doente);

        return doente;
    }

    public List<Doente> getAll() {
        return entityManager.createNamedQuery("getAllDoentes", Doente.class).getResultList();
    }

    private Doente findOrFail(String email){
        Doente doente = find(email);

        if(doente == null){
            throw new NotFoundException("Doente with email = '" + email + "' not found.");
        }

        return doente;
    }

    private Doente find(String email) {
        return entityManager.find(Doente.class, email);
    }
}
