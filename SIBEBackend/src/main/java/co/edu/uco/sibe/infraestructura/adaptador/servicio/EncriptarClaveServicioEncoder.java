package co.edu.uco.sibe.infraestructura.adaptador.servicio;

import co.edu.uco.sibe.dominio.puerto.servicio.EncriptarClaveServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncriptarClaveServicioEncoder implements EncriptarClaveServicio {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String ejecutar(String contrasena) {
        return this.passwordEncoder.encode(contrasena);
    }

    @Override
    public boolean existe(String contrasena, String contrasenaEncriptada) {
        return this.passwordEncoder.matches(contrasena, contrasenaEncriptada);
    }
}