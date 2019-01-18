package net.runelite.client.plugins.screenmarkers.ui;

import lombok.Getter;
import net.runelite.client.plugins.screenmarkers.ScreenMarkerOverlay;
import net.runelite.client.plugins.screenmarkers.ScreenMarkerPlugin;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.FlatTextField;
import net.runelite.client.ui.components.PluginErrorPanel;
import net.runelite.client.util.ImageUtil;

import javax.inject.Inject;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GroupPanel extends JPanel
{
	private static final ImageIcon ADD_ICON;
	private static final ImageIcon ADD_HOVER_ICON;
	private static final ImageIcon RENAME_ICON;
	private static final ImageIcon RENAME_HOVER_ICON;
	private static final ImageIcon VISIBLE_ICON;
	private static final ImageIcon VISIBLE_HOVER_ICON;
	private static final ImageIcon INVISIBLE_ICON;
	private static final ImageIcon INVISIBLE_HOVER_ICON;
	private static final ImageIcon DELETE_ICON;
	private static final ImageIcon DELETE_HOVER_ICON;
	private static final Color BACKGROUND_COLOUR = ColorScheme.GRAND_EXCHANGE_LIMIT;

	@Getter
	private final FlatTextField title = new FlatTextField();

	@Getter
	private final List<ScreenMarkerOverlay> screenMarkers = new ArrayList<>();

	@Getter
	private ScreenMarkerCreationPanel creationPanel;

	@Inject
	private ScreenMarkerPlugin plugin;

	private final JLabel addMarker = new JLabel(ADD_ICON);
	private final JLabel renameGroup = new JLabel(RENAME_ICON);
	private final JLabel visibleGroup = new JLabel(VISIBLE_ICON);
	private final JLabel deleteGroup = new JLabel(DELETE_ICON);
	private final ScreenMarkerPluginPanel panel;
	private final PluginErrorPanel noMarkersPanel = new PluginErrorPanel();
	private final JPanel markerView;
	private final GridBagConstraints c;

	private boolean visible;

	static
	{
		final BufferedImage addIcon = ImageUtil.getResourceStreamFromClass(ScreenMarkerPlugin.class, "add_icon.png");
		ADD_ICON = new ImageIcon(addIcon);
		ADD_HOVER_ICON = new ImageIcon(ImageUtil.alphaOffset(addIcon, 0.53f));

		final BufferedImage renameIcon = ImageUtil.getResourceStreamFromClass(ScreenMarkerPlugin.class, "group_rename_icon.png");
		final BufferedImage renameIconHover = ImageUtil.grayscaleOffset(renameIcon, -150);
		RENAME_ICON = new ImageIcon(renameIcon);
		RENAME_HOVER_ICON = new ImageIcon(renameIconHover);

		final BufferedImage visibleImg = ImageUtil.getResourceStreamFromClass(ScreenMarkerPlugin.class, "visible_icon.png");
		VISIBLE_ICON = new ImageIcon(visibleImg);
		VISIBLE_HOVER_ICON = new ImageIcon(ImageUtil.alphaOffset(visibleImg, -100));

		final BufferedImage invisibleImg = ImageUtil.getResourceStreamFromClass(ScreenMarkerPlugin.class, "invisible_icon.png");
		INVISIBLE_ICON = new ImageIcon(invisibleImg);
		INVISIBLE_HOVER_ICON = new ImageIcon(ImageUtil.alphaOffset(invisibleImg, -100));

		final BufferedImage deleteImg = ImageUtil.getResourceStreamFromClass(ScreenMarkerPlugin.class, "delete_icon.png");
		DELETE_ICON = new ImageIcon(deleteImg);
		DELETE_HOVER_ICON = new ImageIcon(ImageUtil.alphaOffset(deleteImg, -100));
	}

	GroupPanel(final ScreenMarkerPluginPanel panel)
	{
		super();
		this.panel = panel;

		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel northPanel = new JPanel(new BorderLayout());
		northPanel.setBorder(new EmptyBorder(1, 0, 10, 0));

		JPanel actions = new JPanel(new BorderLayout(0, 2));
		JPanel topActions = new JPanel(new BorderLayout(2, 0));
		JPanel bottomActions = new JPanel(new BorderLayout(2, 0));

		topActions.add(addMarker, BorderLayout.WEST);
		topActions.add(renameGroup, BorderLayout.EAST);
		bottomActions.add(visibleGroup, BorderLayout.WEST);
		bottomActions.add(deleteGroup, BorderLayout.EAST);

		actions.add(topActions, BorderLayout.NORTH);
		actions.add(bottomActions, BorderLayout.SOUTH);

		title.setText("Group #"); // TODO: Automatically increment count
		title.setBorder(null);
		title.setEditable(false);
		title.getTextField().setForeground(Color.WHITE);

		northPanel.add(title, BorderLayout.WEST);
		northPanel.add(actions, BorderLayout.EAST);

		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBackground(BACKGROUND_COLOUR);

		markerView = new JPanel(new GridBagLayout());
		markerView.setBackground(BACKGROUND_COLOUR);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;

		build();

		addMarker.setToolTipText("Add new screen marker");
		addMarker.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				// TODO: Create marker
				setCreation(true);
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				addMarker.setIcon(ADD_HOVER_ICON);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				addMarker.setIcon(ADD_ICON);
			}
		});

		renameGroup.setToolTipText("Rename group");
		renameGroup.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				// TODO: Rename group
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				renameGroup.setIcon(RENAME_HOVER_ICON);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				renameGroup.setIcon(RENAME_ICON);
			}
		});

		visibleGroup.setToolTipText(visible ? "Hide group markers" : "Show group markers");
		visibleGroup.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				// TODO: Toggle visibility
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				visibleGroup.setIcon(VISIBLE_HOVER_ICON);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				visibleGroup.setIcon(VISIBLE_ICON);
			}
		});

		deleteGroup.setToolTipText("Delete marker group");
		deleteGroup.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				int confirm = JOptionPane.showConfirmDialog(GroupPanel.this,
					"Are you sure you want to permanently delete this group and all screen markers it contains?",
					"Warning", JOptionPane.OK_CANCEL_OPTION);

				if (confirm == 0)
				{
					// TODO: Delete markers
					GroupPanel.this.getParent().remove(GroupPanel.this);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				deleteGroup.setIcon(DELETE_HOVER_ICON);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				deleteGroup.setIcon(DELETE_ICON);
			}
		});

		centerPanel.add(markerView, BorderLayout.CENTER);

		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);

		revalidate();
		repaint();
	}

	void setCreation(boolean on)
	{
		if (on)
		{
			noMarkersPanel.setVisible(true);
			title.setVisible(true);
		}
		else
		{
			boolean empty = screenMarkers.isEmpty();
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

	void build()
	{
		screenMarkers.forEach(marker ->
		{
			markerView.add(new ScreenMarkerPanel(plugin, marker), c);
			c.gridy++;

			markerView.add(Box.createRigidArea(new Dimension(0, 10)), c);
			c.gridy++;
		});

		noMarkersPanel.setContent("Screen Markers", "Highlight a region on your screen.");
		noMarkersPanel.setVisible(false);

		if (screenMarkers.isEmpty())
		{
			noMarkersPanel.setVisible(true);
			title.setVisible(false);
		}

		markerView.add(noMarkersPanel, c);
		c.gridy++;

		creationPanel = new ScreenMarkerCreationPanel(plugin);
		creationPanel.setVisible(false);

		markerView.add(creationPanel, c);
		c.gridy++;
	}

	void rebuild()
	{
		markerView.removeAll();
		repaint();
		revalidate();
		build();
	}
}
