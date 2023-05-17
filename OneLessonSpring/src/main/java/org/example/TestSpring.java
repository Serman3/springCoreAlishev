package org.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        // Spring ищет в папке resources файл applicationContext.xml и помещает данные из него в контекст приложения

        //Music music = context.getBean("musicBean", Music.class); // получили bean из контекста
        //MusicPlayer musicPlayer = new MusicPlayer(music);

        //Dependency injection
        MusicPlayer musicPlayer = context.getBean("musicPlayer", MusicPlayer.class);
        musicPlayer.playMusicList();
        /*System.out.println(musicPlayer.getName());
        System.out.println(musicPlayer.getVolume());*/
        context.close();
    }
}
