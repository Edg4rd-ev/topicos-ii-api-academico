package br.com.academico.api.utils;

import java.time.LocalDate;
import java.util.Random;

public class GerarMatricula {
  public String gerarMatricula(TipoUsuario type) {
    Random random = new Random();
    int complement = random.nextInt(9000) + 1000;
    int year = LocalDate.now().getYear();
    int month = LocalDate.now().getMonthValue();
    int period = month >= 6 ? 01 : 02;
    String matricula = null;
    if (type == TipoUsuario.PROFESSOR) {
      matricula = String.format("PRF%d%d%d", year, period, complement);
    } else if (type == TipoUsuario.ALUNO) {
      matricula = String.format("ALU%d%d%d", year, period, complement);
    }
    return matricula;
  }
}
