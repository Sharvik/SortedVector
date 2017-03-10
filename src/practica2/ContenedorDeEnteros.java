package practica2;

public class ContenedorDeEnteros {

    private int[] vector;
    private int length;

    // Constructor que inicializa el vector a un tamaño máximo y contiene 0 elementos
    public ContenedorDeEnteros(int length) {
        vector = new int[length];
        this.length = 0;
    }
    
    // Devuelve la longitud del vector
    public int cardinal() {
        return length;
    }

    // Devuelve los elementos de un vector
    public int[] elementos() {
        
        // Copia los elementos del vector en un vector de salida y devuelve el mismo
        int[] outVec = new int[length];
        System.arraycopy(vector, 0, outVec, 0, length);
        return outVec;
    }

    public void vaciar() {
        vector = null;
        length = 0;
    }

    public boolean extraer(int e) {
        // No podemos extraer si el vector se encuentra vacío o si el elemento
        // no está en el vector
        if (cardinal() == 0) {
            return false;
        }
        
        // Recorremos por todos los elementos del vector hasta encontrar un
        // valor mayor o igual al elemento pasado por parámetro
        int i = 0;
        while ((vector[i] < e) && (i < cardinal())) {
            i++;
        }
        
        // Si el elemento del vector es mayor que el elemento o  
        // se ha recorrido todo el vector sin encontrar el elemento entonces 
        // el elemento pasado por parámetro no está en el vector y falla la extracción
        if(vector[i] > e || i >= cardinal())
            return false;
        
        // Si el elemento del vector es e igual al elemento pasado por parámetro
        // entonces lo extraemos
        for(int j = i; j < cardinal() -1; j++) {
            // Si encontramos un 0 hemos llegado al final del vector
            if(vector[j] == 0)
                j = cardinal();
            
            // Actualizamos a una posición menos a cada elemento del vector
            vector[j] = vector[j+1];
        }
        length--;  // La longitud del vector decrementa en 1 tras la extracción 
        return true;
    }

    public boolean insertar(int e) {
        // Si el vector está lleno no podemos insertar más elementos
        if (length == vector.length) {
            return false;
        }

        // Recorremos el vector haste encontrar un elemento mayor que el
        // elemento pasado por parámetro
        int i = 0;

        // Actualizamos la posición siempre que los elementos del vector sean
        // menores que el elemento pasado por parámetro
        while ((i < vector.length) && (vector[i] <= e) && (vector[i] != 0)) {
            // No podemos permitir que existan elementos repetidos en el vector
            if (vector[i] == e) {
                return false;
            }
            i++;
        }
        
        // Guardamos el elemento en la posición ordinal que le corresponde
        int aux = vector[i];
        vector[i] = e;
        
        // Actualizamos las posiciones del resto de elementos del vector
        // los cuáles son mayores que el elemento pasado por parámetro
        for (int j = i + 1; j < vector.length - 1; j++) {
            vector[i] = aux;
            aux = vector[i+1];
        }
        length++;  // La longitud del vector incrementa en 1 tras la inserción
        return true;
    }

    public boolean buscar(int e) {
        // Delimitamos el límite superior e inferior del vector
        int head = 0;
        int tail = length - 1;

        // Realizamos búsquedas dicotómicas hasta que el tamaño del vector sea 1
        while (tail >= head) {
            // Dividimos el vector resultante por la mitad
            int middle = (head + tail) / 2;

            // Si encuencuentra el elemento devolvemos true
            if (vector[middle] == e) {
                return true;
            }

            // Si no lo encuentra y el elemento es mayor que el valor de en medio
            // buscaremos por la izquierda
            if (vector[middle] < e) {
                head = middle + 1;
            }

            // Si no lo encuentra y el elemento es menor que el valor de en medio
            // buscaremos por la derecha
            if (vector[middle] > e) {
                tail = middle - 1;
            }
        }

        // No encuentra el elemento en todo el vector
        return false;
    }

}
