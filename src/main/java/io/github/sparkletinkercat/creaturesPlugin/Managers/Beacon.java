
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

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;

public class Beacon {
    private final JavaPlugin plugin;
    
    public Beacon (JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void summonBeaconDisplay (Location loc) {this.summonBeaconDisplay (loc.getX(), loc.getY(), loc.getZ());}

    public void summonBeaconDisplay (double x, double y, double z) {
        World world = Bukkit.getWorlds().get(0);
        Location displayLoc = new Location(world,x,y,z);

        displayLoc.add(0.5, 0.5, 0.5);
        ItemStack pumpkinItem = new ItemStack(Material.CARVED_PUMPKIN);
        ItemMeta meta = pumpkinItem.getItemMeta();

        if (meta != null) {
            NamespacedKey key = new NamespacedKey(plugin, "custom_model_data");
            meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 665); // neutral
            pumpkinItem.setItemMeta(meta);
        }

        pumpkinItem.setData(DataComponentTypes.CUSTOM_MODEL_DATA, (CustomModelData)CustomModelData.customModelData().addString("664").build());
        ItemDisplay display = (ItemDisplay)displayLoc.getWorld().spawn(displayLoc, ItemDisplay.class);
        display.setItemStack(pumpkinItem);
        display.setPersistent(true);
    }
}


// public void createBeaconDisplay(BeaconSite beacon) {
//       if (beacon != null && beacon.getLocation() != null) {
//          Location displayLoc = beacon.getLocation().clone();
//          displayLoc.add(0.0, 0.5, 0.0);
//          ItemStack pumpkinItem = new ItemStack(Material.CARVED_PUMPKIN);
//          ItemMeta meta = pumpkinItem.getItemMeta();
//          String expectedCMD = this.getCustomModelDataForState(beacon.getState());
//          if (meta != null) {
//             meta.setDisplayName("§6Beacon: " + beacon.getName());
//             pumpkinItem.setItemMeta(meta);
//          }

//          pumpkinItem.setData(DataComponentTypes.CUSTOM_MODEL_DATA, (CustomModelData)CustomModelData.customModelData().addString(expectedCMD).build());
//          ItemDisplay display = (ItemDisplay)displayLoc.getWorld().spawn(displayLoc, ItemDisplay.class);
//          display.setItemStack(pumpkinItem);
//          display.setPersistent(true);
//          Transformation transform = new Transformation(new Vector3f(0.0F, 0.0F, 0.0F), new AxisAngle4f(0.0F, 0.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 1.0F), new AxisAngle4f(0.0F, 0.0F, 1.0F, 0.0F));
//          display.setTransformation(transform);
//          this.beaconDisplays.put(beacon.getName().toLowerCase(), display);
//          this.plugin.getLogger().info("Created item display for beacon: " + beacon.getName());
//       } else {
//          this.plugin.getLogger().warning("Cannot create display for null beacon or location");
//       }
//    }