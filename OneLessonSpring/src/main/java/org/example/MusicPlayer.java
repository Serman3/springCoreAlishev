package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public class MusicPlayer {

    @Value("${musicPlayer.name}")
    private String name;

    @Value("${musicPlayer.volume}")
    private int volume;

    public String getName() {
        return name;
    }

    public int getVolume() {
        return volume;
    }

    @Autowired
    private RockMusic rockMusic;

    @Autowired
    private ClassicalMusic classicalMusic;

    public String playMusic(Enum statusMusic){
        Random random = new Random();
        if(statusMusic.equals(StatusMusic.CLASSICAL)){
            return classicalMusic.getSong().get(random.nextInt(3));
        }
        if (statusMusic.equals(StatusMusic.ROCK)){
            return rockMusic.getSong().get(random.nextInt(3));
        }
        return "Такого жанра нет";
    }

}
