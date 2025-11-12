package com.example.samplesocialnetinterface;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.*;

public class GraphVisualizerController implements Initializable {

    @FXML private Pane graphCanvas;

    private SocialNetworkGraph socialGraph;
    private Map<String, Profile> profiles;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setSocialData(SocialNetworkGraph graph, Map<String, Profile> profiles) {
        this.socialGraph = graph;
        this.profiles = profiles;
        displayGraphVisualization();
    }

    private void displayGraphVisualization() {
        graphCanvas.getChildren().clear();

        List<String> profileNames = new ArrayList<>(socialGraph.getAllProfiles());
        if (profileNames.isEmpty()) {
            Text emptyText = new Text(400, 300, "No profiles in the network.");
            emptyText.setFill(Color.web("#dcddde"));
            emptyText.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
            graphCanvas.getChildren().add(emptyText);
            return;
        }

        double centerX = 400;
        double centerY = 300;
        double radius = 220;
        int numProfiles = profileNames.size();

        Map<String, double[]> positions = new HashMap<>();

        for (int i = 0; i < numProfiles; i++) {
            double angle = 2 * Math.PI * i / numProfiles - Math.PI / 2;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            positions.put(profileNames.get(i), new double[]{x, y});
        }

        Set<String> drawnEdges = new HashSet<>();
        for (String profile1 : profileNames) {
            double[] pos1 = positions.get(profile1);
            Set<String> friends = socialGraph.getFriends(profile1);

            for (String profile2 : friends) {
                String edgeKey = profile1.compareTo(profile2) < 0 ?
                        profile1 + "-" + profile2 : profile2 + "-" + profile1;

                if (!drawnEdges.contains(edgeKey)) {
                    double[] pos2 = positions.get(profile2);
                    Line edge = new Line(pos1[0], pos1[1], pos2[0], pos2[1]);
                    edge.setStroke(Color.web("#4a4d52"));
                    edge.setStrokeWidth(2.5);
                    graphCanvas.getChildren().add(edge);
                    drawnEdges.add(edgeKey);
                }
            }
        }

        for (String profileName : profileNames) {
            double[] pos = positions.get(profileName);

            Circle node = new Circle(pos[0], pos[1], 35);
            node.setFill(Color.web("#1877f2"));
            node.setStroke(Color.web("#dcddde"));
            node.setStrokeWidth(3);

            Text label = new Text(pos[0], pos[1] + 60, profileName);
            label.setFill(Color.web("#dcddde"));
            label.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            label.setTextAlignment(TextAlignment.CENTER);
            label.setWrappingWidth(120);

            label.setX(pos[0] - 60);

            int friendCount = socialGraph.getFriendCount(profileName);
            Text countText = new Text(pos[0], pos[1] + 6, String.valueOf(friendCount));
            countText.setFill(Color.WHITE);
            countText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            countText.setTextAlignment(TextAlignment.CENTER);
            countText.setX(pos[0] - countText.getLayoutBounds().getWidth() / 2);

            graphCanvas.getChildren().addAll(node, countText, label);
        }

        graphCanvas.setMinWidth(800);
        graphCanvas.setMinHeight(600);
    }
}