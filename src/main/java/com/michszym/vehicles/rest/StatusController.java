package com.michszym.vehicles.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/status")
public class StatusController {

    @Value("${server.name}")
    private String serverName;

    @CrossOrigin
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public ResponseEntity status() {
        return new ResponseEntity(headers(), HttpStatus.OK);
    }

    private LinkedMultiValueMap<String, String> headers() {
        final LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("server-name", Collections.singletonList(serverName));
        return headers;
    }

}
