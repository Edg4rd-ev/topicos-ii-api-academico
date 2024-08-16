package br.com.academico.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.academico.api.models.AlunoModel;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<AlunoModel, Integer> {
  Optional<AlunoModel> findByCpf(String cpf);

  Optional<AlunoModel> findByEmail(String email);

  Optional<AlunoModel> findByMatricula(String matricula);

  public void deleteByCpf(String cpf);

  public void deleteByMatricula(String matricula);

  boolean existsByMatricula(String matricula);
}
