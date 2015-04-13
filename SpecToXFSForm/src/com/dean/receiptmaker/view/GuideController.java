package com.dean.receiptmaker.view;

import java.io.File;
import java.net.MalformedURLException;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class GuideController {

	@FXML
	private WebView webViewId;
	
	final public static String GUIDE_HTML = "Guide.html";
	
	private WebEngine engine;
	
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		engine = webViewId.getEngine();
		File file = new File("resources/guidehtml/Index.html");
		try {
			engine.load(file.toURI().toURL().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
