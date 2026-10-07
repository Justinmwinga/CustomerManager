package com.example.customermanager;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private final ObservableList<Customer> customers =
            FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {

        // -------------------------
        // Name input
        // -------------------------

        Label nameLabel = new Label("Name:");

        TextField nameField = new TextField();
        nameField.setPromptText("Enter customer name");


        // -------------------------
        // Province selection
        // -------------------------

        Label provinceLabel = new Label("Province:");

        ComboBox<String> provinceBox = new ComboBox<>();

        provinceBox.getItems().addAll(
                "Central",
                "Copperbelt",
                "Eastern",
                "Luapula",
                "Lusaka",
                "Muchinga",
                "Northern",
                "North-Western",
                "Southern",
                "Western"
        );

        provinceBox.setPromptText("Select province");


        // -------------------------
        // Add button
        // -------------------------

        Button addButton = new Button("Add Customer");


        // -------------------------
        // Delete button
        // -------------------------

        Button deleteButton = new Button("Delete Selected");


        // -------------------------
        // Customer table
        // -------------------------

        TableView<Customer> customerTable =
                new TableView<>();


        // -------------------------
        // Name column
        // -------------------------

        TableColumn<Customer, String> nameColumn =
                new TableColumn<>("Name");

        nameColumn.setCellValueFactory(
                data -> data.getValue().nameProperty()
        );


        // -------------------------
        // Province column
        // -------------------------

        TableColumn<Customer, String> provinceColumn =
                new TableColumn<>("Province");

        provinceColumn.setCellValueFactory(
                data -> data.getValue().provinceProperty()
        );


        // -------------------------
        // Add columns to table
        // -------------------------

        customerTable.getColumns().addAll(
                nameColumn,
                provinceColumn
        );


        // -------------------------
        // Connect list to table
        // -------------------------

        customerTable.setItems(customers);


        // -------------------------
        // Add customer action
        // -------------------------

        addButton.setOnAction(event -> {

            String name = nameField.getText().trim();
            String province = provinceBox.getValue();


            // Validate name

            if (name.isEmpty()) {

                Alert alert =
                        new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Invalid Input");

                alert.setHeaderText(
                        "Customer name is required"
                );

                alert.setContentText(
                        "Please enter a customer name."
                );

                alert.showAndWait();

                nameField.requestFocus();

                return;
            }


            // Validate province

            if (province == null) {

                Alert alert =
                        new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Invalid Input");

                alert.setHeaderText(
                        "Province is required"
                );

                alert.setContentText(
                        "Please select a province."
                );

                alert.showAndWait();

                provinceBox.requestFocus();

                return;
            }


            // Create customer

            Customer customer =
                    new Customer(name, province);


            // Add customer to list

            customers.add(customer);


            // Clear form

            nameField.clear();

            provinceBox
                    .getSelectionModel()
                    .clearSelection();


            // Return focus to name field

            nameField.requestFocus();
        });


        // -------------------------
        // Delete customer action
        // -------------------------

        deleteButton.setOnAction(event -> {

            Customer selectedCustomer =
                    customerTable
                            .getSelectionModel()
                            .getSelectedItem();


            // Check whether a customer is selected

            if (selectedCustomer == null) {

                Alert alert =
                        new Alert(Alert.AlertType.WARNING);

                alert.setTitle("No Customer Selected");

                alert.setHeaderText(
                        "No customer selected"
                );

                alert.setContentText(
                        "Please select a customer to delete."
                );

                alert.showAndWait();

                return;
            }


            // Confirmation dialog

            Alert confirmation =
                    new Alert(
                            Alert.AlertType.CONFIRMATION
                    );

            confirmation.setTitle(
                    "Confirm Deletion"
            );

            confirmation.setHeaderText(
                    "Delete Customer"
            );

            confirmation.setContentText(
                    "Are you sure you want to delete "
                            + selectedCustomer.getName()
                            + "?"
            );


            // Wait for user's answer

            confirmation.showAndWait()
                    .ifPresent(response -> {

                        if (response ==
                                javafx.scene.control.ButtonType.OK) {

                            customers.remove(
                                    selectedCustomer
                            );
                        }
                    });
        });


        // -------------------------
        // Layout
        // -------------------------

        VBox layout = new VBox(
                10,
                nameLabel,
                nameField,
                provinceLabel,
                provinceBox,
                addButton,
                customerTable,
                deleteButton
        );

        layout.setPadding(
                new Insets(15)
        );


        // -------------------------
        // Scene
        // -------------------------

        Scene scene =
                new Scene(
                        layout,
                        600,
                        500
                );


        // -------------------------
        // Window
        // -------------------------

        stage.setTitle(
                "Customer Manager"
        );

        stage.setScene(scene);

        stage.show();


        // Initial keyboard focus

        nameField.requestFocus();
    }


    // -------------------------
    // Application entry point
    // -------------------------

    public static void main(String[] args) {

        launch(args);
    }
}
