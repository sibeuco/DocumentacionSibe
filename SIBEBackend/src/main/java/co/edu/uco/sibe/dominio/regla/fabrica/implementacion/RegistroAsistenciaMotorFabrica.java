package co.edu.uco.sibe.dominio.regla.fabrica.implementacion;

import co.edu.uco.sibe.dominio.modelo.RegistroAsistencia;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotorFabrica;
import co.edu.uco.sibe.dominio.regla.implementacion.RegistroAsistenciaRegla;
import co.edu.uco.sibe.dominio.regla.motor.MotorRegla;

public class RegistroAsistenciaMotorFabrica implements MotorFabrica<RegistroAsistencia> {
    private static final RegistroAsistenciaMotorFabrica INSTANCIA = new RegistroAsistenciaMotorFabrica();

    private RegistroAsistenciaMotorFabrica() {
        super();
    }

    public static RegistroAsistenciaMotorFabrica obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public MotorRegla<RegistroAsistencia> obtenerMotorReglas() {
        var motor = new MotorRegla<RegistroAsistencia>();
        var regla = RegistroAsistenciaRegla.obtenerInstancia();

        motor.agregarRegla(TipoOperacion.CREAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CREAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ELIMINAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CONSULTAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));

        return motor;
    }
}