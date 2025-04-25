package com.sk.flightSearch.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sk.flightSearch.domain.DTO.FlightDTO;
import com.sk.flightSearch.service.FlightService;

@RestController
@RequestMapping("/api/flights")
@CrossOrigin(origins = "*")
public class FlightController {
    private final FlightService flightService;
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<FlightDTO>> searchByFilters(
        @RequestParam(required = false) String origin,
        @RequestParam(required = false) String destination,
        @RequestParam(required = false) Float price,
        @RequestParam(required = false) @DateTimeFormat() LocalDate departureDate
    ){
        List<FlightDTO> flights = flightService.searchFlightByFilters(origin, destination, price, departureDate);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/searchByFlightNumber")
    public ResponseEntity<FlightDTO> searchByFlightNumber(String flightNumber){
        FlightDTO flight = flightService.searchFlightByFlightNumber(flightNumber);
        return ResponseEntity.ok(flight);
    }
}
