package ar.edu.unahur.obj2.catering.propuesta.exception;

public class PrecioNegativoException extends RuntimeException {
  public PrecioNegativoException(String mensaje) {
    super(mensaje);
  }

  public PrecioNegativoException(String mensaje, Throwable causa) {
    super(mensaje, causa);
  }
}
