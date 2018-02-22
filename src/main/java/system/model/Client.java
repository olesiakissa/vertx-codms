package system.model;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * This class represents a client of a certain cellular company.
 */
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class Client {

    @Positive @Min(1)
    private int id;

    @NotBlank
    private String fullName;

    @Positive @Min(1)
    private int regionID;

    @Positive @Min(1)
    private int dataplanID;

    @NotBlank
    private String passportCode;

    @NotBlank
    private String phoneNumber;

}