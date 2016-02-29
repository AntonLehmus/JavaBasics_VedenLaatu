/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lehmusa.vedenlaatu;


import com.google.gson.GsonBuilder;
import static java.lang.System.out;
import java.text.DecimalFormat;



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
                       printLatestMeasurementes(waterArray[0].getLatestMeasurements());

                    }
                });
        httpThread.start();
    }
    private void printLatestMeasurementes(LatestMeasurements current){
        DecimalFormat df = new DecimalFormat("##.#");
        
        double Rusko,Kaupinoja,Messukyla,Pinsio,Julkujarvi,Mustalampi,Hyhky=0.0;
        
        Rusko=current.getRusko();
        Kaupinoja=current.getKaupinoja();
        Messukyla=current.getMessukyla();
        Pinsio=current.getPinsio();
        Julkujarvi=current.getJulkujarvi();
        Mustalampi=current.getMustalampi();
        Hyhky=current.getHyhky();
        
        out.println("Veden aluperä käsittelylaitoksittain:");
        out.println("Rusko: "+df.format(Rusko)+"%");
        out.println("Kaupinoja: "+df.format(Kaupinoja)+"%");
        out.println("Messukyla: "+df.format(Messukyla)+"%");
        out.println("Julkujarvi: "+df.format(Julkujarvi)+"%");
        out.println("Mustalampi: "+df.format(Mustalampi)+"%");
        out.println("Hyhky: "+df.format(Hyhky)+"%");
        out.println("Pinsio: "+df.format(Pinsio)+"%");
    }
}
interface HttpThreadListener {
    public void JsonResponseReady ( String jsonResponse );
}