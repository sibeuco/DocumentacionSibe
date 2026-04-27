package co.edu.uco.sibe.dominio.puerto.consulta;

import co.edu.uco.sibe.dominio.dto.PersonaDTO;
import co.edu.uco.sibe.dominio.dto.UsuarioDTO;
import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.dominio.modelo.Usuario;
import org.springframework.data.domain.Page;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PersonaRepositorioConsulta {
    Persona consultarPersonaPorIdentificador(UUID identificador);

    PersonaDTO consultarPersonaPorCorreoDTO(String correo);

    Persona consultarPersonaPorCorreo(String correo);

    Persona consultarPersonaPorDocumento(String documento);

    UsuarioDTO consultarUsuarioPorIdentificadorDTO(UUID identificador);

    Usuario consultarUsuarioPorIdentificador(UUID identificador);

    UsuarioDTO consultarUsuarioPorCorreoDTO(String correo);

    Usuario consultarUsuarioPorCorreo(String correo);

    Usuario consultarUsuarioInactivoPorCorreo(String correo);

    List<UsuarioDTO> consultarUsuariosDTO();

    Page<UsuarioDTO> consultarUsuariosDTOPaginado(String tipoUsuario, int pagina, int tamano, boolean excluir);

    boolean hayDatos();

    String consultarCodigoConCorreo(String correo);

    LocalDateTime consultarFechaPeticionRecuperacionClaveConCorreo(String correo);

    String consultarClaveConCorreo(String correo);

    long contarUsuariosActivosPorTipoUsuario(String codigoTipoUsuario);
}