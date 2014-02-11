package uk.co.shadeddimensions.ep3.client.gui;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import uk.co.shadeddimensions.ep3.block.BlockDecoration;
import uk.co.shadeddimensions.ep3.block.BlockFrame;
import uk.co.shadeddimensions.ep3.block.BlockStabilizer;
import uk.co.shadeddimensions.ep3.item.ItemEntityCard;
import uk.co.shadeddimensions.ep3.item.ItemGoggles;
import uk.co.shadeddimensions.ep3.item.ItemHandheldScanner;
import uk.co.shadeddimensions.ep3.item.ItemLocationCard;
import uk.co.shadeddimensions.ep3.item.ItemMisc;
import uk.co.shadeddimensions.ep3.item.ItemPaintbrush;
import uk.co.shadeddimensions.ep3.item.ItemPortalModule;
import uk.co.shadeddimensions.ep3.item.ItemUpgrade;
import uk.co.shadeddimensions.ep3.item.ItemWrench;
import uk.co.shadeddimensions.ep3.lib.Localization;
import uk.co.shadeddimensions.ep3.network.ClientProxy;
import uk.co.shadeddimensions.library.gui.GuiBase;
import uk.co.shadeddimensions.library.gui.element.ElementCrafting;
import uk.co.shadeddimensions.library.gui.element.ElementScrollBar;
import uk.co.shadeddimensions.library.gui.element.ElementScrollPanel;
import uk.co.shadeddimensions.library.gui.element.ElementText;
import uk.co.shadeddimensions.library.gui.element.ElementTextBox;
import uk.co.shadeddimensions.library.gui.tab.TabBase;
import uk.co.shadeddimensions.library.gui.tab.TabToggleButton;

public class GuiGuide extends GuiBase
{
	ElementScrollPanel scrollPanel;

	public GuiGuide()
	{
		super(new ResourceLocation("enhancedportals", "textures/gui/guide.png"));
		xSize = 256;
		ySize = 240;
	}

	@Override
	public void drawBackgroundTexture()
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(texture);

