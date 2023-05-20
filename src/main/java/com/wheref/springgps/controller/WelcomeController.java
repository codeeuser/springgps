package com.wheref.springgps.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
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
@RequestMapping("/api/welcome")
public class WelcomeController {

    @Value("classpath:files/trajectory.csv")
    Resource trajectory;

    private Coordinates co;

    int fileLineNumber = 0;
    

    @Scheduled(fixedRate = 1000) // Milliseconds
    public void readLinesPeriodically() throws IOException {
        System.out.println("fileLineNumber: "+ fileLineNumber);
        File file  = trajectory.getFile();
        List<String> lines = FileUtils.readLines(file, "UTF-8");
        String line = lines.get(fileLineNumber);
        String[] arr = StringUtils.split(line, ","); 

        Coordinates coordinates = new Coordinates();
        coordinates.setLat(Float.parseFloat(arr[0]));
        coordinates.setLng(Float.parseFloat(arr[1]));
        this.co = coordinates;

        fileLineNumber++;
        fileLineNumber = fileLineNumber % lines.size();
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
        Geometry geometry = new Geometry("Point", coordinates);
		return geometry;
	}

    @PostMapping(path="/postCoordinate", consumes = "application/json", produces = "application/json")
	public Coordinates postcoordinate(@RequestBody Coordinates coordinates) {
        System.out.println("coordinates: "+ coordinates.getLat()+", "+ coordinates.getLng());
        this.co = coordinates;
        return coordinates;
    }
}
