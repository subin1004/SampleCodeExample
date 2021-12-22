package com.subing.recyclerviewexample;

import java.util.ArrayList;
import java.util.Arrays;

public class Data {

    private String title;
    private String content;
    private int photo;

    public Data(String title, String content, int photo) {
        this.title = title;
        this.content = content;
        this.photo = photo;
    }


    public static ArrayList<Data> DATA = new ArrayList<>(Arrays.asList
            (       new Data("apple", "사과는 마싯서", R.drawable.apple),
                    new Data("banana", "바나나도 마싯서", R.drawable.banana),
                    new Data("blueberry", "블루베리는 더 마싯서", R.drawable.blueberry),
                    new Data("grape", "포도는 더 마싯서", R.drawable.grape),
                    new Data("kiwi", "키위는 더 마싯서", R.drawable.kiwi),
                    new Data("lemon", "레몬은 더 마싯서", R.drawable.lemon),
                    new Data("peach", "복숭아는 더 마싯서", R.drawable.peach),
                    new Data("strawberry", "딸기는 더 마싯서", R.drawable.strawberry),
                    new Data("watermelon", "수박은 더 마싯서", R.drawable.watermelon)

            ));


    public static Data getItem(String title) {

        for(int i=0;i<DATA.size();i++){
            if (DATA.get(i).getTitle().equals(title)) {
                Data data = new Data(DATA.get(i).getTitle(), DATA.get(i).getContent(), DATA.get(i).getPhoto());
                return data;
            }
        }
        return null;
    }

    public static ArrayList<String> getTitles(){
        ArrayList<String> titles = new ArrayList<>();

        for(int i=0;i<DATA.size();i++)
        {
            titles.add(DATA.get(i).getTitle());
        }
        return titles;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
