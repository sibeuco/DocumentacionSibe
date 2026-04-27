package co.edu.uco.sibe.dominio.regla.motor;

import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class MotorRegla<T> {
    private final Map<TipoOperacion, List<Consumer<T>>> mapaReglas = new EnumMap<>(TipoOperacion.class);

    public void agregarRegla(TipoOperacion operacion, Consumer<T> regla) {
        mapaReglas.computeIfAbsent(operacion, k -> new ArrayList<>()).add(regla);
    }

    public void ejecutar(T objeto, TipoOperacion operacion) {
        List<Consumer<T>> reglas = mapaReglas.getOrDefault(operacion, List.of());

        reglas.forEach(regla -> regla.accept(objeto));
    }
}