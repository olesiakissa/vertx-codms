package system.model;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

/**
 * This class represents a color for each dataplan to create an interactive map of data plans in country.
 */
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class DataPlanColor {

    private static final String COLOR_CODE_PATTERN = "^#[a-fA-F0-9]{6}$";

    @Positive @Min(1)
    private int dataplanID;

    @NotBlank
    @Pattern(regexp = COLOR_CODE_PATTERN)
    private String colorCode;

}