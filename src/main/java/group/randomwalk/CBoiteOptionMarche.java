package group.randomwalk;

import javax.swing.*;
import java.awt.event.*;
import javafx.scene.paint.Color;


public class CBoiteOptionMarche extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel N;
    private JTextField valeur_N;
    private JComboBox Box_couleur;
    private JTextField valeur_realisation;
    private JComboBox choix_marche;
    private boolean okClicked;

    public CBoiteOptionMarche() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);



        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // Ajoutez des choix au JComboBox
        choix_marche.addItem("1D");
        choix_marche.addItem("2D");

        // Ajoutez des choix au JComboBox
        Box_couleur.addItem("blanc");
        Box_couleur.addItem("noir");
        Box_couleur.addItem("rouge");
        Box_couleur.addItem("Vert");
    }

    public int getNValue() {
        try {
            String NText = valeur_N.getText().trim();

            // Vérifiez si le nombre est négatif
            if (Integer.parseInt(NText) < 0 || NText.isEmpty()) {
                throw new NumberFormatException("Le nombre ne peut pas être négatif ou vide");
            }
            return Integer.parseInt(NText);

        } catch (NumberFormatException e) {
            // En cas d'erreur de saisie, affichez une boîte de dialogue d'avertissement
            JOptionPane.showMessageDialog(null, "Veuillez saisir un nombre valide et non négatif.", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            // Terminez l'application
            System.exit(0);
            return 0; // Cette ligne est ajoutée pour que la méthode ait une valeur de retour dans tous les cas
        }
    }

    public int getRealisationValue() {

        String realisationValue = valeur_realisation.getText().trim();

        while (realisationValue == null || Integer.parseInt(realisationValue) <= 0) {
            try {
                // Réinitialiser les champs de la boîte de dialogue
                valeur_realisation.setText("");
                // Vous pouvez également réinitialiser d'autres champs si nécessaire

                String realisationText = JOptionPane.showInputDialog("Veuillez saisir le nombre de réalisations :");

                // Vérifiez si la chaîne est vide
                if (realisationText == null || realisationText.isEmpty()) {
                    throw new NumberFormatException("Le nombre ne peut pas être vide.");
                }

                // Convertissez la chaîne en entier
                realisationValue = realisationText.trim();

                // Vérifiez si le nombre est négatif ou égal à zéro
                if (Integer.parseInt(realisationValue) <= 0) {
                    throw new NumberFormatException("Le nombre doit être supérieur à zéro.");
                }

            } catch (NumberFormatException e) {
                // En cas d'erreur de saisie, affichez une boîte de dialogue d'avertissement
                JOptionPane.showMessageDialog(null, "Veuillez saisir un nombre valide et supérieur à zéro.", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                realisationValue = null; // Réinitialiser la valeur pour forcer la répétition de la boucle
            }
        }

        return Integer.parseInt(realisationValue);
    }


    public String getMarcheType() {
        return (String) choix_marche.getSelectedItem();
    }

    public Color getCouleurChoisie() {
        String couleurSelectionnee = (String) Box_couleur.getSelectedItem();

        switch (couleurSelectionnee.toLowerCase()) {
            case "blanc":
                return Color.WHITE;
            case "noir":
                return Color.BLACK;
            case "rouge":
                return Color.RED;
            case "vert":
                return Color.GREEN;
            // Ajoutez d'autres cas selon les couleurs que vous avez dans votre JComboBox
            default:
                // Par défaut, retournez une couleur noire
                return Color.BLACK;
        }
    }
//===========================================

    private void onOK() {
        // add your code here
        dispose();
        // Marquez que "OK" a été cliqué
        okClicked = true;

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    public boolean isOkClicked() {
        return okClicked;
    }

    public static void main(String[] args) {
        CBoiteOptionMarche dialog = new CBoiteOptionMarche();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
