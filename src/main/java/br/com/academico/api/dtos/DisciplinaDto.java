package br.com.academico.api.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisciplinaDto {
  @Column(name = "nome", length = 255, nullable = false)
  private String nome;
  @Column(name = "creditos", nullable = false)
  private int creditos;
}
