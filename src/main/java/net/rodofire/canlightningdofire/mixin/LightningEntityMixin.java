package net.rodofire.canlightningdofire.mixin;

import net.rodofire.canlightningdofire.CanLightningDoFire;
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
        if (this.cosmetic || this.getWorld().isClient || !this.getWorld().getGameRules().getBoolean(GameRules.DO_FIRE_TICK) || !this.getWorld().getGameRules().getBoolean(CanLightningDoFire.LIGHTNING_DO_FIRE)) {
            return;
        }
        BlockPos blockPos = this.getBlockPos();
        BlockState blockState = AbstractFireBlock.getState(this.getWorld(), blockPos);
        if (this.getWorld().getBlockState(blockPos).isAir() && blockState.canPlaceAt(this.getWorld(), blockPos)) {
            this.getWorld().setBlockState(blockPos, blockState);
            ++this.blocksSetOnFire;
        }
        for (int i = 0; i < spreadAttempts; ++i) {
            BlockPos blockPos2 = blockPos.add(this.random.nextInt(3) - 1, this.random.nextInt(3) - 1, this.random.nextInt(3) - 1);
            blockState = AbstractFireBlock.getState(this.getWorld(), blockPos2);
            if (!this.getWorld().getBlockState(blockPos2).isAir() || !blockState.canPlaceAt(this.getWorld(), blockPos2)) continue;
            this.getWorld().setBlockState(blockPos2, blockState);
            ++this.blocksSetOnFire;
        }
    }
}






