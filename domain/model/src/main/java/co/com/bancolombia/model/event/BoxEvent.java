package co.com.bancolombia.model.event;

import co.com.bancolombia.model.box.Box;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoxEvent {
    private String operation;
    private String result;
    private Box box;
}