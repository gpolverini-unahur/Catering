package ar.edu.unahur.obj2.catering.gestion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unahur.obj2.catering.propuesta.Menu;
import ar.edu.unahur.obj2.catering.propuesta.Servicio;
import ar.edu.unahur.obj2.catering.contrato.Contrato;
import ar.edu.unahur.obj2.catering.gestion.exception.NadaParaRevertirException;
import ar.edu.unahur.obj2.catering.gestion.exception.PropuestaNoIncluidaException;

import static org.junit.jupiter.api.Assertions.*;

class GestorTest {

  private Servicio fotografo;
  private Servicio mesaEntradas;
  private Servicio parrilladaCompleta;
  private Servicio mesaDulce;
  private Servicio barraDeTrasos;
  private Servicio djySonido;
  private Servicio brindis;
  private Menu menuParrillero;
  private Menu packFiesta;
  private Gestor gestor;

  @BeforeEach
  void setUp() {
    fotografo = new Servicio("Fotógrafo", 100000);
    mesaEntradas = new Servicio("Mesa de entradas", 80000);
    parrilladaCompleta = new Servicio("Parrillada completa", 240000);
    mesaDulce = new Servicio("Mesa dulce", 60000);
    barraDeTrasos = new Servicio("Barra de tragos", 150000);
    djySonido = new Servicio("DJ y sonido", 120000);
    brindis = new Servicio("Brindis de bienvenida", 50000);

    menuParrillero = new Menu("Menú Parrillero", 10);
    menuParrillero.agregarComponente(mesaEntradas);
    menuParrillero.agregarComponente(parrilladaCompleta);
    menuParrillero.agregarComponente(mesaDulce);
    // Precio: 342.000

    packFiesta = new Menu("Pack Fiesta", 5);
    packFiesta.agregarComponente(barraDeTrasos);
    packFiesta.agregarComponente(djySonido);
    // Precio: 256.500

    gestor = new Gestor();
  }

  @Test
  void dadoUnComandoAplicado_alConsultarHistorial_quedaRegistrado() throws Exception {
    assertEquals(0, gestor.tamanioHistorial());
    
    gestor.aplicar(new AgregarPropuestaAMenu(menuParrillero, fotografo));
    
    assertEquals(1, gestor.tamanioHistorial());
  }

  @Test
  void dadoUnComandoEnHistorial_alRevertir_seDeshacelComando() throws Exception {
    double precioAntesDeAgregar = menuParrillero.getPrecio();

    gestor.aplicar(new AgregarPropuestaAMenu(menuParrillero, fotografo));
    assertEquals(432000, menuParrillero.getPrecio()); // Se agregó Fotógrafo

    gestor.revertir();
    assertEquals(precioAntesDeAgregar, menuParrillero.getPrecio());
    assertEquals(0, gestor.tamanioHistorial());
  }

  @Test
  void dadoUnGestorSinHistorial_alRevertir_lanzaExcepcion() {
    NadaParaRevertirException excepcion = assertThrows(
        NadaParaRevertirException.class,
        () -> gestor.revertir()
    );
    assertTrue(excepcion.getMessage().contains("No hay modificaciones para revertir"));
  }

  @Test
  void dadoUnGestorConTresComandos_alRevertirTodos_retornaAlEstadoInicial() throws Exception {
    // Paso 0: Catálogo inicial
    assertEquals(342000, menuParrillero.getPrecio());

    // Paso 1: Agregar Fotógrafo a Menú Parrillero
    gestor.aplicar(new AgregarPropuestaAMenu(menuParrillero, fotografo));
    assertEquals(432000, menuParrillero.getPrecio());

    // Paso 2: Actualizar precio de Parrillada completa a 260.000
    // (80.000 + 260.000 + 60.000 + 100.000) * 0.9 = 500.000 * 0.9 = 450.000
    gestor.aplicar(new ActualizarPrecioDeServicio(parrilladaCompleta, 260000));
    assertEquals(450000, menuParrillero.getPrecio());

    // Paso 3: Quitar Mesa dulce de Menú Parrillero
    // (80.000 + 260.000 + 100.000) * 0.9 = 440.000 * 0.9 = 396.000
    gestor.aplicar(new QuitarPropuestaDeMenu(menuParrillero, mesaDulce));
    assertEquals(396000, menuParrillero.getPrecio());

    // Paso 4: Revertir (Quitar Mesa dulce)
    gestor.revertir();
    assertEquals(450000, menuParrillero.getPrecio());

    // Paso 5: Revertir (Actualizar precio de Parrillada)
    gestor.revertir();
    assertEquals(432000, menuParrillero.getPrecio());

    // Paso 6: Revertir (Agregar Fotógrafo)
    gestor.revertir();
    assertEquals(342000, menuParrillero.getPrecio());

    // Paso 7: Revertir (Historial vacío, debe fallar)
    NadaParaRevertirException excepcion = assertThrows(
        NadaParaRevertirException.class,
        () -> gestor.revertir()
    );
    assertTrue(excepcion.getMessage().contains("No hay modificaciones"));
  }

