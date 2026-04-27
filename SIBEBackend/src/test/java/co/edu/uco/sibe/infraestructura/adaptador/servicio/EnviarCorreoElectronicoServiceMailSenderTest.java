package co.edu.uco.sibe.infraestructura.adaptador.servicio;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import jakarta.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnviarCorreoElectronicoServiceMailSenderTest {

    @Mock private JavaMailSender mailSender;
    @Mock private MimeMessage mimeMessage;

    private EnviarCorreoElectronicoServiceMailSender service;

    @BeforeEach
    void setUp() {
        service = new EnviarCorreoElectronicoServiceMailSender(mailSender, "sibe@test.com");
    }

    @Test
    void deberiaEnviarCorreoExitosamente() {
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        service.enviarCorreo("destinatario@test.com", "Asunto", "123456");

        verify(mailSender).send(mimeMessage);
    }

    @Test
    void deberiaLanzarExcepcionCuandoFallaElEnvio() {
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        doThrow(new RuntimeException("Error de envío")).when(mailSender).send(any(MimeMessage.class));

        assertThrows(IllegalStateException.class, () ->
                service.enviarCorreo("destinatario@test.com", "Asunto", "123456"));
    }

    @Test
    void deberiaLanzarExcepcionPorMessagingException() throws Exception {
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        doThrow(new jakarta.mail.MessagingException("Error SMTP"))
                .when(mimeMessage).setContent(any(jakarta.mail.Multipart.class));

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                service.enviarCorreo("destinatario@test.com", "Asunto", "123456"));
        assertInstanceOf(jakarta.mail.MessagingException.class, ex.getCause());
    }
}
