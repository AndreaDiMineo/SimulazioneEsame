package org.example;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

public class MyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();

        URI uri = exchange.getRequestURI();
        System.out.println(uri);

        String method = exchange.getRequestMethod();
        System.out.println(method);

        String s = read(is);
        System.out.println(s);

        String ris = uri.toString();
        String response = "";
        Cars.cars.add(new Car(123,"Bmw","X6", 3594.9, 2));
        Cars.cars.add(new Car(3634,"Audi","Q5", 38346.9, 1));
        Cars.cars.add(new Car(135,"Ferrari","Spider", 130000,4));
        if (ris.equals("/all")) {
            response = "<!doctype html>\n" +
                    "<html lang=en>\n" +
                    "<head>\n" +
                    "<meta charset=utf-8>\n" +
                    "<title>MyJava Sample</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "</br><h1>Lista di auto</h1>" +
                    "</br>\n" +
                    "<table>" +
                    "<tr>" +
                    "<th>Id</th>" +
                    "<th>Brand</th>" +
                    "<th>Model</th>" +
                    "<th>Price</th>" +
                    "<th>Quantity</th>" +
                    "</tr>";
            for (Car c: Cars.cars) {
                response += "<tr>" +
                        "<td>" + c.id + "</td>" +
                        "<td>" + c.brand + "</td>" +
                        "<td>" + c.model + "</td>" +
                        "<td>" + c.price + "</td>" +
                        "<td>" + c.qty + "</td>" +
                        "</tr>" +
                        "</table>" +
                        "</br>\n" +
                        "</body>\n" +
                        "</html>\n";
            }
            ;
        }
        if (ris.equals("/all_sorted_on_brand")) {
            response = "<!doctype html>\n" +
                    "<html lang=en>\n" +
                    "<head>\n" +
                    "<meta charset=utf-8>\n" +
                    "<title>MyJava Sample</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "</br><h1>Lista di auto in ordine alfabetico</h1>" +
                    "</br>\n" +
                    Cars.getInstance().toJSON(ris) +
                    "</br>\n" +
                    "</body>\n" +
                    "</html>\n";
            ;
        }
        if (ris.equals("/all_sorted_on_price")) {
            response = "<!doctype html>\n" +
                    "<html lang=en>\n" +
                    "<head>\n" +
                    "<meta charset=utf-8>\n" +
                    "<title>MyJava Sample</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "</br><h1>Lista di auto in ordine di prezzo</h1>" +
                    "</br>\n" +
                    Cars.getInstance().toJSON(ris) +
                    "</br>\n" +
                    "</body>\n" +
                    "</html>\n";
            ;
        }
        if (ris.equals("/more_expensive")) {
            double max = 0;
            String jsonStr = "";
            Gson gson = new Gson();
            for (Car c: Cars.cars) {
                if (c.price > max) {
                    max = c.price;
                    jsonStr = gson.toJson(c);
                }
            }
            response = "<!doctype html>\n" +
                    "<html lang=en>\n" +
                    "<head>\n" +
                    "<meta charset=utf-8>\n" +
                    "<title>MyJava Sample</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "</br>L'auto più costosa</h1>" +
                    "</br>\n" +
                    "<table>" +
                    "<tr>" +
                    "<th>Id</th>" +
                    "<th>Brand</th>" +
                    "<th>Model</th>" +
                    "<th>Price</th>" +
                    "<th>Quantity</th>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>Id</td>" +
                    "<td>Brand</td>" +
                    "<td>Model</td>" +
                    "<td>Price</td>" +
                    "<td>" +
                    "</tr>" +
                    "</table>";
            ;
        }
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String read(InputStream is) {
        BufferedReader br = new BufferedReader( new InputStreamReader(is) );
        System.out.println("\n");
        String received = "";
        while (true) {
            String s = "";
            try {
                if ((s = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(s);
            received += s;
        }
        return received;
    }

    private String[] process(String data){

        String[] splitted = data.split("&");
        if (splitted.length == 0)
            System.out.println("no data");

        return splitted;
    }
}
