package practica2;


public class ContenedorDeEnteros {
    private int[] vector;
    private int length;

    public ContenedorDeEnteros(int length) {
        vector = new int[length];
        this.length = 0;
    }
    
    public int cardinal() {
        return length;
    }
    
    public int[] elementos() {
        int[] outVec = new int[length];
        for (int i = 0; i < length; i++) {
            outVec[i] = vector[i];
        }
        return outVec;
    }
    
    public void vaciar() {
        vector = null;
    }
    
    public boolean extraer(int e) {
        if(!buscar(e) || length == 0)
            return false;
        
        int i = 0;
        while((vector[i] != e) && (i < cardinal())) {
            i++;
            if(vector[i] == e) {
                while(i < cardinal() - 1) {
                    vector[i] = vector[i+1];
                }
                i++;
            }
        }
        length--;
        return true;
    }
    
    public boolean insertar(int e) {
        if(buscar(e) || length == vector.length)
            return false;
        
        int i = 0;
        while((vector[i] < e) && (i < vector.length)) {
            
            if(vector[i] > e) {
                while(i < vector.length - 1) {
                    int aux = vector[i];
                    
                }
            }
        }
        length++;
        return true;
    }

    public boolean buscar(int e) {
        return false;
    }
    
}
