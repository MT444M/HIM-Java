package group.randomwalk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MA_2App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MA_2App.class.getResource("MarcheAleatoire.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        //stage.show();

        // Intégrer votre classe CmarcheAlea
        //CmarcheAlea marcheAlea = new CmarcheAlea();

        // Appel de la méthode Remplit()
        //marcheAlea.Remplit();

        // Appel de la méthode Affiche()
        //marcheAlea.Affiche();

        // Appel de la méthode Affiche(int)
        //int i = 0; // Mettez ici l'indice de la réalisation que vous souhaitez afficher
        //marcheAlea.Affiche(i);
    }

    public static void main(String[] args) {
        launch();
    }
}