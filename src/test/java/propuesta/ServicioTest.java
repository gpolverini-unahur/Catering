package ar.edu.unahur.obj2.catering;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicioTest {

  @Test
  void dadoUnServicio_alConsultarPrecio_retornaElPrecioAsignado() {
    Servicio servicio = new Servicio("Mesa de entradas", 80000);
    assertEquals(80000, servicio.getPrecio());
  }

  @Test
  void dadoUnServicio_alConsultarDescripcion_retornaElNombre() {
    Servicio servicio = new Servicio("Mesa de entradas", 80000);
    assertEquals("Mesa de entradas", servicio.getDescripcion());
  }

  @Test
  void dadoUnServicio_alConsultarCantidadDeServicios_retorna1() {
    Servicio servicio = new Servicio("Mesa de entradas", 80000);
    assertEquals(1, servicio.cantidadServicios());
  }

  @Test
  void dadoUnServicio_alConsultarSiIncluye_retornaTrueSiEsElMismo() {
    Servicio servicio = new Servicio("Mesa de entradas", 80000);
    assertTrue(servicio.incluye("Mesa de entradas"));
  }

  @Test
  void dadoUnServicio_alConsultarSiIncluye_retornaFalseSiEsOtro() {
    Servicio servicio = new Servicio("Mesa de entradas", 80000);
    assertFalse(servicio.incluye("DJ y sonido"));
  }

  @Test
  void dadoUnServicioConPrecioNegativo_alCrearlo_lanzaPrecioNegativoException() {
    PrecioNegativoException excepcion = assertThrows(
        PrecioNegativoException.class,
        () -> new Servicio("Servicio inválido", -100)
    );
    assertTrue(excepcion.getMessage().contains("no puede ser negativo"));
  }

  @Test
  void dadoUnServicioConPrecioCero_alCrearlo_noLanzaExcepcion() {
    Servicio servicio = new Servicio("Servicio gratis", 0);
    assertEquals(0, servicio.getPrecio());
  }
}
