package co.edu.uco.sibe.infraestructura.configuracion.dataloader;

import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarPublicoInteresManejador;
import co.edu.uco.sibe.aplicacion.consulta.HayDatosPublicoInteresManejador;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.CINCO;
import static co.edu.uco.sibe.infraestructura.configuracion.dataloader.fabrica.DatosPublicoInteresFabrica.crearPublicosInteres;

@Component
@Order(CINCO)
@RequiredArgsConstructor
public class PublicoInteresDataLoader extends DataLoader {
    private final HayDatosPublicoInteresManejador hayDatosPublicoInteresManejador;
    private final GuardarPublicoInteresManejador guardarPublicoInteresManejador;

    @Override
    protected boolean debenCargarseDatos() {
        return this.hayDatosPublicoInteresManejador.ejecutar();
    }

    @Override
    protected void cargarDatos() {
        crearPublicosInteres().forEach(this.guardarPublicoInteresManejador::ejecutar);
    }
}