package org.example.genre;

import org.example.Music;

import java.util.Arrays;
import java.util.List;

public class RockMusic implements Music {
    private List<String> rockMusics;

    public List<String> getSong() {
        return Arrays.asList("rockSong1", "rockSong2", "rockSong3");
    }
}
