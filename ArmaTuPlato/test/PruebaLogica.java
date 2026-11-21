import com.armatuplato.builder.*;
import com.armatuplato.decorator.*;
import com.armatuplato.excepciones.*;
import com.armatuplato.modelo.*;
import com.armatuplato.servicio.*;

/** Prueba en consola de la lógica (sin interfaz): java -cp out test/PruebaLogica.java */
public class PruebaLogica {
    public static void main(String[] args) throws Exception {
        Catalogo cat = new Catalogo();

        // Builder
        Producto pizza = new ProductoDirector().pizzaClasica(new PizzaBuilder(cat), Tamano.MEDIANO);
        System.out.println(pizza.getDescripcion() + " -> " + pizza.getPrecio());

        // Decorator (apilados)
        IProducto conExtras = new QuesoExtraDecorator(pizza, 2500);
        conExtras = new BebidaDecorator(conExtras, 4000);
        System.out.println(conExtras.getDescripcion() + " -> " + conExtras.getPrecio());
        System.out.println("Extras: " + conExtras.getExtras());

        // Excepción: builder incompleto
        try {
            new HamburguesaBuilder(cat).tamano(Tamano.GRANDE).base("Pan normal").construir();
        } catch (ProductoIncompletoException e) {
            System.out.println("OK excepción: " + e.getMessage());
        }

        // Pedido + estados + validaciones
        GestorPedidos g = new GestorPedidos();
        Pedido p = new Pedido();
        try { g.registrar(p); } catch (PedidoInvalidoException e) { System.out.println("OK excepción: " + e.getMessage()); }
        p.setCliente("Ana");
        p.agregarItem(conExtras, 2);
        g.registrar(p);
        System.out.println("Subtotal " + p.getSubtotal() + " imp " + p.getImpuesto() + " total " + p.getTotal());
        p.avanzarEstado();
        try { p.cancelar(); } catch (EstadoInvalidoException e) { System.out.println("OK excepción: " + e.getMessage()); }
        try { p.agregarItem(pizza, 1); } catch (PedidoInvalidoException e) { System.out.println("OK excepción: " + e.getMessage()); }
        System.out.println(g.productosMasPedidos() + " " + g.extrasMasSolicitados() + " ventas " + g.ventasDelDia());
        System.out.println(Facturacion.generarComprobante(p));
    }
}
