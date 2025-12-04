package com.example.entrenamiento;

/**
 * CLASE MODELO QUE REPRESENTA UN ENTRENAMIENTO
 *
 * Esta clase almacena toda la información de un entrenamiento:
 * - ID único
 * - Nombre/tipo (ej: "Cardio", "Fuerza")
 * - Descripción con los ejercicios
 * - ID del recurso del icono
 * - Duración (ej: "45 minutos")
 * - Dificultad (ej: "Alta", "Media", "Baja")
 */
public class Entrenamiento {
    // ATRIBUTOS PRIVADOS (solo se pueden acceder mediante getters/setters)
    private int id;                 // ID único del entrenamiento (1, 2, 3, 4, 5...)
    private String nombre;          // Nombre/tipo del entrenamiento
    private String descripcion;     // Lista de ejercicios
    private int iconoResId;         // ID del recurso del icono (ej: android.R.drawable.ic_menu_compass)
    private String duracion;        // Duración del entrenamiento
    private String dificultad;      // Nivel de dificultad

    /**
     * CONSTRUCTOR
     * Se usa para crear un nuevo objeto Entrenamiento con todos sus datos
     *
     * Ejemplo de uso:
     * new Entrenamiento(1, "Cardio", "Correr 30min", R.drawable.icon, "45 minutos", "Alta")
     */
    public Entrenamiento(int id, String nombre, String descripcion, int iconoResId, String duracion, String dificultad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.iconoResId = iconoResId;
        this.duracion = duracion;
        this.dificultad = dificultad;
    }

    // ========== GETTERS (para LEER los valores) ==========

    /**
     * Obtener el ID del entrenamiento
     * @return ID del entrenamiento
     */
    public int getId() {
        return id;
    }

    /**
     * Obtener el nombre del entrenamiento
     * @return Nombre del entrenamiento (ej: "Cardio")
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtener la descripción (ejercicios)
     * @return Descripción con los ejercicios
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtener el ID del recurso del icono
     * @return ID del icono (ej: android.R.drawable.ic_menu_compass)
     */
    public int getIconoResId() {
        return iconoResId;
    }

    /**
     * Obtener la duración
     * @return Duración del entrenamiento (ej: "45 minutos")
     */
    public String getDuracion() {
        return duracion;
    }

    /**
     * Obtener la dificultad
     * @return Nivel de dificultad (ej: "Alta", "Media", "Baja")
     */
    public String getDificultad() {
        return dificultad;
    }

    // ========== SETTERS (para MODIFICAR los valores) ==========

    /**
     * Cambiar el ID del entrenamiento
     * @param id Nuevo ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Cambiar el nombre del entrenamiento
     * @param nombre Nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Cambiar la descripción
     * @param descripcion Nueva descripción
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Cambiar el icono
     * @param iconoResId Nuevo ID del icono
     */
    public void setIconoResId(int iconoResId) {
        this.iconoResId = iconoResId;
    }

    /**
     * Cambiar la duración
     * @param duracion Nueva duración
     */
    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    /**
     * Cambiar la dificultad
     * @param dificultad Nueva dificultad
     */
    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }
}
