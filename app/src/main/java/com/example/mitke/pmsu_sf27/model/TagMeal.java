package com.example.mitke.pmsu_sf27.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitke on 04-Jan-17.
 */
@Table(name = "TagMeal", id="_id")
public class TagMeal extends Model {
    @Column(name = "Tag",notNull=true, onDelete= Column.ForeignKeyAction.CASCADE)
    private Tag tag;
    @Column(name = "Meal",notNull=true, onDelete= Column.ForeignKeyAction.CASCADE)
    private Meal meal;
    public TagMeal(){
        super();
    }
    public TagMeal(Meal meal, Tag tag)
    {
        super();
        this.meal = meal;
        this.tag = tag;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }


    public static List<TagMeal> getAll() {
        return new Select().all().from(TagMeal.class).execute();
    }
}
