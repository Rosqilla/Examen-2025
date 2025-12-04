package com.example.entrenamiento;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class NuevoEntrenamientoDialogFragment extends DialogFragment {

    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private EditText editTextDuracion;
    private Spinner spinnerDificultad;
    private OnEntrenamientoAddedListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_nuevo_entrenamiento, null);

        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextDescripcion = view.findViewById(R.id.editTextDescripcion);
        editTextDuracion = view.findViewById(R.id.editTextDuracion);
        spinnerDificultad = view.findViewById(R.id.spinnerDificultad);

        // Configurar Spinner de dificultad
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.dificultades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDificultad.setAdapter(adapter);

        builder.setView(view)
                .setTitle("Añadir Nuevo Entrenamiento")
                .setPositiveButton("Añadir", null)
                .setNegativeButton("Cancelar", (dialog, which) -> dismiss());

        AlertDialog dialog = builder.create();

        // Sobrescribir el botón positivo para validar antes de cerrar
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                if (validarCampos()) {
                    String nombre = editTextNombre.getText().toString().trim();
                    String descripcion = editTextDescripcion.getText().toString().trim();
                    String duracion = editTextDuracion.getText().toString().trim();
                    String dificultad = spinnerDificultad.getSelectedItem().toString();

                    if (listener != null) {
                        listener.onEntrenamientoAdded(nombre, descripcion, duracion, dificultad);
                    }
                    dismiss();
                }
            });
        });

        return dialog;
    }

    private boolean validarCampos() {
        String nombre = editTextNombre.getText().toString().trim();
        String descripcion = editTextDescripcion.getText().toString().trim();
        String duracion = editTextDuracion.getText().toString().trim();

        if (nombre.isEmpty()) {
            editTextNombre.setError("El nombre es obligatorio");
            editTextNombre.requestFocus();
            return false;
        }

        if (descripcion.isEmpty()) {
            editTextDescripcion.setError("La descripción es obligatoria");
            editTextDescripcion.requestFocus();
            return false;
        }

        if (duracion.isEmpty()) {
            editTextDuracion.setError("La duración es obligatoria");
            editTextDuracion.requestFocus();
            return false;
        }

        return true;
    }

    public void setOnEntrenamientoAddedListener(OnEntrenamientoAddedListener listener) {
        this.listener = listener;
    }
}
