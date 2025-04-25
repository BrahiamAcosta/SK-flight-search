package com.sk.flightSearch.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.sk.flightSearch.domain.model.Flight;

public class FlightSpecifications {
    public static Specification<Flight> filterByOrigin (String origin){
        return (root, query, cb) -> {
            return origin == null ? null : cb.equal(root.get("origin"), origin);
        };
    }    

    public static Specification<Flight> filterByDestination (String destination){
        return (root, query, cb) -> {
            return destination == null ? null : cb.equal(root.get("destination"),destination);
        };
    }

    public static Specification<Flight> filterByPrice (Float price){
        return (root,query,cb) -> {
            return price == null ? null : cb.lessThanOrEqualTo(root.get("price"), price);
        };
    }

    public static Specification<Flight> filterByDepartureDate (LocalDate departureDate){
        return (root,query,cb)->{
            return departureDate == null ? null : cb.greaterThanOrEqualTo(root.get("departureDate"), departureDate);
        };
    }
}
