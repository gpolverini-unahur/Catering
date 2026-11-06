package ar.edu.unahur.obj2.catering.adicional;

import ar.edu.unahur.obj2.catering.propuesta.Propuesta;

public class ServicioNocturno extends PropuestaDecorador {
  private static final double MULTIPLICADOR = 1.20;

  public ServicioNocturno(Propuesta propuestaEnvuelta) {
    super(propuestaEnvuelta);
  }

  @Override
  public double getPrecio() {
    return propuestaEnvuelta.getPrecio() * MULTIPLICADOR;
  }

  @Override
  protected String nombreDelAdicional() {
    return "Servicio nocturno";
  }
}
