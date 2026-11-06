package ar.edu.unahur.obj2.catering;

public interface Propuesta {
  double getPrecio();
  String getDescripcion();
  int cantidadServicios();
  boolean incluye(String nombreServicio);
}
