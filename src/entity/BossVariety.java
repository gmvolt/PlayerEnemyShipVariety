package entity;

import engine.DrawManager.SpriteType;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Represents a variety of bosses with specific configurations.
 */
public class BossVariety {
    private final String name;
    private final List<SpriteType> spriteTypes;
    private final int healthPerPart;

    private static final Map<String, BossVariety> bossVarieties = new HashMap<>();

    /**
     * Private constructor for BossVariety.
     *
     * @param name Name of the boss variety.
     * @param spriteTypes List of sprite types used for the boss.
     * @param healthPerPart Health value assigned to each boss part.
     */
    private BossVariety(String name, List<SpriteType> spriteTypes, int healthPerPart) {
        this.name = name;
        this.spriteTypes = spriteTypes;
        this.healthPerPart = healthPerPart;
    }

    static {
        bossVarieties.put("Crab", new BossVariety(
                "Crab",
                List.of(SpriteType.BossALeft1, SpriteType.BossAMiddle1, SpriteType.BossARight1),
                8
        ));

        bossVarieties.put("Turtle", new BossVariety(
                "Turtle",
                List.of(SpriteType.BossALeft1, SpriteType.BossAMiddle1, SpriteType.BossARight1),
                5
        ));

        bossVarieties.put("DefaultBoss", new BossVariety(
                "DefaultBoss",
                List.of(SpriteType.BossALeft1, SpriteType.BossAMiddle1, SpriteType.BossARight1),
                6
        ));
    }

    /**
     *
     * @param name The name of the boss variety.
     * @return The BossVariety instance corresponding to the name.
     */
    public static BossVariety getBossVariety(String name) {
        return bossVarieties.getOrDefault(name, bossVarieties.get("DefaultBoss"));
    }

    public String getName() {
        return name;
    }

    public List<SpriteType> getSpriteTypes() {
        return spriteTypes;
    }

    public int getHealthPerPart() {
        return healthPerPart;
    }
}
