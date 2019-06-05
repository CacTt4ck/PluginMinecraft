package fr.cactt4ck.cacplugin;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@SuppressWarnings("all")
public class KillerMoney implements Listener {
	
	@EventHandler
	private void onPlayerKillAMob(EntityDeathEvent e) {
		
		final LivingEntity monster = e.getEntity();
		
		if (!(monster.getKiller() instanceof Player))
			return;
		
		final Player p = (Player)monster.getKiller();
		
		int randomMaxValue = 10, randomMinValue = 1;
		final String entityName;
		boolean entityGenderF = false, isPlayer = false;


		
		
		switch (monster.getType()) {
		
		case ZOMBIE :
			entityName = "Zombie";
			randomMaxValue = CacPlugin.config.getInt("killer-money.zombie.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.zombie.min");
			break;
		case CREEPER :
			entityName = "Creeper";
			randomMaxValue = CacPlugin.config.getInt("killer-money.creeper.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.creeper.min");
			break;
		case SKELETON :
			entityName = "Squelette";
			randomMaxValue = CacPlugin.config.getInt("killer-money.skeleton.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.skeleton.min");
			break;
		case ENDER_DRAGON :
			entityName = "Ender Dragon";
			randomMaxValue = CacPlugin.config.getInt("killer-money.ender-dragon.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.ender-dragon.min");
			break;
		case ENDERMAN :
			entityName = "Enderman";
			randomMaxValue = CacPlugin.config.getInt("killer-money.enderman.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.enderman.min");
			break;
		case BLAZE :
			entityName = "Blaze";
			randomMaxValue = CacPlugin.config.getInt("killer-money.blaze.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.blaze.min");
			break;
		case GUARDIAN :
			entityName = "Guardian";
			randomMaxValue = CacPlugin.config.getInt("killer-money.guardian.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.guardian.min");
			break;
		case SHULKER :
			entityName = "Shulker";
			randomMaxValue = CacPlugin.config.getInt("killer-money.shulker.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.shulker.min");
			break;
		case ZOMBIE_VILLAGER :
			entityName = "Zombie Villager";
			randomMaxValue = CacPlugin.config.getInt("killer-money.zombie-villager.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.zombie-villager.min");
			break;
		case MAGMA_CUBE :
			entityName = "Magma Cube";
			randomMaxValue = CacPlugin.config.getInt("killer-money.magmacube.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.magmacube.min");
			break;
		case SLIME :
			entityName = "Slime";
			randomMaxValue = CacPlugin.config.getInt("killer-money.slime.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.slime.min");
			break;
		case SPIDER :
			entityName = "Araignée";
			randomMaxValue = CacPlugin.config.getInt("killer-money.spider.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.spider.min");
			entityGenderF = true;
			break;
		case CAVE_SPIDER :
			entityName = "Araignée Venimeuse";
			randomMaxValue = CacPlugin.config.getInt("killer-money.cavespider.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.cavespider.min");
			entityGenderF = true;
			break;
		case ELDER_GUARDIAN :
			entityName = "Elder Guardian";
			randomMaxValue = CacPlugin.config.getInt("killer-money.elder-guardian.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.elder-guardian.min");
			break;
		case ENDERMITE :
			entityName = "Endermite";
			randomMaxValue = CacPlugin.config.getInt("killer-money.endermite.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.endermite.min");
			entityGenderF = true;
			break;
		case SILVERFISH :
			entityName = "Silverfish";
			randomMaxValue = CacPlugin.config.getInt("killer-money.silverfish.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.silverfish.min");
			entityGenderF = true;
			break;
		case EVOKER :
			entityName = "Evoker";
			randomMaxValue = CacPlugin.config.getInt("killer-money.evoker.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.evoker.min");
			break;
		case GHAST :
			entityName = "Ghast";
			randomMaxValue = CacPlugin.config.getInt("killer-money.ghast.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.ghast.min");
			break;
		case HUSK :
			entityName = "Husk";
			randomMaxValue = CacPlugin.config.getInt("killer-money.husk.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.husk.min");
			break;
		case PIG_ZOMBIE :
			entityName = "Zombie Pigman";
			randomMaxValue = CacPlugin.config.getInt("killer-money.pigzombie.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.pigzombieguardian.min");
			break;
		case STRAY :
			entityName = "Stray";
			randomMaxValue = CacPlugin.config.getInt("killer-money.stray.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.stray.min");
			break;
		case VEX :
			entityName = "Vex";
			randomMaxValue = CacPlugin.config.getInt("killer-money.vex.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.vex.min");
			break;
		case VINDICATOR :
			entityName = "Vindicator";
			randomMaxValue = CacPlugin.config.getInt("killer-money.vindicator.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.vindicator.min");
			break;
		case WITHER_SKELETON :
			entityName = "Wither Squelette";
			randomMaxValue = CacPlugin.config.getInt("killer-money.wither-skeleton.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.wither-skeleton.min");
			break;
		case WITHER :
			randomMaxValue = CacPlugin.config.getInt("killer-money.witherboss.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.witherboss.min");
			entityName = "Wither Boss";
			break;
		case WITCH :
			randomMaxValue = CacPlugin.config.getInt("killer-money.witch.max");
			randomMinValue = CacPlugin.config.getInt("killer-money.witch.min");
			entityName = "Sorcière";
			entityGenderF = true;
			break;
		
		default :
			return;
		}
		
		int money = (int) Math.random() * randomMaxValue + randomMinValue;
		Money.giveMoney(p, money);
		p.sendMessage(ChatColor.GREEN + "Vous avez gagné " + money + " $ en tuant " + (isPlayer ? "" : ("un" + (entityGenderF ? "e" : "") + " ")) + entityName + " !");
		
	}
	
}
