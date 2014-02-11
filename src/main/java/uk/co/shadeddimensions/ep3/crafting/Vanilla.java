package uk.co.shadeddimensions.ep3.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import uk.co.shadeddimensions.ep3.block.BlockDecoration;
import uk.co.shadeddimensions.ep3.block.BlockFrame;
import uk.co.shadeddimensions.ep3.block.BlockStabilizer;
import uk.co.shadeddimensions.ep3.item.ItemEntityCard;
import uk.co.shadeddimensions.ep3.item.ItemGoggles;
import uk.co.shadeddimensions.ep3.item.ItemGuide;
import uk.co.shadeddimensions.ep3.item.ItemHandheldScanner;
import uk.co.shadeddimensions.ep3.item.ItemLocationCard;
import uk.co.shadeddimensions.ep3.item.ItemMisc;
import uk.co.shadeddimensions.ep3.item.ItemPaintbrush;
import uk.co.shadeddimensions.ep3.item.ItemPortalModule;
import uk.co.shadeddimensions.ep3.item.ItemUpgrade;
import uk.co.shadeddimensions.ep3.item.ItemWrench;
import uk.co.shadeddimensions.ep3.network.CommonProxy;
import cpw.mods.fml.common.registry.GameRegistry;

public class Vanilla
{
    public static void registerRecipes()
    {
        if (!CommonProxy.disableVanillaRecipes)
        {
            // Frames
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockFrame.instance, 4, 0), new Object[] { "SIS", "IQI", "SIS", 'S', Blocks.stone, 'Q', Blocks.quartz_block, 'I', Items.iron_ingot }));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.REDSTONE_INTERFACE), new Object[] { " R ", "RFR", " R ", 'F', new ItemStack(BlockFrame.instance, 1, 0), 'R', Items.redstone }));
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.NETWORK_INTERFACE), new ItemStack(BlockFrame.instance, 1, 0), Items.ender_pearl));

            // In-Place Upgrades
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemUpgrade.instance, 1, 0), new Object[] { " R ", "RFR", " R ", 'F', new ItemStack(ItemMisc.instance, 1, 1), 'R', Items.redstone }));
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemUpgrade.instance, 1, 1), new ItemStack(ItemMisc.instance, 1, 1), Items.ender_pearl));

            // Stabilizer
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockStabilizer.instance, 6), new Object[] { "QPQ", "PDP", "QPQ", 'D', Items.diamond, 'Q', Blocks.iron_block, 'P', Items.ender_pearl }));
        }

        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.PORTAL_CONTROLLER), new ItemStack(BlockFrame.instance, 1, 0), Items.diamond));

        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.DIALLING_DEVICE), new ItemStack(BlockFrame.instance, 1, BlockFrame.NETWORK_INTERFACE), Items.diamond));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.BIOMETRIC_IDENTIFIER), new Object[] { "PBC", "ZFZ", 'F', new ItemStack(BlockFrame.instance, 1, 0), 'Z', Items.blaze_powder, 'P', Items.porkchop, 'B', Items.beef, 'C', Items.chicken }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.BIOMETRIC_IDENTIFIER), new Object[] { "PBC", "ZFZ", 'F', new ItemStack(BlockFrame.instance, 1, 0), 'Z', Items.blaze_powder, 'P', Items.cooked_porkchop, 'B', Items.cooked_beef, 'C', Items.cooked_chicken }));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlockFrame.instance, 1, BlockFrame.MODULE_MANIPULATOR), new ItemStack(BlockFrame.instance, 1, 0), Items.diamond, Items.emerald, new ItemStack(ItemMisc.instance, 1, 0)));

        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemUpgrade.instance, 1, 2), new ItemStack(ItemUpgrade.instance, 1, 1), Items.diamond));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemUpgrade.instance, 1, 3), new Object[] { "PBC", "ZFZ", 'F', new ItemStack(ItemMisc.instance, 1, 1), 'Z', Items.blaze_powder, 'P', Items.porkchop, 'B', Items.beef, 'C', Items.chicken }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemUpgrade.instance, 1, 3), new Object[] { "PBC", "ZFZ", 'F', new ItemStack(ItemMisc.instance, 1, 1), 'Z', Items.blaze_powder, 'P', Items.cooked_porkchop, 'B', Items.cooked_beef, 'C', Items.cooked_chicken }));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemUpgrade.instance, 1, 4), new ItemStack(ItemMisc.instance, 1, 1), Items.diamond, Items.emerald, new ItemStack(ItemMisc.instance, 1, 0)));

        // Handheld Scanner
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemHandheldScanner.instance), new Object[] { "GRG", "IQI", "IEI", 'G', Items.gold_ingot, 'I', Items.iron_ingot, 'R', Items.redstone, 'Q', Items.quartz, 'E', ItemEntityCard.instance }));

        // Decoration
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockDecoration.instance, 8, 0), new Object[] { "SQS", "QQQ", "SQS", 'S', Blocks.stone, 'Q', Blocks.quartz_block }));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlockDecoration.instance, 10, 1), Blocks.iron_block, Items.iron_ingot, Items.iron_ingot, Items.iron_ingot, Items.iron_ingot));

        // Blank stuff
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemMisc.instance, 1, 0), true, new Object[] { "NNN", "NIN", "NNN", 'I', Items.iron_ingot, 'N', Items.gold_nugget })); // Blank Portal Module
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemMisc.instance, 8, 1), new Object[] { "D", "P", "R", 'P', Items.paper, 'D', Items.diamond, 'R', "dyeRed" })); // Blank Upgrade

        // Portal Modules
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemPortalModule.instance, 1, 0), new Object[] { "RXG", 'X', new ItemStack(ItemMisc.instance, 1, 0), 'R', Items.redstone, 'G', Items.gunpowder })); // Particle Destroyer
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemPortalModule.instance, 1, 1), new Object[] { "RGB", " X ", "BGR", 'X', new ItemStack(ItemMisc.instance, 1, 0), 'R', "dyeRed", 'B', "dyeBlue", 'G', "dyeGreen" })); // Rainbow particles
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemPortalModule.instance, 1, 2), new Object[] { "RXN", 'X', new ItemStack(ItemMisc.instance, 1, 0), 'R', Items.redstone, 'N', Blocks.noteblock })); // Portal Silencer
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemPortalModule.instance, 1, 3), new Object[] { "AXF", 'X', new ItemStack(ItemMisc.instance, 1, 0), 'A', Blocks.anvil, 'F', Items.feather })); // Momentum
        // 4 - Portal Cloaking - does not have a recipe
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemPortalModule.instance, 1, 5), new Object[] { "BXI", 'X', new ItemStack(ItemMisc.instance, 1, 0), 'B', "dyeWhite", 'I', "dyeBlack" })); // Particle Shader
        // 6 - Ethereal Frame - removed
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemPortalModule.instance, 1, 7), new Object[] { "FFF", "FXF", "FFF", 'X', new ItemStack(ItemMisc.instance, 1, 0), 'F', Items.feather })); // Featherfall

        // Guide Book
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemGuide.instance), new ItemStack(Items.book), new ItemStack(ItemLocationCard.instance)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemGuide.instance), new ItemStack(Items.book), new ItemStack(ItemEntityCard.instance)));

        // Nanobrush
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemPaintbrush.instance), new Object[] { "WT ", "TS ", "  S", 'W', Blocks.wool, 'T', Items.string, 'S', "stickWood" }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemPaintbrush.instance), new Object[] { " TW", " ST", "S  ", 'W', Blocks.wool, 'T', Items.string, 'S', "stickWood" }));

        // Location Card
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLocationCard.instance, 16), new Object[] { "IPI", "PPP", "IDI", 'I', Items.iron_ingot, 'P', Items.paper, 'D', "dyeBlue" }));

        // Entity Card
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemEntityCard.instance, 8), new Object[] { "GPG", "PPP", "GDG", 'G', Items.gold_ingot, 'P', Items.paper, 'D', "dyeLime" }));

        // Wrench
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemWrench.instance), new Object[] { "I I", " Q ", " I ", 'I', Items.iron_ingot, 'Q', Items.quartz }));

        // Glasses
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemGoggles.instance), true, new Object[] { "R B", "GLG", "L L", 'R', "dyeRed", 'B', "dyeCyan", 'G', Blocks.glass_pane, 'L', Items.leather }));
    }
}
