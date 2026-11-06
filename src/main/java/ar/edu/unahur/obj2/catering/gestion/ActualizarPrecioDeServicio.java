package ar.edu.unahur.obj2.catering.gestion;

import ar.edu.unahur.obj2.catering.propuesta.Servicio;
import ar.edu.unahur.obj2.catering.propuesta.exception.PrecioNegativoException;

public class ActualizarPrecioDeServicio implements Comando {
  private Servicio servicio;
  private double precioNuevo;
  private double precioAnterior;
  private boolean ejecutado;

  public ActualizarPrecioDeServicio(Servicio servicio, double precioNuevo) {
    this.servicio = servicio;
    this.precioNuevo = precioNuevo;
    this.precioAnterior = -1;
    this.ejecutado = false;
  }

  @Override
  public void ejecutar() {
    if (precioNuevo < 0) {
      throw new PrecioNegativoException(
          "No se puede actualizar el precio a un valor negativo: " + precioNuevo
      );
    }
    
    if (!ejecutado) {
      this.precioAnterior = servicio.getPrecio();
      this.ejecutado = true;
    }
    
    servicio.actualizarPrecioInterno(precioNuevo);
  }

  @Override
  public void deshacer() {
    if (!ejecutado) {
      throw new RuntimeException("No se puede deshacer un comando que no se ejecutó");
    }
    servicio.actualizarPrecioInterno(precioAnterior);
  }
}
