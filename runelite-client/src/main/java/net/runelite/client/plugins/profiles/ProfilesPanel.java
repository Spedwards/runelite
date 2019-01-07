package net.runelite.client.plugins.profiles;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Arrays;

class ProfilesPanel extends PluginPanel
{
	private final ProfilesConfig config;

	private GridBagConstraints c;
	private int gridy = 0;

	@Inject
	public ProfilesPanel(ProfilesConfig config)
	{
		super();
		this.config = config;

		setBorder(new EmptyBorder(18, 10, 0, 10));
		setBackground(ColorScheme.DARK_GRAY_COLOR);
		setLayout(new GridBagLayout());

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(0, 0, 3, 0);

		final Dimension PREFERRED_SIZE = new Dimension(PluginPanel.PANEL_WIDTH - 20, 30);
		final Dimension MINIMUM_SIZE = new Dimension(0, 30);

		JTextField txtAccountLabel = new JTextField("Account Label");
		txtAccountLabel.setPreferredSize(PREFERRED_SIZE);
		txtAccountLabel.setForeground(ColorScheme.MEDIUM_GRAY_COLOR);
		txtAccountLabel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		txtAccountLabel.setMinimumSize(MINIMUM_SIZE);
		txtAccountLabel.addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				if (txtAccountLabel.getText().equals("Account Label"))
				{
					txtAccountLabel.setText("");
					txtAccountLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
				}
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if (txtAccountLabel.getText().isEmpty())
				{
					txtAccountLabel.setForeground(ColorScheme.MEDIUM_GRAY_COLOR);
					txtAccountLabel.setText("Account Label");
				}
			}
		});

		add(txtAccountLabel, c);
		c.gridy++;

		JTextField txtAccountLogin = new JTextField("Account Login");
		txtAccountLogin.setPreferredSize(PREFERRED_SIZE);
		txtAccountLogin.setForeground(ColorScheme.MEDIUM_GRAY_COLOR);
		txtAccountLogin.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		txtAccountLogin.setMinimumSize(MINIMUM_SIZE);
		txtAccountLogin.addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				if (txtAccountLogin.getText().equals("Account Login"))
				{
					txtAccountLogin.setText("");
					txtAccountLogin.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
				}
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if (txtAccountLogin.getText().isEmpty())
				{
					txtAccountLogin.setForeground(ColorScheme.MEDIUM_GRAY_COLOR);
					txtAccountLogin.setText("Account Login");
				}
			}
		});

		add(txtAccountLogin, c);
		c.gridy++;
		c.insets = new Insets(0, 0, 15, 0);

		JButton btnAddAccount = new JButton("Add Account");
		btnAddAccount.setPreferredSize(PREFERRED_SIZE);
		btnAddAccount.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		btnAddAccount.setMinimumSize(MINIMUM_SIZE);
		btnAddAccount.addActionListener(e ->
		{
			this.addAccount(txtAccountLabel.getText() +
				":" +
				txtAccountLogin.getText());

			txtAccountLabel.setText("Account Label");
			txtAccountLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);

			txtAccountLogin.setText("Account Login");
			txtAccountLogin.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
		});

		add(btnAddAccount, c);
	}

	void addAccount(String data)
	{
		String[] parts = data.split(":");
		c.gridy++;
		c.insets = new Insets(0, 0, 5, 0);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		panel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		panel.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH - 20, 40));

		JLabel label = new JLabel(parts[0]);
		panel.add(label);

		JLabel login = new JLabel(parts[1]);
		panel.add(login);

		add(panel, c);

		revalidate();
		repaint();
	}

	void addAccounts(String data)
	{
		Arrays.stream(data.trim().split("\\n")).forEach(this::addAccount);
	}
}
