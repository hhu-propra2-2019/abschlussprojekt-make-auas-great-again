package mops.feedback.formulare;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Table
@AllArgsConstructor
@Getter
@Setter
public class FormularDAO {
    @Id
    int id;

    @DateTimeFormat
    String erstelltAm;

    @DateTimeFormat
    String guerltigBis;


}
