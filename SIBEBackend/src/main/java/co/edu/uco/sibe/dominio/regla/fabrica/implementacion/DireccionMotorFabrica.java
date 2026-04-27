package co.edu.uco.sibe.dominio.regla.fabrica.implementacion;

import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotorFabrica;
import co.edu.uco.sibe.dominio.regla.implementacion.DireccionRegla;
import co.edu.uco.sibe.dominio.regla.motor.MotorRegla;

public class DireccionMotorFabrica implements MotorFabrica<Direccion> {
    private static final DireccionMotorFabrica INSTANCIA = new DireccionMotorFabrica();

    private DireccionMotorFabrica() {
        super();
    }

    public static DireccionMotorFabrica obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public MotorRegla<Direccion> obtenerMotorReglas() {
        var motor = new MotorRegla<Direccion>();
        var regla = DireccionRegla.obtenerInstancia();

        motor.agregarRegla(TipoOperacion.CREAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CREAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ELIMINAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CONSULTAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));

        return motor;
    }
}