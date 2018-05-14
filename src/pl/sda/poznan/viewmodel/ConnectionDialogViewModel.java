package pl.sda.poznan.viewmodel;

public class ConnectionDialogViewModel {

    private String serverAdress;
    private String playerName;

    public ConnectionDialogViewModel(String serverAdress, String playerName) {
        this.serverAdress = serverAdress;
        this.playerName = playerName;
    }

    public String getServerAdress() {
        return serverAdress;
    }

    public void setServerAdress(String serverAdress) {
        this.serverAdress = serverAdress;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
