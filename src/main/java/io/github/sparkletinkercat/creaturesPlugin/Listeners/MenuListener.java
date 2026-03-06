package io.github.sparkletinkercat.creaturesPlugin.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;

public class MenuListener implements Listener {

    private final Component MENU_TITLE = Component.text("Start a new game");
    private final JavaPlugin plugin;

    public MenuListener (JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        /* Check that the main menu isn't the open one */
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (event.getView().title().equals(MENU_TITLE)) {
            event.setCancelled(true);

            ItemStack clicked = event.getCurrentItem();
            if (clicked == null) return;
            if (!clicked.hasItemMeta()) return;


            if (event.getSlot() == 12 || event.getSlot() == 14) {
                player.closeInventory();
                if (event.getSlot() == 12) {
                    Bukkit.dispatchCommand(player, "admin game startGame");
                }
            }
        }
    }
}

