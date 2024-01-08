package com.feeham.playground;
import com.feeham.playground.controller.MonoController;
import com.feeham.playground.service.LanguageServiceMono;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class HelloControllerTest {

    private final LanguageServiceMono languageService = new LanguageServiceMono();
    private final MonoController helloController = new MonoController(languageService);

    @Test
    public void testSayHello() {
        StepVerifier.create(helloController.sayHello())
                .expectNextMatches(result -> result.equals("Hello from Java! and Hello from Python"))
                .verifyComplete();
    }
}
