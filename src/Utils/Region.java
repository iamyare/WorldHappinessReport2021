package Utils;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Region extends Thread {
    String[] split;
    List<List<String>> Lista = new ArrayList<>();
    List<List<String>> region = new ArrayList<>();
    ArrayList<Pais> region_pais = new ArrayList<Pais>();
    HashSet regionD = new HashSet(); //Limpiar Lista
    private static final String CSV_SEPARATOR = ",";

    public double Suma = 0.0;
    public double Promedio = 0.0;
    public int Contador = 0;

    public void CSVreader() {
        String line;
        String ArchivoCSV = "src/CSV/world-happiness-report-2021.csv"; //Premium

        try (
                BufferedReader br = new BufferedReader(
                        new FileReader(ArchivoCSV))) {
            boolean esPrimeraLinea = true;
            while ((line = br.readLine()) != null) {
                if (esPrimeraLinea) { //Excluyendo 'Header'
                    esPrimeraLinea = false;
                    continue;
                }
                // split para coma separador
                split = line.split(",");
                System.out.println("\nPais\t\t\t\t\t\t\t : " + split[0]);
                System.out.println("Region\t\t\t\t\t\t\t : " + split[1]);
                System.out.println("Social support\t\t\t\t\t : " + split[7]);
                System.out.println("Healthy life expectancy\t\t\t : " + split[8]);
                System.out.println("Freedom to make life choices\t : " + split[9]);
                System.out.println("Generosity\t\t\t\t\t\t : " + split[10]);
                System.out.println("Perception of corruption\t\t : " + split[11]);
                Lista.add(Arrays.asList(split));
                region_pais.add(new Pais(split[0], split[1], Double.valueOf(split[7]), Double.valueOf(split[8]), Double.valueOf(split[9]), Double.valueOf(split[10]), Double.valueOf(split[11])));
                region.add(Arrays.asList(split[1]));
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        CreacionCSV();
    }
    public void CreacionCSV(){
        for (int i = 0; i < region_pais.size(); i++) {
            imprimirCSV(region_pais, region_pais.get(i).region);
        }
        System.out.println("CSVs Creados con exito!");
    }
    public void imprimirCSV(ArrayList<Pais> region_pais, String nombreDeArchivo) {
        double P_Social_support = 0.0;
        double P_HlE = 0.0;
        double P_FtMlC = 0.0;
        double P_Genosity = 0.0;
        double P_PoC = 0.0;


        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/Tst/"+nombreDeArchivo+".csv"), "UTF-8"));
            //Titulo
            bw.write("Country name,Regional indicator,Social support,Healthy life expectancy,Freedom to make life choices,Generosity,Perceptions of corruption\n");
            Contador=0; //

            for (Pais pais : region_pais) {
                if (pais.getRegion().equalsIgnoreCase(nombreDeArchivo)) {
                    Contador += 1;
                    P_Social_support += pais.Social_support;
                    P_HlE += pais.Healthy_life_expectancy;
                    P_FtMlC += pais.Freedom_to_make_life_choices;
                    P_Genosity += pais.Generosity;
                    P_PoC += pais.Perception_of_corruption;
                }
            }

            P_Social_support = num(P_Social_support/Contador);
            P_HlE = num(P_HlE/Contador);
            P_FtMlC = num(P_FtMlC/Contador);
            P_Genosity = num(P_Genosity/Contador);
            P_PoC = num(P_PoC/Contador);


            for (Pais pais : region_pais)
            {
                StringBuffer oneLine = new StringBuffer();
                if (pais.getRegion().equalsIgnoreCase(nombreDeArchivo)) {

                    oneLine.append(pais.getPais());//Pais
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(pais.getRegion());//Region
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(num3(pais.getSocial_support()-P_Social_support));//Social Support
                    oneLine.append(CSV_SEPARATOR);

                    oneLine.append(num3(pais.getHealthy_life_expectancy()-P_HlE));
                    oneLine.append(CSV_SEPARATOR);

                    oneLine.append(num3(pais.getFreedom_to_make_life_choices()-P_FtMlC));
                    oneLine.append(CSV_SEPARATOR);

                    oneLine.append(num3(pais.getGenerosity()-P_Genosity));
                    oneLine.append(CSV_SEPARATOR);

                    oneLine.append(num3(pais.getPerception_of_corruption()-P_PoC));


                    bw.write(oneLine.toString());
                    bw.newLine();
                }
            }

            bw.newLine();
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){
            System.out.println("WARNING: Error en escribir el archivo "+e);
        }
    }


    //Log
    //----------CSV----------
    public void Lista() {
        for (int i = 0; i < Lista.size(); i++) {
            System.out.println(Lista.get(i));
        }
    }

    //----------Paises----------
    public void paises() {
        for (Pais p : region_pais) {
            System.out.println(p.pais);
        }
    }

    //----------Lista Region----------
    public String regionUnica() {
        regionD.addAll(region);
        region.clear();
        region.addAll(regionD);
        String archivo = "";
        for (int i = 0; i < region.size(); i++) {
            System.out.println(text(region.get(i)));
        }
        return archivo;
    }

    //----------Pais dentro de una Region----------
    public void PaisdentroRegion() {
        for (Pais p : region_pais) {
            System.out.println(p.pais + " - " + p.region);
        }
    }

    //----------Regiones con sus paises----------
    public void PaisesRegion() {
        for (int i = 0; i < region.size(); i++) {
            get_Region(region.get(i));
        }
    }

    //Optiene el pais segun la region
    public void get_Region(List<String> Region) {
        String region = text(Region);
        System.out.println("\n------" + Region + "------");
        for (Pais p : region_pais) {
            if (p.getRegion().equalsIgnoreCase(String.valueOf(region))) {
                System.out.println(p.pais);
            }
        }
    }

    //Calculos
    public void calculos() {
        for (int i = 0; i < region.size(); i++) {
            System.out.println("\n------" + region.get(i) + "------");
            System.out.println(Social_support(region.get(i)));
            System.out.println(Healthy_life_expectancy(region.get(i)));
            System.out.println(Freedom_to_make_life_choices(region.get(i)));
            System.out.println(Generosity(region.get(i)));
            System.out.println(Perception_of_corruption(region.get(i)));
        }

    }
    public String Social_support(List<String> prom){
        String datosArray = text(prom); //Eliminacion de []

        for (Pais p : region_pais) {
            if (p.getRegion().equalsIgnoreCase(String.valueOf(datosArray))) {
                Contador += 1;
                Suma += p.Social_support;
                Promedio = Suma / Contador;
            }
        }




        String Social_Support = "Promedio Social Support:\t\t\t\t"+String.format("%.3f",Promedio); Suma =0;Promedio = 0;Contador = 0;
        return Social_Support;
    }
    public String Healthy_life_expectancy(List<String> prom){
        String datosArray = text(prom); //Eliminacion de []
        for (Pais p : region_pais) {
            if (p.getRegion().equalsIgnoreCase(String.valueOf(datosArray))) {
                Contador += 1;
                Suma += p.Healthy_life_expectancy;
                Promedio = Suma / Contador;
            }
        }
        String Healthy_life_expectancy = "Promedio Healthy life expectancy:\t\t"+String.format("%.3f",Promedio); Suma =0;Promedio = 0;Contador = 0;
        return Healthy_life_expectancy;
    }
    public String Freedom_to_make_life_choices(List<String> prom){
        String datosArray = text(prom); //Eliminacion de []
        for (Pais p : region_pais) {
            if (p.getRegion().equalsIgnoreCase(String.valueOf(datosArray))) {
                Contador += 1;
                Suma += p.Freedom_to_make_life_choices;
                Promedio = Suma / Contador;
            }
        }
        String Freedom_to_make_life_choices = "Promedio Freedom to make life choices:\t"+String.format("%.3f",Promedio); Suma =0;Promedio = 0;Contador = 0;
        return Freedom_to_make_life_choices;
    }
    public String Generosity(List<String> prom){
        String datosArray = text(prom); //Eliminacion de []
        String regioon ="";
        double max= -1.0;
        for (Pais p : region_pais) {
            if (p.getRegion().equalsIgnoreCase(String.valueOf(datosArray))) {
                Contador += 1;
                Suma += p.Generosity;
                Promedio = Suma / Contador;
                if (p.Generosity>max){
                    max = p.Generosity;
                    regioon = p.region;
                }
            }
        }
        String Generosity = "Promedio Generosity:\t\t\t\t\t"+String.format("%.3f",Promedio); Suma =0;Promedio = 0;Contador = 0;
        get_Generosity(max, regioon);
        return Generosity;
    }
    public String Perception_of_corruption(List<String> prom){
        String datosArray = text(prom); //Eliminacion de []
        double max=0.0;
        String regioon ="";

        for (Pais p : region_pais) {
            if (p.getRegion().equalsIgnoreCase(String.valueOf(datosArray))) {
                Contador += 1;
                Suma += p.Perception_of_corruption;
                Promedio = Suma / Contador;
                if (p.Perception_of_corruption>max){
                    max = p.Perception_of_corruption;
                   regioon = p.region;
                }
            }
        }
        String Perception_of_corruption = "Promedio Perception of corruption:\t\t"+num3(Promedio); Suma =0;Promedio = 0;Contador = 0;
        get_Corrupt(max,regioon);
        return Perception_of_corruption;
    }

    //Comprobacion
    public void get_Corrupt(double max, String reg) {
        for (Pais p : region_pais) {
            if (p.Perception_of_corruption.equals(max)) {
                ///Funcion de solo mostrar por region
                //Funcion de encontrar pais mediante region
                if (p.getRegion().equalsIgnoreCase(String.valueOf(reg))) {
                    System.out.println("Pais mas corrupto:\t\t\t\t\t\t"+p.pais);
                }

            }
        }
    }
    public void get_Generosity(double max, String reg) {
        for (Pais p : region_pais) {
            if (p.Generosity.equals(max)) {
                if (p.getRegion().equalsIgnoreCase(String.valueOf(reg))) {
                    System.out.println("Pais mas generoso:\t\t\t\t\t\t"+p.pais);
                }
            }
        }
    }

    //Extras
    public double num(double num){
        double number;
        BigDecimal formatNumber = new BigDecimal(num);
        formatNumber = formatNumber.setScale(2, RoundingMode.DOWN);
        number = formatNumber.doubleValue();
        return number;
    }
    public double num3(double num){
        double number;
        BigDecimal formatNumber = new BigDecimal(num);
        formatNumber = formatNumber.setScale(3, RoundingMode.UP);
        number = formatNumber.doubleValue();
        return number;
    }
    public String text(List<String> prom){
        String datosArray = "";
        for (String elemento : prom) {
            datosArray += elemento;
        }//Eliminando los []'
        return datosArray;
    }

}