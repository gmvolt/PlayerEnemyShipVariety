package entity;

import Enemy.HpEnemyShip;
import Sound_Operator.SoundManager;
import engine.Cooldown;
import engine.Core;
import engine.DrawManager.SpriteType;

import java.awt.*;

/**
 * Implements the part of the Boss.
 */
public class BossParts extends Entity {
	/** Part of the Boss' health point */
	private int hp;

	/** Cooldown between sprite changes. */
	private Cooldown animationCooldown;
	/** Checks if the part of the Boss has been hit by a bullet. */
	private Cooldown bossBActiveSkillCooldown;
	private Cooldown bossBDeActiveSkillCooldown;
	private boolean isDestroyed;
	/** Values of the part of the Boss, in points, when destroyed. */
	private int pointValue;

	private static SoundManager sm;

	/** Speed reduction or increase multiplier (1.0 means normal speed). */
	private double speedMultiplier;
	private double defaultSpeedMultiplier;
	/** Checks if the ship is bombed */
	private boolean isChainExploded;

	/**
	 * Constructor, establishes the part's properties.
	 *
	 * @param positionX
	 *            Initial position of the ship in the X axis.
	 * @param positionY
	 *            Initial position of the ship in the Y axis.
	 * @param spriteType
	 *            Sprite type, image corresponding to the part of the Boss.
	 */
	public BossParts(final int positionX, final int positionY,
					 final SpriteType spriteType, int hp) {

		super(positionX, positionY, 12 * 2, 24 * 2, determineColor(hp));

		this.hp = hp;
		this.spriteType = spriteType;
		this.animationCooldown = Core.getCooldown(500);
		this.bossBActiveSkillCooldown = Core.getVariableCooldown(15000,10000);
		this.bossBDeActiveSkillCooldown = Core.getCooldown(4000);
		this.isDestroyed = false;
		this.speedMultiplier = 1.0; // default 1.0
		this.defaultSpeedMultiplier = 1.0;

		this.pointValue = 1000;
	}

	/**
	 * Getter for the score bonus if this part is destroyed.
	 *
	 * @return Value of the ship.
	 */
	public final int getPointValue() {
		return this.pointValue;
	}

	/**
	 * Moves the part of the Boss in the specified distance.
	 *
	 * @param distanceX
	 *            Distance to move in the X axis.
	 * @param distanceY
	 *            Distance to move in the Y axis.
	 */
	public final void move(final int distanceX, final int distanceY) {
		this.positionX += distanceX * this.getSpeedMultiplier();
		this.positionY += distanceY;
	}

	/**
	 * Updates attributes, mainly used for animation purposes.
	 */
	public final void update() {
		if (this.animationCooldown.checkFinished()) {
			this.animationCooldown.reset();

			switch (this.spriteType) {
				case BossAMiddle1:
					this.spriteType = SpriteType.BossAMiddle2;
					break;
				case BossAMiddle2:
					this.spriteType = SpriteType.BossAMiddle1;
					break;
				case BossALeft1:
					this.spriteType = SpriteType.BossALeft2;
					break;
				case BossALeft2:
					this.spriteType = SpriteType.BossALeft1;
					break;
				case BossARight1:
					this.spriteType = SpriteType.BossARight2;
					break;
				case BossARight2:
					this.spriteType = SpriteType.BossARight1;
					break;
				case BossB1:
					// Check skill cooldown and change sprite type to B3 which is B3.
					if (this.bossBActiveSkillCooldown.checkFinished()) {
						this.spriteType = SpriteType.BossB3;
						bossBDeActiveSkillCooldown.reset();
					}
					else
						this.spriteType = SpriteType.BossB2;
					break;
				case BossB2:
					this.spriteType = SpriteType.BossB1;
					break;
				case BossB3:
					if (this.bossBDeActiveSkillCooldown.checkFinished()) {
						this.spriteType = SpriteType.BossB1;
						bossBActiveSkillCooldown.reset();
					}
					else
						this.spriteType = SpriteType.BossB3;
				default:
					break;
			}
		}
	}

	/**
	 * Determine the color of the part of the Boss according to hp
	 * @param hp
	 * 			The Boss' hp
	 * @return color of Boss
	 */
	public static Color determineColor(int hp) {
		if(hp <= 1) return Color.red;
		else if(hp <= 5) return Color.green;
		else if (hp <= 8) return Color.blue;
		else return Color.white;
	}

	public final void changeColor() {
	}

	public static void hit(BossParts bossParts) {
		int hp = bossParts.getHp();
		hp -= 1;
		bossParts.setHp(hp);

		// Maybe we should add blinking effect here when the Boss get hit.

		if (hp <= 0) {
			bossParts.destroy();
		}else{
			sm = SoundManager.getInstance();
			sm.playES("hit_enemy");
		}

		bossParts.setColor(determineColor(hp));
	}

	/**
	 * Destroys the part of the Boss, causing an explosion.
	 */
	public final void destroy() {
		this.isDestroyed = true;
		sm = SoundManager.getInstance();
		switch (this.spriteType) {
			case BossAMiddle1:
			case BossAMiddle2:
				sm.playES("boss_die");  // 보스 중앙 파괴
				break;
			case BossALeft1:
			case BossALeft2:
			case BossARight1:
			case BossARight2:
				sm.playES("boss_part_destroy");  // 보스 사이드 파괴
				break;
			case BossB1:
			case BossB2:
				sm.playES("boss_die");
			default:
				sm.playES("special_enemy_die");
				break;
		}

		this.spriteType = SpriteType.Explosion;
	}

	/**
	 * Checks if the part of the Boss has been destroyed.
	 *
	 * @return True if the part of the Boss has been destroyed.
	 */
	public final boolean isDestroyed() {
		return this.isDestroyed;
	}

	/**
	 * Getter for the Hp of this part of the Boss.
	 *
	 * @return Hp of the part of the Boss.
	 */
	public final int getHp() {
		return this.hp;
	}

	/**
	 * Setter for the Hp of the part of the Boss.
	 *
	 * @param hp
	 * 			New hp of the part of the Boss.
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}

	/**
	 * Getter for the current speed multiplier.
	 *
	 * @return The current speed multiplier.
	 */
	public double getSpeedMultiplier() {
		return this.speedMultiplier;
	}

	public void setSpeedMultiplier(double speedMultiplier) {
		this.speedMultiplier = speedMultiplier;
	}

	public void resetSpeedMultiplier() {
		this.speedMultiplier = this.defaultSpeedMultiplier;
	}

	/**
	 * Destroys ship, causing a chain explode.
	 */
	public final void chainExplode() { // Added by team Enemy
		destroy();
		setChainExploded(true);
		setHp(0);
	}

	/**
	 * Checks if the ship has been chain exploded.
	 *
	 * @return True if the ship has been chain exploded.
	 */
	public final boolean isChainExploded() {
		return this.isChainExploded;
	}

	/**
	 * Setter for enemy ship's isChainExploded to false.
	 */
	public final void setChainExploded(boolean isChainExploded) {
		this.isChainExploded = isChainExploded;
	}


}
