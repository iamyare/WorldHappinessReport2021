import Utils.Region;

public class App {
    public static void main(String[] args) {
        Region Explorador = new Region();

        //Contenido
        //Lee el CSV
            System.out.println("\n----------INFORMACION DE INTERES----------");
        Explorador.CSVreader();

        //Muestra el CSV
        System.out.println("\n----------CSV----------");
        Explorador.Lista();

        //Muestra los paises
        System.out.println("\n----------Paises----------");
        Explorador.paises();

        //Muestra las Regiones
        System.out.println("\n----------Regiones----------");
        Explorador.regionUnica();

        //Muestra el pais y su Region
        System.out.println("\n----------Pais dentro de una Region----------");
        Explorador.PaisdentroRegion();
        //Muestra La region con su paises
        System.out.println("\n----------Regiones con sus paises----------");
        Explorador.PaisesRegion();

        //Muestra las estadisticas de cada Region
        System.out.println("\n----------Calculos----------");
        Explorador.calculos();


        //Escritura
        //Llamando Hilo
        System.out.println("\n----------Creando CSV----------");
        Explorador.start();

    }
}
