package system.model;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * This class represents a database table for queries that include selecting mobile numbers by data plan.
 */
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class NumberPlan {

    @Positive @Min(1)
    private int dataplanID;

    @NotBlank
    private String phoneNumber;

}