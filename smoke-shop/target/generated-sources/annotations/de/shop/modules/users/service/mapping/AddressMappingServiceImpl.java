package de.shop.modules.users.service.mapping;

import de.shop.modules.users.domain.dto.InputAddressDto;
import de.shop.modules.users.domain.dto.OutputAddressDto;
import de.shop.modules.users.domain.entity.AddressEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-18T21:31:11+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Amazon.com Inc.)"
)
@Component
public class AddressMappingServiceImpl implements AddressMappingService {

    @Override
    public AddressEntity mapDtoToEntity(InputAddressDto dto) {
        if ( dto == null ) {
            return null;
        }

        AddressEntity addressEntity = new AddressEntity();

        addressEntity.setName( dto.getName() );
        addressEntity.setStreet( dto.getStreet() );
        addressEntity.setHouse( dto.getHouse() );
        addressEntity.setPostalCode( dto.getPostalCode() );
        addressEntity.setLocality( dto.getLocality() );
        addressEntity.setRegion( dto.getRegion() );
        addressEntity.setEmail( dto.getEmail() );
        addressEntity.setPhone( dto.getPhone() );

        return addressEntity;
    }

    @Override
    public OutputAddressDto mapEntityToDto(AddressEntity entity) {
        if ( entity == null ) {
            return null;
        }

        OutputAddressDto outputAddressDto = new OutputAddressDto();

        outputAddressDto.setName( entity.getName() );
        outputAddressDto.setStreet( entity.getStreet() );
        outputAddressDto.setHouse( entity.getHouse() );
        outputAddressDto.setPostalCode( entity.getPostalCode() );
        outputAddressDto.setLocality( entity.getLocality() );
        outputAddressDto.setRegion( entity.getRegion() );
        outputAddressDto.setCountry( entity.getCountry() );
        outputAddressDto.setId( entity.getId() );
        outputAddressDto.setEmail( entity.getEmail() );
        outputAddressDto.setPhone( entity.getPhone() );

        return outputAddressDto;
    }
}
