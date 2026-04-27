package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.dominio.puerto.consulta.DireccionRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NO_EXISTE_DIRECCION_CON_NOMBRE;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class ConsultarDireccionPorNombreUseCase {
    private final DireccionRepositorioConsulta direccionRepositorioConsulta;
    private final AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    public ConsultarDireccionPorNombreUseCase(DireccionRepositorioConsulta direccionRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        this.direccionRepositorioConsulta = direccionRepositorioConsulta;
        this.autorizacionServicio = autorizacionServicio;
    }

    public Direccion ejecutar(String nombre) {
        validarSiNoExisteDireccionConNombre(nombre);

        var resultado = this.direccionRepositorioConsulta.consultarPorNombre(nombre);
        autorizacionServicio.validarAccesoADireccion(resultado.getIdentificador());
        return resultado;
    }

    private void validarSiNoExisteDireccionConNombre(String nombre) {
        if (esNulo(this.direccionRepositorioConsulta.consultarPorNombre(nombre))) {
            throw new ValorInvalidoExcepcion(obtenerMensajeConParametro(NO_EXISTE_DIRECCION_CON_NOMBRE, nombre));
        }
    }
}