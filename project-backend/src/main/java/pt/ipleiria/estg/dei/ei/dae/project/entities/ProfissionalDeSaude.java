package pt.ipleiria.estg.dei.ei.dae.project.entities;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "PROFISSIONAISDESAUDE")
@NamedQueries({
        @NamedQuery(
                name = "getAllProfissionaisDeSaude",
                query = "SELECT p FROM ProfissionalDeSaude p ORDER BY p.name"
        )
})
public class ProfissionalDeSaude extends User{

        @OneToMany(mappedBy = "profissionalDeSaude", cascade = CascadeType.REMOVE)
        private ArrayList<Prescricao> prescricoes;

        public ProfissionalDeSaude() {
                this.prescricoes = new ArrayList<>();
        }

        public ProfissionalDeSaude(String name, String email, String password) {
                super(name, email, password);
                this.prescricoes = new ArrayList<>();
        }

        public ArrayList<Prescricao> getPrescricoes() {
                return prescricoes;
        }

        public Prescricao adicionarPrescricao(Prescricao prescricao){
                System.out.println(prescricoes);
                System.out.println(prescricao.getProfissionalDeSaude());
                if(!prescricoes.contains(prescricao)){
                        prescricoes.add(prescricao);
                        return prescricao;
                }

                return null;
        }

        public void setPrescricoes(ArrayList<Prescricao> prescricoes) {
                this.prescricoes = prescricoes;
        }
}
