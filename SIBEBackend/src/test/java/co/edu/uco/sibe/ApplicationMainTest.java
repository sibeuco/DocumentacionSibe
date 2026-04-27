package co.edu.uco.sibe;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.mockito.Mockito.mock;

class ApplicationMainTest {

    @Test
    void deberiaEjecutarMain() {
        try (MockedStatic<SpringApplication> mockedStatic = Mockito.mockStatic(SpringApplication.class)) {
            mockedStatic.when(() -> SpringApplication.run(Application.class, new String[]{}))
                    .thenReturn(mock(ConfigurableApplicationContext.class));
            Application.main(new String[]{});
            mockedStatic.verify(() -> SpringApplication.run(Application.class, new String[]{}));
        }
    }
}
