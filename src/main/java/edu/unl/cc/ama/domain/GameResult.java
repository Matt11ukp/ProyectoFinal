package edu.unl.cc.ama.domain;

/**
 * Value Object inmutable que transporta los resultados de una prueba.
 *
 * PRINCIPIO — SRP:
 *   GameResult solo almacena datos. La PRESENTACIÓN de esos datos
 *   es responsabilidad de ConsoleLogger (Objetivo 3).
 *   showReport() delega a ConsoleLogger para no mezclar responsabilidades.
 */
public class GameResult {

    private final int    successes;
    private final int    mistakes;
    private final double time;

    public GameResult(int successes, int mistakes, double time) {
        this.successes = successes;
        this.mistakes  = mistakes;
        this.time      = time;
    }

    /**
     * Imprime el reporte en consola delegando a ConsoleLogger.
     * GameResult no sabe CÓMO imprimir; solo sabe QUÉ datos tiene.
     */
    public void showReport() {
        ConsoleLogger.log(this);
    }

    public int    getSuccesses() { return successes; }
    public int    getMistakes()  { return mistakes; }
    public double getTime()      { return time; }
}
