package ar.edu.unahur.obj2.catering.adicional;

import ar.edu.unahur.obj2.catering.propuesta.Propuesta;

public class VajillaPremium extends PropuestaDecorador {
  private static final double COSTO = 12000;

  public VajillaPremium(Propuesta propuestaEnvuelta) {
    super(propuestaEnvuelta);
  }

  @Override
  public double getPrecio() {
    return propuestaEnvuelta.getPrecio() + COSTO;
  }

  @Override
  protected String nombreDelAdicional() {
    return "Vajilla premium";
  }
}
