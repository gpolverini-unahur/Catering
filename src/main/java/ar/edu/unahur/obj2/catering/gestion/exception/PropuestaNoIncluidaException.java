package ar.edu.unahur.obj2.catering.gestion.exception;

public class PropuestaNoIncluidaException extends RuntimeException {
  public PropuestaNoIncluidaException(String mensaje) {
    super(mensaje);
  }

  public PropuestaNoIncluidaException(String mensaje, Throwable causa) {
    super(mensaje, causa);
  }
}
