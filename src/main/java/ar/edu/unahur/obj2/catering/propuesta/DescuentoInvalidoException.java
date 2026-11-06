package ar.edu.unahur.obj2.catering;

public class DescuentoInvalidoException extends RuntimeException {
  public DescuentoInvalidoException(String mensaje) {
    super(mensaje);
  }

  public DescuentoInvalidoException(String mensaje, Throwable causa) {
    super(mensaje, causa);
  }
}
