package br.com.academico.api.dtos;

import java.util.Date;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurmaDto {
  @Column(name = "dataInicio", nullable = false)
  private Date dataInicio;
  @Column(name = "dataFim", nullable = true)
  private Date dataFim;
  @Column(name = "idProfessor", nullable = false)
  private int idProfessor;
  @Column(name = "idDisciplina", nullable = false)
  private int idDisciplina;
}
