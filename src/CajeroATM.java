import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/* ====== Contrato del manejador ====== */
interface Dispensador {
    void setNext(Dispensador next);
    void handle(DispenseRequest request);
}

/* ====== Request que viaja por la cadena ====== */
class DispenseRequest {
    int amount;  // monto restante por dispensar
    final Map<Integer, Integer> notas = new LinkedHashMap<>(); // denom -> cantidad

    DispenseRequest(int amount) { this.amount = amount; }
}

/* ====== Manejador base (boilerplate) ====== */
abstract class BaseDispensador implements Dispensador {
    protected Dispensador next;
    protected final int denominacion; // p. ej. 100000, 50000, etc.

    protected BaseDispensador(int denominacion) {
        this.denominacion = denominacion;
    }

    @Override
    public void setNext(Dispensador next) {
        this.next = next;
    }

    @Override
    public void handle(DispenseRequest request) {
        int cantidad = request.amount / denominacion;
        if (cantidad > 0) {
            request.notas.put(denominacion, cantidad);
            request.amount = request.amount % denominacion;
        }
        if (next != null) next.handle(request);
    }
}

/* ====== Manejadores concretos por denominación ====== */
class Dispensador100k extends BaseDispensador { public Dispensador100k() { super(100_000); } }
class Dispensador50k  extends BaseDispensador { public Dispensador50k()  { super(50_000); } }
class Dispensador20k  extends BaseDispensador { public Dispensador20k()  { super(20_000); } }
class Dispensador10k  extends BaseDispensador { public Dispensador10k()  { super(10_000); } }
class Dispensador5k   extends BaseDispensador { public Dispensador5k()   { super(5_000); } }

/* ====== Cliente / Builder de la cadena ====== */
public class CajeroATM {
    private static Dispensador buildCadena() {
        Dispensador d100 = new Dispensador100k();
        Dispensador d50  = new Dispensador50k();
        Dispensador d20  = new Dispensador20k();
        Dispensador d10  = new Dispensador10k();
        Dispensador d5   = new Dispensador5k();

        d100.setNext(d50);
        d50.setNext(d20);
        d20.setNext(d10);
        d10.setNext(d5);

        return d100; // cabeza de la cadena
    }

    public static Map<Integer, Integer> dispensar(int monto) {
        if (monto <= 0 || (monto % 5_000 != 0)) {
            throw new IllegalArgumentException(
                "Monto inválido: debe ser positivo y múltiplo de 5.000");
        }
        DispenseRequest req = new DispenseRequest(monto);
        Dispensador cadena = buildCadena();
        cadena.handle(req);
        return req.notas;
    }

    /* ====== Demo ====.== */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Insertar Monto de dinero a Retirar: ");
        int monto = sc.nextInt();
        // int monto = 420_000; // prueba
        Map<Integer, Integer> resultado = dispensar(monto);

        System.out.println("Monto ingresado: " + monto);
        resultado.forEach((denom, cant) ->
            System.out.println("Billetes de " + denom + ": " + cant)
        );
        int verificacion = resultado.entrySet().stream()
            .mapToInt(e -> e.getKey() * e.getValue()).sum();
        System.out.println("Total dispensado: " + verificacion);
    }
}
