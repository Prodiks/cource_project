package cource_project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "apartment")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @Max(value = 128, message = "")
    @Min(value = 1, message = "")
    @Column(name = "number")
    private int number;

    @NotNull
    @Max(value = 22, message = "")
    @Min(value = 1, message = "")
    @Column(name = "floor")
    private int floor;

    @NotNull
    @Column(name = "note")
    @Size(min = 2, max = 128, message = "")
    private String note;

//    @NotNull
//    @Column(name = "title")
//    @Size(min = 2, max = 100, message = "Название книги должно быть от 2 до 100 символов")
//    private String title;
//
//    @NotNull
//    @Column(name = "author")
//    @Size(min = 2, max = 100, message = "Имя автора должно быть от 2 до 100 символов")
//    private String author;
//
//    @NotNull
//    @Max(value = 3000, message = "Год должен быть корректным")
//    @Min(value = 1, message = "Год должен быть коррекстным")
//    @Column(name = "year")
//    private int year;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;
}
