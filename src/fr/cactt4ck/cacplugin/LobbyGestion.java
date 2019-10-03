package fr.cactt4ck.cacplugin;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("all")
public class LobbyGestion implements Listener{

    private Inventory compInv = Bukkit.createInventory(null, 9, "World Selector");

    private ItemStack overworld = new ItemStack(Material.GRASS);
    private ItemStack nether = new ItemStack(Material.NETHERRACK);
    private ItemStack end = new ItemStack(Material.END_STONE);

    private ItemMeta overworldMeta = overworld.getItemMeta();
    private ItemMeta netherMeta = nether.getItemMeta();
    private ItemMeta endMeta = end.getItemMeta();

    @EventHandler
    private void onCompassRightClick(PlayerInteractEvent e){
        Player p = e.getPlayer();

        ItemStack compassItem = compass();

        overworldMeta.setDisplayName(ChatColor.GREEN + "Overworld");
        netherMeta.setDisplayName(ChatColor.RED + "Nether");
        endMeta.setDisplayName(ChatColor.YELLOW + "End");

        overworld.setItemMeta(overworldMeta);
        nether.setItemMeta(netherMeta);
        end.setItemMeta(endMeta);

        compInv.setItem(2, overworld);
        compInv.setItem(4, nether);
        compInv.setItem(6, end);


        if(p.getInventory().getItemInMainHand().equals(compassItem)){
            if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR){
                p.openInventory(compInv);
            }
        }
    }

    @EventHandler
    private void compInvClicked(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        ItemStack clicked = e.getCurrentItem();
        Inventory inventory = e.getInventory();

        World w = Bukkit.getServer().getWorld("world");
        World wn = Bukkit.getServer().getWorld("world_nether");
        World wte = Bukkit.getServer().getWorld("world_the_end");

        Material grass = Material.getMaterial(String.valueOf(overworld.getType()));
        Material netherrack = Material.getMaterial(String.valueOf(nether.getType()));
        Material enderstone = Material.getMaterial(String.valueOf(end.getType()));

        if (inventory.getType() == compInv.getType()) {
            if (clicked.getType() == grass) {
                e.setCancelled(true);
                p.closeInventory();
                p.sendMessage("Téléportation dans l'Overworld");
                p.teleport(w.getSpawnLocation());
            }else if(clicked.getType() == netherrack){
                e.setCancelled(true);
                p.closeInventory();
                p.sendMessage("Téléportation dans le Nether");
                p.teleport(wn.getSpawnLocation());
            }else if(clicked.getType() == enderstone){
                e.setCancelled(true);
                p.closeInventory();
                p.sendMessage("Téléportation dans l'End");
                p.teleport(wte.getSpawnLocation());
            }
        }
    }

    public static ItemStack compass(){

        ItemStack compassItem = new ItemStack(Material.COMPASS);
        ItemMeta compassMeta = compassItem.getItemMeta();

        compassMeta.setDisplayName(ChatColor.GOLD + "Lobby Selector");
        compassMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        compassItem.setItemMeta(compassMeta);

        return compassItem;
    }
}