  @Test
  void dadoUnLoteDosActualizacionesDelMismoServicio_alAplicarYRevertir_funcionaCorrectamente() throws Exception {
    // Paso a: Aplicar las dos actualizaciones
    List<Comando> lote = new ArrayList<>();
    lote.add(new ActualizarPrecioDeServicio(parrilladaCompleta, 260000));
    lote.add(new ActualizarPrecioDeServicio(parrilladaCompleta, 300000));

    gestor.aplicarLote(lote);

    assertEquals(300000, parrilladaCompleta.getPrecio());
    assertEquals(2, gestor.tamanioHistorial());

    // Paso b: Revertir (debe revertir la segunda actualización)
    gestor.revertir();
    assertEquals(260000, parrilladaCompleta.getPrecio());

    // Paso c: Revertir (debe revertir la primera actualización)
    gestor.revertir();
    assertEquals(240000, parrilladaCompleta.getPrecio());
  }

  @Test
  void dadoUnLoteSatisfactorio_alAplicar_seRegistranTodosEnHistorial() throws Exception {
    List<Comando> lote = new ArrayList<>();
    lote.add(new AgregarPropuestaAMenu(menuParrillero, fotografo));
    lote.add(new ActualizarPrecioDeServicio(parrilladaCompleta, 260000));
    lote.add(new QuitarPropuestaDeMenu(menuParrillero, mesaDulce));

    gestor.aplicarLote(lote);

    assertEquals(396000, menuParrillero.getPrecio());
    assertEquals(3, gestor.tamanioHistorial());
  }

  @Test
  void dadoUnLoteConFalla_alAplicar_revierteAllComandosAplicados() throws Exception {
    // Primero agregamos Fotógrafo de inmediato
    gestor.aplicar(new AgregarPropuestaAMenu(menuParrillero, fotografo));
    assertEquals(432000, menuParrillero.getPrecio());

    // Ahora intentamos un lote que fallará
    List<Comando> lote = new ArrayList<>();
    lote.add(new ActualizarPrecioDeServicio(parrilladaCompleta, 260000));
    lote.add(new QuitarPropuestaDeMenu(menuParrillero, barraDeTrasos)); // FALLA: no está en Parrillero

    PropuestaNoIncluidaException excepcion = assertThrows(
        PropuestaNoIncluidaException.class,
        () -> gestor.aplicarLote(lote)
    );
    assertTrue(excepcion.getMessage().contains("no es componente directo"));

    // El catálogo debe quedar como estaba ANTES del lote
    assertEquals(432000, menuParrillero.getPrecio()); // Fotógrafo sigue, Parrillada en 240.000
    assertEquals(240000, parrilladaCompleta.getPrecio()); // REVERTIDA la actualización
    assertEquals(1, gestor.tamanioHistorial()); // Solo está el comando pre-aplicado
  }

  @Test
  void dadoUnLoteConTresComandos_alRevertirUnoAUno_esEquivalenteASecuenciaInmediata() throws Exception {
    // Crear 3 modificaciones
    List<Comando> lote = new ArrayList<>();
    lote.add(new AgregarPropuestaAMenu(menuParrillero, fotografo));
    lote.add(new ActualizarPrecioDeServicio(parrilladaCompleta, 260000));
    lote.add(new QuitarPropuestaDeMenu(menuParrillero, mesaDulce));

    // Paso a: Aplicar el lote
    gestor.aplicarLote(lote);
    assertEquals(396000, menuParrillero.getPrecio());

    // Paso b: Revertir
    gestor.revertir();
    assertEquals(450000, menuParrillero.getPrecio());

    // Paso c: Revertir dos veces más
    gestor.revertir();
    gestor.revertir();
    assertEquals(342000, menuParrillero.getPrecio());

    // Paso d: Revertir (debe fallar)
    NadaParaRevertirException excepcion = assertThrows(
        NadaParaRevertirException.class,
        () -> gestor.revertir()
    );
    assertTrue(excepcion.getMessage().contains("No hay modificaciones"));
  }

  @Test
  void dadoUnContratoCreado_cuandoCambiaElCatalogo_elContratoMantieneSuImporte() throws Exception {
    // Crear contrato cuando Parrillada cuesta 240.000
    Contrato contrato = new Contrato("Lucía", menuParrillero);
    assertEquals(342000, contrato.getImporte());

    // Actualizar Parrillada a 260.000
    gestor.aplicar(new ActualizarPrecioDeServicio(parrilladaCompleta, 260000));

    // El catálogo cambió
    assertEquals(360000, menuParrillero.getPrecio());

    // Pero el contrato sigue con su importe original
    assertEquals(342000, contrato.getImporte());
  }
}
