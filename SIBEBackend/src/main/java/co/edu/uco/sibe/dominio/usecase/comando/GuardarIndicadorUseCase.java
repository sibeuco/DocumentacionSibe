package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.Indicador;
import co.edu.uco.sibe.dominio.puerto.comando.IndicadorRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.IndicadorRepositorioConsulta;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotoresFabrica;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NOMBRE_INDICADOR_EXISTENTE;

public class GuardarIndicadorUseCase {
    private final IndicadorRepositorioComando indicadorRepositorioComando;
    private final IndicadorRepositorioConsulta indicadorRepositorioConsulta;

    public GuardarIndicadorUseCase(IndicadorRepositorioComando indicadorRepositorioComando, IndicadorRepositorioConsulta indicadorRepositorioConsulta) {
        this.indicadorRepositorioComando = indicadorRepositorioComando;
        this.indicadorRepositorioConsulta = indicadorRepositorioConsulta;
    }

    public UUID ejecutar(Indicador indicador){
        MotoresFabrica.MOTOR_INDICADOR.ejecutar(indicador, TipoOperacion.CREAR);

        validarNoExisteIndicadorConNombre(indicador.getNombre());

        return this.indicadorRepositorioComando.guardar(indicador);
    }

    private void validarNoExisteIndicadorConNombre(String nombre) {
        if (!ValidadorObjeto.esNulo(this.indicadorRepositorioConsulta.consultarPorNombre(nombre))){
            throw new ValorDuplicadoExcepcion(NOMBRE_INDICADOR_EXISTENTE);
        }
    }
}