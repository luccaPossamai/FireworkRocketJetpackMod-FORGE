package net.lpcamors.firework_jetpack.capability.firework_ability;

public class FireworkAbilityCapabilty {

    public static final int TIME = 15;
    private int lastTick = 0;

    public FireworkAbilityCapabilty() {}

    public boolean canUse(int tick){
        if(tick - lastTick >= TIME) {
            this.lastTick = tick;
            return true;
        } else {
            return false;
        }
    }

    public int getLastTick() {
        return lastTick;
    }

    public void setLastTick(int lastTick) {
        this.lastTick = lastTick;
    }
}
