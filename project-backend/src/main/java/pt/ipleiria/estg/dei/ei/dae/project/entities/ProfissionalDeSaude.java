package pt.ipleiria.estg.dei.ei.dae.project.entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PROFISSIONAISDESAUDE")
@NamedQueries({
        @NamedQuery(
                name = "getAllProfissionaisDeSaude",
                query = "SELECT p FROM ProfissionalDeSaude p ORDER BY p.name"
        )
})
public class ProfissionalDeSaude extends User{

        public ProfissionalDeSaude() {
        }

        public ProfissionalDeSaude(String name, String email, String password) {
                super(name, email, password);
        }
}
