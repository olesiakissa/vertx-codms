package system.model;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * This class represents a data plan that is provided by cellular company.
 */
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString

public class DataPlan {

    @Positive @Min(1)
    private int dataplanID;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

}