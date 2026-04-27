package co.edu.uco.sibe.dominio.regla.fabrica.implementacion;

import co.edu.uco.sibe.dominio.modelo.Estudiante;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotorFabrica;
import co.edu.uco.sibe.dominio.regla.implementacion.EstudianteRegla;
import co.edu.uco.sibe.dominio.regla.motor.MotorRegla;

public class EstudianteMotorFabrica implements MotorFabrica<Estudiante> {
    private static final EstudianteMotorFabrica INSTANCIA = new EstudianteMotorFabrica();

    private EstudianteMotorFabrica() {
        super();
    }

    public static EstudianteMotorFabrica obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public MotorRegla<Estudiante> obtenerMotorReglas() {
        var motor = new MotorRegla<Estudiante>();
        var regla = EstudianteRegla.obtenerInstancia();

        motor.agregarRegla(TipoOperacion.CREAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CREAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ELIMINAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CONSULTAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));

        return motor;
    }
}