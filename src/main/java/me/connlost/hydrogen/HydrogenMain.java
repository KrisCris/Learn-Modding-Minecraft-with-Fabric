package me.connlost.hydrogen;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class HydrogenMain implements ModInitializer {

    //Item groups, different ways, but the same result.
    public static final ItemGroup SOLID_GROUP = FabricItemGroupBuilder.build(
            new Identifier("hydrogen", "solid"),
            () -> new ItemStack(Blocks.IRON_BLOCK)
    );

    public static final ItemGroup LIQUID_GROUP = FabricItemGroupBuilder.create(
            new Identifier("hydrogen", "liquid"))
            .icon(() -> new ItemStack(Items.WATER_BUCKET))
            .appendItems(itemStacks -> {
                itemStacks.add(ItemStack.EMPTY);
                itemStacks.add(new ItemStack(Items.LAVA_BUCKET));
                itemStacks.add(new ItemStack(Items.WATER_BUCKET));
                itemStacks.add(PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.LONG_FIRE_RESISTANCE));
            })
            .build();

    //An item
    public static final H2OItem H2O = new H2OItem(new Item.Settings().group(LIQUID_GROUP).maxCount(4));

    //A block
    public static final Block SOLID_H2O_BLOCK = new SolidH2OBlock(FabricBlockSettings.of(Material.METAL).hardness(4.0f));


    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        Registry.register(Registry.ITEM, new Identifier("hydrogen", "h2o"), H2O);
        Registry.register(Registry.BLOCK, new Identifier("hydrogen","solid_h2o_block"),SOLID_H2O_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("hydrogen", "solid_h2o_block"), new BlockItem(SOLID_H2O_BLOCK, new Item.Settings().group(SOLID_GROUP)));

    }
}
