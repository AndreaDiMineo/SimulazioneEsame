package org.example;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Cars {

    private static Cars INSTANCE;

    List<Car> cars = new ArrayList<Car>();

    private Cars() {
        cars.add(new Car(123,"Bmw","X6", 3594.9, 2));
        cars.add(new Car(3634,"Audi","Q5", 38346.9, 1));
        cars.add(new Car(135,"Ferrari","Spider", 130000,4));
    }

    public static Cars getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Cars();
        }

        return INSTANCE;
    }

    int sorted(String c1, String c2){
        return c1.compareTo(c2);
    }

    String toJSON(String cmd){
        Gson gson = new Gson();
        String jsonStr = "";
        double max = 0;
        String[] ch;
        ArrayList<Car> list = new ArrayList<Car>();
        if (cmd.equals("all"))
            jsonStr = gson.toJson(cars);
        if (cmd.equals("all_sorted_on_brand") || cmd.equals("ALL SORTED_ON_BRAND"))
            cars.sort((o1, o2) -> {
                return o1.getBrand().compareTo(o2.getBrand());
            });
            jsonStr = gson.toJson(cars);
        if (cmd.equals("all_sorted_on_price") || cmd.equals("ALL SORTED_ON_PRICE"))
            if (cars.get(0).price > cars.get(1).price) {
                list.add(cars.get(0));
                if (cars.get(0).price > cars.get(2).price) {
                    list.add(cars.get(1));
                    list.add(cars.get(2));
                    jsonStr = gson.toJson(list);
                }
                else {
                    list.add(cars.get(2));
                    list.add(cars.get(1));
                    jsonStr = gson.toJson(list);
                }
            } else if (cars.get(1).price > cars.get(2).price) {
                list.add(cars.get(1));
                list.add(cars.get(2));
                jsonStr = gson.toJson(list);
            } else {
                list.add(cars.get(2));
                list.add(cars.get(1));
                list.add(cars.get(0));
                jsonStr = gson.toJson(list);
            }
        if (cmd.equals("more_expensive") || cmd.equals("MORE_EXPENSIVE"))
            for (Car c: cars) {
                if (c.price > max) {
                    max = c.price;
                    jsonStr = gson.toJson(c);
                }
            }
        return jsonStr;
    }
}
