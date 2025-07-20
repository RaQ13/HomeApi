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
import pl.homeapp.homeapi.domain.CommandHistory;
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

    /** ***             Get methods            *** */

    /**
     * @return single {@link CommandHistory} by id.
     * */
    @Operation(summary = "Find Command history element by ID", description = "Returns a single Command from history", tags = {"Command history"}, operationId = "GetSingleCommand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found",
                content = @Content(schema = @Schema(implementation = CommandHistory.class))),
            @ApiResponse(responseCode = "404", description = "Command not found")
    })
    @GetMapping("/comman-history/{id}")
    public CommandHistory findCommandById(@PathVariable long id) {
        return commandHistoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    /**
     * @return <List> of all {@link CommandHistory}.
     * */
    @Operation(summary = "Find all Command history elements", description = "Returns all commands history", tags = {"Command history"}, operationId = "GetAllCommand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found",
                    content = @Content(schema = @Schema(implementation = CommandHistory.class))),
            @ApiResponse(responseCode = "404", description = "No commands found")
    })
    @GetMapping("/command-history/all")
    public List<CommandHistory> findAllCommandsHistory() {
        return Optional.of(commandHistoryRepository.findAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("No remote devices found"));
    }

    /**
     * @return single {@link RemoteDevice} by id.
     * */
    @Operation(summary = "Find Remote Device by ID", description = "Returns a single remote device", tags = {"Remote Device"}, operationId = "GetSingleRemoteDevice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found",
                    content = @Content(schema = @Schema(implementation = RemoteDevice.class))),
            @ApiResponse(responseCode = "404", description = "Remote Device not found")
    })
    @GetMapping("/remote-device/{id}")
    public RemoteDevice findRemoteDeviceById(@PathVariable long id) {
        return remoteDeviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    /**
     * @return <List> of all {@link RemoteDevice}.
     * */
    @Operation(summary = "Find all Remote Device", description = "Returns all remote devices", tags = {"Remote Device"}, operationId = "GetAllRemoteDevices")
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

    /** ***             Post methods            *** */

    /**
     * Creates new resource of {@link RemoteDevice} and saves it in {@link RemoteDeviceRepository}
     *
     * @param device - Object {@link RemoteDevice} to save, passed for save in JSON format
     * @return {@link ResponseEntity} with saved object {@link RemoteDevice} and HTTP status.
     */
    @Operation(operationId = "PostRemoteDevice", tags = {"Remote Device"})
    @PostMapping("/remote-devices")
    public ResponseEntity<RemoteDevice> createRemoteDevice(@RequestBody RemoteDevice device) {
        RemoteDevice saved = remoteDeviceRepository.save(device);
        return ResponseEntity.ok(saved);
    }

    /**
     * Creates new resource of {@link CommandHistory} based on data from object {@link ApiCommandDTO}
     * and saves it in {@link CommandHistoryRepository}. Resource is linked with existing {@link RemoteDevice}
     *
     * @param dto - Object {@link RemoteDevice} containing command data for save passed in JSON format.
     * @return {@link ResponseEntity} with saved object {@link CommandHistory}, text message and HTTP status.
     */
    @Operation(operationId = "PostSendCommand", tags = {"Command history"})
    @PostMapping("/send-command")
    public ResponseEntity<?> sendCommandViaApi(@RequestBody ApiCommandDTO dto) {
        RemoteDevice remoteDevice = remoteDeviceRepository.findById(dto.getRemoteDeviceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RemoteDevice not found"));

        CommandHistory command = new CommandHistory();
        command.setBody(dto.getBody());
        command.setRemoteDevice(remoteDevice);
        command.setUserDevice(null);

        commandHistoryRepository.save(command);

        return ResponseEntity.ok("Command sent via API.");
    }
}
