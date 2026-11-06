package ar.edu.unahur.obj2.catering.gestion;

public interface Comando {
  void ejecutar() throws Exception;
  void deshacer() throws Exception;
}
