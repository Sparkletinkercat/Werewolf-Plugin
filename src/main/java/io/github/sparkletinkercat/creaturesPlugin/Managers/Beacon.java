
package io.github.sparkletinkercat.creaturesPlugin.Managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Entity;  
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;

public class Beacon {
    private final JavaPlugin plugin;
    private final World world;
    
    public Beacon (JavaPlugin plugin) {
        this.plugin = plugin;
        this.world = Bukkit.getWorlds().get(0);
    }

    /**
     * Summons the custom beacon display at a specified location
     *
     * @param loc The location of the beacon
     */
    public void summonBeaconDisplay (Location loc) {this.summonBeaconDisplay (loc.getX(), loc.getY(), loc.getZ());}

    /**
     * Summons the custom beacon display at a specified location
     *
     * @param x The beacons x coordinate
     * @param y The beacons y coordinate
     * @param z The beacons z coordinate
     * 
     */
    public void summonBeaconDisplay (double x, double y, double z) {
        
        Location displayLoc = new Location(world,x,y,z);

        displayLoc.add(0.5, 0.5, 0.5);
        ItemStack pumpkinItem = new ItemStack(Material.CARVED_PUMPKIN);
        ItemMeta meta = pumpkinItem.getItemMeta();

        if (meta != null) {
            
            NamespacedKey key2 = new NamespacedKey(plugin, "Type");
            meta.getPersistentDataContainer().set(key2, PersistentDataType.STRING, "Beacon"); 

            pumpkinItem.setItemMeta(meta);
        }

        pumpkinItem.setData(DataComponentTypes.CUSTOM_MODEL_DATA, (CustomModelData)CustomModelData.customModelData().addString("666").build());

        ItemDisplay display = (ItemDisplay)displayLoc.getWorld().spawn(displayLoc, ItemDisplay.class);
        display.setItemStack(pumpkinItem);
        display.setPersistent(true);
    }

    public void removeBeaconDisplay (Location loc) {this.removeBeaconDisplay (loc.getX(), loc.getY(), loc.getZ());}

    public void removeBeaconDisplay (double x, double y, double z) {
        Entity entity = returnBeaconAtLocation(x + 0.5,y + 0.5,z + 0.5);
        if (entity != null) {entity.remove();}
    }

    public Entity returnBeaconAtLocation (double x, double y, double z) {
        Entity returnEntity = null;
        Location displayLoc = new Location(world,x,y,z);

        for (Entity entity : world.getNearbyEntities(displayLoc, 1, 1, 1)) {

            // Get Item display then check if its a beacon
            if (entity instanceof ItemDisplay itemDisplay) {
                ItemStack stack = itemDisplay.getItemStack();
                ItemMeta meta = stack.getItemMeta();
                NamespacedKey key = new NamespacedKey(plugin, "type");

                String value = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
                if (value.toLowerCase().contains("beacon")) {
                    returnEntity = entity;
                    break;
                }
            }
        }
        return returnEntity;
    }

    public void changeBeaconDisplay(CommandSourceStack source, int type) {
        Beacon beacon = new Beacon (this.plugin);

        Entity entity = source.getExecutor();
        Player player = (Player) entity;
    
        Block target = player.getTargetBlockExact(20);
        if (target != null && target.getType() == Material.BARRIER) {
            beacon.changeBeaconDisplay(type, target.getX(),target.getY(),target.getZ());
        } else {
            player.sendMessage("No beacon block in range");
        }
    }

    public void changeBeaconDisplay(int display, double x, double y, double z) {
        ItemDisplay itemDisplay = (ItemDisplay) returnBeaconAtLocation(x + 0.5, y + 0.5, z + 0.5);
        if (itemDisplay == null) return;

        // Create a new ItemStack with the correct material
        ItemStack stack = itemDisplay.getItemStack(); // whatever base material

        // Set custom model data
        
        stack.setData(DataComponentTypes.CUSTOM_MODEL_DATA, (CustomModelData)CustomModelData.customModelData().addString(String.valueOf(display)).build());

        // Push the new ItemStack to the ItemDisplay
        itemDisplay.setItemStack(stack);

        System.out.println("Updated beacon display to model ID: " + display);
    }

    public void registerBeacon (Player player, String name) {
    
        Block target = player.getTargetBlockExact(20);
        if (target != null && target.getType() == Material.BARRIER) {
            // Add name metadata
            this.updateMetaData(name, target.getX(),target.getY(),target.getZ());
            // Save Beacon to file
        } else {
            player.sendMessage("No beacon block in range");
        }
    }

    public void updateMetaData (String name, double x, double y, double z) {
        Entity beacon = this.returnBeaconAtLocation (x + 0.5, y + 0.5, z + 0.5);
        if (beacon != null) {
            ItemDisplay itemDisplay = (ItemDisplay) beacon;
            ItemStack stack = itemDisplay.getItemStack();
            ItemMeta meta = stack.getItemMeta();

            if (meta != null) {
                
                NamespacedKey key2 = new NamespacedKey(plugin, "Name");
                meta.getPersistentDataContainer().set(key2, PersistentDataType.STRING, name); 

                stack.setItemMeta(meta);
                itemDisplay.setItemStack(stack);
            }
        }
    }

    
}

