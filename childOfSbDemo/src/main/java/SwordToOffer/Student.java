package SwordToOffer;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author ggz on 2022/8/30
 */
@Data
@ToString
public class Student implements Serializable {
    private static final long serialVersionUID = 4025062149500727285L;

    public Student() {
    }

    public Student(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    private String name;
    private String sex;
}