package ar.edu.unahur.obj2.catering.contrato;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.catering.propuesta.Menu;
import ar.edu.unahur.obj2.catering.propuesta.Propuesta;
import ar.edu.unahur.obj2.catering.propuesta.Servicio;
import ar.edu.unahur.obj2.catering.adicional.MozosAdicionales;

import static org.junit.jupiter.api.Assertions.*;

class ContratoTest {

  private Servicio mesaEntradas;
  private Servicio parrilladaCompleta;
  private Servicio mesaDulce;
  private Menu menuParrillero;

  @BeforeEach
  void setUp() {
    mesaEntradas = new Servicio("Mesa de entradas", 80000);
    parrilladaCompleta = new Servicio("Parrillada completa", 240000);
    mesaDulce = new Servicio("Mesa dulce", 60000);

    menuParrillero = new Menu("Menú Parrillero", 10);
    menuParrillero.agregarComponente(mesaEntradas);
    menuParrillero.agregarComponente(parrilladaCompleta);
    menuParrillero.agregarComponente(mesaDulce);
    // Precio: 342.000
  }

  @Test
  void dadoUnContrato_alConsultarImporte_guardaElPrecioDelMenu() {
    Contrato contrato = new Contrato("Lucía", menuParrillero);
    assertEquals(342000, contrato.getImporte());
  }

  @Test
  void dadoUnContrato_alConsultarNombreCliente_retornaElNombreAsignado() {
    Contrato contrato = new Contrato("Lucía", menuParrillero);
    assertEquals("Lucía", contrato.getNombreCliente());
  }

  @Test
  void dadoUnContrato_alConsultarDescripcion_retornaLaDelMenu() {
    Contrato contrato = new Contrato("Lucía", menuParrillero);
    assertEquals("Menú Parrillero", contrato.getDescripcion());
  }

  @Test
  void dadoUnContratoConMenuConAdicionales_alConsultarImporte_guardaElPrecioConAdicionales() {
    // Menu Parrillero: 342.000 + Mozos: 18.000 = 360.000
    Propuesta propuestaContratada = new MozosAdicionales(menuParrillero);
    Contrato contrato = new Contrato("Lucía", propuestaContratada);
    
    assertEquals(360000, contrato.getImporte());
  }

  @Test
  void dadoUnContratoConMenuConAdicionales_alConsultarDescripcion_retornaLaDelMenuConAdicionales() {
    // Menu Parrillero + Mozos
    Propuesta propuestaContratada = new MozosAdicionales(menuParrillero);
    Contrato contrato = new Contrato("Lucía", propuestaContratada);
    
    assertEquals("Menú Parrillero + Mozos adicionales", contrato.getDescripcion());
  }

  @Test
  void dadoUnContratoYCambiosAlMenu_alConsultarImporte_mantienePrecioOriginal() {
    // Crear contrato con Menu Parrillero a 342.000
    Contrato contrato = new Contrato("Lucía", menuParrillero);
    assertEquals(342000, contrato.getImporte());
    
    // Cambiar el menú (agregar más componentes)
    mesaEntradas = new Servicio("Mesa de entradas", 100000); // cambio de precio
    Menu nuevoMenuParrillero = new Menu("Menú Parrillero", 10);
    nuevoMenuParrillero.agregarComponente(mesaEntradas);
    nuevoMenuParrillero.agregarComponente(parrilladaCompleta);
    nuevoMenuParrillero.agregarComponente(mesaDulce);
    
    // El contrato sigue mostrando 342.000 (el valor al momento de su creación)
    assertEquals(342000, contrato.getImporte());
  }
}
