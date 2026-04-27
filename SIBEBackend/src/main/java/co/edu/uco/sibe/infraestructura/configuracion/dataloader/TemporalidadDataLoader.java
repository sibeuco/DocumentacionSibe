package co.edu.uco.sibe.infraestructura.configuracion.dataloader;

import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarTemporalidadManejador;
import co.edu.uco.sibe.aplicacion.consulta.HayDatosTemporalidadManejador;
import co.edu.uco.sibe.infraestructura.configuracion.dataloader.fabrica.DatosTemporalidadFabrica;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.TRES;

@Component
@Order(TRES)
@RequiredArgsConstructor
public class TemporalidadDataLoader extends DataLoader {
    private final HayDatosTemporalidadManejador hayDatosTemporalidadManejador;
    private final GuardarTemporalidadManejador guardarTemporalidadManejador;

    @Override
    protected boolean debenCargarseDatos() {
        return this.hayDatosTemporalidadManejador.ejecutar();
    }

    @Override
    protected void cargarDatos() {
        DatosTemporalidadFabrica.obtenerTemporalidades()
                .forEach(this.guardarTemporalidadManejador::ejecutar);
    }
}