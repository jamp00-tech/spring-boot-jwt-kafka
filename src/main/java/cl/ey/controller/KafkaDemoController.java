package cl.ey.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ey.messaging.event.KafkaDemoState;

@RestController
@RequestMapping("/kafka")
public class KafkaDemoController {

    private final KafkaDemoState kafkaDemoState;

    public KafkaDemoController(KafkaDemoState kafkaDemoState) {
        this.kafkaDemoState = kafkaDemoState;
    }

    @GetMapping("/demo")
    public Map<String, Object> demo() {
        return Map.of(
            "topic", "user-created",
            "lastProducedEvent", kafkaDemoState.getLastProducedEvent(),
            "lastConsumedEvent", kafkaDemoState.getLastConsumedEvent(),
            "kafkaFlow", "producer -> topic -> consumer",
            "status", "OK"
        );
    }
}
