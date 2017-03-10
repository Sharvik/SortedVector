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
        
        int head = 0;
        int tail = cardinal() - 1;
        int middle = 0;
        while(head <= tail) {
            middle = (head + tail)/2;
            
            if(vector[middle] == e) {
                for(int i = middle; i < cardinal() - 1; i++) {
                    vector[i] = vector[i+1];
                }
                
                length--;  // La longitud del vector decrementa en 1 tras la extracción 
                return true;
            }
            
            if(vector[middle] < e)
                head = middle + 1;
            
            if(vector[middle] > e)
                tail = middle - 1;
        }
        
        return false;
    }

    public boolean insertar(int e) {
        // Si el vector está lleno no podemos insertar más elementos
        if ((length >= vector.length) && (length < 0)) {
            return false;
        }

        int head = 0;
        int tail = length - 1;
        int middle = 0;
        while(head <= tail) {
            middle = (head + tail)/2;
            
            if(vector[middle] == e)
                return false;
            
            if(vector[middle] < e)
                head = middle + 1;
            
            if(vector[middle] > e)
                tail = middle - 1;
        }
        
        for(int i = length-1; i >= middle; i--) {
            vector[i+1] = vector[i];
        }
        vector[head] = e;
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
    
    // TODO borrar después de comprobar que funciona        
    private int insertBinarySearch(int e) {
        int head = 0;
        int tail = length -1 ;
        int middle = 0;
        
        while(tail >= head) {
            middle = (head + tail)/2;
            
            if(vector[middle] == e)
                return -1;
            
            else if(vector[middle] < e)
                head = middle + 1;
            
            else if(vector[middle] > e)
                tail = middle - 1;
        }
        
        
        return middle;
    }

}
