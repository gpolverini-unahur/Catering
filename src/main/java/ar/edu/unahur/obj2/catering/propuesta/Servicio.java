package ar.edu.unahur.obj2.catering;

public class Servicio implements Propuesta {
  private String nombre;
  private double precio;

  public Servicio(String nombre, double precio) {
    if (precio < 0) {
      throw new PrecioNegativoException(
          "El precio de '" + nombre + "' no puede ser negativo. Recibido: " + precio
      );
    }
    this.nombre = nombre;
    this.precio = precio;
  }

  @Override
  public double getPrecio() {
    return precio;
  }

  protected void actualizarPrecioInterno(double nuevoPrecio) {
    if (nuevoPrecio < 0) {
      throw new PrecioNegativoException(
          "El precio no puede ser negativo. Recibido: " + nuevoPrecio
      );
    }
    this.precio = nuevoPrecio;
  }

  @Override
  public String getDescripcion() {
    return nombre;
  }

  @Override
  public int cantidadServicios() {
    return 1;
  }

  @Override
  public boolean incluye(String nombreServicio) {
    return this.nombre.equals(nombreServicio);
  }

  @Override
  public String toString() {
    return nombre;
  }
}
