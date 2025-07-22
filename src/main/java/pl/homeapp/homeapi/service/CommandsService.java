package pl.homeapp.homeapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class CommandsService {
    private final ResourceLoader resourceLoader;

    public CommandsService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

//    public List<String> listAvailableCommands() throws IOException {
    public Map<String, Object> listAvailableCommands() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:static/commands/*.json");

//        return Arrays.stream(resources)
//                .map(resource -> resource.getFilename())
//                .collect(Collectors.toList());
//
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Resource[] resources = resolver.getResources("classpath:static/commands/*.json");

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> commandMap = new LinkedHashMap<>();
        for (Resource resource : resources) {
            String name = resource.getFilename();
            try (InputStream is = resource.getInputStream()) {
                Object parsedJson = objectMapper.readValue(is, Object.class);
                Map<String, Object> wrapped = new LinkedHashMap<>();
                wrapped.put("body", parsedJson);
                commandMap.put(name, wrapped);
            }
        }
        return commandMap;
    }
}
