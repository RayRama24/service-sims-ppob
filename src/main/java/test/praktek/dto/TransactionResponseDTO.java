package test.praktek.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponseDTO {

    private String invoiceNumber;
    private String serviceCode;
    private String serviceName;
    private String transactionType;
    private Long totalAmount;
    private LocalDateTime createdOn;
}
