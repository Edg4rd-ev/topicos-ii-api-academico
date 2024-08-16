package br.com.academico.api.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaDto {
  @Column(name = "idAluno", nullable = false)
  private int idAluno;
  @Column(name = "idTurma", nullable = false)
  private int idTurma;
}
