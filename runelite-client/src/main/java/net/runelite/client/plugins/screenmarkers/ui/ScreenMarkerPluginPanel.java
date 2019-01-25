/*
 * Copyright (c) 2018, Kamiel, <https://github.com/Kamielvf>
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
package net.runelite.client.plugins.screenmarkers.ui;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import lombok.Getter;
import net.runelite.client.plugins.screenmarkers.Marker;
import net.runelite.client.plugins.screenmarkers.MarkerGroup;
import net.runelite.client.plugins.screenmarkers.MarkerOverlay;
import net.runelite.client.plugins.screenmarkers.MarkerWrapper;
import net.runelite.client.plugins.screenmarkers.ScreenMarkerPlugin;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.PluginErrorPanel;
import net.runelite.client.util.ImageUtil;

@Singleton
public class ScreenMarkerPluginPanel extends PluginPanel
{
	private static final ImageIcon ADD_ICON;
	private static final ImageIcon ADD_HOVER_ICON;
	private static final ImageIcon GROUP_ICON;
	private static final ImageIcon GROUP_HOVER_ICON;

	private static final Color DEFAULT_BORDER_COLOR = Color.GREEN;
	private static final Color DEFAULT_FILL_COLOR = new Color(0, 255, 0, 0);

	private static final int DEFAULT_BORDER_THICKNESS = 3;

	private final JLabel addMarker = new JLabel(ADD_ICON);
	private final JLabel addGroup = new JLabel(GROUP_ICON);
	private final JLabel title = new JLabel();
	private final PluginErrorPanel noMarkersPanel = new PluginErrorPanel();

	@Inject
	private ScreenMarkerPlugin plugin;

	@Getter
	private Color selectedColor = DEFAULT_BORDER_COLOR;

	@Getter
	private Color selectedFillColor = DEFAULT_FILL_COLOR;

	@Getter
	private int selectedBorderThickness = DEFAULT_BORDER_THICKNESS;

	@Getter
	private ScreenMarkerCreationPanel creationPanel;

	static
	{
		final BufferedImage addIcon = ImageUtil.getResourceStreamFromClass(ScreenMarkerPlugin.class, "add_icon.png");
		ADD_ICON = new ImageIcon(addIcon);
		ADD_HOVER_ICON = new ImageIcon(ImageUtil.alphaOffset(addIcon, 0.53f));

		// TODO: Get icon - Currently using the add icon
		final BufferedImage groupIcon = ImageUtil.getResourceStreamFromClass(ScreenMarkerPlugin.class, "group_icon.png");
		GROUP_ICON = new ImageIcon(groupIcon);
		GROUP_HOVER_ICON = new ImageIcon(ImageUtil.alphaOffset(groupIcon, 0.53f));
	}

	public void init()
	{
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel northPanel = new JPanel(new BorderLayout());
		northPanel.setBorder(new EmptyBorder(1, 0, 10, 0));

		JPanel addActions = new JPanel(new BorderLayout(2, 0));
		addActions.add(addMarker, BorderLayout.WEST);
		addActions.add(addGroup, BorderLayout.EAST);

		title.setText("Screen Markers");
		title.setForeground(Color.WHITE);

		northPanel.add(title, BorderLayout.WEST);
		northPanel.add(addActions, BorderLayout.EAST);

		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBackground(ColorScheme.DARK_GRAY_COLOR);

		JPanel markerView = new JPanel(new GridBagLayout());
		markerView.setBackground(ColorScheme.DARK_GRAY_COLOR);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1;
		constraints.gridx = 0;
		constraints.gridy = 0;

		for (final MarkerWrapper wrapper : plugin.getMarkers())
		{
			if (wrapper instanceof Marker)
			{
				markerView.add(new ScreenMarkerPanel(plugin, (Marker) wrapper), constraints);
			}
			else
			{
				markerView.add(new GroupPanel(plugin, (MarkerGroup) wrapper), constraints);
			}
			constraints.gridy++;

			markerView.add(Box.createRigidArea(new Dimension(0, 10)), constraints);
			constraints.gridy++;
		}

		noMarkersPanel.setContent("Screen Markers", "Highlight a region on your screen.");
		noMarkersPanel.setVisible(false);

		if (plugin.getMarkers().isEmpty())
		{
			noMarkersPanel.setVisible(true);
			title.setVisible(false);
		}

		markerView.add(noMarkersPanel, constraints);
		constraints.gridy++;

		creationPanel = new ScreenMarkerCreationPanel(plugin);
		creationPanel.setVisible(false);

		markerView.add(creationPanel, constraints);
		constraints.gridy++;

		addMarker.setToolTipText("Add new screen marker");
		addMarker.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent mouseEvent)
			{
				setCreation(true);
			}

			@Override
			public void mouseEntered(MouseEvent mouseEvent)
			{
				addMarker.setIcon(ADD_HOVER_ICON);
			}

			@Override
			public void mouseExited(MouseEvent mouseEvent)
			{
				addMarker.setIcon(ADD_ICON);
			}
		});

		addGroup.setToolTipText("Add new screen marker group");
		addGroup.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				GroupPanel groupPanel = new GroupPanel(plugin, plugin.createGroup());
				markerView.add(groupPanel, constraints);
				constraints.gridy++;

				markerView.add(Box.createRigidArea(new Dimension(0, 10)), constraints);
				constraints.gridy++;

				rebuild();
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				addGroup.setIcon(GROUP_HOVER_ICON);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				addGroup.setIcon(GROUP_ICON);
			}
		});

		centerPanel.add(markerView, BorderLayout.CENTER);

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
	}

	public void rebuild()
	{
		removeAll();
		repaint();
		revalidate();
		init();
	}

	/* Enables/Disables new marker creation mode */
	public void setCreation(boolean on)
	{
		if (on)
		{
			noMarkersPanel.setVisible(false);
			title.setVisible(true);
		}
		else
		{
			boolean empty = plugin.getMarkers().isEmpty();
			noMarkersPanel.setVisible(empty);
			title.setVisible(!empty);
		}

		creationPanel.setVisible(on);
		addMarker.setVisible(!on);

		if (on)
		{
			creationPanel.lockConfirm();
			plugin.setMouseListenerEnabled(true);
		}
	}
}
