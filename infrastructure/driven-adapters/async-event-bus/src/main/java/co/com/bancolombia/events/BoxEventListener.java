package co.com.bancolombia.events;
import co.com.bancolombia.model.event.BoxEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BoxEventListener {

    @EventListener
    public void handleBoxEvent(BoxEvent boxEvent) {
        log.info("Received Box event: {}", boxEvent);
    }

}