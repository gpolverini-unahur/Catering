package ar.edu.unahur.obj2.catering;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

  private Servicio mesaEntradas;
  private Servicio parrilladaCompleta;
  private Servicio mesaDulce;
  private Servicio barraDeTrasos;
  private Servicio djySonido;
  private Servicio brindis;

  @BeforeEach
  void setUp() {
    // Catálogo inicial según README
    mesaEntradas = new Servicio("Mesa de entradas", 80000);
    parrilladaCompleta = new Servicio("Parrillada completa", 240000);
    mesaDulce = new Servicio("Mesa dulce", 60000);
    barraDeTrasos = new Servicio("Barra de tragos", 150000);
    djySonido = new Servicio("DJ y sonido", 120000);
    brindis = new Servicio("Brindis de bienvenida", 50000);
  }

  @Test
  void dadoUnMenu_alConsultarPrecioSinComponentes_retorna0() {
    Menu menu = new Menu("Menu vacío", 10);
    assertEquals(0, menu.getPrecio());
  }

  @Test
  void dadoUnMenuConUnServicio_alConsultarPrecio_retornaElPrecioDelServicio() {
    Menu menu = new Menu("Menu simple", 0);
    menu.agregarComponente(mesaEntradas);
    assertEquals(80000, menu.getPrecio());
  }

  @Test
  void dadoUnMenuParrilleroConTresServicios_alConsultarPrecio_retornaConDescuento() {
    // Menú Parrillero: Mesa de entradas + Parrillada completa + Mesa dulce, 10% descuento
    // (80.000 + 240.000 + 60.000) * 0.9 = 380.000 * 0.9 = 342.000
    Menu menuParrillero = new Menu("Menú Parrillero", 10);
    menuParrillero.agregarComponente(mesaEntradas);
    menuParrillero.agregarComponente(parrilladaCompleta);
    menuParrillero.agregarComponente(mesaDulce);

    assertEquals(342000, menuParrillero.getPrecio());
  }

  @Test
  void dadoPackFiestaConDosServicios_alConsultarPrecio_retornaConDescuento() {
    // Pack Fiesta: Barra de tragos + DJ y sonido, 5% descuento
    // (150.000 + 120.000) * 0.95 = 270.000 * 0.95 = 256.500
    Menu packFiesta = new Menu("Pack Fiesta", 5);
    packFiesta.agregarComponente(barraDeTrasos);
    packFiesta.agregarComponente(djySonido);

    assertEquals(256500, packFiesta.getPrecio());
  }

  @Test
  void dadoUnMenuConDescuentoNegativo_alCrearlo_lanzaExcepcion() {
    DescuentoInvalidoException excepcion = assertThrows(
        DescuentoInvalidoException.class,
        () -> new Menu("Menu inválido", -5)
    );
    assertTrue(excepcion.getMessage().contains("debe estar entre 0 y 100"));
  }

  @Test
  void dadoUnMenuConDescuentoMayorACien_alCrearlo_lanzaExcepcion() {
    DescuentoInvalidoException excepcion = assertThrows(
        DescuentoInvalidoException.class,
        () -> new Menu("Menu inválido", 150)
    );
    assertTrue(excepcion.getMessage().contains("debe estar entre 0 y 100"));
  }

  @Test
  void dadoUnMenuParrilleroConTresServicios_alConsultarCantidad_retorna3() {
    Menu menuParrillero = new Menu("Menú Parrillero", 10);
    menuParrillero.agregarComponente(mesaEntradas);
    menuParrillero.agregarComponente(parrilladaCompleta);
    menuParrillero.agregarComponente(mesaDulce);

    assertEquals(3, menuParrillero.cantidadServicios());
  }

  @Test
  void dadoUnMenuParrilleroConParrillada_alBuscarParrillada_retornaTrue() {
    Menu menuParrillero = new Menu("Menú Parrillero", 10);
    menuParrillero.agregarComponente(mesaEntradas);
    menuParrillero.agregarComponente(parrilladaCompleta);
    menuParrillero.agregarComponente(mesaDulce);

    assertTrue(menuParrillero.incluye("Parrillada completa"));
  }

  @Test
  void dadoUnMenuParrilleroSinDJ_alBuscarDJ_retornaFalse() {
    Menu menuParrillero = new Menu("Menú Parrillero", 10);
    menuParrillero.agregarComponente(mesaEntradas);
    menuParrillero.agregarComponente(parrilladaCompleta);
    menuParrillero.agregarComponente(mesaDulce);

    assertFalse(menuParrillero.incluye("DJ y sonido"));
  }

  @Test
  void dadoUnEventoCompletoAnidado_alConsultarPrecio_retornaConDescuentoAplicado() {
    // Evento Completo: Menú Parrillero + Pack Fiesta + Brindis, 20% descuento
    // Menú Parrillero = 342.000
    // Pack Fiesta = 256.500
    // Brindis = 50.000
    // (342.000 + 256.500 + 50.000) - 20% = 648.500 * 0.80 = 518.800

    Menu menuParrillero = new Menu("Menú Parrillero", 10);
    menuParrillero.agregarComponente(mesaEntradas);
    menuParrillero.agregarComponente(parrilladaCompleta);
    menuParrillero.agregarComponente(mesaDulce);

    Menu packFiesta = new Menu("Pack Fiesta", 5);
    packFiesta.agregarComponente(barraDeTrasos);
    packFiesta.agregarComponente(djySonido);

    Menu eventoCompleto = new Menu("Evento Completo", 20);
    eventoCompleto.agregarComponente(menuParrillero);
    eventoCompleto.agregarComponente(packFiesta);
    eventoCompleto.agregarComponente(brindis);

    assertEquals(518800, eventoCompleto.getPrecio());
  }

  @Test
  void dadoUnEventoCompletoAnidado_alConsultarCantidadServicios_retorna6() {
    // Evento Completo contiene 6 servicios:
    // Menú Parrillero: 3 (Mesa entradas, Parrillada, Mesa dulce)
    // Pack Fiesta: 2 (Barra tragos, DJ sonido)
    // Brindis: 1
    // Total: 6

    Menu menuParrillero = new Menu("Menú Parrillero", 10);
    menuParrillero.agregarComponente(mesaEntradas);
    menuParrillero.agregarComponente(parrilladaCompleta);
    menuParrillero.agregarComponente(mesaDulce);

    Menu packFiesta = new Menu("Pack Fiesta", 5);
    packFiesta.agregarComponente(barraDeTrasos);
    packFiesta.agregarComponente(djySonido);

    Menu eventoCompleto = new Menu("Evento Completo", 20);
    eventoCompleto.agregarComponente(menuParrillero);
    eventoCompleto.agregarComponente(packFiesta);
    eventoCompleto.agregarComponente(brindis);

    assertEquals(6, eventoCompleto.cantidadServicios());
  }

  @Test
  void dadoUnEventoCompletoAnidadoConDJ_alBuscarDJ_retornaTrue() {
    Menu menuParrillero = new Menu("Menú Parrillero", 10);
    menuParrillero.agregarComponente(mesaEntradas);
    menuParrillero.agregarComponente(parrilladaCompleta);
    menuParrillero.agregarComponente(mesaDulce);

    Menu packFiesta = new Menu("Pack Fiesta", 5);
    packFiesta.agregarComponente(barraDeTrasos);
    packFiesta.agregarComponente(djySonido);

    Menu eventoCompleto = new Menu("Evento Completo", 20);
    eventoCompleto.agregarComponente(menuParrillero);
    eventoCompleto.agregarComponente(packFiesta);
    eventoCompleto.agregarComponente(brindis);

    assertTrue(eventoCompleto.incluye("DJ y sonido"));
  }

  @Test
  void dadoUnEventoCompletoAnidadoSinFotografo_alBuscarFotografo_retornaFalse() {
    Menu menuParrillero = new Menu("Menú Parrillero", 10);
    menuParrillero.agregarComponente(mesaEntradas);
    menuParrillero.agregarComponente(parrilladaCompleta);
    menuParrillero.agregarComponente(mesaDulce);

    Menu packFiesta = new Menu("Pack Fiesta", 5);
    packFiesta.agregarComponente(barraDeTrasos);
    packFiesta.agregarComponente(djySonido);

    Menu eventoCompleto = new Menu("Evento Completo", 20);
    eventoCompleto.agregarComponente(menuParrillero);
    eventoCompleto.agregarComponente(packFiesta);
    eventoCompleto.agregarComponente(brindis);

    assertFalse(eventoCompleto.incluye("Fotógrafo"));
  }

  @Test
  void dadoUnMenuConComponente_alQuitarlo_retornaPrecio0() {
    Menu menu = new Menu("Menu prueba", 0);
    menu.agregarComponente(mesaEntradas); // 80.000
    assertEquals(80000, menu.getPrecio());

    menu.quitarComponente(mesaEntradas);
    assertEquals(0, menu.getPrecio());
  }
}
