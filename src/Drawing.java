import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Drawing extends JFrame {
    private JTextField searchingBar;
    private JButton Browse;
    private JList winnerList;
    private JButton drawButton;
    private JPanel mainpanel;

    private String filepath="";
    private ArrayList<String> participants = new ArrayList<>();
    private Set<String> winners = new TreeSet<>();
    private DefaultListModel model = new DefaultListModel();

    public Drawing(String title) {

        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainpanel);
        this.pack();
        winnerList.setModel(model);

        Browse.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int i = fileChooser.showOpenDialog(Browse);

                if(i==JFileChooser.APPROVE_OPTION) { // kullanıcı açılan dialog penceresinde okaye basmış demektir
                    filepath = fileChooser.getSelectedFile().getPath();
                    searchingBar.setText(filepath);
                }
            }
        });
        
        
        drawButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                if(filepath.equals("")) {
                    JOptionPane.showMessageDialog(drawButton,"please choose a file");
                }
                else {
                    doDrawing();
                    for (String winner : winners) {
                        model.addElement(winner);
                    }

                    addMusic();

                }

            }
            
        });
    }

    private void addMusic() {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File("alkış.wav"));

            Clip clip = AudioSystem.getClip();

            clip.open(stream);
            clip.start();

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    public void doDrawing() {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), StandardCharsets.UTF_8))){

            String person;

            while ((person = reader.readLine()) != null) {
                participants.add(person);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (winners.size() != 10) {

            Random random = new Random();

            int winnersIndex = random.nextInt(participants.size());

            winners.add(participants.get(winnersIndex));

        }

    }

    public static void main(String[] args) {

        JFrame frame = new Drawing("my converter");
        frame.setSize(400,600);
        frame.setVisible(true);

    }




}