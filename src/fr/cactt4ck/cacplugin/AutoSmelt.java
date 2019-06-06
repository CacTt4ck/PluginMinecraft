package fr.cactt4ck.cacplugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class AutoSmelt extends Enchantment implements Listener {

    private ItemStack iron_ingot = new ItemStack(Material.IRON_INGOT);
    ArrayList<Material> oreList = new ArrayList<Material>();
    ArrayList<ItemStack> ingotList = new ArrayList<ItemStack>();

    public AutoSmelt(int id) {
        super(id);
        oreList.add(Material.IRON_ORE);
        oreList.add(Material.GOLD_ORE);

        ingotList.add(new ItemStack(Material.IRON_INGOT));
        ingotList.add(new ItemStack(Material.GOLD_INGOT));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(event.getPlayer() instanceof Player) {
            Player p = event.getPlayer();
            Block block = event.getBlock();
            Location loc = new Location(block.getWorld(), block.getX(), block.getY() + 0.5, block.getZ());

            if(p.getInventory().getItemInMainHand().containsEnchantment(this) && block.getType() == oreList.get(0)){
                event.getBlock().getLocation().getWorld().dropItemNaturally(loc, ingotList.get(0));
                event.setCancelled(true);
                event.getBlock().setType(Material.AIR);

            }else if(p.getInventory().getItemInMainHand().containsEnchantment(this) && block.getType() == oreList.get(1)){
                event.getBlock().getLocation().getWorld().dropItemNaturally(loc, ingotList.get(1));
                event.setCancelled(true);
                event.getBlock().setType(Material.AIR);
            }
        }
    }

    @Override
    public int getId(){
        return 555;
    }

    @Override
    public String getName() {
        return "Auto-Smelt";
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.TOOL;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return false;
    }
}
