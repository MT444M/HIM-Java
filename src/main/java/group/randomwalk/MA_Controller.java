package group.randomwalk;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;


public class MA_Controller {
    @FXML // fx:id="AffichageModeDessin"
    private MenuItem AffichageModeDessin; // Value injected by FXMLLoader

    @FXML // fx:id="AffichageModeTexte"
    private MenuItem AffichageModeTexte; // Value injected by FXMLLoader

    @FXML // fx:id="AreaChart"
    private AreaChart<?, ?> AreaChart; // Value injected by FXMLLoader
    @FXML // fx:id="X_axis"
    private CategoryAxis X_axis; // Value injected by FXMLLoader

    @FXML // fx:id="Y_axis"
    private NumberAxis Y_axis; // Value injected by FXMLLoader

    @FXML // fx:id="Text_00"
    private TextArea Text_00; // Value injected by FXMLLoader

    @FXML // fx:id="Text_10"
    private TextArea Text_10; // Value injected by FXMLLoader
    @FXML // fx:id="Text_10"
    private TextArea Text_20; // Value injected by FXMLLoader
    @FXML // fx:id="Text_10"
    private TextArea Text_30; // Value injected by FXMLLoader
    @FXML // fx:id="GridText"
    private GridPane GridText; // Value injected by FXMLLoader

    @FXML
    private Canvas canvas;

    private Thread animationThread;


    // Référence à votre instance de CmarcheAlea
    private CmarcheAlea marcheAlea;

    // Méthode appelée lorsque l'utilisateur sélectionne "Mode Texte
    @FXML
    void onAffichageModeTexte(ActionEvent event) {
        AreaChart.setDisable(true);
        AreaChart.setVisible(false);

        GridText.setVisible(true);
        GridText.setDisable(false);

        canvas.setDisable(true);
        canvas.setVisible(false);

        // Créez une instance de la boîte de dialogue
        marcheAlea = new CmarcheAlea();

        CBoiteOptionMarche optionDialog = new CBoiteOptionMarche();

        // Affichez la boîte de dialogue et attendez jusqu'à ce qu'elle soit fermée
        optionDialog.pack();
        optionDialog.setVisible(true);

        // Vérifiez si l'utilisateur a cliqué sur "OK"
        if (optionDialog.isOkClicked()) {
            // Récupérez les valeurs saisies dans la boîte de dialogue
            int nValue = optionDialog.getNValue();
            int realisationValue = optionDialog.getRealisationValue();
            String marcheType = optionDialog.getMarcheType();

            // Mettez à jour les paramètres de votre instance CmarcheAlea
            marcheAlea.set_N(nValue);
            marcheAlea.set_Realisation(realisationValue);
            marcheAlea.set_Cas("1D".equals(marcheType) ? 0 : 1);

            // Réinitialisez et remplissez la marche aléatoire avec les nouveaux paramètres
        }
        // Initialisation de votre instance de CmarcheAlea si ce n'est pas déjà fait
        marcheAlea.Remplit();

        for (int i = 0; i < marcheAlea.get_Realisation(); i++) {
            // Obtenez le textarea correspondant à la réalisation actuelle
            TextArea textArea = getTextAreaForRealisation(i);
            assert textArea != null;
            textArea.clear();
            //textArea.setStyle("-fx-text-fill: green; -fx-control-inner-background: lightgray;");

            Color couleur = optionDialog.getCouleurChoisie();
            // Convertissez la couleur en format CSS rgba
            String couleurCSS = String.format("-fx-control-inner-background: rgba(%d, %d, %d);",
                    (int) (couleur.getRed() * 255),
                    (int) (couleur.getGreen() * 255),
                    (int) (couleur.getBlue() * 255));

            textArea.setStyle(couleurCSS + "-fx-text-fill: black;");  // Définir la couleur du texte

            // Ajoutez le texte dans le textarea
            textArea.appendText(marcheAlea.Affiche(i));
        }
    }

    private TextArea getTextAreaForRealisation(int réalisationIndex) {
        switch (réalisationIndex) {
            case 0:
                return Text_00;
            case 1:
                return Text_10;
            case 2:
                return Text_20;
            case 3:
                return Text_30;
            default:
                return null;
        }
    }

