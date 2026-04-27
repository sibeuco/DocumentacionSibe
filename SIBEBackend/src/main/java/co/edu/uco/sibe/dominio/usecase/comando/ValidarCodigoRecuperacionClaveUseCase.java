package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.puerto.comando.PersonaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.servicio.EncriptarClaveServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.TiempoVencidoExcepcion;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.EL_CODIGO_PARA_RECUPERAR_CLAVE_ES_INCORRECTO;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.EL_CODIGO_PARA_RECUPERAR_CLAVE_YA_NO_ES_VALIDO;

public class ValidarCodigoRecuperacionClaveUseCase {
    private final PersonaRepositorioConsulta personaRepositorioConsulta;
    private final PersonaRepositorioComando personaRepositorioComando;
    private final EncriptarClaveServicio encriptarClaveServicio;

    public ValidarCodigoRecuperacionClaveUseCase(PersonaRepositorioConsulta personaRepositorioConsulta, PersonaRepositorioComando personaRepositorioComando, EncriptarClaveServicio encriptarClaveServicio) {
        this.personaRepositorioConsulta = personaRepositorioConsulta;
        this.personaRepositorioComando = personaRepositorioComando;
        this.encriptarClaveServicio = encriptarClaveServicio;
    }

    public Boolean ejecutar(String correo, String codigo) {
        validarSiNoExisteCodigo(codigo, correo);

        var fecha = this.personaRepositorioConsulta.consultarFechaPeticionRecuperacionClaveConCorreo(correo);

        validarSiAunEsValidoElCodigo(fecha);

        this.personaRepositorioComando.eliminarPeticionRecuperacionClaveConCorreo(correo);

        return Boolean.TRUE;
    }

    private void validarSiNoExisteCodigo(String codigo, String correo) {
        var codigoCifrado = this.personaRepositorioConsulta.consultarCodigoConCorreo(correo);

        if (!this.encriptarClaveServicio.existe(codigo, codigoCifrado)) {
            throw new ValorInvalidoExcepcion(EL_CODIGO_PARA_RECUPERAR_CLAVE_ES_INCORRECTO);
        }
    }

    private void validarSiAunEsValidoElCodigo(LocalDateTime fecha) {
        var ahora = LocalDateTime.now();

        var minutosDiferencia = ChronoUnit.MINUTES.between(fecha, ahora);

        if (minutosDiferencia > 5) {
            throw new TiempoVencidoExcepcion(EL_CODIGO_PARA_RECUPERAR_CLAVE_YA_NO_ES_VALIDO);
        }
    }
}