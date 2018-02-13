package edu.billing.config;

import java.util.Locale;
import java.util.ResourceBundle;

public class Settings {

    private final static String HOST = "host_url";
    public final static String CONTROLLER_API = "/api/account";

    private final static ResourceBundle bundle =
            ResourceBundle.getBundle(
                    "appData",
                    new Locale("en", "EN", "windows")
            );

    public final static String HOST_URL = bundle.getString(HOST);
}