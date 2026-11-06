package ar.edu.unahur.obj2.catering.gestion;

import ar.edu.unahur.obj2.catering.propuesta.Menu;
import ar.edu.unahur.obj2.catering.propuesta.Propuesta;

public class AgregarPropuestaAMenu implements Comando {
  private Menu menu;
  private Propuesta propuesta;

  public AgregarPropuestaAMenu(Menu menu, Propuesta propuesta) {
    this.menu = menu;
    this.propuesta = propuesta;
  }

  @Override
  public void ejecutar() {
    menu.agregarComponente(propuesta);
  }

  @Override
  public void deshacer() {
    menu.quitarComponente(propuesta);
  }
}
