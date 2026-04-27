package co.edu.uco.sibe.dominio.regla.fabrica.implementacion;

import co.edu.uco.sibe.dominio.modelo.EjecucionActividad;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotorFabrica;
import co.edu.uco.sibe.dominio.regla.implementacion.EjecucionActividadRegla;
import co.edu.uco.sibe.dominio.regla.motor.MotorRegla;

public class EjecucionActividadMotorFabrica implements MotorFabrica<EjecucionActividad> {
    private static final EjecucionActividadMotorFabrica INSTANCIA = new EjecucionActividadMotorFabrica();

    private EjecucionActividadMotorFabrica() {
        super();
    }

    public static EjecucionActividadMotorFabrica obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public MotorRegla<EjecucionActividad> obtenerMotorReglas() {
        var motor = new MotorRegla<EjecucionActividad>();
        var regla = EjecucionActividadRegla.obtenerInstancia();

        motor.agregarRegla(TipoOperacion.CREAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.ELIMINAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CONSULTAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));

        return motor;
    }
}