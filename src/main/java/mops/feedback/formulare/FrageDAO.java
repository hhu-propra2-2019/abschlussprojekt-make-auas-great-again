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
@Getter
@Setter
public class FrageDAO {

    @Id
    int id;

    String Antwortmoeglichkeiten;

    String fragentext;

    String typ;



    int formularId;

}
