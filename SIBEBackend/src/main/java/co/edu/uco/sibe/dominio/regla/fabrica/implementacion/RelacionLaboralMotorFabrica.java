package co.edu.uco.sibe.dominio.regla.fabrica.implementacion;

import co.edu.uco.sibe.dominio.modelo.RelacionLaboral;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotorFabrica;
import co.edu.uco.sibe.dominio.regla.implementacion.RelacionLaboralRegla;
import co.edu.uco.sibe.dominio.regla.motor.MotorRegla;

public class RelacionLaboralMotorFabrica implements MotorFabrica<RelacionLaboral> {
    private static final RelacionLaboralMotorFabrica INSTANCIA = new RelacionLaboralMotorFabrica();

    private RelacionLaboralMotorFabrica() {
        super();
    }

    public static RelacionLaboralMotorFabrica obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public MotorRegla<RelacionLaboral> obtenerMotorReglas() {
        var motor = new MotorRegla<RelacionLaboral>();
        var regla = RelacionLaboralRegla.obtenerInstancia();

        motor.agregarRegla(TipoOperacion.CREAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CREAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ELIMINAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CONSULTAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));

        return motor;
    }
}