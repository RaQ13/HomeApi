package pl.homeapp.homeapi.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class UserDevice {
    @Id
    @GeneratedValue
    @Schema(description = "Unique identifier of user device", example = "1", required = true)
    private Long id;
    @Schema(description = "A brand of user device", example = "Samsung", required = false)
    private String brand;
    @Schema(description = "A model of a user devices", example = "Galaxy A52", required = false)
    private String model;
    @Schema(description = "A operation system of a user devices", example = "Android 15", required = false)
    private String operationSystem;

    @OneToMany(mappedBy = "userDevice", cascade = CascadeType.ALL)
    private List<Command> sentCommands;
}