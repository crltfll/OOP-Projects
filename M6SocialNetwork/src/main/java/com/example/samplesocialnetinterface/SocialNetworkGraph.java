package com.example.samplesocialnetinterface;

import java.util.*;

public class SocialNetworkGraph {

    private final Map<String, Set<String>> adjacencyList;

    public SocialNetworkGraph() {
        this.adjacencyList = new HashMap<>();
    }


    public void addProfile(String profileName) {
        adjacencyList.putIfAbsent(profileName, new HashSet<>());
    }


    public void removeProfile(String profileName) {

        for (Set<String> friends : adjacencyList.values()) {
            friends.remove(profileName);
        }

        adjacencyList.remove(profileName);
    }

    public boolean addFriendship(String profile1, String profile2) {
        if (!adjacencyList.containsKey(profile1) || !adjacencyList.containsKey(profile2)) {
            return false;
        }
        if (profile1.equals(profile2)) {
            return false;
        }

        adjacencyList.get(profile1).add(profile2);
        adjacencyList.get(profile2).add(profile1);
        return true;
    }

    public boolean removeFriendship(String profile1, String profile2) {
        if (!adjacencyList.containsKey(profile1) || !adjacencyList.containsKey(profile2)) {
            return false;
        }

        boolean removed1 = adjacencyList.get(profile1).remove(profile2);
        boolean removed2 = adjacencyList.get(profile2).remove(profile1);
        return removed1 || removed2;
    }

    public boolean areFriends(String profile1, String profile2) {
        if (!adjacencyList.containsKey(profile1)) {
            return false;
        }
        return adjacencyList.get(profile1).contains(profile2);
    }

    public Set<String> getFriends(String profileName) {
        return adjacencyList.getOrDefault(profileName, new HashSet<>());
    }

    public int getFriendCount(String profileName) {
        return adjacencyList.getOrDefault(profileName, new HashSet<>()).size();
    }

    public Set<String> getAllProfiles() {
        return adjacencyList.keySet();
    }

    public boolean hasProfile(String profileName) {
        return adjacencyList.containsKey(profileName);
    }
}