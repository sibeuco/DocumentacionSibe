package co.edu.uco.sibe.infraestructura.adaptador.servicio;

import co.edu.uco.sibe.dominio.puerto.servicio.EnviarCorreoElectronicoService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import static co.edu.uco.sibe.dominio.transversal.constante.CorreoConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.ERROR_AL_ENVIAR_CORREO;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.ERROR_INESPERADO_EN_EL_SERVICIO_DE_ENVIAR_CORREO;

@Service
public class EnviarCorreoElectronicoServiceMailSender implements EnviarCorreoElectronicoService {
    private final JavaMailSender mailSender;
    private final String fromEmail;

    public EnviarCorreoElectronicoServiceMailSender(JavaMailSender mailSender,
                                                    @Value("${spring.mail.username}") String fromEmail) {
        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
    }

    @Async
    @Override
    public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_OCHO);

            helper.setFrom(fromEmail);
            helper.setTo(destinatario);
            helper.setSubject(asunto);

            String htmlContent = generarPlantillaHtml(cuerpo);
            helper.setText(htmlContent, true);

            ClassPathResource logo = new ClassPathResource(LOGO_PATH);
            helper.addInline(LOGO_IMAGE_PROPERTY, logo);

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new IllegalStateException(ERROR_AL_ENVIAR_CORREO, e);
        } catch (Exception e) {
            throw new IllegalStateException(ERROR_INESPERADO_EN_EL_SERVICIO_DE_ENVIAR_CORREO, e);
        }
    }

    private String generarPlantillaHtml(String codigo) {
        return String.format(
                PLANTILLA_HTML_RECUPERACION_CLAVE,
                codigo
        );
    }
}