package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.ProfissionalDeSaude;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

@Stateless
public class ProfissionalDeSaudeBean {

    @PersistenceContext
    EntityManager entityManager;

    @EJB
    UserBean userBean;

    public ProfissionalDeSaude create(String name, String email, String password) throws MyConstraintViolationException, MyEntityExistsException {
        if(userBean.find(email) != null)
        {
            throw new MyEntityExistsException("User with email = '" + email + "' already exists.");
        }

        ProfissionalDeSaude profissionalDeSaude;

        try {
            profissionalDeSaude = new ProfissionalDeSaude(name, email, password);
            entityManager.persist(profissionalDeSaude);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        return profissionalDeSaude;
    }

    public ProfissionalDeSaude update(String name, String email) throws MyConstraintViolationException, MyEntityNotFoundException {
        ProfissionalDeSaude profissionalDeSaude = findOrFail(email);

        try {
            profissionalDeSaude.setName(name);

            entityManager.merge(profissionalDeSaude);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        return profissionalDeSaude;
    }

    public String delete(String email) throws MyEntityNotFoundException {
        ProfissionalDeSaude profissionalDeSaude = findOrFail(email);
        entityManager.remove(profissionalDeSaude);

        return email;
    }

    private ProfissionalDeSaude findOrFail(String email) throws MyEntityNotFoundException {
        ProfissionalDeSaude profissionalDeSaude = find(email);

        if(profissionalDeSaude == null){
            throw new MyEntityNotFoundException("Profissional de Saude with email = '" + email + "' not found.");
        }

        return profissionalDeSaude;
    }

    public ProfissionalDeSaude find(String email) {
        return entityManager.find(ProfissionalDeSaude.class, email);
    }
}
