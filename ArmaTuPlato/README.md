# ArmaTuPlato

Sistema de pedidos de pizzas y hamburguesas personalizables, hecho en **Java (Swing)** para el curso
**Programación III** — Universidad de Córdoba, Ingeniería de Sistemas, semestre 2026-2.

**Integrantes:** Juan David Saez Ariza y Mateo Alejandro Vélez Doria
**Profesor:** Pierre Peña

## Patrones de diseño

| Patrón | Tipo | Dónde está | Para qué se usa |
|---|---|---|---|
| **Builder** | Creacional | `builder/` | Arma la pizza o la hamburguesa paso a paso (tamaño, base, salsa, queso, proteína, vegetales) y valida que no falte nada. |
| **Decorator** | Estructural | `decorator/` | Agrega extras (queso extra, tocineta, salsa especial, papas, bebida) a un producto ya armado, sumando descripción y precio sin modificarlo. |

Roles de cada patrón (también marcados en el diagrama UML `docs/diagrama_clases.png`):

- **Builder:** `ProductoBuilder` (Builder) · `BaseProductoBuilder` / `PizzaBuilder` / `HamburguesaBuilder` (ConcreteBuilder) · `ProductoDirector` (Director) · `Producto` (Product).
- **Decorator:** `IProducto` (Component) · `Producto` (ConcreteComponent) · `ProductoDecorator` (Decorator) · `QuesoExtraDecorator`, `TocinetaDecorator`, `SalsaEspecialDecorator`, `PapasDecorator`, `BebidaDecorator` (ConcreteDecorator).

## Requisitos del proyecto

| Requisito | Dónde se ve |
|---|---|
| Java | Todo el proyecto (JDK 11 o superior). |
| Encapsulamiento | Atributos `private` con getters/validación en `Producto`, `Ingrediente`, `Pedido`, `ItemPedido`. |
| Herencia | `Producto` → `Pizza`, `Hamburguesa`; `ProductoDecorator` → 5 decoradores; `ArmaTuPlatoException` → 4 excepciones; `BaseProductoBuilder` → 2 builders. |
| Polimorfismo | `IProducto` (`getDescripcion()` / `getPrecio()`) se comporta distinto en `Pizza`, `Hamburguesa` y cada decorador; `ProductoBuilder` se usa igual con `PizzaBuilder` o `HamburguesaBuilder`. |
| Manejo de excepciones | Paquete `excepciones/`: `ProductoIncompletoException`, `PedidoInvalidoException`, `EstadoInvalidoException`, `CatalogoException`. La GUI las captura y muestra mensajes al usuario. |
| Interfaz gráfica | Swing, paquete `ui/` (4 pestañas: Nuevo pedido, Pedidos del día, Catálogo y precios, Reportes). |

## Funcionalidades

Crear pedidos con cliente (F1), armar el producto paso a paso (F2–F3), extras (F4), descripción y precio actualizados en vivo (F5),
varios productos por pedido con cantidad (F6), subtotal y total (F7), confirmar / quitar productos / cancelar antes de cocina (F8),
comprobante y comanda (F9), cambio de estado (F10), listado del día (F11), catálogo y precios editables (F12) y reportes de ventas (F13).

Notas de diseño:
- El tamaño multiplica el precio base del producto (Pequeña ×1.0, Mediana ×1.3, Grande ×1.6; redondeado a la centena).
- Se aplica un impoconsumo del 8 % (constante `Pedido.TASA_IMPUESTO`; ponerla en `0` lo desactiva).
- Los datos viven en memoria (no hay base de datos). El comprobante de cada pedido confirmado se guarda en `comprobantes/pedido_N.txt`.

## Estructura

```
src/com/armatuplato/
├── Main.java
├── builder/      Patrón Builder
├── decorator/    Patrón Decorator
├── modelo/       IProducto, Producto, Pizza, Hamburguesa, Catalogo, Ingrediente, enums
├── servicio/     Pedido, ItemPedido, GestorPedidos, Facturacion
├── excepciones/  Excepciones propias
├── ui/           Interfaz gráfica (Swing)
└── util/         Formato de moneda y fechas
docs/             Diagrama de clases UML (PNG, PDF y PlantUML)
test/             Prueba de la lógica en consola
```

## Cómo compilar y ejecutar

Desde la carpeta raíz del proyecto:

**Windows (PowerShell / CMD)**
```
dir /s /b src\*.java > sources.txt
javac -encoding UTF-8 -d out @sources.txt
java -cp out com.armatuplato.Main
```

**Linux / macOS**
```
find src -name "*.java" > sources.txt
javac -encoding UTF-8 -d out @sources.txt
java -cp out com.armatuplato.Main
```

También se puede abrir la carpeta en IntelliJ IDEA, Eclipse o NetBeans: marcar `src` como carpeta de fuentes y ejecutar `com.armatuplato.Main`.

Prueba de lógica sin interfaz (después de compilar):
```
java -cp out test/PruebaLogica.java
```
# Repositorio de GITHUB

https://github.com/ForTec20/ArmaTuPlato/tree/main

