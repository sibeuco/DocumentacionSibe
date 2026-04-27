package co.edu.uco.sibe.infraestructura.configuracion.dataloader;

import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarSubareaManejador;
import co.edu.uco.sibe.aplicacion.consulta.HayDatosSubareaManejador;
import co.edu.uco.sibe.infraestructura.configuracion.dataloader.fabrica.DatosSubareaFabrica;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.SIETE;

@Component
@Order(SIETE)
@RequiredArgsConstructor
public class SubareaDataLoader extends DataLoader {
    private final HayDatosSubareaManejador hayDatosSubareaManejador;
    private final GuardarSubareaManejador guardarSubareaManejador;

    @Override
    protected boolean debenCargarseDatos() {
        return this.hayDatosSubareaManejador.ejecutar();
    }

    @Override
    protected void cargarDatos() {
        DatosSubareaFabrica.obtenerSubareas()
                .forEach(this.guardarSubareaManejador::ejecutar);
    }
}