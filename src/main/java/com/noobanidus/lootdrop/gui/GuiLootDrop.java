package com.noobanidus.lootdrop.gui;

import com.noobanidus.lootdrop.util.LootSelector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiLootDrop extends GuiScreen {
    private GuiLootTableList tableList;

    public GuiLootDrop() {
    }

    @Override
    public void initGui() {
        if (tableList == null) {
            tableList = new GuiLootTableList(this, mc, width, height, 40, height, 16);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        tableList.drawScreen(mouseX, mouseY, partialTicks);
        drawCenteredString(fontRenderer, "Select Loot Table", 65, 15, 0xffffff);
        ResourceLocation current = LootSelector.getSelectedFor();
        if (current != null) {
            drawString(fontRenderer, "Selected: " + current.toString(), 135, 15, 0xffffff);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        tableList.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        tableList.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        tableList.handleMouseInput();
    }

    public static class GuiLootTableList extends GuiListExtended {
        private GuiLootDrop gui;
        private List<GuiLootTableEntry> entries = new ArrayList<>();
        private int selected = -1;

        public GuiLootTableList(GuiLootDrop gui, Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
            super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
            this.gui = gui;
            refreshList();
        }

        @Nullable
        public GuiLootTableEntry getSelectedTable() {
            if (selected >= 0 && selected < getSize()) {
                return (GuiLootTableEntry) getListEntry(selected);
            }

            return null;
        }

        public void refreshList() {
            entries.clear();
            List<ResourceLocation> locations = new ArrayList<>(LootTableList.getAll());
            locations.sort(Comparator.comparing(ResourceLocation::getPath));

            for (ResourceLocation loc : locations) {
                entries.add(new GuiLootTableEntry(loc, this));
            }
            selected = -1;
        }

        @Override
        protected boolean isSelected(int slotIndex) {
            return slotIndex == selected;
        }

        public void setSelected(int slotIndex) {
            this.selected = slotIndex;
            if (getSelectedTable() != null) {
                LootSelector.setSelectedFor(getSelectedTable().lootTable);
            }
        }

        @Override
        public int getListWidth() {
            return super.getListWidth() + 100;
        }

        @Override
        protected int getScrollBarX() {
            return super.getScrollBarX();
        }

        @Override
        public void drawScreen(int mouseXIn, int mouseYIn, float partialTicks) {
            if (visible) {
                mouseX = mouseXIn;
                mouseY = mouseYIn;
                drawBackground();
                int x = getScrollBarX();
                bindAmountScrolled();
                GlStateManager.disableLighting();
                GlStateManager.disableFog();
                Tessellator tessellator = Tessellator.getInstance();
                drawContainerBackground(tessellator);
                int inLeft = width - getListWidth() + 2;
                int inTop = top + 4 - (int) amountScrolled;
                drawSelectionBox(inLeft, inTop, mouseXIn, mouseYIn, partialTicks);
            }
        }

        @Override
        public void handleMouseInput() {
            int i2 = Mouse.getEventDWheel();
            if (i2 != 0) {
                if (i2 > 0) {
                    i2 = -1;
                } else if (i2 < 0) {
                    i2 = 1;
                }

                amountScrolled += (float) (i2 * slotHeight);
            } else {
                super.handleMouseInput();
            }
        }

        @Override
        protected void drawContainerBackground(Tessellator tessellator) {
            gui.drawDefaultBackground();
        }

        @Override
        public IGuiListEntry getListEntry(int index) {
            return entries.get(index);
        }

        @Override
        protected int getSize() {
            return entries.size();
        }
    }

    public static class GuiLootTableEntry implements GuiListExtended.IGuiListEntry {

        private ResourceLocation lootTable;
        private GuiLootTableList parent;
        private Minecraft mc;

        public GuiLootTableEntry(ResourceLocation lootTable, GuiLootTableList parent) {
            this.lootTable = lootTable;
            this.parent = parent;
            this.mc = Minecraft.getMinecraft();
        }

        @Override
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float partialTicks) {
            mc.fontRenderer.drawStringWithShadow(this.lootTable.toString(), x + 4, y + 2, 0x808080);
            GlStateManager.color(1f, 1f, 1f, 1f);
        }

        @Override
        public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
            parent.setSelected(slotIndex);
            return true;
        }

        @Override
        public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
        }

        @Override
        public void updatePosition(int slotIndex, int x, int y, float partialTicks) {
        }
    }
}
