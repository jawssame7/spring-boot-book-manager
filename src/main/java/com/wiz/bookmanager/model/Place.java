package com.wiz.bookmanager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Place extends BaseModel {

    /**
     * id
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 名前
     */
    private String name;

    /**
     * idを返す
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * idを設定する
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 名前を設定する
     * @return 名前
     */
    public String getName() {
        return name;
    }

    /**
     * 名前を返す
     * @param name 名前
     */
    public void setName(String name) {
        this.name = name;
    }

}
