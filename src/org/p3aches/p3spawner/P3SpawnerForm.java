/*
 * Created by JFormDesigner on Sat Mar 02 15:46:17 EST 2013
 */

package org.p3aches.p3spawner;

import org.powerbot.core.event.listeners.PaintListener;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

/**
 * @author garrett bond
 */
public class P3SpawnerForm extends JDialog {
    public P3SpawnerForm(Frame owner) {
        super(owner);
        initComponents();
        Image edvilImg = null,edvilObeliskImg = null,
                catherbyImg = null, catherbyObeliskImg = null,
                cwarsImg = null, cwarsObeliskImg = null, lleytaImg = null;
        try {
            edvilImg = ImageIO.read(new URL("http://p3ach.com/images/edvil.png"));
            edvilObeliskImg = ImageIO.read(new URL("http://p3ach.com/images/edvilNoPots.png"));
            catherbyImg = ImageIO.read(new URL("http://p3ach.com/images/catherby.PNG"));
            catherbyObeliskImg = ImageIO.read(new URL("http://p3ach.com/images/catherbyNoPots.PNG"));
            cwarsImg = ImageIO.read(new URL("http://p3ach.com/images/cwars.PNG"));
            cwarsObeliskImg = ImageIO.read(new URL("http://p3ach.com/images/cwarsNoPots.PNG"));
            lleytaImg = ImageIO.read(new URL("http://p3ach.com/images/lleyta.png"));

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        edvilMap.setIcon(new ImageIcon(edvilImg));
        edvilObelisk.setIcon(new ImageIcon(edvilObeliskImg));
        catherbyMap.setIcon(new ImageIcon(catherbyImg));
        catherbyObelisk.setIcon(new ImageIcon(catherbyObeliskImg));
        cwarsMap.setIcon(new ImageIcon(cwarsImg));
        cwarsObelisk.setIcon(new ImageIcon(cwarsObeliskImg));
        lletyaMap.setIcon(new ImageIcon((lleytaImg)));
    }

    public P3SpawnerForm(Dialog owner) {
        super(owner);
        initComponents();
    }

    public P3SpawnerForm() {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void edvillButtonActionPerformed(ActionEvent e) {
        // TODO add your code here
        edvilActions();

    }

    private void edvilMapAncestorAdded(AncestorEvent e) {
        // TODO add your code here
    }

    private void obeliskButtonStateChanged(ChangeEvent e) {
        if(edvillButton.isSelected())
            edvilActions();
        if(cWarsButton.isSelected())
            cwarsActions();
        if(catherbyButton.isSelected())
            catherbyActions();
        if(lletyaButton.isSelected()){
            lletyaActions();
        }
    }
    private void sumPotButtonStateChanged(ChangeEvent e) {
        if(edvillButton.isSelected())
            edvilActions();
        if(cWarsButton.isSelected())
            cwarsActions();
        if(catherbyButton.isSelected())
            catherbyActions();
        if(lletyaButton.isSelected()){
            lletyaActions();
        }
    }
    private void clearImages(){
        cwarsMap.setVisible(false);
        cwarsObelisk.setVisible(false);
        edvilMap.setVisible(false);
        edvilObelisk.setVisible(false);
        catherbyMap.setVisible(false);
        catherbyObelisk.setVisible(false);
        lletyaMap.setVisible(false);
    }
     private void edvilActions(){
         P3Spawner.location = "Edgeville";
         spawnType();
         obeliskButton.setVisible(true);
         sumPotButton.setVisible(true);
         areaRadioButton1.setVisible(true);
         areaRadioButton2.setVisible(true);
         if(obeliskButton.isSelected()){
             clearImages();
             edvilObelisk.setVisible(true);
             areaRadioButton3.setVisible(false);
             areaRadioButton4.setVisible(false);
             areaRadioButton5.setVisible(false);
             areaRadioButton6.setVisible(false);

             P3SVars.toObelisk = P3SVars.Path_edvilObelisk;
             P3SVars.obelisk = P3SVars.S_EDVIL_OBELISK;
             P3Spawner.restoreType = P3Spawner.RESTORE_TYPE.OBELISK;
             if(areaRadioButton1.isSelected()||areaRadioButton2.isSelected()){
                 P3SVars.bankTile = P3SVars.T_BANK_EDVIL_NORTH;
                 okButton.setEnabled(true);
             }
         }
         if(sumPotButton.isSelected()){
             clearImages();
             edvilMap.setVisible(true);
             areaRadioButton3.setVisible(true);
             areaRadioButton4.setVisible(true);
             areaRadioButton5.setVisible(true);
             areaRadioButton6.setVisible(false);
             obeliskButton.setVisible(true);
             sumPotButton.setVisible(true);
             P3Spawner.restoreType = P3Spawner.RESTORE_TYPE.SUMMONING_POTS;
             if(areaRadioButton1.isSelected()||areaRadioButton2.isSelected()){
                 P3SVars.bankTile = P3SVars.T_BANK_EDVIL_SOUTH;
                 okButton.setEnabled(true);
             }
             else if(areaRadioButton3.isSelected()
                     || areaRadioButton4.isSelected()
                     || areaRadioButton5.isSelected()){
                 P3SVars.bankTile = P3SVars.T_BANK_EDVIL_NORTH;
                 okButton.setEnabled(true);
             }
         }
     }
     private void cwarsActions(){
         P3Spawner.location = "Castle Wars";
         spawnType();
         P3SVars.bankChest = P3SVars.S_C_WARS_BANK;
         P3SVars.bankTile = P3SVars.T_BANK_CWARS;
         if(areaRadioButton1.isSelected()){
             okButton.setEnabled(true);
         }
         if(obeliskButton.isSelected()){
             clearImages();
             cwarsObelisk.setVisible(true);
             areaRadioButton1.setVisible(true);
             areaRadioButton2.setVisible(false);
             areaRadioButton3.setVisible(false);
             areaRadioButton4.setVisible(false);
             areaRadioButton5.setVisible(false);
             areaRadioButton6.setVisible(false);
             P3Spawner.restoreType = P3Spawner.RESTORE_TYPE.OBELISK;
             P3SVars.toObelisk = P3SVars.Path_cWarsObelisk;
             P3SVars.obelisk = P3SVars.S_CWARS_OBELISK;
         }
         if(sumPotButton.isSelected()){
             clearImages();
             cwarsMap.setVisible(true);
             areaRadioButton1.setVisible(true);
             areaRadioButton2.setVisible(false);
             areaRadioButton3.setVisible(false);
             areaRadioButton4.setVisible(false);
             areaRadioButton5.setVisible(false);
             areaRadioButton6.setVisible(false);
             P3Spawner.restoreType = P3Spawner.RESTORE_TYPE.SUMMONING_POTS;
         }
         obeliskButton.setVisible(true);
         sumPotButton.setVisible(true);
     }
    private void catherbyActions(){
        P3Spawner.location = "Catherby";
        spawnType();
        P3SVars.bankTile = P3SVars.T_BANK_CATHEBY;
        if(areaRadioButton1.isSelected()||areaRadioButton2.isSelected()){
            okButton.setEnabled(true);
        }
        if(obeliskButton.isSelected()){
            clearImages();
            if(P3SVars.pouch == P3SVars.FRUIT_POUCH)
                P3SVars.noteing = true;
            catherbyObelisk.setVisible(true);
            areaRadioButton1.setVisible(true);
            areaRadioButton2.setVisible(true);
            areaRadioButton3.setVisible(false);
            areaRadioButton4.setVisible(false);
            areaRadioButton5.setVisible(false);
            areaRadioButton6.setVisible(false);
            P3Spawner.restoreType = P3Spawner.RESTORE_TYPE.OBELISK;
            if(areaRadioButton1.isSelected())
                P3SVars.toObelisk = P3SVars.Path_catherbyObelisk1;
            if(areaRadioButton2.isSelected())
                P3SVars.toObelisk = P3SVars.Path_catherbyObelisk2;
            P3SVars.obelisk = P3SVars.S_CATHERBY_OBELISK;
            P3SVars.npcLepercon = P3SVars.NPC_LEP_CATH;
        }
        if(sumPotButton.isSelected()){
            clearImages();
            catherbyMap.setVisible(true);
            areaRadioButton1.setVisible(true);
            areaRadioButton2.setVisible(true);
            areaRadioButton3.setVisible(false);
            areaRadioButton4.setVisible(false);
            areaRadioButton5.setVisible(false);
            areaRadioButton6.setVisible(false);
            P3Spawner.restoreType = P3Spawner.RESTORE_TYPE.SUMMONING_POTS;
        }
        obeliskButton.setVisible(true);
        sumPotButton.setVisible(true);
    }
    private void lletyaActions(){
        P3Spawner.location = "Lletya";
        spawnType();
        obeliskButton.setVisible(false);
        sumPotButton.setVisible(true);
        areaRadioButton1.setVisible(true);
        areaRadioButton2.setVisible(false);

        if(sumPotButton.isSelected()){
            clearImages();
            lletyaMap.setVisible(true);
            areaRadioButton3.setVisible(false);
            areaRadioButton4.setVisible(false);
            areaRadioButton5.setVisible(false);
            areaRadioButton6.setVisible(false);
            P3Spawner.restoreType = P3Spawner.RESTORE_TYPE.SUMMONING_POTS;
            P3SVars.bankTile = P3SVars.T_BANK_LLETYA;
            if(areaRadioButton1.isSelected()){

                okButton.setEnabled(true);
            }
        }
    }
    private void spawnType(){
         if(fruitBatButton.isSelected()){
             P3SVars.scroll = P3SVars.FRUIT_SCROLL;
             P3SVars.pouch = P3SVars.FRUIT_POUCH;
         }
         else if(redSpiderRaidoButton.isSelected()){
             P3SVars.scroll = P3SVars.SPIDER_SCROLL;
             P3SVars.pouch = P3SVars.SPIDER_POUCH;
         }
     }
     private void okButtonActionPerformed(ActionEvent e) {
         P3Spawner.gui.dispose();
         P3Spawner.startScript = true;
     }

     private void areaRadioButton1ActionPerformed(ActionEvent e) {
         P3Spawner.locationNumb = 1;
         if(edvillButton.isSelected()&& sumPotButton.isSelected()){
             P3Spawner.spawnArea = P3SVars.A_EDV_1;
             edvilActions();
         }
         else if(edvillButton.isSelected() && obeliskButton.isSelected()){
             P3Spawner.spawnArea = P3SVars.A_EDV_1_NO_SUM;
             edvilActions();
         }
         else if(cWarsButton.isSelected() && sumPotButton.isSelected()){
             P3Spawner.spawnArea = P3SVars.A_CWARS_1;
             cwarsActions();
         }
         else if(cWarsButton.isSelected() && obeliskButton.isSelected()){
             P3Spawner.spawnArea = P3SVars.A_CWARS_1_NO_SUM;
             cwarsActions();
         }
         else if(catherbyButton.isSelected() && sumPotButton.isSelected()){
             P3Spawner.spawnArea = P3SVars.A_CATHERBY_1;
             catherbyActions();
         }
         else if(catherbyButton.isSelected() && obeliskButton.isSelected()){
             P3Spawner.spawnArea = P3SVars.A_CATHERBY_1_NO_SUM;
             catherbyActions();
         }
         else if(lletyaButton.isSelected() && sumPotButton.isSelected()){
             P3Spawner.spawnArea = P3SVars.A_LLETYA_1;
             lletyaActions() ;
         }
     }

     private void areaRadioButton2ActionPerformed(ActionEvent e) {
         P3Spawner.locationNumb = 2;
         if(edvillButton.isSelected()&& sumPotButton.isSelected()){
             P3Spawner.spawnArea = P3SVars.A_EDV_2;
             edvilActions();
         }
         else if(edvillButton.isSelected() && obeliskButton.isSelected()){
             P3Spawner.spawnArea = P3SVars.A_EDV_2_NO_SUM;
             edvilActions();
         }
         else if(catherbyButton.isSelected() && sumPotButton.isSelected()){
             P3Spawner.spawnArea = P3SVars.A_CATHERBY_2;
             catherbyActions();
         }
         else if(catherbyButton.isSelected() && obeliskButton.isSelected()){
             P3Spawner.spawnArea = P3SVars.A_CATHERBY_2_NO_SUM;
             catherbyActions();
         }
     }

     private void areaRadioButton3ActionPerformed(ActionEvent e) {
         P3Spawner.locationNumb = 3;
         if(edvillButton.isSelected()&& sumPotButton.isSelected()){
             P3Spawner.spawnArea = P3SVars.A_EDV_3;
             edvilActions();
         }
     }

     private void areaRadioButton4ActionPerformed(ActionEvent e) {
         P3Spawner.locationNumb = 4;
         if(edvillButton.isSelected()&& sumPotButton.isSelected()){
             P3Spawner.spawnArea = P3SVars.A_EDV_4;
             edvilActions();
         }
     }

     private void areaRadioButton5ActionPerformed(ActionEvent e) {
         P3Spawner.locationNumb = 5;
         if(edvillButton.isSelected()&& sumPotButton.isSelected()){
             P3Spawner.spawnArea = P3SVars.A_EDV_5;
             edvilActions();
         }
     }

     private void fruitBatButtonActionPerformed(ActionEvent e) {
         P3SVars.scroll = P3SVars.FRUIT_SCROLL;
         P3SVars.pouch = P3SVars.FRUIT_POUCH;
     }

     private void redSpiderRaidoButtonActionPerformed(ActionEvent e) {
         P3SVars.scroll = P3SVars.SPIDER_SCROLL;
         P3SVars.pouch = P3SVars.SPIDER_POUCH;
     }

     private void cWarsButtonActionPerformed(ActionEvent e) {
         cwarsActions();
     }

     private void catherbyButtonActionPerformed(ActionEvent e) {
         catherbyActions();
     }

     private void lietyaButtonActionPerformed(ActionEvent e) {
         lletyaActions();
     }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - garrett bond
        dialogPane = new JPanel();
        restoreMethodPanel = new JPanel();
        label3 = new JLabel();
        sumPotButton = new JRadioButton();
        obeliskButton = new JRadioButton();
        spawnTypePanel = new JPanel();
        label2 = new JLabel();
        fruitBatButton = new JRadioButton();
        redSpiderRaidoButton = new JRadioButton();
        label1 = new JLabel();
        locationPanel = new JPanel();
        label4 = new JLabel();
        cWarsButton = new JRadioButton();
        edvillButton = new JRadioButton();
        catherbyButton = new JRadioButton();
        fallyEButton = new JRadioButton();
        yanilleButton = new JRadioButton();
        neitiznotButton = new JRadioButton();
        lunarIslandButton = new JRadioButton();
        buthropeButton = new JRadioButton();
        taverleyButton = new JRadioButton();
        alKharidButton = new JRadioButton();
        mobArmiesButton = new JRadioButton();
        lletyaButton = new JRadioButton();
        shiloVilageButton = new JRadioButton();
        canfisButton = new JRadioButton();
        dranyorVillageButton = new JRadioButton();
        okButton = new JButton();
        locationImagePanel = new JPanel();
        edvilMap = new JLabel();
        edvilObelisk = new JLabel();
        cwarsMap = new JLabel();
        cwarsObelisk = new JLabel();
        catherbyMap = new JLabel();
        catherbyObelisk = new JLabel();
        lletyaMap = new JLabel();
        subAreaPanel = new JPanel();
        label5 = new JLabel();
        areaRadioButton1 = new JRadioButton();
        areaRadioButton2 = new JRadioButton();
        areaRadioButton3 = new JRadioButton();
        areaRadioButton4 = new JRadioButton();
        areaRadioButton5 = new JRadioButton();
        areaRadioButton6 = new JRadioButton();

        //======== this ========
        Container contentPane = getContentPane();

        //======== dialogPane ========
        {
            dialogPane.setBorder(null);

            // JFormDesigner evaluation mark
            dialogPane.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            dialogPane.setLayout(null);

            //======== restoreMethodPanel ========
            {
                restoreMethodPanel.setBorder(new TitledBorder(new EtchedBorder(), "Restore Method"));
                restoreMethodPanel.setLayout(null);

                //---- label3 ----
                label3.setText("Restore Method");
                label3.setFont(label3.getFont().deriveFont(label3.getFont().getSize() + 1f));
                label3.setVisible(false);
                restoreMethodPanel.add(label3);
                label3.setBounds(new Rectangle(new Point(8, 2), label3.getPreferredSize()));

                //---- sumPotButton ----
                sumPotButton.setText("Sum. Pots");
                sumPotButton.setSelected(true);
                sumPotButton.setVisible(false);
                sumPotButton.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        sumPotButtonStateChanged(e);
                    }
                });
                restoreMethodPanel.add(sumPotButton);
                sumPotButton.setBounds(10, 15, sumPotButton.getPreferredSize().width, 16);

                //---- obeliskButton ----
                obeliskButton.setText("Obelisk");
                obeliskButton.setVisible(false);
                obeliskButton.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        obeliskButtonStateChanged(e);
                    }
                });
                restoreMethodPanel.add(obeliskButton);
                obeliskButton.setBounds(10, 30, obeliskButton.getPreferredSize().width, 15);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < restoreMethodPanel.getComponentCount(); i++) {
                        Rectangle bounds = restoreMethodPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = restoreMethodPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    restoreMethodPanel.setMinimumSize(preferredSize);
                    restoreMethodPanel.setPreferredSize(preferredSize);
                }
            }
            dialogPane.add(restoreMethodPanel);
            restoreMethodPanel.setBounds(112, 36, 103, 54);

            //======== spawnTypePanel ========
            {
                spawnTypePanel.setBorder(new TitledBorder(new EtchedBorder(), "Spawn Type"));
                spawnTypePanel.setLayout(null);

                //---- label2 ----
                label2.setText("Spawn Type");
                label2.setFont(label2.getFont().deriveFont(label2.getFont().getSize() + 1f));
                label2.setVisible(false);
                spawnTypePanel.add(label2);
                label2.setBounds(8, 2, label2.getPreferredSize().width, 14);

                //---- fruitBatButton ----
                fruitBatButton.setText("Fruit Bat");
                fruitBatButton.setSelected(true);
                fruitBatButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        fruitBatButtonActionPerformed(e);
                    }
                });
                spawnTypePanel.add(fruitBatButton);
                fruitBatButton.setBounds(5, 15, fruitBatButton.getPreferredSize().width, 15);

                //---- redSpiderRaidoButton ----
                redSpiderRaidoButton.setText("Red Spider");
                redSpiderRaidoButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        redSpiderRaidoButtonActionPerformed(e);
                    }
                });
                spawnTypePanel.add(redSpiderRaidoButton);
                redSpiderRaidoButton.setBounds(5, 30, redSpiderRaidoButton.getPreferredSize().width, 14);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < spawnTypePanel.getComponentCount(); i++) {
                        Rectangle bounds = spawnTypePanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = spawnTypePanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    spawnTypePanel.setMinimumSize(preferredSize);
                    spawnTypePanel.setPreferredSize(preferredSize);
                }
            }
            dialogPane.add(spawnTypePanel);
            spawnTypePanel.setBounds(15, 36, 88, 54);

            //---- label1 ----
            label1.setText("P3Spawner");
            label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 4f));
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            dialogPane.add(label1);
            label1.setBounds(new Rectangle(new Point(65, 11), label1.getPreferredSize()));

            //======== locationPanel ========
            {
                locationPanel.setBorder(new TitledBorder(new EtchedBorder(), "Location"));
                locationPanel.setLayout(null);

                //---- label4 ----
                label4.setText("Location");
                label4.setFont(label4.getFont().deriveFont(label4.getFont().getSize() + 1f));
                label4.setVisible(false);
                locationPanel.add(label4);
                label4.setBounds(new Rectangle(new Point(69, 2), label4.getPreferredSize()));

                //---- cWarsButton ----
                cWarsButton.setText("Castle Wars");
                cWarsButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cWarsButtonActionPerformed(e);
                    }
                });
                locationPanel.add(cWarsButton);
                cWarsButton.setBounds(10, 15, cWarsButton.getPreferredSize().width, 13);

                //---- edvillButton ----
                edvillButton.setText("Edgeville");
                edvillButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        edvillButtonActionPerformed(e);
                    }
                });
                locationPanel.add(edvillButton);
                edvillButton.setBounds(10, 30, edvillButton.getPreferredSize().width, 15);

                //---- catherbyButton ----
                catherbyButton.setText("Catherby");
                catherbyButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        catherbyButtonActionPerformed(e);
                    }
                });
                locationPanel.add(catherbyButton);
                catherbyButton.setBounds(105, 15, 79, 12);

                //---- fallyEButton ----
                fallyEButton.setText("Fally E. Bank");
                fallyEButton.setEnabled(false);
                locationPanel.add(fallyEButton);
                fallyEButton.setBounds(105, 30, fallyEButton.getPreferredSize().width, 13);

                //---- yanilleButton ----
                yanilleButton.setText("Yanille");
                yanilleButton.setEnabled(false);
                locationPanel.add(yanilleButton);
                yanilleButton.setBounds(10, 45, yanilleButton.getPreferredSize().width, 15);

                //---- neitiznotButton ----
                neitiznotButton.setText("Neitiznot");
                neitiznotButton.setEnabled(false);
                locationPanel.add(neitiznotButton);
                neitiznotButton.setBounds(105, 45, neitiznotButton.getPreferredSize().width, 15);

                //---- lunarIslandButton ----
                lunarIslandButton.setText("Lunar Isle");
                lunarIslandButton.setEnabled(false);
                locationPanel.add(lunarIslandButton);
                lunarIslandButton.setBounds(10, 60, lunarIslandButton.getPreferredSize().width, 15);

                //---- buthropeButton ----
                buthropeButton.setText("Buthrope");
                buthropeButton.setEnabled(false);
                locationPanel.add(buthropeButton);
                buthropeButton.setBounds(105, 60, buthropeButton.getPreferredSize().width, 15);

                //---- taverleyButton ----
                taverleyButton.setText("Taverley");
                taverleyButton.setEnabled(false);
                locationPanel.add(taverleyButton);
                taverleyButton.setBounds(10, 75, taverleyButton.getPreferredSize().width, 15);

                //---- alKharidButton ----
                alKharidButton.setText("Al Kharid");
                alKharidButton.setEnabled(false);
                locationPanel.add(alKharidButton);
                alKharidButton.setBounds(105, 75, alKharidButton.getPreferredSize().width, 15);

                //---- mobArmiesButton ----
                mobArmiesButton.setText("Mob. Armies");
                mobArmiesButton.setEnabled(false);
                locationPanel.add(mobArmiesButton);
                mobArmiesButton.setBounds(10, 90, mobArmiesButton.getPreferredSize().width, 15);

                //---- lletyaButton ----
                lletyaButton.setText("Lletya");
                lletyaButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        lietyaButtonActionPerformed(e);
                    }
                });
                locationPanel.add(lletyaButton);
                lletyaButton.setBounds(105, 90, lletyaButton.getPreferredSize().width, 15);

                //---- shiloVilageButton ----
                shiloVilageButton.setText("Shilo Vilage");
                shiloVilageButton.setEnabled(false);
                locationPanel.add(shiloVilageButton);
                shiloVilageButton.setBounds(10, 105, shiloVilageButton.getPreferredSize().width, 15);

                //---- canfisButton ----
                canfisButton.setText("Canfis");
                canfisButton.setEnabled(false);
                locationPanel.add(canfisButton);
                canfisButton.setBounds(105, 105, canfisButton.getPreferredSize().width, 15);

                //---- dranyorVillageButton ----
                dranyorVillageButton.setText("Draynor Village");
                dranyorVillageButton.setEnabled(false);
                locationPanel.add(dranyorVillageButton);
                dranyorVillageButton.setBounds(10, 120, dranyorVillageButton.getPreferredSize().width, 15);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < locationPanel.getComponentCount(); i++) {
                        Rectangle bounds = locationPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = locationPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    locationPanel.setMinimumSize(preferredSize);
                    locationPanel.setPreferredSize(preferredSize);
                }
            }
            dialogPane.add(locationPanel);
            locationPanel.setBounds(15, 90, 198, 145);

            //---- okButton ----
            okButton.setText("Start");
            okButton.setEnabled(false);
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    okButtonActionPerformed(e);
                }
            });
            dialogPane.add(okButton);
            okButton.setBounds(150, 355, 65, 40);

            //======== locationImagePanel ========
            {
                locationImagePanel.setBorder(new TitledBorder(new EtchedBorder(), "Location Image"));
                locationImagePanel.setLayout(null);

                //---- edvilMap ----
                edvilMap.setAutoscrolls(true);
                edvilMap.setVisible(false);
                edvilMap.addAncestorListener(new AncestorListener() {
                    @Override
                    public void ancestorMoved(AncestorEvent e) {}
                    @Override
                    public void ancestorAdded(AncestorEvent e) {
                        edvilMapAncestorAdded(e);
                    }
                    @Override
                    public void ancestorRemoved(AncestorEvent e) {}
                });
                locationImagePanel.add(edvilMap);
                edvilMap.setBounds(20, 15, 90, 87);

                //---- edvilObelisk ----
                edvilObelisk.setVisible(false);
                locationImagePanel.add(edvilObelisk);
                edvilObelisk.setBounds(20, 20, 88, 80);

                //---- cwarsMap ----
                cwarsMap.setVisible(false);
                locationImagePanel.add(cwarsMap);
                cwarsMap.setBounds(25, 25, 75, 65);

                //---- cwarsObelisk ----
                cwarsObelisk.setVisible(false);
                locationImagePanel.add(cwarsObelisk);
                cwarsObelisk.setBounds(20, 20, 90, 75);

                //---- catherbyMap ----
                catherbyMap.setVisible(false);
                locationImagePanel.add(catherbyMap);
                catherbyMap.setBounds(12, 20, 105, 85);

                //---- catherbyObelisk ----
                catherbyObelisk.setVisible(false);
                locationImagePanel.add(catherbyObelisk);
                catherbyObelisk.setBounds(10, 15, 110, 90);
                locationImagePanel.add(lletyaMap);
                lletyaMap.setBounds(15, 20, 100, 75);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < locationImagePanel.getComponentCount(); i++) {
                        Rectangle bounds = locationImagePanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = locationImagePanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    locationImagePanel.setMinimumSize(preferredSize);
                    locationImagePanel.setPreferredSize(preferredSize);
                }
            }
            dialogPane.add(locationImagePanel);
            locationImagePanel.setBounds(15, 240, 130, 115);

            //======== subAreaPanel ========
            {
                subAreaPanel.setBorder(new TitledBorder(new EtchedBorder(), "Sub-Area"));
                subAreaPanel.setLayout(null);

                //---- label5 ----
                label5.setText("Sub-Area");
                label5.setFont(label5.getFont().deriveFont(label5.getFont().getSize() + 2f));
                label5.setVisible(false);
                subAreaPanel.add(label5);
                label5.setBounds(new Rectangle(new Point(3, 5), label5.getPreferredSize()));

                //---- areaRadioButton1 ----
                areaRadioButton1.setText("1");
                areaRadioButton1.setVisible(false);
                areaRadioButton1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        areaRadioButton1ActionPerformed(e);
                    }
                });
                subAreaPanel.add(areaRadioButton1);
                areaRadioButton1.setBounds(10, 20, areaRadioButton1.getPreferredSize().width, 15);

                //---- areaRadioButton2 ----
                areaRadioButton2.setText("2");
                areaRadioButton2.setVisible(false);
                areaRadioButton2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        areaRadioButton2ActionPerformed(e);
                    }
                });
                subAreaPanel.add(areaRadioButton2);
                areaRadioButton2.setBounds(10, 35, areaRadioButton2.getPreferredSize().width, 15);

                //---- areaRadioButton3 ----
                areaRadioButton3.setText("3");
                areaRadioButton3.setVisible(false);
                areaRadioButton3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        areaRadioButton3ActionPerformed(e);
                    }
                });
                subAreaPanel.add(areaRadioButton3);
                areaRadioButton3.setBounds(10, 50, areaRadioButton3.getPreferredSize().width, 15);

                //---- areaRadioButton4 ----
                areaRadioButton4.setText("4");
                areaRadioButton4.setVisible(false);
                areaRadioButton4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        areaRadioButton4ActionPerformed(e);
                    }
                });
                subAreaPanel.add(areaRadioButton4);
                areaRadioButton4.setBounds(10, 65, areaRadioButton4.getPreferredSize().width, 15);

                //---- areaRadioButton5 ----
                areaRadioButton5.setText("5");
                areaRadioButton5.setVisible(false);
                areaRadioButton5.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        areaRadioButton5ActionPerformed(e);
                    }
                });
                subAreaPanel.add(areaRadioButton5);
                areaRadioButton5.setBounds(10, 80, areaRadioButton5.getPreferredSize().width, 15);

                //---- areaRadioButton6 ----
                areaRadioButton6.setText("6");
                areaRadioButton6.setVisible(false);
                subAreaPanel.add(areaRadioButton6);
                areaRadioButton6.setBounds(10, 95, areaRadioButton6.getPreferredSize().width, 15);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < subAreaPanel.getComponentCount(); i++) {
                        Rectangle bounds = subAreaPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = subAreaPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    subAreaPanel.setMinimumSize(preferredSize);
                    subAreaPanel.setPreferredSize(preferredSize);
                }
            }
            dialogPane.add(subAreaPanel);
            subAreaPanel.setBounds(150, 240, 65, 115);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < dialogPane.getComponentCount(); i++) {
                    Rectangle bounds = dialogPane.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = dialogPane.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                dialogPane.setMinimumSize(preferredSize);
                dialogPane.setPreferredSize(preferredSize);
            }
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(dialogPane, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(dialogPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );
        setSize(245, 435);
        setLocationRelativeTo(getOwner());

        //---- restoreButtonGroup ----
        ButtonGroup restoreButtonGroup = new ButtonGroup();
        restoreButtonGroup.add(sumPotButton);
        restoreButtonGroup.add(obeliskButton);

        //---- spawnButtonGroup ----
        ButtonGroup spawnButtonGroup = new ButtonGroup();
        spawnButtonGroup.add(fruitBatButton);
        spawnButtonGroup.add(redSpiderRaidoButton);

        //---- locationGroup ----
        ButtonGroup locationGroup = new ButtonGroup();
        locationGroup.add(cWarsButton);
        locationGroup.add(edvillButton);
        locationGroup.add(catherbyButton);
        locationGroup.add(fallyEButton);
        locationGroup.add(yanilleButton);
        locationGroup.add(neitiznotButton);
        locationGroup.add(lunarIslandButton);
        locationGroup.add(buthropeButton);
        locationGroup.add(taverleyButton);
        locationGroup.add(alKharidButton);
        locationGroup.add(mobArmiesButton);
        locationGroup.add(lletyaButton);
        locationGroup.add(shiloVilageButton);
        locationGroup.add(canfisButton);
        locationGroup.add(dranyorVillageButton);

        //---- areaGroup ----
        ButtonGroup areaGroup = new ButtonGroup();
        areaGroup.add(areaRadioButton1);
        areaGroup.add(areaRadioButton2);
        areaGroup.add(areaRadioButton3);
        areaGroup.add(areaRadioButton4);
        areaGroup.add(areaRadioButton5);
        areaGroup.add(areaRadioButton6);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
    private Image edvilImage;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - garrett bond
    private JPanel dialogPane;
    private JPanel restoreMethodPanel;
    private JLabel label3;
    private JRadioButton sumPotButton;
    private JRadioButton obeliskButton;
    private JPanel spawnTypePanel;
    private JLabel label2;
    private JRadioButton fruitBatButton;
    private JRadioButton redSpiderRaidoButton;
    private JLabel label1;
    private JPanel locationPanel;
    private JLabel label4;
    private JRadioButton cWarsButton;
    private JRadioButton edvillButton;
    private JRadioButton catherbyButton;
    private JRadioButton fallyEButton;
    private JRadioButton yanilleButton;
    private JRadioButton neitiznotButton;
    private JRadioButton lunarIslandButton;
    private JRadioButton buthropeButton;
    private JRadioButton taverleyButton;
    private JRadioButton alKharidButton;
    private JRadioButton mobArmiesButton;
    private JRadioButton lletyaButton;
    private JRadioButton shiloVilageButton;
    private JRadioButton canfisButton;
    private JRadioButton dranyorVillageButton;
    private JButton okButton;
    private JPanel locationImagePanel;
    private JLabel edvilMap;
    private JLabel edvilObelisk;
    private JLabel cwarsMap;
    private JLabel cwarsObelisk;
    private JLabel catherbyMap;
    private JLabel catherbyObelisk;
    private JLabel lletyaMap;
    private JPanel subAreaPanel;
    private JLabel label5;
    private JRadioButton areaRadioButton1;
    private JRadioButton areaRadioButton2;
    private JRadioButton areaRadioButton3;
    private JRadioButton areaRadioButton4;
    private JRadioButton areaRadioButton5;
    private JRadioButton areaRadioButton6;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
