package com.sk.flightSearch.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.sk.flightSearch.domain.DTO.FlightDTO;
import com.sk.flightSearch.domain.model.Flight;

@Component
public class FlightMapper {
    public FlightDTO toDto(Flight flight){
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setId(flight.getId());
        flightDTO.setFlightNumber(flight.getFlightNumber());
        flightDTO.setOrigin(flight.getOrigin());
        flightDTO.setDestination(flight.getDestination());
        flightDTO.setDepartureDate(flight.getDepartureDate().toString());
        flightDTO.setDepartureTime(flight.getDepartureTime().toString());
        flightDTO.setAircraft(flight.getAircraft());
        flightDTO.setPrice(flight.getPrice());
        return flightDTO;
    }
    public Flight toEntity(FlightDTO flightDTO){
        Flight flight = new Flight();
        flight.setId(flightDTO.getId());
        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setOrigin(flightDTO.getOrigin());
        flight.setDestination(flightDTO.getDestination());
        flight.setDepartureDate(LocalDate.parse(flightDTO.getDepartureDate()));
        flight.setDepartureTime(LocalTime.parse(flightDTO.getDepartureTime()));
        flight.setAircraft(flightDTO.getAircraft());
        flight.setPrice(flightDTO.getPrice());
        return flight;
    }
}
