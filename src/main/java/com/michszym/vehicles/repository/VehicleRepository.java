package com.michszym.vehicles.repository;

import com.michszym.vehicles.model.Vehicle;

public interface VehicleRepository {

    Iterable<Vehicle> findAll();

    <S extends Vehicle> S save(S entity);
}
