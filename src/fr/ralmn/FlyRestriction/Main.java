package fr.ralmn.FlyRestriction;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import api.ralmn.Logger.Log;

public class Main extends JavaPlugin {

	Log log;
	
	@Override
	public void onDisable() {
		
		log.initFrame();
		log.addline("Desactiver");
		log.closeFrame();
		
	}

	@Override
	public void onEnable() {
		
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new Event(this), this);
		
		if(!getConfig().isSet("message.kick.run"))
			getConfig().set("message.kick.run", "You can't use the speed Hack");
		
		if(!getConfig().isSet("settings.runmax"))
			getConfig().set("settings.runmax", 3);
		
		saveConfig();
		
		log = new Log(this);
		log.initFrame();
		log.addline("Activer");
		log.addline("Max speed : " + getConfig().getInt("settings.runmax"));
		log.addline("Developed by Ralmn - Free-world.fr.cr");
		log.closeFrame();	
		
	}
	
	public void initOnReload(){
		if(getServer().getOnlinePlayers().length > 0){
			Player[] pls = getServer().getOnlinePlayers();
			for(int i = 0; i < pls.length; i++){
				initConfig(pls[i].getName());
				
				pls[i].setAllowFlight(canFly(pls[i].getName()));
			}
			
		}
	}
	
	public void initConfig(String name){
		
		if(!getConfig().isSet("user." + name)){
			getConfig().set("user." + name + ".fly", false);
			getConfig().set("user." + name + ".run", false);
			saveConfig();
		}
		
	}
	
	public boolean canFly(String name){
		
		initConfig(name);
		
		return getConfig().getBoolean("user." + name + ".fly");
		
	}
	
public boolean canRun(String name){
		
		initConfig(name);
		
		return getConfig().getBoolean("user." + name + ".run");
		
	}
}
