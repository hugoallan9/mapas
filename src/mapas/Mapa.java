/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapas;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author hugo
 */
public class Mapa {
    File rutaCSV;
    static File rutaSalida;
    static Boolean bajada;
    
    
   public Mapa(String rutaCSV, String rutaSalida){
        this.rutaCSV = new File(rutaCSV);
        this.rutaSalida = new File(rutaSalida);
    }
    
    public void hacerRegional(String csv){
        try {
            File  f = new File(rutaSalida,"mapaRegional.py");
            File f1 = new File(rutaSalida, "mapasRegionales");
            f1 = new File(f1, "datos.tex");
            File CSV =  new File(rutaCSV,csv);
            /*
            Los parámetros son 1. Ruta para el csv
            2. Ruta de los shapes para los mapas
            3. Ruta del tex de los datos para etiquetas
            4. La ruta de salida para el pdf
            */
            String[] cmd = {
            "/bin/bash",
            "-c",
            "python2.7 " + f.getAbsolutePath() + " " + CSV.getAbsolutePath() + " " + 
                    rutaSalida + " " + f1 + " " + rutaSalida  
            };
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader in = new BufferedReader(
                                new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public void hacerDepartamental(String csv){
        try {
            File  f = new File(rutaSalida,"mapa.py");
            File f1 = new File(rutaSalida, "mapasDepartamentales");
            f1 = new File(f1, "datos.tex");
            File CSV =  new File(rutaCSV,csv);
            /*
            Los parámetros son 1. Ruta para el csv
            2. Ruta de los shapes para los mapas
            3. Ruta del tex de los datos para etiquetas
            4. La ruta de salida para el pdf
            */
            String[] cmd = {
            "/bin/bash",
            "-c",
            "python2.7 " + f.getAbsolutePath() + " " + CSV.getAbsolutePath() + " " + 
                    rutaSalida + " " + f1 + " " + rutaSalida  
            };
            System.out.println(cmd[2]);
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader in = new BufferedReader(
                                new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void descarga(){
        File file = null; 
       URL url = null;
       System.out.println(rutaSalida);
        
       try {
            if( rutaSalida.exists() == false){
                rutaSalida.mkdir();
            }
            file = new  File(rutaSalida,"mapaRegional.py");
            url = new URL("http://www.ine.gob.gt/ftparchivos/Scripts/mapaRegional.py");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
       
       try {
            File f = new File(rutaSalida, "mapasRegionales");
            if( f.exists() == false){
                f.mkdir();
            }
            file = new  File(f,"mapaAnotado.tex");
            url = new URL("http://www.ine.gob.gt/ftparchivos/mapasRegionales/mapaAnotado.tex");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            File f = new  File(rutaSalida,"MapaRegiones");
            if( f.exists() == false){
                f.mkdir();
            }
            file = new  File(f,"mapaRegiones.dbf");
            url = new URL("http://www.ine.gob.gt/ftparchivos/pruebasMapas/MapaRegiones/mapaRegiones.dbf");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        try {
            File f = new  File(rutaSalida,"MapaRegiones");
            if( f.exists() == false){
                f.mkdir();
            }
            file = new  File(f,"mapaRegiones.prj");
            url = new URL("http://www.ine.gob.gt/ftparchivos/pruebasMapas/MapaRegiones/mapaRegiones.prj");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        try {
            File f = new  File(rutaSalida,"MapaRegiones");
            if( f.exists() == false){
                f.mkdir();
            }
            file = new  File(f,"mapaRegiones.qpj");
            url = new URL("http://www.ine.gob.gt/ftparchivos/pruebasMapas/MapaRegiones/mapaRegiones.qpj");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            File f = new  File(rutaSalida,"MapaRegiones");
            if( f.exists() == false){
                f.mkdir();
            }
            file = new  File(f,"mapaRegiones.shp");
            url = new URL("http://www.ine.gob.gt/ftparchivos/pruebasMapas/MapaRegiones/mapaRegiones.shp");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            File f = new  File(rutaSalida,"MapaRegiones");
            if( f.exists() == false){
                f.mkdir();
            }
            file = new  File(f,"mapaRegiones.shx");
            System.out.println(f.getAbsolutePath());
            url = new URL("http://www.ine.gob.gt/ftparchivos/pruebasMapas/MapaRegiones/mapaRegiones.shx");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }
    
    
    public static void descargaDepartamental(){
        File file = null; 
       URL url = null;
       System.out.println(rutaSalida);
        
       try {
            if( rutaSalida.exists() == false){
                rutaSalida.mkdir();
            }
            file = new  File(rutaSalida,"mapa.py");
            url = new URL("http://www.ine.gob.gt/ftparchivos/Scripts/mapa.py");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
       
       try {
            File f = new File(rutaSalida, "mapasDepartamentales");
            if( f.exists() == false){
                f.mkdir();
            }
            file = new  File(f,"mapaAnotado.tex");
            url = new URL("http://www.ine.gob.gt/ftparchivos/mapasDepartamentos/mapaAnotado.tex");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            File f = new File(rutaSalida, "mapasDepartamentales");
            if( f.exists() == false){
                f.mkdir();
            }
            file = new  File(f,"barraColor.pdf");
            url = new URL("http://www.ine.gob.gt/ftparchivos/mapasDepartamentos/barraColor.pdf");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            File f = new  File(rutaSalida,"MapaDepartamentos");
            if( f.exists() == false){
                f.mkdir();
            }
            file = new  File(f,"22_DEPARTAMENTOS.dbf");
            url = new URL("http://www.ine.gob.gt/ftparchivos/pruebasMapas/MapaRepublica/22_DEPARTAMENTOS.dbf");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
//        try {
//            File f = new  File(rutaSalida,"MapaDepartamentos");
//            if( f.exists() == false){
//                f.mkdir();
//            }
//            file = new  File(f,"mapaRegiones.prj");
//            url = new URL("http://www.ine.gob.gt/ftparchivos/pruebasMapas/MapaRepublica/22_DEPARTAMENTOS.prj");
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        try {
//            FileUtils.copyURLToFile(url, file);
//        } catch (IOException ex) {
//            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
        
//        try {
//            File f = new  File(rutaSalida,"MapaDepartamentos");
//            if( f.exists() == false){
//                f.mkdir();
//            }
//            file = new  File(f,"mapaRegiones.qpj");
//            url = new URL("http://www.ine.gob.gt/ftparchivos/pruebasMapas/MapaRegiones/mapaRegiones.qpj");
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        try {
//            FileUtils.copyURLToFile(url, file);
//        } catch (IOException ex) {
//            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
        try {
            File f = new  File(rutaSalida,"MapaDepartamentos");
            if( f.exists() == false){
                f.mkdir();
            }
            file = new  File(f,"22_DEPARTAMENTOS.shp");
            url = new URL("http://www.ine.gob.gt/ftparchivos/pruebasMapas/MapaRepublica/22_DEPARTAMENTOS.shp");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            File f = new  File(rutaSalida,"MapaDepartamentos");
            if( f.exists() == false){
                f.mkdir();
            }
            file = new  File(f,"22_DEPARTAMENTOS.shx");
            System.out.println(f.getAbsolutePath());
            url = new URL("http://www.ine.gob.gt/ftparchivos/pruebasMapas/MapaRepublica/22_DEPARTAMENTOS.shx");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }
    
    public void mapasDepAutomaticos(){
        List mapas = new ArrayList();
        File[] listOfFiles = rutaCSV.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
        if (listOfFiles[i].isFile()) {
            if( FilenameUtils.getExtension(listOfFiles[i].getName().trim()).equalsIgnoreCase("csv") ){
                System.out.println("File " + listOfFiles[i].getName().trim() );
                mapas.add(listOfFiles[i].getName());
                Iterator iterator = mapas.iterator();
                while( iterator.hasNext() ){
                    hacerDepartamental((String) iterator.next());
                }
            }
        }else{
            System.out.println("ES: " + listOfFiles[i].getName());
        }
        }
    }
    
    
    public void mapasIPC(){
        List mapas = new ArrayList();
        mapas.add("2_04.csv");
        mapas.add("2_22.csv");
        mapas.add("2_25.csv");
        mapas.add("2_43.csv");
        mapas.add("2_46.csv");
        Iterator iterator = mapas.iterator();
        while( iterator.hasNext() ){
            hacerRegional((String) iterator.next());
        }
        
    }
    
    public void preprimaria(){
        List mapas = new ArrayList();
        mapas.add("1.9.csv");
        mapas.add("1.12.csv");
        mapas.add("1.6.csv");
        Iterator iterator = mapas.iterator();
        while( iterator.hasNext() ){
            hacerDepartamental((String) iterator.next());
        }
    }
    
    
    public void preprimaria1(){
        List mapas = new ArrayList();
        mapas.add("14.7.csv");
        mapas.add("1.12.csv");
        mapas.add("1.6.csv");
        Iterator iterator = mapas.iterator();
        while( iterator.hasNext() ){
            hacerDepartamental((String) iterator.next());
        }
    }
    
    
    
    public void terciaria(){
        List mapas = new ArrayList();
        mapas.add("17.3.csv");
        mapas.add("17.20.csv");
        Iterator iterator = mapas.iterator();
        while( iterator.hasNext() ){
            hacerDepartamental((String) iterator.next());
        }
    }
    
    
    public void diarrea(){
        List mapas = new ArrayList();
        mapas.add("10.8.csv");
        mapas.add("10.3.csv");
        Iterator iterator = mapas.iterator();
        while( iterator.hasNext() ){
            hacerDepartamental((String) iterator.next());
        }
    }
    
    public void primaria(){
        List mapas = new ArrayList();
        mapas.add("1.16.csv");
        mapas.add("1.19.csv");
        mapas.add("1.22.csv");
        mapas.add("1.25.csv");
        mapas.add("1.7.csv");
        mapas.add("1.10.csv");
        mapas.add("1.13.csv");
        Iterator iterator = mapas.iterator();
        while( iterator.hasNext() ){
            hacerDepartamental((String) iterator.next());
        }
    }
    
    
    
    public void primaria1(){
        List mapas = new ArrayList();
        mapas.add("15.3.csv");
        mapas.add("15.7.csv");
        mapas.add("15.12.csv");
        mapas.add("15.15.csv");
        mapas.add("15.20.csv");
        mapas.add("15.28.csv");
        mapas.add("1.13.csv");
        Iterator iterator = mapas.iterator();
        while( iterator.hasNext() ){
            hacerDepartamental((String) iterator.next());
        }
    }
    
    
        public void diversificado(){
        List mapas = new ArrayList();
        mapas.add("1.13.csv");
        mapas.add("1.16.csv");
        mapas.add("1.19.csv");
        mapas.add("1.22.csv");
        mapas.add("1.25.csv");
        mapas.add("1.7.csv");
        mapas.add("1.10.csv");
        Iterator iterator = mapas.iterator();
        while( iterator.hasNext() ){
            hacerDepartamental((String) iterator.next());
        }
    }
        
        public void basicos(){
        List mapas = new ArrayList();
        mapas.add("1.10.csv");
        mapas.add("1.13.csv");
        mapas.add("1.16.csv");
        mapas.add("1.19.csv");
        mapas.add("1.22.csv");
        mapas.add("1.25.csv");
        mapas.add("1.7.csv");
        Iterator iterator = mapas.iterator();
        while( iterator.hasNext() ){
            hacerDepartamental((String) iterator.next());
        }
    }
        
        public void secundaria(){
        List mapas = new ArrayList();
        mapas.add("16.3.csv");
        mapas.add("16.10.csv");
        mapas.add("16.13.csv");
        mapas.add("16.18.csv");
        mapas.add("16.29.csv");
        Iterator iterator = mapas.iterator();
        while( iterator.hasNext() ){
            hacerDepartamental((String) iterator.next());
        }
    }
    
        
           public void estructuraPoblacion(){
        List mapas = new ArrayList();
        mapas.add("1.3.csv");
        mapas.add("1.4.csv");
        mapas.add("1.7.csv");
        mapas.add("1.19.csv");
        mapas.add("1.23.csv");
        Iterator iterator = mapas.iterator();
        while( iterator.hasNext() ){
            hacerDepartamental((String) iterator.next());
        }
        
        }
           
        public void salud(){
        List mapas = new ArrayList();
        mapas.add("8.5.csv");
        mapas.add("8.10.csv");
        Iterator iterator = mapas.iterator();
        while( iterator.hasNext() ){
            hacerDepartamental((String) iterator.next());
        }
        
    }
        
        public void migracion(){
        List mapas = new ArrayList();
        mapas.add("2.3.csv");
        mapas.add("2.12.csv");
        mapas.add("2.15.csv");
        Iterator iterator = mapas.iterator();
        while( iterator.hasNext() ){
            hacerDepartamental((String) iterator.next());
            }
        }
        
        public void participacion(){
        List mapas = new ArrayList();
        mapas.add("4.7.csv");
        Iterator iterator = mapas.iterator();
        while( iterator.hasNext() ){
            hacerDepartamental((String) iterator.next());
            }
        }
        
        public void tipologia(){
        List mapas = new ArrayList();
        mapas.add("3.5.csv");
        mapas.add("3.8.csv");
        Iterator iterator = mapas.iterator();
        while( iterator.hasNext() ){
            hacerDepartamental((String) iterator.next());
            }
        }
        
        
        public void medios(){
        List mapas = new ArrayList();
        mapas.add("5.7.csv");
        mapas.add("5.12.csv");
        mapas.add("5.17.csv");
        Iterator iterator = mapas.iterator();
        while( iterator.hasNext() ){
            hacerDepartamental((String) iterator.next());
            }
        }
        
        public void inmunizacion(){
        List mapas = new ArrayList();
        mapas.add("14.7.csv");
        mapas.add("14.12.csv");
        mapas.add("14.17.csv");
        mapas.add("14.22.csv");
        mapas.add("14.27.csv");
        Iterator iterator = mapas.iterator();
        while( iterator.hasNext() ){
            hacerDepartamental((String) iterator.next());
            }
        }
        
         public void fecundidad(){
        List mapas = new ArrayList();
        mapas.add("12.9.csv");
        mapas.add("12.12.csv");
        mapas.add("12.15.csv");
        mapas.add("12.18.csv");
        mapas.add("12.23");
        mapas.add("12.26");
        mapas.add("12.29");
        mapas.add("12.34");
        mapas.add("12.37");
        mapas.add("12.40");
        mapas.add("12.43");
        Iterator iterator = mapas.iterator();
        while( iterator.hasNext() ){
            hacerDepartamental((String) iterator.next());
            }
        }
        
        
}
