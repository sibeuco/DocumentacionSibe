package co.edu.uco.sibe.infraestructura.configuracion.dataloader;

import org.springframework.boot.CommandLineRunner;

public abstract class DataLoader implements CommandLineRunner {
    @Override
    public final void run(String... args) throws Exception {
        if (!debenCargarseDatos()) {
            cargarDatos();
        }
    }

    protected abstract boolean debenCargarseDatos();

    protected abstract void cargarDatos();
}