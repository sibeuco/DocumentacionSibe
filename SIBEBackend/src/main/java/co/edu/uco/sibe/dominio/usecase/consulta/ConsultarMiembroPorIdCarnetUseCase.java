package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.MiembroDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.MiembroRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.MIEMBRO_NO_ENCONTRADO_CON_ID_CARNET;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class ConsultarMiembroPorIdCarnetUseCase {
    private final MiembroRepositorioConsulta miembroRepositorioConsulta;

    public ConsultarMiembroPorIdCarnetUseCase(MiembroRepositorioConsulta miembroRepositorioConsulta) {
        this.miembroRepositorioConsulta = miembroRepositorioConsulta;
    }

    public MiembroDTO ejecutar(String idCarnet) {
        return validarSiExisteMiembro(idCarnet);
    }

    private MiembroDTO validarSiExisteMiembro(String idCarnet) {
        var miembro = miembroRepositorioConsulta.consultarPorIdCarnetDTO(idCarnet);
        if (esNulo(miembro)) {
            throw new ValorInvalidoExcepcion(obtenerMensajeConParametro(MIEMBRO_NO_ENCONTRADO_CON_ID_CARNET,idCarnet));
        }
        return miembro;
    }
}