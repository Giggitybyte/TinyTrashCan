package me.thegiggitybyte.ttc.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class TrashCanBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final VoxelShape SHAPE;
    
    public TrashCanBlock(Settings settings) {
        super(settings);
    }
    
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new TrashCanBlockEntity();
    }
    
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient && hand == Hand.MAIN_HAND) {
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
            
            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
                world.playSound(null, pos,
                        SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN,
                        SoundCategory.BLOCKS, 0.9f, 1.3f);
            }
        }
        
        return ActionResult.SUCCESS;
    }
    
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
    
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
    
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    
    static {
        VoxelShape trashCan = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D);
        VoxelShape trashCanLid = Block.createCuboidShape(3.0D, 9.0D, 3.0D, 13.0D, 11.0D, 13.0D);
        
        SHAPE = VoxelShapes.union(trashCan, trashCanLid);
    }
}
