package org.example;

import org.example.config.SpringConfigurations;
import org.example.genre.ClassicalMusic;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestSpring {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfigurations.class);

        MusicPlayer musicPlayer = context.getBean("musicPlayer", MusicPlayer.class);
        System.out.println(musicPlayer.getName());
        System.out.println(musicPlayer.getVolume());
        System.out.println(musicPlayer.playMusic());
        //System.out.println(musicPlayer.playMusic(StatusMusic.ROCK));


        ClassicalMusic classicalMusic1 = context.getBean("classicalMusic", ClassicalMusic.class);


        context.close();
    }
}
