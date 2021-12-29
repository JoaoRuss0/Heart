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

    public DadoBiomedico create(String nome, String descricao, Double maximo, Double minimo, String unidadeMedida, List<String> qualificadores) throws Exception {
        DadoBiomedico dadoBiomedico = find(nome);

        if(dadoBiomedico != null) {
            throw new Exception("Found Dado Biomedico '" + nome + "'.");
        }

        dadoBiomedico = new DadoBiomedico(nome, descricao, maximo, minimo, unidadeMedida, qualificadores);
        entityManager.persist(dadoBiomedico);

        return dadoBiomedico;
    }

    public List<DadoBiomedico> getAll() {
        return entityManager.createNamedQuery("getAllDadosBiomedicos", DadoBiomedico.class).getResultList();
    }

    public void deleteDado(String nome){
        DadoBiomedico dado = this.find(nome);

        if(dado == null) {
            throw new NotFoundException("Dado Biomedico with name " + nome + " does not exist!");
        }
        entityManager.remove(dado);
    }

    public DadoBiomedico findOrFail(String nome) {
        DadoBiomedico dadoBiomedico = find(nome);

        if(dadoBiomedico == null) {
            throw new NotFoundException("Dado Biomedico '" + nome + "' was not found.");
        }

        return dadoBiomedico;
    }

    public DadoBiomedico update(DadoBiomedico dado, DadoBiomedicoDTO dadoBiomedicoDTO) {
        dado.setNome(dadoBiomedicoDTO.getNome());
        dado.setDescricao(dadoBiomedicoDTO.getDescricao());
        dado.setMinimo(dadoBiomedicoDTO.getMinimo());
        dado.setMaximo(dadoBiomedicoDTO.getMaximo());
        dado.setUnidadeMedida(dadoBiomedicoDTO.getUnidadeMedida());
        dado.setQualificadores(dadoBiomedicoDTO.getQualificadores());

        entityManager.merge(dado);

        return dado;
    }

    public DadoBiomedico find(String nome) {
        return entityManager.find(DadoBiomedico.class, nome);
    }
}
