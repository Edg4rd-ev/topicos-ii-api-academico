package br.com.academico.api.dtos;

import java.util.Date;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDto {
  @Column(name = "nome", length = 255, nullable = false)
  private String nome;
  @Column(name = "cpf", length = 14, nullable = false, unique = true)
  private String cpf;
  @Column(name = "dataNascimento", nullable = false)
  private Date dataNascimento;
  @Column(name = "email", length = 255, nullable = false, unique = true)
  private String email;
  @Column(name = "celular", length = 14, nullable = false)
  private String celular;
  @Column(name = "matricula", length = 12, nullable = false, unique = true)
  private String matricula;
}
