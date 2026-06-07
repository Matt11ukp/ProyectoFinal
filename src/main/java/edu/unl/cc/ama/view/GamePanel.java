package edu.unl.cc.ama.view;

import edu.unl.cc.ama.domain.*;
import edu.unl.cc.ama.domain.objects.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Orquestador principal del motor 2D y mediador de todos los sistemas.
 *
 * OBJETIVO 4 — Flujo modular de navegación:
 *   • launchVisualTest(): lanza el minijuego desde cualquier estado
 *     (TITLE o PLAY). Memoriza previousGameState para volver.
 *   • returnFromVisualTest(): devuelve al estado exacto de origen.
 *   • updateVisual(): tick del minijuego; detecta fin y regresa solo.
 *
 * OBJETIVO 1 — Escalabilidad para futuras pruebas:
 *   El campo "private Test activeTest" es el punto de extensión.
 *   Para añadir PruebaMatematicas:
 *     1. PruebaMatematicas extends Test  (en domain/)
 *     2. Llamar launchTest(new PruebaMatematicas(), GameState.MATH)
 *     3. El resto del motor (loop, render, resultado) no cambia.
 *
 * CORRECCIONES RESPECTO AL ORIGINAL:
 *   • updateLoading() ya no contiene el bloque VISUAL huérfano.
 *   • paintComponent() ya no duplica el draw del minijuego.
 *   • isMenuState() incluye VISUAL → evita dibujar el mundo debajo.
 *   • Mouse movido de domain a view (violación MVC corregida).
 */
public class GamePanel extends JPanel implements IGameLoop {

    // ── CONFIGURACIÓN ─────────────────────────────────────────────────────────
    public final int originalTileSize = 16;
    public final int scale            = 3;
    public final int tileSize         = originalTileSize * scale;
    public final int maxScreenCol     = 30;
    public final int maxScreenRow     = 16;
    public final int screenWidth      = tileSize * maxScreenCol;
    public final int screenHeight     = tileSize * maxScreenRow;
    public final int maxWorldCol      = 50;
    public final int maxWorldRow      = 50;
    public final int worldWidth       = tileSize * maxWorldCol;
    public final int worldHeight      = tileSize * maxWorldRow;

    // ── SISTEMAS (orden de instanciación — Regla de Oro #5) ──────────────────
    public  final TileManager    tileM    = new TileManager(this);    // 1
    public  final Key            keyH     = new Key(this);            // 2
    public  final CollisionCheck cChecker = new CollisionCheck(this); // 3
    public  final AssetSetter    sett     = new AssetSetter(this);    // 4
    public  final Avatar         skins    = new Avatar(this);         // 5
    public  final Ui             ui       = new Ui(this);             // 6
    public  final EventHandler   eHandler = new EventHandler(this);   // 7
    public  final Player         player   = new Player(this, keyH);   // 8
    public  final EntityRenderer renderer = new EntityRenderer(this); // 9

    // ── ENTIDADES ─────────────────────────────────────────────────────────────
    public Item[]   obj     = new Item[50];
    public Entity[] npc     = new Entity[10];
    public Entity[] monster = new Entity[10];

    public  final LootSystem lootSystem = new LootSystem(this);       // 10
    private final GameLoop   loop       = new GameLoop(this);         // 11

    // ── AUDIO ─────────────────────────────────────────────────────────────────
    private final Sound music      = new Sound();
    private final Sound soundEfect = new Sound();

    // ── MINIJUEGO ACTIVO ──────────────────────────────────────────────────────
    // Punto de extensión para nuevas pruebas (Objetivo 1).
    // launchTest(new PruebaMatematicas(), GameState.MATH) y el motor
    // llama activeTest.startTest() / update() / endTest() automáticamente.
    private Visual      visualTest;
    private GameState   previousGameState = GameState.TITLE;

    // ── SISTEMA DE USUARIOS ───────────────────────────────────────────────────
    private User                      currentUser;
    private int                       loadingProgress = 0;
    private boolean                   usersLoaded     = false;
    private UserProgressManager       userProgressManager;
    private final UserRepository         userRepository         = new UserRepository();
    private final UserRegistrationForm   userRegistrationForm   = new UserRegistrationForm();
    private final UserRegistrationController userRegistrationController =
                                        new UserRegistrationController(userRegistrationForm);
    private final UserSelectionMenu      userSelectionMenu      = new UserSelectionMenu();
    private final UserSelectionController userSelectionController =
                                        new UserSelectionController(userSelectionMenu);

    // ── ESTADO ────────────────────────────────────────────────────────────────
    public GameState gameState = GameState.LOADING;

    // ── INPUT ─────────────────────────────────────────────────────────────────
    // Mouse en view (corregido — antes estaba en domain violando MVC)
    private final Mouse mouseH = new Mouse(this);

    // ── LISTAS DE RENDERIZADO ─────────────────────────────────────────────────
    private final ArrayList<Entity> entityList = new ArrayList<>();
    private final ArrayList<Item>   itemList   = new ArrayList<>();

