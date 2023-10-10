package by.dorogokupets.walletservice.infrastructure.menu;

/**
 * An interface for displaying menu options to the user.
 */
public interface DisplayMenu {

    /**
     * Display the main menu with registration and authorization options.
     */
    void showMainMenu();

    /**
     * Display the client-specific menu with options for clients.
     */
    void showClientMenu();
}