		int bgHeight = 0;
		while (bgHeight < height)
		{
			drawTexturedModalRect(guiLeft, bgHeight, 0, 15, xSize, 206);
			bgHeight += 206;
		}
	}

	@Override
	public void drawGuiBackgroundLayer(float f, int mouseX, int mouseY)
	{
		super.drawGuiBackgroundLayer(f, mouseX, mouseY);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, 0, 0, 0, xSize, 7);
		drawTexturedModalRect(guiLeft, height - 10, 0, 229, xSize, 10);
	}

	@Override
	public void addElements()
	{
		scrollPanel = new ElementScrollPanel(this, 11, -guiTop + 7, xSize - 22, height - 17);
		addElement(scrollPanel);
		addElement(new ElementScrollBar(this, xSize - 9, -guiTop + 7, 5, height - 16, scrollPanel));
		updateScrollPanel();
	}

	@Override
	public void addTabs()
	{
		addTab(new TabToggleButton(this, "mainMain", "Main", null));
		addTab(new TabToggleButton(this, "mainBlocks", "Blocks", new ItemStack(BlockFrame.instance)));
		addTab(new TabToggleButton(this, "mainItems", "Items", new ItemStack(ItemGoggles.instance)));

		if (ClientProxy.manualPage.startsWith("block"))
		{
			tabs.get(1).setFullyOpen();
			addTabsForPage("mainBlocks");
		}
		else if (ClientProxy.manualPage.startsWith("item"))
		{
			tabs.get(2).setFullyOpen();
			addTabsForPage("mainItems");
		}

		for (TabBase tab : tabs)
		{
			TabToggleButton t = (TabToggleButton) tab;

			if (t.ID.equals(ClientProxy.manualPage))
			{
				t.setFullyOpen();
			}
		}

		handleElementButtonClick(ClientProxy.manualPage, 1);
	}

	void addTabsForPage(String page)
	{
		if (page.startsWith("main"))
		{
			for (int i = tabs.size() - 1; i > 2; i--)
			{
				tabs.remove(i);
			}
		}
		
		if (page.equals("mainBlocks"))
		{
			addTab(new TabToggleButton(this, 1, "blockFrame", Localization.getGuiString("blockFrame"), new ItemStack(BlockFrame.instance)));
			addTab(new TabToggleButton(this, 1, "blockController", Localization.getGuiString("blockController"), new ItemStack(BlockFrame.instance, 1, 1)));
			addTab(new TabToggleButton(this, 1, "blockRedstoneInterface", Localization.getGuiString("blockRedstoneInterface"), new ItemStack(BlockFrame.instance, 1, 2)));
			addTab(new TabToggleButton(this, 1, "blockNetworkInterface", Localization.getGuiString("blockNetworkInterface"), new ItemStack(BlockFrame.instance, 1, 3)));
			addTab(new TabToggleButton(this, 1, "blockDiallingDevice", Localization.getGuiString("blockDiallingDevice"), new ItemStack(BlockFrame.instance, 1, 4)));
			addTab(new TabToggleButton(this, 1, "blockBiometric", Localization.getGuiString("blockBiometric"), new ItemStack(BlockFrame.instance, 1, 5)));
			addTab(new TabToggleButton(this, 1, "blockModule", Localization.getGuiString("blockModule"), new ItemStack(BlockFrame.instance, 1, 6)));
			addTab(new TabToggleButton(this, 1, "blockDbs", Localization.getGuiString("blockDBS"), new ItemStack(BlockStabilizer.instance)));
			addTab(new TabToggleButton(this, 1, "blockDecoration", Localization.getGuiString("blockDecoration"), new ItemStack(BlockDecoration.instance)));
		}
		else if (page.equals("mainItems"))
		{
			addTab(new TabToggleButton(this, 1, "itemGlasses", Localization.getGuiString("itemGlasses"), new ItemStack(ItemGoggles.instance)));
			addTab(new TabToggleButton(this, 1, "itemWrench", Localization.getGuiString("itemWrench"), new ItemStack(ItemWrench.instance)));
			addTab(new TabToggleButton(this, 1, "itemNanobrush", Localization.getGuiString("itemNanobrush"), new ItemStack(ItemPaintbrush.instance)));
			addTab(new TabToggleButton(this, 1, "itemLocationCard", Localization.getGuiString("itemLocationCard"), new ItemStack(ItemLocationCard.instance)));
			addTab(new TabToggleButton(this, 1, "itemIdCard", Localization.getGuiString("itemIdCard"), new ItemStack(ItemEntityCard.instance)));
			addTab(new TabToggleButton(this, 1, "itemScanner", Localization.getGuiString("itemScanner"), new ItemStack(ItemHandheldScanner.instance)));
			addTab(new TabToggleButton(this, 1, "itemPortalModules", Localization.getGuiString("itemPortalModules"), new ItemStack(ItemMisc.instance, 1, 0)));
			addTab(new TabToggleButton(this, 1, "itemUpgrades", Localization.getGuiString("itemUpgrades"), new ItemStack(ItemMisc.instance, 1, 1)));
		}
	}

	@Override
	public void handleElementButtonClick(String buttonName, int mouseButton)
	{
		ClientProxy.manualPage = buttonName;
		updateScrollPanel();
		addTabsForPage(ClientProxy.manualPage);
	}

	void updateScrollPanel()
	{
		scrollPanel.clear();

		if (ClientProxy.manualPage == null)
		{
			return;
		}

		scrollPanel.addElement(new ElementText(this, scrollPanel.getWidth() / 2 - getFontRenderer().getStringWidth(Localization.getGuiString(ClientProxy.manualPage)) / 2, 5, Localization.getGuiString(ClientProxy.manualPage), null, 0xFFFF00, true));
		int offset = 0;

		if (ClientProxy.manualPage.startsWith("block") || ClientProxy.manualPage.startsWith("item"))
		{
			scrollPanel.addElement(new ElementText(this, 5, 18, Localization.getGuiString("crafting"), null, 0x44AAFF, true));
			ElementCrafting craft = new ElementCrafting(this, scrollPanel.getWidth() / 2 - 58, 30, 0);

			if (ClientProxy.manualPage.equals("blockFrame"))
			{
				craft.addOutputSlot(new ItemStack(BlockFrame.instance));
				craft.addAllGridSlots(new ItemStack[] { new ItemStack(Blocks.stone), new ItemStack(Items.iron_ingot), new ItemStack(Blocks.stone), new ItemStack(Items.iron_ingot), new ItemStack(Blocks.quartz_block), new ItemStack(Items.iron_ingot), new ItemStack(Blocks.stone), new ItemStack(Items.iron_ingot), new ItemStack(Blocks.stone) });
			}
			else if (ClientProxy.manualPage.equals("blockController"))
			{
				craft.addOutputSlot(new ItemStack(BlockFrame.instance, 0, 1));
				craft.addAllGridSlots(new ItemStack[] { new ItemStack(BlockFrame.instance), new ItemStack(Items.diamond) });
			}
			else if (ClientProxy.manualPage.equals("blockRedstoneInterface"))
			{
				craft.addOutputSlot(new ItemStack(BlockFrame.instance, 0, 2));
				craft.addAllGridSlots(new ItemStack[] { null, new ItemStack(Items.redstone), null, new ItemStack(Items.redstone), new ItemStack(BlockFrame.instance), new ItemStack(Items.redstone), null, new ItemStack(Items.redstone), null });
			}
			else if (ClientProxy.manualPage.equals("blockNetworkInterface"))
			{
				craft.addOutputSlot(new ItemStack(BlockFrame.instance, 0, 3));
				craft.addAllGridSlots(new ItemStack[] { new ItemStack(BlockFrame.instance), new ItemStack(Items.ender_pearl) });
			}
			else if (ClientProxy.manualPage.equals("blockDiallingDevice"))
			{
				craft.addOutputSlot(new ItemStack(BlockFrame.instance, 0, 4));
				craft.addAllGridSlots(new ItemStack[] { new ItemStack(BlockFrame.instance, 0, 3), new ItemStack(Items.diamond) });
			}
			else if (ClientProxy.manualPage.equals("blockBiometric"))
			{
				craft.addOutputSlot(new ItemStack(BlockFrame.instance, 0, 5));
				craft.addAllGridSlots(new ItemStack[] { new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_beef), new ItemStack(Items.cooked_chicken), new ItemStack(Items.blaze_powder), new ItemStack(BlockFrame.instance), new ItemStack(Items.blaze_powder) });

				ElementCrafting craft2 = new ElementCrafting(this, scrollPanel.getWidth() / 2 - 58, 86, 0);
				craft2.addOutputSlot(new ItemStack(BlockFrame.instance, 0, 5));
				craft2.addAllGridSlots(new ItemStack[] { new ItemStack(Items.porkchop), new ItemStack(Items.beef), new ItemStack(Items.chicken), new ItemStack(Items.blaze_powder), new ItemStack(BlockFrame.instance), new ItemStack(Items.blaze_powder) });
				scrollPanel.addElement(craft2);
				offset += craft2.getHeight();
			}
			else if (ClientProxy.manualPage.equals("blockModule"))
			{
				craft.addOutputSlot(new ItemStack(BlockFrame.instance, 0, 6));
				craft.addAllGridSlots(new ItemStack[] { null, new ItemStack(ItemMisc.instance), null, new ItemStack(Items.emerald), new ItemStack(BlockFrame.instance), new ItemStack(Items.diamond) });
			}
			else if (ClientProxy.manualPage.equals("blockDbs"))
			{
				craft.addOutputSlot(new ItemStack(BlockStabilizer.instance, 6));
				craft.addAllGridSlots(new ItemStack[] { new ItemStack(Blocks.iron_block), new ItemStack(Items.ender_pearl), new ItemStack(Blocks.iron_block), new ItemStack(Items.ender_pearl), new ItemStack(Items.diamond), new ItemStack(Items.ender_pearl), new ItemStack(Blocks.iron_block), new ItemStack(Items.ender_pearl), new ItemStack(Blocks.iron_block) });
			}
			else if (ClientProxy.manualPage.equals("blockDecoration"))
			{
				craft.addOutputSlot(new ItemStack(BlockDecoration.instance, 8, 0));
				craft.addAllGridSlots(new ItemStack[] { new ItemStack(Blocks.stone), new ItemStack(Blocks.quartz_block), new ItemStack(Blocks.stone), new ItemStack(Blocks.quartz_block), new ItemStack(Blocks.quartz_block), new ItemStack(Blocks.quartz_block), new ItemStack(Blocks.stone), new ItemStack(Blocks.quartz_block), new ItemStack(Blocks.stone) });

				ElementCrafting craft2 = new ElementCrafting(this, scrollPanel.getWidth() / 2 - 58, 86, 0);
				craft2.addOutputSlot(new ItemStack(BlockDecoration.instance, 10, 1));
				craft2.addAllGridSlots(new ItemStack[] { null, new ItemStack(Items.iron_ingot), null, new ItemStack(Items.iron_ingot), new ItemStack(Blocks.iron_block), new ItemStack(Items.iron_ingot), null, new ItemStack(Items.iron_ingot), null });
				scrollPanel.addElement(craft2);
				offset += craft2.getHeight();
			}
			else if (ClientProxy.manualPage.equals("itemGlasses"))
			{
				craft.addOutputSlot(new ItemStack(ItemGoggles.instance));
				craft.addAllGridSlots(new ItemStack[] { new ItemStack(Items.dye, 0, 1), null, new ItemStack(Items.dye, 0, 6), new ItemStack(Blocks.glass_pane), new ItemStack(Items.leather), new ItemStack(Blocks.glass_pane), new ItemStack(Items.leather), null, new ItemStack(Items.leather) });
			}
			else if (ClientProxy.manualPage.equals("itemWrench"))
			{
				craft.addOutputSlot(new ItemStack(ItemWrench.instance));
				craft.addAllGridSlots(new ItemStack[] { new ItemStack(Items.iron_ingot), null, new ItemStack(Items.iron_ingot), null, new ItemStack(Items.quartz), null, null, new ItemStack(Items.iron_ingot) });
			}
			else if (ClientProxy.manualPage.equals("itemNanobrush"))
			{
				craft.addOutputSlot(new ItemStack(ItemPaintbrush.instance));
				craft.addAllGridSlots(new ItemStack[] { null, new ItemStack(Items.string), new ItemStack(Blocks.wool), null, new ItemStack(Items.stick), new ItemStack(Items.string), new ItemStack(Items.stick) });
			}
			else if (ClientProxy.manualPage.equals("itemLocationCard"))
			{
				craft.addOutputSlot(new ItemStack(ItemLocationCard.instance, 16));
				craft.addAllGridSlots(new ItemStack[] { new ItemStack(Items.iron_ingot), new ItemStack(Items.paper), new ItemStack(Items.iron_ingot), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.iron_ingot), new ItemStack(Items.dye, 0, 4), new ItemStack(Items.iron_ingot) });
			}
			else if (ClientProxy.manualPage.equals("itemIdCard"))
			{
				craft.addOutputSlot(new ItemStack(ItemEntityCard.instance, 8));
				craft.addAllGridSlots(new ItemStack[] { new ItemStack(Items.gold_ingot), new ItemStack(Items.paper), new ItemStack(Items.gold_ingot), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.gold_ingot), new ItemStack(Items.dye, 0, 10), new ItemStack(Items.gold_ingot) });
			}
			else if (ClientProxy.manualPage.equals("itemScanner"))
			{
				craft.addOutputSlot(new ItemStack(ItemHandheldScanner.instance));
				craft.addAllGridSlots(new ItemStack[] { new ItemStack(Items.gold_ingot), new ItemStack(Items.redstone), new ItemStack(Items.gold_ingot), new ItemStack(Items.iron_ingot), new ItemStack(Items.quartz), new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), new ItemStack(ItemEntityCard.instance), new ItemStack(Items.iron_ingot) });
			}
			else if (ClientProxy.manualPage.equals("itemPortalModules"))
			{
				scrollPanel.addElement(new ElementCrafting(this, craft.getRelativeX(), 30, 0).addOutputSlot(new ItemStack(ItemMisc.instance)).addAllGridSlots(new ItemStack[] { new ItemStack(Items.gold_nugget), new ItemStack(Items.gold_nugget), new ItemStack(Items.gold_nugget), new ItemStack(Items.gold_nugget), new ItemStack(Items.iron_ingot), new ItemStack(Items.gold_nugget), new ItemStack(Items.gold_nugget), new ItemStack(Items.gold_nugget), new ItemStack(Items.gold_nugget) }));
				scrollPanel.addElement(new ElementCrafting(this, craft.getRelativeX(), 30 + 57, 0).addOutputSlot(new ItemStack(ItemPortalModule.instance)).addAllGridSlots(new ItemStack[] { new ItemStack(Items.redstone), new ItemStack(ItemMisc.instance), new ItemStack(Items.gunpowder) }));
				scrollPanel.addElement(new ElementCrafting(this, craft.getRelativeX(), 30 + 114, 0).addOutputSlot(new ItemStack(ItemPortalModule.instance, 0, 1)).addAllGridSlots(new ItemStack[] { new ItemStack(Items.dye, 0, 1), new ItemStack(Items.dye, 0, 2), new ItemStack(Items.dye, 0, 4), null, new ItemStack(ItemMisc.instance), null, new ItemStack(Items.dye, 0, 4), new ItemStack(Items.dye, 0, 2), new ItemStack(Items.dye, 0, 1) }));
				scrollPanel.addElement(new ElementCrafting(this, craft.getRelativeX(), 30 + 171, 0).addOutputSlot(new ItemStack(ItemPortalModule.instance, 0, 2)).addAllGridSlots(new ItemStack[] { new ItemStack(Items.redstone), new ItemStack(ItemMisc.instance), new ItemStack(Blocks.noteblock) }));
				scrollPanel.addElement(new ElementCrafting(this, craft.getRelativeX(), 30 + 228, 0).addOutputSlot(new ItemStack(ItemPortalModule.instance, 0, 3)).addAllGridSlots(new ItemStack[] { new ItemStack(Blocks.anvil), new ItemStack(ItemMisc.instance), new ItemStack(Items.feather) }));
				scrollPanel.addElement(new ElementCrafting(this, craft.getRelativeX(), 30 + 285, 0).addOutputSlot(new ItemStack(ItemPortalModule.instance, 0, 5)).addAllGridSlots(new ItemStack[] { new ItemStack(Items.dye, 0, 15), new ItemStack(ItemMisc.instance), new ItemStack(Items.dye) }));
				scrollPanel.addElement(new ElementCrafting(this, craft.getRelativeX(), 30 + 342, 0).addOutputSlot(new ItemStack(ItemPortalModule.instance, 0, 7)).addAllGridSlots(new ItemStack[] { new ItemStack(Items.feather), new ItemStack(Items.feather), new ItemStack(Items.feather), new ItemStack(Items.feather), new ItemStack(ItemMisc.instance), new ItemStack(Items.feather), new ItemStack(Items.feather), new ItemStack(Items.feather), new ItemStack(Items.feather) }));

				craft = null;
				offset += 57 * 6;
			}
			else
			{
				scrollPanel.addElement(new ElementCrafting(this, craft.getRelativeX(), 30, 0).addOutputSlot(new ItemStack(ItemMisc.instance, 1, 1)).addAllGridSlots(new ItemStack[] { new ItemStack(Items.diamond), null, null, new ItemStack(Items.paper), null, null, new ItemStack(Items.dye, 0, 1) }));
				scrollPanel.addElement(new ElementCrafting(this, craft.getRelativeX(), 30 + 57, 0).addOutputSlot(new ItemStack(ItemUpgrade.instance)).addAllGridSlots(new ItemStack[] { null, new ItemStack(Items.redstone), null, new ItemStack(Items.redstone), new ItemStack(ItemMisc.instance, 1, 1), new ItemStack(Items.redstone), null, new ItemStack(Items.redstone) }));
				scrollPanel.addElement(new ElementCrafting(this, craft.getRelativeX(), 30 + 114, 0).addOutputSlot(new ItemStack(ItemUpgrade.instance, 1, 1)).addAllGridSlots(new ItemStack[] { new ItemStack(ItemMisc.instance, 1, 1), new ItemStack(Items.ender_pearl) }));
				scrollPanel.addElement(new ElementCrafting(this, craft.getRelativeX(), 30 + 171, 0).addOutputSlot(new ItemStack(ItemUpgrade.instance, 1, 2)).addAllGridSlots(new ItemStack[] { new ItemStack(ItemUpgrade.instance, 1, 1), new ItemStack(Items.diamond) }));
				scrollPanel.addElement(new ElementCrafting(this, craft.getRelativeX(), 30 + 228, 0).addOutputSlot(new ItemStack(ItemUpgrade.instance, 1, 3)).addAllGridSlots(new ItemStack[] { new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_beef), new ItemStack(Items.cooked_chicken), new ItemStack(Items.blaze_powder), new ItemStack(ItemMisc.instance, 1, 1), new ItemStack(Items.blaze_powder) }));
				scrollPanel.addElement(new ElementCrafting(this, craft.getRelativeX(), 30 + 285, 0).addOutputSlot(new ItemStack(ItemUpgrade.instance, 1, 4)).addAllGridSlots(new ItemStack[] { null, new ItemStack(ItemMisc.instance), null, new ItemStack(Items.emerald), new ItemStack(ItemMisc.instance, 1, 1), new ItemStack(Items.diamond) }));

				craft = null;
				offset += 57 * (BlockFrame.FRAME_TYPES - 2);
			}

			if (craft != null)
			{
				scrollPanel.addElement(craft);
			}

			scrollPanel.addElement(new ElementText(this, 5, 90 + offset, Localization.getGuiString("information"), null, 0x44AAFF, true));
			scrollPanel.addElement(new ElementTextBox(this, 8, 103 + offset, Localization.getGuiString(ClientProxy.manualPage + ".information"), scrollPanel.getWidth() - 14, 0xFFFFFF, false));
		}
		else
		{
			scrollPanel.addElement(new ElementTextBox(this, 8, 18, Localization.getGuiString(ClientProxy.manualPage + ".information"), scrollPanel.getWidth() - 14, 0xFFFFFF, false));
		}
	}
}
