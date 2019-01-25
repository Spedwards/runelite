/*
 * Copyright (c) 2018, Kamiel, <https://github.com/Kamielvf>
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * Copyright (c) 2018, Psikoi <https://github.com/psikoi>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.screenmarkers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Provides;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.input.MouseManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.screenmarkers.ui.ScreenMarkerPluginPanel;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ImageUtil;

@PluginDescriptor(
	name = "Screen Markers",
	description = "Enable drawing of screen markers on top of the client",
	tags = {"boxes", "overlay", "panel"}
)
public class ScreenMarkerPlugin extends Plugin
{
	private static final String PLUGIN_NAME = "Screen Markers";
	private static final String CONFIG_GROUP = "screenmarkers";
	private static final String CONFIG_KEY = "markers";
	private static final String ICON_FILE = "panel_icon.png";
	private static final String DEFAULT_MARKER_NAME = "Marker";
	private static final String DEFAULT_GROUP_NAME = "Group";
	private static final Dimension DEFAULT_SIZE = new Dimension(2, 2);
	private static final Gson GSON = new Gson();

	@Inject
	private ConfigManager configManager;

	@Inject
	private MouseManager mouseManager;

	@Inject
	private ScreenMarkerConfig config;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ScreenMarkerCreationOverlay overlay;

	@Getter
	private final List<MarkerWrapper> markers = new ArrayList<>();

	@Getter(AccessLevel.PACKAGE)
	private Marker currentMarker;

	@Getter
	private boolean creatingScreenMarker = false;

	private Point startLocation = null;
	private ScreenMarkerPluginPanel pluginPanel;
	private NavigationButton navigationButton;
	private ScreenMarkerMouseListener mouseListener;

	@Provides
	ScreenMarkerConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ScreenMarkerConfig.class);
	}

	@Override
	protected void startUp()
	{
		markers.addAll(deserializeMarkers());
		overlayManager.add(overlay);
		getScreenMarkers().forEach(overlayManager::add);

		pluginPanel = injector.getInstance(ScreenMarkerPluginPanel.class);
		pluginPanel.rebuild();

		final BufferedImage icon = ImageUtil.getResourceStreamFromClass(getClass(), ICON_FILE);

		navigationButton = NavigationButton.builder()
			.tooltip(PLUGIN_NAME)
			.icon(icon)
			.priority(5)
			.panel(pluginPanel)
			.build();

		clientToolbar.addNavigation(navigationButton);

		mouseListener = new ScreenMarkerMouseListener(this);
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(overlay);
		overlayManager.removeIf(MarkerOverlay.class::isInstance);
		clientToolbar.removeNavigation(navigationButton);
		setMouseListenerEnabled(false);

		pluginPanel = null;
		mouseListener = null;
		navigationButton = null;

		serializeMarkers();
		markers.clear();
	}

	public void setMouseListenerEnabled(boolean enabled)
	{
		if (enabled)
		{
			mouseManager.registerMouseListener(mouseListener);
		}
		else
		{
			mouseManager.unregisterMouseListener(mouseListener);
		}
	}

	private Stream<MarkerOverlay> getScreenMarkers()
	{
		return markers.stream()
			.flatMap(wrapper -> {
				if (wrapper instanceof Marker)
				{
					return Stream.of(((Marker) wrapper).getOverlay());
				}
				else
				{
					MarkerGroup group = (MarkerGroup) wrapper;
					return group.getMarkers()
						.stream()
						.map(marker -> marker.getOverlay());
				}
			});
	}

	public void startCreation(Point location)
	{
		currentMarker = new Marker();
		currentMarker.setId(Instant.now().toEpochMilli());
		currentMarker.setName(DEFAULT_MARKER_NAME + " " + (markers.size() + 1));
		currentMarker.setBorderThickness(pluginPanel.getSelectedBorderThickness());
		currentMarker.setColor(pluginPanel.getSelectedColor());
		currentMarker.setFill(pluginPanel.getSelectedFillColor());
		currentMarker.setVisible(true);

		startLocation = location;
		overlay.setPreferredLocation(location);
		overlay.setPreferredSize(DEFAULT_SIZE);
		creatingScreenMarker = true;
	}

	public void finishCreation(boolean aborted)
	{
		if (!aborted)
		{
			final MarkerOverlay markerOverlay = new MarkerOverlay();
			markerOverlay.setPreferredLocation(overlay.getBounds().getLocation());
			markerOverlay.setPreferredSize(overlay.getBounds().getSize());
			markerOverlay.setId(currentMarker.getId());
			markerOverlay.setBorderThickness(currentMarker.getBorderThickness());
			markerOverlay.setColor(currentMarker.getColor());
			markerOverlay.setFill(currentMarker.getFill());

			currentMarker.setOverlay(markerOverlay);
			overlayManager.saveOverlay(markerOverlay);
			overlayManager.add(markerOverlay);
			pluginPanel.rebuild();
			serializeMarkers();
		}
	}

	public void completeSelection()
	{
		pluginPanel.getCreationPanel().unlockConfirm();
	}

	public void deleteMarker(final Marker marker)
	{
		deleteMarker(marker, null);
	}

	public void deleteMarker(final Marker marker, final MarkerGroup group)
	{
		if (group == null)
		{
			markers.remove(marker);
		}
		else
		{
			group.getMarkers().remove(marker);
		}
		overlayManager.remove(marker.getOverlay());
		overlayManager.resetOverlay(marker.getOverlay());
		pluginPanel.rebuild();
		serializeMarkers();
	}

	void resizeMarker(Point point)
	{
		Rectangle bounds = new Rectangle(startLocation);
		bounds.add(point);
		overlay.setPreferredLocation(bounds.getLocation());
		overlay.setPreferredSize(bounds.getSize());
	}

	public MarkerGroup createGroup()
	{
		int groups = markers.stream()
			.filter(wrapper -> wrapper instanceof MarkerGroup)
			.toArray().length;
		final MarkerGroup group = new MarkerGroup();
		group.setId(Instant.now().toEpochMilli());
		group.setName(DEFAULT_GROUP_NAME + " " + (groups + 1));
		group.setVisible(true);
		group.setSequential(false);

		markers.add(group);

		return group;
	}

	public void serializeMarkers()
	{
		config.markersData(GSON.toJson(markers));
	}

	private List<MarkerWrapper> deserializeMarkers()
	{
		Type listType = new TypeToken<List<MarkerWrapper>>()
		{
		}.getType();
		return GSON.fromJson(config.markersData(), listType);
	}
}
