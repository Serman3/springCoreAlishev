package org.example;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;

@Component
@Scope("singleton")
public class ClassicalMusic implements Music{

    @Override
    public List<String> getSong() {
        return Arrays.asList("classicSong1", "classicSong2", "classicSong3");
    }

    // Spring инициализирует все при запуске контекста
    @PostConstruct
    public void doMyInit(){
        System.out.println("Doing my initialization");
    }

    // Spring выгружает и удаляет все процессы при завершении программы (Действует только при scope singleton, при prototype игнорируется)
    @PreDestroy
    public void doMyDestroy(){
        System.out.println("Doing my destruction");
    }

}
