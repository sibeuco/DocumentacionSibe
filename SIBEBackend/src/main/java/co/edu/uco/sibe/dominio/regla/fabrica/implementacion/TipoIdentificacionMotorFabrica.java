package co.edu.uco.sibe.dominio.regla.fabrica.implementacion;

import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotorFabrica;
import co.edu.uco.sibe.dominio.regla.implementacion.TipoIdentificacionRegla;
import co.edu.uco.sibe.dominio.regla.motor.MotorRegla;

public class TipoIdentificacionMotorFabrica implements MotorFabrica<TipoIdentificacion> {
    private static final TipoIdentificacionMotorFabrica INSTANCIA = new TipoIdentificacionMotorFabrica();

    private TipoIdentificacionMotorFabrica() {
        super();
    }

    public static TipoIdentificacionMotorFabrica obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public MotorRegla<TipoIdentificacion> obtenerMotorReglas() {
        var motor = new MotorRegla<TipoIdentificacion>();
        var regla = TipoIdentificacionRegla.obtenerInstancia();

        motor.agregarRegla(TipoOperacion.CREAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CREAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ELIMINAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CONSULTAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));

        return motor;
    }
}