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
public class ServerGestion implements Listener {

    private final Inventory clockInv = Bukkit.createInventory(null, 18, "Server Settings");
    private final Inventory WEATHERCHANGER = Bukkit.createInventory(null, 9, "Weather Settings");
    private final Inventory DIFFICULTY = Bukkit.createInventory(null, 9, "Difficulty Settings");
    private final Inventory GAMEMODE = Bukkit.createInventory(null, 9, "Gamemode Settings");



    private ItemStack weatherChanger = new ItemStack(Material.TALL_GRASS);
    private ItemStack setDay = new ItemStack(Material.GLOWSTONE);
    private ItemStack setNight = new ItemStack(Material.REDSTONE_LAMP);
    private ItemStack setDiff = new ItemStack(Material.BONE);
    private ItemStack setSun = new ItemStack(Material.LAVA_BUCKET);
    private ItemStack setRain = new ItemStack(Material.WATER_BUCKET);

    private ItemStack returnToMain = new ItemStack(Material.BARRIER);

    private ItemStack diff0 = new ItemStack(Material.WOODEN_SWORD);
    private ItemStack diff1 = new ItemStack(Material.STONE_SWORD);
    private ItemStack diff2 = new ItemStack(Material.IRON_SWORD);
    private ItemStack diff3 = new ItemStack(Material.DIAMOND_SWORD);

    private ItemStack setgm = new ItemStack(Material.EGG);
    private ItemStack gm0 = new ItemStack(Material.COAL);
    private ItemStack gm1 = new ItemStack(Material.IRON_INGOT);
    private ItemStack gm2 = new ItemStack(Material.GOLD_INGOT);
    private ItemStack gm3 = new ItemStack(Material.DIAMOND);

    private ItemStack reload = new ItemStack(Material.CLOCK);



    private ItemMeta weatherChengerMeta = weatherChanger.getItemMeta();
    private ItemMeta setDiffMeta = setDiff.getItemMeta();
    private ItemMeta setgmMeta = setgm.getItemMeta();

    private ItemMeta reloadMeta = reload.getItemMeta();

    private ItemMeta rTMMeta = returnToMain.getItemMeta();

    private ItemMeta setDayMeta = setDay.getItemMeta();
    private ItemMeta setNightMeta = setNight.getItemMeta();
    private ItemMeta setSunMeta = setSun.getItemMeta();
    private ItemMeta setRainMeta = setRain.getItemMeta();

    private ItemMeta diff0Meta = diff0.getItemMeta();
    private ItemMeta diff1Meta = diff1.getItemMeta();
    private ItemMeta diff2Meta = diff2.getItemMeta();
    private ItemMeta diff3Meta = diff3.getItemMeta();

    private ItemMeta gm0Meta = gm0.getItemMeta();
    private ItemMeta gm1Meta = gm1.getItemMeta();
    private ItemMeta gm2Meta = gm2.getItemMeta();
    private ItemMeta gm3Meta = gm3.getItemMeta();


    @EventHandler
    private void onCompassRightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        ItemStack clockItem = clock();

        weatherChengerMeta.setDisplayName(ChatColor.AQUA + "Weather");
        weatherChanger.setItemMeta(weatherChengerMeta);
        clockInv.setItem(0, weatherChanger);

        setDiffMeta.setDisplayName(ChatColor.GRAY + "Difficulty");
        setDiff.setItemMeta(setDiffMeta);
        clockInv.setItem(1, setDiff);

        setgmMeta.setDisplayName(ChatColor.GRAY + "Gamemode");
        setgm.setItemMeta(setgmMeta);
        clockInv.setItem(2, setgm);

        reloadMeta.setDisplayName(ChatColor.GREEN + "Reload");
        reload.setItemMeta(reloadMeta);
        clockInv.setItem(17, reload);



        rTMMeta.setDisplayName(ChatColor.RED + "Return");
        returnToMain.setItemMeta(rTMMeta);
        WEATHERCHANGER.setItem(8, returnToMain);
        DIFFICULTY.setItem(8, returnToMain);
        GAMEMODE.setItem(8, returnToMain);


        setDayMeta.setDisplayName(ChatColor.YELLOW + "DAY");
        setDay.setItemMeta(setDayMeta);
        WEATHERCHANGER.setItem(0, setDay);

