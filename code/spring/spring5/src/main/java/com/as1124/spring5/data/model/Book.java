package com.as1124.spring5.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Entity(name = "Book")
@Table(name = "book")
@Data
@NoArgsConstructor(force = true)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer bookID;

    @Column(name = "reader")
    private String reader;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private Double price;

    @Column(name = "description")
    private String description;

    @Column(name = "author_id")
    private Integer authorId;

    // ATTENTION: 如果两边都设置关联查询的话在解析成JSON、XML传递到客户端就出现循环解析了，
    // 此时内存也就 StackOverFlow 了；所以这里通过 @Transient注解使其不参与持久化
    //	@ManyToOne(targetEntity = Author.class)
    //	@JoinColumn(name = "author_id")
    @Transient
    @JsonIgnore
    private Author author;

    public Book(String title, String isbn, String reader, Integer authorID) {
        this.title = title;
        this.isbn = isbn;
        this.reader = reader;
        this.authorId = authorID;
    }

    @Override
    public String toString() {
        return "Book named " + this.bookID + "--" + this.title + ", price is " + this.price;
    }
}
