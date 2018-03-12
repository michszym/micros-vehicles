package com.michszym.vehicles.rest;

import com.michszym.vehicles.model.Vehicle;
import com.michszym.vehicles.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/vehicles")
public class VehicleRestController {

    @Qualifier("dynamoDBVehicleRepository")
    @Autowired
    private VehicleRepository repository;

    @Value("${server.name}")
    private String serverName;

    @CrossOrigin
    @RequestMapping(value = {""}, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Vehicle>> getVehicles() {
        final List<Vehicle> vehicles = StreamSupport.stream(repository.findAll().spliterator(), false)
                                              .collect(Collectors.toList());

        return new ResponseEntity<>(vehicles, headers(), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Vehicle> postVehicle(@RequestBody Vehicle vehicle) {
        final Vehicle result = repository.save(vehicle);
        return new ResponseEntity<>(result, headers(), HttpStatus.OK);
    }

    private LinkedMultiValueMap<String, String> headers() {
        final LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("server-name", Collections.singletonList(serverName));
        return headers;
    }

}
