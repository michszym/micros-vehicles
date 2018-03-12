package com.michszym.vehicles.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.michszym.vehicles.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DynamoDBVehicleRepository implements VehicleRepository {

    @Autowired
    private DynamoDBMapper mapper;

    @Override
    public Iterable<Vehicle> findAll() {
        return mapper.scan(Vehicle.class, new DynamoDBScanExpression());
    }

    @Override
    public <S extends Vehicle> S save(S entity) {
        mapper.save(entity);
        return entity;
    }
}
