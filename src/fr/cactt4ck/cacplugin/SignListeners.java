package fr.cactt4ck.cacplugin;

import org.bukkit.*;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Random;

import static fr.cactt4ck.cacplugin.CacPlugin.back;

@SuppressWarnings("all")
public class SignListeners implements Listener {
	
	private Inventory poubelle = Bukkit.createInventory(null, 27, "Poubelle");
	
	@EventHandler
	private void onSignChange(SignChangeEvent e) {
		
		final Player p = (Player)e.getPlayer();
		
		if (e.getLine(0).equalsIgnoreCase("[Heal]")) {
			if (!p.isOp()) {
				e.setLine(0, ChatColor.DARK_RED + "[Heal]");
				p.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de créer cette pancarte !");
				return;
			}

			e.setLine(0, ChatColor.DARK_BLUE + "[Heal]");

		} else if (e.getLine(0).equalsIgnoreCase("[Feed]")) {
			
			if (!p.isOp()) {
				e.setLine(0, ChatColor.DARK_RED + "[Feed]");
				p.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de créer cette pancarte !");
				return;
			}
			
			e.setLine(0, ChatColor.DARK_BLUE + "[Feed]");
		
		} else if (e.getLine(0).equalsIgnoreCase("[Buy]")) {
			
			if (!p.isOp()) {
				e.setLine(0, ChatColor.DARK_RED + "[Buy]");
				p.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de créer cette pancarte !");
				return;
			}

			if(e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation().add(0.0,-1.0,0.0)).getType() == Material.CHEST){
                p.sendMessage("CHEST UNDER ME!");
            }
			final int number = this.getTradeNumber(e.getLine(1)), price = this.getTradePrice(e.getLine(3));
			final ItemStack item = this.getTradeItem(e.getLine(2));
			
			if (number == -1 || item == null || price == -1) {
				e.getPlayer().sendMessage(ChatColor.RED + "Erreur lors de la création du panneau shop !");
				e.setLine(0, ChatColor.DARK_RED + "[Buy]");
				e.setLine(1, ChatColor.DARK_RED + "Nombre d'items");
				e.setLine(2, ChatColor.DARK_RED + "Nom / ID item");
				e.setLine(3, ChatColor.DARK_RED + "Prix (entier)");
			} else {
				e.setLine(0, ChatColor.DARK_BLUE + "[Buy]");
				e.setLine(1, ChatColor.BLACK + String.valueOf(number));
				e.setLine(2, ChatColor.BLACK + item.getType().toString().toLowerCase() + (item.getDurability() == 0 ? "" : ":" + String.valueOf(item.getDurability())));
				e.setLine(3, ChatColor.BLACK + String.valueOf(price) + " $");
				e.getPlayer().sendMessage(ChatColor.BLUE + "Panneau créé avec succès !");
			}
			
		} else if (e.getLine(0).equalsIgnoreCase("[Sell]")) {
			
			if (!p.isOp()) {
				e.setLine(0, ChatColor.DARK_RED + "[Sell]");
				p.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de créer cette pancarte !");
				return;
			}
			
			final int number = this.getTradeNumber(e.getLine(1)), price = this.getTradePrice(e.getLine(3));
			final ItemStack item = this.getTradeItem(e.getLine(2));
			
			if (number == -1 || item == null || price == -1) {
				e.getPlayer().sendMessage(ChatColor.RED + "Erreur lors de la création du panneau shop !");
				e.setLine(0, ChatColor.DARK_RED + "[Sell]");
				e.setLine(1, ChatColor.DARK_RED + "Nombre d'items");
				e.setLine(2, ChatColor.DARK_RED + "Nom / ID item");
				e.setLine(3, ChatColor.DARK_RED + "Prix (entier)");
			} else {
				e.setLine(0, ChatColor.DARK_BLUE + "[Sell]");
				e.setLine(1, ChatColor.BLACK + String.valueOf(number));
				e.setLine(2, ChatColor.BLACK + item.getType().toString().toLowerCase() + (item.getDurability() == 0 ? "" : ":" + String.valueOf(item.getDurability())));
				e.setLine(3, ChatColor.BLACK + String.valueOf(price) + " $");
				e.getPlayer().sendMessage(ChatColor.BLUE + "Panneau créé avec succès !");
			}
			
		} else if (e.getLine(0).equalsIgnoreCase("[Trash]")) {
			
			if (!p.isOp()) {
				e.setLine(0, ChatColor.DARK_RED + "[Trash]");
				p.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de créer cette pancarte !");
				return;
			}
			
			e.setLine(0, ChatColor.DARK_BLUE + "[Trash]");
			
		}else if(e.getLine(0).equalsIgnoreCase("[RandomTP]")){
			if (!p.isOp()) {
				e.setLine(0, ChatColor.DARK_RED + "[RandomTP]");
				p.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de créer cette pancarte !");
				return;
			}else if(p.isOp()){
				e.setLine(0, ChatColor.GREEN + "[RandomTP]");
			}
		} else if(e.getLine(0).equalsIgnoreCase("[TP]")){
			if(!p.isOp()){
				e.setLine(0, ChatColor.DARK_RED + "[TP]");
				p.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de créer cette pancarte !");
			}else if(p.isOp()){
				e.setLine(0, ChatColor.GREEN + "[TP]");
			}
		}else if(e.getLine(0).equalsIgnoreCase("[cmd]")){
			if(!p.isOp()){
				e.setLine(0, ChatColor.DARK_RED + "[cmd]");
				p.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de créer cette pancarte !");
			}else if(p.isOp()){
				e.setLine(0, ChatColor.DARK_BLUE + "[CMD]");
			}
		}
	}
	
	@EventHandler
	private void onPlayerInteract(PlayerInteractEvent e) {

		final Player p = e.getPlayer();
		if(p.isOp() || !p.isOp()){
			if ((e.getAction() == Action.RIGHT_CLICK_BLOCK)) {

				if (e.getClickedBlock().getState() instanceof Sign) {
					final Sign sign = (Sign)e.getClickedBlock().getState();

					if (sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_BLUE + "[Heal]")) {
						p.setHealth(20);
						p.sendMessage(ChatColor.DARK_PURPLE + "Vous avez été soigné !");

					}else if(sign.getLine(0).equalsIgnoreCase(ChatColor.GREEN + "[TP]")){
						double x,y,z;
						x = Double.valueOf(sign.getLine(1));
						y = Double.valueOf(sign.getLine(2));
						z = Double.valueOf(sign.getLine(3));
						World world = p.getWorld();
						final Location dest = new Location(world, x,y,z);
						back.put(p.getName(), p.getLocation());
						p.teleport(dest);
						p.sendMessage(ChatColor.GREEN + "Téléporté en " + String.valueOf(x) + " " + String.valueOf(y) + " " + String.valueOf(z));

					}else if(sign.getLine(0).equalsIgnoreCase(ChatColor.GREEN + "[RandomTP]")){

						Random ran1 = new Random();
						Random ran2 = new Random();

						int x = ran1.nextInt(20000) + 1000;
						int z = ran2.nextInt(20000) + 1000;
						int y = 128;
						int signex = 0 + (int)(Math.random() * ((1 - 0) + 1));
						int signez = 0 + (int)(Math.random() * ((1 - 0) + 1));
						if(signex == 1){
							x = x;
						}else if(signex == 0){
							x = -x;
						}
						if(signez == 1){
							z = z;
						}else if(signez == 0){
							z = -z;
						}
						World world = p.getWorld();
						Location l = new Location(world, x, y, z);
						p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 127, false, false));
						p.teleport(l);
						p.sendMessage("Vous avez été téléporté en " + x + "  " + y + "  " + z);

					}else if (sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_BLUE + "[Feed]")) {

						p.setFoodLevel(20);
						p.setSaturation(20);
						p.sendMessage(ChatColor.DARK_PURPLE + "Vous êtes rassasié !");

					} else if (sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_BLUE + "[Buy]")) {

						final int number = this.getTradeNumber(sign.getLine(1)), price = this.getTradePrice(sign.getLine(3).replace(" $", ""));
						final ItemStack item = this.getTradeItem(sign.getLine(2));

						if (number == -1 || item == null || price == -1)
							p.sendMessage(ChatColor.RED + "Erreur lors de l'achat de l'item ! Il se peut que la pancarte shop soit corrompue !");
						else {
							item.setAmount(number);
							try {
								Money.takeMoney(p, price);
							} catch (NotEnoughMoneyException ex) {
								p.sendMessage(ChatColor.RED + "Vous n'avez pas assez d'argent pour acheter cet article !");
								return;
							}
							final HashMap<Integer, ItemStack> itemsLeftMap = p.getInventory().addItem(item);
							if (itemsLeftMap.size() > 0) {
								for (ItemStack itemLeft : itemsLeftMap.values())
									p.getWorld().dropItem(p.getLocation(), itemLeft);
							}
							p.sendMessage(ChatColor.GREEN + "Achat effectuée !" + ChatColor.RED + " (-" + price + "$)");
						}

					} else if (sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_BLUE + "[Sell]")) {

						final int number = this.getTradeNumber(sign.getLine(1)), price = this.getTradePrice(sign.getLine(3).replace(" $", ""));
						final ItemStack item = this.getTradeItem(sign.getLine(2));

						if (number == -1 || item == null || price == -1)
							p.sendMessage(ChatColor.RED + "Erreur lors de la vente de l'item ! Il se peut que la pancarte shop soit corrompue !");
						else {
							boolean playerGotItem = false;
							final PlayerInventory inventory = p.getInventory();
							for (int i = number; i <= 64; i++) {
								item.setAmount(i);
								if (inventory.contains(item)) {
									playerGotItem = true;
									break;
								}
							}
							item.setAmount(number);
							if (!playerGotItem) {
								p.sendMessage(ChatColor.RED + "Vous ne pouvez pas vendre un article que vous ne possédez pas !");
								return;
							}
							inventory.removeItem(item);
							Money.giveMoney(p, price);
							p.sendMessage(ChatColor.GREEN + "Vente effectuée ! " + ChatColor.BLUE + "(+" + price + "$)");
						}

					}else if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_BLUE + "[Trash]")) {

						p.openInventory(poubelle);
						this.resetInventory();

					}else if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_BLUE + "[cmd]")){
						final String cmd = sign.getLine(1);
						Bukkit.getServer().dispatchCommand(p,cmd);
					}
				}
			}
		}
	}
	
	private void resetInventory() {
		if (poubelle.getContents() != new ItemStack[]{})
			poubelle.clear();
	}
	
	
	
	
	
	private int getTradeNumber(final String numberLine) {
		int number = -1;
		
		try {
			number = Integer.valueOf(numberLine);
		} catch(NumberFormatException ex) {
			number = -1;
		}
		
		if (number <= 0 || number > 64)
			return -1;
		
		return number;
	}
	
	private ItemStack getTradeItem(final String itemLine) {
		int itemID = -1;
		short itemMeta = 0;
		final String[] splittedItemName = itemLine.replace("minecraft:", "").split(":");
		
		try {
			itemID = Integer.valueOf(splittedItemName[0]);
		} catch (NumberFormatException ex) {
			itemID = -1;
		}
		
		if (splittedItemName.length > 1) {
			try {
				itemMeta = Short.valueOf(splittedItemName[1]);
			} catch (NumberFormatException ex) {
				itemMeta = 0;
			}
		}

		@Deprecated
		Material material = null;
		
		if (itemID == -1)
			material = Material.matchMaterial(splittedItemName[0]);
		else
			material = Material.getMaterial(itemID);
		
		if (material == null)
			return null;
		
		ItemStack item = new ItemStack(material);
		
		if (itemMeta > 0)
			item.setDurability(itemMeta);
		
		return item;
	}
	
	private int getTradePrice(final String priceLine) {
		int price = -1;
		
		try {
			price = Integer.valueOf(priceLine);
		} catch(NumberFormatException ex) {
			price = -1;
		}
		
		if (price < 0)
			return -1;
		
		return price;
	}
	
}
