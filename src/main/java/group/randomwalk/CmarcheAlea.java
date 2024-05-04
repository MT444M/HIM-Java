package group.randomwalk;



public class CmarcheAlea {
    private int N; // taille de la marche aleatoire
    private int Realisation; // nombre de réalisations
    private int cas; // cas 1D ou 2D
    private int[][] MarcheX;  // cord de x
    private int[][] MarcheY; // cord de y

    // Constructeur par défaut
    public CmarcheAlea() {
        this.N = 20;
        this.Realisation = 4;
        this.cas = 0; // cas 1D
        this.MarcheX = new int[Realisation][N];
        this.MarcheY = new int[Realisation][N];

        // Initialisation des tableaux avec des zéros
        for (int i = 0; i < Realisation; i++) {
            for (int j = 0; j < N; j++) {
                MarcheX[i][j] = 0;
                MarcheY[i][j] = 0;
            }
        }
    }

    // Constructeur avec paramètres
    public CmarcheAlea(int taille, int realisation, int type) {
        this.N = taille;
        this.Realisation = realisation;
        this.cas = type;
        this.MarcheX = new int[realisation][taille];
        this.MarcheY = new int[realisation][taille];

        // Initialisation des tableaux avec des zéros
        for (int i = 0; i < realisation; i++) {
            for (int j = 0; j < taille; j++) {
                MarcheX[i][j] = 0;
                MarcheY[i][j] = 0;
            }
        }
    }

    // Méthode pour obtenir un nombre aléatoire entier valant 1 ou -1
    public int RandomBinaire() {
        return Math.random() < 0.5 ? 1 : -1;
    }

    // Méthode de remplissage des éléments constitutifs des marches aléatoires
    // Méthode pour remplir les éléments des marches aléatoires
    public void Remplit() {
        if (cas == 0) {
            // Marche aléatoire 1D
            for (int i = 0; i < Realisation; i++) {
                // Remplir MarcheX avec des nombres entiers de 0 à N - 1
                for (int j = 0; j < N; j++) {
                    MarcheX[i][j] = j;
                }

                // Remplir MarcheY selon la règle (1)
                for (int j = 1; j < N; j++) {
                    MarcheY[i][j] = MarcheY[i][j - 1] + RandomBinaire();
                }
            }

        } else {
            // Marche aléatoire 2D
            for (int i = 0; i < Realisation; i++) {
                // Remplir MarcheX et MarcheY selon les règles (2) et (3)
                for (int j = 1; j < N; j++) {
                    MarcheX[i][j] = MarcheX[i][j - 1] + RandomBinaire();
                    MarcheY[i][j] = MarcheY[i][j - 1] + RandomBinaire();
                }
            }
        }
    }


    // Méthode pour calculer la moyenne µ des termes de chaque réalisation de marche aléatoire
    public double calculMoyenne() {
        double somme = 0;

        for (int i = 0; i < Realisation; i++) {
            for (int j = 0; j < N; j++) {
                // Ajoutez le terme actuel à la somme
                somme += (cas == 0) ? MarcheX[i][j] : MarcheY[i][j];
            }
        }

        // Calcul de la moyenne
        return somme / (N * Realisation);
    }


    // Méthodes d'accès aux données membres
    public int get_N() {
        return N;
    }

    public int get_Realisation() {
        return Realisation;
    }

    public int get_Cas() {
        return cas;
    }

    // Méthodes d'écriture des données membres (si nécessaire)
    public void set_N(int n) {
        N = n;
    }

    public void set_Realisation(int realisation) {
        Realisation = realisation;
    }

    public void set_Cas(int cas) {
        this.cas = cas;
    }

    // Méthode pour accéder à un élément spécifique de MarcheX
    public int getMarcheX(int i, int j) {
        return MarcheX[i][j];
    }

    // Méthode pour accéder à un élément spécifique de MarcheY
    public int getMarcheY(int i, int j) {
        return MarcheY[i][j];
    }

    // Méthode d'affichage des marches aléatoires
    public String Affiche() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < Realisation; i++) {
            result.append("Réalisation ").append(i + 1).append(":\n");
            for (int j = 0; j < N; j++) {
                result.append("( x[").append(j).append("] =").append(MarcheX[i][j]).append(", y[").append(j).append("] =").append(MarcheY[i][j]).append(" )\n");
            }
        }

        return result.toString();
    }

    // Méthode d'affichage de la i-ème réalisation de marche aléatoire
    public String Affiche(int réalisationIndex) {
        StringBuilder result = new StringBuilder();

        result.append("Réalisation ").append(réalisationIndex + 1).append(":\n");

        for (int j = 0; j < N; j++) {
            result.append("( x[").append(j).append("] =").append(MarcheX[réalisationIndex][j]).append(", y[").append(j).append("] =").append(MarcheY[réalisationIndex][j]).append(" )\n");
        }

        return result.toString();
    }

    public String Affiche(int réalisationIndex, int j) {
        return "( X[" + j + "] =" + MarcheX[réalisationIndex][j] + ", Y[" + j + "] =" + MarcheY[réalisationIndex][j] + " )\n";
    }


}


