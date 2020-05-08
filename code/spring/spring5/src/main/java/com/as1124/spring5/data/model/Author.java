package com.as1124.spring5.data.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * OneToMany 一对多关联映射的三种方式在{@link OneToMany API文档}里面描述的很清楚共有三种方式 ；
 * <br/> 不能 transient 关联属性，否则映射关系对象查出来无数据
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Entity(name = "Author")
@Table(name = "author")
@Data
@NoArgsConstructor(force = true)
public class Author implements Serializable {

    private static final long serialVersionUID = -587340985358125700L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer authorId;

    @Column(name = "user_name", nullable = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    // 方式一：mappedBy 指定的是 N 端的属性名称
    // ATTENTION 不能 transient 这个属性，否则映射关系查出来无数据
    // @OneToMany(targetEntity = Book.class, mappedBy = "author")

    // 方式二：只用在一端添加就好
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "author_id")
    private List<Book> books;

    @Override
    public String toString() {
        return "Author name is " + this.authorId + "--" + this.username + "+ address is " + this.address;
    }

}
