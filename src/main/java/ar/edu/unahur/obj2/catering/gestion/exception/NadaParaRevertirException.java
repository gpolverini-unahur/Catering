package ar.edu.unahur.obj2.catering.gestion.exception;

public class NadaParaRevertirException extends RuntimeException {
  public NadaParaRevertirException(String mensaje) {
    super(mensaje);
  }

  public NadaParaRevertirException(String mensaje, Throwable causa) {
    super(mensaje, causa);
  }
}
