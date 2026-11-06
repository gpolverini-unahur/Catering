package ar.edu.unahur.obj2.catering.propuesta;

public interface Propuesta {
  double getPrecio();
  String getDescripcion();
  int cantidadServicios();
  boolean incluye(String nombreServicio);
}
