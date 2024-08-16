package br.com.academico.api.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorDisciplinasAlunosDTO {
  private ProfessorDto professor;
  private List<DisciplinaComAlunosDTO> disciplinas;
}
