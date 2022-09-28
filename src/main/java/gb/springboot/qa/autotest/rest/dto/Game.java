package gb.springboot.qa.autotest.rest.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Game {
    private int id;
    private String name;
    private String genre;
    private boolean mmo;
}
