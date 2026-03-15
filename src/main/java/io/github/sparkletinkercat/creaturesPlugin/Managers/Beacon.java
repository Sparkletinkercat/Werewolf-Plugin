
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

import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import org.bukkit.configuration.ConfigurationSection;

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

    /**
     * Removes the custom beacon display at a specified location
     *
     * @param loc The beacons location
     * 
     */
    public void removeBeaconDisplay (Location loc) {this.removeBeaconDisplay (loc.getX(), loc.getY(), loc.getZ());}

    /**
     * Removes the custom beacon display at a specified location
     *
     * @param x The beacons x coordinate
     * @param y The beacons y coordinate
     * @param z The beacons z coordinate
     * 
     */
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

    /**
     * Changes the beacons display to another custom look
     *
     * @param display The number of the resource file of the texture.
     * @param x The beacons x coordinate
     * @param y The beacons y coordinate
     * @param z The beacons z coordinate
     * 
     */
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

    /**
     * Registers a beacon for gameplay, updating its metadata and adding it to the yml file.
     *
     * @param player The player who ran the command
     * @param name The name of the beacon
     * 
     */
    public void registerBeacon (Player player, String name) {
    
        Block target = player.getTargetBlockExact(20);
        if (target != null && target.getType() == Material.BARRIER) {
            // Add name metadata
            this.updateMetaData(name, "Name", target.getX(),target.getY(),target.getZ());
            // Add Conversion Amount
            this.updateMetaData("50", "ConversionAmount", target.getX(),target.getY(),target.getZ());
            // Current Controlling Team
            this.updateMetaData("None", "ControllingTeam", target.getX(),target.getY(),target.getZ());
            // Save Beacon to file
            this.storeBeaconInFile (name,target.getX(),target.getY(),target.getZ());
        } else {
            player.sendMessage("No beacon block in range");
        }
    }

    /**
     * Updates the Meta Data for a beacon 
     *
     * @param name The exact name of the beacon
     * @param metadata Where the metadata is located
     * @param x The beacons x coordinate
     * @param y The beacons y coordinate
     * @param z The beacons z coordinate
     * 
     */
    public void updateMetaData (String name, String metadata, double x, double y, double z) {
        Entity beacon = this.returnBeaconAtLocation (x + 0.5, y + 0.5, z + 0.5);
        if (beacon != null) {
            ItemDisplay itemDisplay = (ItemDisplay) beacon;
            ItemStack stack = itemDisplay.getItemStack();
            ItemMeta meta = stack.getItemMeta();

            if (meta != null) {
                
                NamespacedKey key2 = new NamespacedKey(plugin, metadata);
                meta.getPersistentDataContainer().set(key2, PersistentDataType.STRING, name); 

                stack.setItemMeta(meta);
                itemDisplay.setItemStack(stack);
            }
        }
    }

    /**
     * Stores a beacon for later reference in the beacon.yml file
     *
     * @param beaconName The exact name of the beacon
     * @param x The beacons x coordinate
     * @param y The beacons y coordinate
     * @param z The beacons z coordinate
     * 
     */
    public void storeBeaconInFile (String beaconName, double x, double y, double z) {
        File file = new File(plugin.getDataFolder(), "beacons.yml");
        if (!file.exists()) {
            plugin.getLogger().warning("beacons.yml does not exist!");
            return;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set("beacons." + beaconName + ".x", x);
        config.set("beacons." + beaconName + ".y", y);
        config.set("beacons." + beaconName + ".z", z);

        try {
            config.save(file);
            plugin.getLogger().info("Saved beacon " + beaconName + " to beacons.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class BeaconItem {
        private double x;
        private double y;
        private double z;
        private String name;

        public BeaconItem (double x, double y, double z, String name) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.name = name;
        }

        public double getX() {return this.x;}
        public double getY() {return this.y;}
        public double getZ() {return this.z;}
        public String getName() {return this.name;}

    }

    public class BeaconType {
        private String name;
        private int type;
        private static List<BeaconType> beaconTypes;

        public BeaconType (String name, int type) {
            this.name = name;
            this.type = type;
        }

        public BeaconType () {beaconTypes = retrieveAllBeaconTypes();}

        public int getType () {return this.type;}
        public String getName () {return this.name;}

        public List<String> getAllBeaconTypeNames () {
            List<String> allBeaconTypes = new ArrayList<String> ();
            for (BeaconType type : beaconTypes) {allBeaconTypes.add(type.getName());}

            return allBeaconTypes;
        }

        public int getTypeByBeaconTypeName (String name) {
            for (BeaconType type : beaconTypes) {
                System.out.println(type.getName() + " : " + name);
                if (type.getName().contains(name)) {
                    System.out.println("here");
                    return type.getType();
                }
            }
            return 0;
        }

        public List<BeaconType> getBeaconTypes () {return beaconTypes;}

        private List<BeaconType> retrieveAllBeaconTypes() {
            FileManager file = new FileManager(plugin, "beacons");
            YamlConfiguration config = file.returnConfig();

            ConfigurationSection section = config.getConfigurationSection("beaconTypes");
            List<BeaconType> beaconTypes = new ArrayList<>();

            if (section != null) {
                for (String name : section.getKeys(false)) {
                    int beaconTypeNumber = section.getInt(name + ".type");
                    System.out.println(name + " -> " + beaconTypeNumber);
                    beaconTypes.add(new BeaconType(name, beaconTypeNumber));
                }
            }

            return beaconTypes;
        }
    }

    /**
     * Retrieves the x,y, and z for a registered beacon in the beacon.yml file
     *
     * @param beaconName The exact name of the beacon
     * @return Returns a beacon item with the data from the yml file for that beacon
     * 
     */
    public BeaconItem retrieveBeaconByNameFromFile (String beaconName) {
        File file = new File(plugin.getDataFolder(), "beacons.yml");

        if (!file.exists()) {
            plugin.getLogger().warning("beacons.yml does not exist!");
            return null;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        String path = "beacons." + beaconName;
        if (!config.contains(path)) {return null;}

        double x = config.getDouble(path + ".x");
        double y = config.getDouble(path + ".y");
        double z = config.getDouble(path + ".z");

        BeaconItem beaconItem = this.new BeaconItem (x,y,z,beaconName);
        return beaconItem;
    }

    /**
     * Retrieves the x,y,z and name for all registered beacon in the beacon.yml file
     *
     * @return Returns an arraylist of beacon items with the data from the yml file for all registered beacons
     * 
     */
    public List<BeaconItem> retrieveAllBeaconsFromFile () {
        File file = new File(plugin.getDataFolder(), "beacons.yml");

        if (!file.exists()) {
            plugin.getLogger().warning("beacons.yml does not exist!");
            return null;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (!config.contains("beacons")) {return null;}

        ConfigurationSection section = config.getConfigurationSection("beacons");
        List<BeaconItem> beaconItems = new ArrayList<BeaconItem>();

        for (String name : section.getKeys(false)) {
            String path = "beacons." + name;

            double x = config.getDouble(path + ".x");
            double y = config.getDouble(path + ".y");
            double z = config.getDouble(path + ".z");

            beaconItems.add(this.new BeaconItem(x,y,z,name));
        }

        return beaconItems;
    }

    public Map<String, InformationBar> createBeaconInformationBars (List<BeaconItem> beaconItems) {
        Map<String, InformationBar> beaconInfoBars = new HashMap<>();
        
        for (BeaconItem beacon : beaconItems) {
            beaconInfoBars.put(beacon.getName(), new InformationBar("Concencrating " + beacon.getName() + "..."));
        }


        return beaconInfoBars;
    }

    public String getBeaconMetadata (Entity entity, String metadata) {
        String value = null;
        if (entity instanceof ItemDisplay itemDisplay) {
            ItemStack stack = itemDisplay.getItemStack();
            ItemMeta meta = stack.getItemMeta();
            NamespacedKey key = new NamespacedKey(plugin, metadata);

            if (key != null) {
                value = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
            }
            
        }
        return value;
    }

    public String getBeaconMetadata (ItemStack stack, String metadata) {
        String value = null;
        
        ItemMeta meta = stack.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin, metadata);

        if (key != null) {
            value = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        }
            
        
        return value;
    }
    
    public ItemStack checkIfBeaconNearby(Player player, int radius) {
        for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
            if (entity instanceof ItemDisplay display) {
                ItemStack item = display.getItemStack();

                if (item != null && item.getType() == Material.CARVED_PUMPKIN) {return item;}
            }
        }
        player.sendMessage("Beacon not nearby");
        return null;
    }

    
}

