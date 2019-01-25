package net.runelite.client.plugins.screenmarkers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarkerWrapper
{
	private String name;
	private boolean visible;

	public void setVisible(boolean visible)
	{
		this.visible = visible;
		if (this instanceof Marker)
		{
			Marker marker = (Marker) this;
			if (marker.getOverlay() != null)
			{
				marker.getOverlay().setVisible(visible);
			}
		}
		else
		{
			((MarkerGroup) this).getMarkers().forEach(marker -> marker.setVisible(visible));
		}
	}
}
