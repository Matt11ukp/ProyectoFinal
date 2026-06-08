package edu.unl.cc.ama.domain;

/**
 * Contrato mínimo para leer y escribir la configuración visual del avatar.
 *
 * PRINCIPIO — DIP + Regla de Oro #1 (Dominio ciego):
 *   UserProgressManager necesita guardar y restaurar los índices de
 *   skin/pelo/camisa/ojos del jugador. Antes importaba Avatar (view).
 *   Ahora depende de esta interfaz que vive en domain.
 *
 *   Avatar (view) implementa IAvatarConfig.
 *   UserProgressManager (domain) solo conoce IAvatarConfig.
 *   El dominio nunca vuelve a importar nada de view.
 *
 *   Mismo patrón que IGameLoop del Paso 8:
 *     GameLoop   → IGameLoop  (no conoce GamePanel)
 *     UserProgressManager → IAvatarConfig (no conoce Avatar)
 */
public interface IAvatarConfig {
    int  getActualSkin();   void setActualSkin(int v);
    int  getActualHair();   void setActualHair(int v);
    int  getActualShirt();  void setActualShirt(int v);
    int  getActualEye();    void setActualEye(int v);
    boolean isGender();     void setGender(boolean v);
}
