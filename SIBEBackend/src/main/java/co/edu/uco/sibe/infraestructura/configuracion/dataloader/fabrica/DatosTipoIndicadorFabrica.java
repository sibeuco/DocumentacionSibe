package co.edu.uco.sibe.infraestructura.configuracion.dataloader.fabrica;

import co.edu.uco.sibe.aplicacion.comando.TipoIndicadorComando;
import java.util.List;
import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.*;

public final class DatosTipoIndicadorFabrica {
    private DatosTipoIndicadorFabrica() {
        super();
    }

    public static List<TipoIndicadorComando> crearTiposIndicador() {
        return List.of(
                crearTipoIndicadorEficiencia(),
                crearTipoIndicadorCapacidadInstalada(),
                crearTipoIndicadorEficacia(),
                crearTipoIndicadorEfectividad(),
                crearTipoIndicadorValor()
        );
    }

    public static TipoIndicadorComando crearTipoIndicadorEficiencia() {
        return new TipoIndicadorComando(
                NATURALEZA_EFICIENCIA,
                TIPOLOGIA_EFICIENCIA
        );
    }

    public static TipoIndicadorComando crearTipoIndicadorCapacidadInstalada() {
        return new TipoIndicadorComando(
                NATURALEZA_CAPACIDAD_INSTALADA,
                TIPOLOGIA_CAPACIDAD_INSTALADA
        );
    }

    public static TipoIndicadorComando crearTipoIndicadorEficacia() {
        return new TipoIndicadorComando(
                NATURALEZA_EFICACIA,
                TIPOLOGIA_EFICACIA
        );
    }

    public static TipoIndicadorComando crearTipoIndicadorEfectividad() {
        return new TipoIndicadorComando(
                NATURALEZA_EFECTIVIDAD,
                TIPOLOGIA_EFECTIVIDAD
        );
    }

    public static TipoIndicadorComando crearTipoIndicadorValor() {
        return new TipoIndicadorComando(
                NATURALEZA_VALOR,
                TIPOLOGIA_VALOR
        );
    }
}