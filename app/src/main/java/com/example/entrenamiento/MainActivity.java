package com.example.entrenamiento;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

/**
 * ACTIVITY PRINCIPAL
 * Gestiona la interfaz principal de la app y el cambio entre orientaciones (portrait/landscape)
 */
public class MainActivity extends AppCompatActivity implements
        ListaEntrenamientosFragment.OnEntrenamientoSeleccionadoListener,
        ActionDialogFragment.OnActionSaveListener {

    // Variable para saber si estamos en modo landscape (2 paneles) o portrait (1 panel)
    private boolean isDualPane;

    // ID del entrenamiento que el usuario está viendo actualmente
    // static para que persista durante rotaciones de pantalla
    private static int entrenamientoSeleccionadoId = 1; // Por defecto el primero (Cardio)

    /**
     * MÉTODO QUE SE EJECUTA AL CREAR LA ACTIVITY
     * Se ejecuta cada vez que se inicia la app o cuando rotas el dispositivo
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Cargar el layout correspondiente (portrait o landscape según orientación)
        setContentView(R.layout.activity_main);

        // CONFIGURAR TOOLBAR (barra superior con el botón "Añadir")
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        // DETECTAR SI ESTAMOS EN LANDSCAPE O PORTRAIT
        // Si existe fragment_detalle = landscape (2 paneles)
        // Si no existe = portrait (1 panel)
        isDualPane = findViewById(R.id.fragment_detalle) != null;

        // RESTAURAR DATOS GUARDADOS (si viene de una rotación)
        // savedInstanceState contiene datos guardados antes de rotar
        if (savedInstanceState != null) {
            entrenamientoSeleccionadoId = savedInstanceState.getInt("entrenamientoSeleccionadoId", 1);
        }

        // CARGAR LOS FRAGMENTS SEGÚN LA ORIENTACIÓN
        configurarFragments();
    }

    /**
     * GUARDAR DATOS ANTES DE QUE SE DESTRUYA LA ACTIVITY
     * Esto ocurre cuando rotas el dispositivo
     * Los datos se recuperan en onCreate() con savedInstanceState
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Guardar qué entrenamiento estaba viendo el usuario
        outState.putInt("entrenamientoSeleccionadoId", entrenamientoSeleccionadoId);
    }

    /**
     * CONFIGURAR FRAGMENTS SEGÚN LA ORIENTACIÓN
     * Este es el método MÁS IMPORTANTE para evitar pantallas en blanco
     */
    private void configurarFragments() {
        // FragmentManager = gestor de fragments
        FragmentManager fm = getSupportFragmentManager();

        if (isDualPane) {
            // ========== MODO LANDSCAPE (2 PANELES) ==========

            // PASO 1: Limpiar fragments del modo portrait si existen
            // Esto previene que queden fragments "fantasma" de la orientación anterior
            if (fm.findFragmentById(R.id.fragment_container) != null) {
                fm.beginTransaction()
                    .remove(fm.findFragmentById(R.id.fragment_container))
                    .commitNow(); // commitNow() = ejecuta INMEDIATAMENTE (sin esto = pantalla en blanco)
            }

            // PASO 2: Cargar LISTA en el panel izquierdo (30% de la pantalla)
            ListaEntrenamientosFragment listaFragment =
                (ListaEntrenamientosFragment) fm.findFragmentById(R.id.fragment_lista);

            // Si no existe, crearlo
            if (listaFragment == null) {
                listaFragment = new ListaEntrenamientosFragment();
                fm.beginTransaction()
                    .replace(R.id.fragment_lista, listaFragment, "TAG_LISTA")
                    .commitNow(); // commitNow() es CRUCIAL para evitar pantalla en blanco
            }

            // PASO 3: Cargar DETALLES en el panel derecho (70% de la pantalla)
            DetalleEntrenamientoFragment detalleFragment =
                (DetalleEntrenamientoFragment) fm.findFragmentById(R.id.fragment_detalle);

            // Si no existe, crearlo con el entrenamiento seleccionado
            if (detalleFragment == null) {
                // Obtener el entrenamiento que estaba viendo el usuario
                Entrenamiento entrenamiento = ListaEntrenamientosFragment.getEntrenamientoById(entrenamientoSeleccionadoId);

                // Si no existe (primera vez), cargar el primero (Cardio)
                if (entrenamiento == null) {
                    entrenamiento = ListaEntrenamientosFragment.getPrimerEntrenamiento();
                    if (entrenamiento != null) {
                        entrenamientoSeleccionadoId = entrenamiento.getId();
                    }
                }

                // Crear fragment de detalles
                if (entrenamiento != null) {
                    detalleFragment = DetalleEntrenamientoFragment.newInstance(entrenamiento);
                    fm.beginTransaction()
                        .replace(R.id.fragment_detalle, detalleFragment, "TAG_DETALLE")
                        .commitNow();
                }
            }
        } else {
            // ========== MODO PORTRAIT (1 PANEL) ==========

            // PASO 1: Limpiar fragments del modo landscape si existen
            if (fm.findFragmentById(R.id.fragment_lista) != null) {
                fm.beginTransaction()
                    .remove(fm.findFragmentById(R.id.fragment_lista))
                    .commitNow();
            }
            if (fm.findFragmentById(R.id.fragment_detalle) != null) {
                fm.beginTransaction()
                    .remove(fm.findFragmentById(R.id.fragment_detalle))
                    .commitNow();
            }

            // PASO 2: Limpiar el historial de navegación (BackStack)
            // Esto hace que al volver a portrait siempre se vea la lista, no los detalles
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            // PASO 3: Cargar solo la LISTA en pantalla completa
            ListaEntrenamientosFragment listaFragment =
                (ListaEntrenamientosFragment) fm.findFragmentById(R.id.fragment_container);

            if (listaFragment == null) {
                listaFragment = new ListaEntrenamientosFragment();
                fm.beginTransaction()
                    .replace(R.id.fragment_container, listaFragment, "TAG_LISTA")
                    .commitNow();
            }
        }
    }

    /**
     * SE EJECUTA CUANDO EL USUARIO HACE CLICK EN UN ENTRENAMIENTO DE LA LISTA
     * Implementa la interfaz OnEntrenamientoSeleccionadoListener
     */
    @Override
    public void onEntrenamientoSeleccionado(Entrenamiento entrenamiento) {
        // Guardar el ID del entrenamiento seleccionado
        entrenamientoSeleccionadoId = entrenamiento.getId();

        if (isDualPane) {
            // LANDSCAPE: Solo actualizar el panel derecho (detalles)
            // La lista permanece visible en el panel izquierdo
            DetalleEntrenamientoFragment detalleFragment =
                DetalleEntrenamientoFragment.newInstance(entrenamiento);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_detalle, detalleFragment, "TAG_DETALLE")
                    .commit();
        } else {
            // PORTRAIT: Reemplazar toda la pantalla con los detalles
            // La lista se oculta (pero se puede volver con botón atrás)
            DetalleEntrenamientoFragment detalleFragment =
                    DetalleEntrenamientoFragment.newInstance(entrenamiento);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detalleFragment)
                    .addToBackStack(null) // Añadir al historial para poder volver con botón atrás
                    .commit();
        }
    }

    /**
     * CREAR EL MENÚ DE LA TOOLBAR (botón "Añadir")
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Cargar el archivo de menú (main_menu.xml)
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * SE EJECUTA CUANDO EL USUARIO PRESIONA EL BOTÓN "AÑADIR" EN LA TOOLBAR
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_button) {
            mostrarActionDialog(); // Abrir el diálogo para añadir entrenamiento
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * MOSTRAR EL DIÁLOGO PARA AÑADIR NUEVO ENTRENAMIENTO
     */
    private void mostrarActionDialog() {
        ActionDialogFragment dialog = new ActionDialogFragment();
        dialog.setOnActionSaveListener(this); // Esta activity escuchará cuando se guarde
        dialog.show(getSupportFragmentManager(), "ActionDialog");
    }

    /**
     * SE EJECUTA CUANDO EL USUARIO GUARDA UN NUEVO ENTRENAMIENTO EN EL DIÁLOGO
     * Implementa la interfaz OnActionSaveListener
     */
    @Override
    public void onActionSave(String tipo, String ejercicios) {
        // Añadir el nuevo entrenamiento a la lista
        // Duración y dificultad se asignan por defecto
        actualizarListaEntrenamientos(tipo, ejercicios, "45 minutos", "Media");
    }

    /**
     * AÑADIR NUEVO ENTRENAMIENTO A LA LISTA
     */
    private void actualizarListaEntrenamientos(String nombre, String descripcion, String duracion, String dificultad) {
        // Buscar el fragment de lista
        ListaEntrenamientosFragment listaFragment =
                (ListaEntrenamientosFragment) getSupportFragmentManager().findFragmentByTag("TAG_LISTA");

        // Si existe, añadir el entrenamiento
        if (listaFragment != null) {
            listaFragment.agregarEntrenamiento(nombre, descripcion, duracion, dificultad);
        }
    }
}