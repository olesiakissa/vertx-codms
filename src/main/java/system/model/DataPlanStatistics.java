package system.model;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * This class represents a database table for queries that include percent of clients per each data plan in certain country region.
 */
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class DataPlanStatistics {

    @Positive
    @Min(1)
    private int regionID;

    @NotBlank
    private String regionName;

    @Positive @Min(1)
    private int dataplanID;

}