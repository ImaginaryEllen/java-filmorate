package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Rating extends Entity {
    private String name;

    public Rating(Long id, String name) {
        super(id);
        this.name = name;
    }

    public Rating(String name) {
        this.name = name;
    }
}
