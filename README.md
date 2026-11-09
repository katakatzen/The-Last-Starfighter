[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/LhnUoRsc)

# The Last Starfighter

Juego de naves 2D de desplazamiento horizontal hecho en **Java + Swing**, desarrollado como Trabajo Final de **Programación Orientada a Objetos** (Grupo 11).

Controlás una nave que tiene que sobrevivir a oleadas de enemigos cada vez más difíciles, juntar power-ups y conseguir el mejor puntaje para entrar al **ranking top 5**, que se guarda en una base de datos SQLite.

---

## Contenido

- [Cómo se juega](#cómo-se-juega)
- [Requisitos](#requisitos)
- [Cómo ejecutarlo](#cómo-ejecutarlo)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Conceptos de POO aplicados](#conceptos-de-poo-aplicados)
- [Integrantes](#integrantes)

---

## Cómo se juega

### Controles

| Tecla | Acción |
|---|---|
| `←` `→` `↑` `↓` | Mover la nave |
| `Espacio` | Disparar (mantener presionado) |
| Botón **Pausar** | Pausar / reanudar la partida |

### Objetivo

- Destruí enemigos para sumar puntos.
- Si un enemigo se escapa por el borde izquierdo, **perdés la mitad de los puntos que valía**.
- Empezás con **12 vidas**. Cada impacto o choque resta una. Cuando llegás a 0, termina la partida y tu puntaje se guarda en el ranking.

### Enemigos

| Enemigo | Vida | Movimiento | Ataque | Puntos |
|---|---|---|---|---|
| 🔴 **Rojo** | 1 | Zigzag | Disparo en línea recta | 200 |
| 🔵 **Azul** | 3 | Zigzag | Choque directo contra la nave | 300 |
| 🟣 **Violeta** | 6 | Línea recta | Misil teledirigido hacia la posición del jugador | 500 |

### Fases y dificultad

- El juego alterna entre una **fase tranquila** (30 s, enemigos violetas y azules) y una **oleada** (10 s, enemigos rojos y azules apareciendo más seguido).
- **Cada 30 segundos sube el nivel** (hasta el nivel 8): los enemigos aparecen más rápido, se mueven más rápido y disparan más seguido.

### Power-ups

Cada 30 segundos aparece un ítem en pantalla. Si lo agarrás, su efecto dura **15 segundos**:

| Power-up | Efecto |
|---|---|
| 🛡️ **Escudo** | La nave no recibe daño |
| 🚀 **Misil x3** | Los disparos hacen el triple de daño |

---

## Requisitos

- **JDK 17 o superior** (el proyecto usa *pattern matching* en `instanceof`, disponible desde Java 16).
- **Driver SQLite JDBC** `sqlite-jdbc-3.50.3.0.jar`.

> ⚠️ El `.gitignore` excluye los archivos `.jar`, así que **el driver no se descarga al clonar el repositorio**. Descargalo desde [Maven Central](https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.50.3.0/sqlite-jdbc-3.50.3.0.jar) y guardalo en la carpeta `SQLite/`.

---

## Cómo ejecutarlo

> **Importante:** el juego se tiene que ejecutar **desde la carpeta raíz del proyecto**, porque las imágenes, la fuente y la base de datos se cargan con rutas relativas (`Assets/...`, `fondo/...`, `juego.db`).

### Desde la terminal

**macOS / Linux**
```bash
javac -encoding UTF-8 -d out -cp "SQLite/sqlite-jdbc-3.50.3.0.jar" Main.java modelo/*.java vista/*.java controlador/*.java dao/*.java
java -cp "out:SQLite/sqlite-jdbc-3.50.3.0.jar" Main
```

**Windows** (en el classpath se usa `;` en lugar de `:`)
```bat
javac -encoding UTF-8 -d out -cp "SQLite/sqlite-jdbc-3.50.3.0.jar" Main.java modelo/*.java vista/*.java controlador/*.java dao/*.java
java -cp "out;SQLite/sqlite-jdbc-3.50.3.0.jar" Main
```

### Desde VS Code

1. Instalá el *Extension Pack for Java*.
2. Agregá `SQLite/sqlite-jdbc-3.50.3.0.jar` en **Java Projects → Referenced Libraries**.
3. Ejecutá `Main.java`. Asegurate de que el directorio de trabajo (`cwd`) sea la raíz del proyecto.

### Base de datos

El repositorio ya incluye `juego.db` con la tabla `jugadores`. Si la borrás, podés volver a crearla ejecutando `dao/CrearTabla.java` una vez.

---

## Estructura del proyecto

El código sigue el patrón **MVC**, con una capa **DAO** para la persistencia:

```
├── Main.java                  # Punto de entrada
├── modelo/                    # Lógica y entidades del juego
│   ├── personaje_base         # Clase abstracta base (posición, vida, daño, hitbox)
│   ├── spaceship_1            # Nave del jugador
│   ├── Enemigo                # Clase abstracta de los enemigos
│   ├── Enemigo_Rojo / _Azul / _Violeta
│   ├── TipoEnemigo            # Enum de tipos de enemigo
│   ├── Factory_Enemigos       # Creación de enemigos
│   ├── EstrategiaMovimiento   # Interfaz Strategy
│   ├── MovimientoZigzag / MovimientoRecto
│   ├── misil, misilTeledirigido, misilColision
│   ├── Decorator_spaceship    # Decorator base
│   ├── Decorator_Escudo / Decorator_misilx3
│   ├── TipoPowerUp            # Enum de power-ups
│   ├── AplicacionDecorators   # Aparición y duración de los power-ups
│   ├── Gestor_Combate         # Detección de impactos y choques
│   ├── Pantalla               # Medidas del área de juego
│   └── JugadorBD              # Datos de un jugador del ranking
├── vista/                     # Pantallas Swing
│   ├── Ventanas               # JFrame con CardLayout entre pantallas
│   ├── panel                  # Pantalla de juego (dibujo)
│   ├── PantallaInicio, PantallaInstrucciones, PantallaRanking, PantallaGameOver
│   └── FondoAnimado           # Fondo con efecto parallax
├── controlador/
│   ├── MotorJuego             # Game loop (Timer de ~60 FPS)
│   ├── controlador            # Entrada de teclado (KeyListener)
│   ├── controladorEnemigos    # Fases, aparición de enemigos y dificultad
│   └── cargaBDD               # Guarda el puntaje final
├── dao/
│   ├── ConexionBD             # Conexión a SQLite (Singleton)
│   ├── JugadorDAO             # Guardar puntaje / consultar top 5
│   └── CrearTabla             # Script para crear la tabla
├── Assets/                    # Sprites, botones y fuente
├── fondo/                     # Capas del fondo animado
└── juego.db                   # Base de datos SQLite
```

---

## Conceptos de POO aplicados

### Pilares

| Pilar | Dónde se aplica |
|---|---|
| **Abstracción** | `personaje_base` y `Enemigo` son abstractas. `Enemigo` define qué hace cualquier enemigo (`atacar`, `getPuntos`, `getTipo`) sin decir cómo. |
| **Encapsulamiento** | Los atributos son `private` y se acceden con getters/setters. El daño pasa por `recibirDanio()`, que también maneja la invulnerabilidad temporal. |
| **Herencia** | `spaceship_1` y `Enemigo` heredan de `personaje_base`. Cada enemigo hereda de `Enemigo`. `misilTeledirigido` y `misilColision` heredan de `misil`. |
| **Polimorfismo** | El motor trata a todos los enemigos como `Enemigo` (`e.movimiento()`, `e.atacar(...)`), y cada uno responde a su manera. La vista elige el sprite según `getTipo()`, sin cadenas de `if`. `Gestor_Combate.Verificar_Impacto` está sobrecargado. |

### Patrones de diseño

| Patrón | Clases | Para qué |
|---|---|---|
| **Decorator** | `Decorator_spaceship`, `Decorator_Escudo`, `Decorator_misilx3` | Agregar comportamiento temporal a la nave (inmunidad, más daño) sin modificar `spaceship_1`. |
| **Factory** | `Factory_Enemigos` | Centralizar la creación de enemigos según su `TipoEnemigo`. |
| **Strategy** | `EstrategiaMovimiento`, `MovimientoZigzag`, `MovimientoRecto` | Separar *qué enemigo es* de *cómo se mueve*. Cambiar el movimiento no requiere tocar la clase del enemigo. |
| **Singleton** | `ConexionBD` | Una única conexión a la base de datos durante toda la ejecución. |
| **DAO** | `JugadorDAO` | Aislar las consultas SQL del resto del juego. |
| **MVC** | `modelo/`, `vista/`, `controlador/` | Separar lógica, presentación y control. |

### Principio abierto/cerrado

Para agregar un enemigo nuevo:

1. Crear su clase heredando de `Enemigo` y elegir una `EstrategiaMovimiento`.
2. Agregar una constante a `TipoEnemigo`.
3. Agregar un `case` en `Factory_Enemigos`.
4. Cargar sus sprites en `panel`.

No hace falta modificar `MotorJuego` ni `Gestor_Combate`.

---

## Integrantes

**Grupo 11**

- [katakatzen](https://github.com/katakatzen)
- [lautaro9555](https://github.com/lautaro9555)
- [MarcosBonetto](https://github.com/MarcosBonetto)

---

## Licencia

Distribuido bajo licencia MIT. Ver [`LICENSE`](LICENSE).
