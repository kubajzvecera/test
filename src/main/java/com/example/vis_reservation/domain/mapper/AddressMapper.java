package com.example.vis_reservation.domain.mapper;

import com.example.vis_reservation.data.entity.AddressEntity;
import com.example.vis_reservation.domain.Address;

public class AddressMapper {

    public static Address toDomain(AddressEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Address(
                entity.getId(),
                entity.getStreet(),
                entity.getCity(),
                entity.getHouseNumber(),
                entity.getZipCode()
        );
    }

    public static AddressEntity toEntity(Address domain) {
        if (domain == null) {
            return null;
        }
        return new AddressEntity(
                domain.getId(),
                domain.getStreet(),
                domain.getCity(),
                domain.getHouseNumber(),
                domain.getZipCode()
        );
    }
}
