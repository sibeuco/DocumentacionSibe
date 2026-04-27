package co.edu.uco.sibe.infraestructura.adaptador.servicio;

import co.edu.uco.sibe.dominio.dto.ContextoUsuarioAutenticado;
import co.edu.uco.sibe.dominio.puerto.servicio.ContextoUsuarioProveedorServicio;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ContextoUsuarioProveedorServicioImpl implements ContextoUsuarioProveedorServicio {

    @Override
    public ContextoUsuarioAutenticado obtenerContextoActual() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof ContextoUsuarioAutenticado contexto) {
            return contexto;
        }
        throw new SecurityException("No se encontró contexto de usuario autenticado");
    }
}