    // =========================================================================
    // CONSTRUCTOR
    // =========================================================================
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        this.addMouseMotionListener(mouseH);
        this.setFocusable(true);

        // Creamos el objeto sin iniciar el test (startTest se llama en launchVisualTest)
        visualTest = new Visual();
    }

    // =========================================================================
    // SETUP
    // =========================================================================
    public void setUpGame() {
        player.getPlayerImage();
        player.getPlayerAttackImage();
        sett.setObject();
        sett.setNPC();
        sett.setMonster();
        registerLootSystem();

        userProgressManager = new UserProgressManager(
                userRepository,
                userSelectionMenu.getUsers(),
                skins,
                player);

        gameState = GameState.LOADING;
        playMusic(SoundName.MENU);
    }

    private void registerLootSystem() {
        for (Entity m : monster) {
            if (m != null) m.setDeathListener(lootSystem);
        }
    }

    public void startGameThread() { loop.start(); }

    // =========================================================================
    // LANZAMIENTO Y RETORNO DE MINIJUEGOS — Objetivo 4
    // =========================================================================

    /**
     * Lanza el minijuego de linterna desde cualquier estado del motor.
     * Funciona tanto desde el menú TITLE como desde el Mundo Hub (PLAY).
     *
     * Uso desde Key.java (menú):  gp.launchVisualTest()
     * Uso desde Player (mundo):   gp.launchVisualTest()  via TEST_PORTAL
     */
    public void launchVisualTest() {
        previousGameState = gameState;      // memoriza dónde estaba el jugador
        visualTest.startTest();             // llama Test.startTest() → onStart()
        gameState = GameState.VISUAL;
        stopMusic();
        playMusic(SoundName.GAME);
    }

    /**
     * Devuelve al estado exacto desde donde se lanzó el minijuego.
     * También puede usarse con ESC para salida anticipada.
     */
    public void returnFromVisualTest() {
        gameState = previousGameState;
        stopMusic();
        // Restaurar música según estado de retorno
        if (previousGameState == GameState.TITLE ||
            previousGameState == GameState.USER_SELECTION) {
            playMusic(SoundName.MENU);
        } else {
            playMusic(SoundName.GAME); // PLAY u otros estados de mundo
        }
        ui.commandNumber = 0;
    }

    // =========================================================================
    // UPDATE
    // =========================================================================
    @Override
    public void update() {
        switch (gameState) {
            case LOADING        -> updateLoading();
            case USER_SELECTION -> { /* navegación manejada en Key.java */ }
            case REGISTER       -> { /* input manejado en Key.java */      }
            case VISUAL         -> updateVisual();    // ← CORREGIDO: propio case
            case PLAY           -> updatePlay();
            case WIN_SHOW       -> updateWinShow();
            case LOST_SHOW      -> updateLostShow();
            default             -> { /* TITLE, PAUSE, WIN, LOST, skins */ }
        }
    }

    private void updateLoading() {
        // CORRECCIÓN: ya no contiene lógica de VISUAL (estado distinto)
        if (!usersLoaded) {
            userSelectionMenu.setUsers(userRepository.loadUsers());
            usersLoaded = true;
        }
        loadingProgress = Math.min(loadingProgress + 1, 100);
        if (loadingProgress >= 100) {
            gameState = GameState.USER_SELECTION;
        }
    }

    /**
     * Tick del minijuego de linterna.
     * Visual.update() detecta el fin y llama ConsoleLogger internamente.
     */
    private void updateVisual() {
        visualTest.update();
        // Retorno automático 2 segundos después de completar el test
        if (visualTest.isTestCompleted() && visualTest.shouldReturnToGame(2000)) {
            returnFromVisualTest();
        }
    }

    private void updatePlay() {
        eHandler.checkEvent();
        player.update();
        for (Entity e : npc) { if (e != null) e.update(); }
        updateMonsters();
    }

    private void updateWinShow() {
        player.incrementWinCounter();
        if (player.getWinCounter() > 280) {
            gameState = GameState.WIN;
            player.setWinCounter(0);
        }
    }

    private void updateLostShow() {
        player.incrementLostCounter();
        if (player.getLostCounter() > 200) {
            player.setInvincible(false);
            gameState = GameState.LOST;
            playMusic(SoundName.DEATH);
            player.setLostCounter(0);
        }
    }

    private void updateMonsters() {
        for (int i = 0; i < monster.length; i++) {
            if (monster[i] == null) continue;
            if (monster[i].isAlive() && !monster[i].isDying()) monster[i].update();
            if (!monster[i].isAlive()) monster[i] = null;
        }
    }

    // =========================================================================
    // RETRY
    // =========================================================================
    public void retry() {
        player.setDefaultValues();
        player.getPlayerImage();
        sett.setObject();
        sett.setNPC();
        sett.setMonster();
        registerLootSystem();
        applyCurrentUserProgress();
        gameState = GameState.PLAY;
    }

    // =========================================================================
    // RENDERIZADO — Algoritmo del Pintor
    // =========================================================================
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        long drawStart = 0;
        if (keyH.checkDrawTime) drawStart = System.nanoTime();

        renderFrame(g2); // gestiona TODOS los estados, incluyendo VISUAL

        if (keyH.checkDrawTime) {
            long passed = System.nanoTime() - drawStart;
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 12f));
            g2.drawString("Draw: " + passed + "ns", 10, 400);
        }
        // CORREGIDO: eliminado el "else if (VISUAL)" duplicado que estaba aquí
        g2.dispose();
    }

    private void renderFrame(Graphics2D g2) {
        // CORRECCIÓN: VISUAL añadido a isMenuState() → Ui.draw() lo maneja
        // correctamente con VisualDrawer sin dibujar el mundo debajo
        if (isMenuState()) {
            ui.draw(g2);
            return;
        }
        // Estado PLAY, PAUSE, WIN, LOST, etc. → dibujar mundo
        tileM.draw(g2);
        collectEntities();
        entityList.sort(Comparator.comparingInt(Entity::getWorldY));
        for (Entity e : entityList) {
            if (e instanceof Player p) renderer.draw(p, g2);
            else                        renderer.draw(e, g2);
        }
        entityList.clear();
        ui.draw(g2);
        collectItems();
        itemList.sort(Comparator.comparingInt(Item::getWorldY));
        for (Item item : itemList) drawItem(item, g2);
        itemList.clear();
    }

    /**
     * CORREGIDO: VISUAL incluido → el motor no dibuja el mundo debajo del minijuego.
     * Ui.draw() enruta correctamente a VisualDrawer cuando gameState == VISUAL.
     */
    private boolean isMenuState() {
        return switch (gameState) {
            case TITLE, SKIN_SELECTION, SKIN_HAIR_SELECTION,
                 SKIN_SHIRT_SELECTION, SKIN_EYES_SELECTION,
                 LOADING, USER_SELECTION, REGISTER, VISUAL -> true;
            default -> false;
        };
    }

    private void collectEntities() {
        entityList.add(player);
        for (Entity e : npc)     { if (e != null) entityList.add(e); }
        for (Entity e : monster) { if (e != null) entityList.add(e); }
    }

    private void collectItems() {
        for (Item item : obj) { if (item != null) itemList.add(item); }
    }

    private void drawItem(Item item, Graphics2D g2) {
        int sx = computeScreenX(item.getWorldX());
        int sy = computeScreenY(item.getWorldY());
        BufferedImage img = ImageGetter.getObjects()[item.getType().getIndex()];
        if (img != null) g2.drawImage(img, sx, sy, null);
    }

    // =========================================================================
    // PROYECCIÓN MUNDO → PANTALLA
    // =========================================================================
    public int computeScreenX(int worldX) {
        int x = worldX - player.getWorldX() + player.getScreenX();
        if (player.getScreenX() > player.getWorldX()) x = worldX;
        if (screenWidth - player.getScreenX() > worldWidth - player.getWorldX())
            x = screenWidth - (worldWidth - worldX);
        return x;
    }

    public int computeScreenY(int worldY) {
        int y = worldY - player.getWorldY() + player.getScreenY();
        if (player.getScreenY() > player.getWorldY()) y = worldY;
        if (screenHeight - player.getScreenY() > worldHeight - player.getWorldY())
            y = screenHeight - (worldHeight - worldY);
        return y;
    }

    // =========================================================================
    // GETTERS DE SISTEMAS (para Key.java y otros colaboradores)
    // =========================================================================
    public Visual                    getVisualTest()                   { return visualTest; }
    public User                      getCurrentUser()                  { return currentUser; }
    public void                      setCurrentUser(User user)         { this.currentUser = user; }
    public void                      updateCurrentUserProgress()       { userProgressManager.saveProgress(currentUser); }
    public void                      applyCurrentUserProgress()        { userProgressManager.applyProgress(currentUser); }
    public UserSelectionMenu         getUserSelectionMenu()            { return userSelectionMenu; }
    public UserSelectionController   getUserSelectionController()      { return userSelectionController; }
    public UserRegistrationForm      getUserRegistrationForm()         { return userRegistrationForm; }
    public UserRegistrationController getUserRegistrationController()  { return userRegistrationController; }
    public int                       getLoadingProgress()              { return loadingProgress; }
    public void                      setLoadingProgress(int v)         { this.loadingProgress = v; }

    public void addUserToSelection(User user) {
        userSelectionMenu.addUser(user);
        userProgressManager.saveProgress(user);
    }
    public void selectLastCreatedUser() { userSelectionMenu.selectLastUser(); }
    public void deleteSelectedUser() {
        userSelectionMenu.removeSelectedUser();
        userRepository.saveUsers(userSelectionMenu.getUsers());
        currentUser = null;
    }

    // =========================================================================
    // AUDIO
    // =========================================================================
    public void playMusic(SoundName name) { music.setFile(name); music.play(); music.loop(); }
    public void stopMusic()               { music.stop(); }
    public void playSE(SoundName name)    { soundEfect.setFile(name); soundEfect.play(); }
}
