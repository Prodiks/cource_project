package cource_project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

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
    @Max(value = 128, message = "Сликшом большой номер квартиры")
    @Min(value = 1, message = "Номер квартиры - натуральное число")
    @Column(name = "number")
    private int number = 1;

    @NotNull
    @Max(value = 22, message = "Слишком большой этаж")
    @Min(value = 1, message = "Сликшом маленький этаж")
    @Column(name = "floor")
    private int floor = 1;

    @NotNull
    @Column(name = "note")
    @Size(min = 0, max = 128, message = "Неверная доп. информация")
    private String note = "";

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;
}
