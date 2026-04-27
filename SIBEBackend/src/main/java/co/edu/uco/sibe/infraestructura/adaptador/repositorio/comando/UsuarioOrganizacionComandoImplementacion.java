package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.puerto.comando.UsuarioOrganizacionComando;
import co.edu.uco.sibe.infraestructura.adaptador.dao.*;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.UsuarioOrganizacionMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class UsuarioOrganizacionComandoImplementacion implements UsuarioOrganizacionComando {
    private final UsuarioDAO usuarioDAO;
    private final PersonaDAO personaDAO;
    private final DireccionDAO direccionDAO;
    private final AreaDAO areaDAO;
    private final SubareaDAO subareaDAO;
    private final UsuarioOrganizacionDAO usuarioOrganizacionDAO;
    private final UsuarioOrganizacionMapeador usuarioOrganizacionMapeador;

    @Override
    public UUID vincularUsuarioConDireccion(UUID usuario, UUID direccion) {
        var usuarioEntidad = usuarioDAO.findById(usuario).orElse(null);
        var direccionEntidad = direccionDAO.findById(direccion).orElse(null);

        assert !esNulo(usuarioEntidad) && !esNulo(direccionEntidad);
        var entidad = usuarioOrganizacionMapeador.construirEntidadVinculadaConDireccion(usuarioEntidad, direccionEntidad);

        return this.usuarioOrganizacionDAO.save(entidad).getIdentificador();
    }

    @Override
    public UUID cambiarVinculacionUsuarioConDireccion(UUID persona, UUID direccion) {
        var personaEntidad = personaDAO.findById(persona).orElse(null);
        assert !esNulo(personaEntidad);

        var usuarioEntidad = usuarioDAO.findByCorreo(personaEntidad.getCorreo());
        var direccionEntidad = direccionDAO.findById(direccion).orElse(null);

        assert !esNulo(usuarioEntidad) && !esNulo(direccionEntidad);
        var entidad = usuarioOrganizacionDAO.findByUsuario(usuarioEntidad);

        assert !esNulo(entidad);
        usuarioOrganizacionMapeador.cambiarEntidadVinculadaConDireccion(entidad, direccionEntidad);

        return this.usuarioOrganizacionDAO.save(entidad).getIdentificador();
    }

    @Override
    public UUID vincularUsuarioConArea(UUID usuario, UUID area) {
        var usuarioEntidad = usuarioDAO.findById(usuario).orElse(null);
        var areaEntidad = areaDAO.findById(area).orElse(null);

        assert !esNulo(usuarioEntidad) && !esNulo(areaEntidad);
        var entidad = usuarioOrganizacionMapeador.construirEntidadVinculadaConArea(usuarioEntidad, areaEntidad);

        return this.usuarioOrganizacionDAO.save(entidad).getIdentificador();
    }

    @Override
    public UUID cambiarVinculacionUsuarioConArea(UUID persona, UUID area) {
        var personaEntidad = personaDAO.findById(persona).orElse(null);

        assert !esNulo(personaEntidad);
        var usuarioEntidad = usuarioDAO.findByCorreo(personaEntidad.getCorreo());

        var areaEntidad = areaDAO.findById(area).orElse(null);

        assert !esNulo(usuarioEntidad) && !esNulo(areaEntidad);
        var entidad = usuarioOrganizacionDAO.findByUsuario(usuarioEntidad);

        assert !esNulo(entidad);
        usuarioOrganizacionMapeador.cambiarEntidadVinculadaConArea(entidad, areaEntidad);

        return this.usuarioOrganizacionDAO.save(entidad).getIdentificador();
    }

    @Override
    public UUID vincularUsuarioConSubarea(UUID usuario, UUID subarea) {
        var usuarioEntidad = usuarioDAO.findById(usuario).orElse(null);
        var subareaEntidad = subareaDAO.findById(subarea).orElse(null);

        assert !esNulo(usuarioEntidad) && !esNulo(subareaEntidad);
        var entidad = usuarioOrganizacionMapeador.construirEntidadVinculadaConSubarea(usuarioEntidad, subareaEntidad);

        return this.usuarioOrganizacionDAO.save(entidad).getIdentificador();
    }

    @Override
    public UUID cambiarVinculacionUsuarioConSubarea(UUID persona, UUID subarea) {
        var personaEntidad = personaDAO.findById(persona).orElse(null);

        assert !esNulo(personaEntidad);
        var usuarioEntidad = usuarioDAO.findByCorreo(personaEntidad.getCorreo());
        var subareaEntidad = subareaDAO.findById(subarea).orElse(null);

        assert !esNulo(usuarioEntidad) && !esNulo(subareaEntidad);
        var entidad = usuarioOrganizacionDAO.findByUsuario(usuarioEntidad);

        assert !esNulo(entidad);
        usuarioOrganizacionMapeador.cambiarEntidadVinculadaConArea(entidad, subareaEntidad);

        return this.usuarioOrganizacionDAO.save(entidad).getIdentificador();
    }
}