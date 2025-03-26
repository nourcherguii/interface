

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class essaye extends JFrame {
    private DefaultListModel<String> fileListModel;
    private JList<String> fileList;
    private JToolBar toolBar;
    private JButton toggleToolBarButton;
    private JLabel toggleToolBarButtonLabel;

    public essaye() {
        this.setTitle("Éditeur Graphique");
        this.setSize(800, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // ----- Création de la barre de menu -----
        JMenuBar menubar = new JMenuBar();

        JMenu fichier = new JMenu("Fichier");
        JMenu edition = new JMenu("Édition");
        JMenu afficher = new JMenu("Afficher");
        JMenu aide = new JMenu("Aide");

        fichier.add(new JMenuItem("📂 Ouvrir"));
        fichier.add(new JMenuItem("💾 Enregistrer"));
        fichier.add(new JMenuItem("📂 Enregistrer Sous"));
        fichier.add(new JMenuItem("🔤 Renommer"));
        fichier.add(new JMenuItem("❌ Quitter"));
        fichier.add(new JMenuItem("Fermer"));

        edition.add(new JMenuItem("↩ Annuler"));
        edition.add(new JMenuItem("📥 Copier"));
        edition.add(new JMenuItem("✂️ Coller"));
        edition.add(new JMenuItem("✂️ Couper"));
        edition.add(new JMenuItem("🗑 Supprimer"));

        afficher.add(new JMenuItem("Afficher la barre d'outils"));
        afficher.add(new JMenuItem("Mode plein écran"));
        JMenu themeMenu = new JMenu("Thème");
        themeMenu.add(new JMenuItem("Sombre"));
        themeMenu.add(new JMenuItem("Clair"));

        JMenu policeMenu = new JMenu("Police");
        policeMenu.add(new JMenuItem("Petite"));
        policeMenu.add(new JMenuItem("Moyenne"));
        policeMenu.add(new JMenuItem("Grande"));

        afficher.add(themeMenu);
        afficher.add(policeMenu);

        aide.add(new JMenuItem("❓ Aide"));
        aide.add(new JMenuItem("ℹ Information sur l'éditeur"));
        aide.add(new JMenuItem("📖 Tutoriel"));

        menubar.add(fichier);
        menubar.add(edition);
        menubar.add(afficher);
        menubar.add(aide);
        this.setJMenuBar(menubar);

        // ----- Barre d'outils -----
        toolBar = new JToolBar(JToolBar.VERTICAL);
        toolBar.setVisible(false);

        JButton undoButton = new JButton("↩"); // Annuler
        JButton redoButton = new JButton("↪"); // Rétablir
        JButton boldButton = new JButton("𝐁"); // Gras
        JButton italicButton = new JButton("𝘐"); // Italique
        JButton underlineButton = new JButton("U̲"); // Souligné
        JButton colorButton = new JButton("🎨"); // Sélecteur de couleur

        // Sélection de taille de texte
        String[] fontSizes = {"8pt", "10pt", "12pt", "14pt", "16pt"};
        JComboBox<String> fontSizeBox = new JComboBox<>(fontSizes);

        // Slider pour épaisseur du trait
        JSlider thicknessSlider = new JSlider(1, 10, 2);

        // Ajout des composants à la barre d'outils
        toolBar.add(undoButton);
        toolBar.add(redoButton);
        toolBar.addSeparator();
        toolBar.add(fontSizeBox);
        toolBar.add(boldButton);
        toolBar.add(italicButton);
        toolBar.add(underlineButton);
        toolBar.addSeparator();
        toolBar.add(colorButton);
        toolBar.add(thicknessSlider);

        this.add(toolBar, BorderLayout.WEST);

        // ----- Bouton pour afficher/masquer la barre d'outils -----
        toggleToolBarButton = new JButton("▶");
        toggleToolBarButtonLabel = new JLabel("Barre d'outil");

        toggleToolBarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolBar.setVisible(!toolBar.isVisible());
                if (toolBar.isVisible()) {
                    toggleToolBarButton.setText("◀");
                    toggleToolBarButtonLabel.setText("Masquer la barre d'outil");
                } else {
                    toggleToolBarButton.setText("▶");
                    toggleToolBarButtonLabel.setText("Barre d'outil");
                }
            }
        });

        JPanel togglePanel = new JPanel(new BorderLayout());
        togglePanel.add(toggleToolBarButton, BorderLayout.CENTER);
        togglePanel.add(toggleToolBarButtonLabel, BorderLayout.SOUTH);
        this.add(togglePanel, BorderLayout.WEST);

        // ----- Liste des fichiers -----
        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileList.setFixedCellHeight(25);
        JScrollPane fileScrollPane = new JScrollPane(fileList);
        fileScrollPane.setPreferredSize(new Dimension(150, 0));

        // Zone principale (éditeur graphique)
        JPanel canvas = new JPanel();
        canvas.setBackground(Color.WHITE);

        this.add(fileScrollPane, BorderLayout.EAST);
        this.add(canvas, BorderLayout.CENTER);

        // ----- Zone de commande -----
        JPanel commandPanel = new JPanel(new BorderLayout());
        JTextField commandField = new JTextField();
        JButton executeButton = new JButton("⚡ Exécuter");

        commandPanel.add(commandField, BorderLayout.CENTER);
        commandPanel.add(executeButton, BorderLayout.EAST);
        this.add(commandPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new essaye();
    }
}