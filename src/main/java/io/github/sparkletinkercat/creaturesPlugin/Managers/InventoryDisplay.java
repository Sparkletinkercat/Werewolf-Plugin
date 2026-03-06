package io.github.sparkletinkercat.creaturesPlugin.Managers;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import java.util.List;
import org.bukkit.entity.Player;

public class InventoryDisplay {
    
    private final JavaPlugin plugin;
    private Inventory inventory = null;

    public InventoryDisplay (JavaPlugin plugin) {
        this.plugin = plugin;
    }


    public void createMenu (int numberOfSlots, String menuTitle) {
        this.inventory = Bukkit.createInventory(null, numberOfSlots, Component.text(menuTitle));
    }

    public void createMenuButton (Material material, String text, String description, NamedTextColor color, int slotNumber) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text(text).color(color));
        itemMeta.lore(List.of(Component.text(description)));
        item.setItemMeta(itemMeta);

        this.inventory.setItem(slotNumber, item);
    }

    public Inventory returnInventoryDisplay () {
        return inventory;
    }

    public void openInventory (Player player) {player.openInventory(this.inventory);}
}

