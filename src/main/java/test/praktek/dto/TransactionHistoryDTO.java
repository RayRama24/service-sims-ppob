package test.praktek.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionHistoryDTO {

    private Integer offset;
    private Integer limit;
    private List<TransactionResponseDTO> records;
}
