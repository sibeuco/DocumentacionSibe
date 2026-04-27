package co.edu.uco.sibe.dominio.regla.fabrica.implementacion;

import co.edu.uco.sibe.dominio.modelo.ParticipanteEstudiante;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotorFabrica;
import co.edu.uco.sibe.dominio.regla.implementacion.ParticipanteEstudianteRegla;
import co.edu.uco.sibe.dominio.regla.motor.MotorRegla;

public class ParticipanteEstudianteMotorFabrica implements MotorFabrica<ParticipanteEstudiante> {
    private static final ParticipanteEstudianteMotorFabrica INSTANCIA = new ParticipanteEstudianteMotorFabrica();

    private ParticipanteEstudianteMotorFabrica() {
        super();
    }

    public static ParticipanteEstudianteMotorFabrica obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public MotorRegla<ParticipanteEstudiante> obtenerMotorReglas() {
        var motor = new MotorRegla<ParticipanteEstudiante>();
        var regla = ParticipanteEstudianteRegla.obtenerInstancia();

        motor.agregarRegla(TipoOperacion.CREAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CREAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ELIMINAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CONSULTAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));

        return motor;
    }
}