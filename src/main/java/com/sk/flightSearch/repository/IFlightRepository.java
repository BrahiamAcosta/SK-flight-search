package com.sk.flightSearch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sk.flightSearch.domain.model.Flight;

public interface IFlightRepository extends JpaRepository<Flight, Long>, JpaSpecificationExecutor<Flight>{
    Optional<Flight> findByFlightNumber(String flightNumber);
}
