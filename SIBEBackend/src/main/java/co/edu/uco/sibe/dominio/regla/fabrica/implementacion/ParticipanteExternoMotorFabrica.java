package co.edu.uco.sibe.dominio.regla.fabrica.implementacion;

import co.edu.uco.sibe.dominio.modelo.ParticipanteExterno;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotorFabrica;
import co.edu.uco.sibe.dominio.regla.implementacion.ParticipanteExternoRegla;
import co.edu.uco.sibe.dominio.regla.motor.MotorRegla;

public class ParticipanteExternoMotorFabrica implements MotorFabrica<ParticipanteExterno> {
    private static final ParticipanteExternoMotorFabrica INSTANCIA = new ParticipanteExternoMotorFabrica();

    private ParticipanteExternoMotorFabrica() {
        super();
    }

    public static ParticipanteExternoMotorFabrica obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public MotorRegla<ParticipanteExterno> obtenerMotorReglas() {
        var motor = new MotorRegla<ParticipanteExterno>();
        var regla = ParticipanteExternoRegla.obtenerInstancia();

        motor.agregarRegla(TipoOperacion.CREAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CREAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ELIMINAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CONSULTAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));

        return motor;
    }
}