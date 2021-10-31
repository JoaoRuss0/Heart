package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.Administrador;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Doente;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Stateless
public class AdministradorBean {

    @PersistenceContext
    EntityManager entityManager;

    public Administrador create(String name, String email, String password) throws Exception {

        Administrador administrador = find(email);

        if(administrador != null) {
            throw new Exception("Found doente with email = '" + email + "'.");
        }

        administrador = new Administrador(name, email, password);
        entityManager.persist(administrador);

        return administrador;
    }

    public List<Administrador> getAll() {
        return entityManager.createNamedQuery("getAllAdministradores", Administrador.class).getResultList();
    }

    private Administrador findOrFail(String email){
        Administrador doente = find(email);

        if(doente == null){
            throw new NotFoundException("Administrador with email = '" + email + "' not found.");
        }

        return doente;
    }

    private Administrador find(String email) {
        return entityManager.find(Administrador.class, email);
    }
}
