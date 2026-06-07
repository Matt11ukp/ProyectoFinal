package edu.unl.cc.ama.domain;

/**
 * Prueba de tipo Concentration — stub listo para implementación futura.
 *
 * OBJETIVO 1 — Escalabilidad:
 *   Al extender Test, el motor reconoce esta clase automáticamente.
 *   Para implementarla completamente:
 *     1. onStart()  → inicializar recursos.
 *     2. update()   → lógica de tick.
 *     3. Crear GameState.CONCENTRATION si necesita pantalla propia.
 *     4. Crear su Drawer en view/.
 *   GameLoop, GameResult y ConsoleLogger funcionan sin cambios.
 */
public class Concentration extends Test {

    @Override
    protected void onStart() {
        // TODO: inicializar recursos de la prueba Concentration
    }

    @Override
    public void update() {
        // TODO: lógica de tick de la prueba Concentration
        // Cuando el test termine: result = endTest(); ConsoleLogger.log(result);
    }
}
