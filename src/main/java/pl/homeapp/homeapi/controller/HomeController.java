package pl.homeapp.homeapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.homeapp.homeapi.domain.CommandHistory;
import pl.homeapp.homeapi.domain.Home;
import pl.homeapp.homeapi.domain.RemoteDevice;
import pl.homeapp.homeapi.domain.Room;
import pl.homeapp.homeapi.dto.ApiCommandDTO;
import pl.homeapp.homeapi.exception.ResourceNotFoundException;
import pl.homeapp.homeapi.repository.CommandHistoryRepository;
import pl.homeapp.homeapi.repository.HomeRepository;
import pl.homeapp.homeapi.repository.RemoteDeviceRepository;
import pl.homeapp.homeapi.repository.RoomRepository;
import pl.homeapp.homeapi.service.CommandsService;
import pl.homeapp.homeapi.utils.UdpClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/home")
public class HomeController {

    private final CommandHistoryRepository commandHistoryRepository;
    private final HomeRepository homeRepository;
    private final RemoteDeviceRepository remoteDeviceRepository;
    private final RoomRepository roomRepository;

    private final CommandsService commandFileService;

    public HomeController(CommandHistoryRepository commandHistoryRepository, HomeRepository homeRepository, RemoteDeviceRepository remoteDeviceRepository, RoomRepository roomRepository, CommandsService commandFileService) {
        this.commandHistoryRepository = commandHistoryRepository;
        this.homeRepository = homeRepository;
        this.remoteDeviceRepository = remoteDeviceRepository;
        this.roomRepository = roomRepository;
        this.commandFileService = commandFileService;
    }

    /** ***             Get methods            *** */

    /**
     * @return single {@link CommandHistory} by id.
     * */
    @Operation(summary = "Find Command history element by ID", description = "Returns a single Command from history", tags = {"Commands"}, operationId = "GetSingleCommand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found",
                content = @Content(schema = @Schema(implementation = CommandHistory.class))),
            @ApiResponse(responseCode = "404", description = "Command not found",
                content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/commands-history/{id}")
    public CommandHistory findCommandById(@PathVariable long id) {
        return commandHistoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    /**
     * @return <List> of all {@link CommandHistory}.
     * */
    @Operation(summary = "Find all Command history elements", description = "Returns all commands history", tags = {"Commands"}, operationId = "GetAllCommand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found",
                    content = @Content(schema = @Schema(implementation = CommandHistory.class))),
            @ApiResponse(responseCode = "404", description = "No commands found",
                    content = @Content(schema = @Schema(hidden = true))),
    })
    @GetMapping("/commands-history/all")
    public List<CommandHistory> findAllCommandsHistory() {
        return Optional.of(commandHistoryRepository.findAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("No commands in history found"));
    }

