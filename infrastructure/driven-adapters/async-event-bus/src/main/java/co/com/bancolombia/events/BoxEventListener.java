package co.com.bancolombia.events;
import co.com.bancolombia.model.box.Box;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BoxEventListener {

    @EventListener
    public void handleBoxEvent(Box box) {
        log.info("Received Box event: {}", box);
    }
}