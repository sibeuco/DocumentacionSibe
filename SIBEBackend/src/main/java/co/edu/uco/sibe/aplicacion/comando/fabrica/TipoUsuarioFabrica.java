package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.TipoUsuarioComando;
import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoUsuarioRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class TipoUsuarioFabrica {
    private final TipoUsuarioRepositorioConsulta tipoUsuarioRepositorioConsulta;

    public TipoUsuario construir(TipoUsuarioComando comando) {
        return TipoUsuario.construir(
                generar(uuid -> !esNulo(tipoUsuarioRepositorioConsulta.consultarPorIdentificador(uuid))),
                comando.getCodigo(),
                comando.getNombre(),
                comando.isCrear(),
                comando.isModificar(),
                comando.isEliminar(),
                comando.isConsultar()
        );
    }
}