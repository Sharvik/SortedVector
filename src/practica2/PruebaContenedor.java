package practica2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class PruebaContenedor {
    
    private static final String[] CHARCOUNT = {"primeras","segundas","terceras",
                                            "cuartas","quintas","sextas","séptimas",
                                            "octavas","novenas","útimas"};
            
    public static void main(String[] args) throws IOException {
        Time time = new Time();
        RandomAccessFile dataFile = new RandomAccessFile("datos.dat","r");
        BufferedWriter outFile = new BufferedWriter(new FileWriter("salida2.txt"));
        ContenedorDeEnteros sortedVec = new ContenedorDeEnteros(100000);
        
        outFile.write("[+] Iniciando prueba de inserción : ");
        outFile.newLine();
        outFile.newLine();        
        
        insertionTest(dataFile, outFile, sortedVec, time);
        time = new Time();
        
        outFile.newLine();
        outFile.newLine();
        outFile.newLine();
        
        outFile.write("[+] Iniciando prueba de extracción : ");
        outFile.newLine();
        outFile.newLine();
        
        extractionTest(dataFile, outFile, sortedVec, time);
        time = new Time();
        
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
        
        for(int i = 0; i < 10; i++) {
            outFile.write("Resultado de las " + CHARCOUNT[i] + " 10.000 inserciones : ");
            outFile.write("-----------------------------------------------------------");
            time.startTime(i);
            
            for(int j = 0; j < 10000; j++)
                sortedVec.insertar(dataFile.readInt());
            
            time.stopTime(i);
            outFile.write("   [+] Tiempo de inserción : " + time.getTime(i) + " ms");
            outFile.write("   [+] Tiempo medio de inserción : " + time.meanTime() + " ms");            
        }
                
    }

    private static void extractionTest(RandomAccessFile dataFile, 
                                    BufferedWriter outFile, 
                                    ContenedorDeEnteros sortedVec, 
                                    Time time) throws IOException {
        
        for(int i = 0; i < 10; i++) {
            outFile.write("Resultado de las " + CHARCOUNT[i] + " 10.000 extracciones : ");
            outFile.write("-----------------------------------------------------------");
            time.startTime(i);
            
            for(int j = 0; j < 10000; j++)
                sortedVec.insertar(dataFile.readInt());
            
            time.stopTime(i);
            outFile.write("   [+] Tiempo de extracción : " + time.getTime(i) + " ms");
            outFile.write("   [+] Tiempo medio de extracción : " + time.meanTime() + " ms");            
        }
    }
    
    
    private static class Time{
        private final ArrayList<Long> time;

        private Time() {
            time = new ArrayList<>();
        }
        
        private long getTime(int i) {
            return time.get(i);
        }
        
        private void startTime(int i) {
            time.add(i, System.currentTimeMillis());
        }
        
        private void stopTime(int i) {
            time.set(i, Long.sum(System.currentTimeMillis(), -time.get(i)));
        }
        
        private long totalTime() {
            long total = 0;
            for (Long timeLapse : time) {
                total += timeLapse;
            }
            return total;
        }
        
        private double meanTime() {
            return totalTime()/time.size();
        }
    }
    
}
