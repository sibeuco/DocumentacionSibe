package co.edu.uco.sibe.dominio.regla;

import java.util.UUID;

public interface Regla<T> {
    void validarIdentificador(UUID identificador);
    void validarCampos(T modelo);
}