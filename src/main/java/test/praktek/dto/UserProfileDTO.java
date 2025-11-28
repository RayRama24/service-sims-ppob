package test.praktek.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserProfileDTO {

    private String email;
    private String first_name;
    private String last_name;
//    private String profile_image;
}
