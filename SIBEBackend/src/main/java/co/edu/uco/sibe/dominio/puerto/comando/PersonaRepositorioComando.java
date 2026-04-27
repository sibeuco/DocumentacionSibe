package co.edu.uco.sibe.dominio.puerto.comando;

import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.dominio.modelo.Usuario;
import java.time.LocalDateTime;
import java.util.UUID;

public interface PersonaRepositorioComando {
    UUID agregarNuevoUsuario(Usuario usuario, Persona persona, String contrasenaEncriptada);

    UUID reactivarUsuario(String correo, Usuario usuario, Persona persona, String claveEncriptada);

    UUID modificarUsuario(Usuario usuario, Persona persona);

    UUID modificarClave(String nuevaContrasena, UUID identificador);

    void eliminarUsuario(UUID identificador);

    UUID crearPeticionRecuperacionClave(String codigoCifrado, String correo, LocalDateTime fecha);

    void eliminarPeticionRecuperacionClaveConCorreo(String correo);

    UUID modificarClaveConCorreo(String claveCifrada, String correo);
}