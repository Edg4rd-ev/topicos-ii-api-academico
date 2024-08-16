package br.com.academico.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.academico.api.models.ProfessorModel;
import br.com.academico.api.models.TurmaModel;

public interface ProfessorRepository extends JpaRepository<ProfessorModel, Integer> {
  Optional<ProfessorModel> findByCpf(String cpf);

  Optional<ProfessorModel> findByEmail(String email);

  Optional<ProfessorModel> findByMatricula(String matricula);

  public void deleteByCpf(String cpf);

  public void deleteByMatricula(String matricula);

  boolean existsByMatricula(String matricula);

  @Query("SELECT t FROM TurmaModel t WHERE t.professor.id = :professorId AND t.dataFim IS NULL")
  List<TurmaModel> findTurmasByProfessorId(@Param("professorId") int professorId);
}
