package test.praktek.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO {

    private String serviceCode;
    private String serviceName;
    private String serviceIcon;
    private Long serviceTarif;
}
