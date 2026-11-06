package ar.edu.unahur.obj2.catering.adicional;

import ar.edu.unahur.obj2.catering.propuesta.Propuesta;

public class MozosAdicionales extends PropuestaDecorador {
  private static final double COSTO = 18000;

  public MozosAdicionales(Propuesta propuestaEnvuelta) {
    super(propuestaEnvuelta);
  }

  @Override
  public double getPrecio() {
    return propuestaEnvuelta.getPrecio() + COSTO;
  }

  @Override
  protected String nombreDelAdicional() {
    return "Mozos adicionales";
  }
}
