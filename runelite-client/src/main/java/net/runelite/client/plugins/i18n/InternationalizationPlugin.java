package net.runelite.client.plugins.i18n;

import com.google.inject.Provides;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.MessageNode;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Internationalization",
	description = "Translate OSRS and RuneLite",
	tags = {"internationalization", "i18n"}
)
public class InternationalizationPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ChatMessageManager chatMessageManager;

	@Inject
	private InternationalizationConfig config;

	private ResourceBundle messages;
	private Map<String, String> defaultMessages;

	@Provides
	InternationalizationConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(InternationalizationConfig.class);
	}

	@Override
	protected void startUp()
	{
		setLocale();

		defaultMessages = new HashMap<>();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("net.runelite.client.plugins.i18n.MessagesBundle");
		Enumeration<String> keys = resourceBundle.getKeys();
		while (keys.hasMoreElements())
		{
			String key = keys.nextElement();
			defaultMessages.put(resourceBundle.getString(key), key);
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		setLocale();
	}

	private void setLocale()
	{
		Language lang = config.language();
		Locale selectedLocale = new Locale(lang.getLanguage(), lang.getCountry());
		messages = ResourceBundle.getBundle("net.runelite.client.plugins.i18n.MessagesBundle", selectedLocale);
	}

	@Subscribe(priority = -999)
	public void onChatMessage(ChatMessage event)
	{
		if (event.getType() != ChatMessageType.GAMEMESSAGE && event.getType() != ChatMessageType.CONSOLE && event.getType() != ChatMessageType.SPAM)
		{
			return;
		}

		final MessageNode messageNode = event.getMessageNode();
		final String message = messageNode.getValue();
		final String newMessage = findTranslation(message);
		if (newMessage != null)
		{
			messageNode.setRuneLiteFormatMessage(newMessage);
			chatMessageManager.update(messageNode);
			client.refreshChat();
		}
	}

	@Subscribe(priority = -999)
	public void onWidgetLoaded(WidgetLoaded event)
	{
		if (event.getGroupId() != WidgetID.DIALOG_NPC_GROUP_ID && event.getGroupId() != WidgetID.DIALOG_PLAYER_GROUP_ID && event.getGroupId() != WidgetID.DIALOG_OPTION_GROUP_ID)
		{
			return;
		}

		if (event.getGroupId() == WidgetID.DIALOG_NPC_GROUP_ID || event.getGroupId() == WidgetID.DIALOG_PLAYER_GROUP_ID)
		{
			Widget textWidget = event.getGroupId() == WidgetID.DIALOG_NPC_GROUP_ID
				? client.getWidget(WidgetInfo.DIALOG_NPC_TEXT)
				: client.getWidget(WidgetInfo.DIALOG_PLAYER_TEXT);
			if (textWidget != null)
			{
				clientThread.invokeLater(() ->
				{
					if (textWidget.getText().isEmpty())
					{
						return false;
					}
					String text = textWidget.getText();
					String newText = findTranslation(text);
					if (newText != null)
					{
						textWidget.setText(newText);
					}
					return true;
				});
			}
		}
		else
		{
			Widget groupWidget = client.getWidget(WidgetInfo.DIALOG_OPTIONS);
			clientThread.invokeLater(() ->
			{
				if (groupWidget == null || groupWidget.getChildren() == null || groupWidget.getChildren().length == 0)
				{
					return false;
				}
				Widget[] children = groupWidget.getChildren();
				Arrays.stream(children).limit(children.length - 2).forEach(child ->
				{
					String text = child.getText();
					log.debug("Child Widget: " + text);
					String newText = findTranslation(text);
					if (newText != null)
					{
						child.setText(newText);
					}
				});
				return true;
			});
		}
	}

	private String findTranslation(String toTranslate)
	{
		if (defaultMessages.containsKey(toTranslate))
		{
			String key = defaultMessages.get(toTranslate);
			if (messages.containsKey(key))
			{
				return messages.getString(key);
			}
		}
		return null;
	}
}
