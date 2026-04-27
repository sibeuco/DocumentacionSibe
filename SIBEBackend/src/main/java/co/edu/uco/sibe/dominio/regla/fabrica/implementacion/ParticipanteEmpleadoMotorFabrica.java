package co.edu.uco.sibe.dominio.regla.fabrica.implementacion;

import co.edu.uco.sibe.dominio.modelo.ParticipanteEmpleado;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotorFabrica;
import co.edu.uco.sibe.dominio.regla.implementacion.ParticipanteEmpleadoRegla;
import co.edu.uco.sibe.dominio.regla.motor.MotorRegla;

public class ParticipanteEmpleadoMotorFabrica implements MotorFabrica<ParticipanteEmpleado> {
    private static final ParticipanteEmpleadoMotorFabrica INSTANCIA = new ParticipanteEmpleadoMotorFabrica();

    private ParticipanteEmpleadoMotorFabrica() {
        super();
    }

    public static ParticipanteEmpleadoMotorFabrica obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public MotorRegla<ParticipanteEmpleado> obtenerMotorReglas() {
        var motor = new MotorRegla<ParticipanteEmpleado>();
        var regla = ParticipanteEmpleadoRegla.obtenerInstancia();

        motor.agregarRegla(TipoOperacion.CREAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CREAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ELIMINAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CONSULTAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));

        return motor;
    }
}