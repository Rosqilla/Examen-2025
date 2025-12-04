package com.example.entrenamiento;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * DIÁLOGO PARA AÑADIR UN NUEVO ENTRENAMIENTO
 * Se abre cuando el usuario presiona el botón "Añadir" en la toolbar
 *
 * Este diálogo tiene 2 campos:
 * - Tipo: El nombre del entrenamiento (ej: "Pilates")
 * - Ejercicios: La descripción de los ejercicios (ej: "Plancha 1min, Abdominales")
 */
public class ActionDialogFragment extends DialogFragment {

    /**
     * INTERFAZ PARA COMUNICARSE CON LA ACTIVITY
     * Cuando el usuario guarda, se notifica a MainActivity con los datos ingresados
     */
    public interface OnActionSaveListener {
        void onActionSave(String tipo, String ejercicios);
    }

    // CAMPOS DE ENTRADA DEL DIÁLOGO
    private EditText editTextTipo;        // Campo para el nombre/tipo del entrenamiento
    private EditText editTextEjercicios;  // Campo para los ejercicios (multilínea)

    // Listener para notificar cuando se guarde
    private OnActionSaveListener listener;

    /**
     * CREAR EL DIÁLOGO
     * Este método se ejecuta cuando se muestra el diálogo
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // AlertDialog.Builder = constructor de diálogos
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // LayoutInflater = convierte XML en objetos Java
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflar el layout del diálogo
        View view = inflater.inflate(R.layout.dialog_action, null);

        // CONECTAR LOS CAMPOS DE ENTRADA CON LAS VARIABLES
        editTextTipo = view.findViewById(R.id.editTextTipo);
        editTextEjercicios = view.findViewById(R.id.editTextEjercicios);

        // CONFIGURAR EL DIÁLOGO
        builder.setView(view)
                .setTitle("Añadir Entrenamiento")           // Título del diálogo
                .setPositiveButton("Save", null)            // Botón guardar (null = configurar después)
                .setNegativeButton("Cancel", (dialog, which) -> dismiss()); // Botón cancelar (cierra el diálogo)

        // Crear el diálogo
        AlertDialog dialog = builder.create();

        // CONFIGURAR EL BOTÓN "SAVE" DESPUÉS DE CREAR EL DIÁLOGO
        // Se hace así para poder validar antes de cerrar
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                // VALIDAR LOS CAMPOS
                if (validarCampos()) {
                    // Si todo está bien, obtener los datos
                    String tipo = editTextTipo.getText().toString().trim();
                    String ejercicios = editTextEjercicios.getText().toString().trim();

                    // Notificar a la Activity con los datos
                    if (listener != null) {
                        listener.onActionSave(tipo, ejercicios);
                    }

                    // Cerrar el diálogo
                    dismiss();
                }
                // Si la validación falla, el diálogo permanece abierto
            });
        });

        return dialog;
    }

    /**
     * VALIDAR QUE LOS CAMPOS OBLIGATORIOS ESTÉN LLENOS
     *
     * @return true si todo está correcto, false si falta algo
     */
    private boolean validarCampos() {
        String tipo = editTextTipo.getText().toString().trim();
        String ejercicios = editTextEjercicios.getText().toString().trim();

        // Validar campo TIPO
        if (tipo.isEmpty()) {
            editTextTipo.setError("El tipo es obligatorio");      // Mostrar mensaje de error
            editTextTipo.requestFocus();                          // Poner el cursor en este campo
            return false;
        }

        // Validar campo EJERCICIOS
        if (ejercicios.isEmpty()) {
            editTextEjercicios.setError("Los ejercicios son obligatorios");
            editTextEjercicios.requestFocus();
            return false;
        }

        return true; // Todo correcto
    }

    /**
     * ESTABLECER EL LISTENER PARA RECIBIR LOS DATOS
     * MainActivity llama a este método antes de mostrar el diálogo
     */
    public void setOnActionSaveListener(OnActionSaveListener listener) {
        this.listener = listener;
    }
}
