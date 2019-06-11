package fr.cactt4ck.cacplugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class AutoSmelt extends Enchantment implements Listener {

    private ItemStack iron_ingot = new ItemStack(Material.IRON_INGOT);
    ArrayList<Material> rawBlockList = new ArrayList<Material>();
    ArrayList<ItemStack> processedBlockList = new ArrayList<ItemStack>();

    public AutoSmelt(int id) {
        super(id);
        rawBlockList.add(Material.IRON_ORE);
        rawBlockList.add(Material.GOLD_ORE);
        rawBlockList.add(Material.STONE);
        rawBlockList.add(Material.COBBLESTONE);

        processedBlockList.add(new ItemStack(Material.IRON_INGOT));
        processedBlockList.add(new ItemStack(Material.GOLD_INGOT));
        processedBlockList.add(new ItemStack(Material.STONE));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event){
        if(event.getPlayer() instanceof Player) {
            Player p = event.getPlayer();
            Block block = event.getBlock();
            Location loc = new Location(block.getWorld(), block.getX(), block.getY() + 0.5, block.getZ());

            if(p.getInventory().getItemInMainHand().containsEnchantment(this) && block.getType() == rawBlockList.get(0)){
                block.getLocation().getWorld().dropItemNaturally(loc, processedBlockList.get(0));
                block.getLocation().getWorld().spawnParticle(Particle.FLAME, loc, 3);
                event.setCancelled(true);
                block.setType(Material.AIR);

            }else if(p.getInventory().getItemInMainHand().containsEnchantment(this) && block.getType() == rawBlockList.get(1)){
                block.getLocation().getWorld().dropItemNaturally(loc, processedBlockList.get(1));
                block.getLocation().getWorld().spawnParticle(Particle.FLAME, loc, 3);
                event.setCancelled(true);
                block.setType(Material.AIR);

            }else if(p.getInventory().getItemInMainHand().containsEnchantment(this) && block.getType() == rawBlockList.get(2)){
                block.getLocation().getWorld().dropItemNaturally(loc, processedBlockList.get(2));
                block.getLocation().getWorld().spawnParticle(Particle.FLAME, loc, 3);
                event.setCancelled(true);
                block.setType(Material.AIR);
            }else if(p.getInventory().getItemInMainHand().containsEnchantment(this) && block.getType() == rawBlockList.get(3)){
                block.getLocation().getWorld().dropItemNaturally(loc, processedBlockList.get(2));
                block.getLocation().getWorld().spawnParticle(Particle.FLAME, loc, 3);
                event.setCancelled(true);
                block.setType(Material.AIR);
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
