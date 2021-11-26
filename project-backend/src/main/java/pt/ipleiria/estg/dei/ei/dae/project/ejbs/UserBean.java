package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Stateless
public class UserBean {

    @PersistenceContext
    EntityManager entityManager;

    @EJB
    AdministradorBean administradorBean;

    public List<User> getAll() {
        return entityManager.createNamedQuery("getAllUsers", User.class).getResultList();
    }

    public User authenticate(final String email, final String password) throws Exception {
        User user = entityManager.find(User.class, email);

        if (user != null && user.getPassword().equals(User.hashPassword(password))) {
            return user;
        }

        throw new Exception("Failed logging in with email '" + email + "': unknown email or wrong password");
    }

    public User findOrFail(String email) {
        User user = find(email);

        if(user == null) {
            throw new NotFoundException("User with email='" + email +"' was not found.");
        }

        return user;
    }

    public User find(String email) {
        return entityManager.find(User.class, email);
    }
}
