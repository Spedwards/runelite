package net.runelite.client.plugins.i18n;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("i18nplugin")
public interface InternationalizationConfig extends Config
{
	@ConfigItem(
		keyName = "language",
		name = "Language",
		description = "Which language to use for OSRS and RuneLite"
	)
	default Language language()
	{
		return Language.EN_GB;
	}
}
