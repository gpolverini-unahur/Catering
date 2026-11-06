package ar.edu.unahur.obj2.catering.gestion;

import ar.edu.unahur.obj2.catering.propuesta.Menu;
import ar.edu.unahur.obj2.catering.propuesta.Propuesta;
import ar.edu.unahur.obj2.catering.gestion.exception.PropuestaNoIncluidaException;

public class QuitarPropuestaDeMenu implements Comando {
  private Menu menu;
  private Propuesta propuesta;

  public QuitarPropuestaDeMenu(Menu menu, Propuesta propuesta) {
    this.menu = menu;
    this.propuesta = propuesta;
  }

  @Override
  public void ejecutar() {
    if (!menu.contieneComponenteDirecto(propuesta)) {
      throw new PropuestaNoIncluidaException(
          "La propuesta '" + propuesta.getDescripcion() + 
          "' no es componente directo del menú '" + menu.getDescripcion() + "'"
      );
    }
    menu.quitarComponente(propuesta);
  }

  @Override
  public void deshacer() {
    menu.agregarComponente(propuesta);
  }
}
