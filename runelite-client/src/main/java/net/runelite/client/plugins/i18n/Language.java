package net.runelite.client.plugins.i18n;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Language
{
	EN_GB("English", "en", "GB"),
	EN_AU("English (US)", "en", "US"),
	TL_PH("Tagalog (Philippines)", "tl", "PH");

	@Getter
	private final String formatted;
	@Getter
	private final String language;
	@Getter
	private final String country;

	@Override
	public String toString()
	{
		return this.formatted;
	}
}
