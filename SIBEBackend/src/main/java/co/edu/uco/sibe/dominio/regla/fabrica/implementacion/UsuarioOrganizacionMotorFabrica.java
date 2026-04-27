package co.edu.uco.sibe.dominio.regla.fabrica.implementacion;

import co.edu.uco.sibe.dominio.modelo.UsuarioOrganizacion;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotorFabrica;
import co.edu.uco.sibe.dominio.regla.implementacion.UsuarioOrganizacionRegla;
import co.edu.uco.sibe.dominio.regla.motor.MotorRegla;

public class UsuarioOrganizacionMotorFabrica implements MotorFabrica<UsuarioOrganizacion> {
    private static final UsuarioOrganizacionMotorFabrica INSTANCIA = new UsuarioOrganizacionMotorFabrica();

    private UsuarioOrganizacionMotorFabrica() {
        super();
    }

    public static UsuarioOrganizacionMotorFabrica obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public MotorRegla<UsuarioOrganizacion> obtenerMotorReglas() {
        var motor = new MotorRegla<UsuarioOrganizacion>();
        var reglaMotor = UsuarioOrganizacionRegla.obtenerInstancia();

        motor.agregarRegla(TipoOperacion.CREAR, modelo -> reglaMotor.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.ACTUALIZAR, modelo -> reglaMotor.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.ELIMINAR, modelo -> reglaMotor.validarIdentificador(modelo.getIdentificador()));
        motor.agregarRegla(TipoOperacion.CONSULTAR, modelo -> reglaMotor.validarIdentificador(modelo.getIdentificador()));

        return motor;
    }
}