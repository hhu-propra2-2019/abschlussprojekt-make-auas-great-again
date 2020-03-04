package mops.feedback.formulare;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table
@AllArgsConstructor
@Setter
@Getter

public class AntwortenDAO {
    @Id
    int id;

    int frageId;

    String antworttext;


}