    /**
     * Shows a <List> of all avaiable commands exisitng in static/commands
     */
    @Operation(summary = "View all avaiable commands", description = "Returns a list of all avaiable commans", tags = {"Commands"}, operationId = "GetListOfCommands")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found",
                    content = @Content(schema = @Schema(implementation = ApiCommandDTO.class))),
            @ApiResponse(responseCode = "404", description = "No commands found",
                    content = @Content(schema = @Schema(hidden = true))),
    })
    @GetMapping("/available-commands")
    public Map<String, Object > listCommands() throws IOException {
        return commandFileService.listAvailableCommands();
    }

    /**
     * @return single {@link Home} by id.
     * */
    @Operation(summary = "Find Home by ID", description = "Returns a single home", tags = {"Home"}, operationId = "GetSingleHome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found",
                    content = @Content(schema = @Schema(implementation = Home.class))),
            @ApiResponse(responseCode = "404", description = "Home not found",
                    content = @Content(schema = @Schema(hidden = true))),
    })
    @GetMapping("/home/{id}")
    public Home findHomeById(@PathVariable long id) {
        return homeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    /**
     * @return <List> of all {@link Home}.
     * */
    @Operation(summary = "Find all saved Homes", description = "Returns all homes", tags = {"Home"}, operationId = "GetAllHomes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found",
                    content = @Content(schema = @Schema(implementation = Home.class))),
            @ApiResponse(responseCode = "404", description = "No homes saved",
                    content = @Content(schema = @Schema(hidden = true))),
    })
    @GetMapping("/home/all")
    public List<Home> findAllHomes() {
        return Optional.of(homeRepository.findAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("No saved homes found"));
    }

    /**
     * @return single {@link RemoteDevice} by id.
     * */
    @Operation(summary = "Find Remote Device by ID", description = "Returns a single remote device", tags = {"Remote Device"}, operationId = "GetSingleRemoteDevice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found",
                    content = @Content(schema = @Schema(implementation = RemoteDevice.class))),
            @ApiResponse(responseCode = "404", description = "Remote Device not found",
                    content = @Content(schema = @Schema(hidden = true))),
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
            @ApiResponse(responseCode = "404", description = "No remote devices",
                    content = @Content(schema = @Schema(hidden = true))),
    })
    @GetMapping("/remote-device/all")
    public List<RemoteDevice> findAllRemoteDevices() {
        return Optional.of(remoteDeviceRepository.findAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("No remote devices found"));
    }

    /**
     * @return single {@link Room} by id.
     * */
    @Operation(summary = "Find Room by ID", description = "Returns a single room", tags = {"Room"}, operationId = "GetSingleRoom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found",
                    content = @Content(schema = @Schema(implementation = Room.class))),
            @ApiResponse(responseCode = "404", description = "Remote Device not found",
                    content = @Content(schema = @Schema(hidden = true))),
    })
    @GetMapping("/room/{id}")
    public Room findRoomById(@PathVariable long id) {
        return roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    /**
     * @return <List> of all {@link Room}.
     * */
    @Operation(summary = "Find all Rooms declared in home", description = "Returns all rooms in home", tags = {"Room"}, operationId = "GetAllRooms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found",
                    content = @Content(schema = @Schema(implementation = Room.class))),
            @ApiResponse(responseCode = "404", description = "No remote devices",
                    content = @Content(schema = @Schema(hidden = true))),
    })
    @GetMapping("/room/all")
    public List<Room> findAllRooms() {
        return Optional.of(roomRepository.findAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("No rooms found"));
    }

    /** ***             Post methods            *** */

    /**
     * Creates new resource of {@link RemoteDevice} and saves it in {@link RemoteDeviceRepository}
     *
     * @param device - Object {@link RemoteDevice} to save, passed for save in JSON format
     * @return {@link ResponseEntity} with saved object {@link RemoteDevice} and HTTP status.
     */
    @Operation(summary = "Create remote device", operationId = "PostRemoteDevice", tags = {"Remote Device"})
    @PostMapping("/remote-devices")
    public ResponseEntity<RemoteDevice> createRemoteDevice(
            @RequestBody RemoteDevice device) {
        RemoteDevice saved = remoteDeviceRepository.save(device);
        return ResponseEntity.ok(saved);
    }

    /**
     * Creates new resource of {@link CommandHistory} based on data from object {@link ApiCommandDTO}
     * and saves it in {@link CommandHistoryRepository}. Resource is linked with existing {@link RemoteDevice}
     *
     * @param request - Object {@link RemoteDevice} containing command data for save passed in JSON format.
     * @return {@link ResponseEntity} with saved object {@link CommandHistory}, text message and HTTP status.
     */
    @Operation(summary= "Send command", operationId = "PostSendCommand", tags = {"Commands"}, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    examples = @ExampleObject(
                            name = "Example turn off command",
                            summary = "Sample payload for WiZ bulb",
                            value= """
                                    {
                                       "remoteDeviceId": 3,
                                       "body": { \s
                                        "method": "setState",
                                        "params": {"state": "false"}
                                       }
                                     }
                                   """
                    )
            )
    ))
    @PostMapping("/send-command")
    public ResponseEntity<?> sendCommandViaApi(@RequestBody ApiCommandDTO request) throws JsonProcessingException {
        RemoteDevice remoteDevice = remoteDeviceRepository.findById(request.getRemoteDeviceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RemoteDevice not found"));

        String stringifiedCommand = new ObjectMapper().writeValueAsString(request.getBody());
//        System.out.println(stringified);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedNow = now.format(formatter);

        CommandHistory command = new CommandHistory();
        command.setBody(stringifiedCommand);
        command.setRemoteDevice(remoteDevice);
        command.setUserDevice(null);
        command.setTimeStamp(formattedNow);

        UdpClient.sendCommand(command.getBody(), remoteDevice.getIp());

        commandHistoryRepository.save(command);

        return ResponseEntity.ok("Command sent via API successfully.");
    }
}
