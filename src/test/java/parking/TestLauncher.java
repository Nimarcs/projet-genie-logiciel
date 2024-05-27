package parking;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;

import fr.ul.miage.genielogiciel.parking.Launcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//
//import org.junit.*;
//
//import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;

@DisplayName("I'm testing if..")
public class TestLauncher {
    private InputStream originalIn;

    // ===== TESTS for Welcome Menu =====
    @Test
    @DisplayName("1. Correct input (Welcome Menu)")
    public void testWelcomeMenu1() {
        String input = "1\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        var l = new Launcher();
        int choice = l.welcomeMenu(scanner);

        assertEquals(1, choice);
    }

    @Test
    @DisplayName("2. 'abc' input (Welcome Menu)")
    public void testWelcomeMenu2() {
        String input = "abc\n4\n2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        int choice = Launcher.welcomeMenu(scanner);
        assertEquals(2, choice);
    }

    @Test
    @DisplayName("If input is > 3 (Welcome Menu)")
    public void testWelcomeMenu3() {
        String input = "5\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        int choice = Launcher.welcomeMenu(scanner);
        assertEquals(1, choice);
    }

    @Test
    @DisplayName("If input is < 1 (Welcome Menu)")
    public void testWelcomeMenu4() {
        String input = "-5\n2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        int choice = Launcher.welcomeMenu(scanner);
        assertEquals(2, choice);
    }

    @Test
    @DisplayName("Quit (Welcome Menu)")
    public void testWelcomeMenu5() {
        String input = "3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Launcher.welcomeMenu(scanner);

        assertTrue(true);
    }


// ===== TESTS for Main Menu =====

    @Test
    @DisplayName("Valid license number (Main Menu)")
    public void testMainMenu1() {
        String input = "1\nlicense123\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Launcher.mainMenu(scanner);

        assertTrue(true);
    }

    @Test
    @DisplayName("Valid reservation number (Main Menu)")
    public void testMainMenu2() {
        String input = "2\nreservation123\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Launcher.mainMenu(scanner);

        assertTrue(true);
    }

    @Test
    @DisplayName("First valid then invalid input (Main Menu)")
    public void testMainMenu3() {
        String input = "invalid\n4\n1\nlicense123\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Launcher.mainMenu(scanner);

        assertTrue(true);
    }

    @Test
    @DisplayName("Quit (Main Menu)")
    public void testMainMenu4() {
        String input = "3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Launcher.mainMenu(scanner);

        assertTrue(true);
    }

}
