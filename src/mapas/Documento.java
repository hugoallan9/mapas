/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author INE
 */
package mapas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils; 

public class Documento {


    private String titulo;
    private String mes;
    private String year;
    private String yearServidor;
    private String mesServidor;
    private String trimestre;
    private List junta;
    private List gerencias;
    private String ruta;
    private Double varAnual;
    private Double varMensual;
    private Double varAcumulada;

    public String getRuta() {
        return ruta;
    }
    private File tex;
    private File cuerpoReporte;
    private File cuerpoPresentacion;
    private String anioPublicacion;
    private DecimalFormat df;
    private DecimalFormat df2;
    private DecimalFormat df3;
    private String formatoTrimestre;

    public String getFormatoTrimestre() {
        return formatoTrimestre;
    }

    public String getFormatoSubtituloG() {
        return formatoSubtituloG;
    }
    private String formatoSubtituloG;

    protected void setVarAnual( Double valor ){
        this.varAnual = valor;
    }
    
    protected void setVarMensual( Double valor ){
        this.varMensual = valor;
    }
    
    protected void setVarAcumulada( Double valor ){
        this.varAcumulada = valor;
    }
    

    
    public  Documento(String titulo, String trimestre, String pYear){
        this.titulo = titulo;
        this.trimestre = trimestre;
        Calendar cal = Calendar.getInstance();
        this.mesServidor = new SimpleDateFormat("MMMM").format(cal.getTime());
        this.yearServidor = new SimpleDateFormat("yyyy").format(cal.getTime());
        junta = new  ArrayList();
        gerencias = new ArrayList();
        tex = null;
        this.anioPublicacion = pYear;
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        df =  (DecimalFormat) nf;
        df.applyPattern("###,###.#");
        df2 =  (DecimalFormat) nf;
        df2.applyPattern("###,###.##");
        df3 =  (DecimalFormat) nf;
        df3.applyPattern("###,###.###");
        formatoTrimestre =  corregirTrimestre(getTrimestre()).toLowerCase() + " trimestre del " + getAnioPublicacion();
        formatoSubtituloG =  corregirTrimestre(getTrimestre()) + " trimestre, año "+ getAnioPublicacion();
    }

    public DecimalFormat getDf() {
        df.applyPattern("###,###.#");
        return df;
    }
    
    public DecimalFormat getDf2(){
        df2.applyPattern("###,###.##");
        return df2;
    }
    
    public DecimalFormat getDf3(){
            df3.applyPattern("###,###.###");
            return(df3);
    }

    public String getAnioPublicacion() {
        return anioPublicacion;
    }
    
    public void setMes(String mes){
        this.mes = mes;
    }
    
    public void setYear(String year){
        this.year = year;
    }
    
