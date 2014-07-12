package org.arachnidium.util.logging;

import java.awt.Color;

public enum eLogColors {
	CORRECTSTATECOLOR(new Color(0, 255, 0), "Green"), WARNSTATECOLOR(new Color(
			255, 255, 0), "Yellow"), DEBUGCOLOR(new Color(255, 250, 250),
			"Snow"), SEVERESTATECOLOR(new Color(255, 0, 0), "Red");

	private Color stateColor;
	private String htmlColorDescription;

	private eLogColors(Color color, String htmlColorDescription) {
		this.stateColor = color;
		this.htmlColorDescription = htmlColorDescription;
	}

	public String getHTMLColorDescription() {
		return htmlColorDescription;
	}

	public Color getStateColor() {
		return stateColor;
	}

}
