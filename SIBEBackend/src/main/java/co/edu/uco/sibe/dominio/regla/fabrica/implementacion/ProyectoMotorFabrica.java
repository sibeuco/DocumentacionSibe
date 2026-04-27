package co.edu.uco.sibe.dominio.regla.fabrica.implementacion;

import co.edu.uco.sibe.dominio.modelo.Proyecto;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotorFabrica;
import co.edu.uco.sibe.dominio.regla.implementacion.ProyectoRegla;
import co.edu.uco.sibe.dominio.regla.motor.MotorRegla;

public class ProyectoMotorFabrica implements MotorFabrica<Proyecto> {
    private static final ProyectoMotorFabrica INSTANCIA = new ProyectoMotorFabrica();

    private ProyectoMotorFabrica() {
        super();
    }

    public static ProyectoMotorFabrica obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public MotorRegla<Proyecto> obtenerMotorReglas() {
        var motor = new MotorRegla<Proyecto>();
        var regla = ProyectoRegla.obtenerInstancia();

        motor.agregarRegla(TipoOperacion.CREAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CREAR, modelo -> regla.validarNumeroProyecto(modelo.getNumeroProyecto()));
        motor.agregarRegla(TipoOperacion.CREAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, modelo -> regla.validarNumeroProyecto(modelo.getNumeroProyecto()));
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, regla::validarCampos);
        motor.agregarRegla(TipoOperacion.ELIMINAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CONSULTAR, modelo -> regla.validarIdentificador(modelo.getIdentificador()));

        return motor;
    }
}