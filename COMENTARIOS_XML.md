# 📚 RESUMEN COMENTARIOS XML - PARA EL EXAMEN

## ✅ ARCHIVOS XML COMENTADOS (8 archivos)

### 1️⃣ **activity_main.xml (Portrait)** - Layout Vertical
```
📂 Ubicación: res/layout/
📱 Cuándo se usa: Dispositivo en VERTICAL

Estructura:
├── LinearLayout (vertical)
│   ├── Toolbar (barra superior con botón "Añadir")
│   └── FrameLayout (fragment_container)
│       └── UN SOLO fragment a la vez
│           ├── ListaEntrenamientosFragment (lista completa)
│           └── DetalleEntrenamientoFragment (detalles, reemplaza lista)
```

### 2️⃣ **activity_main.xml (Landscape)** - Layout Horizontal
```
📂 Ubicación: res/layout-land/
📱 Cuándo se usa: Dispositivo en HORIZONTAL

Estructura:
├── LinearLayout (vertical)
│   ├── Toolbar (barra superior)
│   └── LinearLayout (horizontal)
│       ├── FrameLayout (fragment_lista) - 30%
│       │   └── ListaEntrenamientosFragment
│       └── FrameLayout (fragment_detalle) - 70%
│           └── DetalleEntrenamientoFragment

⚙️ PROPORCIÓN (layout_weight):
   - 1:2 = 30%-70% (actual)
   - 1:1 = 50%-50% (cambiar weight a 1 y 1)
   - 1:3 = 25%-75% (cambiar weight a 1 y 3)
```

### 3️⃣ **fragment_lista_entrenamientos.xml** - Lista
```
📂 Ubicación: res/layout/

Componente principal: ListView
- ID: listViewEntrenamientos
- Muestra lista con scroll
- Cada fila usa: item_entrenamiento.xml
- Divisor gris entre elementos

Se conecta en Java con:
listView = findViewById(R.id.listViewEntrenamientos);
adapter = new EntrenamientoAdapter(context, entrenamientos);
listView.setAdapter(adapter);
```

### 4️⃣ **fragment_detalle_entrenamiento.xml** - Detalles
```
📂 Ubicación: res/layout/

Componentes (de arriba a abajo):
1. ScrollView (permite scroll)
2. ImageView (imageViewDetalleIcono) - 100x100dp
3. TextView (textViewDetalleNombre) - 28sp, bold, centrado
4. LinearLayout horizontal:
   - TextView (textViewDetalleDuracion) - "Duración: 60 min"
   - TextView (textViewDetalleDificultad) - "Dificultad: Alta"
5. TextView "Ejercicios:" - 20sp, bold
6. TextView (textViewDetalleDescripcion) - Lista de ejercicios

🎨 CAMBIAR TAMAÑOS:
   - textSize="28sp" → "32sp" (más grande)
   - layout_width="100dp" → "120dp" (icono más grande)
```

### 5️⃣ **item_entrenamiento.xml** - Fila del ListView
```
📂 Ubicación: res/layout/

Estructura de CADA FILA:
├── LinearLayout (horizontal)
│   ├── ImageView (imageViewIcono) - 48x48dp
│   └── TextView (textViewNombre) - 18sp, bold

Este layout se reutiliza para cada entrenamiento visible.
Si ves 6 entrenamientos, este XML se usa 6 veces.
```

### 6️⃣ **dialog_action.xml** - Diálogo Simple
```
📂 Ubicación: res/layout/

Campos del diálogo:
1. TextView "Tipo" (etiqueta)
2. EditText (editTextTipo) - Una línea
3. TextView "Ejercicios" (etiqueta)
4. EditText (editTextEjercicios) - Multilínea (minLines="3")

Botones "Save" y "Cancel" los añade AlertDialog automáticamente.
```

### 7️⃣ **dialog_nuevo_entrenamiento.xml** - Diálogo Completo
```
📂 Ubicación: res/layout/

Campos del diálogo:
1. EditText (editTextNombre) - Nombre
2. EditText (editTextDescripcion) - Descripción multilínea
3. EditText (editTextDuracion) - Duración
4. Spinner (spinnerDificultad) - Lista desplegable (Baja/Media/Alta)

NOTA: Este diálogo NO se usa actualmente.
La app usa dialog_action.xml (más simple).
```

### 8️⃣ **main_menu.xml** - Menú de la Toolbar
```
📂 Ubicación: res/menu/

Elemento del menú:
<item
    android:id="@+id/action_button"
    android:title="Añadir"
    android:icon="@android:drawable/ic_menu_add"
    app:showAsAction="always" />

showAsAction opciones:
- "always" = siempre visible
- "ifRoom" = visible si hay espacio
- "never" = siempre en menú overflow (...)

🎨 CAMBIAR ICONO:
   - ic_menu_edit (lápiz)
   - ic_input_add (+ en círculo)
   - ic_menu_camera (cámara)
```

