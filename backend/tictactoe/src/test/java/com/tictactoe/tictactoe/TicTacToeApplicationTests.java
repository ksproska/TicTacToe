package com.tictactoe.tictactoe;

import com.tictactoe.tictactoe.controllers.HealthCheckController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TicTacToeApplicationTests {
	@Autowired
	private HealthCheckController authController;

	@Test
	void contextLoads() {
		assertThat(authController).isNotNull();
	}
}
