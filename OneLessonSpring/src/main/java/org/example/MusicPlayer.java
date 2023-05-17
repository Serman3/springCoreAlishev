package org.example;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {
    private Music music;
    private List<Music> musicList = new ArrayList<>();
    private String name;
    private int volume;

    public MusicPlayer(){};

    // Inversion of control
    public MusicPlayer(Music music){
        this.music = music;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public List<Music> getMusicList() {
        return musicList;
    }

    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
    }

    // Spring отбрасывает в названии метода set, поэтому используем в xml файле имя сетода Music (вместо setMusic)
    public void setMusic(Music music){
        this.music = music;
    }

    public void playMusicList(){
        for (Music music : musicList){
            System.out.println("Playing " + music.getSong());
        }
    }

}
