package com.example.mitke.pmsu_sf27.model;


import android.util.Log;

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
    @Column(name = "ImagePath")
    private int imagePath;

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }

    public Meal() {
        super();
    }


    public Meal(String name, String description, double price, int imagePath) {
        super();
        this.name = name;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
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
        ArrayList<Tag> ret = new ArrayList<>();
        for(TagMeal tm:manyToMany){
            ret.add(tm.getTag());
        }
        return ret;
    }
    public static List<Meal> getFilteredByTags(ArrayList<Tag> tags,ArrayList<Meal> meals){
        ArrayList<Meal> filteredList = new ArrayList<>();
//        for(TagMeal tm: (ArrayList<TagMeal>)TagMeal.getAll()){
//
//        }

        for(Meal m: meals){
            if(m.getTags().containsAll(tags)){
                filteredList.add(m);
            }
        }
        return filteredList;
    }
    public static List<Meal> getAllMeals(){
        return new Select().all().from(Meal.class).execute();
    }
    public static Meal getById(long id){
        List<Meal> list =  new Select().from(Meal.class).as("m").where("m._id = ?" ,id).execute();
        return list.get(0);
    }



}
