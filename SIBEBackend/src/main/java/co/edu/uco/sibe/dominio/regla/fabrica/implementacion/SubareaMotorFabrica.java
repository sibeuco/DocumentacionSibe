package co.edu.uco.sibe.dominio.regla.fabrica.implementacion;

import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotorFabrica;
import co.edu.uco.sibe.dominio.regla.implementacion.SubareaRegla;
import co.edu.uco.sibe.dominio.regla.motor.MotorRegla;

public class SubareaMotorFabrica implements MotorFabrica<Subarea> {
    private static final SubareaMotorFabrica INSTANCIA = new SubareaMotorFabrica();

    private SubareaMotorFabrica() {
        super();
    }

    public static SubareaMotorFabrica obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public MotorRegla<Subarea> obtenerMotorReglas() {
        var motor = new MotorRegla<Subarea>();
        var regla = SubareaRegla.obtenerInstancia();

        motor.agregarRegla(TipoOperacion.CREAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CREAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ELIMINAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CONSULTAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));

        return motor;
    }
}