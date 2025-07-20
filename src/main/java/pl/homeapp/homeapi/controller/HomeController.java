package pl.homeapp.homeapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.homeapp.homeapi.domain.CoomandHistory;
import pl.homeapp.homeapi.domain.RemoteDevice;
import pl.homeapp.homeapi.dto.ApiCommandDTO;
import pl.homeapp.homeapi.exception.ResourceNotFoundException;
import pl.homeapp.homeapi.repository.CommandHistoryRepository;
import pl.homeapp.homeapi.repository.RemoteDeviceRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/home")
public class HomeController {

    private final CommandHistoryRepository commandHistoryRepository;
    private final RemoteDeviceRepository remoteDeviceRepository;

    public HomeController(CommandHistoryRepository commandHistoryRepository, RemoteDeviceRepository remoteDeviceRepository) {
        this.commandHistoryRepository = commandHistoryRepository;
        this.remoteDeviceRepository = remoteDeviceRepository;
    }

    @Operation(summary = "Find Command by ID", description = "Returns a single Command", tags = {"Command"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found",
                content = @Content(schema = @Schema(implementation = CoomandHistory.class))),
            @ApiResponse(responseCode = "404", description = "Command not found")
    })
    @GetMapping("/command/{id}")
    public CoomandHistory findCommandById(@PathVariable long id) {
        return commandHistoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Operation(summary = "Find all Remote Device", description = "Returns all remote devices", tags = {"All Remote Devices"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found",
                    content = @Content(schema = @Schema(implementation = RemoteDevice.class))),
            @ApiResponse(responseCode = "404", description = "No remote devices")
    })
    @GetMapping("/remote-device/all")
    public List<RemoteDevice> findAllRemoteDevices() {
        return Optional.of(remoteDeviceRepository.findAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("No remote devices found"));
    }

    @Operation(summary = "Find Remote Device by ID", description = "Returns a single remote device", tags = {"Remote Device"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found",
                    content = @Content(schema = @Schema(implementation = RemoteDevice.class))),
            @ApiResponse(responseCode = "404", description = "Remote Device not found")
    })
    @GetMapping("/remote-device/{id}")
    public RemoteDevice findRemoteDeviceById(@PathVariable long id) {
        return remoteDeviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @PostMapping("/remote-devices")
    public ResponseEntity<RemoteDevice> createRemoteDevice(@RequestBody RemoteDevice device) {
        RemoteDevice saved = remoteDeviceRepository.save(device);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/sendCommand")
    public ResponseEntity<?> sendCommandViaApi(@RequestBody ApiCommandDTO dto) {
        RemoteDevice remoteDevice = remoteDeviceRepository.findById(dto.getRemoteDeviceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RemoteDevice not found"));

        CoomandHistory command = new CoomandHistory();
        command.setBody(dto.getBody());
        command.setRemoteDevice(remoteDevice);
        command.setUserDevice(null); // <--- to ustawienie jest domyślne, ale możesz jawnie wpisać

        commandHistoryRepository.save(command);

        return ResponseEntity.ok("Command sent via API.");
    }
}
