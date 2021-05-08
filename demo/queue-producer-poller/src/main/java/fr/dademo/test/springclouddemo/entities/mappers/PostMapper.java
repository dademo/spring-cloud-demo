package fr.dademo.test.springclouddemo.entities.mappers;

import fr.dademo.test.springclouddemo.dto.PostDto;
import fr.dademo.test.springclouddemo.entities.PostEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDto mapToPostDto(PostEntity entity);
}
