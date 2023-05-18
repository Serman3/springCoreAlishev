package org.example;

import org.springframework.beans.factory.annotation.Value;
import java.util.List;
import java.util.Random;

public class MusicPlayer {

    private List<Music> genreMusic;

    public MusicPlayer(List<Music> genreMusic){
        this.genreMusic = genreMusic;
    }

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

    public String playMusic(){
        Random random = new Random();
        return genreMusic.get(random.nextInt(genreMusic.size())).getSong().get(random.nextInt(genreMusic.size()));
    }

}
