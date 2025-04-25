package com.sk.flightSearch.service;

import java.time.LocalDate;
import java.util.List;

import com.sk.flightSearch.domain.DTO.FlightDTO;

public interface IFlightService {
    FlightDTO searchFlightByFlightNumber (String flightNumber);
    List<FlightDTO> searchFlightByFilters (String origin, String destination, Float price, LocalDate departureDate);
}
