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
    private static final String[] CHARCOUNT = {"PRIMERAS","SEGUNDAS","TERCERAS",
                                            "CUARTAS","QUINTAS","SEXTAS","SÉPTIMAS",
                                            "OCTAVAS","NOVENAS","ÚLTIMAS"};
            
    public static void main(String[] args) throws IOException {
        // Inicializamos los marcadores de tiempo
        Time time = new Time();
        
        // Abrimos los ficheros de datos a insertar y de resultados
        RandomAccessFile dataFile = new RandomAccessFile("datos.dat","r");
        BufferedWriter outFile = new BufferedWriter(new FileWriter("salida2.txt"));
        
        // Creamos un vector ordenado con capacidad para almacenar 100k elementos
        ContenedorDeEnteros sortedVec = new ContenedorDeEnteros(100000);
        
        // Iniciamos prueba de inserción
        outFile.write("#####################################");
        outFile.newLine();
        outFile.write("### Iniciando prueba de inserción ###");
        outFile.newLine();
        outFile.write("#####################################");
        outFile.newLine();
        outFile.newLine();        
        
        insertionTest(dataFile, outFile, sortedVec, time);
        time = new Time();  // Reseteamos el Tiempo para prepararlo para la 
                            // la siguiente prueba
                        
                            
        // Colocamos el puntero del fichero de datos al principio
        dataFile.seek(0);

        // Dejamos algunas líneas para separar un test de otro en el documento
        // de salida
        outFile.newLine();
        outFile.newLine();
        outFile.newLine();
        
        
        // Iniciamos la prueba de extracción
        outFile.write("######################################");
        outFile.newLine();
        outFile.write("### Iniciando prueba de extracción ###");
        outFile.newLine();
        outFile.write("######################################");
        outFile.newLine();
        outFile.newLine();        
        outFile.newLine();
        outFile.newLine();
        
        extractionTest(dataFile, outFile, sortedVec, time);
        time = new Time();  // Reseteamos el Tiempo para prepararlo para la 
                            // la siguiente prueba
        
        // Colocamos el puntero del fichero de datos al principio
        dataFile.seek(0);
        
        // Dejamos algunas líneas para separar un test de otro en el documento
        // de salida
        outFile.newLine();
        outFile.newLine();
        outFile.newLine();
        
        
        // Iniciamos la prueba de búsqueda exitosa
        outFile.write("############################################");
        outFile.newLine();
        outFile.write("### Iniciando prueba de búsqueda exitosa ###");
        outFile.newLine();
        outFile.write("############################################");
        outFile.newLine();
        outFile.newLine();        
        outFile.newLine();
        outFile.newLine();
        
        canFindTest(dataFile, outFile, sortedVec, time);
        time = new Time();  // Reseteamos el Tiempo para prepararlo para la 
                            // la siguiente prueba
        sortedVec = new ContenedorDeEnteros(100000);  // Reiniciamos el contenedor
        
        // Colocamos el puntero del fichero de datos al principio
        dataFile.seek(0);
        
        
        // Dejamos algunas líneas para separar un test de otro en el documento
        // de salida
        outFile.newLine();
        outFile.newLine();
        outFile.newLine();
        
        
        // Iniciamos la prueba de búsqueda no exitosa
        outFile.write("###############################################");
        outFile.newLine();
        outFile.write("### Iniciando prueba de búsqueda no exitosa ###");
        outFile.newLine();
        outFile.write("###############################################");
        outFile.newLine();
        outFile.newLine();
        
        cannotFindTest(dataFile, outFile, sortedVec, time);
        
        // Mensaje de despedida del programa
        outFile.newLine();
        outFile.newLine();
        outFile.write("¡ FIN DEL PROGRAMA ! ! APRUÉBEME, POR FAVOR !");
        
        dataFile.close();
        outFile.close();
    }
    
    
    private static void insertionTest(RandomAccessFile dataFile,
                                    BufferedWriter outFile,
                                    ContenedorDeEnteros sortedVec, 
                                    Time time) throws IOException {
        
        // Leemos de 10k en 10k hasta llegar a 100k elementos
        for(int i = 0; i < 10; i++) {
            outFile.write("Ejecutando prueba de las " + CHARCOUNT[i] + " 10.000 inserciones : ");
            outFile.newLine();
            outFile.write("-------------------------------------------------------------------");
            outFile.newLine();
            outFile.newLine();
            int counter = 0;    // Contador para saber si hemos llegado a 1000 elementos
            time.startTime(counter);  // Comenzamos a contar el tiempo por cada 1000 elementos
            
            // Insertamos los enteros de datos.dat en el vector ordenado de 10k en 10k
            for(int j = 0; j <= 10000; j++) {
                // Inserta los elementos del contenedor de 10k en 10k y comprueba que
                // se añaden correctamente
                if((j < 10000) && (!sortedVec.insertar(dataFile.readInt()))) {
                    outFile.write("   [!] ¡Error! No se pudo insertar un elemento en el contenedor");
                    outFile.newLine();
                    outFile.newLine();
                }
                
                // Colocamos una marca de tiempo por cada 1000 inserciones
                if((j != 0) && (j % 1000 == 0)) {
                    // Detenemos el tiempo y extraemos su estadística por cada 1000 elementos
                    time.stopTime(counter);
                    outFile.write("   [+] Tiempo de inserción de las 1.000 " + CHARCOUNT[counter] 
                                    + " operaciones : " + time.getTime(counter) + " ms");
                    outFile.newLine();
                    counter++;
                    if(counter < 10)
                        time.startTime(counter);
                }
            }
            
            outFile.newLine();
            outFile.newLine();

            // Resultado de la estadística por cada 10k operaciones
            time.meanTime();
            outFile.write("   [+] Tiempo medio de inserción de las 10.000 " + CHARCOUNT[i] 
                           + " operaciones : " + time.getMeanTime(i)+ " ms");
            
            outFile.newLine();
            outFile.newLine();
            
        }
        
        // Promedio total de la prueba
        outFile.newLine();
        outFile.write("   [+] PROMEDIO TOTAL DE INSERCIÓN : " + time.meanResult());
    }

    private static void extractionTest(RandomAccessFile dataFile, 
                                    BufferedWriter outFile, 
                                    ContenedorDeEnteros sortedVec, 
                                    Time time) throws IOException {
        
        // Leemos de 10k en 10k hasta llegar a 100k elementos
        for(int i = 0; i < 10; i++) {
            outFile.write("Ejecutando prueba de las " + CHARCOUNT[i] + " 10.000 extracciones : ");
            outFile.newLine();
            outFile.write("--------------------------------------------------------------------");
            outFile.newLine();
            outFile.newLine();
            int counter = 0;    // Contador para saber si hemos llegado a 1000 elementos
            time.startTime(counter);  // Comenzamos a contar el tiempo por cada 1000 elementos
            
            // Extraemos los enteros de datos.dat en el vector ordenado de 10k en 10k
            for(int j = 0; j <= 10000; j++) {
                // Extrae los elementos del contenedor de 10k en 10k y comprueba que
                // se eliminan correctamente
                if((j < 10000) && (!sortedVec.extraer(dataFile.readInt()))) {
                    outFile.write("   [!] ¡Error! No se pudo extraer un elemento del contenedor");
                    outFile.newLine();
                    outFile.newLine();
                }
                
                // Colocamos una marca de tiempo por cada 1000 extracciones
                if((j != 0) && (j % 1000 == 0)) {
                    // Detenemos el tiempo y extraemos su estadística por cada 1000 elementos
                    time.stopTime(counter);
                    outFile.write("   [+] Tiempo de extracción de las 1.000 " + CHARCOUNT[counter] 
                                    + " operaciones : " + time.getTime(counter) + " ms");
                    outFile.newLine();
                    counter++;
                    if(counter < 10) {
                        time.startTime(counter);
                    }
                }
            }
            
            outFile.newLine();
            outFile.newLine();
            
            // Extraemos la estadística por cada 10k operaciones
            time.meanTime();
            outFile.write("   [+] Tiempo medio de extracción de las 10.000 " + CHARCOUNT[i] 
                           + " operaciones : " + time.getMeanTime(i)+ " ms");
            
            outFile.newLine();
            outFile.newLine();
        }
        
        // Promedio total de la prueba
        outFile.newLine();
        outFile.write("   [+] PROMEDIO TOTAL DE EXTRACCIÓN : " + time.meanResult());
        
        
    }

    private static void canFindTest(RandomAccessFile dataFile, 
                                    BufferedWriter outFile, 
                                    ContenedorDeEnteros sortedVec, 
                                    Time time) throws IOException {
        
        // Sirve para ajustar las marcas de tiempos a insertar en la posición
        // que le corresponde en cada iteración
        int counter = 0;  
        
        // Leemos de 10k en 10k hasta llegar a 100k elementos
        for(int i = 0; i < 10; i++) {
            outFile.write("Ejecutando prueba de las " + CHARCOUNT[i] + " 10.000 búsquedas : ");
            outFile.newLine();
            outFile.write("--------------------------------------------------------------------");
            outFile.newLine();
            outFile.newLine();
            

            
            // Insertamos los enteros de datos.dat en el vector ordenado de 10k en 10k
            for(int j = 0; j < 10000; j++) {
                sortedVec.insertar(dataFile.readInt());
            }
            
            dataFile.seek(0);
            time.startTime(i - counter);  // Comenzamos a contar el tiempo por cada 1000 elementos
            
            
            // Buscamos los elementos insertados en la estructura de datos resultante
            for(int j = 0; j <= (i+1)*10000; j++) {
                // Busca los elementos y comprueba que los encuentra exitosamente
                if((j < (i+1)*10000) && (!sortedVec.buscar(dataFile.readInt()))) {
                        outFile.write("   [!] ¡Error! No encuentra el elemento en búsqueda exitosa");
                        outFile.newLine();
                        outFile.newLine();
                }
                
                // Colocamos una marca de tiempo por cada 1000 búsquedas
                if((j > 0) && (j % 1000 == 0)) {
                    // Detenemos el tiempo y extraemos su estadística por cada 1000 elementos
                    time.stopTime(i - counter);
                    outFile.write("   [+] Tiempo de búsqueda exitosa de las 1.000 " + CHARCOUNT[i] 
                                    + " operaciones : " + time.getTime(i - counter) + " ms");
                    outFile.newLine();
                    
                    // Calculamos la estadística al final de cada ciclo
                    // de búsqueda
                    if(j == (i+1)*10000) {
                        time.meanTime();
                        counter++;
                    }
                        
                    
                    // Reiniciamos el tiempo por cada 1.000 búsquedas
                    if(j < (i+1)*10000) 
                        time.startTime(i - counter);
                }
            }
            
            outFile.newLine();
            outFile.newLine();
            
            
            outFile.write("   [+] Tiempo medio de búsquedas exitosas de las 10.000 " + CHARCOUNT[i] 
                           + " operaciones : " + time.getMeanTime(i)+ " ms");
            
            outFile.newLine();
            outFile.newLine();
            
        }
        
        // Promedio total de la prueba
        outFile.newLine();
        outFile.write("PROMEDIO TOTAL DE BÚSQUEDA EXITOSA : " + time.meanResult());
    }

    private static void cannotFindTest(RandomAccessFile dataFile, 
                                       BufferedWriter outFile, 
                                       ContenedorDeEnteros sortedVec, 
                                       Time time) throws IOException {
        
        // Sirve para ajustar las marcas de tiempos a insertar en la posición
        // que le corresponde en cada iteración
        int counter = 0;
        
        // Leemos de 10k en 10k hasta llegar a 100k elementos
        for(int i = 0; i < 10; i++) {
            outFile.write("Ejecutando prueba de las " + CHARCOUNT[i] + " 10.000 extracciones : ");
            outFile.newLine();
            outFile.write("--------------------------------------------------------------------");
            outFile.newLine();
            outFile.newLine();
        
            // Insertamos los enteros de datos.dat en el vector ordenado de 10k en 10k
            for(int j = 0; j < 10000; j++) {
                sortedVec.insertar(dataFile.readInt());
            }
            
            // Cargamos el fichero de datos que no están en el vector e iniciamos la prueba
            RandomAccessFile nodataFile = new RandomAccessFile("datos_no.dat","r");
            
            time.startTime(i - counter);  // Comenzamos a contar el tiempo por cada 1000 elementos
            
            // Buscamos los elementos insertados en la estructura de datos resultante
            for(int j = 0; j <= 20000; j++) {
                // Busca los elementos y comprueba que no se encuentran en el contenedor
                if((j < 20000) && (sortedVec.buscar(nodataFile.readInt()))) {
                        outFile.write("   [!] ¡Error! Se ha encontrado un número " + 
                                    "que no debería estar en el contenedor.");
                        outFile.newLine();
                        outFile.newLine();
                }
                
                // Colocamos una marca de tiempo por cada 1000 búsquedas
                if((j > 0) && (j % 1000 == 0)) {
                    // Detenemos el tiempo y extraemos su estadística por cada 1000 elementos
                    time.stopTime(i - counter);
                    outFile.write("   [+] Tiempo de búsqueda no exitosa de las 1.000 " + CHARCOUNT[i] 
                                    + " operaciones : " + time.getTime(i - counter) + " ms");
                    outFile.newLine();
                    
                    // Calculamos la estadística al final de cada ciclo
                    // de búsqueda
                    if(j == 20000)
                        time.meanTime();

                        
                    // Reiniciamos el tiempo por cada 1.000 búsquedas
                    if(j < 20000) {
                        time.startTime(i - counter);
                        nodataFile.seek(0);  // Colocamos el puntero al principio del fichero
                                             // para poder continuar con la prueba en la
                                             // siguiente iteración
                    }
                    
                    
                }
            }
            
            outFile.newLine();
            outFile.newLine();
            
            outFile.write("   [+] Tiempo medio de búsquedas no exitosas de las 10.000 " + CHARCOUNT[i] 
                           + " operaciones : " + time.getMeanTime(i - counter)+ " ms");
            
            outFile.newLine();
            outFile.newLine();
            
            counter++;
            
            // Cerramos el fichero no_data.dat
            nodataFile.close();
        }
        
        // Promedio total de la prueba
        outFile.newLine();
        outFile.write("PROMEDIO TOTAL DE BÚSQUEDA NO EXITOSA : " + time.meanResult());
        
        
    }
    
    
    
    
    // Esta clase maneja todos los marcadores de tiempo que utilizaremos para medir
    // El rendimiento del programa
    private static class Time{
        private ArrayList<Long> time;
        private ArrayList<Double> meanTime;

        // Constructor por defecto
        private Time() {
            time = new ArrayList<>();
            meanTime = new ArrayList<>();
        }
        
        // Obtiene el valor de tiempo actualmente almacenado en el objeto de Tiempo
        private long getTime(int i) {
            return time.get(i);
        }
        
        // Obtiene la media de tiempo actualmente almacenado en una posición determinada
        private double getMeanTime(int i) {
            return meanTime.get(i);
        }
        
        // Inicializa una marca de tiempo en una posición determinada del banco
        // de tiempos
        private void startTime(int i) {
            time.add(i, System.currentTimeMillis());
        }
        
        // Calcula el incremento de tiempo desde que se inició una marca de tiempo
        // determinada
        private void stopTime(int i) {
            time.set(i, System.currentTimeMillis() -time.get(i));
        }
        
        // Devuelve el total de todas las marcas de tiempo actuales
        private long totalTime() {
            long total = 0;
            for (Long timeLapse : time) {
                total += timeLapse;
            }
            return total;
        }
        
        // Devuelve el total de todas las medias almacenadas
        private double totalMeanTime() {
            double total = 0;
            for (Double meanLapse : meanTime) {
                total += meanLapse;
            }
            return total;
        }
        
        // Devuelve la media aritmética de todas las marcas de tiempo
        private void meanTime() {
            meanTime.add((double)totalTime()/time.size());
            time = new ArrayList<>();  // Resetea el vector de marcas de tiempos
                                       // Cada vez que se haya la media
        }
        
        // Devuelve la media de todas las medias
        private double meanResult() {
            return totalMeanTime()/meanTime.size();
        }
    }
    
}
