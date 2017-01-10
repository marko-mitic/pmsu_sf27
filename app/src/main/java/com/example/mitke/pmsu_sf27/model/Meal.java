package com.example.mitke.pmsu_sf27.model;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by android on 23.3.16..
 */
@Table(name = "Meal", id="_id")
public class Meal extends Model {

    @Column(name = "Name", notNull=true)
    private String name;
    @Column(name = "Description", notNull=true)
    private String description;
    @Column(name = "Price", notNull=true)
    private double price;

    public Meal() {
        super();
    }


    public Meal(String name, String description, double price) {
        super();
        this.name = name;
        this.description = description;
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<Tag> getTags() {
        List<TagMeal> manyToMany = getMany(TagMeal.class, "Meal");
        ArrayList<Tag> ret = new ArrayList<Tag>();
        for(TagMeal tm:manyToMany){
            ret.add(tm.getTag());
        }
        return ret;
    }
    public List<Meal> getFilteredByTags(ArrayList<Tag> tags){
        From query = new Select()
                .from(Meal.class).as("m");
        int i = 0;
        for(Tag tag : tags){
            query = query.join(TagMeal.class).as("t"+i)
                    .on("m_id = t"+i+".meal and t+i+.rag = ?",tag.getId());
            i++;
        }
        return query.execute();
    }
    public static List<Meal> getAllMeals(){
        return new Select().all().from(Meal.class).execute();
    }
    public static Meal getById(int id){
        return new Select().from(Meal.class).where("_id = ?" ,id).executeSingle();
    }



}
