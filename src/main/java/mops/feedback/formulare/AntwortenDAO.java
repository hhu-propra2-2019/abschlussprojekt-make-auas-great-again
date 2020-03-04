package mops.feedback.formulare;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("Antworten")
public class AntwortenDAO {
    @Id
    Integer id;

    Integer frageId;

    String antworttext;


}
