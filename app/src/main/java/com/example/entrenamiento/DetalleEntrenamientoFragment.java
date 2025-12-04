package com.example.entrenamiento;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * FRAGMENT QUE MUESTRA LOS DETALLES DE UN ENTRENAMIENTO
 * Se muestra en portrait (pantalla completa) y en landscape (panel derecho 70%)
 */
public class DetalleEntrenamientoFragment extends Fragment {
    // Clave para pasar el ID del entrenamiento en el Bundle
    private static final String ARG_ENTRENAMIENTO_ID = "entrenamiento_id";

    // COMPONENTES DE LA INTERFAZ (se conectan con el XML)
    private TextView textViewNombre;        // Nombre del entrenamiento (ej: "Cardio")
    private TextView textViewDescripcion;   // Lista de ejercicios
    private TextView textViewDuracion;      // Duración (ej: "60 minutos")
    private TextView textViewDificultad;    // Dificultad (ej: "Alta")
    private ImageView imageViewIcono;       // Icono del entrenamiento

    // Objeto con todos los datos del entrenamiento
    private Entrenamiento entrenamiento;

    /**
     * MÉTODO ESTÁTICO PARA CREAR UNA INSTANCIA DEL FRAGMENT
     * Se usa desde MainActivity para pasar datos al fragment
     *
     * @param entrenamiento El entrenamiento que se quiere mostrar
     * @return Una nueva instancia del fragment con los datos
     */
    public static DetalleEntrenamientoFragment newInstance(Entrenamiento entrenamiento) {
        DetalleEntrenamientoFragment fragment = new DetalleEntrenamientoFragment();

        // Bundle = "bolsa" para pasar datos entre fragments/activities
        Bundle args = new Bundle();
        args.putInt(ARG_ENTRENAMIENTO_ID, entrenamiento.getId()); // Solo pasamos el ID
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * SE EJECUTA CUANDO SE CREA LA VISTA DEL FRAGMENT
     * Aquí se infla el layout y se muestran los datos del entrenamiento
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el layout del fragment (convertir XML en objetos Java)
        View view = inflater.inflate(R.layout.fragment_detalle_entrenamiento, container, false);

        // CONECTAR LAS VARIABLES CON LOS COMPONENTES DEL XML
        // findViewById busca el componente por su ID
        textViewNombre = view.findViewById(R.id.textViewDetalleNombre);
        textViewDescripcion = view.findViewById(R.id.textViewDetalleDescripcion);
        textViewDuracion = view.findViewById(R.id.textViewDetalleDuracion);
        textViewDificultad = view.findViewById(R.id.textViewDetalleDificultad);
        imageViewIcono = view.findViewById(R.id.imageViewDetalleIcono);

        // OBTENER EL ID DEL ENTRENAMIENTO QUE SE PASÓ EN EL BUNDLE
        if (getArguments() != null) {
            int entrenamientoId = getArguments().getInt(ARG_ENTRENAMIENTO_ID);
            cargarDetalles(entrenamientoId); // Cargar y mostrar los datos
        }

        return view;
    }

    /**
     * CARGAR Y MOSTRAR LOS DATOS DEL ENTRENAMIENTO
     *
     * @param entrenamientoId ID del entrenamiento a mostrar
     */
    private void cargarDetalles(int entrenamientoId) {
        // Buscar el entrenamiento en la lista usando su ID
        entrenamiento = ListaEntrenamientosFragment.getEntrenamientoById(entrenamientoId);

        // Si se encontró el entrenamiento, mostrar sus datos
        if (entrenamiento != null) {
            // Mostrar el NOMBRE
            textViewNombre.setText(entrenamiento.getNombre());

            // Mostrar la DESCRIPCIÓN (lista de ejercicios)
            textViewDescripcion.setText(entrenamiento.getDescripcion());

            // Mostrar la DURACIÓN (si existe)
            if (entrenamiento.getDuracion() != null && !entrenamiento.getDuracion().isEmpty()) {
                textViewDuracion.setText("Duración: " + entrenamiento.getDuracion());
            } else {
                // Si no hay duración, ocultar el TextView
                textViewDuracion.setVisibility(View.GONE);
            }

            // Mostrar la DIFICULTAD (si existe)
            if (entrenamiento.getDificultad() != null && !entrenamiento.getDificultad().isEmpty()) {
                textViewDificultad.setText("Dificultad: " + entrenamiento.getDificultad());
            } else {
                // Si no hay dificultad, ocultar el TextView
                textViewDificultad.setVisibility(View.GONE);
            }

            // Mostrar el ICONO
            imageViewIcono.setImageResource(entrenamiento.getIconoResId());
        }
    }

    /**
     * ACTUALIZAR LOS DETALLES CON UN NUEVO ENTRENAMIENTO
     * Se puede usar para cambiar dinámicamente el entrenamiento mostrado
     * (Actualmente no se usa en la app, pero está disponible)
     */
    public void actualizarDetalle(Entrenamiento nuevoEntrenamiento) {
        this.entrenamiento = nuevoEntrenamiento;
        // Si la vista ya está creada, actualizar los datos
        if (textViewNombre != null) {
            cargarDetalles(nuevoEntrenamiento.getId());
        }
    }
}
