package co.edu.uco.sibe.dominio.regla.implementacion;

import co.edu.uco.sibe.dominio.modelo.Proyecto;
import co.edu.uco.sibe.dominio.regla.Regla;

import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesValidacionConstante.*;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorNumero.validarNumeroEntre;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.validarObligatorio;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarTextoAlfanumericoValido;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.validarTextoValido;

public final class ProyectoRegla implements Regla<Proyecto> {
    private static final ProyectoRegla INSTANCIA = new ProyectoRegla();

    private ProyectoRegla() {
        super();
    }

    public static ProyectoRegla obtenerInstancia() {
        return INSTANCIA;
    }

    @Override
    public void validarIdentificador(UUID identificador) {
        validarObligatorio(identificador, IDENTIFICADOR_PROYECTO_NULO);
    }

    @Override
    public void validarCampos(Proyecto modelo) {
        validarNombre(modelo.getNombre());
        validarObjetivo(modelo.getObjetivo());
    }

    public void validarNumeroProyecto(String numeroProyecto) {
        validarObligatorio(numeroProyecto, NUMERO_PROYECTO_OBLIGATORIO);
        validarTextoAlfanumericoValido(numeroProyecto, NUMERO_PROYECTO_INVALIDO);
        validarNumeroEntre(numeroProyecto.length(), 1, 12, LONGITUD_NUMERO_PROYECTO_INVALIDA);
    }

    private void validarNombre(String nombre) {
        validarObligatorio(nombre, NOMBRE_PROYECTO_OBLIGATORIO);
        validarTextoValido(nombre, NOMBRE_PROYECTO_INVALIDO);
        validarNumeroEntre(nombre.length(), 10, 100, LONGITUD_NOMBRE_PROYECTO_INVALIDA);
    }

    private void validarObjetivo(String objetivo) {
        validarObligatorio(objetivo, OBJETIVO_PROYECTO_OBLIGATORIO);
        validarTextoAlfanumericoValido(objetivo, OBJETIVO_PROYECTO_INVALIDO);
        validarNumeroEntre(objetivo.length(), 10, 500, LONGITUD_OBJETIVO_PROYECTO_INVALIDA);
    }
}