package edu.unl.cc.ama.domain.objects;

/**
 * Identifica el tipo de cada item del mundo.
 * TEST_PORTAL: activa un minijuego al ser recogido por el jugador
 * en el Mundo Hub (Objetivo 4 — flujo de navegación modular).
 */
public enum Type {
    BOOTS(0),
    CHEST(1),
    DOOR(2),
    DOOR_OPEN(3),
    HEART(4),
    HALF_HEART(5),
    BLANK_HEART(6),
    KEY(7),
    PORTAL(8),
    TEST_PORTAL(9);   // ← NUEVO: portal de entrada al minijuego

    private final int index;
    Type(int index) { this.index = index; }
    public int getIndex()        { return index; }
}
