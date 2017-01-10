package com.example.mitke.pmsu_sf27.model;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;
import java.util.List;

/**
 * Created by android on 23.3.16..
 */
@Table(name = "Tag", id="_id")

public class Tag extends Model {
    @Column(name = "Name", notNull = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Tag(){
        super();

    }
    public Tag(String name){
        super();
        this.name = name;
    }

    @Override
    public String toString() {

        return super.toString()+"Tag{" +
                "name='" + name + '\'' +
                '}';
    }
}
