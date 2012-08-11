package com.lavacraftserver.HarryPotterSpells.Spells;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import org.bukkit.entity.Player;

import com.lavacraftserver.HarryPotterSpells.HarryPotterSpells;

public abstract class Spell {
	protected HarryPotterSpells plugin;
	public Spell(HarryPotterSpells instance){
		plugin=instance;
	}
public abstract void cast(Player p);

public void teach(Player sender,Player target){
	if(target != null) {
		if(playerKnows(target)) {
			plugin.PM.warn(sender, target.getName() + " already knows that spell!");
		} else {
			teach(target);
			plugin.PM.tell(sender, "You have taught " + target.getName() + " the spell " + toString() + ".");
		}
	} else {
		plugin.PM.warn(sender, "The player was not found.");
	}
}

public void teach(Player p){
	List<String> list = plugin.PlayerSpellConfig.getPSC().getStringList(p.getName());
	list.add(toString());
	plugin.PlayerSpellConfig.getPSC().set(p.getName(), list);
	plugin.PlayerSpellConfig.savePSC();
	plugin.PM.tell(p, "You have been taught " + toString());
}

public boolean playerKnows(Player p){
	List<String> list = plugin.PlayerSpellConfig.getPSC().getStringList(p.getName());
	if(list.contains(toString())) {
		return true;
	} else {
		return false;
	}
}

public void unTeach(Player p){
	List<String> list = plugin.PlayerSpellConfig.getPSC().getStringList(p.getName());
	list.remove(toString());
	plugin.PlayerSpellConfig.getPSC().set(p.getName(), list);
	plugin.PlayerSpellConfig.savePSC();
	plugin.PM.tell(p, "You have forgotten " + toString());
}

public String toString(){
	return this.getClass().getSimpleName();
}

@Retention(RetentionPolicy.RUNTIME) @interface spell{ //not in use as of yet
	boolean chat() default false;
	boolean wand() default true;
	String description() default "A mysterious spell";
}
}