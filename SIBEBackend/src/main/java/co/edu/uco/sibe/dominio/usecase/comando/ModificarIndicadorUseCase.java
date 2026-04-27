package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.Indicador;
import co.edu.uco.sibe.dominio.puerto.comando.IndicadorRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.IndicadorRepositorioConsulta;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotoresFabrica;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.INDICADOR_NO_EXISTENTE;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NOMBRE_INDICADOR_EXISTENTE;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class ModificarIndicadorUseCase {
    private final IndicadorRepositorioComando indicadorRepositorioComando;
    private final IndicadorRepositorioConsulta indicadorRepositorioConsulta;

    public ModificarIndicadorUseCase(IndicadorRepositorioComando indicadorRepositorioComando, IndicadorRepositorioConsulta indicadorRepositorioConsulta) {
        this.indicadorRepositorioComando = indicadorRepositorioComando;
        this.indicadorRepositorioConsulta = indicadorRepositorioConsulta;
    }

    public UUID ejecutar(Indicador indicador, UUID identificador){
        MotoresFabrica.MOTOR_INDICADOR.ejecutar(indicador, TipoOperacion.ACTUALIZAR);

        validarExisteIndicadorConIdentificador(identificador);
        validarNoExisteIndicadorConNombre(indicador.getNombre(), identificador);

        return this.indicadorRepositorioComando.guardar(indicador);
    }

    private void validarExisteIndicadorConIdentificador(UUID identificador) {
        if (esNulo(this.indicadorRepositorioConsulta.consultarPorIdentificador(identificador))) {
            throw new ValorDuplicadoExcepcion(INDICADOR_NO_EXISTENTE);
        }
    }

    private void validarNoExisteIndicadorConNombre(String nombre, UUID identificador) {
        var indicador = this.indicadorRepositorioConsulta.consultarPorNombre(nombre);
        if (!esNulo(indicador) && !indicador.getIdentificador().equals(identificador)) {
            throw new ValorDuplicadoExcepcion(NOMBRE_INDICADOR_EXISTENTE);
        }
    }
}