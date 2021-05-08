package fr.dademo.test.springclouddemo.entities;

import lombok.Data;

@Data
public class PostEntity {
    Long userId;
    Long id;
    String title;
    String body;
}
