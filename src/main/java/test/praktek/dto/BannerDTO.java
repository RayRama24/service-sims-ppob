package test.praktek.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerDTO {

    private String banner_name;
    private String banner_image;
    private String description;
}