        setNightMeta.setDisplayName(ChatColor.DARK_AQUA + "NIGHT");
        setNight.setItemMeta(setNightMeta);
        WEATHERCHANGER.setItem(2, setNight);

        setSunMeta.setDisplayName(ChatColor.GREEN + "SUN");
        setSun.setItemMeta(setSunMeta);
        setRainMeta.setDisplayName(ChatColor.BLUE + "RAIN");
        setRain.setItemMeta(setRainMeta);
        WEATHERCHANGER.setItem(4, setSun);
        WEATHERCHANGER.setItem(6, setRain);

        gm0Meta.setDisplayName("SURVIVAL");
        gm1Meta.setDisplayName("CREATIVE");
        gm2Meta.setDisplayName("ADVENTURE");
        gm3Meta.setDisplayName("SPECTATOR");
        gm0.setItemMeta(gm0Meta);
        gm1.setItemMeta(gm1Meta);
        gm2.setItemMeta(gm2Meta);
        gm3.setItemMeta(gm3Meta);
        GAMEMODE.setItem(0, gm0);
        GAMEMODE.setItem(2, gm1);
        GAMEMODE.setItem(4, gm2);
        GAMEMODE.setItem(6, gm3);



        diff0Meta.setDisplayName(ChatColor.YELLOW + "PEACEFUL");
        diff0.setItemMeta(diff0Meta);
        DIFFICULTY.setItem(0, diff0);

        diff1Meta.setDisplayName(ChatColor.YELLOW + "EASY");
        diff1.setItemMeta(diff1Meta);
        DIFFICULTY.setItem(2, diff1);

        diff2Meta.setDisplayName(ChatColor.YELLOW + "NORMAL");
        diff2.setItemMeta(diff2Meta);
        DIFFICULTY.setItem(4, diff2);

        diff3Meta.setDisplayName(ChatColor.YELLOW + "HARD");
        diff3.setItemMeta(diff3Meta);
        DIFFICULTY.setItem(6, diff3);

