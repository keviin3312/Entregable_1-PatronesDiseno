package Dispensadores;

// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
abstract class BaseDispensador implements Dispensador {
   protected Dispensador next;
   protected final int denominacion;

   protected BaseDispensador(int denominacion) {
      this.denominacion = denominacion;
   }

   public void setNext(Dispensador next) {
      this.next = next;
   }

   public void handle(DispenseRequest request) {
      int cantidad = request.amount / this.denominacion;
      if (cantidad > 0) {
         request.notas.put(this.denominacion, cantidad);
         request.amount %= this.denominacion;
      }

      if (this.next != null) {
         this.next.handle(request);
      }

   }
}
