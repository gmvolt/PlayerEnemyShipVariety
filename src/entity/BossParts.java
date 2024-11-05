package entity;

import Enemy.HpEnemyShip;
import Sound_Operator.SoundManager;
import engine.Cooldown;
import engine.Core;
import engine.DrawManager.SpriteType;

import java.awt.*;

/**
 * Implements a Boss's parts.
 */
public class BossPart extends Entity {
	/** EnemyShip's health point */
	private int hp; // Add by team

	/** Cooldown between sprite changes. */
	private Cooldown animationCooldown;
	/** Checks if the ship has been hit by a bullet. */
	private boolean isDestroyed;
	/** Values of the ship, in points, when destroyed. */
	private int pointValue;

	// Sound Operator
	private static SoundManager sm;

	// 쓸지 안 쓸지 모름
	/** Speed reduction or increase multiplier (1.0 means normal speed). */
	private double speedMultiplier;
	private double defaultSpeedMultiplier;

	/**
	 * Constructor, establishes the ship's properties.
	 *
	 * @param positionX
	 *            Initial position of the ship in the X axis.
	 * @param positionY
	 *            Initial position of the ship in the Y axis.
	 * @param spriteType
	 *            Sprite type, image corresponding to the ship.
	 */


	public BossPart(final int positionX, final int positionY,
                    final SpriteType spriteType, int hp, int x, int y) {// Edited by Enemy

		super(positionX, positionY, 12 * 2, 24 * 2, HpEnemyShip.determineColor(hp));

		this.hp = hp;// Add by team Enemy
		this.spriteType = spriteType;
		this.animationCooldown = Core.getCooldown(500);
		this.isDestroyed = false;
		this.speedMultiplier=1.0; // default 1.0
		this.defaultSpeedMultiplier = 1.0;

		this.pointValue = 1000;
	}


	/**
	 * Getter for the score bonus if this ship is destroyed.
	 *
	 * @return Value of the ship.
	 */
	public final int getPointValue() {
		return this.pointValue;
	}

	/**
	 * Moves the ship the specified distance.
	 *
	 * @param distanceX
	 *            Distance to move in the X axis.
	 * @param distanceY
	 *            Distance to move in the Y axis.
	 */
	public final void move(final int distanceX, final int distanceY) {
		this.positionX += distanceX * this.getSpeedMultiplier(); // team Inventory
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
	 * Destroys the ship, causing an explosion.
	 */
	public final void destroy() {
		this.isDestroyed = true;
		// Sound Operator
		sm.playES("special_enemy_die");
	}

	/**
	 * Checks if the ship has been destroyed.
	 *
	 * @return True if the ship has been destroyed.
	 */
	public final boolean isDestroyed() {
		return this.isDestroyed;
	}


	/** Constructor for original EnemyShip that did not have hp.
	 * That enemyShip is moved to a constructor with the hp default of 1*/
	public BossPart(final int positionX, final int positionY,
                    final SpriteType spriteType){
		this(positionX,positionY,spriteType,1,-2,-2);
	}// Edited by Enemy

	/**
	 * Getter for the Hp of this Enemy ship.
	 *
	 * @return Hp of the ship.
	 */
	public final int getHp() {
		return this.hp;
	}// Added by team Enemy

	/**
	 * Setter for the Hp of the Enemy ship.
	 *
	 * @param hp
	 * 			New hp of the Enemy ship.
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}// Added by team Enemy


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
