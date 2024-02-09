package com.ximure.minesweeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MinesweeperApplication {
	public static void main(String[] args) {
		SpringApplication.run(MinesweeperApplication.class, args);
	}
}
