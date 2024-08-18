package de.shop.modules.users.service.mapping;

import de.shop.modules.users.domain.dto.InputRegDto;
import de.shop.modules.users.domain.dto.OutputRegDto;
import de.shop.modules.users.domain.entity.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-18T21:31:11+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Amazon.com Inc.)"
)
@Component
public class AuthorMappingServiceImpl implements AuthorMappingService {

    @Override
    public UserEntity mapDtoToEntity(InputRegDto dto) {
        if ( dto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setEmail( dto.getEmail() );
        userEntity.setPassword( dto.getPassword() );
        userEntity.setSubscribeNews( dto.getSubscribeNews() );

        return userEntity;
    }

    @Override
    public OutputRegDto mapEntityToDto(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        OutputRegDto outputRegDto = new OutputRegDto();

        outputRegDto.setId( entity.getId() );
        outputRegDto.setEmail( entity.getEmail() );

        return outputRegDto;
    }
}
