package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.UsuarioComando;
import co.edu.uco.sibe.aplicacion.comando.UsuarioModificacionComando;
import co.edu.uco.sibe.dominio.modelo.Usuario;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoUsuarioRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class UsuarioFabrica {
    private final TipoUsuarioRepositorioConsulta tipoUsuarioRepositorioConsulta;
    private final PersonaRepositorioConsulta personaRepositorioConsulta;

    public Usuario construir(UsuarioComando comando){
        var identificadorUsuario = generar(uuid -> !esNulo(personaRepositorioConsulta.consultarUsuarioPorIdentificador(uuid)));
        var tipoUsuario = tipoUsuarioRepositorioConsulta.consultarPorIdentificador(UtilUUID.textoAUUID(comando.getTipoUsuario()));
        var usuario = Usuario.construir(identificadorUsuario, comando.getCorreo(), comando.getClave(), tipoUsuario, true);

        return usuario;
    }

    public Usuario construirActualizar(UsuarioModificacionComando comando, UUID identificador){
        var tipoUsuario = tipoUsuarioRepositorioConsulta.consultarPorIdentificador(UtilUUID.textoAUUID(comando.getTipoUsuario()));
        var correoActual = personaRepositorioConsulta.consultarPersonaPorIdentificador(identificador).getCorreo();
        var usuarioActual = personaRepositorioConsulta.consultarUsuarioPorCorreo(correoActual);
        var usuario = Usuario.construir(usuarioActual.getIdentificador(), comando.getCorreo(), usuarioActual.getClave(), tipoUsuario, usuarioActual.isEstaActivo());

        return usuario;
    }
}