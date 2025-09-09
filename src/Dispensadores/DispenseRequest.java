package Dispensadores;

import java.util.LinkedHashMap;
import java.util.Map;

class DispenseRequest {
   int amount;
   final Map<Integer, Integer> notas = new LinkedHashMap();

   DispenseRequest(int amount) {
      this.amount = amount;
   }
}
