package edu.unl.cc.ama.domain;

public final class ConsoleLogger {

    private ConsoleLogger() {}

    public static void log(GameResult result) {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║         REPORTE DE PRUEBA  —  AMA        ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf( "║  ✔  Aciertos    : %-22d║%n", result.getSuccesses());
        System.out.printf( "║  ✘  Desaciertos : %-22d║%n", result.getMistakes());
        System.out.printf( "║  ⏱  Tiempo      : %-18.2f seg  ║%n", result.getTime());
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println();
    }

    public static void log(String testName, GameResult result) {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.printf( "║  %-40s║%n", testName.toUpperCase());
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf( "║  ✔  Aciertos    : %-22d║%n", result.getSuccesses());
        System.out.printf( "║  ✘  Desaciertos : %-22d║%n", result.getMistakes());
        System.out.printf( "║  ⏱  Tiempo      : %-18.2f seg  ║%n", result.getTime());
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println();
    }
}
