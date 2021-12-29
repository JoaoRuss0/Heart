package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import pt.ipleiria.estg.dei.ei.dae.project.dtos.DadoBiomedicoDTO;
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

    public DadoBiomedico create(String name, String descricao, Double maximum, Double minimum, String measuringUnit, List<String> qualificadores) throws Exception {
        DadoBiomedico dadoBiomedico = find(name);

        if(dadoBiomedico != null) {
            throw new Exception("Found Dado Biomedico '" + name + "'.");
        }

        dadoBiomedico = new DadoBiomedico(name, descricao, maximum, minimum, measuringUnit, qualificadores);
        entityManager.persist(dadoBiomedico);

        return dadoBiomedico;
    }

    public List<DadoBiomedico> getAll() {
        return entityManager.createNamedQuery("getAllDadosBiomedicos", DadoBiomedico.class).getResultList();
    }

    public void deleteDado(String name){
        DadoBiomedico dado = this.find(name);

        if(dado == null) {
            throw new NotFoundException("Dado Biomedico with name " + name + " does not exist!");
        }
        entityManager.remove(dado);
    }

    public DadoBiomedico findOrFail(String name) {
        DadoBiomedico dadoBiomedico = find(name);

        if(dadoBiomedico == null) {
            throw new NotFoundException("Dado Biomedico '" + name + "' was not found.");
        }

        return dadoBiomedico;
    }

    public DadoBiomedico update(DadoBiomedico dado, DadoBiomedicoDTO dadoBiomedicoDTO) {
        dado.setName(dadoBiomedicoDTO.getName());
        dado.setDescricao(dadoBiomedicoDTO.getDescricao());
        dado.setMinimum(dadoBiomedicoDTO.getMinimum());
        dado.setMaximum(dadoBiomedicoDTO.getMaximum());
        dado.setMeasuringUnit(dadoBiomedicoDTO.getMeasuringUnit());
        dado.setQualificadores(dadoBiomedicoDTO.getQualificadores());

        entityManager.merge(dado);

        return dado;
    }

    public DadoBiomedico find(String name) {
        return entityManager.find(DadoBiomedico.class, name);
    }
}
