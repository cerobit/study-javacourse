package co.com.bancolombia.model.box;
import co.com.bancolombia.model.boxstatus.BoxStatus;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Box {
    private String id;                // ID de la caja
    private String name;              // Nombre o número de caja
    private BoxStatus status;         // Estado: OPENED, CLOSED
    private BigDecimal openingAmount; // Monto de apertura
    private BigDecimal closingAmount; // Monto de cierre
    private LocalDateTime openedAt;   // Fecha y hora de apertura
    private LocalDateTime closedAt;   // Fecha y hora de cierre
    private BigDecimal currentBalance;// Saldo actual de la caja
}
