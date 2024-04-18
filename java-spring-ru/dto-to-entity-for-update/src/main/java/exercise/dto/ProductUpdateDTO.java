package exercise.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

// BEGIN
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductUpdateDTO {
    String title;
    int price;
}
// END