    @FXML
    void onAffichageGraphique(ActionEvent event) {
        // Activer ou désactiver les composants en fonction du mode
        GridText.setDisable(true);
        GridText.setVisible(false);

        AreaChart.setVisible(true);
        AreaChart.setDisable(false);


        // Initialisation de votre instance de CmarcheAlea si ce n'est pas déjà fait
        marcheAlea = new CmarcheAlea(1000, 2, 1);
        marcheAlea.Remplit();


        // Efface les séries de données précédentes
        AreaChart.getData().clear();

        // Ajoutez des séries de données pour chaque réalisation
        for (int i = 0; i < marcheAlea.get_Realisation(); i++) {
            XYChart.Series series = new XYChart.Series();
            series.setName("Réalisation " + (i + 1));

            for (int j = 0; j < marcheAlea.get_N(); j++) {
                series.getData().add(new XYChart.Data<>(String.valueOf(marcheAlea.getMarcheX(i, j)), marcheAlea.getMarcheY(i, j)));
            }

            // Ajoutez la série au AreaChart
            AreaChart.getData().add(series);
        }
    }

    //==========================================ANIMATION
    @FXML
    void onDemarrerAnimation(ActionEvent event) {
        AreaChart.setDisable(true);
        AreaChart.setVisible(false);

        GridText.setVisible(true);
        GridText.setDisable(false);

        // Arrêtez le thread existant s'il y en a un
        //arreterAnimation();
        onArreterAnimation(event);

        // Obtenez la valeur de N depuis la boîte de dialogue

        // Créez une nouvelle instance de CmarcheAlea avec la valeur de N
        marcheAlea = new CmarcheAlea(10, 3, 0);
        marcheAlea.Remplit();

        // Créez et démarrez le thread d'animation avec la valeur de N
        animationThread = new Thread(() -> afficherRalisationsAvecTemporisation( 10));
        animationThread.start();
    }

