package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.AccionComando;
import co.edu.uco.sibe.dominio.modelo.Accion;
import co.edu.uco.sibe.dominio.puerto.consulta.AccionRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class AccionFabrica {
    private final AccionRepositorioConsulta accionRepositorioConsulta;

    public Accion construir(AccionComando comando){
        return Accion.construir(
                generar(uuid -> !esNulo(accionRepositorioConsulta.consultarPorIdentificador(uuid))),
                comando.getDetalle(),
                comando.getObjetivo()
        );
    }

    public Accion construirActualizar(AccionComando comando, UUID identificador){
        return Accion.construir(
                identificador,
                comando.getDetalle(),
                comando.getObjetivo()
        );
    }
}