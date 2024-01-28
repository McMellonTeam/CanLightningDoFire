package net.louis.canlightningdofire.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.louis.canlightningdofire.CanLightningDoFire;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Random;

@Mixin(LightningEntity.class)
public abstract class LightningEntityMixin extends Entity {

    @Unique
    private boolean cosmetic;

    @Shadow
    private  int blocksSetOnFire;


    public LightningEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    /**
     * @author
     * @reason
     */
    @Overwrite
    private void spawnFire(int spreadAttempts) {
        System.out.println("ok ok ok ok okok oko kok ok ok ok ok o kok ok ok ok ok ");
        if (this.cosmetic || this.getWorld().isClient || !this.getWorld().getGameRules().getBoolean(GameRules.DO_FIRE_TICK) || !this.getWorld().getGameRules().getBoolean(CanLightningDoFire.LIGHTNING_DO_FIRE)) {
            System.out.println("nooo oo o oo o oo o o o oo o on");
            return;
        }
        BlockPos blockPos = this.getBlockPos();
        BlockState blockState = AbstractFireBlock.getState(this.getWorld(), blockPos);
        if (this.getWorld().getBlockState(blockPos).isAir() && blockState.canPlaceAt(this.getWorld(), blockPos)) {
            System.out.println("pro");
            this.getWorld().setBlockState(blockPos, blockState);
            ++this.blocksSetOnFire;
        }
        for (int i = 0; i < spreadAttempts; ++i) {
            System.out.println("pro");
            BlockPos blockPos2 = blockPos.add(this.random.nextInt(3) - 1, this.random.nextInt(3) - 1, this.random.nextInt(3) - 1);
            blockState = AbstractFireBlock.getState(this.getWorld(), blockPos2);
            if (!this.getWorld().getBlockState(blockPos2).isAir() || !blockState.canPlaceAt(this.getWorld(), blockPos2)) continue;
            this.getWorld().setBlockState(blockPos2, blockState);
            ++this.blocksSetOnFire;
        }
    }
}






