package com.example;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class CalendarGUI extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private CalendarManager manager = new CalendarManager();
    private Map<String, String> baseUtilisateurs = new HashMap<>();
    private String userConnecte = null;

    public CalendarGUI() {
        baseUtilisateurs.put("Roger", "Chat");
        baseUtilisateurs.put("Pierre", "KiRouhl");

        setTitle("Calendrier - Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);

        mainPanel.add(creerEcranConnexion(), "LOGIN");
        mainPanel.add(creerEcranPrincipal(), "APP");

        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel creerEcranConnexion() {
        JPanel panel = new JPanel(new GridBagLayout());
        JTextField txtUser = new JTextField(15);
        JPasswordField txtPass = new JPasswordField(15);
        JPasswordField txtConfirm = new JPasswordField(15);

        JLabel lblConfirm = new JLabel("Confirmer le mot de passe :");
        lblConfirm.setVisible(false);
        txtConfirm.setVisible(false);

        JButton btnActionPrincipal = new JButton("Se connecter");
        JButton btnSwitchMode = new JButton("Pas de compte ? S'inscrire");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Utilisateur :"), gbc);
        gbc.gridx = 1;
        panel.add(txtUser, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Mot de passe :"), gbc);
        gbc.gridx = 1;
        panel.add(txtPass, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblConfirm, gbc);
        gbc.gridx = 1;
        panel.add(txtConfirm, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(btnActionPrincipal, gbc);
        gbc.gridy = 4;
        panel.add(btnSwitchMode, gbc);

        btnSwitchMode.addActionListener(e -> {
            boolean estEnModeLogin = btnActionPrincipal.getText().equals("Se connecter");
            lblConfirm.setVisible(estEnModeLogin);
            txtConfirm.setVisible(estEnModeLogin);
            btnActionPrincipal.setText(estEnModeLogin ? "Finaliser l'inscription" : "Se connecter");
            btnSwitchMode.setText(estEnModeLogin ? "Déjà un compte ? Se connecter" : "Pas de compte ? S'inscrire");
            panel.revalidate();
            panel.repaint();
        });

        btnActionPrincipal.addActionListener(e -> {
            String u = txtUser.getText();
            String p = new String(txtPass.getPassword());
            if (btnActionPrincipal.getText().equals("Se connecter")) {
                if (baseUtilisateurs.containsKey(u) && baseUtilisateurs.get(u).equals(p)) {
                    userConnecte = u;
                    cardLayout.show(mainPanel, "APP");
                } else {
                    JOptionPane.showMessageDialog(this, "Identifiants incorrects.");
                }
            } else {
                String c = new String(txtConfirm.getPassword());
                if (u.isEmpty() || p.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Veuillez remplir les champs.");
                } else if (!p.equals(c)) {
                    JOptionPane.showMessageDialog(this, "Les mots de passe ne correspondent pas.");
                } else {
                    baseUtilisateurs.put(u, p);
                    JOptionPane.showMessageDialog(this, "Compte créé !");
                    btnSwitchMode.doClick();
                }
            }
        });
        return panel;
    }

    private JPanel creerEcranPrincipal() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea area = new JTextArea();
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        area.setEditable(false);

        JPanel toolBar = new JPanel(new GridLayout(2, 3, 10, 10));
        toolBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btn1 = new JButton("Voir Events");
        JButton btn2 = new JButton("RDV Perso");
        JButton btn3 = new JButton("Réunion");
        JButton btn4 = new JButton("Périodique");
        JButton btn6 = new JButton("Supprimer ID");
        JButton btn5 = new JButton("Déconnexion");

        toolBar.add(btn1);
        toolBar.add(btn2);
        toolBar.add(btn3);
        toolBar.add(btn4);
        toolBar.add(btn6);
        toolBar.add(btn5);

        panel.add(toolBar, BorderLayout.NORTH);
        panel.add(new JScrollPane(area), BorderLayout.CENTER);

        btn1.addActionListener(e -> {
            area.setText("--- VOS ÉVÉNEMENTS ---\n");
            if (manager.getEvents().isEmpty())
                area.append("Aucun événement.");
            manager.getEvents()
                    .forEach(ev -> area.append("- ID: " + ev.id().value() + " | " + ev.description() + "\n"));
        });

        btn2.addActionListener(e -> ouvrirFormulaireRdv());
        btn3.addActionListener(e -> ouvrirFormulaireReunion());
        btn4.addActionListener(e -> ouvrirFormulairePeriodique());

        btn5.addActionListener(e -> {
            userConnecte = null;
            cardLayout.show(mainPanel, "LOGIN");
        });

        btn6.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("ID de l'événement à supprimer :");
            if (id != null) {
                try {
                    manager.supprimerParId(EventId.fromString(id));
                    btn1.doClick();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ID invalide.");
                }
            }
        });

        return panel;
    }

    private void ouvrirFormulaireRdv() {
        JTextField fTitre = new JTextField();
        JTextField fJ = new JTextField("26");
        JTextField fM = new JTextField("03");
        JTextField fA = new JTextField("2026");
        JTextField fH = new JTextField("10");
        JTextField fMin = new JTextField("00");
        JTextField fDur = new JTextField("60");

        Object[] message = { "Titre:", fTitre, "Date (J/M/A):", fJ, fM, fA, "Heure (H:m):", fH, fMin, "Durée (min):",
                fDur };

        if (JOptionPane.showConfirmDialog(this, message, "Nouveau RDV",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                manager.ajouter(new RdvPersonnel(EventId.generate(), new TitreEvenement(fTitre.getText()),
                        new DateEvenement(LocalDate.of(Integer.parseInt(fA.getText()), Integer.parseInt(fM.getText()),
                                Integer.parseInt(fJ.getText()))),
                        new HeureDebut(LocalTime.of(Integer.parseInt(fH.getText()), Integer.parseInt(fMin.getText()))),
                        new DureeEvenement(Integer.parseInt(fDur.getText()))));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            }
        }
    }

    private void ouvrirFormulaireReunion() {
        JTextField fTitre = new JTextField();
        JTextField fLieu = new JTextField();
        JTextField fPart = new JTextField();
        JTextField fJ = new JTextField("26");
        JTextField fM = new JTextField("03");
        JTextField fA = new JTextField("2026");
        JTextField fH = new JTextField("14");
        JTextField fMin = new JTextField("00");
        JTextField fDur = new JTextField("90");

        Object[] message = { "Titre:", fTitre, "Lieu:", fLieu, "Participants:", fPart, "Date (J/M/A):", fJ, fM, fA,
                "Heure (H:m):", fH, fMin, "Durée (min):", fDur };

        if (JOptionPane.showConfirmDialog(this, message, "Nouvelle Réunion",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                manager.ajouter(new Reunion(EventId.generate(), new TitreEvenement(fTitre.getText()),
                        new LieuEvenement(fLieu.getText()), new ParticipantEvenement(fPart.getText()),
                        new DateEvenement(LocalDate.of(Integer.parseInt(fA.getText()), Integer.parseInt(fM.getText()),
                                Integer.parseInt(fJ.getText()))),
                        new HeureDebut(LocalTime.of(Integer.parseInt(fH.getText()), Integer.parseInt(fMin.getText()))),
                        new DureeEvenement(Integer.parseInt(fDur.getText()))));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            }
        }
    }

    private void ouvrirFormulairePeriodique() {
        JTextField fTitre = new JTextField();
        JTextField fJ = new JTextField("26");
        JTextField fM = new JTextField("03");
        JTextField fA = new JTextField("2026");
        JTextField fH = new JTextField("09");
        JTextField fMin = new JTextField("00");
        JTextField fDur = new JTextField("60");
        JTextField fFreq = new JTextField("7");

        Object[] message = { "Titre:", fTitre, "Début (J/M/A):", fJ, fM, fA, "Heure (H:m):", fH, fMin, "Durée (min):",
                fDur, "Fréquence (jours):", fFreq };

        if (JOptionPane.showConfirmDialog(this, message, "Nouveau Périodique",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                manager.ajouter(new EventPeriodique(EventId.generate(), new TitreEvenement(fTitre.getText()),
                        new DateEvenement(LocalDate.of(Integer.parseInt(fA.getText()), Integer.parseInt(fM.getText()),
                                Integer.parseInt(fJ.getText()))),
                        new HeureDebut(LocalTime.of(Integer.parseInt(fH.getText()), Integer.parseInt(fMin.getText()))),
                        new DureeEvenement(Integer.parseInt(fDur.getText())),
                        new FrequenceEvenement(Integer.parseInt(fFreq.getText()))));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalendarGUI::new);
    }
}