package system.model;

import lombok.*;

/**
 * This class represents a system admin with predetermined credentials.
 */
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
public class Admin {

    private int id;

    private String name;

    private String password;

    private String adminCredentials;

}