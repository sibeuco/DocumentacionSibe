package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.PublicoInteres;
import co.edu.uco.sibe.dominio.puerto.comando.PublicoInteresRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.PublicoInteresRepositorioConsulta;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotoresFabrica;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.PUBLICO_INTERES_EXISTENTE;

public class GuardarPublicoInteresUseCase {
    private final PublicoInteresRepositorioComando publicoInteresRepositorioComando;
    private final PublicoInteresRepositorioConsulta publicoInteresRepositorioConsulta;

    public GuardarPublicoInteresUseCase(PublicoInteresRepositorioComando publicoInteresRepositorioComando, PublicoInteresRepositorioConsulta publicoInteresRepositorioConsulta) {
        this.publicoInteresRepositorioComando = publicoInteresRepositorioComando;
        this.publicoInteresRepositorioConsulta = publicoInteresRepositorioConsulta;
    }

    public UUID ejecutar(PublicoInteres publicoInteres) {
        MotoresFabrica.MOTOR_PUBLICO_INTERES.ejecutar(publicoInteres, TipoOperacion.CREAR);

        validarNoExistePublicoInteresConNombre(publicoInteres.getNombre());

        return this.publicoInteresRepositorioComando.guardar(publicoInteres);
    }

    private void validarNoExistePublicoInteresConNombre(String nombre) {
        if (!ValidadorObjeto.esNulo(this.publicoInteresRepositorioConsulta.consultarPorNombre(nombre))){
            throw new ValorDuplicadoExcepcion(PUBLICO_INTERES_EXISTENTE);
        }
    }
}