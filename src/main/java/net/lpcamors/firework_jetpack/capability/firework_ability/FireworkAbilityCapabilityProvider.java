package net.lpcamors.firework_jetpack.capability.firework_ability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class FireworkAbilityCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<FireworkAbilityCapabilty> CAP = CapabilityManager.get(new CapabilityToken<FireworkAbilityCapabilty>() { });
    private FireworkAbilityCapabilty fireworkAbilityCapabilty = null;
    private final LazyOptional<FireworkAbilityCapabilty> optional = LazyOptional.of(this::getCap);

    @Nonnull
    private FireworkAbilityCapabilty getCap() {
        if (fireworkAbilityCapabilty == null) {
            fireworkAbilityCapabilty = new FireworkAbilityCapabilty();
        }
        return fireworkAbilityCapabilty;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CAP) {
            return optional.cast();
        } else {
            return LazyOptional.empty();
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("LastTick", getCap().getLastTick());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getCap().setLastTick(nbt.getInt("LastTick"));
    }


}