### 9️⃣ **strings.xml** - Textos de la App
```
📂 Ubicación: res/values/

Recursos definidos:
1. <string name="app_name">Entrenamiento</string>
   - Nombre de la app

2. <string-array name="dificultades">
   - Array para el Spinner
   - Opciones: Baja, Media, Alta

Para USAR en XML:
android:text="@string/app_name"

Para USAR en Java:
String nombre = getString(R.string.app_name);
```

---

## 🔑 CONCEPTOS XML CLAVE

### 📏 **Unidades de Medida**
```xml
dp (density-independent pixels) - Para tamaños de componentes
   Ejemplo: android:layout_width="100dp"
   
sp (scale-independent pixels) - Para tamaños de texto
   Ejemplo: android:textSize="18sp"
   
px (píxeles) - NO usar (no se adapta a diferentes pantallas)
```

### 📐 **match_parent vs wrap_content vs 0dp**
```xml
match_parent = ocupa TODO el espacio disponible
   android:layout_width="match_parent"

wrap_content = ajusta al tamaño del contenido
   android:layout_width="wrap_content"

0dp + layout_weight = tamaño calculado según proporción
   android:layout_width="0dp"
   android:layout_weight="1"
```

### ⚖️ **layout_weight (Proporción)**
```xml
<!-- Panel izquierdo 30% -->
<FrameLayout
    android:layout_width="0dp"
    android:layout_weight="1" />

<!-- Panel derecho 70% -->
<FrameLayout
    android:layout_width="0dp"
    android:layout_weight="2" />

Total: 1+2 = 3 partes
Panel 1 = 1/3 = 33% ≈ 30%
Panel 2 = 2/3 = 66% ≈ 70%
```

### 🎨 **Colores del Sistema**
```xml
@android:color/black - Negro
@android:color/white - Blanco
@android:color/darker_gray - Gris oscuro
@android:color/transparent - Transparente

?attr/colorPrimary - Color primario del tema
?attr/actionBarSize - Altura estándar de ActionBar
```

### 🖼️ **Iconos del Sistema**
```xml
@android:drawable/ic_menu_add - Icono +
@android:drawable/ic_menu_edit - Icono lápiz
@android:drawable/ic_menu_compass - Brújula
@android:drawable/ic_menu_today - Calendario
@android:drawable/ic_menu_camera - Cámara
@android:drawable/ic_menu_gallery - Galería
```

---

## 📝 PARA EL EXAMEN - CAMBIOS RÁPIDOS

### ✏️ Cambiar proporción landscape (30%-70% → 50%-50%):
```xml
<!-- En layout-land/activity_main.xml -->
<FrameLayout android:layout_weight="1" /> <!-- Era 1 -->
<FrameLayout android:layout_weight="1" /> <!-- Era 2, cambiar a 1 -->
```

### ✏️ Cambiar tamaño de texto del nombre:
```xml
<!-- En fragment_detalle_entrenamiento.xml -->
<TextView
    android:id="@+id/textViewDetalleNombre"
    android:textSize="32sp" /> <!-- Era 28sp -->
```

### ✏️ Cambiar icono del botón "Añadir":
```xml
<!-- En menu/main_menu.xml -->
<item
    android:icon="@android:drawable/ic_menu_edit" /> <!-- Era ic_menu_add -->
```

### ✏️ Añadir nueva dificultad:
```xml
<!-- En values/strings.xml -->
<string-array name="dificultades">
    <item>Muy Baja</item> <!-- NUEVA -->
    <item>Baja</item>
    <item>Media</item>
    <item>Alta</item>
    <item>Muy Alta</item> <!-- NUEVA -->
</string-array>
```

---

## 🎓 PREGUNTAS TÍPICAS DE EXAMEN

**P: ¿Dónde está el layout para landscape?**
R: En `res/layout-land/activity_main.xml`

**P: ¿Cómo cambiar la proporción a 50%-50%?**
R: Poner `layout_weight="1"` en ambos FrameLayout

**P: ¿Qué hace `commitNow()` en Java?**
R: Ejecuta la transacción INMEDIATAMENTE (evita pantalla en blanco)

**P: ¿Qué es un Spinner?**
R: Lista desplegable para seleccionar opciones (como "Baja", "Media", "Alta")

**P: ¿Diferencia entre `dp` y `sp`?**
R: `dp` para tamaños de componentes, `sp` para tamaños de texto

---

✅ **TODOS LOS ARCHIVOS XML ESTÁN COMPLETAMENTE COMENTADOS** 🎉

¡Ahora puedes entender cada parte de los layouts! 📖

