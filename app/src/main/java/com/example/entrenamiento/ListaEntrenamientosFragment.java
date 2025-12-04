package com.example.entrenamiento;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * FRAGMENT QUE MUESTRA LA LISTA DE ENTRENAMIENTOS
 * Se muestra en portrait (pantalla completa) y en landscape (panel izquierdo 30%)
 */
public class ListaEntrenamientosFragment extends Fragment {
    // ListView = componente que muestra la lista con scroll
    private ListView listView;

    // Adapter = conecta los datos (entrenamientos) con el ListView
    private EntrenamientoAdapter adapter;

    // Lista de entrenamientos (static para que persista durante rotaciones)
    private static List<Entrenamiento> entrenamientos;

    // ID para el siguiente entrenamiento nuevo (empieza en 5 porque ya hay 4)
    private static int nextId = 5;

    // ARRAY CON LOS 4 ICONOS QUE SE USARÁN DE FORMA ROTATIVA
    // Solo estos 4 iconos, no más
    private static final int[] ICONOS_DISPONIBLES = {
            android.R.drawable.ic_menu_compass,      // Icono 1 - Brújula (Cardio)
            android.R.drawable.ic_menu_today,        // Icono 2 - Calendario (Fuerza)
            android.R.drawable.ic_menu_myplaces,     // Icono 3 - Ubicación (Yoga)
            android.R.drawable.ic_menu_agenda        // Icono 4 - Agenda (Crossfit)
    };

    // Índice del siguiente icono a usar (rota de 0 a 3)
    private static int iconoIndex = 0;

    // Listener para comunicarse con la Activity cuando se selecciona un entrenamiento
    private OnEntrenamientoSeleccionadoListener listener;

    /**
     * INTERFAZ PARA COMUNICARSE CON LA ACTIVITY
     * Cuando se hace click en un entrenamiento, se notifica a la Activity
     */
    public interface OnEntrenamientoSeleccionadoListener {
        void onEntrenamientoSeleccionado(Entrenamiento entrenamiento);
    }

    /**
     * SE EJECUTA CUANDO EL FRAGMENT SE ADJUNTA A LA ACTIVITY
     * Aquí se conecta el listener
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verificar que la Activity implemente la interfaz
        if (context instanceof OnEntrenamientoSeleccionadoListener) {
            listener = (OnEntrenamientoSeleccionadoListener) context;
        }
    }

    /**
     * SE EJECUTA CUANDO SE CREA LA VISTA DEL FRAGMENT
     * Aquí se infla el layout y se configura el ListView
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el layout del fragment (convertir XML en objetos Java)
        View view = inflater.inflate(R.layout.fragment_lista_entrenamientos, container, false);

        // Obtener referencia al ListView del layout
        listView = view.findViewById(R.id.listViewEntrenamientos);

        // INICIALIZAR LA LISTA DE ENTRENAMIENTOS (solo la primera vez)
        // Como es static, se mantiene durante rotaciones
        if (entrenamientos == null) {
            entrenamientos = new ArrayList<>();

            // ENTRENAMIENTOS PREDEFINIDOS (los 4 iniciales)
            // Cada uno tiene: ID, Nombre, Ejercicios, Icono, Duración, Dificultad
            entrenamientos.add(new Entrenamiento(1, "Cardio",
                "Correr 30min, Bicicleta 20min, Saltar cuerda 10min",
                android.R.drawable.ic_menu_compass, "60 minutos", "Media"));

            entrenamientos.add(new Entrenamiento(2, "Fuerza",
                "Press banca 4x10, Sentadillas 4x12, Peso muerto 3x8",
                android.R.drawable.ic_menu_today, "45 minutos", "Alta"));

            entrenamientos.add(new Entrenamiento(3, "Yoga",
                "Saludo al sol, Postura del árbol, Postura del guerrero",
                android.R.drawable.ic_menu_myplaces, "30 minutos", "Baja"));

            entrenamientos.add(new Entrenamiento(4, "Crossfit",
                "Burpees 5min, Kettlebell swings, Box jumps",
                android.R.drawable.ic_menu_agenda, "40 minutos", "Alta"));
        }

        // CREAR EL ADAPTER
        // El adapter conecta los datos (entrenamientos) con el ListView
        adapter = new EntrenamientoAdapter(getContext(), entrenamientos);
        listView.setAdapter(adapter);

        // CONFIGURAR EL CLICK EN CADA ITEM DE LA LISTA
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el entrenamiento en la posición clickeada
                Entrenamiento entrenamientoSeleccionado = entrenamientos.get(position);

                // Notificar a la Activity que se seleccionó un entrenamiento
                if (listener != null) {
                    listener.onEntrenamientoSeleccionado(entrenamientoSeleccionado);
                }
            }
        });

        return view;
    }

    /**
     * AÑADIR UN NUEVO ENTRENAMIENTO A LA LISTA
     * Este método se llama desde MainActivity cuando se guarda el diálogo
     */
    public void agregarEntrenamiento(String nombre, String descripcion, String duracion, String dificultad) {
        // ASIGNAR ICONO DE FORMA ROTATIVA (solo los 4 disponibles)
        // iconoIndex va de 0 a 3 y luego vuelve a 0
        int iconoResId = ICONOS_DISPONIBLES[iconoIndex];
        iconoIndex = (iconoIndex + 1) % ICONOS_DISPONIBLES.length; // % 4 hace que rote (0,1,2,3,0,1,2,3...)

        // Crear el nuevo objeto Entrenamiento
        Entrenamiento nuevoEntrenamiento = new Entrenamiento(
                nextId++,         // ID autoincremental (5, 6, 7...)
                nombre,           // Tipo de entrenamiento (ej: "Pilates")
                descripcion,      // Ejercicios (ej: "Plancha, Abdominales")
                iconoResId,       // Icono asignado automáticamente
                duracion,         // Duración (ej: "45 minutos")
                dificultad        // Dificultad (ej: "Media")
        );

        // Añadir a la lista
        entrenamientos.add(nuevoEntrenamiento);

        // Notificar al adapter que los datos cambiaron
        // Esto hace que el ListView se actualice y muestre el nuevo entrenamiento
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * BUSCAR UN ENTRENAMIENTO POR SU ID
     * Se usa para recuperar el entrenamiento al rotar la pantalla
     */
    public static Entrenamiento getEntrenamientoById(int id) {
        if (entrenamientos != null) {
            // Recorrer todos los entrenamientos
            for (Entrenamiento e : entrenamientos) {
                if (e.getId() == id) {
                    return e; // Encontrado
                }
            }
        }
        return null; // No encontrado
    }

    /**
     * OBTENER EL PRIMER ENTRENAMIENTO DE LA LISTA
     * Se usa en landscape para mostrar algo por defecto al iniciar
     */
    public static Entrenamiento getPrimerEntrenamiento() {
        if (entrenamientos != null && !entrenamientos.isEmpty()) {
            return entrenamientos.get(0); // Devuelve "Cardio"
        }
        return null;
    }

    /**
     * OBTENER TODA LA LISTA DE ENTRENAMIENTOS
     */
    public static List<Entrenamiento> getEntrenamientos() {
        return entrenamientos;
    }
}
