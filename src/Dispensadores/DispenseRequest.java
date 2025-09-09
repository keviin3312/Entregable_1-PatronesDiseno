package Dispensadores;

// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.util.LinkedHashMap;
import java.util.Map;

class DispenseRequest {
   int amount;
   final Map<Integer, Integer> notas = new LinkedHashMap();

   DispenseRequest(int amount) {
      this.amount = amount;
   }
}
