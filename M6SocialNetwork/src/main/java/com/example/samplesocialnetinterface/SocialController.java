package com.example.samplesocialnetinterface;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SocialController implements Initializable {

    @FXML private Button addProfile;
    @FXML private Button addFriend;
    @FXML private Button deleteProfile;
    @FXML private Button deleteFriend;
    @FXML private Button lookupProfile;
    @FXML private Button changeStatButton;
    @FXML private Button changeQuoteButton;
    @FXML private Button changePicButton;
    @FXML private Button visualizeGraph;
    @FXML private Button exit;
    @FXML private TextArea currStatus;
    @FXML private TextArea currQuote;
    @FXML private TextField addUserField;
    @FXML private TextField addFriendField;
    @FXML private TextField changePic;
    @FXML private TextField changeStatus;
    @FXML private TextField changeQuote;
    @FXML private ImageView profileImage;
    @FXML private Label profileName;
    @FXML private Label currentPage;
    @FXML private ListView<String> profileList;
    @FXML private ListView<String> friendList;

    private final Map<String, Profile> profiles = new HashMap<>();
    private final SocialNetworkGraph socialGraph = new SocialNetworkGraph();
    private Profile currentProfile = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addPredefinedProfiles();

        profileList.setItems(FXCollections.observableArrayList(profiles.keySet()));
        profileList.setOnMouseClicked(e -> {
            String selected = profileList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                loadProfile(selected);
            }
        });
    }

    private void addPredefinedProfiles() {

        profiles.put("Emilio Aguinaldo", new Profile("Emilio Aguinaldo", "For the Filipino, or for the colony? I can't decide.", "We cannot free ourselves unless we move forward united in a single desire.", "/aguinaldo.png"));
        profiles.put("Andres Bonifacio", new Profile("Andres Bonifacio", "Supremo. The true first president.", "Ibigin mo ang iyong bayan nang sunod kay Bathala, sa iyong kapurihan, at higit sa lahat sa iyong sarili.", "/bonifacio.png"));
        profiles.put("Antonio Luna", new Profile("Hen. Antonio Luna", "Hotheaded, ready to fight!", "Ipapakita ko sa kanila na ang mga Pilipino ay higit na may tapang, puri, at dangal.", "/luna.png"));
        profiles.put("Apolinario Mabini", new Profile("Apolinario Mabini", "Sitting down, thinking", "Napakahirap maghanap ng politikang dalisay, totoo, at tapat sa panahong ito, dahil kasakiman at ambisyon ang bumubulag sa pamahalaan.", "/mabini.png"));
        profiles.put("Jose Rizal", new Profile("Jose Rizal", "For the nation!", "Ang kabataan ang pag-asa ng bayan.", "/rizal.png"));
        profiles.put("Melchora Aquino", new Profile("Melchora Aquino","Serving the Philippines, the way I know best.","I have no regrets and if I had nine lives I would have gladly given them all up for my country.", "/tandangsora.png"));

        for (String name : profiles.keySet()) {
            socialGraph.addProfile(name);
        }

        socialGraph.addFriendship("Jose Rizal", "Andres Bonifacio");
        socialGraph.addFriendship("Jose Rizal", "Antonio Luna");
        socialGraph.addFriendship("Andres Bonifacio", "Emilio Aguinaldo");
        socialGraph.addFriendship("Antonio Luna", "Apolinario Mabini");
        socialGraph.addFriendship("Melchora Aquino", "Andres Bonifacio");
    }

    private void loadProfile(String name) {
        currentProfile = profiles.get(name);
        if (currentProfile == null) {
            updateCurrentPage("Profile not found: " + name);
            return;
        }

        profileName.setText(name);
        currStatus.setText(currentProfile.getStatus());
        currQuote.setText(currentProfile.getQuote());

        Set<String> friends = socialGraph.getFriends(name);
        friendList.setItems(FXCollections.observableArrayList(friends));

        updateCurrentPage("Displaying profile: " + name + " (" + friends.size() + " friends)");
        loadProfileImage();
    }

    private void loadProfileImage() {
        if (currentProfile == null) return;
        try {
            String imagePath = currentProfile.getImagePath();
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            if (image.isError()) {
                throw new Exception("Image failed to load");
            }
            profileImage.setImage(image);
        } catch (Exception e) {
            System.out.println("Failed to load image for " + currentProfile.getName() + ": " + currentProfile.getImagePath());
            try {
                profileImage.setImage(new Image(getClass().getResourceAsStream("/unknown.png")));
            } catch (Exception defaultError) {
                System.out.println("Failed to load default image as well");
                profileImage.setImage(null);
            }
        }
    }

    @FXML
    public void addProfile(ActionEvent event) {
        String name = addUserField.getText().trim();
        if (name.isEmpty()) {
            updateCurrentPage("Please enter a profile name");
            return;
        }

        if (!profiles.containsKey(name)) {
            Profile newProfile = new Profile(name, "No status yet", "No quote yet", "default.png");
            profiles.put(name, newProfile);
            socialGraph.addProfile(name);
            profileList.getItems().add(name);
            addUserField.clear();
            updateCurrentPage("Added new profile: " + name);
        } else {
            updateCurrentPage("Profile already exists: " + name);
        }
    }

    @FXML
    public void deleteProfile(ActionEvent event) {
        if (currentProfile != null) {
            String name = currentProfile.getName();
            profiles.remove(name);
            socialGraph.removeProfile(name);
            profileList.getItems().remove(name);
            friendList.getItems().clear();
            profileName.setText("");
            currStatus.clear();
            currQuote.clear();
            profileImage.setImage(null);
            currentProfile = null;
            updateCurrentPage("Deleted profile: " + name);
        } else {
            updateCurrentPage("No profile selected to delete");
        }
    }

    @FXML
    public void addFriend(ActionEvent event) {
        if (currentProfile != null) {
            String friendName = addFriendField.getText().trim();

            if (friendName.isEmpty()) {
                updateCurrentPage("Please enter a friend name");
                return;
            }

            if (profiles.containsKey(friendName)) {
                if (friendName.equals(currentProfile.getName())) {
                    updateCurrentPage("Cannot add yourself as a friend");
                } else if (socialGraph.areFriends(currentProfile.getName(), friendName)) {
                    updateCurrentPage("Already friends with: " + friendName);
                } else {
                    socialGraph.addFriendship(currentProfile.getName(), friendName);

                    // Refresh friend list
                    Set<String> friends = socialGraph.getFriends(currentProfile.getName());
                    friendList.setItems(FXCollections.observableArrayList(friends));

                    addFriendField.clear();
                    updateCurrentPage("Added friend: " + friendName + " (bidirectional friendship created)");
                }
            } else {
                updateCurrentPage("Profile does not exist: " + friendName);
            }
        } else {
            updateCurrentPage("No profile selected to add friend to");
        }
    }

    @FXML
    public void deleteFriend(ActionEvent event) {
        if (currentProfile != null) {
            String friendName = addFriendField.getText().trim();

            if (friendName.isEmpty()) {
                updateCurrentPage("Please enter a friend name to remove");
                return;
            }

            if (socialGraph.removeFriendship(currentProfile.getName(), friendName)) {
                // Refresh friend list
                Set<String> friends = socialGraph.getFriends(currentProfile.getName());
                friendList.setItems(FXCollections.observableArrayList(friends));

                addFriendField.clear();
                updateCurrentPage("Removed friend: " + friendName + " (bidirectional friendship removed)");
            } else {
                updateCurrentPage("Friend not found: " + friendName);
            }
        } else {
            updateCurrentPage("No profile selected to remove friend from");
        }
    }

    @FXML
    public void lookupProfile(ActionEvent event) {
        String name = addUserField.getText().trim();
        if (name.isEmpty()) {
            updateCurrentPage("Please enter a profile name to lookup");
            return;
        }

        if (profiles.containsKey(name)) {
            loadProfile(name);
        } else {
            updateCurrentPage("Profile not found: " + name);
        }
    }

    @FXML
    public void changeStatus(ActionEvent event) {
        if (currentProfile != null) {
            String newStatus = changeStatus.getText().trim();
            if (newStatus.isEmpty()) {
                updateCurrentPage("Please enter a status");
                return;
            }

            currentProfile.setStatus(newStatus);
            currStatus.setText(newStatus);
            changeStatus.clear();
            updateCurrentPage("Updated status for " + currentProfile.getName());
        } else {
            updateCurrentPage("No profile selected to update status");
        }
    }

    @FXML
    public void changeQuote(ActionEvent event) {
        if (currentProfile != null) {
            String newQuote = changeQuote.getText().trim();
            if (newQuote.isEmpty()) {
                updateCurrentPage("Please enter a quote");
                return;
            }

            currentProfile.setQuote(newQuote);
            currQuote.setText(newQuote);
            changeQuote.clear();
            updateCurrentPage("Updated quote for " + currentProfile.getName());
        } else {
            updateCurrentPage("No profile selected to update quote");
        }
    }

    @FXML
    public void changePicture(ActionEvent event) {
        if (currentProfile != null) {
            String img = changePic.getText().trim();

            if (img.isEmpty()) {
                updateCurrentPage("Please enter an image filename");
                return;
            }

            String imagePath = "/"+img;
            Image image;

            try {
                image = new Image(getClass().getResourceAsStream(imagePath));
                if (image.isError()) {
                    throw new Exception("Failed to load image: " + img);
                }
            } catch (Exception e) {
                System.out.println("Image not found: " + img + ", using default unknown.png");
                imagePath = "/unknown.png";
                image = new Image(getClass().getResourceAsStream(imagePath));
                updateCurrentPage("Image not found, using default");
                return;
            }

            currentProfile.setImagePath(img);
            profileImage.setImage(image);
            changePic.clear();
            updateCurrentPage("Updated image for " + currentProfile.getName());
        } else {
            updateCurrentPage("No profile selected to update image");
        }
    }

    @FXML
    public void exitApp(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void visualizeGraph(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("graphvisualizer.fxml"));
            Parent root = loader.load();

            GraphVisualizerController controller = loader.getController();
            controller.setSocialData(socialGraph, profiles);

            Stage stage = new Stage();
            stage.setTitle("Social Network Graph Visualization");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

            updateCurrentPage("Graph visualization window opened");
        } catch (IOException e) {
            e.printStackTrace();
            updateCurrentPage("Error opening graph visualization: " + e.getMessage());
        }
    }

    private void updateCurrentPage(String actionDescription) {
        currentPage.setText(actionDescription);
    }
}