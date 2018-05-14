package pl.sda.poznan.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import pl.sda.poznan.Message;
import pl.sda.poznan.Transmission;
import pl.sda.poznan.viewmodel.ConnectionDialogViewModel;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


//Executing when user takes NewGame
public class MainWindowController {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private Transmission transmission;


    public void handleClick(MouseEvent mouseEvent) {
        Label source = (Label) mouseEvent.getSource();
        System.out.println(source.getId());
    }

    public void connectToServerAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("ConnectionDialogWindow.fxml"));

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Connect to server");
        dialog.setHeaderText("Fill data");

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> optionalType = dialog.showAndWait();
        optionalType.ifPresent(buttonType -> {
            ConnectionDialogController controller = fxmlLoader.getController();
            ConnectionDialogViewModel connectionDetails = controller.getConnectionDetails();
            connectToServer(connectionDetails);
        });

    }

    public void connectToServer(ConnectionDialogViewModel viewModel){
        logger.log(Level.INFO, String.format("Trying to connect server at adress {} with username {}",
                viewModel.getServerAdress(), viewModel.getPlayerName()));

        String[] adress = viewModel.getServerAdress().split(":");
        String host = adress[0];
        int port = Integer.parseInt(adress[1]);
        // todo: add server validation - display error dialog if sth is wrong
        try {
            //Trying to make a connection on client side and sending Connect message
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port));
            this.transmission = new Transmission(socket);
            transmission.sendObject(Message.builder()
                    .header("Connect")
                    .data(viewModel.getPlayerName())
                    .build());

            Object o = null;
            try {
                o = transmission.readObject();
                Message message = (Message) o;
                logger.info(String.format("Received message %s", message.getHeader()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            logger.log(Level.INFO, "Cannot connect to server: " + e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something went wrong...");
            alert.setContentText("Could not connect to server");
            alert.showAndWait();
        }
    }
}
