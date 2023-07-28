package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Genre extends Entity {
    private String name;

    public Genre(Long id, String name) {
        super(id);
        this.name = name;
    }
}
