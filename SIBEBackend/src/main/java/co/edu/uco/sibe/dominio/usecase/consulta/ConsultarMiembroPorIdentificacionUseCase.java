package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.MiembroDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.MiembroRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.MIEMBRO_NO_ENCONTRADO_CON_IDENTIFICACION;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class ConsultarMiembroPorIdentificacionUseCase {

    private final MiembroRepositorioConsulta miembroRepositorioConsulta;

    public ConsultarMiembroPorIdentificacionUseCase(MiembroRepositorioConsulta miembroRepositorioConsulta) {
        this.miembroRepositorioConsulta = miembroRepositorioConsulta;
    }

    public MiembroDTO ejecutar(String identificacion) {
        return validarSiExisteMiembro(identificacion);
    }

    private MiembroDTO validarSiExisteMiembro(String identificacion) {
        var miembro = miembroRepositorioConsulta.consultarPorIdentificacionDTO(identificacion);
        if (esNulo(miembro)) {
            throw new ValorInvalidoExcepcion(obtenerMensajeConParametro(MIEMBRO_NO_ENCONTRADO_CON_IDENTIFICACION, identificacion));
        }
        return miembro;
    }
}