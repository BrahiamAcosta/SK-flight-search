package com.sk.flightSearch.service;

import com.sk.flightSearch.domain.DTO.FlightDTO;
import com.sk.flightSearch.domain.FlightMapper;
import com.sk.flightSearch.domain.model.Flight;
import com.sk.flightSearch.repository.IFlightRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {
    @Mock
    private IFlightRepository flightRepository;

    @Mock
    private FlightMapper flightMapper;

    @InjectMocks
    private FlightService flightService;

    @Test
    void searchFlightByFlightNumber_Found(){
        //Arrange
        Flight flight = new Flight();
        flight.setFlightNumber("NY100");

        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setFlightNumber("NY100");

        when(flightRepository.findByFlightNumber("NY100")).thenReturn(Optional.of(flight));
        when(flightMapper.toDto(flight)).thenReturn(flightDTO);

        //Act
        FlightDTO result = flightService.searchFlightByFlightNumber("NY100");

        //Assert
        assertNotNull(result);
        assertEquals("NY100", result.getFlightNumber());
    }

    @Test
    void searchFlightByFlightNumber_NotFound(){
        //Arrange
        String nonExistentFlightNumber = "XYZ-123";
        when(flightRepository.findByFlightNumber(nonExistentFlightNumber)).thenReturn(Optional.empty());
        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            flightService.searchFlightByFlightNumber(nonExistentFlightNumber);
        });
    }

    @Test
    void searchFlightByFilters_Found(){
        //Arrange
        LocalDate departureDate = LocalDate.of(2025, 5, 15);
        Flight flight1 = new Flight();
        flight1.setFlightNumber("ABC-123");
        flight1.setOrigin("New York");
        flight1.setDestination("London");
        flight1.setDepartureDate(LocalDate.of(2025, 5, 15));
        flight1.setDepartureTime(LocalTime.of(15, 30));
        flight1.setAircraft("Boeing 747");
        flight1.setPrice(500.0f);

        Flight flight2 = new Flight();
        flight2.setFlightNumber("XYZ456");
        flight2.setOrigin("Paris");
        flight2.setDestination("Berlin");
        flight2.setDepartureDate(LocalDate.of(2025, 6, 10));
        flight2.setDepartureTime(LocalTime.of(10, 45));
        flight2.setAircraft("Airbus A320");
        flight2.setPrice(300.0f);

        FlightDTO flightDTO1 = new FlightDTO(
                "ABC-123",
                "New York",
                "London",
                LocalDate.of(2025, 5, 15),
                LocalTime.of(15, 30),
                "Boeing 747",
                500.0f
        );
        FlightDTO flightDTO2 = new FlightDTO(
                "XYZ-456",
                "Paris",
                "Berlin",
                LocalDate.of(2025, 6, 10),
                LocalTime.of(10, 45),
                "Airbus A320",
                300.0f
        );

        when(flightRepository.findAll(any(Specification.class))).thenReturn(List.of(flight1,flight2));
        when(flightMapper.toDto(flight1)).thenReturn(flightDTO1);
        when(flightMapper.toDto(flight2)).thenReturn(flightDTO2);

        //Act
        List<FlightDTO> result = flightService.searchFlightByFilters(null,null,null,null);

        //Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("ABC-123",result.get(0).getFlightNumber());
        assertEquals("XYZ-456",result.get(1).getFlightNumber());
    }
    @Test
    void searchFlightByFilters_NotFound(){
        //Arrange
        String nonExistentOrigin = "Kenya";
        when(flightRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

        // Act:
        List<FlightDTO> result = flightService.searchFlightByFilters(nonExistentOrigin, null, null, null);

        // Assert
        assertTrue(result.isEmpty());
    }
    @Test
    void searchFlightByFilters_AllFilters(){
        //Act
        String origin = "New York";
        String destination = "London";
        Float price = 500.0f;
        LocalDate departureDate = LocalDate.of(2025, 5, 15);

        Flight flight = new Flight();
        flight.setFlightNumber("ABC-123");
        flight.setOrigin("New York");
        flight.setDestination("London");
        flight.setDepartureDate(LocalDate.of(2025, 5, 15));
        flight.setDepartureTime(LocalTime.of(15, 30));
        flight.setAircraft("Boeing 747");
        flight.setPrice(500.0f);

        FlightDTO flightDTO = new FlightDTO(
                "ABC-123",
                "New York",
                "London",
                LocalDate.of(2025, 5, 15),
                LocalTime.of(15, 30),
                "Boeing 747",
                500.0f
        );

        when(flightRepository.findAll(any(Specification.class))).thenReturn(List.of(flight));
        when(flightMapper.toDto(flight)).thenReturn(flightDTO);

        //Act
        List<FlightDTO> result = flightService.searchFlightByFilters(origin,destination,price,departureDate);

        //Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(flightDTO.getFlightNumber(), result.get(0).getFlightNumber());
        assertEquals(flightDTO.getOrigin(), result.get(0).getOrigin());
        assertEquals(flightDTO.getDestination(), result.get(0).getDestination());
        assertEquals(flightDTO.getDepartureDate(), result.get(0).getDepartureDate());
        assertEquals(flightDTO.getPrice(), result.get(0).getPrice());
    }
}
