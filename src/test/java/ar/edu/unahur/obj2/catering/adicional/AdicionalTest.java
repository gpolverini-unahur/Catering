package ar.edu.unahur.obj2.catering.adicional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.catering.propuesta.Menu;
import ar.edu.unahur.obj2.catering.propuesta.Propuesta;
import ar.edu.unahur.obj2.catering.propuesta.Servicio;

import static org.junit.jupiter.api.Assertions.*;

class AdicionalTest {

  private Servicio mesaEntradas;
  private Servicio parrilladaCompleta;
  private Servicio mesaDulce;
  private Servicio brindis;
  private Menu menuParrillero;

  @BeforeEach
  void setUp() {
    mesaEntradas = new Servicio("Mesa de entradas", 80000);
    parrilladaCompleta = new Servicio("Parrillada completa", 240000);
    mesaDulce = new Servicio("Mesa dulce", 60000);
    brindis = new Servicio("Brindis de bienvenida", 50000);

    menuParrillero = new Menu("Menú Parrillero", 10);
    menuParrillero.agregarComponente(mesaEntradas);
    menuParrillero.agregarComponente(parrilladaCompleta);
    menuParrillero.agregarComponente(mesaDulce);
    // Precio: 342.000
  }

  @Test
  void dadoUnServicioConMozosAdicionales_alConsultarPrecio_sumaElCostoDeMozos() {
    // Brindis: 50.000 + Mozos: 18.000 = 68.000
    Propuesta propuesta = new MozosAdicionales(brindis);
    assertEquals(68000, propuesta.getPrecio());
  }

  @Test
  void dadoUnMenuConMozosAdicionales_alConsultarPrecio_sumaElCostoDeMozos() {
    // Menu Parrillero: 342.000 + Mozos: 18.000 = 360.000
    Propuesta propuesta = new MozosAdicionales(menuParrillero);
    assertEquals(360000, propuesta.getPrecio());
  }

  @Test
  void dadoUnServicioConVajillaPremium_alConsultarPrecio_sumaElCostoDeVajilla() {
    // Brindis: 50.000 + Vajilla: 12.000 = 62.000
    Propuesta propuesta = new VajillaPremium(brindis);
    assertEquals(62000, propuesta.getPrecio());
  }

  @Test
  void dadoUnMenuConVajillaPremium_alConsultarPrecio_sumaElCostoDeVajilla() {
    // Menu Parrillero: 342.000 + Vajilla: 12.000 = 354.000
    Propuesta propuesta = new VajillaPremium(menuParrillero);
    assertEquals(354000, propuesta.getPrecio());
  }

  @Test
  void dadoUnServicioConNocturno_alConsultarPrecio_multiplicaPorFactorNocturno() {
    // Brindis: 50.000 * 1.20 = 60.000
    Propuesta propuesta = new ServicioNocturno(brindis);
    assertEquals(60000, propuesta.getPrecio());
  }

  @Test
  void dadoUnMenuConNocturno_alConsultarPrecio_multiplicaPorFactorNocturno() {
    // Menu Parrillero: 342.000 * 1.20 = 410.400
    Propuesta propuesta = new ServicioNocturno(menuParrillero);
    assertEquals(410400, propuesta.getPrecio());
  }

  @Test
  void dadoUnMenuConMozosRepetidos_alConsultarPrecio_sumaAmbosCostos() {
    // Menu Parrillero: 342.000 + Mozos: 18.000 + Mozos: 18.000 = 378.000
    Propuesta propuesta = new MozosAdicionales(
        new MozosAdicionales(menuParrillero)
    );
    assertEquals(378000, propuesta.getPrecio());
  }

  @Test
  void dadoUnServicioConVajillaRepetida_alConsultarPrecio_sumaAmbosCostos() {
    // Brindis: 50.000 + Vajilla: 12.000 + Vajilla: 12.000 = 74.000
    Propuesta propuesta = new VajillaPremium(
        new VajillaPremium(brindis)
    );
    assertEquals(74000, propuesta.getPrecio());
  }