        if(p.getInventory().getItemInMainHand().equals(clockItem)){
            if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR){
                p.openInventory(clockInv);
            }
        }
    }

    @EventHandler
    private void clockInvClicked(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        ItemStack clicked = e.getCurrentItem();
        Inventory inventory = e.getInventory();

        Material weather = Material.getMaterial(String.valueOf(weatherChanger.getType()));
        Material difficulty = Material.getMaterial(String.valueOf(setDiff.getType()));
        Material gm = Material.getMaterial(String.valueOf(setgm.getType()));
        Material rl = Material.getMaterial(String.valueOf(reload.getType()));

        if (inventory.getType() == clockInv.getType()) {
            if (clicked.getType() == weather) {
                e.setCancelled(true);
                p.closeInventory();
                p.openInventory(WEATHERCHANGER);

            }else if (clicked.getType() == difficulty) {
                e.setCancelled(true);
                p.closeInventory();
                p.openInventory(DIFFICULTY);
            }else if (clicked.getType() == gm) {
                e.setCancelled(true);
                p.closeInventory();
                p.openInventory(GAMEMODE);
            }else if (clicked.getType() == rl) {
                e.setCancelled(true);
                p.closeInventory();
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "reload");
            }
        }
    }

    @EventHandler
    private void gamemodeInvClicked(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack clicked = e.getCurrentItem();
        Inventory inventory = e.getInventory();

        Material gamemode0 = Material.getMaterial(String.valueOf(gm0.getType()));
        Material gamemode1 = Material.getMaterial(String.valueOf(gm1.getType()));
        Material gamemode2 = Material.getMaterial(String.valueOf(gm2.getType()));
        Material gamemode3 = Material.getMaterial(String.valueOf(gm3.getType()));
        Material retour = Material.getMaterial(String.valueOf(returnToMain.getType()));

        if (inventory.getType() == GAMEMODE.getType()) {
            if (clicked.getType() == gamemode0) {
                e.setCancelled(true);
                p.closeInventory();
                p.setGameMode(GameMode.SURVIVAL);
            } else if (clicked.getType() == gamemode1) {
                e.setCancelled(true);
                p.closeInventory();
                p.setGameMode(GameMode.CREATIVE);
            } else if (clicked.getType() == gamemode2) {
                e.setCancelled(true);
                p.closeInventory();
                p.setGameMode(GameMode.ADVENTURE);
            } else if (clicked.getType() == gamemode3) {
                e.setCancelled(true);
                p.closeInventory();
                p.setGameMode(GameMode.SPECTATOR);
            }else if(clicked.getType() == retour){
                e.setCancelled(true);
                p.closeInventory();
                p.openInventory(clockInv);
            }
        }
    }


    @EventHandler
    private void weatherInvClicked(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        ItemStack clicked = e.getCurrentItem();
        Inventory inventory = e.getInventory();

        Material day = Material.getMaterial(String.valueOf(setDay.getType()));
        Material night = Material.getMaterial(String.valueOf(setNight.getType()));
        Material retour = Material.getMaterial(String.valueOf(returnToMain.getType()));
        Material sun = Material.getMaterial(String.valueOf(setSun.getType()));
        Material rain = Material.getMaterial(String.valueOf(setRain.getType()));

        if(inventory.getType() == WEATHERCHANGER.getType()){
            if(clicked.getType() == day){
                e.setCancelled(true);
                p.closeInventory();
                Bukkit.getServer().getPlayer(p.getName()).getWorld().setTime(1000L);
            }else if(clicked.getType() == night){
                e.setCancelled(true);
                p.closeInventory();
                Bukkit.getServer().getPlayer(p.getName()).getWorld().setTime(13000L);
            }else if(clicked.getType() == retour){
                e.setCancelled(true);
                p.closeInventory();
                p.openInventory(clockInv);
            }else if(clicked.getType() == sun){
                e.setCancelled(true);
                p.closeInventory();
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "weather clear");
            }else if(clicked.getType() == rain){
                e.setCancelled(true);
                p.closeInventory();
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "weather rain");
            }
        }
    }

    @EventHandler
    private void DifficultyInvClicked(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        ItemStack clicked = e.getCurrentItem();
        Inventory inventory = e.getInventory();

        Material peace = Material.getMaterial(String.valueOf(diff0.getType()));
        Material easy = Material.getMaterial(String.valueOf(diff1.getType()));
        Material normal = Material.getMaterial(String.valueOf(diff2.getType()));
        Material hard = Material.getMaterial(String.valueOf(diff3.getType()));
        Material retour = Material.getMaterial(String.valueOf(returnToMain.getType()));

        World w = p.getWorld();

        if(inventory.getType() == DIFFICULTY.getType()){
            if(clicked.getType() == peace){
                e.setCancelled(true);
                p.closeInventory();
                w.setDifficulty(Difficulty.PEACEFUL);
                p.sendMessage(ChatColor.GRAY + "Difficulté du monde " + ChatColor.LIGHT_PURPLE + w.getName() + ChatColor.GRAY
                        + " définie sur " + ChatColor.RESET + w.getDifficulty());

            }else if(clicked.getType() == easy){
                e.setCancelled(true);
                p.closeInventory();
                w.setDifficulty(Difficulty.EASY);
                p.sendMessage(ChatColor.GRAY + "Difficulté du monde " + ChatColor.LIGHT_PURPLE + w.getName() + ChatColor.GRAY
                        + " définie sur " + ChatColor.RESET + w.getDifficulty());

            }else if(clicked.getType() == normal){
                e.setCancelled(true);
                p.closeInventory();
                w.setDifficulty(Difficulty.NORMAL);
                p.sendMessage(ChatColor.GRAY + "Difficulté du monde " + ChatColor.LIGHT_PURPLE + w.getName() + ChatColor.GRAY
                        + " définie sur " + ChatColor.RESET + w.getDifficulty());

            }else if(clicked.getType() == hard){
                e.setCancelled(true);
                p.closeInventory();
                w.setDifficulty(Difficulty.HARD);
                p.sendMessage(ChatColor.GRAY + "Difficulté du monde " + ChatColor.LIGHT_PURPLE + w.getName() + ChatColor.GRAY
                        + " définie sur " + ChatColor.RESET + w.getDifficulty());
            }else if(clicked.getType() == retour){
                e.setCancelled(true);
                p.closeInventory();
                p.openInventory(clockInv);
            }
        }
    }

    public static ItemStack clock(){

        ItemStack clockItem = new ItemStack(Material.CLOCK);
        ItemMeta clockMeta = clockItem.getItemMeta();

        clockMeta.setDisplayName(ChatColor.GOLD + "Server Settings");
        clockMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        clockItem.setItemMeta(clockMeta);

        return clockItem;
    }

}
