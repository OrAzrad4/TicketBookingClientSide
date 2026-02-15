package com.hit.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.hit.client.model.Request;
import com.hit.client.model.Response;
import com.hit.client.model.Ticket;
import com.hit.client.network.Client;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.lang.reflect.Type;

public class TicketController implements Initializable {

    @FXML private TextField tfId, tfEventName, tfCustomerName, tfPrice, tfSearch;
    @FXML private Label lblAddStatus, lblSearchStatus;
    @FXML private TableView<Ticket> ticketsTable;

    private Client client = new Client();
    private Gson gson = new Gson();

    // Start this function when the screen wake up and show all the events
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        onSearchClick();
    }

    //Add new ticket
    @FXML
    protected void onAddTicketClick() {
        try {
            Ticket t = new Ticket(
                    Long.parseLong(tfId.getText()),
                    tfEventName.getText(),
                    tfCustomerName.getText(),
                    Double.parseDouble(tfPrice.getText())
            );

            Map<String, String> headers = new HashMap<>();
            headers.put("action", "ticket/save");
            Request request = new Request(headers, t);

            String response = client.sendRequest(request);
            lblAddStatus.setText("Server Response: " + response);

            // Reload the page immediately after add a new ticket
            clearAddForm();
            onSearchClick();

        } catch (NumberFormatException e) {
            lblAddStatus.setText("Error: Check numeric fields");
        } catch (Exception e) {
            lblAddStatus.setText("Error: " + e.getMessage());
        }
    }

    //Search event
    @FXML
    protected void onSearchClick() {
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("action", "ticket/search");     // Create the header

            Map<String, String> body = new HashMap<>();
            // If called from initialize return all ticket because in SearchService return allTickets if the field is empty
            body.put("searchQuery", tfSearch.getText()); // Create body

            Request request = new Request(headers, body); // create request
            String jsonResponse = client.sendRequest(request); // get the response

            if (jsonResponse != null) {
                Response responseObj = gson.fromJson(jsonResponse, Response.class); // convert to java object
                // Convert again to JSON but now ask to List Ticket
                String listJson = gson.toJson(responseObj.getBody());
                Type listType = new TypeToken<List<Ticket>>(){}.getType();
                List<Ticket> tickets = gson.fromJson(listJson, listType);

                ObservableList<Ticket> data = FXCollections.observableArrayList(tickets);  //Convert the list to JavaFX list
                ticketsTable.setItems(data);  // Update the UI list to JavaFX list

                // Showing the data to user
                if (tfSearch.getText().isEmpty()) {
                    lblSearchStatus.setText("Showing all " + tickets.size() + " tickets");
                } else {
                    lblSearchStatus.setText("Found " + tickets.size() + " results for '" + tfSearch.getText() + "'");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            lblSearchStatus.setText("Error parsing search results");
        }
    }

    // Delete
    @FXML
    protected void onDeleteClick() {
        Ticket selected = ticketsTable.getSelectionModel().getSelectedItem(); // Check the chosen Ticket
        if (selected == null) {
            lblSearchStatus.setText("Select a ticket first"); // Telling the user to choose Ticket if he didn't
            return;
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("action", "ticket/delete");    // Create the header
        client.sendRequest(new Request(headers, selected)); // Create response

        // Local remove from the table
        ticketsTable.getItems().remove(selected);
        lblSearchStatus.setText("Deleted ticket ID: " + selected.getId());
    }

    // Clear the form for user comfort
    private void clearAddForm() {
        tfId.clear();
        tfEventName.clear();
        tfCustomerName.clear();
        tfPrice.clear();
    }
}