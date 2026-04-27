package co.edu.uco.sibe.infraestructura.configuracion.bean;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class EncriptarClaveBeanTest {

    @Test
    void deberiaCrearPasswordEncoderBCrypt() {
        EncriptarClaveBean bean = new EncriptarClaveBean();
        PasswordEncoder encoder = bean.EncriptarClave();

        assertNotNull(encoder);
        assertInstanceOf(BCryptPasswordEncoder.class, encoder);
    }
}
