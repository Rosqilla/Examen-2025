package com.example.entrenamiento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * ADAPTER PERSONALIZADO PARA EL LISTVIEW DE ENTRENAMIENTOS
 *
 * Un Adapter es un "puente" que conecta los datos (lista de entrenamientos)
 * con la interfaz visual (ListView)
 *
 * Por cada entrenamiento en la lista, este adapter crea una fila (item)
 * mostrando el icono y el nombre
 */
public class EntrenamientoAdapter extends ArrayAdapter<Entrenamiento> {
    private Context context;                    // Contexto de la aplicación
    private List<Entrenamiento> entrenamientos; // Lista de datos

    /**
     * CONSTRUCTOR DEL ADAPTER
     *
     * @param context Contexto de la aplicación (normalmente la Activity o Fragment)
     * @param entrenamientos Lista de entrenamientos a mostrar
     */
    public EntrenamientoAdapter(@NonNull Context context, @NonNull List<Entrenamiento> entrenamientos) {
        super(context, 0, entrenamientos); // Llamar al constructor de ArrayAdapter
        this.context = context;
        this.entrenamientos = entrenamientos;
    }

    /**
     * MÉTODO QUE CREA Y DEVUELVE LA VISTA PARA CADA FILA DEL LISTVIEW
     *
     * Este método se llama automáticamente por cada elemento visible en la pantalla
     * Por ejemplo, si se ven 6 entrenamientos, se llama 6 veces
     *
     * @param position Posición del elemento en la lista (0, 1, 2, 3...)
     * @param convertView Vista reciclada (para reutilizar y mejorar rendimiento)
     * @param parent El ListView padre
     * @return La vista configurada para este elemento
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // REUTILIZAR O CREAR NUEVA VISTA
        // Si convertView es null, crear una nueva vista
        // Si no es null, reutilizarla (mejor rendimiento)
        View listItem = convertView;
        if (listItem == null) {
            // LayoutInflater convierte el XML (item_entrenamiento.xml) en objetos Java
            listItem = LayoutInflater.from(context).inflate(R.layout.item_entrenamiento, parent, false);
        }

        // OBTENER EL ENTRENAMIENTO EN LA POSICIÓN ACTUAL
        Entrenamiento currentEntrenamiento = entrenamientos.get(position);

        // CONECTAR LOS COMPONENTES DEL XML CON VARIABLES
        ImageView imageView = listItem.findViewById(R.id.imageViewIcono);      // Icono del entrenamiento
        TextView textViewNombre = listItem.findViewById(R.id.textViewNombre);  // Nombre del entrenamiento

        // CONFIGURAR LOS DATOS EN LOS COMPONENTES
        imageView.setImageResource(currentEntrenamiento.getIconoResId()); // Establecer el icono
        textViewNombre.setText(currentEntrenamiento.getNombre());         // Establecer el nombre

        // DEVOLVER LA VISTA CONFIGURADA
        // Esta vista se mostrará en el ListView
        return listItem;
    }
}
