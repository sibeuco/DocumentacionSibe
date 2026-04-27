package co.edu.uco.sibe.dominio.regla.fabrica;

import co.edu.uco.sibe.dominio.regla.motor.MotorRegla;

public interface MotorFabrica<T> {
    MotorRegla<T> obtenerMotorReglas();
}