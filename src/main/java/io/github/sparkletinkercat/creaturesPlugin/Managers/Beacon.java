
package io.github.sparkletinkercat.creaturesPlugin.Managers;


public class Beacon {
    
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