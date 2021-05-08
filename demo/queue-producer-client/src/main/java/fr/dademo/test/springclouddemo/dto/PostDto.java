package fr.dademo.test.springclouddemo.dto;

import lombok.Data;

@Data
public class PostDto {
    Long userId;
    Long id;
    String title;
    String body;
}
