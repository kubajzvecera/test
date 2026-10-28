package com.example.vis_reservation.domain.mapper;

import com.example.vis_reservation.data.entity.CustomerEntity;
import com.example.vis_reservation.domain.Customer;

public class CustomerMapper {

    public static Customer toDomain(CustomerEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Customer(
                entity.getId(),
                entity.getFname(),
                entity.getLname(),
                entity.getPhone(),
                entity.getEmail(),
                AddressMapper.toDomain(entity.getAddress())
        );
    }

    public static CustomerEntity toEntity(Customer domain) {
        if (domain == null) {
            return null;
        }
        return new CustomerEntity(
                domain.getId(),
                domain.getFname(),
                domain.getLname(),
                domain.getPhone(),
                domain.getEmail(),
                AddressMapper.toEntity(domain.getAddress())
        );
    }
}
