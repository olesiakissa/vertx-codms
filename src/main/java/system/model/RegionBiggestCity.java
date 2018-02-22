package system.model;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * This class represents the biggest city in a region.
 */
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class RegionBiggestCity {

    @Positive @Min(1)
    private int regionID;

    @NotBlank
    private String name;

    @Positive @Min(1)
    private long population;

}