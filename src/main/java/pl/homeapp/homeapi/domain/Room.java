package pl.homeapp.homeapi.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of room", example = "1", required = true)
    private Long id;
    @Schema(description = "Name of room", example = "Bedroom", required = false)
    private String name;
    @Schema(description = "A description of room", example = "1st floor bedroom", required = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<RemoteDevice> remoteDevicesList;
}