    private void afficherRalisationsAvecTemporisation(int N) {
        // Affichez les réalisations une par une avec une temporisation
        for (int i = 0; i < marcheAlea.get_Realisation(); i++) {
            if (Thread.interrupted()) {
                // Si le thread est interrompu, sortir de la boucle
                return;
            }

            final int realisationIndex = i; // Variable finale pour l'utilisation dans la lambda
            // Obtenez le TextArea correspondant à la réalisation actuelle
            TextArea textArea = getTextAreaForRealisation(realisationIndex);
            assert textArea != null;
            textArea.clear(); //nettoyé
            textArea.setStyle("-fx-text-fill: green; -fx-control-inner-background: lightgray;");
            textArea.appendText("Réalisation " + realisationIndex + ":\n");
            for (int j = 0; j < N; j++) {
                final int coordIndex = j; // Variable finale pour l'utilisation dans la lambda
                Platform.runLater(() -> {
                    // Ajoutez le texte dans le TextArea
                    textArea.appendText(marcheAlea.Affiche(realisationIndex, coordIndex));
                });

                // Pause temporaire
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // Si le thread est interrompu pendant le sommeil, sortir de la boucle
                    Thread.currentThread().interrupt(); // Réinitialise le statut d'interruption
                    return;
                }
            }
        }
    }

    @FXML
    void onArreterAnimation(ActionEvent event) {
        if (animationThread != null && animationThread.isAlive()) {
            animationThread.interrupt();
        }
    }

    @FXML
    void onRemiseZero(ActionEvent event) {
        // Désactiver AreaChart et activer GridText
        AreaChart.setDisable(true);
        AreaChart.setVisible(false);

        GridText.setVisible(false);
        GridText.setDisable(true);

        canvas.setVisible(false);
        canvas.setDisable(true);

        // Réinitialiser les champs Text_00 ... Text_30
        Text_00.clear();
        Text_10.clear();
        Text_20.clear();
        Text_30.clear();

        // Restaurer les couleurs par défaut
        // Vous devrez remplacer les couleurs par défaut par celles que vous souhaitez utiliser
        String couleurCSS = "-fx-text-fill: black; -fx-control-inner-background: white;";
        Text_00.setStyle(couleurCSS);
        Text_10.setStyle(couleurCSS);
        Text_20.setStyle(couleurCSS);
        Text_30.setStyle(couleurCSS);
    }

    @FXML
    void onQuitter(ActionEvent event) {
        System.exit(0);
    }

    //===============================Animation DESSIN
    private void dessinerAxesAvecGraduations(GraphicsContext gc, int echelle) {
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();

        double axeXCentre = canvasWidth / 2;
        double axeYCentre = canvasHeight / 2;

        // Dessiner l'axe des X
        gc.strokeLine(0, axeYCentre, canvasWidth, axeYCentre);
        // Dessiner les graduations sur l'axe des X
        for (int x = 0; x <= canvasWidth; x += echelle) {
            gc.strokeLine(x, axeYCentre - 5, x, axeYCentre + 5);

            // Afficher la valeur numérique à côté de la graduation
            //gc.fillText(Integer.toString((int) (x - axeXCentre)), x - 10, axeYCentre + 20);
        }

        // Dessiner l'axe des Y
        gc.strokeLine(axeXCentre, 0, axeXCentre, canvasHeight);
        // Dessiner les graduations sur l'axe des Y
        for (int y = 0; y <= canvasHeight; y += echelle) {
            gc.strokeLine(axeXCentre - 5, y, axeXCentre + 5, y);

            // Afficher la valeur numérique à côté de la graduation
            //gc.fillText(Integer.toString((int) (axeYCentre - y)), axeXCentre + 10, y + 5);
        }
    }

    private void animerDessin() {
        GraphicsContext gc = canvas.getGraphicsContext2D();


        // Créez une nouvelle instance de CmarcheAlea avec la valeur de N
        marcheAlea = new CmarcheAlea(1000, 2, 1);
        marcheAlea.Remplit();

        // Effacez le dessin précédent
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Ajoutez un titre
        String titre = "Animation Marche Aléatoire";
        gc.setFill(Color.RED); // Couleur du texte
        gc.fillText(titre, canvas.getWidth() / 2 -20 , 20);

        for (int i = 0; i < marcheAlea.get_Realisation(); i++) {
            for (int j = 0; j < marcheAlea.get_N(); j++) {

                // Dessinez les axes avec les graduations
                dessinerAxesAvecGraduations(gc, 10);
                // Dessinez le point actuel
                gc.setFill(Color.RED);
                double x = canvas.getWidth() / 2 + marcheAlea.getMarcheX(i, j);
                double y = canvas.getHeight() / 2 - marcheAlea.getMarcheY(i, j);
                gc.fillOval(x, y, 3, 3); // Vous pouvez ajuster la taille du point

                // Attendez 500 millisecondes avant de passer au point suivant
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                // Si le thread est interrompu pendant le sommeil, nettoyez et quittez la méthode
                Thread.currentThread().interrupt(); // Rétablit le statut d'interruption
                return;

            }
            }
        }
    }

    // Déclarer une variable pour le thread global
    private Thread animationDessinThread;

    @FXML
    void onAnimDessin(ActionEvent event) {

        AreaChart.setDisable(true);
        AreaChart.setVisible(false);

        GridText.setVisible(false);
        GridText.setDisable(true);

        canvas.setDisable(false);
        canvas.setVisible(true);


        // Arrêter le thread précédent s'il est en cours
        if (animationDessinThread != null && animationDessinThread.isAlive()) {
            animationDessinThread.interrupt();
        }

        // Créer un nouveau thread d'animation
        animationDessinThread = new Thread(() -> {
            animerDessin();
        });

        // Démarrer le nouveau thread
        animationDessinThread.start();
    }

    @FXML
    void onArreterDessin(ActionEvent event) {
        // Arrêter le thread d'animation actuel s'il est en cours
        if (animationDessinThread != null && animationDessinThread.isAlive()) {
            animationDessinThread.interrupt();
        }
    }

}
