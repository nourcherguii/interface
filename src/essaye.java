
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class essaye extends JFrame {
    private JToolBar toolBar;
    private JButton toggleGridButton;
    private boolean showGrid = false;
    private Color currentColor = Color.BLACK;
    private DrawingPanel canvas;
    private JPanel dynamicPanelShapes;
    private JPanel dynamicPanelContainers;
    private boolean isShapesPanelVisible = false;
    private boolean isContainersPanelVisible = false;

    public essaye() {
        this.setTitle("Éditeur Graphique");
        this.setSize(800, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // ----- Création de la barre de menu -----
        JMenuBar menubar = new JMenuBar();
        menubar.setBackground(Color.WHITE);

        JMenu fichier = new JMenu("Fichier");
        JMenu edition = new JMenu("Édition");
        JMenu afficher = new JMenu("Afficher");
        JMenu aide = new JMenu("Aide");

        fichier.add(new JMenuItem("📂 Ouvrir"));
        fichier.add(new JMenuItem("💾 Enregistrer"));
        fichier.add(new JMenuItem("📂 Enregistrer Sous"));
        fichier.add(new JMenuItem("🔤 Renommer"));
        JMenuItem quitter = new JMenuItem("❌ Quitter");
        quitter.addActionListener(e -> System.exit(0));
        fichier.add(quitter);

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

        // ----- Barre d'outils horizontale -----
        toolBar = new JToolBar(JToolBar.HORIZONTAL);
        toolBar.setBackground(Color.WHITE);

        JButton undoButton = new JButton("↩");
        JButton redoButton = new JButton("↪");
        JButton boldButton = new JButton("𝐁");
        JButton italicButton = new JButton("𝘐");
        JButton underlineButton = new JButton("U̲");
        JButton colorButton = new JButton("🎨");
        toggleGridButton = new JButton("Grille");

        String[] fontSizes = {"8pt", "10pt", "12pt", "14pt", "16pt"};
        JComboBox<String> fontSizeBox = new JComboBox<>(fontSizes);

        JSlider thicknessSlider = new JSlider(1, 10, 2);

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
        toolBar.add(toggleGridButton);

        colorButton.addActionListener(e -> {
            currentColor = JColorChooser.showDialog(this, "Choisir une couleur", currentColor);
            if (currentColor == null) {
                currentColor = Color.BLACK;
            }
        });

        toggleGridButton.addActionListener(e -> {
            showGrid = !showGrid;
            canvas.repaint();
        });

        this.add(toolBar, BorderLayout.NORTH);

        // ----- Panneau vertical fixe -----
        JPanel sidePanel = new JPanel(new GridLayout(2, 1));
        JButton shapesButton = new JButton("Shapes");
        JButton containersButton = new JButton("Containers");
        sidePanel.add(shapesButton);
        sidePanel.add(containersButton);
        this.add(sidePanel, BorderLayout.WEST);

        // Panneaux dynamiques
        dynamicPanelShapes = new JPanel();
        dynamicPanelShapes.setLayout(new FlowLayout());
        dynamicPanelShapes.setBackground(Color.GRAY);
        dynamicPanelShapes.setVisible(false);
        dynamicPanelShapes.add(new JButton("Triangle"));
        dynamicPanelShapes.add(new JButton("Cercle"));

        dynamicPanelContainers = new JPanel();
        dynamicPanelContainers.setLayout(new FlowLayout());
        dynamicPanelContainers.setBackground(Color.GRAY);
        dynamicPanelContainers.setVisible(false);
        dynamicPanelContainers.add(new JButton("Boîte"));
        dynamicPanelContainers.add(new JButton("Panneau"));

        // Panneau conteneur des panneaux dynamiques
        JPanel dynamicContainer = new JPanel();
        dynamicContainer.setLayout(new GridLayout(2, 1));
        dynamicContainer.add(dynamicPanelShapes);
        dynamicContainer.add(dynamicPanelContainers);

        // Zone principale avec le panneau de dessin
        canvas = new DrawingPanel();
        canvas.setBackground(Color.WHITE);

        // Panneau central contenant dynamicContainer et canvas
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(dynamicContainer, BorderLayout.WEST);
        centerPanel.add(canvas, BorderLayout.CENTER);
        this.add(centerPanel, BorderLayout.CENTER);

        // Actions des boutons
        shapesButton.addActionListener(e -> {
            isShapesPanelVisible = !isShapesPanelVisible;
            isContainersPanelVisible = false;
            dynamicPanelShapes.setVisible(isShapesPanelVisible);
            dynamicPanelContainers.setVisible(false);
            this.revalidate();
            this.repaint();
        });

        containersButton.addActionListener(e -> {
            isContainersPanelVisible = !isContainersPanelVisible;
            isShapesPanelVisible = false;
            dynamicPanelContainers.setVisible(isContainersPanelVisible);
            dynamicPanelShapes.setVisible(false);
            this.revalidate();
            this.repaint();
        });


        this.setVisible(true);
    }

    // Classe interne pour dessiner la grille
    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (showGrid) {
                dessinerGrille(g);
            }
        }

        private void dessinerGrille(Graphics g) {
            g.setColor(Color.LIGHT_GRAY);
            for (int i = 0; i < getWidth(); i += 20) {
                g.drawLine(i, 0, i, getHeight());
            }
            for (int j = 0; j < getHeight(); j += 20) {
                g.drawLine(0, j, getWidth(), j);
            }
        }
    }

    public static void main(String[] args) {
        new essaye();
    }
}
