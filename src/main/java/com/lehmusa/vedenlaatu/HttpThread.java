/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lehmusa.vedenlaatu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anton
 */
public class HttpThread extends Thread {
    private String url = "";
    private HttpThreadListener listener;
    public HttpThread( String url, HttpThreadListener listener ){
        this.url = url;
        this.listener = listener;
    }
    
    public void run(){
        //lukee JSONin Stringiin
        BufferedReader reader = null;
        String webResponse = "";
        try {
            URL jsonurl = new URL(url);
            reader = new BufferedReader(new InputStreamReader(jsonurl.openStream()));
            StringBuilder buffer = new StringBuilder();
            String line="";
            //rivi riviltä niin kaun kun tavaraa riittää
            while ((line = reader.readLine()) != null){
                buffer.append(line); 
            }

            webResponse = buffer.toString();
            reader.close();
        }
        catch (MalformedURLException ex) {
            Logger.getLogger(HttpThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HttpThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        listener.JsonResponseReady( webResponse );
    }
}
