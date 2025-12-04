# App de Gestión de Entrenamientos

Aplicación Android en Java para gestionar entrenamientos deportivos con soporte para orientación portrait y landscape.

## Características

-  Lista de entrenamientos con iconos
-  Vista detallada de cada entrenamiento
-  Sistema de fragments adaptativo
-  Soporte portrait (vertical) y landscape (horizontal)
-  Diálogo para añadir nuevos entrenamientos
-  Sistema de iconos rotativos (4 iconos predefinidos)
-  Persistencia de datos durante rotaciones

## Entrenamientos Predefinidos

1. **Cardio** - Correr 30min, Bicicleta 20min, Saltar cuerda 10min
2. **Fuerza** - Press banca 4x10, Sentadillas 4x12, Peso muerto 3x8
3. **Yoga** - Saludo al sol, Postura del árbol, Postura del guerrero
4. **Crossfit** - Burpees 5min, Kettlebell swings, Box jumps

## Arquitectura

### Modo Portrait (Vertical)
- Un solo contenedor mostrando un fragment a la vez
- Click en entrenamiento → Muestra detalles (pantalla completa)
- Botón atrás → Vuelve a la lista

### Modo Landscape (Horizontal)
- Dos contenedores simultáneos:
  - **Panel izquierdo (30%)**: Lista de entrenamientos
  - **Panel derecho (70%)**: Detalles del entrenamiento
- Lista siempre visible
- Click en entrenamiento → Actualiza panel derecho

## Estructura del Proyecto

### Archivos Java Principales

#### 1. `MainActivity.java`
**Cerebro de la aplicación**
- Gestiona orientación portrait/landscape
- Carga fragments según la configuración
- Maneja el menú y botón "Añadir"
- Usa `commitNow()` para evitar pantallas en blanco

#### 2. `ListaEntrenamientosFragment.java`
**Fragment de lista**
- Muestra ListView con entrenamientos
- Gestiona el adapter
- Asigna iconos automáticamente en rotación (0→1→2→3→0)
- **Para añadir entrenamientos**: Ver líneas 97-114

#### 3. `DetalleEntrenamientoFragment.java`
**Fragment de detalles**
- Muestra información completa del entrenamiento
- Recibe datos mediante Bundle
- Conecta con `fragment_detalle_entrenamiento.xml`

#### 4. `ActionDialogFragment.java`
**Diálogo de añadir**
- Campos: Tipo y Ejercicios
- Validación de campos obligatorios
- Botones: Save y Cancel

#### 5. `Entrenamiento.java`
**Modelo de datos**
- Atributos: id, nombre, descripcion, iconoResId, duracion, dificultad
- Getters y setters

#### 6. `EntrenamientoAdapter.java`
**Adapter personalizado**
- Conecta datos con ListView
- Crea cada fila (icono + nombre)
- Reutiliza vistas para mejor rendimiento

### Archivos XML Principales

#### Layouts
- `layout/activity_main.xml` - Portrait (1 contenedor)
- `layout-land/activity_main.xml` - Landscape (2 contenedores)
- `layout/fragment_lista_entrenamientos.xml` - ListView
- `layout/fragment_detalle_entrenamiento.xml` - Detalles
- `layout/item_entrenamiento.xml` - Fila del ListView
- `layout/dialog_action.xml` - Diálogo añadir

#### Menú
- `menu/main_menu.xml` - Botón "Añadir" en toolbar

#### Recursos
- `values/strings.xml` - Textos y array de dificultades

## Cómo Añadir un Nuevo Entrenamiento

### Manualmente en el código:

**Archivo:** `ListaEntrenamientosFragment.java` (líneas 97-114)

```java
if (entrenamientos == null) {
    entrenamientos = new ArrayList<>();
    
    // Entrenamientos existentes...
    
    // AÑADIR NUEVO AQUÍ:
    entrenamientos.add(new Entrenamiento(5, "Pilates",
        "Plancha 1min, Abdominales 3x20, Estiramiento",
        android.R.drawable.ic_menu_compass, "35 minutos", "Media"));
}
```

**Importante:** Cambiar también `nextId = 5` por `nextId = 6` en la línea 28

### Mediante la app:
1. Pulsa el botón **+** en la toolbar
2. Rellena campos: Tipo y Ejercicios
3. El icono se asigna automáticamente

