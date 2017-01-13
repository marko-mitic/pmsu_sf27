package com.example.mitke.pmsu_sf27.db;


import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Location;

import com.example.mitke.pmsu_sf27.R;
import com.example.mitke.pmsu_sf27.model.Meal;
import com.example.mitke.pmsu_sf27.model.Restourant;
import com.example.mitke.pmsu_sf27.model.Tag;
import com.example.mitke.pmsu_sf27.model.TagMeal;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitke on 25-Dec-16.
 */

public class DataBaseHelper {

    public static void insertTestValues(Context context) throws MalformedURLException {
        ArrayList<Restourant> restorani = new ArrayList<>();

         String name = "Kineski restoran 88";
         String description = "Najbolji kineski restoran u gradu";
         Bitmap smallPhoto = BitmapFactory.decodeResource(context.getResources(), R.mipmap.chinese_restourant_small);
         Bitmap largePhoto = smallPhoto;

         String address = "Bulevar oslobođenja 133, Novi Sad 403123, Serbia";

         int startHour = 10;
         int startMinute = 0;
         int endHour =  23;
         int endMinute = 0;
         String phone  = "+381 21 6616663";
         String email = "kineski@gmail.com";
        URL site =  new URL("http://kineskirestoran88.co.rs/");

        List<Meal> meals = new ArrayList<>();
        double lat =45.242144;
        double longt =19.843048;



        String name1 = "Pancetta";
        String description1 = "Food and music bar";
        Bitmap smallPhoto1 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.pancetta_image_small);
        Bitmap largePhoto1 = smallPhoto;

        String address1 = "Njegoseva 12 , Novi Sad 21000, Serbia";

        int startHour1 = 8;
        int startMinute1 = 0;
        int endHour1 =  23;
        int endMinute1 = 0;
        String phone1  = "+381 21 6616663";
        String email1 = "restoranpancetta@gmail.com";
        URL site1 =  new URL("http://www.restoranpancetta.com/");

        double lat1 =45.256200;
        double longt1 =19.844078;
        List meals1 = new ArrayList<Meal>();


        Meal pancettaDorucak = new Meal("PANCETTA BREAKFAST", "Chorizzo, Prosciutto, Eggs, Smetana cream, served with yoghurt!", 390.00,R.mipmap.pancetta_dorucak);
        Meal PikSaTunjevinom = new Meal("SPICY SANDWICH WITH TUNA", "Tuna, Corn, Cucumber, Mayonnaise, Chilli peppers, Red onion all inside a home made triangle buns!", 350.00,R.mipmap.sendvic_tunjevina);
        Meal teleca = new Meal("VEAL RAGOUT SOUP", "Hot veal ragout soup, with lean ragout meat and home made noodles!", 270.00,R.mipmap.ragou_soup);
        Meal govedjiT = new Meal("BEEF CAPRACO", "Ementaler, Gorgonzola, Grana padano, Brie, Cranberry ,Honey. An Itallan specialty that never gets old!", 850.00,R.mipmap.beef_carpaco);

        meals.add(pancettaDorucak);
        meals.add(PikSaTunjevinom);
        meals.add(teleca);
        meals.add(govedjiT);
        for (Meal m : meals){
            m.save();
        }
        Tag vege = new Tag();
        vege.setName("Vegetarijanska");
        Tag pikantno = new Tag();
        pikantno.setName("Pikantno");
        vege.save();
        pikantno.save();

        TagMeal tm1 = new TagMeal();
        tm1.setMeal(PikSaTunjevinom);
        tm1.setTag(pikantno);
        tm1.save();

        TagMeal tm2 = new TagMeal();
        tm2.setMeal(PikSaTunjevinom);
        tm2.setTag(vege);
        tm2.save();

        TagMeal tm3 = new TagMeal();
        tm3.setMeal(teleca);
        tm3.setTag(pikantno);
        tm3.save();

        Restourant pancettaR = new Restourant(name1,description1,smallPhoto1,largePhoto1,address1,startHour1,
                startMinute1,endHour1,endMinute1,phone1,email1,site1,meals1,longt1,lat1);
        Restourant kineskiRestoran = new Restourant(name,description,smallPhoto,largePhoto,address,startHour,
                startMinute,endHour,endMinute,phone,email,site,meals,longt,lat);


        Meal kinPil = new Meal("CHINESE CHICKEN", "Chicken, Rice, Peas, Bamboo peaces, spices. Classical chinese meal!", 550.00,R.mipmap.chinese_chicken);
        Meal kinMeal = new Meal("SOYA PEACES ", "Soya peaces, peanut, vegetable sauce. Perfect to suit your vegetarian needs", 550.00,R.mipmap.chinese_chicken);
        kinMeal.save();
        TagMeal tm4 = new TagMeal();
        tm4.setTag(vege);
        tm4.setMeal(kinMeal);
        tm4.save();

        kinPil.save();
        List<Meal> m  = new ArrayList<>();
        m.add(kinPil);
        m.add(kinMeal);
        kineskiRestoran.setSiteString("http://kineskirestoran88.co.rs");
        pancettaR.setSiteString("http://www.restoranpancetta.com");
        kineskiRestoran.setImagePath(R.mipmap.chinese_restourant_small);
        pancettaR.setImagePath(R.mipmap.pancetta_image_small);
        pancettaR.save();
        kineskiRestoran.save();
        kineskiRestoran.setMeals(m);
        pancettaR.setMeals(meals);
    }
}
