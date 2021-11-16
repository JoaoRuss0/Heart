package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserBean {

    @PersistenceContext
    EntityManager entityManager;

    public User authenticate(final String email, final String password) throws Exception {
        User user = entityManager.find(User.class, email);

        if (user != null && user.getPassword().equals(User.hashPassword(password))) {
            return user;
        }

        throw new Exception("Failed logging in with email '" + email + "': unknown email or wrong password");
    }
}
