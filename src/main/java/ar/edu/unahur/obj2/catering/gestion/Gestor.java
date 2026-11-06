package ar.edu.unahur.obj2.catering.gestion;

import java.util.ArrayList;
import java.util.List;
import ar.edu.unahur.obj2.catering.gestion.exception.NadaParaRevertirException;

public class Gestor {
  private List<Comando> historial;

  public Gestor() {
    this.historial = new ArrayList<>();
  }

  public void aplicar(Comando comando) throws Exception {
    comando.ejecutar();
    historial.add(comando);
  }

  public void revertir() {
    if (historial.isEmpty()) {
      throw new NadaParaRevertirException("No hay modificaciones para revertir");
    }
    
    int ultimoIndice = historial.size() - 1;
    Comando ultimoComando = historial.get(ultimoIndice);
    
    try {
      ultimoComando.deshacer();
      historial.remove(ultimoIndice);
    } catch (Exception e) {
      throw new RuntimeException("Error al revertir: " + e.getMessage(), e);
    }
  }

  public void aplicarLote(List<Comando> comandos) throws Exception {
    List<Comando> aplicados = new ArrayList<>();

    try {
      for (Comando comando : comandos) {
        comando.ejecutar();
        aplicados.add(comando);
      }

      historial.addAll(aplicados);

    } catch (Exception excepcionLote) {
      deshacerComandosAplicados(aplicados);
      throw excepcionLote;
    }
  }

  private void deshacerComandosAplicados(List<Comando> aplicados) {
    for (int i = aplicados.size() - 1; i >= 0; i--) {
      try {
        aplicados.get(i).deshacer();
      } catch (Exception e) {
        throw new RuntimeException(
            "Error crítico: no se pudo revertir tras fallo de lote. " + 
            "Catálogo puede estar en estado inconsistente.",
            e
        );
      }
    }
  }

  public int tamanioHistorial() {
    return historial.size();
  }
}
