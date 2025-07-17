package pl.homeapp.homeapi.domain;

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
    private Long id;
    private String brand;
    private String model;
    private String operationSystem;

    @OneToMany(mappedBy = "command_id", cascade = CascadeType.ALL)
    private List<Command> sentCommands;
}