  @Test
  void dadoUnServicioConNocturnoRepetido_alConsultarPrecio_multiplicaAmbosFactores() {
    // Mesa dulce: 60.000 * 1.20 * 1.20 = 86.400
    Propuesta propuesta = new ServicioNocturno(
        new ServicioNocturno(mesaDulce)
    );
    assertEquals(86400, propuesta.getPrecio());
  }

  @Test
  void dadoUnMenuConMozosYNocturno_alConsultarPrecio_aplicaEnOrdenCorrecta() {
    // Menu Parrillero: 342.000
    // + Mozos: 18.000 = 360.000
    // * Nocturno 1.20 = 432.000
    Propuesta propuesta = new ServicioNocturno(
        new MozosAdicionales(menuParrillero)
    );
    assertEquals(432000, propuesta.getPrecio());
  }

  @Test
  void dadoUnMenuConNocturnoYMozos_alConsultarPrecio_aplicaEnOrdenInvertida() {
    // Menu Parrillero: 342.000
    // * Nocturno 1.20 = 410.400
    // + Mozos: 18.000 = 428.400
    Propuesta propuesta = new MozosAdicionales(
        new ServicioNocturno(menuParrillero)
    );
    assertEquals(428400, propuesta.getPrecio());
  }

  @Test
  void dadoUnServicioConMozosAdicionales_alConsultarDescripcion_incluyeAmbasDescripciones() {
    Propuesta propuesta = new MozosAdicionales(brindis);
    assertEquals("Brindis de bienvenida + Mozos adicionales", propuesta.getDescripcion());
  }

  @Test
  void dadoUnMenuConTresAdicionales_alConsultarDescripcion_incluyeTodas() {
    // Menu Parrillero + Mozos + Vajilla + Nocturno
    Propuesta propuesta = new ServicioNocturno(
        new VajillaPremium(
            new MozosAdicionales(menuParrillero)
        )
    );
    String esperado = "Menú Parrillero + Mozos adicionales + Vajilla premium + Servicio nocturno";
    assertEquals(esperado, propuesta.getDescripcion());
  }

  @Test
  void dadoUnMenuConAdicionalesDecorador_alConsultarCantidad_noCambia() {
    // Menu Parrillero tiene 3 servicios
    // Con adicionales sigue siendo 3
    Propuesta propuesta = new MozosAdicionales(
        new VajillaPremium(menuParrillero)
    );
    assertEquals(3, propuesta.cantidadServicios());
  }

  @Test
  void dadoUnMenuConAdicionalesDecorador_alBuscarServicio_retornaLosOriginales() {
    Propuesta propuesta = new MozosAdicionales(
        new VajillaPremium(menuParrillero)
    );
    assertTrue(propuesta.incluye("Parrillada completa"));
    assertFalse(propuesta.incluye("Fotógrafo"));
  }

  @Test
  void dadoUnMenuDecoradorConAdicionales_alConsultarOriginal_noHaCambiado() {
    double precioOriginal = menuParrillero.getPrecio();
    String descripcionOriginal = menuParrillero.getDescripcion();

    // Crear propuesta con adicionales (no modifica menuParrillero)
    @SuppressWarnings("unused")
    Propuesta propuestaCon = new MozosAdicionales(menuParrillero);

    // La propuesta original sigue intacta
    assertEquals(342000, precioOriginal);
    assertEquals(342000, menuParrillero.getPrecio());
    assertEquals("Menú Parrillero", descripcionOriginal);
    assertEquals("Menú Parrillero", menuParrillero.getDescripcion());
  }

  @Test
  void dadoUnMenuConDecoradorDeAdicionales_alConsultarOriginal_seMantieneSinCambios() {
    Propuesta conAdicional = new MozosAdicionales(menuParrillero);
    
    assertEquals(360000, conAdicional.getPrecio());
    assertEquals("Menú Parrillero + Mozos adicionales", conAdicional.getDescripcion());
    
    // La propuesta original sin decoradores sigue siendo la original
    assertEquals(342000, menuParrillero.getPrecio());
    assertEquals("Menú Parrillero", menuParrillero.getDescripcion());
  }
}
