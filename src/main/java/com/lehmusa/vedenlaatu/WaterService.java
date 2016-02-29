/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lehmusa.vedenlaatu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import static java.lang.System.out;
import java.util.List;


/**
 *
 * @author Anton
 */
public class WaterService {
    public static void main(String[] args) {
        WaterService waterService = new WaterService();
     
    }
    public WaterService() {
        // Luo HTTPThread -säie ja käynnistä se
        HttpThread httpThread = new HttpThread( 
                "https://vellamo.tampere.fi/api/v1/latest.json", 
                new HttpThreadListener() {
                    @Override
                    public void JsonResponseReady(String jsonResponse) {
                        //out.println(jsonResponse);

                        Vedenlaatu[] waterArray = new GsonBuilder().create().
                                fromJson(jsonResponse, Vedenlaatu[].class);
                        
                        out.println(waterArray[0].getName().toString());

                    }
                });
        httpThread.start();
    }
}
interface HttpThreadListener {
    public void JsonResponseReady ( String jsonResponse );
}