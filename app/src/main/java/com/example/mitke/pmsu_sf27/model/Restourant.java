package com.example.mitke.pmsu_sf27.model;

import android.graphics.Bitmap;
import android.location.Address;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.example.mitke.pmsu_sf27.tools.Util;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by android on 23.3.16..
 */
@Table(name = "Restourant", id="_id")
public class Restourant extends Model {
    @Column(name = "Name", notNull=true)
    private String name;
    @Column(name = "Description", notNull=true)
    private String description;
    @Column(name = "SmallPhoto" )
    private Bitmap smallPhoto;
    @Column(name = "LargePhoto" )
    private Bitmap largePhoto;
    @Column(name = "Address")
    private String address;
    @Column(name = "StartHour" )
    private int startHour;
    @Column(name = "ImagePath")
    private int imagePath;

    @Column(name = "StartMinute")
    private int startMinute;
    @Column(name = "EndHour")
    private int endHour;
    @Column(name = "EndMinute")
    private int endMinute;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "Email")
    private String email;
    @Column(name = "Site")
    private URL site;
    @Column(name = "Meals")
    private List<Meal> meals;
    @Column(name = "LocationLong")
    private double locationLong;
    @Column(name = "LocationLat")
    private double locationLat;
    @Column(name = "SiteString")
    private String siteString;

    public String getSiteString() {
        return siteString;
    }

    public void setSiteString(String siteString) {
        this.siteString = siteString;
    }

    public Restourant() {
        super();
    }


    public Restourant(String name, String description, Bitmap smallPhoto, Bitmap largePhoto, String address, int startHour, int startMinute, int endHour, int endMinute, String phone, String email, URL site, List<Meal> meals,double locationLong,double locationLat) {
        super();
        this.name = name;
        this.description = description;
        this.smallPhoto = smallPhoto;
        this.largePhoto = largePhoto;
        this.address = address;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.phone = phone;
        this.email = email;
        this.site = site;
        this.meals = meals;
        this.locationLong = locationLong;
        this.locationLat = locationLat;

    }

    private void saveMeals() {
        for(Meal m: this.meals){
            MealToRestourant mr = new MealToRestourant(m,this);
            mr.save();
        }
    }

    public List<Meal> getMealsFromDb() {
        ArrayList<Meal> meals = new ArrayList<Meal>();
        for(MealToRestourant mr: MealToRestourant.getAll()){
            if (mr.getR().getId()==this.getId()){
                meals.add(mr.getM());
            }
        }
        this.meals = meals;
        return meals;
    }
    public List<Meal> getMeals(){
        return meals;
    }

    public static  List<Restourant> getAllRestourants(){
        return new Select()
                .all()
                .from(Restourant.class)
                .execute();
    }


    public int getImagePath() {
        return imagePath;
    }

    @Override
    public boolean equals(Object obj) {
        Restourant r = (Restourant) obj;
        if (r.getName()==getName()){
            return true;
        }else return false;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }

    public double getLocationLong() {
        return locationLong;
    }

    public void setLocationLong(double locationLong) {
        this.locationLong = locationLong;
    }

    public double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        for(Meal m: meals) {
            MealToRestourant mr = new MealToRestourant(m, this);
            mr.save();
        }}



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

    public Bitmap getSmallPhoto() {
        return smallPhoto;
    }

    public void setSmallPhoto(Bitmap smallPhoto) {
        this.smallPhoto = smallPhoto;
    }

    public Bitmap getLargePhoto() {
        return largePhoto;
    }

    public void setLargePhoto(Bitmap largePhoto) {
        this.largePhoto = largePhoto;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public URL getSite() {
        return site;
    }

    public void setSite(URL site) {
        this.site = site;
    }

    public static Restourant getById(int id){
        return getAllRestourants().get(id);
    }

    public static ArrayList<Restourant> filterByDistance(ArrayList<Restourant> toFilter, double latitude, double longitude, double distance){
        Iterator<Restourant> it = toFilter.iterator();
        while (it.hasNext()){
            Restourant r = it.next();
            if(Util.haversineDistance(latitude,longitude,r.getLocationLat(),r.getLocationLong())>distance){
                it.remove();
            }
        }
        return toFilter;
    }
    public static ArrayList<Restourant> filterByWorkTime(int hour, int min){
        ArrayList<Restourant> toFilter = (ArrayList<Restourant>) Restourant.getAllRestourants();
        Iterator<Restourant> it = toFilter.iterator();
        while(it.hasNext()){
            Restourant r = it.next();
            if(r.getStartHour()>hour||r.getEndHour()<hour){
                it.remove();
            }else if(r.getStartHour()==hour){
                if (r.getStartMinute()>min){
                    it.remove();
                }
            }else if(r.getEndHour()==hour){
                if (r.getEndMinute()<min){
                    it.remove();
                }
            }
        }
        return toFilter;
    }
    public String getWorkTimeString() {
        String result;
        String startHour;
        String endHour;
        String startMinute;
        String endMinute;
        if(this.startHour<10){
            startHour = "0"+this.startHour;
        }else {
            startHour = this.startHour+"";
        }
        if(this.startMinute<10){
            startMinute = "0"+this.startMinute;
        }else {
            startMinute = this.startMinute+"";
        }
        if(this.endHour<10){
            endHour = "0"+this.endHour;
        }else {
            endHour = this.endHour+"";
        }
        if(this.endMinute<10){
            endMinute = "0"+this.endMinute;
        }else {
            endMinute = this.endMinute+"";
        }
        result = startHour+":"+startMinute+"-"+endHour+":"+endMinute;
        return result;




    }
}
