package co.edu.uco.sibe.infraestructura.configuracion.dataloader;

import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarEstadoActividadManejador;
import co.edu.uco.sibe.aplicacion.consulta.HayDatosEstadoActividadManejador;
import co.edu.uco.sibe.infraestructura.configuracion.dataloader.fabrica.DatosEstadoActividadFabrica;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.CUATRO;

@Component
@Order(CUATRO)
@RequiredArgsConstructor
public class EstadoActividadDataLoader extends DataLoader {
    private final HayDatosEstadoActividadManejador hayDatosEstadoActividadManejador;
    private final GuardarEstadoActividadManejador guardarEstadoActividadManejador;

    @Override
    protected boolean debenCargarseDatos() {
        return this.hayDatosEstadoActividadManejador.ejecutar();
    }

    @Override
    protected void cargarDatos() {
        DatosEstadoActividadFabrica.obtenerEstadosActividad()
                .forEach(this.guardarEstadoActividadManejador::ejecutar);
    }
}