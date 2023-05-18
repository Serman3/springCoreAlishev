package org.example.genre;

import org.example.Music;

import java.util.Arrays;
import java.util.List;

public class JazzMusic implements Music {
    @Override
    public List<String> getSong() {
        return Arrays.asList("jazzSong1", "jazzSong2", "jazzSong3");
    }
}
