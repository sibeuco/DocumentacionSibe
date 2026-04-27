package co.edu.uco.sibe.aplicacion.transversal;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ComandoRespuesta<T> {
    private T valor;

    public ComandoRespuesta(T valor) {
        this.valor = valor;
    }
}