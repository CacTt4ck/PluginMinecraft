package fr.cactt4ck.cacplugin;

import org.bukkit.GameMode;
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
import java.util.HashMap;

public class AutoSmelt extends Enchantment implements Listener {

    HashMap<Material, Material> blockList = new HashMap<Material, Material>();

    public AutoSmelt(int id) {
        super(id);
        blockList.put(Material.IRON_ORE, Material.IRON_INGOT);
        blockList.put(Material.GOLD_ORE, Material.GOLD_INGOT);
        blockList.put(Material.STONE, Material.STONE);
        blockList.put(Material.COBBLESTONE, Material.STONE);
        blockList.put(Material.SAND, Material.GLASS);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event){
        if(event.getPlayer() instanceof Player) {
            Player p = event.getPlayer();
            ItemStack playerHand = p.getInventory().getItemInMainHand();
            Block block = event.getBlock();
            Location loc = new Location(block.getWorld(), block.getX(), block.getY() + 0.5, block.getZ());

            for (Material raw:
                 blockList.keySet()) {
                if(playerHand.containsEnchantment(this) && block.getType() == raw && p.getGameMode() != GameMode.CREATIVE){
                    block.getLocation().getWorld().dropItemNaturally(loc, new ItemStack(blockList.get(raw)));
                    block.getLocation().getWorld().spawnParticle(Particle.FLAME, loc, 3);
                    block.setType(Material.AIR);
                    playerHand.setDurability((short) (playerHand.getDurability()+1));
                }
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
