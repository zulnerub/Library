package enums;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Enumeration with parameter simpleName to represent the Categories of a book.
 */
@XmlRootElement(name = "book-tag")
@XmlAccessorType(XmlAccessType.FIELD)
public enum BookTags {
    CHILDREN,
    STORY,
    LEARNING,
    HOBBY
}
