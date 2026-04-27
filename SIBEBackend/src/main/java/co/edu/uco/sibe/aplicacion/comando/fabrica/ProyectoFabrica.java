package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.ProyectoComando;
import co.edu.uco.sibe.aplicacion.comando.ProyectoModificacionComando;
import co.edu.uco.sibe.dominio.modelo.Proyecto;
import co.edu.uco.sibe.dominio.puerto.consulta.AccionRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.ProyectoRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class ProyectoFabrica {
    private final AccionRepositorioConsulta accionRepositorioConsulta;
    private final ProyectoRepositorioConsulta proyectoRepositorioConsulta;

    public Proyecto construir(ProyectoComando comando){
        var acciones = this.accionRepositorioConsulta.consultarTodosPorIdentificadores(comando.getAcciones().stream().map(UtilUUID::textoAUUID).toList());

        return Proyecto.construir(
                generar(uuid -> !esNulo(proyectoRepositorioConsulta.consultarPorIdentificador(uuid))),
                comando.getNumeroProyecto(),
                comando.getNombre(),
                comando.getObjetivo(),
                acciones
        );
    }

    public Proyecto construirActualizar(ProyectoModificacionComando comando, UUID parametro){
        var acciones = this.accionRepositorioConsulta.consultarTodosPorIdentificadores(comando.getAcciones().stream().map(UtilUUID::textoAUUID).toList());

        return Proyecto.construir(
                parametro,
                comando.getNumeroProyecto(),
                comando.getNombre(),
                comando.getObjetivo(),
                acciones
        );
    }
}