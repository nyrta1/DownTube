package com.example.youdown;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
class YoudownApplicationTests {
	@Container
	private static final GenericContainer<?> spring_boot_container = new GenericContainer<>("youdown-spring-boot-backend:latest")
			.withExposedPorts(8080);

	@Container
	private static final GenericContainer<?> angular_app_container = new GenericContainer<>("youdown-angular-frontend:latest")
			.withExposedPorts(4200);

	@BeforeAll
	public static void beforeAll() {
		spring_boot_container.start();
		angular_app_container.start();
	}

	@AfterAll
	public static void afterAll() {
		spring_boot_container.stop();
		angular_app_container.stop();
	}

	@Test
	public void testContainerIsRunning() {
		assertTrue(spring_boot_container.isRunning());
		assertTrue(angular_app_container.isRunning());
	}
}
