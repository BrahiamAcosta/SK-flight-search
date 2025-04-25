package com.sk.flightSearch.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sk.flightSearch.domain.FlightMapper;
import com.sk.flightSearch.domain.DTO.FlightDTO;
import com.sk.flightSearch.domain.model.Flight;
import com.sk.flightSearch.repository.FlightSpecifications;
import com.sk.flightSearch.repository.IFlightRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FlightService implements IFlightService{
    private final IFlightRepository flightRepository;
    private final FlightMapper flightMapper;

    public FlightService(IFlightRepository flightRepository, FlightMapper flightMapper) {
        this.flightRepository = flightRepository;
        this.flightMapper = flightMapper;
    }

    @Override
    public FlightDTO searchFlightByFlightNumber(String flightNumber){
        Flight flight = flightRepository.findByFlightNumber(flightNumber).orElseThrow(() -> new EntityNotFoundException(String.format("Flight with number %s not found", flightNumber)));
        return flightMapper.toDto(flight);
    }

    @Override
    public List<FlightDTO> searchFlightByFilters(String origin, String destination, Float price, LocalDate departureDate){
        Specification<Flight> specification = Specification.where(null);
        if(origin != null){
            specification = specification.and(FlightSpecifications.filterByOrigin(origin));
        }
        if(destination != null){
            specification = specification.and(FlightSpecifications.filterByDestination(destination));
        }
        if(price != null){
            specification = specification.and(FlightSpecifications.filterByPrice(price));
        }
        if(departureDate != null){
            specification = specification.and(FlightSpecifications.filterByDepartureDate(departureDate));
        }
        return flightRepository.findAll(specification).stream()
                .map(flight -> flightMapper.toDto(flight))
                .collect(Collectors.toList());
    }
}
