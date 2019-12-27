package semantico;

import java.util.Comparator;

public class MetodoOffsetComparator implements Comparator<Metodo>
{
   public int compare(Metodo metodo1, Metodo metodo2) 
   {
     // Orden ascendente
     return metodo1.getOffset() - metodo2.getOffset();
   }
}
