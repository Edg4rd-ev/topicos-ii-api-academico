package br.com.academico.api.utils;

public enum TipoUsuario {
  PROFESSOR(0), ALUNO(1);

  private final int codigo;

  TipoUsuario(int codigo) {
    this.codigo = codigo;
  }

  public int getCodigo() {
    return codigo;
  }

  public static TipoUsuario fromCodigo(int codigo) {
    for (TipoUsuario tipo : TipoUsuario.values()) {
      if (tipo.getCodigo() == codigo) {
        return tipo;
      }
    }
    throw new IllegalArgumentException("Código inválido: " + codigo);
  }
}
