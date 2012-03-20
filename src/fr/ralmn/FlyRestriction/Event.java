package fr.ralmn.FlyRestriction;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Event implements Listener {

	Main plugin;

	public Event(Main m) {
		plugin = m;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerSpeedHack(PlayerMoveEvent e) {
		Player pl = e.getPlayer();

		Location from = new Location(e.getFrom().getWorld(),
				e.getFrom().getX(), 0, e.getFrom().getZ());
		Location to = new Location(e.getTo().getWorld(), e.getTo().getX(), 0, e
				.getTo().getZ());
		

		if (!(pl.getGameMode() == GameMode.CREATIVE)
				&& !plugin.canRun(pl.getName())) {
			double speed = from.distance(to) * 5;
			if ((int) speed > plugin.getConfig().getInt("settings.runmax")) {
				pl.kickPlayer(ChatColor.RED
						+ plugin.getConfig().getString("message.kick.run"));
			}
		}

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player pl = e.getPlayer();

		boolean a = plugin.canFly(pl.getName());

		pl.setAllowFlight(a);

		if (a)
			plugin.log.info(pl.getName() + " peut voler");
		else
			plugin.log.info(pl.getName() + " ne peux pas voler");

	}

}
