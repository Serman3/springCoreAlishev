package org.example;

import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
public class RockMusic implements Music{

    private List<String> rockMusics;

    @Override
    public List<String> getSong() {
        return Arrays.asList("rockSong1", "rockSong2", "rockSong3");
    }
}
