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
	private boolean isDestroyed;
	/** Values of the part of the Boss, in points, when destroyed. */
	private int pointValue;

	private static SoundManager sm;

	/** Speed reduction or increase multiplier (1.0 means normal speed). */
	private double speedMultiplier;
	private double defaultSpeedMultiplier;

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
				case EnemyShipA1:
					this.spriteType = SpriteType.EnemyShipA2;
					break;
				case EnemyShipA2:
					this.spriteType = SpriteType.EnemyShipA1;
					break;
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
		return switch (hp) {
			case 20 -> Color.BLUE;
			case 30 -> Color.RED; // 추가 보스의 경우를 위한 임시 체력과 색깔
			case 40 -> Color.GREEN; // 위와 동일
			default -> Color.WHITE; // 위와 동일
		};
	}

	public final void changeColor() {
	}

	public final void hit() {
		this.hp -= 1;

		// Maybe we should add blinking effect here when the Boss get hit.

		if (hp <= 0) {
			destroy();
		}
	}

	/**
	 * Destroys the part of the Boss, causing an explosion.
	 */
	public final void destroy() {
		this.isDestroyed = true;
		sm.playES("special_enemy_die");
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
}
