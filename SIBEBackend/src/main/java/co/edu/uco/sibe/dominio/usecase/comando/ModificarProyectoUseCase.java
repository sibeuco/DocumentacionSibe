package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.Proyecto;
import co.edu.uco.sibe.dominio.puerto.comando.ProyectoRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.ProyectoRepositorioConsulta;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotoresFabrica;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NUMERO_PROYECTO_EXISTENTE;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.PROYECTO_NO_EXISTE_CON_IDENTIFICADOR;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;

public class ModificarProyectoUseCase {
    private final ProyectoRepositorioComando proyectoRepositorioComando;
    private final ProyectoRepositorioConsulta proyectoRepositorioConsulta;

    public ModificarProyectoUseCase(ProyectoRepositorioComando proyectoRepositorioComando, ProyectoRepositorioConsulta proyectoRepositorioConsulta) {
        this.proyectoRepositorioComando = proyectoRepositorioComando;
        this.proyectoRepositorioConsulta = proyectoRepositorioConsulta;
    }

    public UUID ejecutar(Proyecto proyecto, UUID identificador){
        validarSiExisteProyectoCoIdentificador(identificador);

        MotoresFabrica.MOTOR_PROYECTO.ejecutar(proyecto, TipoOperacion.ACTUALIZAR);

        validarNoExisteProyectoCoNumero(proyecto.getNumeroProyecto(), identificador);

        return this.proyectoRepositorioComando.modificar(proyecto, identificador);
    }

    private void validarSiExisteProyectoCoIdentificador(UUID identificador) {
        if (ValidadorObjeto.esNulo(this.proyectoRepositorioConsulta.consultarPorIdentificador(identificador))){
            throw new ValorInvalidoExcepcion(obtenerMensajeConParametro(PROYECTO_NO_EXISTE_CON_IDENTIFICADOR, identificador));
        }
    }

    private void validarNoExisteProyectoCoNumero(String numeroProyecto, UUID identificador) {
        var proyecto = this.proyectoRepositorioConsulta.consultarPorNumeroProyecto(numeroProyecto);

        if (!ValidadorObjeto.esNulo(proyecto) && !proyecto.getIdentificador().equals(identificador)){
            throw new ValorDuplicadoExcepcion(NUMERO_PROYECTO_EXISTENTE);
        }
    }
}