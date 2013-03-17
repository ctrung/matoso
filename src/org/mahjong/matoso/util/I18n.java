package org.mahjong.matoso.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
	private ResourceBundle bundle;

	public I18n(String bundleName) {
		this.bundle = ResourceBundle.getBundle(bundleName);
	}

	public I18n(String bundleName, Locale newLocale) {
		this.bundle = ResourceBundle.getBundle(bundleName, newLocale);
	}

	public String getString(String key) {
		try {
			return this.bundle.getString(key);
		} catch (Exception e) {
			return key;
		}
	}
}