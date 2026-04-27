package co.edu.uco.sibe.dominio.puerto.servicio;

public interface EncriptarClaveServicio {
    String ejecutar(String contrasena);

    boolean existe(String contrasena, String contrasenaEncriptada);
}