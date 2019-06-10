package fr.cactt4ck.cacplugin;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Kits {

    kit;

    static private ItemStack[] firstConnexionKit = new ItemStack[]{
            new ItemStack(Material.IRON_HELMET, 1),
            new ItemStack(Material.IRON_CHESTPLATE, 1),
            new ItemStack(Material.IRON_LEGGINGS, 1),
            new ItemStack(Material.IRON_BOOTS, 1),
            new ItemStack(Material.IRON_PICKAXE, 1),
            new ItemStack(Material.COOKED_BEEF, 64)
    };

    Kits(){

    }

    static ItemStack[] getKitWithNumber(int kitNumber){
        switch (kitNumber){
            case 1:
                return firstConnexionKit;
            default:
                return null;
        }
    }

}
