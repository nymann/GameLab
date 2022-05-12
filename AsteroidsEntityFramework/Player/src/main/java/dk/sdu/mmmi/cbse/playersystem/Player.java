package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Player extends Entity {
    private float cooldown = 0;

    public Player() {

    }

    public boolean canShoot(float gameTime) {
        cooldown -= gameTime;
         return cooldown <= 0;
    }

    public void resetCooldown() {
        this.cooldown = 0.5f;
    }
}
