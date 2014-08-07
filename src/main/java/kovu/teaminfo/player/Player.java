package kovu.teaminfo.player;

public class Player
{
	private boolean showgui;
	private boolean isPlayerDraggable;
	private boolean isPlayerPoisoned;
	private boolean isPlayerFireResistant;
	private boolean isPlayerWeak;
	private boolean isPlayerSwift;
	private boolean isPlayerSlow;
	private boolean isPlayerRegenning;
	private boolean isPlayerDraingingHunger;
	private boolean isPlayerOnFire;
	
	private Integer playerHealth;
	private Integer playerPreviousHealth;
	private Integer playerHunger;
	private Integer playerArmour;
	private Integer playerX;
	private Integer playerY;
	private Integer playerZ;
	private Integer topLeftI;
	private Integer topLeftJ;
	
	private String username;
	
	public Player(String username)
	{
		this.username = username;
	}
	
	public boolean isPlayerShowing()
	{
		return showgui;
	}

	public boolean isPlayerDraggable()
	{
		return isPlayerDraggable;
	}

	public boolean isPlayerPoisoned()
	{
		return isPlayerPoisoned;
	}
	
	public boolean isPlayerFireResistant()
	{
		return isPlayerFireResistant;
	}
	
	public boolean isPlayerWeak()
	{
		return isPlayerWeak;
	}
	
	public boolean isPlayerSwift()
	{
		return isPlayerSwift;
	}
	
	public boolean isPlayerSlow()
	{
		return isPlayerSlow;
	}
	
	public boolean isPlayerRegenning()
	{
		return isPlayerRegenning;
	}
	
	public boolean isPlayerDrainingHunger()
	{
		return isPlayerDraingingHunger;
	}
	
	public boolean isPlayerOnFire()
	{
		return isPlayerOnFire;
	}
	
	public Integer getPlayerHealth()
	{
		return playerHealth;
	}
	
	public Integer getPlayerPreviousHealth()
	{
		return playerPreviousHealth;
	}

	public Integer getPlayerHunger()
	{
		return playerHunger;
	}

	public Integer getPlayerArmour()
	{
		return playerArmour;
	}

	public Integer getPlayerX()
	{
		return playerX;
	}

	public Integer getPlayerY()
	{
		return playerY;
	}

	public Integer getPlayerZ()
	{
		return playerZ;
	}

	public Integer getPlayerTopLeftI()
	{
		return topLeftI;
	}

	public Integer getPlayerTopLeftJ()
	{
		return topLeftJ;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public void setShowGui(boolean show)
	{
		showgui = show;
	}
	
	public void setPlayerDraggable(boolean draggable)
	{
		isPlayerDraggable = draggable;
	}
	
	public void setPlayerPoisoned(boolean poisoned)
	{
		isPlayerPoisoned = poisoned;
	}
	
	public void setPlayerFireResistant(boolean resistant)
	{
		isPlayerFireResistant = resistant;
	}
	
	public void setPlayerWeak(boolean weak)
	{
		isPlayerWeak = weak;
	}
	
	public void setPlayerSwift(boolean swift)
	{
		isPlayerSwift = swift;
	}

	public void setPlayerSlow(boolean slow)
	{
		isPlayerSlow = slow;
	}
	
	public void setPlayerRegen(boolean regen)
	{
		isPlayerRegenning = regen;
	}
	
	public void setPlayerDrainingHunger(boolean drain)
	{
		isPlayerDraingingHunger = drain;
	}
	
	public void setPlayerOnFire(boolean fire)
	{
		isPlayerOnFire = fire;
	}
	
	public void setPlayerHealth(Integer health)
	{
		playerHealth = health;
	}
	
	public void setPlayerPreviousHealth(Integer pHealth)
	{
		playerPreviousHealth = pHealth;
	}
	
	public void setPlayerHunger(Integer hunger)
	{
		playerHunger = hunger;
	}
	
	public void setPlayerArmour(Integer armour)
	{
		playerArmour = armour;
	}
	
	public void setPlayerX(Integer pX)
	{
		playerX = pX;
	}
	
	public void setPlayerY(Integer pY)
	{
		playerY = pY;
	}
	
	public void setPlayerZ(Integer pZ)
	{
		playerZ = pZ;
	}
	
	public void setTopLeftI(Integer TLI)
	{
		topLeftI = TLI;
	}
	
	public void setTopLeftJ(Integer TLJ)
	{
		topLeftJ = TLJ;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
}