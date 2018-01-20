package ru.otus.homework03.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.homework03.xml.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

import static ru.otus.homework03.xml.XMLConsts.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeePersonalInfo {

    @XmlAttribute(name = ATTR_BIRTH_DATE)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate birthDate;

    @XmlAttribute(name = ATTR_FIRST_NAME)
    private String firstName;

    @XmlAttribute(name = ATTR_MIDDLE_NAME)
    private String middleName;

    @XmlAttribute(name = ATTR_SECOND_NAME)
    private String secondName;

    @XmlElement(name = ELEMENT_LOCATION)
    private String location;

}
