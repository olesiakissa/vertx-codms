package system.model;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * This class represents an object container that will be displayed on the client side of application.
 */
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class ClientDisplayInfo {

    @Positive @Min(1)
    private int id;

    @NotBlank
    private String fullName;

    @NotBlank
    private String passportCode;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String regionName;

    @NotBlank
    private String dataplanTitle;

}