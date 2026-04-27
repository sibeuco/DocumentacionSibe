package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.TipoIndicador;
import co.edu.uco.sibe.dominio.puerto.comando.TipoIndicadorRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoIndicadorRepositorioConsulta;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotoresFabrica;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NATURALEZA_TIPO_INDICADOR_EXISTENTE;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class GuardarTipoIndicadorUseCase {
    private final TipoIndicadorRepositorioComando tipoIndicadorRepositorioComando;
    private final TipoIndicadorRepositorioConsulta tipoIndicadorRepositorioConsulta;

    public GuardarTipoIndicadorUseCase(TipoIndicadorRepositorioComando tipoIndicadorRepositorioComando, TipoIndicadorRepositorioConsulta tipoIndicadorRepositorioConsulta) {
        this.tipoIndicadorRepositorioComando = tipoIndicadorRepositorioComando;
        this.tipoIndicadorRepositorioConsulta = tipoIndicadorRepositorioConsulta;
    }


    public UUID ejecutar(TipoIndicador tipoIndicador) {
        MotoresFabrica.MOTOR_TIPO_INDICADOR.ejecutar(tipoIndicador, TipoOperacion.CREAR);

        validarNoExisteTipoIndicadorConNaturaleza(tipoIndicador.getNaturaleza());

        return this.tipoIndicadorRepositorioComando.guardar(tipoIndicador);
    }

    private void validarNoExisteTipoIndicadorConNaturaleza(String naturaleza) {
        if (!esNulo(this.tipoIndicadorRepositorioConsulta.consultarPorNaturaleza(naturaleza))){
            throw new ValorDuplicadoExcepcion(NATURALEZA_TIPO_INDICADOR_EXISTENTE);
        }
    }
}