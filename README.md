# java-filmorate

<h3 align="center">Схема базы данных</h3>

![shema bd](https://github.com/ImaginaryEllen/java-filmorate/assets/124062632/87f1a168-9360-4e3c-8303-4aebce00900c)

<h3 align="center">Примеры взаимодействия с базой данных:</h3>

<details>
<summary>Как получить данные о пользователе по id</summary>

```
SELECT *
FROM users
WHERE id = 1;
```

</details>

<details>
<summary>Как получить данные о фильме по id</summary>

```
SELECT *
FROM films
WHERE id = 1;
```

</details>

<details>
<summary>Как получить названия фильмов с жанром комедия</summary>

```
SELECT f.name
FROM films AS f
INNER JOIN film_genres AS fg ON f.film_id = fg.film_id 
INNER JOIN genres AS g ON fg.genre_id = g.genre_id
WHERE g.name = 'Комедия'
GROUP BY f.name;
```

</details>

<details>
<summary>Как получить id пользователей 1990 года рождения</summary>

```
SELECT user_id
FROM users
WHERE EXTRACT(YEAR FROM CAST(birthday AS date)) = 1990
GROUP BY user_id;
```

</details>