## Iconos Disponibles (Rotación Automática)

Los nuevos entrenamientos usan estos 4 iconos en orden:

1. `ic_menu_compass` (Brújula) - Cardio
2. `ic_menu_today` (Calendario) - Fuerza
3. `ic_menu_myplaces` (Ubicación) - Yoga
4. `ic_menu_agenda` (Agenda) - Crossfit

Luego vuelve al primero (rotación infinita).

## ️ Cambios Rápidos

### Cambiar proporción landscape (30%-70% → 50%-50%):
**Archivo:** `layout-land/activity_main.xml`
```xml
<FrameLayout android:layout_weight="1" /> <!-- Cambiar de 1 a 1 -->
<FrameLayout android:layout_weight="1" /> <!-- Cambiar de 2 a 1 -->
```

### Cambiar icono del botón "Añadir":
**Archivo:** `menu/main_menu.xml`
```xml
<item android:icon="@android:drawable/ic_menu_edit" />
```

### Añadir nueva dificultad:
**Archivo:** `values/strings.xml`
```xml
<string-array name="dificultades">
    <item>Muy Baja</item>
    <item>Baja</item>
    <item>Media</item>
    <item>Alta</item>
    <item>Muy Alta</item>
</string-array>
```

## Conceptos Técnicos Clave

### commitNow() vs commit()
```java
// EVITAR (puede causar pantalla en blanco):
fm.beginTransaction().replace(...).commit();

// USAR (ejecución inmediata):
fm.beginTransaction().replace(...).commitNow();
```

### Detección de Orientación
```java
boolean isDualPane = findViewById(R.id.fragment_detalle) != null;
// Si existe fragment_detalle → Landscape
// Si no existe → Portrait
```

### Rotación de Iconos
```java
int iconoResId = ICONOS_DISPONIBLES[iconoIndex];
iconoIndex = (iconoIndex + 1) % 4; // Rota: 0→1→2→3→0
```

### Unidades de Medida XML
- **dp** (density-independent pixels) → Para tamaños de componentes
- **sp** (scale-independent pixels) → Para tamaños de texto
- **match_parent** → Ocupa todo el espacio disponible
- **wrap_content** → Se ajusta al contenido
- **0dp + layout_weight** → Tamaño proporcional

## Solución de Problemas

### Pantalla en blanco al rotar
- **Causa:** Uso de `commit()` en lugar de `commitNow()`
- **Solución:** Ver `MainActivity.configurarFragments()` (usa `commitNow()`)

### No aparece la lista en landscape
- **Causa:** Fragments en contenedor incorrecto
- **Solución:** Limpiar fragments del contenedor anterior antes de cargar

### App crashea al añadir entrenamiento
- **Causa:** Adapter null o lista null
- **Solución:** Verificar `if (adapter != null)` antes de `notifyDataSetChanged()`

## Flujo de Datos

```
MainActivity
    ↓
ListaEntrenamientosFragment
    ↓
EntrenamientoAdapter → Entrenamiento (modelo)
    ↓
Click en item
    ↓
DetalleEntrenamientoFragment → Entrenamiento (modelo)

Botón "Añadir"
    ↓
ActionDialogFragment
    ↓
MainActivity.onActionSave()
    ↓
ListaEntrenamientosFragment.agregarEntrenamiento()
```

## Notas para Desarrollo

### Métodos Importantes MainActivity
- `onCreate()` - Inicialización y detección de orientación
- `configurarFragments()` - **Clave para evitar pantalla en blanco**
- `onSaveInstanceState()` - Guarda datos antes de rotación
- `onEntrenamientoSeleccionado()` - Maneja clicks en lista

### Variables Estáticas
- `entrenamientos` (List) - Persiste durante rotaciones
- `nextId` - ID autoincremental para nuevos entrenamientos
- `iconoIndex` - Índice del icono actual (0-3)

## Requisitos

- **Android Studio** Arctic Fox o superior
- **minSdkVersion** 21 (Android 5.0)
- **targetSdkVersion** 33 (Android 13)
- **Java** 8 o superior

## Instalación

1. Clonar el repositorio
2. Abrir con Android Studio
3. Sync Gradle
4. Run en emulador o dispositivo físico

---