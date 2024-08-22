package de.shop.modules.users.service.mapping;

import de.shop.modules.users.domain.dto.InputAddressDto;
import de.shop.modules.users.domain.dto.OutputAddressDto;
import de.shop.modules.users.domain.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMappingService {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    AddressEntity mapDtoToEntity(InputAddressDto dto);

    @Mapping(target = "name", source = "name")
    OutputAddressDto mapEntityToDto(AddressEntity entity);
}
