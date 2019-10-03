package fr.cactt4ck.cacplugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class Furnace implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {

        Player p = (Player)s;
        if(cmd.getName().equalsIgnoreCase("furnace")){
            if(args.length != 0){
                p.sendMessage(ChatColor.RED + "Faites /furnace");
            }else {
                ItemStack iih = p.getInventory().getItemInMainHand();
                int numberOfItems = iih.getAmount();

                if(iih.getType() == Material.IRON_ORE){
                    iih.setType(Material.IRON_INGOT);
                }else if(iih.getType() == Material.GOLD_ORE){
                    iih.setType(Material.GOLD_INGOT);
                }else if(iih.getType() == Material.BEEF) {
                    iih.setType(Material.COOKED_BEEF);
                }else if(iih.getType() == Material.CHICKEN){
                    iih.setType(Material.COOKED_CHICKEN);
                }else if(iih.getType() == Material.COD) {
                    iih.setType(Material.COOKED_COD);
                }else if(iih.getType() == Material.SALMON){
                    iih.setType(Material.COOKED_SALMON);
                }else if(iih.getType() == Material.MUTTON){
                    iih.setType(Material.COOKED_MUTTON);
                }else if(iih.getType() == Material.RABBIT){
                    iih.setType(Material.COOKED_RABBIT);
                }else if(iih.getType() == Material.PORKCHOP){
                    iih.setType(Material.COOKED_PORKCHOP);
                }else if(iih.getType() == Material.POTATO){
                    iih.setType(Material.BAKED_POTATO);
                }else if(iih.getType() == Material.OAK_LOG){
                    iih.setType(Material.CHARCOAL);
                }else if(iih.getType() == Material.ACACIA_LOG){
                    iih.setType(Material.CHARCOAL);
                }else if(iih.getType() == Material.BIRCH_LOG){
                    iih.setType(Material.CHARCOAL);
                }else if(iih.getType() == Material.DARK_OAK_LOG){
                    iih.setType(Material.CHARCOAL);
                }else if(iih.getType() == Material.JUNGLE_LOG){
                    iih.setType(Material.CHARCOAL);
                }else if(iih.getType() == Material.SPRUCE_LOG){
                    iih.setType(Material.CHARCOAL);
                }else if(iih.getType() == Material.COBBLESTONE){
                    iih.setType(Material.STONE);
                }else if(iih.getType() == Material.SAND){
                    iih.setType(Material.GLASS);
                }else if(iih.getType() == Material.COBBLESTONE){
                    iih.setType(Material.STONE);
                }else if(iih.getType() == Material.NETHERRACK){
                    iih.setType(Material.NETHER_BRICK);
                }else if(iih.getType() == Material.CLAY_BALL){
                    iih.setType(Material.BRICK);
                }else{
                    p.sendMessage(ChatColor.RED + "Cet item ne peut pas être cuit!");
                    return true;
                }
                p.giveExp(numberOfItems);
                p.sendMessage(ChatColor.GREEN + "Le contenu de votre main a été cuit!");
            }
        }
        return true;
    }
}
