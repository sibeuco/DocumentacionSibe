package co.edu.uco.sibe.dominio.puerto.servicio;

import co.edu.uco.sibe.dominio.dto.ContextoUsuarioAutenticado;

public interface ContextoUsuarioProveedorServicio {
    ContextoUsuarioAutenticado obtenerContextoActual();
}
