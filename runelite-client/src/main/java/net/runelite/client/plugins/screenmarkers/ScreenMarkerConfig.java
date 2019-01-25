package net.runelite.client.plugins.screenmarkers;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("screenmarker")
public interface ScreenMarkerConfig extends Config
{
	@ConfigItem(
		keyName = "markersData",
		name = "",
		description = "",
		hidden = true
	)
	default String markersData()
	{
		return "";
	}

	@ConfigItem(
		keyName = "markersData",
		name = "",
		description = ""
	)
	void markersData(String data);
}
