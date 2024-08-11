package de.shop.modules.users.service.mapping;

import de.shop.modules.users.domain.dto.InputAddressDto;
import de.shop.modules.users.domain.dto.OutputAddressDto;
import de.shop.modules.users.domain.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMappingService {
    @Mapping(target = "id", ignore = true)
    AddressEntity mapDtoToEntity(InputAddressDto dto);
OutputAddressDto mapEntityToDto(AddressEntity entity);
}
