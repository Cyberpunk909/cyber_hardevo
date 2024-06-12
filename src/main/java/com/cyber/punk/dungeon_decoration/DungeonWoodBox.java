package com.cyber.punk.dungeon_decoration;

import com.cyber.punk.util.BlockUtils;
import com.cyber.punk.bounding_block.VoxelUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.state.DirectionProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import java.util.stream.Stream;

public class DungeonWoodBox extends BlockUtils {
    public static final DirectionProperty FACING = HorizontalBlock.FACING;

    public DungeonWoodBox() {
        super(Properties.of(Material.WOOD)
                .strength(2f, 3.0f)
                .noOcclusion());
    }
    private static final VoxelShape SHAPE_N;

    static {
        Stream<VoxelShape> shapeStream = Stream.of(
                Block.box(-1, 13, 4, 15, 16, 8),
                Block.box(0, 13, 12, 16, 16, 16),
                Block.box(1, 13, 8, 17, 16, 12),
                Block.box(0, 13, 0, 16, 16, 4),
                Block.box(12, 2, 1, 15, 13, 4),
                Block.box(1, 2, 1, 4, 13, 4),
                Block.box(1, 2, 12, 4, 13, 15),
                Block.box(12, 2, 12, 15, 13, 15),
                Block.box(1, 0, 1, 15, 2, 15),
                Block.box(2, 2, 2, 14, 13, 14)
        );
        SHAPE_N = shapeStream.reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();
    }
    private static final VoxelShape SHAPE_E = VoxelUtil.rotateShape(Direction.NORTH, Direction.EAST, SHAPE_N);
    private static final VoxelShape SHAPE_S = VoxelUtil.rotateShape(Direction.NORTH, Direction.SOUTH, SHAPE_N);
    private static final VoxelShape SHAPE_W = VoxelUtil.rotateShape(Direction.NORTH, Direction.WEST, SHAPE_N);

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction facing = state.getValue(FACING);
        switch (facing) {
            case EAST:
                return SHAPE_E;
            case SOUTH:
                return SHAPE_S;
            case WEST:
                return SHAPE_W;
            default:
                return SHAPE_N;
        }
    }
}