/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lehmusa.vedenlaatu;


import com.google.gson.GsonBuilder;
import static java.lang.System.out;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;




/**
 *
 * @author Anton
 */
public class WaterService {
    private static Vedenlaatu[] waterArray;
    private boolean dataReadable = false;
    
    
    public static void main(String[] args) {
        WaterService main = new WaterService(); 
        main.mainMenu();
     
    }
    public void getWaterData() {
        // Luo HTTPThread -säie ja käynnistä se
        HttpThread httpThread = new HttpThread( 
                "https://vellamo.tampere.fi/api/v1/latest.json", 
                new HttpThreadListener() {
                    @Override
                    public void JsonResponseReady(String jsonResponse) {
                        //out.println(jsonResponse);

                        waterArray = new GsonBuilder().create().
                                fromJson(jsonResponse, Vedenlaatu[].class);
                        dataReadable = true;
                    }
                });
        httpThread.start();
    }
    private void mainMenu(){
        String strInput;
        int input = 0;
        
        showOptions();
        
        strInput = JOptionPane.showInputDialog("Toiminto: ");
        //input validation
        if(strInput != null && (strInput.equals("1") || 
                strInput.equals("2") || strInput.equals("3") || 
                strInput.equals("4") )){
            input = Integer.parseInt( strInput);
        }
        else{
            out.println("");
            out.println("Valitse jokin vaihtoehto!");
            out.println("");
            out.println("");
            mainMenu();
        }
        
        
        switch (input){
            case 1:
                getWaterData();
                out.println("Data päivitetty!");
                out.println("");
                mainMenu();
                break;
            case 2:
                String area=null;
                while(area==null){
                   area = JOptionPane.showInputDialog("Alue (pienilläkirjaimilla): "); 
                }
                out.println("");
                showResults(area);
                break;
            case 3:
                out.println("");
                listLocations();
                break;
            case 4:
                System.exit(0);
                break;
            default:
                out.println("Virheellinen syöte!");
                out.println("");
                out.println("");
                mainMenu();    
                break;
                 
        }
    }
    
    private void listLocations(){
        out.println("TODO");
    }
    
    private void showResults(String area){
        if(dataReadable){
            ArrayList<Integer> foundIndexes = findArea(area);
                if(foundIndexes.size()>0){
                    for (Integer index : foundIndexes) {
                            out.println("Alue: "+waterArray[index].getSlug().toString());
                            printLatestMeasurementes(waterArray[index].getLatestMeasurements());
                            }
                    }
                else{
                    out.println("Aluetta ei löytynyt!");
                    out.println("");
                    mainMenu();
                }
        }
        else{
            out.println("Hae data ensin!");
            out.println("");
            mainMenu();
        }
        mainMenu();
    }
    
    private void showOptions(){
        out.println("1.Päivitä data (uutta dataa saatavissa tunnin välein)");
        out.println("2.Näytä data sijainnin perusteella");
        out.println("3.Listaa sijainnit");
        out.println("4.poistu");
        
    }
    
    private void printLatestMeasurementes(LatestMeasurements current){
        DecimalFormat df = new DecimalFormat("##.#");
        
        double Rusko,Kaupinoja,Messukyla,Pinsio,Julkujarvi,Mustalampi,Hyhky,
                cl,hardness,ph,pintavesi,retention,temperature,pohjavesi=0.0;
        
        Rusko=current.getRusko();
        Kaupinoja=current.getKaupinoja();
        Messukyla=current.getMessukyla();
        Pinsio=current.getPinsio();
        Julkujarvi=current.getJulkujarvi();
        Mustalampi=current.getMustalampi();
        Hyhky=current.getHyhky();
        
        cl=current.getCl();
        hardness=current.getHardness();
        ph=current.getPh();
        retention=current.getRetention();
        pintavesi=current.getPintavesi();
        pohjavesi=current.getPohjavesi();
        temperature=current.getT();
        
        
        out.println("Veden aluperä käsittelylaitoksittain:");
        out.println("Rusko: "+df.format(Rusko)+"%");
        out.println("Kaupinoja: "+df.format(Kaupinoja)+"%");
        out.println("Messukyla: "+df.format(Messukyla)+"%");
        out.println("Julkujarvi: "+df.format(Julkujarvi)+"%");
        out.println("Mustalampi: "+df.format(Mustalampi)+"%");
        out.println("Hyhky: "+df.format(Hyhky)+"%");
        out.println("Pinsio: "+df.format(Pinsio)+"%");
        out.println("");
        out.println("Veden laatu:");
        out.println("Pohjavesi: "+df.format(pohjavesi)+"%");
        out.println("Pintavesi: "+df.format(pintavesi)+"%");
        out.println("Viipymä: "+df.format(retention)+"h");
        out.println("Lämpötila: "+df.format(temperature)+"°C");
        out.println("pH: "+df.format(ph));
        out.println("Kloori: "+df.format(cl)+"mg / l");
        out.println("Kovuus: "+df.format(cl)+"°dH");
        out.println("");
        out.println("--------------------------------------------------------");
    }
    
    private  ArrayList<Integer> findArea(String needle){
        ArrayList<Integer> found = new ArrayList<Integer>();
        int subStrEndIndex;
        int indexOfNotfound = -1;
        String slug;
        //etsitään kaikki maininnat halutusta alueesta
        for(int i=0; i<waterArray.length;i++){
            slug =  waterArray[i].getSlug();
            subStrEndIndex=slug.indexOf('-');
            
            if(subStrEndIndex==indexOfNotfound){
                if(needle.equals(slug)){
                    found.add(i);
                }
            }
            else{
                if(needle.equals(slug.substring(0,subStrEndIndex))){
                    found.add(i);
                }
            }
        }
        //palautetaan lista waterArray:n indekseistä joissa on halutun paikan 
        //tieotja
        return found;
    }
}
interface HttpThreadListener {
    public void JsonResponseReady ( String jsonResponse );
}