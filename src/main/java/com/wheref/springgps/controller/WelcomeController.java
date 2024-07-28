package com.wheref.springgps.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wheref.springgps.model.Coordinates;
import com.wheref.springgps.model.Geometry;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class WelcomeController {

    @Value("classpath:static/trajectory.csv")
    Resource trajectory;

    private Coordinates co;

    int fileLineNumber = 0;
    
    @Scheduled(fixedRate = 1000) // Milliseconds
    public void readLinesPeriodically() throws IOException {
        System.out.println("fileLineNumber: "+ fileLineNumber);
        String content = trajectory.getContentAsString(StandardCharsets.UTF_8);
        String[] lines = content.split("\n");
        if (lines.length==0) return;
        String[] arr = StringUtils.split(lines[fileLineNumber], ","); 

        Coordinates coordinates = new Coordinates();
        coordinates.setLat(Float.parseFloat(arr[0]));
        coordinates.setLng(Float.parseFloat(arr[1]));
        this.co = coordinates;

        fileLineNumber++;
        fileLineNumber = fileLineNumber % lines.length;
    }

    @GetMapping("/coordinate")
	public Geometry coordinate() {
        Map<String, Float> coordinates = new HashMap<>();
        System.out.println("co: "+ co);
        if (this.co!=null){
            coordinates.put("lat", this.co.getLat());
            coordinates.put("lng", this.co.getLng());
        } else {
            coordinates.put("lat", 3.1390f);
            coordinates.put("lng", 101.6869f);
        }
        return new Geometry("Point", coordinates);
	}

    @PostMapping(path="/postCoordinate", consumes = "application/json", produces = "application/json")
	public Coordinates postcoordinate(@RequestBody Coordinates coordinates) {
        System.out.println("coordinates: "+ coordinates.getLat()+", "+ coordinates.getLng());
        this.co = coordinates;
        return coordinates;
    }
}
