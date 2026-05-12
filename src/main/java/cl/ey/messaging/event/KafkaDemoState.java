package cl.ey.messaging.event;

import org.springframework.stereotype.Component;

@Component
public class KafkaDemoState {

    private String lastProducedEvent;
    private String lastConsumedEvent;

    public void setLastProducedEvent(String event) {
        this.lastProducedEvent = event;
    }

    public void setLastConsumedEvent(String event) {
        this.lastConsumedEvent = event;
    }

    public String getLastProducedEvent() {
        return lastProducedEvent;
    }

    public String getLastConsumedEvent() {
        return lastConsumedEvent;
    }
}
