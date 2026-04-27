package co.edu.uco.sibe.dominio.regla.fabrica.implementacion;

import co.edu.uco.sibe.dominio.modelo.TipoIndicador;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotorFabrica;
import co.edu.uco.sibe.dominio.regla.implementacion.TipoIndicadorRegla;
import co.edu.uco.sibe.dominio.regla.motor.MotorRegla;

public class TipoIndicadorMotorFabrica implements MotorFabrica<TipoIndicador> {
    private static final TipoIndicadorMotorFabrica INSTANCIA = new TipoIndicadorMotorFabrica();

    private TipoIndicadorMotorFabrica() {
        super();
    }

    public static TipoIndicadorMotorFabrica obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public MotorRegla<TipoIndicador> obtenerMotorReglas() {
        var motor = new MotorRegla<TipoIndicador>();
        var regla = TipoIndicadorRegla.obtenerInstancia();

        motor.agregarRegla(TipoOperacion.CREAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CREAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ELIMINAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CONSULTAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));

        return motor;
    }
}