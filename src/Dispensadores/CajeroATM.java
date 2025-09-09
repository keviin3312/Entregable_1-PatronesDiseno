package Dispensadores;

// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.io.PrintStream;
import java.util.Map;

public class CajeroATM {
   public CajeroATM() {
   }

   private static Dispensador buildCadena() {
      Dispensador d100 = new Dispensador100k();
      Dispensador d50 = new Dispensador50k();
      Dispensador d20 = new Dispensador20k();
      Dispensador d10 = new Dispensador10k();
      Dispensador d5 = new Dispensador5k();
      d100.setNext(d50);
      d50.setNext(d20);
      d20.setNext(d10);
      d10.setNext(d5);
      return d100;
   }

   public static Map<Integer, Integer> dispensar(int monto) {
      if (monto > 0 && monto % 5000 == 0) {
         DispenseRequest req = new DispenseRequest(monto);
         Dispensador cadena = buildCadena();
         cadena.handle(req);
         return req.notas;
      } else {
         throw new IllegalArgumentException("Monto inválido: debe ser positivo y múltiplo de 5.000");
      }
   }

   public static void main(String[] args) {
      int monto = 420000;
      Map<Integer, Integer> resultado = dispensar(monto);
      System.out.println("Monto: " + monto);
      resultado.forEach((denom, cant) -> {
         PrintStream var10000 = System.out;
         String var10001 = String.valueOf(denom);
         var10000.println("Billetes de " + var10001 + ": " + String.valueOf(cant));
      });
      int verificacion = resultado.entrySet().stream().mapToInt((e) -> {
         return (Integer)e.getKey() * (Integer)e.getValue();
      }).sum();
      System.out.println("Total dispensado: " + verificacion);
   }
}
