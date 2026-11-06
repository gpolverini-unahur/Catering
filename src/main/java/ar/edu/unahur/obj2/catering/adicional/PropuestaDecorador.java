package ar.edu.unahur.obj2.catering.adicional;

import ar.edu.unahur.obj2.catering.propuesta.Propuesta;

public abstract class PropuestaDecorador implements Propuesta {
  protected Propuesta propuestaEnvuelta;

  public PropuestaDecorador(Propuesta propuestaEnvuelta) {
    this.propuestaEnvuelta = propuestaEnvuelta;
  }

  @Override
  public abstract double getPrecio();

  @Override
  public String getDescripcion() {
    return propuestaEnvuelta.getDescripcion() + " + " + nombreDelAdicional();
  }

  protected abstract String nombreDelAdicional();

  @Override
  public int cantidadServicios() {
    return propuestaEnvuelta.cantidadServicios();
  }

  @Override
  public boolean incluye(String nombreServicio) {
    return propuestaEnvuelta.incluye(nombreServicio);
  }
}
