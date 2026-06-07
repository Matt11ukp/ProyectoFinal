package edu.unl.cc.ama.domain;

import edu.unl.cc.ama.domain.objects.*;
import edu.unl.cc.ama.domain.objects.ObjectTestPortal;
import edu.unl.cc.ama.view.GamePanel;

import java.util.List;
import java.util.logging.Logger;

public class AssetSetter {

    private static final Logger LOG = Logger.getLogger(AssetSetter.class.getName());

    private final GamePanel   gp;
    private final WorldLoader loader;

    public AssetSetter(GamePanel gp) {
        this.gp     = gp;
        this.loader = new WorldLoader();
    }

    public void setObject() {
        clearArray(gp.obj);
        int objIndex = 0;

        for (WorldEntry entry : loader.load()) {
            Item item = createItem(entry.kind());
            if (item == null) continue;  // no es un Item (puede ser NPC o monstruo)

            if (objIndex >= gp.obj.length) {
                LOG.warning("Límite de objetos alcanzado (" + gp.obj.length + "). Ignorando: " + entry);
                break;
            }

            place(item, entry.col(), entry.row());
            gp.obj[objIndex++] = item;
        }
    }

    public void setNPC() {
        clearArray(gp.npc);
        int npcIndex = 0;

        for (WorldEntry entry : loader.load()) {
            Entity npc = createNPC(entry.kind());
            if (npc == null) continue;

            if (npcIndex >= gp.npc.length) {
                LOG.warning("Límite de NPCs alcanzado (" + gp.npc.length + "). Ignorando: " + entry);
                break;
            }

            place(npc, entry.col(), entry.row());
            gp.npc[npcIndex++] = npc;
        }
    }

    public void setMonster() {
        clearArray(gp.monster);
        int monsterIndex = 0;

        for (WorldEntry entry : loader.load()) {
            Entity monster = createMonster(entry);
            if (monster == null) continue;

            if (monsterIndex >= gp.monster.length) {
                LOG.warning("Límite de monstruos alcanzado (" + gp.monster.length + "). Ignorando: " + entry);
                break;
            }

            place(monster, entry.col(), entry.row());
            gp.monster[monsterIndex++] = monster;
        }
    }

    private Item createItem(String kind) {
        return switch (kind) {
            case "KEY"    -> new ObjectKey();
            case "PORTAL" -> new ObjectPortal();
            case "DOOR"   -> new ObjectDoor();
            case "CHEST"  -> new ObjectChest();
            case "BOOTS"  -> new ObjectBoots();
            case "TEST_PORTAL" -> new ObjectTestPortal();
            default       -> null;
        };
    }

    private Entity createNPC(String kind) {
        return switch (kind) {
            case "NPC_OLD_MAN" -> new NpcOldMan(gp);
            default            -> null;
        };
    }

    private Entity createMonster(WorldEntry entry) {
        return switch (entry.kind()) {
            case "SLIME" -> {
                GreenSlime s = new GreenSlime(gp);
                yield s;
            }
            case "SLIME_BOOTS" -> {
                GreenSlime s = new GreenSlime(gp);
                s.setDropsBoots(true);
                yield s;
            }
            default -> null;
        };
    }

    private void place(Entity entity, int col, int row) {
        entity.setWorldX(col * gp.tileSize);
        entity.setWorldY(row * gp.tileSize);
    }

    private void place(Item item, int col, int row) {
        item.setWorldX(col * gp.tileSize);
        item.setWorldY(row * gp.tileSize);
    }

    private <T> void clearArray(T[] array) {
        for (int i = 0; i < array.length; i++) array[i] = null;
    }
}
