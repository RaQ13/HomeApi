package pl.homeapp.homeapi.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of command wchich can be used for remote devices", example = "1", required = true)
    private Long id;
    @Schema(description = "Body of command", example = "'setState: {power: off}'", required = true)
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_device_id")
    private UserDevice userDevice;

    @ManyToOne
    @JoinColumn(name = "remote_device_id")
    private RemoteDevice remoteDevice;
}
