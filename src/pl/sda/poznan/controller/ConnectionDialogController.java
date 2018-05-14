package pl.sda.poznan.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import pl.sda.poznan.viewmodel.ConnectionDialogViewModel;

public class ConnectionDialogController {

    @FXML
    public TextField serverAdressTextField;

    @FXML
    public TextField playerNameTextField;

    public ConnectionDialogViewModel getConnectionDetails(){
        return new ConnectionDialogViewModel(serverAdressTextField.getText(), playerNameTextField.getText());
    }
}
