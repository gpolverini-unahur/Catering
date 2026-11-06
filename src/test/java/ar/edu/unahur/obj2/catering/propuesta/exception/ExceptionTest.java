package ar.edu.unahur.obj2.catering.propuesta.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExceptionTest {

  @Test
  void dadoUnaPrecioNegativoException_alCrearlaConMensaje_guardaElMensaje() {
    PrecioNegativoException excepcion = new PrecioNegativoException("Precio inválido");
    
    assertEquals("Precio inválido", excepcion.getMessage());
  }

  @Test
  void dadoUnaPrecioNegativoException_alCrearlaConMensajeYCausa_guardaAmbos() {
    Throwable causa = new IllegalArgumentException("Causa original");
    PrecioNegativoException excepcion = new PrecioNegativoException("Precio inválido", causa);
    
    assertEquals("Precio inválido", excepcion.getMessage());
    assertEquals(causa, excepcion.getCause());
  }

  @Test
  void dadoUnaDescuentoInvalidoException_alCrearlaConMensaje_guardaElMensaje() {
    DescuentoInvalidoException excepcion = new DescuentoInvalidoException("Descuento fuera de rango");
    
    assertEquals("Descuento fuera de rango", excepcion.getMessage());
  }

  @Test
  void dadoUnaDescuentoInvalidoException_alCrearlaConMensajeYCausa_guardaAmbos() {
    Throwable causa = new IllegalArgumentException("Causa original");
    DescuentoInvalidoException excepcion = new DescuentoInvalidoException("Descuento fuera de rango", causa);
    
    assertEquals("Descuento fuera de rango", excepcion.getMessage());
    assertEquals(causa, excepcion.getCause());
  }
}
