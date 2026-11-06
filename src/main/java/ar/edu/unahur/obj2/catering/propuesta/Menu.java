package ar.edu.unahur.obj2.catering.propuesta;

import java.util.ArrayList;
import java.util.List;
import ar.edu.unahur.obj2.catering.propuesta.exception.DescuentoInvalidoException;

public class Menu implements Propuesta {
  private String nombre;
  private double descuentoPorcentaje;
  private List<Propuesta> componentes;

  public Menu(String nombre, double descuentoPorcentaje) {
    if (descuentoPorcentaje < 0 || descuentoPorcentaje > 100) {
      throw new DescuentoInvalidoException(
          "El descuento debe estar entre 0 y 100. Recibido: " + descuentoPorcentaje
      );
    }
    this.nombre = nombre;
    this.descuentoPorcentaje = descuentoPorcentaje;
    this.componentes = new ArrayList<>();
  }

  public void agregarComponente(Propuesta propuesta) {
    componentes.add(propuesta);
  }

  public void quitarComponente(Propuesta propuesta) {
    componentes.remove(propuesta);
  }

  public boolean contieneComponenteDirecto(Propuesta propuesta) {
    return componentes.contains(propuesta);
  }

  @Override
  public double getPrecio() {
    double precioSinDescuento = componentes.stream()
        .mapToDouble(Propuesta::getPrecio)
        .sum();
    
    double descuentoMonto = precioSinDescuento * (descuentoPorcentaje / 100.0);
    
    return precioSinDescuento - descuentoMonto;
  }

  @Override
  public String getDescripcion() {
    return nombre;
  }

  @Override
  public int cantidadServicios() {
    return componentes.stream()
        .mapToInt(Propuesta::cantidadServicios)
        .sum();
  }

  @Override
  public boolean incluye(String nombreServicio) {
    return componentes.stream()
        .anyMatch(propuesta -> propuesta.incluye(nombreServicio));
  }

  @Override
  public String toString() {
    return nombre;
  }
}
