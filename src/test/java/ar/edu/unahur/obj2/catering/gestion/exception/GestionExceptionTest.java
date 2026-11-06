package ar.edu.unahur.obj2.catering.gestion.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GestionExceptionTest {

  @Test
  void dadoUnaNadaParaRevertirException_alCrearlaConMensaje_guardaElMensaje() {
    NadaParaRevertirException excepcion = new NadaParaRevertirException("No hay historial");
    
    assertEquals("No hay historial", excepcion.getMessage());
  }

  @Test
  void dadoUnaNadaParaRevertirException_alCrearlaConMensajeYCausa_guardaAmbos() {
    Throwable causa = new IllegalStateException("Estado inválido");
    NadaParaRevertirException excepcion = new NadaParaRevertirException("No hay historial", causa);
    
    assertEquals("No hay historial", excepcion.getMessage());
    assertEquals(causa, excepcion.getCause());
  }

  @Test
  void dadoUnaPropuestaNoIncluidaException_alCrearlaConMensaje_guardaElMensaje() {
    PropuestaNoIncluidaException excepcion = new PropuestaNoIncluidaException("Propuesta no encontrada");
    
    assertEquals("Propuesta no encontrada", excepcion.getMessage());
  }

  @Test
  void dadoUnaPropuestaNoIncluidaException_alCrearlaConMensajeYCausa_guardaAmbos() {
    Throwable causa = new IllegalArgumentException("Argumento inválido");
    PropuestaNoIncluidaException excepcion = new PropuestaNoIncluidaException("Propuesta no encontrada", causa);
    
    assertEquals("Propuesta no encontrada", excepcion.getMessage());
    assertEquals(causa, excepcion.getCause());
  }
}
