package com.hit.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable; // ייבוא חדש
import javafx.scene.control.*;
import com.hit.client.model.Request;
import com.hit.client.model.Response;
import com.hit.client.model.Ticket;
import com.hit.client.network.Client;

import java.net.URL; // ייבוא חדש
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle; // ייבוא חדש
import java.lang.reflect.Type;

// הוספנו "implements Initializable" לכותרת המחלקה
public class TicketController implements Initializable {

    @FXML private TextField tfId, tfEventName, tfCustomerName, tfPrice, tfSearch;
    @FXML private Label lblAddStatus, lblSearchStatus;
    @FXML private TableView<Ticket> ticketsTable;

    private Client client = new Client();
    private Gson gson = new Gson();

    // --- פונקציה חדשה: רצה אוטומטית כשהחלון נפתח ---
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // אנחנו קוראים לפונקציית החיפוש מיד בהתחלה.
        // מכיוון שתיבת החיפוש (tfSearch) ריקה כרגע,
        // השרת יבין שצריך להחזיר את כל הרשימה.
        onSearchClick();
    }

    // --- הוספת כרטיס ---
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

            // בונוס: רענון הטבלה אוטומטית אחרי הוספה (כדי שנראה את החדש מיד)
            clearAddForm();
            onSearchClick();

        } catch (NumberFormatException e) {
            lblAddStatus.setText("Error: Check numeric fields");
        } catch (Exception e) {
            lblAddStatus.setText("Error: " + e.getMessage());
        }
    }

    // --- חיפוש (כעת נקראת גם אוטומטית וגם ידנית) ---
    @FXML
    protected void onSearchClick() {
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("action", "ticket/search");

            Map<String, String> body = new HashMap<>();
            // אם הפונקציה נקראת מה-initialize, הטקסט פה ריק -> השרת יחזיר הכל
            body.put("searchQuery", tfSearch.getText());

            Request request = new Request(headers, body);
            String jsonResponse = client.sendRequest(request);

            if (jsonResponse != null) {
                Response responseObj = gson.fromJson(jsonResponse, Response.class);

                String listJson = gson.toJson(responseObj.getBody());
                Type listType = new TypeToken<List<Ticket>>(){}.getType();
                List<Ticket> tickets = gson.fromJson(listJson, listType);

                ObservableList<Ticket> data = FXCollections.observableArrayList(tickets);
                ticketsTable.setItems(data);

                // עדכון הסטטוס למשתמש
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

    // --- מחיקה ---
    @FXML
    protected void onDeleteClick() {
        Ticket selected = ticketsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            lblSearchStatus.setText("Select a ticket first");
            return;
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("action", "ticket/delete");
        client.sendRequest(new Request(headers, selected));

        // הסרה מקומית מהטבלה
        ticketsTable.getItems().remove(selected);
        lblSearchStatus.setText("Deleted ticket ID: " + selected.getId());
    }

    private void clearAddForm() {
        tfId.clear();
        tfEventName.clear();
        tfCustomerName.clear();
        tfPrice.clear();
    }
}