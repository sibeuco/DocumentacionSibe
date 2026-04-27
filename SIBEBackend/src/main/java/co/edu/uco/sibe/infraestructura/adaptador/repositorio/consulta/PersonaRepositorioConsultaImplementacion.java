package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.dto.PersonaDTO;
import co.edu.uco.sibe.dominio.dto.UsuarioDTO;
import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.dominio.modelo.Usuario;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.infraestructura.adaptador.dao.IdentificacionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.PersonaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.PeticionRecuperacionClaveDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.UsuarioDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.PersonaMapeador;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.PeticionRecuperacionClaveMapeador;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.UsuarioMapeador;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.CERO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.esNumeroMayor;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class PersonaRepositorioConsultaImplementacion implements PersonaRepositorioConsulta {
    private final PersonaDAO personaDAO;
    private final PersonaMapeador personaMapeador;
    private final UsuarioDAO usuarioDAO;
    private final UsuarioMapeador usuarioMapeador;
    private final IdentificacionDAO identificacionDAO;
    private final PeticionRecuperacionClaveDAO peticionRecuperacionClaveDAO;
    private final PeticionRecuperacionClaveMapeador peticionRecuperacionClaveMapeador;

    @Override
    public Persona consultarPersonaPorIdentificador(UUID identificador) {
        var entidad = this.personaDAO.findById(identificador).orElse(null);

        if (esNulo(entidad)){
            return null;
        }

        var usuarioEntidad = this.usuarioDAO.findByCorreo(entidad.getCorreo());

        if(!usuarioEntidad.isEstaActivo()) {
            return null;
        }

        return this.personaMapeador.construirModelo(entidad);
    }

    @Override
    public PersonaDTO consultarPersonaPorCorreoDTO(String correo) {
        var entidad = this.personaDAO.findByCorreo(correo);

        if (esNulo(entidad)){
            return null;
        }

        var usuarioEntidad = this.usuarioDAO.findByCorreo(correo);

        if(!usuarioEntidad.isEstaActivo()) {
            return null;
        }

        return this.personaMapeador.construirDTO(entidad);
    }

    @Override
    public Persona consultarPersonaPorCorreo(String correo) {
        var entidad = this.personaDAO.findByCorreo(correo);

        if (esNulo(entidad)){
            return null;
        }

        var usuarioEntidad = this.usuarioDAO.findByCorreo(correo);

        if(!usuarioEntidad.isEstaActivo()) {
            return null;
        }

        return this.personaMapeador.construirModelo(entidad);
    }

    @Override
    public Persona consultarPersonaPorDocumento(String documento) {
        var identificacionEntidad = this.identificacionDAO.findFirstByNumeroIdentificacion(documento);
        var entidad = this.personaDAO.findByIdentificacion(identificacionEntidad);

        if (esNulo(entidad)){
            return null;
        }

        var usuarioEntidad = this.usuarioDAO.findByCorreo(entidad.getCorreo());

        if(!usuarioEntidad.isEstaActivo()) {
            return null;
        }

        return this.personaMapeador.construirModelo(entidad);
    }

    @Override
    public UsuarioDTO consultarUsuarioPorIdentificadorDTO(UUID identificador) {
        var entidad = this.personaDAO.findById(identificador).orElse(null);

        if (esNulo(entidad)){
            return null;
        }

        var usuarioEntidad = this.usuarioDAO.findByCorreo(entidad.getCorreo());

        if(!usuarioEntidad.isEstaActivo()) {
            return null;
        }

        return this.usuarioMapeador.construirDTO(entidad);
    }

    @Override
    public Usuario consultarUsuarioPorIdentificador(UUID identificador) {
        var entidad = this.usuarioDAO.findById(identificador).orElse(null);

        if (esNulo(entidad) || !entidad.isEstaActivo()){
            return null;
        }

        return this.usuarioMapeador.construirModelo(entidad);
    }

    @Override
    public UsuarioDTO consultarUsuarioPorCorreoDTO(String correo) {
        var entidad = this.usuarioDAO.findByCorreo(correo);
        var personaEntidad = this.personaDAO.findByCorreo(correo);

        if (esNulo(entidad) || !entidad.isEstaActivo()){
            return null;
        }

        return this.usuarioMapeador.construirDTO(personaEntidad);
    }

    @Override
    public Usuario consultarUsuarioPorCorreo(String correo) {
        var entidad = this.usuarioDAO.findByCorreo(correo);

        if (esNulo(entidad) || !entidad.isEstaActivo()) {
            return null;
        }

        return this.usuarioMapeador.construirModelo(entidad);
    }

    @Override
    public Usuario consultarUsuarioInactivoPorCorreo(String correo) {
        var entidad = this.usuarioDAO.findByCorreo(correo);

        if (esNulo(entidad) || entidad.isEstaActivo()) {
            return null;
        }

        return this.usuarioMapeador.construirModelo(entidad);
    }

    @Override
    public List<UsuarioDTO> consultarUsuariosDTO() {
        var entidades = this.personaDAO.findAll();

        return this.usuarioMapeador.construirDTOs(entidades).stream().filter(UsuarioDTO::getEstaActivo).toList();
    }

    @Override
    public Page<UsuarioDTO> consultarUsuariosDTOPaginado(String tipoUsuario, int pagina, int tamano, boolean excluir) {
        var entidades = excluir
                ? this.personaDAO.findByTipoUsuarioNot(tipoUsuario, PageRequest.of(pagina, tamano, Sort.by(Sort.Direction.ASC, "nombres")))
                : this.personaDAO.findByTipoUsuario(tipoUsuario, PageRequest.of(pagina, tamano, Sort.by(Sort.Direction.ASC, "nombres")));

        return this.usuarioMapeador.construirDTOsPaginado(entidades);
    }

    @Override
    public boolean hayDatos() {
        var cantidad = usuarioDAO.count();

        return esNumeroMayor(cantidad, CERO);
    }

    @Override
    public String consultarCodigoConCorreo(String correo) {
        var entidad = this.peticionRecuperacionClaveDAO.findByCorreo(correo);

        if (esNulo(entidad)){
            return null;
        }

        return entidad.getCodigo();
    }

    @Override
    public LocalDateTime consultarFechaPeticionRecuperacionClaveConCorreo(String correo) {
        var entidad = this.peticionRecuperacionClaveDAO.findByCorreo(correo);

        if (esNulo(entidad)){
            return null;
        }

        return entidad.getFecha();
    }

    @Override
    public String consultarClaveConCorreo(String correo) {
        var entidad = this.usuarioDAO.findByCorreo(correo);

        if (esNulo(entidad)){
            return null;
        }

        return entidad.getClave();
    }

    @Override
    public long contarUsuariosActivosPorTipoUsuario(String codigoTipoUsuario) {
        return this.personaDAO.countActivosPorTipoUsuario(codigoTipoUsuario);
    }
}