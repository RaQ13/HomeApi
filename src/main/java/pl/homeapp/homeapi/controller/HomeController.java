package pl.homeapp.homeapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jdk.jfr.ContentType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.homeapp.homeapi.domain.Command;
import pl.homeapp.homeapi.exception.ResourceNotFoundException;
import pl.homeapp.homeapi.repository.CommandRepository;

@RestController
@RequestMapping("/home")
public class HomeController {
    private final CommandRepository commandRepository;
    public HomeController(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    @Operation(summary = "Find Command by ID", description = "Returns a single Command", tags = {"command"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found",
                content = @Content(schema = @Schema(implementation = Command.class))),
            @ApiResponse(responseCode = "404", description = "Command not found")
    })
    @GetMapping("/command/{id}")
    public Command findCommandById(@PathVariable long id) {
        return commandRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
