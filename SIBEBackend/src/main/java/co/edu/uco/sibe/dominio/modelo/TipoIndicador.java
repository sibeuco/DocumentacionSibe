package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.obtenerValorDefecto;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;

@Getter
public class TipoIndicador {
    private UUID identificador;
    private String naturaleza;
    private String tipologia;

    private TipoIndicador(UUID identificador, String naturaleza, String tipologia) {
        this.identificador = identificador;
        this.naturaleza = naturaleza;
        this.tipologia = tipologia;
    }

    public static TipoIndicador construir(UUID identificador, String naturalezaIndicador, String tipologiaIndicador) {
        return new TipoIndicador(
                identificador,
                obtenerTextoPorDefecto(naturalezaIndicador),
                obtenerTextoPorDefecto(tipologiaIndicador)
        );
    }

    public static TipoIndicador construir() {
        return new TipoIndicador(
                obtenerValorDefecto(),
                VACIO,
                VACIO
        );
    }
}