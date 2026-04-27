package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.regla.Regla;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.*;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroEntre;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarSemestreValido;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarTextoAlfanumericoValido;

public final class ActividadRegla implements Regla<Actividad> {
    private static final ActividadRegla INSTANCIA = new ActividadRegla();

    private ActividadRegla() {
        super();
    }

    public static ActividadRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_ACTIVIDAD_NULO);
    }

    @Override
    public void validarCampos(Actividad modelo) {
        validarNombre(modelo.getNombre());
        validarObjetivo(modelo.getObjetivo());
        validarSemestre(modelo.getSemestre());
        validarRutaInsumos(modelo.getRutaInsumos());
    }

    private void validarNombre(String nombre) {
        validarObligatorio(nombre, NOMBRE_ACTIVIDAD_OBLIGATORIO);
        validarTextoAlfanumericoValido(nombre, NOMBRE_ACTIVIDAD_INVALIDO);
        validarNumeroEntre(nombre.length(), 10, 200, LONGITUD_NOMBRE_ACTIVIDAD_INVALIDA);
    }

    private void validarObjetivo(String objetivo) {
        validarObligatorio(objetivo, OBJETIVO_ACTIVIDAD_OBLIGATORIO);
        validarTextoAlfanumericoValido(objetivo, OBJETIVO_ACTIVIDAD_INVALIDO);
        validarNumeroEntre(objetivo.length(), 10, 500, LONGITUD_OBJETIVO_ACTIVIDAD_INVALIDA);
    }

    private void validarSemestre(String semestre) {
        validarObligatorio(semestre, SEMESTRE_ACTIVIDAD_OBLIGATORIO);
        validarSemestreValido(semestre, SEMESTRE_ACTIVIDAD_INVALIDO);
        validarNumeroEntre(semestre.length(), 6, 6, LONGITUD_SEMESTRE_ACTIVIDAD_INVALIDA);
    }

    private void validarRutaInsumos(String rutaInsumos) {
        validarObligatorio(rutaInsumos, RUTA_INSUMOS_ACTIVIDAD_OBLIGATORIA);
        validarTextoAlfanumericoValido(rutaInsumos, RUTA_INSUMOS_ACTIVIDAD_INVALIDA);
        validarNumeroEntre(rutaInsumos.length(), 10, 3000, LONGITUD_RUTA_INSUMOS_ACTIVIDAD_INVALIDA);
    }
}