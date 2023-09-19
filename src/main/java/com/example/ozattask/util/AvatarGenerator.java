package com.example.ozattask.util;

public class AvatarGenerator {
    private static final String[] COLORS = {"#ff0000", "#00ff00", "#0000ff", "#ff8800", "#8800ff", "#ff0088"};

    public static String generateAvatar(String name) {
        String initials = String.valueOf(name.charAt(0)).toUpperCase();

        int colorIndex = name.length() % COLORS.length;
        String selectedColor = COLORS[colorIndex];

        return "background-color: " + selectedColor
                + "; color: white; border-radius: 50%; width: 50px; height: 50px; text-align: center; line-height: 50px;"
                + "'>" + initials;
    }


}

