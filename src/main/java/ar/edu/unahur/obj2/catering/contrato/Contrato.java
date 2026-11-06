package ar.edu.unahur.obj2.catering.contrato;

import ar.edu.unahur.obj2.catering.propuesta.Propuesta;

public class Contrato {
  private String nombreCliente;
  private double importe;
  private String descripcion;

  public Contrato(String nombreCliente, Propuesta propuesta) {
    this.nombreCliente = nombreCliente;
    this.importe = propuesta.getPrecio();
    this.descripcion = propuesta.getDescripcion();
  }

  public String getNombreCliente() {
    return nombreCliente;
  }

  public double getImporte() {
    return importe;
  }

  public String getDescripcion() {
    return descripcion;
  }
}
