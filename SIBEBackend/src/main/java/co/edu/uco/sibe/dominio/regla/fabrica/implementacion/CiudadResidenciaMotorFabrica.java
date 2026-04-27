package co.edu.uco.sibe.dominio.regla.fabrica.implementacion;

import co.edu.uco.sibe.dominio.modelo.CiudadResidencia;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotorFabrica;
import co.edu.uco.sibe.dominio.regla.implementacion.CiudadResidenciaRegla;
import co.edu.uco.sibe.dominio.regla.motor.MotorRegla;

public class CiudadResidenciaMotorFabrica implements MotorFabrica<CiudadResidencia> {
    private static final CiudadResidenciaMotorFabrica INSTANCIA = new CiudadResidenciaMotorFabrica();

    private CiudadResidenciaMotorFabrica() {
        super();
    }

    public static CiudadResidenciaMotorFabrica obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public MotorRegla<CiudadResidencia> obtenerMotorReglas() {
        var motor = new MotorRegla<CiudadResidencia>();
        var regla = CiudadResidenciaRegla.obtenerInstancia();

        motor.agregarRegla(TipoOperacion.CREAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CREAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ELIMINAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CONSULTAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));

        return motor;
    }
}