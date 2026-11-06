package ar.edu.unahur.obj2.catering.gestion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.catering.propuesta.Menu;
import ar.edu.unahur.obj2.catering.propuesta.Servicio;
import ar.edu.unahur.obj2.catering.gestion.exception.PropuestaNoIncluidaException;
import ar.edu.unahur.obj2.catering.propuesta.exception.PrecioNegativoException;

import static org.junit.jupiter.api.Assertions.*;

class ComandoTest {

  private Servicio fotografo;
  private Servicio mesaEntradas;
  private Servicio parrilladaCompleta;
  private Servicio mesaDulce;
  private Servicio barraDeTrasos;
  private Menu menuParrillero;
  private Gestor gestor;

  @BeforeEach
  void setUp() {
    fotografo = new Servicio("Fotógrafo", 100000);
    mesaEntradas = new Servicio("Mesa de entradas", 80000);
    parrilladaCompleta = new Servicio("Parrillada completa", 240000);
    mesaDulce = new Servicio("Mesa dulce", 60000);
    barraDeTrasos = new Servicio("Barra de tragos", 150000);

    menuParrillero = new Menu("Menú Parrillero", 10);
    menuParrillero.agregarComponente(mesaEntradas);
    menuParrillero.agregarComponente(parrilladaCompleta);
    menuParrillero.agregarComponente(mesaDulce);
    // Precio: 342.000

    gestor = new Gestor();
  }

  @Test
  void dadoUnComandoAgregarPropuesta_alEjecutar_agregarComponenteAlMenu() throws Exception {
    new AgregarPropuestaAMenu(menuParrillero, fotografo).ejecutar();

    // Menu Parrillero ahora tiene 4 componentes: 3 originales + Fotógrafo
    // (80.000 + 240.000 + 60.000 + 100.000) * 0.9 = 480.000 * 0.9 = 432.000
    assertEquals(432000, menuParrillero.getPrecio());
  }

  @Test
  void dadoUnComandoAgregarYRevertir_alDeshacer_retornaAlEstadoAnterior() throws Exception {
    double precioOriginal = menuParrillero.getPrecio();
    
    Comando comando = new AgregarPropuestaAMenu(menuParrillero, fotografo);
    comando.ejecutar();
    assertEquals(432000, menuParrillero.getPrecio());
    
    comando.deshacer();
    assertEquals(precioOriginal, menuParrillero.getPrecio());
  }

  @Test
  void dadoUnComandoQuitarPropuesta_alEjecutar_quitaComponenteDelMenu() throws Exception {
    // Menu Parrillero inicialmente tiene Mesa de entradas, Parrillada, Mesa dulce (342.000)
    // Quitamos Mesa dulce: (80.000 + 240.000) * 0.9 = 320.000 * 0.9 = 288.000
    new QuitarPropuestaDeMenu(menuParrillero, mesaDulce).ejecutar();

    assertEquals(288000, menuParrillero.getPrecio());
  }

  @Test
  void dadoUnComandoQuitarComponenteNoIncluido_alEjecutar_lanzaExcepcion() {
    // Barra de tragos NO está en Menú Parrillero
    assertThrows(
        PropuestaNoIncluidaException.class,
        () -> new QuitarPropuestaDeMenu(menuParrillero, barraDeTrasos).ejecutar()
    );
  }

  @Test
  void dadoUnComandoQuitarYRevertir_alDeshacer_retornaAlEstadoAnterior() throws Exception {
    double precioOriginal = menuParrillero.getPrecio();
    
    Comando comando = new QuitarPropuestaDeMenu(menuParrillero, mesaDulce);
    comando.ejecutar();
    assertEquals(288000, menuParrillero.getPrecio());
    
    comando.deshacer();
    assertEquals(precioOriginal, menuParrillero.getPrecio());
  }

  @Test
  void dadoUnComandoActualizarPrecio_alEjecutar_cambiaElPrecioDelServicio() throws Exception {
    // Parrillada original: 240.000
    // Actualizar a: 260.000
    // Menu Parrillero: (80.000 + 260.000 + 60.000) * 0.9 = 400.000 * 0.9 = 360.000
    double precioOriginal = menuParrillero.getPrecio();
    assertEquals(342000, precioOriginal);

    new ActualizarPrecioDeServicio(parrilladaCompleta, 260000).ejecutar();

    assertEquals(260000, parrilladaCompleta.getPrecio());
    assertEquals(360000, menuParrillero.getPrecio()); // El menú se actualiza dinámicamente
  }

  @Test
  void dadoUnComandoActualizarConPrecioNegativo_alEjecutar_lanzaExcepcion() {
    assertThrows(
        PrecioNegativoException.class,
        () -> new ActualizarPrecioDeServicio(parrilladaCompleta, -100).ejecutar()
    );
  }

  @Test
  void dadoUnComandoActualizarYRevertir_alDeshacer_retornaAlPrecioAnterior() throws Exception {
    double precioOriginal = parrilladaCompleta.getPrecio();
    
    Comando comando = new ActualizarPrecioDeServicio(parrilladaCompleta, 260000);
    comando.ejecutar();
    assertEquals(260000, parrilladaCompleta.getPrecio());
    
    comando.deshacer();
    assertEquals(precioOriginal, parrilladaCompleta.getPrecio());
  }
}
