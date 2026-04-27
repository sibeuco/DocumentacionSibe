package co.edu.uco.sibe.dominio.regla.fabrica.implementacion;

import co.edu.uco.sibe.dominio.modelo.Indicador;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotorFabrica;
import co.edu.uco.sibe.dominio.regla.implementacion.IndicadorRegla;
import co.edu.uco.sibe.dominio.regla.motor.MotorRegla;

public class IndicadorMotorFabrica implements MotorFabrica<Indicador> {
    private static final IndicadorMotorFabrica INSTANCIA = new IndicadorMotorFabrica();

    private IndicadorMotorFabrica() {
        super();
    }

    public static IndicadorMotorFabrica obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public MotorRegla<Indicador> obtenerMotorReglas() {
        var motor = new MotorRegla<Indicador>();
        var regla = IndicadorRegla.obtenerInstancia();

        motor.agregarRegla(TipoOperacion.CREAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CREAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ELIMINAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CONSULTAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));

        return motor;
    }
}