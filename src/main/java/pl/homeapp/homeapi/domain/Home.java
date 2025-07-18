package pl.homeapp.homeapi.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Home {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique ID of a home", example = "1", required = true)
    private Long id;
    @Schema(description = "A name of home", example = "My home", required = false)
    private String name;
    @Schema(description = "Description of a home", example = "My home", required = false)
    private String description;

    @OneToMany(mappedBy = "home", cascade = CascadeType.ALL)
    private List<Room> rooms;
}
