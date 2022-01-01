package pt.ipleiria.estg.dei.ei.dae.project.entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ADMINISTRADORES")
@NamedQueries({
        @NamedQuery(
                name = "getAllAdministradores",
                query = "SELECT a FROM Administrador a ORDER BY a.name"
        )
})
public class Administrador extends User {

    public Administrador() {
    }

    public Administrador(String name, String email, String password) {
        super(name, email, password);
    }
}