    public String getYearServidor(){
       return(yearServidor); 
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMes() {
        return mes;
    }
    
    public String getMesServidor(){
        return mesServidor;
    }

    public String getYear() {
        return year;
    }

    public String getTrimestre() {
        return trimestre;
    }
    
    
    public void setRuta(String ruta) {
        this.ruta = ruta;
        this.cuerpoReporte = new File(ruta, "cuerpoReporte.tex");
        this.cuerpoPresentacion = new File(ruta, "cuerpoPresentacion.tex");
        File presentacion = new File(ruta,"GraficasPresentacion");
        if (!presentacion.exists()) {
            System.out.println("creating directory: " + presentacion.getAbsolutePath());
            boolean result = false;

        try{
            presentacion.mkdir();
            result = true;
        } 
        catch(SecurityException se){
        //handle it
        }        
        if(result) {    
            System.out.println("DIR created");  
        }
}
    }
    
    public void setTex(String tex){
        this.tex = new File(ruta , tex + ".tex");
    }
    
    
    public File getTex(){
        return tex;
    }
    
    
    protected void iniciarDocumento(){
        try {
            tex.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            FileWriter escritora = new FileWriter(tex,true);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write("\\renewcommand{\\titulodoc}{" + titulo + " " + trimestreToSimbolo(trimestre)+ "-"+anioPublicacion +"}");
            buffer.write("\\begin{document}\n");
            buffer.write("\\includepdf{caratula.pdf}");
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void iniciarDocumentoAnual(){
        try {
            tex.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            FileWriter escritora = new FileWriter(tex,true);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write("\\renewcommand{\\titulodoc}{" + titulo + " " + " -  " + getMes()+ " " + anioPublicacion +"}");
            buffer.write("\\definecolor{color1}{rgb}{0,0,0.8}\n" +
            "\\definecolor{color2}{rgb}{0.3,0.5,1}");
            buffer.write("\\usepackage{booktabs}\n" +
            "\\usepackage{multirow} \n" +
            "\\usepackage{lscape} \n" +
            "\n" +
            "\n" +
            "\\newcommand{\\ra}[1]{\\renewcommand{\\arraystretch}{#1}}\n" +
            "\n" +
            "\\newcommand{\\mesreportado}{"+ getMes()+ "}\n" +
            "\\newcommand{\\varmens}{"+ varMensual +  "}\n" +
            "\\newcommand{\\varanu}{"+ varAnual  +"}\n" +
            "\\newcommand{\\varacu}{"+ varAcumulada   +"}\n\n");
            buffer.write("\n\n\n  %#########INICIO DE DOCUMENTO#################\n");
            buffer.write("\\begin{document}\n");
            buffer.write("\\includepdf{caratula.pdf}");
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void terminarDocumento(){
        try {
            FileWriter escritora = new FileWriter(tex,true);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write("\n \\includepdf{contraPortada.pdf} \n");
            buffer.write("\n \\end{document}\n");
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    protected void juntaDirectiva(){
        try {
            Document doc = Jsoup.connect("http://www.ine.gob.gt/index.php/institucion/organizacion").get();
            Elements tables =  doc.select("tbody");
            Element juntaDirectiva = tables.get(0);
            Elements miembros = juntaDirectiva.select("tr");
            String[] temp;
            for( int i = 0 ; i < miembros.size(); i++ ){
                String[] partes = miembros.get(i).text().split("Suplente");
                if(i%2 != 0){
                    junta.add(partes[0]);
                    junta.add("Suplente" + partes[1]);
                }else{
                    junta.add(partes[0]);
                }
                
            }
            
            Element  tablaGerente = tables.get(1);
            Elements gerente = tablaGerente.select("tr");
            gerencias.add(gerente.get(0).text().split("Gerente")[1]);
            
            Element tablaGerencias = tables.get(2);
            Elements subgerencias = tablaGerencias.select("td");
            gerencias.add(subgerencias.get(0).text().split("[Ss]ubgerencia [Aa]dministrativa [Ff]inanciera")[1]);
            gerencias.add(subgerencias.get(1).text().split("[Ss]ubgerencia [Tt][ée]cnica")[1]);
            System.out.println(gerencias.get(1));
            System.out.println(gerencias.get(2));
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            FileWriter escritora = new FileWriter(tex,true);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write("\\hoja{\n" +
            "	$\\ $\n" +
            "	\\vspace{0.3cm}\n" +
            "	\n" +
            "	\\begin{center}\n" +
            "		{\\Bold \\LARGE AUTORIDADES}\\\\[0.7cm]\n" +
            "		\n" +
            "		\n" +
            "		{\\Bold \\large \\color{color1!89!black} JUNTA  DIRECTIVA} \\\\[0.5cm]\n" +
            "		\n" +
            "		\\begin{center}\n" +
            "			\\begin{tabular}{x{7.0cm}x{7.0cm}}\n");
            buffer.write("\t \t \t { \\Bold Ministerio de Economía}   & 		{\\Bold Ministerio de Finanzas}\\\\ \n");
            buffer.write( "\t \t \t " +  junta.get(junta.indexOf("Ministerio de Economía") + 1 )  +" & "+ junta.get(junta.indexOf("Ministerio de Finanzas Públicas") + 1 )  +"  \\\\ \n");
            buffer.write( "\t \t \t " +  junta.get(junta.indexOf("Ministerio de Economía") + 2 )  +" & "+ junta.get(junta.indexOf("Ministerio de Finanzas Públicas") + 2 )  +"  \\\\ \n");
            buffer.write("\t \t \t & \\\\\n" +
"				{\\Bold Ministerio de Agricultura,} & {\\Bold Ministerio de Energía y Minas}\\\\ \n" +
"				{\\Bold Ganadería y Alimentación} & "+  junta.get(junta.indexOf("Ministerio de Energía y Minas")+1) +"\\\\ \n");
            buffer.write( "\t \t \t " +  junta.get(junta.indexOf("Ministerio de Agricultura, Ganadería y Alimentación") + 1 )  +" & "+ junta.get(junta.indexOf("Ministerio de Energía y Minas") + 2 )  +"  \\\\ \n");
            buffer.write( "\t \t \t " +  junta.get(junta.indexOf("Ministerio de Agricultura, Ganadería y Alimentación") + 2 )  +" &   \\\\ \n");
            buffer.write("\t \t \t & {\\Bold Banco de Guatemala} \\\\ \n");
            buffer.write("\t \t \t {\\Bold Secretaría de Planificación y} &"+ junta.get(junta.indexOf("Banco de Guatemala") + 1 )   + "\\\\\n" +
"				{\t \t \t \\Bold Programación de la Presidencia} & "  + junta.get(junta.indexOf("Banco de Guatemala") + 2 )  + " \\\\\n \t \t \t "     +
                                 junta.get(junta.indexOf("Secretaría de Planificación y Programación de la Presidencia") + 1 ) +" & \\\\ \n");
            buffer.write("\t \t \t " + junta.get(junta.indexOf("Secretaría de Planificación y Programación de la Presidencia") + 2 ) +  " & {\\Bold Universidad de San Carlos de Guatemala} \\\\ \n");
            buffer.write("&" + junta.get(junta.indexOf("Universidad de San Carlos de Guatemala") + 1 )   +  "  \\\\\n" +
"				{\\Bold Comité Coordinador de } &" +  junta.get(junta.indexOf("Universidad de San Carlos de Guatemala") + 2 )+   " \\\\  \n" +
"				{\\Bold Asociaciones  Agrícolas, Comerciales, } & \\\\\n" +
"				{\\Bold Industriales y Financieras} & {\\Bold Universidades Privadas} \\\\\n" +
                                junta.get(junta.indexOf("Comité Coordinador de Asociaciones Agrícolas, Comerciales, Industriales y Financieras") +1 ) + " & " + junta.get(junta.indexOf("Universidades Privadas") + 1) +   "\\\\\n" +
				junta.get(junta.indexOf("Comité Coordinador de Asociaciones Agrícolas, Comerciales, Industriales y Financieras") +2 ) + " & " + junta.get(junta.indexOf("Universidades Privadas") + 2) +   "\\\\\n" +
"			\\end{tabular} 	\n" +
"		\\end{center} \n	");
            buffer.write("	\n" +
"		{\\Bold \\large \\color{color1!89!black} GERENCIA}\\\\[0.2cm]\n" +
                "Gerente: " + gerencias.get(0) +   "		\\\\\n" +
                "Subgerente Técnico: " + gerencias.get(2) + 		"\\\\\n" +
                "Subgerente Administrativo Financiero: " + gerencias.get(1) +  "\\\\ \n");
            buffer.write("\t \t \t \\end{center}\n");
            buffer.write("\t \t } \n");
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    protected void juntaDirectivaAnual(){
        try {
            Document doc = Jsoup.connect("http://www.ine.gob.gt/index.php/institucion/organizacion").get();
            Elements tables =  doc.select("tbody");
            Element juntaDirectiva = tables.get(0);
            Elements miembros = juntaDirectiva.select("tr");
            String[] temp;
            for( int i = 0 ; i < miembros.size(); i++ ){
                String[] partes = miembros.get(i).text().split("Suplente");
                if(i%2 != 0){
                    junta.add(partes[0]);
                    junta.add("Suplente" + partes[1]);
                }else{
                    junta.add(partes[0]);
                }
                
            }
            
            Element  tablaGerente = tables.get(1);
            Elements gerente = tablaGerente.select("tr");
            gerencias.add(gerente.get(0).text().split("Gerente")[1]);
            
            Element tablaGerencias = tables.get(2);
            Elements subgerencias = tablaGerencias.select("td");
            gerencias.add(subgerencias.get(0).text().split("[Ss]ubgerencia [Aa]dministrativa [Ff]inanciera")[1]);
            gerencias.add(subgerencias.get(1).text().split("[Ss]ubgerencia [Tt][ée]cnica")[1]);
            System.out.println(gerencias.get(1));
            System.out.println(gerencias.get(2));
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            FileWriter escritora = new FileWriter(tex,true);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write("\n" +
            "	$\\ $\n" +
            "	\\vspace{0.0cm}\n" +
            "	\n" +
            "	\\begin{center}\n" +
            "		{\\Bold \\LARGE AUTORIDADES}\\\\[1cm]\n" +
            "		\n" +
            "		\n" +
            "		{\\Bold \\large \\color{color1!89!black} JUNTA  DIRECTIVA} \\\\[0.4cm]\n" +
            "		\n");
            buffer.write("\t \t \t { \\Bold Ministerio de Economía}  		\\\\ \n");
            buffer.write( "\t \t \t " +  junta.get(junta.indexOf("Ministerio de Economía") + 1 )  + "  \\\\ \n");
            buffer.write( "\t \t \t " +  junta.get(junta.indexOf("Ministerio de Economía") + 2 )  +"  \\\\ [0.4cm] \n\n");
            
            buffer.write("\t \t \t {\\Bold Ministerio de Finanzas} \\\\ \n");
            buffer.write("\t \t \t " + junta.get(junta.indexOf("Ministerio de Finanzas Públicas") + 1 )  + "\\\\ \n");
            buffer.write("\t \t \t" + junta.get(junta.indexOf("Ministerio de Finanzas Públicas") + 2 )  + "\\\\[0.4cm] \n\n ");
            
            
            buffer.write("\t \t \t " +
"				{\\Bold Ministerio de Agricultura, Ganadería y Alimentación} \\\\ \n ");
            buffer.write( "\t \t \t " +  junta.get(junta.indexOf("Ministerio de Agricultura, Ganadería y Alimentación") + 1 )  + "  \\\\ \n");
            buffer.write( "\t \t \t " +  junta.get(junta.indexOf("Ministerio de Agricultura, Ganadería y Alimentación") + 2 )  +" \\\\ [0.4cm] \n\n");
            
            buffer.write("\t \t \t " + "{\\Bold Ministerio de Energía y Minas}\\\\ \n");
            buffer.write("\t\t\t" +   junta.get(junta.indexOf("Ministerio de Energía y Minas")+1) +"\\\\ \n");
            buffer.write("\t\t\t" + junta.get(junta.indexOf("Ministerio de Energía y Minas") + 2 ) + "\\\\ [0.4cm]\n");
            
            
            
            buffer.write("\t \t \t {\\Bold Secretaría de Planificación y Programación de la Presidencia}  " + " \\\\\n \t \t \t ");     
            buffer.write("\t\t\t " + junta.get(junta.indexOf("Secretaría de Planificación y Programación de la Presidencia") + 1 ) +"  \\\\ \n");
            buffer.write("\t \t \t " + junta.get(junta.indexOf("Secretaría de Planificación y Programación de la Presidencia") + 2 ) + "\\\\ [0.4cm] \n");        
            
            
                  
            
            buffer.write("\t \t \t  {\\Bold Banco de Guatemala} \\\\ \n");
            buffer.write("\t\t\t " +junta.get(junta.indexOf("Banco de Guatemala") + 1 ) +"\\\\ \n");
            buffer.write("\t\t\t " + junta.get(junta.indexOf("Banco de Guatemala") + 2 ) +"\\\\ [0.4cm] \n");
            
              
            buffer.write("\t \t \t  {\\Bold Universidad de San Carlos de Guatemala de Guatemala} \\\\ \n");
            buffer.write("\t\t\t" + junta.get(junta.indexOf("Universidad de San Carlos de Guatemala") + 1 )   +  "  \\\\\n");
            buffer.write("\t\t\t" + junta.get(junta.indexOf("Universidad de San Carlos de Guatemala") + 2 )   +  "  \\\\ [0.4cm]\n");
            
            buffer.write("{\\Bold Universidades Privadas} \\\\\n");
            buffer.write("\t\t\t "  + junta.get(junta.indexOf("Universidades Privadas") + 1)  + "\\\\");
            buffer.write("\t\t\t "  + junta.get(junta.indexOf("Universidades Privadas") + 2)  + "\\\\ [0.4cm] \n");
            
            buffer.write("\t\t\t {\\Bold Comité Coordinador de \\ Asociaciones  Agrícolas, Comerciales,Industriales y Financieras} \\\\ \n");  
            buffer.write("\t\t\t" + junta.get(junta.indexOf("Comité Coordinador de Asociaciones Agrícolas, Comerciales, Industriales y Financieras") +1 ) +   "\\\\\n");
            buffer.write("\t\t\t " + junta.get(junta.indexOf("Comité Coordinador de Asociaciones Agrícolas, Comerciales, Industriales y Financieras") +2 ) +  "\\\\ [0.4cm]\n" );			  
      
            
            
            buffer.write("	\n" +
"		{\\Bold \\large \\color{color1!89!black} GERENCIA}\\\\[0.2cm]\n" +
                "Gerente: " + gerencias.get(0) +   "		\\\\\n" +
                "Subgerente Técnico: " + gerencias.get(2) + 		"\\\\\n" +
                "Subgerente Administrativo Financiero: " + gerencias.get(1) +  "\\\\ \n");
            buffer.write("\t \t \t \\end{center}\n");
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    protected void equipoYPresentacion(Double anual, Double mensual, Double acumulada){
        
        
        escribirLinea("\\clearpage\n" +
                "\n" +
                "$\\ $\n" +
                "\\vspace{1cm}\n" +
                "\n" +
                "\\begin{center}\n" +
                "	{\\Bold \\LARGE EQUIPO RESPONSABLE}\\\\[2cm]\n" +
                "	\n" +
                "	{\\Bold \\large \\color{color1!89!black} REVISIÓN GENERAL}\\\\[0.2cm]\n" +
                "	Rubén Narciso\\\\[0.8cm]\n" +
                "	\n" +
                "	\n" +
                "	{\\Bold \\large \\color{color1!89!black} EQUIPO TÉCNICO}\\\\[0.2cm]\n" +
                "	Eiji Arturo De Paz Pérez \\\\[0.8cm] \n" +
                "       Edwin Rafael Tut Rocael \\\\[0.8cm]" +
                "	\n" +
                "	{\\Bold \\large \\color{color1!89!black} DIAGRAMACIÓN Y DISEÑO}\\\\[0.2cm]\n" +
                "	Hugo Allan García Monterrosa\\\\\n" +
                "	Fabiola Beatriz Ramírez Pinto\\\\\n" +
                "	José Carlos Bonilla Aldana\\\\[0.8cm]\n" +
                "	\n" +
                "	\n" +
                "	\n" +
                "\\end{center}\\setcounter{page}{0}\\cleardoublepage\n" +
                "\n" +
                "\n" +
                "\n" +
                "$\\ $\\\\[1cm]\n" +
                "\n" +
                "\\tableofcontents\n" +
                "\n" +
                "\\cleardoublepage\n" +
                "\\pagestyle{estandar}\n" +
                "\\setcounter{page}{1}\n" +
                "\\setlength{\\arrayrulewidth}{1.0pt}\n" +
                "\n" +
                "\n" +
                "\\cleardoublepage\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "$\\ $\\\\[0.5cm]\n" +
                "\\thispagestyle{empty}\n" +
                "\\noindent {\\Bold \\huge Presentación}\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "$\\ $\\\\[0.5cm]\n" +
                "\\large"+
                "$\\ $\\\\\n" +
                "El presente informe mensual, contiene los principales resultados "
                + "del Índice de Precios al Consumidor (IPC) del Instituto Nacional de Estadística "
                + "-INE-.  Como indicador macroeconómico, este dato se utiliza para "
                + "medir el comportamiento del nivel general de precios de la economía "
                + "del país, tomando como base los precios observados en el mes de referencia. "+
                "\n" +
                "Los niveles de inflación más importantes de {\\Bold " + getMes() + " de " + getYear() +" }"
                +"son los siguientes: se registró una variación intermensual de " + String.valueOf(mensual) +"\\%, una variación "
                + "interanual de " +  String.valueOf(anual) + "\\% y una variación acumulada de "+  String.valueOf(acumulada)+"\\%. " +
                "Al final  se incluye un glosario que contiene la definición "
                + "de los principales conceptos relacionados con el IPC y la metodología"
                + " de cálculo para la obtención de los diferentes índices y variaciones."
                + ""
                + "\n" +
                "$\\ $"
                +"\\thispagestyle{empty}\n" +
                "\n" +
                "\n" +
                "\\cleardoublepage");
    }
    
    
    protected void preambuloPresentacion(){
    
    }
    
    
    protected void preambulo(){
       File file = null; 
       URL url = null;
        try {
            file = new  File(ruta,"preambulo.tex");
            url = new URL("http://www.ine.gob.gt/ftparchivos/Oficio/preambulo.tex");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            file = new  File(ruta,"cabezae.pdf");
            url = new URL("http://www.ine.gob.gt/ftparchivos/Oficio/cabezae.pdf");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        try {
            file = new  File(ruta,"cabezao.pdf");
            url = new URL("http://www.ine.gob.gt/ftparchivos/Oficio/cabezao.pdf");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            file = new  File(ruta,"capitulo3.pdf");
            url = new URL("http://www.ine.gob.gt/ftparchivos/Oficio/capitulo3.pdf");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            file = new  File(ruta,"pie.png");
            url = new URL("http://www.ine.gob.gt/ftparchivos/Oficio/pie.png");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        try {
            FileWriter escritora = new FileWriter(tex,false);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write("\\input{preambulo}\n");
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void preambuloAnual(){
       File file = null; 
       URL url = null;
        try {
            file = new  File(ruta,"Carta3.tex");
            url = new URL("http://www.ine.gob.gt/ftparchivos/Carta/Carta3.tex");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            file = new  File(ruta,"encabezado.pdf");
            url = new URL("http://www.ine.gob.gt/ftparchivos/Carta/encabezado.pdf");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        try {
            file = new  File(ruta,"encabezadopar.pdf");
            url = new URL("http://www.ine.gob.gt/ftparchivos/Carta/encabezadopar.pdf");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            file = new  File(ruta,"fondo-capitulo.pdf");
            url = new URL("http://www.ine.gob.gt/ftparchivos/Carta/fondo-capitulo.pdf");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            file = new  File(ruta,"fondo-capitulo-no-descripcion.pdf");
            url = new URL("http://www.ine.gob.gt/ftparchivos/Carta/fondo-capitulo-no-descripcion.pdf");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            file = new  File(ruta,"topeven3.pdf");
            url = new URL("http://www.ine.gob.gt/ftparchivos/Carta/topeven3.pdf");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            file = new  File(ruta,"topodd3.pdf");
            url = new URL("http://www.ine.gob.gt/ftparchivos/Carta/topodd3.pdf");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        try {
            FileWriter escritora = new FileWriter(tex,false);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write("\\input{Carta3}\n");
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    protected void hacerTitulo(){
        try{
           FileWriter escritora = new FileWriter(tex,true);
           BufferedWriter buffer = new BufferedWriter(escritora);
           buffer.write("$\\ $\n" +
           "\\vspace{4.5cm}\n" +
           "\n" +
           "\\noindent\\begin{tabular}{p{0.1cm}p{6.8cm}}\n" +
           "	& " + year +  ".$\\,$ Guatemala, América Central \\\\\n" +
           "	&\\Bold Instituto Nacional de Estadística\\\\[-0.4cm]\n" +
           "	&\\color{blue!50!black}\\url{www.ine.gob.gt}\\\\[0.9cm]\n" +
           "\\end{tabular}\\\\\n" +
           "\\noindent\\begin{tabular}{p{0.1cm}p{6.8cm}}\n" +
           "	& Está permitida la reproducción parcial o total de los contenidos de este documento con la mención de la fuente. \\\\[0.5cm]\n" +
           "	\n" +
           "	& Este documento fue elaborado empleando  {\\Sans R}, Inkscape y {\\Logos \\XeLaTeX}.\\\\\n" +
           "\\end{tabular} \n" +
           "\n" +
           "\n" +
           "\\clearpage");
           
           buffer.write("$\\ $\n" +
          "\\vspace{3.5cm}\n" +
          "\n" +
          "\\begin{center}\n" +
          "	\\Bold \\LARGE REPÚBLICA DE GUATEMALA\\\\\n" +
          "{\\MakeUppercase{\\titulodoc}}\n"+
                   "\\end{center}\n" +
          "\\cleardoublepage");
           buffer.close();
        } catch(IOException ex){
            System.out.println(ex);
        }
    }
    
    
    protected void hacerTituloAnual(){
        try{
           FileWriter escritora = new FileWriter(tex,true);
           BufferedWriter buffer = new BufferedWriter(escritora);
           buffer.write("$\\ $\n" +
           "\\vspace{14.5cm}\n" +
           "\n" +
           "\\noindent\\begin{tabular}{p{0.9cm}p{6.8cm}}\n" +
           "	& " + year +  ".$\\,$ Guatemala, América Central \\\\\n" +
           "	&\\Bold Instituto Nacional de Estadística\\\\[-0.4cm]\n" +
           "	&\\color{blue!50!black}\\url{www.ine.gob.gt}\\\\[0.9cm]\n" +
           "\\end{tabular}\\\\\n" +
           "\\noindent\\begin{tabular}{p{0.9cm}p{6.8cm}}\n" +
           "	& Está permitida la reproducción parcial o total de los contenidos de este documento con la mención de la fuente. \\\\[0.5cm]\n" +
           "	\n" +
           "	& Este documento fue elaborado empleando  {\\Sans R}, Inkscape y {\\Logos \\XeLaTeX}.\\\\\n" +
           "\\end{tabular} \n" +
           "\n" +
           "\n" +
           "\\clearpage");
           
           buffer.write("$\\ $\n" +
          "\\vspace{7cm}\n" +
          "\n" +
          "\\begin{center}\n" +
          "	\\Bold \\LARGE {\\MakeUppercase{\\titulodoc}} \\\\\n" +
                   "\\end{center}\n" +
          "\\cleardoublepage");
           buffer.close();
        } catch(IOException ex){
            System.out.println(ex);
        }
    }
    
    protected String trimestreToSimbolo(String trimestre){
        String simbolo = null;
        System.out.println(this.trimestre);
        if(trimestre.equalsIgnoreCase("primero")){
            simbolo = "T1";
        }else if(trimestre.equalsIgnoreCase("segundo")){
            simbolo = "T2";
        }else if(trimestre.equalsIgnoreCase("tercero")){
            simbolo = "T3";
        }else if(trimestre.equalsIgnoreCase("cuarto")){
            simbolo = "T4";
        }else{
            simbolo = "ERROR" ;
        }
         return simbolo;   
        
    }
    
    protected String  simboloToTrimestre(String simbolo){
        String trimestre = null;
        if(simbolo.equalsIgnoreCase("T1")){
            trimestre = "primer";
        }else if(simbolo.equalsIgnoreCase("T2")){
            trimestre = "segundo";
        }else if(simbolo.equalsIgnoreCase("T3")){
            trimestre  = "tercer";
        }else if(simbolo.equalsIgnoreCase("T4")){
            trimestre = "cuarto";
        }else{
            System.err.println(simbolo + "NO ESCRIBISTE UN SIMBOLO CORRECTO");
        }
        return trimestre;
    }
    
    protected void escribirCapitulo(String nombreIndice, String nombre1, String nombre2, String contenido){
        FileWriter escritora;
        try {
            escritora = new FileWriter(tex,true);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(" \n \\INEchapter{" + nombreIndice + " }{" + nombre1 + "}{"
                   + nombre2 + "}{" + contenido + "}%\n");
            buffer.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    protected void escribirCapituloAnual(String nombreIndice, String nombreReal){
        FileWriter escritora;
        try {
            escritora = new FileWriter(tex,true);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(" \n \\INEchaptercarta{" + nombreIndice + " }{" + nombreReal + "}\n");
            buffer.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected String hojaTrimestral(String columna1, String columna2){
        return("\\hojados{" + columna1 +"}{" + columna2 + "}");
    }
    
    protected String columna(String codigo, String nombreSeccion, String parrafo1, String parrafo2, 
            String tituloGrafica, String tipoGrafica, String grafica, String fuente, String pie, Boolean presentacion){
        File f = new File(tex.getParent(), codigo);
        if( !f.exists() ){
            System.out.println("La carpeta no existe: " + f.getAbsolutePath());
            f.mkdir();
        }
        
        File titulo = new File(f, "titulo.tex");
        File primeraDescripcion = new File(f, "parrafo1.tex");
        File segundaDescripcion = new File(f,"parrafo2.tex");
        File titleGrafica = new File(f,"tituloGrafica.tex");
        File desGrafica = new File(f, "desGrafica.tex");
        File desFuente = new File(f, "fuente.tex");
        
        FileWriter escritora;
        try {
            escritora = new FileWriter(titulo);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(nombreSeccion);
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            escritora = new FileWriter(primeraDescripcion);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(parrafo1);
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            escritora = new FileWriter(segundaDescripcion);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(parrafo2);
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            escritora = new FileWriter(titleGrafica);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(tituloGrafica);
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            escritora = new FileWriter(desGrafica);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(tipoGrafica);
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            escritora = new FileWriter(desFuente);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(fuente);
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(primeraDescripcion.getParent().replaceAll("\\\\", "/"));
        String extension = grafica.substring(grafica.length()-3, grafica.length());
        String formato = null;
        String formatoP = null;
        String carpeta = null;
        if(presentacion == true){
            if( extension.equalsIgnoreCase("pdf") ){
                System.out.println("***********ES UN PDF PARA PRESENTACION Y ENTRE AL SI*****************************");
                formatoP = "\\includegraphics[scale=1.15]{GraficasPresentacion/"+  grafica+"}";
            }else{
                formatoP = "\\begin{tikzpicture}[x=1pt,y=1pt,scale = 1.15]  \\input{GraficasPresentacion/" + grafica +"} \\end{tikzpicture}";
            }
        }
            if( extension.equalsIgnoreCase("pdf") ){
                System.out.println("***********ES UN PDF Y ENTRE AL SI*****************************");
                formato = "\\includegraphics{"+  grafica+"}";
            }else{
                formato = "\\begin{tikzpicture}[x=1pt,y=1pt]  \\input{" + grafica +"} \\end{tikzpicture}";
            }
        System.out.println("PARA EL CODIGO " + codigo + " la extension es: " + extension + " ADEMAS FORMATO ES " + formato + " Y FORMATOP ES " + formatoP);
        
        String retorno =  "\n \\columna{%\n"
                + nombreSeccion+ "}%\n{"
                + "%\n \\input{" + (codigo + "/parrafo1.tex").replaceAll("\\\\", "/") + "}}%\n"
                + "{%\n \\input{" + (codigo + "/parrafo2.tex").replaceAll("\\\\","/") + "}} %\n"
                + "{%\n \\input{" + (codigo + "/tituloGrafica.tex").replaceAll("\\\\", "/") +   "}} %\n"
                + "{%\n \\input{" + (codigo + "/desGrafica.tex").replaceAll("\\\\","/") +   "}} %\n"
                + "{%\n"+ formato + "   }%\n"
                + "{%\n \\input{" + (codigo + "/fuente.tex").replaceAll("\\\\","/") + "}} %\n "
                + "{%\n " + pie+"}%\n";
        
        String retorno1 =  "\n \\columna{%\n"
                + nombreSeccion+ "}%\n{"
                + "%\n \\input{" + (codigo + "/parrafo1.tex").replaceAll("\\\\", "/") + "}}%\n"
                + "{%\n \\input{" + (codigo + "/parrafo2.tex").replaceAll("\\\\","/") + "}} %\n"
                + "{%\n \\input{" + (codigo + "/tituloGrafica.tex").replaceAll("\\\\", "/") +   "}} %\n"
                + "{%\n \\input{" + (codigo + "/desGrafica.tex").replaceAll("\\\\","/") +   "}} %\n"
                + "{%\n"+ formatoP + "   }%\n"
                + "{%\n \\input{" + (codigo + "/fuente.tex").replaceAll("\\\\","/") + "}} %\n "
                + "{%\n " + pie+"}%\n";
        
        if(presentacion == true){
            escribirLineaPresentacion(retorno1);
        }
        
        return(retorno);
        
    }
    
    
    protected String tablaApendice(String codigo, String nombreSeccion, String tipo,String tabla, String fuente, String pie){
        File f = new File(tex.getParent(), codigo);
        if( !f.exists() ){
            System.out.println("La carpeta no existe: " + f.getAbsolutePath());
            f.mkdir();
        }
        
        File titulo = new File(f, "titulo.tex");
        File desFuente = new File(f, "fuente.tex");
        
        FileWriter escritora;
        try {
            escritora = new FileWriter(titulo);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(nombreSeccion);
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }

        

        

        
        try {
            escritora = new FileWriter(desFuente);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(fuente);
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return("\n \\columnatablaprovisional{%\n"
                + nombreSeccion+ "}%\n{"
                + "%\n \\input{" + "}}%\n"
                + "{%\n \\input{" + "}} %\n"
                + "{%\n \\input{" + tipo +   "}} %\n"
                + "{%\n \\input{"  +   "}} %\n"
                + "{%\n \\includegraphics[width=26\\cuadri]{" + (codigo + "/"+ tabla).replaceAll("\\\\","/")  + "}}%\n"
                + "{%\n \\input{" + (codigo + "/fuente.tex").replaceAll("\\\\","/") + "}} %\n "
                + "{%\n " + pie+"}%\n");
    }
    
    
    
    
    
    protected String seccion(String codigo, String nombreSeccion, String descripcion, 
            String tituloGrafica, String tipoGrafica,
            String grafica, String fuente) {
    
        File f = new File(tex.getParent(), codigo);
        if( !f.exists() ){
            System.out.println("La carpeta no existe: " + f.getAbsolutePath());
            f.mkdir();
        }
        
        File titulo = new File(f, "titulo.tex");
        File primeraDescripcion = new File(f, "descripcion.tex");
        File titleGrafica = new File(f,"tituloGrafica.tex");
        File desGrafica = new File(f, "desGrafica.tex");
        File desFuente = new File(f, "fuente.tex");
        
        FileWriter escritora;
        try {
            escritora = new FileWriter(titulo);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(nombreSeccion);
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        try {
//            escritora = new FileWriter(primeraDescripcion);
//            BufferedWriter buffer = new BufferedWriter(escritora);
//            buffer.write(descripcion);
//            buffer.close();
//        } catch (IOException ex) {
//            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
        
        
        try {
            escritora = new FileWriter(titleGrafica);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(tituloGrafica);
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            escritora = new FileWriter(desGrafica);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(tipoGrafica);
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            escritora = new FileWriter(desFuente);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(fuente);
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return("\n \\cajita{%\n"
                + nombreSeccion+ "}%\n{"
                + "%\n \\input{" + (codigo + "/descripcion.tex").replaceAll("\\\\", "/") + "}}%\n"
                + "{%\n \\input{" + (codigo + "/tituloGrafica.tex").replaceAll("\\\\", "/") +   "}} %\n"
                + "{%\n \\input{" + (codigo + "/desGrafica.tex").replaceAll("\\\\","/") +   "}} %\n"
                + "{%\n " + grafica + "}%\n"
                + "{%\n \\input{" + (codigo + "/fuente.tex").replaceAll("\\\\","/") + "}} %\n ");
        
    }
    
    
        protected String cajotaMapa(String codigo, String nombreSeccion, String descripcion, 
            String tituloGrafica, String tipoGrafica,
            String tabla, String fuente) {
    
        File f = new File(tex.getParent(), codigo);
        if( !f.exists() ){
            System.out.println("La carpeta no existe: " + f.getAbsolutePath());
            f.mkdir();
        }
        
        File titulo = new File(f, "titulo.tex");
        File primeraDescripcion = new File(f, "descripcion.tex");
        File titleGrafica = new File(f,"tituloGrafica.tex");
        File desGrafica = new File(f, "desGrafica.tex");
        File desFuente = new File(f, "fuente.tex");
        
        FileWriter escritora;
        try {
            escritora = new FileWriter(titulo);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(nombreSeccion);
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        try {
//            escritora = new FileWriter(primeraDescripcion);
//            BufferedWriter buffer = new BufferedWriter(escritora);
//            buffer.write(descripcion);
//            buffer.close();
//        } catch (IOException ex) {
//            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
        
        
        try {
            escritora = new FileWriter(titleGrafica);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(tituloGrafica);
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            escritora = new FileWriter(desGrafica);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(tipoGrafica);
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            escritora = new FileWriter(desFuente);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(fuente);
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return("\n \\cajota{%\n"
                + nombreSeccion+ "}%\n{"
                + "%\n \\input{" + (codigo+"/descripcion.tex").replaceAll("\\\\", "/") + "}}%\n"
                + "{%\n \\input{" + (codigo+"/tituloGrafica.tex").replaceAll("\\\\", "/") +   "}} %\n"
                + "{%\n \\input{" + (codigo+"/desGrafica.tex").replaceAll("\\\\","/") +   "}} %\n"
                + "{%\n " + tabla + "}%\n"
                + "{%\n \\input{" + (codigo+"/fuente.tex").replaceAll("\\\\","/") + "}} %\n ");
        
    }
    
    protected String cajotaTabla(String codigo, String nombreSeccion, String descripcion, 
            String tituloGrafica, String tipoGrafica,
            String tabla, String fuente) {
    
        File f = new File(tex.getParent(), codigo);
        if( !f.exists() ){
            System.out.println("La carpeta no existe: " + f.getAbsolutePath());
            f.mkdir();
        }
        
        File titulo = new File(f, "titulo.tex");
        File primeraDescripcion = new File(f, "descripcion.tex");
        File titleGrafica = new File(f,"tituloGrafica.tex");
        File desGrafica = new File(f, "desGrafica.tex");
        File desFuente = new File(f, "fuente.tex");
        
        FileWriter escritora;
        try {
            escritora = new FileWriter(titulo);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(nombreSeccion);
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        try {
//            escritora = new FileWriter(primeraDescripcion);
//            BufferedWriter buffer = new BufferedWriter(escritora);
//            buffer.write(descripcion);
//            buffer.close();
//        } catch (IOException ex) {
//            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
        
        
        try {
            escritora = new FileWriter(titleGrafica);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(tituloGrafica);
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            escritora = new FileWriter(desGrafica);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(tipoGrafica);
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            escritora = new FileWriter(desFuente);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(fuente);
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return("\n \\cajotatabla{%\n"
                + nombreSeccion+ "}%\n{"
                + "%\n \\input{" + (codigo+"/descripcion.tex").replaceAll("\\\\", "/") + "}}%\n"
                + "{%\n \\input{" + (codigo+"/tituloGrafica.tex").replaceAll("\\\\", "/") +   "}} %\n"
                + "{%\n \\input{" + (codigo+"/desGrafica.tex").replaceAll("\\\\","/") +   "}} %\n"
                + "{%\n " + tabla + "}%\n"
                + "{%\n \\input{" + (codigo+"/fuente.tex").replaceAll("\\\\","/") + "}} %\n ");
        
    }
    
    
    protected  void escribirLinea(String linea){
        FileWriter escritora;
        try {
            escritora = new FileWriter(tex,true);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(linea);
            buffer.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    protected  void escribirLineaPresentacion(String linea){
        FileWriter escritora;
        try {
            escritora = new FileWriter(cuerpoPresentacion,true);
            BufferedWriter buffer = new BufferedWriter(escritora);
            buffer.write(linea);
            buffer.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    protected String corregirTrimestre(String trimestre){
        String trim = null;
        if(trimestre.equalsIgnoreCase("primero")){
            trim = "Primer";
        }else if(trimestre.equalsIgnoreCase("segundo")){
            trim = "Segundo";
        }else if(trimestre.equalsIgnoreCase("tercero")){
            trim = "Tercer";
        }else{
            trim = "Cuarto";
        }
        return trim;
    }

    
        protected void compilar(SesionR rr, String ruta, String mostrar){
            rr.get().eval("compilar('" + ruta + "',"+ mostrar+")" );
            rr.get().end();
        }
        
        protected String formatearNumero(Double numero){
            String retorno= "";
            System.out.println("**************************************");
            System.out.println(getDf().format(numero).substring(0 , 1));
            System.out.println("**************************************");
            if(getDf().format(numero).substring(0 , 1) == "0"){
                if(getDf().format(numero) != "0.0"){
                    retorno = getDf().format(numero);
                }else if(getDf2().format(numero) != "0.00"){
                    retorno = getDf2().format(numero);
                }else{
                    retorno = getDf3().format(numero);
                }
            }else{
                System.out.println("No comienza en cero y retorna " + df2.format(numero));
                retorno =  getDf2().format(numero);
            }
                 
            return retorno;
        }
}
