package com.example.mitke.pmsu_sf27.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitke on 08-Jan-17.
 */
@Table(name = "MealToRestourant", id="_id")
public class MealToRestourant extends Model {
    @Column(name = "Meal" )
    private Meal m;
    @Column(name = "Restourant")
    private Restourant r;
    public MealToRestourant(){
        super();
    }
    public Restourant getR() {
        return r;
    }

    public void setR(Restourant r) {
        this.r = r;
    }

    public Meal getM() {
        return m;
    }
    public static List<MealToRestourant> getAll(){
        return new Select()
                .all()
                .from(MealToRestourant.class)
                .execute();
    }

    public MealToRestourant(Meal m, Restourant r) {
        super();
        this.m = m;
        this.r = r;
    }

    public void setM(Meal m) {
        this.m = m;

    }



}
