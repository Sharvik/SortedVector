package practica2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class PruebaContenedor {
    // Vector de String constante para ser usada por los programas de test
    // para identificar en qué iteración de 10k elementos estamos extrayendo
    // los datos estadísticos de marca de tiempo
    private static final String[] CHARCOUNT = {"primeras","segundas","terceras",
                                            "cuartas","quintas","sextas","séptimas",
                                            "octavas","novenas","útimas"};
            
    public static void main(String[] args) throws IOException {
        // Inicializamos los marcadores de tiempo
        Time time = new Time();
        
        // Abrimos los ficheros de datos a insertar y de resultados
        RandomAccessFile dataFile = new RandomAccessFile("datos.dat","r");
        BufferedWriter outFile = new BufferedWriter(new FileWriter("salida2.txt"));
        
        // Creamos un vector ordenado con capacidad para almacenar 100k elementos
        ContenedorDeEnteros sortedVec = new ContenedorDeEnteros(100000);
        
        // Iniciamos prueba de inserción
        outFile.write("[+] Iniciando prueba de inserción : ");
        outFile.newLine();
        outFile.newLine();        
        
        insertionTest(dataFile, outFile, sortedVec, time);
        time = new Time();  // Reseteamos el Tiempo para prepararlo para la 
                            // la siguiente prueba

        // Dejamos algunas líneas para separar un test de otro en el documento
        // de salida
        outFile.newLine();
        outFile.newLine();
        outFile.newLine();
        
        
        // Iniciamos la prueba de extracción
        outFile.write("[+] Iniciando prueba de extracción : ");
        outFile.newLine();
        outFile.newLine();
        
        extractionTest(dataFile, outFile, sortedVec, time);
        time = new Time();  // Reseteamos el Tiempo para prepararlo para la 
                            // la siguiente prueba

        // Esto sólo es un ejemplo de cómo usar la clase Tiempo
        for (int i = 0; i < 10; i++) {
            time.startTime(i);
            System.out.println(i + " tiempo inicial : " + time.getTime(i));
            for (long j = 0; j < (long)100000000; j++) {
                
            }
            time.stopTime(i);
            System.out.println(i + " tiempo final : " + time.getTime(i) + "\n");
        }
        System.out.println(time.meanTime());
        outFile.close();
    }
    
    
    private static void insertionTest(RandomAccessFile dataFile,
                                    BufferedWriter outFile,
                                    ContenedorDeEnteros sortedVec, 
                                    Time time) throws IOException {
        
        // Leemos de 10k en 10k hasta llegar a 100k elementos
        for(int i = 0; i < 10; i++) {
            outFile.write("Resultado de las " + CHARCOUNT[i] + " 10.000 inserciones : ");
            outFile.write("-----------------------------------------------------------");
            time.startTime(i);  // Comenzamos a contar el tiempo por cada 10k elementos
            
            // Insertamos los enteros de datos.dat en el vector ordenado de 10k en 10k
            for(int j = 0; j < 10000; j++)
                sortedVec.insertar(dataFile.readInt());
            
            // Detenemos el tiempo y extraemos su estadística por cada 10k elementos
            time.stopTime(i);
            outFile.write("   [+] Tiempo de inserción : " + time.getTime(i) + " ms");
            outFile.write("   [+] Tiempo medio de inserción : " + time.meanTime() + " ms");            
        }
                
    }

    private static void extractionTest(RandomAccessFile dataFile, 
                                    BufferedWriter outFile, 
                                    ContenedorDeEnteros sortedVec, 
                                    Time time) throws IOException {
        
        // Leemos de 10k en 10k hasta llegar a 100k elementos
        for(int i = 0; i < 10; i++) {
            outFile.write("Resultado de las " + CHARCOUNT[i] + " 10.000 extracciones : ");
            outFile.write("-----------------------------------------------------------");
            time.startTime(i);
            
            // Extraemos los enteros de datos.dat en el vector ordenado de 10k en 10k
            for(int j = 0; j < 10000; j++)
                sortedVec.insertar(dataFile.readInt());
            
            // Detenemos el tiempo y extraemos su estadística por cada 10k elementos
            time.stopTime(i);
            outFile.write("   [+] Tiempo de extracción : " + time.getTime(i) + " ms");
            outFile.write("   [+] Tiempo medio de extracción : " + time.meanTime() + " ms");            
        }
    }
    
    
    // Esta clase maneja todos los marcadores de tiempo que utilizaremos para medir
    // El rendimiento del programa
    private static class Time{
        private final ArrayList<Long> time;

        // Constructor por defecto
        private Time() {
            time = new ArrayList<>();
        }
        
        // Obtiene el valor de tiempo actualmente almacenado en el objeto de Tiempo
        private long getTime(int i) {
            return time.get(i);
        }
        
        // Inicializa una marca de tiempo en una posición determinada del banco
        // de tiempos
        private void startTime(int i) {
            time.add(i, System.currentTimeMillis());
        }
        
        // Calcula el incremento de tiempo desde que se inició una marca de tiempo
        // determinada
        private void stopTime(int i) {
            time.set(i, Long.sum(System.currentTimeMillis(), -time.get(i)));
        }
        
        // Devuelve el total de todas las marcas de tiempo actuales
        private long totalTime() {
            long total = 0;
            for (Long timeLapse : time) {
                total += timeLapse;
            }
            return total;
        }
        
        // Devuelve la media aritmética de todas las marcas de tiempo
        private double meanTime() {
            return totalTime()/time.size();
        }
    }
    
}
