package Dispensadores;


interface Dispensador {
   void setNext(Dispensador var1);

   void handle(DispenseRequest var1);
}
