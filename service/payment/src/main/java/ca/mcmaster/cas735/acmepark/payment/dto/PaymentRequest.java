package ca.mcmaster.cas735.acmepark.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    private String userType;

    private String licensePlate;

    private Long lotId;

    private int userId;

    private String permitType;

    private UUID transponderNumber;

    private LocalDateTime validFrom;

    private LocalDateTime validUntil;

    private String paymentMethod;

    private String payrollNum;

    private BigDecimal amount;

    private LocalDateTime entryTime;

    private BigDecimal hourlyRate;

    private boolean result;

    private String gateId;

    private int permitId;

}
