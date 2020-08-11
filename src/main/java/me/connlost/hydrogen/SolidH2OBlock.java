package me.connlost.hydrogen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SolidH2OBlock extends Block {
    public SolidH2OBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(HARDENED,false));
    }

    public static final BooleanProperty HARDENED = BooleanProperty.of("hardened");

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HARDENED);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient){
            player.sendMessage(new LiteralText("DON'T FUCKIN' TOUCH ME!"),false);
        }

        //state
        player.sendMessage(new LiteralText("Now you made me hardened"), true);
        world.setBlockState(pos, state.with(HARDENED, true));


        return ActionResult.SUCCESS;
    }

    @Override
    public float getHardness(BlockView world, BlockPos pos) {
        boolean hardened = world.getBlockState(pos).get(HARDENED);
        if (hardened) return 2.0f;
        else return 0.5f;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient && placer instanceof PlayerEntity){
            ((PlayerEntity)placer).sendMessage(new LiteralText("where the fuck i am?"), true);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0f,0f,0f,1f,1f,0.5f);
    }
}
