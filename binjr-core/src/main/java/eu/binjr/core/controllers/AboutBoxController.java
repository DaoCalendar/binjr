/*
 *    Copyright 2016-2018 Frederic Thevenet
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package eu.binjr.core.controllers;


import eu.binjr.core.dialogs.Dialogs;
import eu.binjr.core.preferences.AppEnvironment;
import eu.binjr.core.preferences.SysInfoProperty;
import eu.binjr.core.preferences.UpdateManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.Bloom;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

//import javafx.scene.web.WebView;

/**
 * The controller for the about dialog
 *
 * @author Frederic Thevenet
 */
public class AboutBoxController implements Initializable {
    private static final Logger logger = LogManager.getLogger(AboutBoxController.class);
    public TextFlow licenseView;
    public TextFlow acknowledgementView;
    public Label copyrightText;

    @FXML
    private DialogPane aboutRoot;

    @FXML
    private Label versionLabel;

    @FXML
    private TextFlow versionCheckFlow;

    @FXML
    private Hyperlink binjrUrl;

    @FXML
    private Accordion detailsPane;

    @FXML
    private TableView<SysInfoProperty> sysInfoListTable;

    @FXML
    private TitledPane sysInfoPane;

    @FXML
    private TitledPane licensePane;

    @FXML
    private TitledPane acknowledgementPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert aboutRoot != null : "fx:id\"aboutRoot\" was not injected!";
        assert versionLabel != null : "fx:id\"versionLabel\" was not injected!";
        assert binjrUrl != null : "fx:id\"binjrUrl\" was not injected!";
        assert detailsPane != null : "fx:id\"detailsPane\" was not injected!";
        assert sysInfoPane != null : "fx:id\"sysInfoPane\" was not injected!";
        assert sysInfoListTable != null : "fx:id\"sysInfoListTable\" was not injected!";
        assert licensePane != null : "fx:id\"licensePane\" was not injected!";
        assert acknowledgementPane != null : "fx:id\"thirdPartiesPane\" was not injected!";
        assert versionCheckFlow != null : "fx:id\"versionCheckFlow\" was not injected!";

        fillTextFlow(licenseView, getClass().getResource("/eu/binjr/text/about_license.txt"));
        fillTextFlow(acknowledgementView, getClass().getResource("/eu/binjr/text/about_acknowledgements.txt"));

        copyrightText.setText(AppEnvironment.COPYRIGHT_NOTICE);

        aboutRoot.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Dialogs.getStage(aboutRoot).close();
            }
        });

        aboutRoot.sceneProperty().addListener((observable, oldValue, scene) -> {
            if (scene != null) {
                licenseView.setMaxSize(scene.getWidth(), scene.getHeight());
                licenseView.maxWidthProperty().bind(scene.widthProperty());
                licenseView.maxHeightProperty().bind(scene.heightProperty());
            } else {
                licenseView.maxWidthProperty().unbind();
                licenseView.maxHeightProperty().unbind();
            }
        });

        Platform.runLater(() -> {
            overrideCss();
            Pane header = (Pane) sysInfoListTable.lookup("TableHeaderRow");
            if (header != null) {
                header.setMaxHeight(0);
                header.setMinHeight(0);
                header.setPrefHeight(0);
                header.setVisible(false);
            }
        });

        versionCheckFlow.getChildren().clear();
        Label l = new Label("Checking for updates...");
        l.setTextFill(Color.DARKGRAY);
        l.setPadding(new Insets(3, 0, 0, 4));
        versionCheckFlow.getChildren().add(l);
        UpdateManager.getInstance().asyncForcedCheckForUpdate(githubRelease -> {
                    versionCheckFlow.getChildren().clear();
                    Hyperlink latestReleaseLink = new Hyperlink("v" + githubRelease.getVersion().toString() + " is available");
                    latestReleaseLink.setTextFill(Color.valueOf("#4BACC6"));
                    latestReleaseLink.setEffect(new Bloom());
                    latestReleaseLink.setOnAction(event -> {
                        try {
                            Dialogs.launchUrlInExternalBrowser(githubRelease.getHtmlUrl());
                        } catch (IOException | URISyntaxException e) {
                            logger.error(e);
                        }
                    });
                    versionCheckFlow.getChildren().add(latestReleaseLink);
                },
                version -> l.setText(AppEnvironment.APP_NAME + " is up to date"),
                () -> versionCheckFlow.getChildren().clear());
        sysInfoListTable.getItems().addAll(AppEnvironment.getInstance().getSysInfoProperties());
        versionLabel.setText("version " + AppEnvironment.getInstance().getVersion());
        detailsPane.getPanes().forEach(p -> p.expandedProperty().addListener((obs, oldValue, newValue) -> Platform.runLater(() -> {
            p.requestLayout();
            p.getScene().getWindow().sizeToScene();
        })));
        Node closeButton = aboutRoot.lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
    }

    private void fillTextFlow(TextFlow textFlow, URL sourceUrl) {
        try {
            new BufferedReader(new InputStreamReader(sourceUrl.openStream())).lines().forEach(s -> {
                Text licText = new Text();
                if (s.startsWith("^b")) {
                    s = s.replace("^b", "");
                    licText.setStyle("-fx-font-weight: bold");
                }
                licText.setFontSmoothingType(FontSmoothingType.LCD);
                licText.setText(s + "\n");
                licText.setFill(Color.valueOf("#DAEEF3"));
                textFlow.getChildren().add(licText);
            });
            textFlow.setTextAlignment(TextAlignment.LEFT);
        } catch (IOException e) {
            logger.error("Cannot display content of URL " + sourceUrl + ": " + e.getMessage());
            logger.debug(() -> "Exception stack", e);
        }
    }

    private void overrideCss() {
        Stage aboutStage = Dialogs.getStage(aboutRoot);
        if (aboutStage == null || aboutStage.getScene() == null) {
            logger.warn("Cannot set css: About dialog scene is not ready.");
            return;
        }
        aboutStage.getScene().getStylesheets().clear();
    }

    @FXML
    private void goTobinjrDotEu(ActionEvent actionEvent) {
        try {
            Dialogs.launchUrlInExternalBrowser(AppEnvironment.HTTP_WWW_BINJR_EU);
        } catch (IOException | URISyntaxException e) {
            logger.error(e);
        }
        binjrUrl.setVisited(false);
    }
}
