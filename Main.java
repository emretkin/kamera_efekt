package fotocekim;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import org.json.JSONObject;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.IOException;


import java.net.URLEncoder;


class GUI {

    protected JFrame jf=new JFrame("Kamera");
    private JLabel jl1=new JLabel("Parlat");
    private JLabel jl2=new JLabel("Siyah Beyaz Efekti");
    private JLabel jl3=new JLabel("Negatif Efekt");
    protected  JLabel jl4=new JLabel();
    protected JLabel jl5=new JLabel("Efekti Kaldır");
    protected  JPanel jp=new JPanel();
    protected  JButton jb1=new JButton();
    protected  JButton jb2=new JButton();
    protected  JButton jb3=new JButton();
    protected JButton jb4=new JButton();
    protected  JButton jb5=new JButton("Fotoğraf Çek");
    protected  JButton jb6=new JButton("Kaydet");
    protected  JButton jb7=new JButton("Geri Dön");
    protected JButton jb8 = new JButton("Paylaş");

    public GUI(){

        jf.setSize(1100, 650);
        jp.setBounds(20, 20, 640, 480);
        jb1.setBounds(700, 25, 80, 50);
        jb2.setBounds(700, 90, 80, 50);
        jb3.setBounds(700, 155, 80, 50);
        jb4.setBounds(700,220,80,50);
        jb5.setBounds(700, 350, 120, 50);
        jb6.setBounds(850,350,120,50);
        jb7.setBounds(700,430,120,50);
        jb8.setBounds(850, 430, 120, 50);

        jl1.setBounds(800, 35, 100, 30);
        jl2.setBounds(800, 100, 120, 30);
        jl3.setBounds(800, 165, 100, 30);
        jl5.setBounds(800, 230, 100, 30);

        jb1.setBackground(Color.ORANGE);
        jb2.setBackground(Color.BLACK);
        jb3.setBackground(Color.BLUE);
        jb4.setBackground(Color.WHITE);

        jf.add(jl1);
        jf.add(jl2);
        jf.add(jl3);
        jf.add(jl5);
        jp.add(jl4);


        jf.add(jb1);
        jf.add(jb2);
        jf.add(jb3);
        jf.add(jb4);
        jf.add(jb5);
        jf.add(jb6);
        jf.add(jb7);
        jf.add(jb8);

        jf.add(jp);



        jf.setLayout(null);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}



 class fotografCeken extends GUI {
    private String path=null;
    private Timer timer;
    private Webcam webcam;
    private WebcamPanel webcamPanel;
    private BufferedImage img,img2;
    Boolean efektliMod=false;
    public fotografCeken() throws IOException {
        super();
        Efektler efekt=new Efektler();
        Kayit kaydet=new Kayit();
        webcam = Webcam.getDefault();

        Dimension boyutlar = new Dimension(640, 480);
        webcam.setViewSize(boyutlar);
        webcam.open();
        webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setMirrored(true);


        jp.add(webcamPanel);

        timer = new Timer(1000 / 30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                webcamPanel.repaint();
            }
        });

        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jl4.getIcon() == null) {
                    JOptionPane.showMessageDialog(jf, "Efekt yapabilmek için önce fotoğraf çekmelisiniz.", "Hata", JOptionPane.ERROR_MESSAGE);
                } else {
                    img2 = efekt.parlatma(img);
                    efektliMod = true;

                    jl4.setIcon(new ImageIcon(img2));
                }
            }
        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jl4.getIcon() == null) {
                    JOptionPane.showMessageDialog(jf, "Efekt yapabilmek için önce fotoğraf çekmelisiniz.", "Hata", JOptionPane.ERROR_MESSAGE);
                } else {
                    img2 = efekt.siyahBeyaz(img);
                    efektliMod = true;
                    jl4.setIcon(new ImageIcon(img2));
                }
            }
        });
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jl4.getIcon() == null) {
                    JOptionPane.showMessageDialog(jf, "Efekt yapabilmek için önce fotoğraf çekmelisiniz.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    try {
                        img2 = efekt.negatif(img);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    efektliMod = true;
                    jl4.setIcon(new ImageIcon(img2));
                }
            }
        });
        jb4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jl4.setIcon(new ImageIcon(img));
                efektliMod=false;
            }
        });


        jb5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                img = webcam.getImage();
                efektliMod=false;
                jl4.setIcon(new ImageIcon(img));
            }
        });

        jb6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (efektliMod) {
                    try {
                        path = kaydet.kaydedici(img2);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                } else {
                    try {
                        path=kaydet.kaydedici(img);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });


        jb7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jl4.setIcon(null);
            }
        });

        jb8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (path == null) {
                    JOptionPane.showMessageDialog(jf, "Fotoğrafı paylaşabilmek için önce kaydetmelisiniz.","Hata", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    if (efektliMod) {
                        try {
                            Paylasim.shareOnTwitter(img2, path);
                        } catch (URISyntaxException ex) {
                            throw new RuntimeException(ex);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        try {
                            Paylasim.shareOnTwitter(img, path);
                        } catch (URISyntaxException ex) {
                            throw new RuntimeException(ex);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });

        timer.start();
        jf.setVisible(true);

    }
}

 class Efektler {
    public BufferedImage parlatma(BufferedImage img) {
        BufferedImage image = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int width = image.getWidth();
        int height = image.getHeight();

        Graphics2D g2d = image.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        double degree = 1.85;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                int red = (int) (color.getRed() * degree);
                int green = (int) (color.getGreen() * degree);
                int blue = (int) (color.getBlue() * degree);

                red = Math.min(red, 255);
                green = Math.min(green, 255);
                blue = Math.min(blue, 255);

                Color brightColor = new Color(red, green, blue);
                image.setRGB(x, y, brightColor.getRGB());
            }
        }

        return image;
    }
     
     public BufferedImage siyahBeyaz(BufferedImage img) {
        BufferedImage image = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int width = img.getWidth();
        int height = img.getHeight();


        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color c = new Color(img.getRGB(x, y));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();

                int gray = (red + green + blue) / 3;

                Color gColor = new Color(gray, gray, gray);
                image.setRGB(x, y, gColor.getRGB());
            }
        }

        return image;
    }

    public BufferedImage negatif(BufferedImage img) throws IOException {
        BufferedImage image = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        int width = image.getWidth();
        int height = image.getHeight();


        Graphics2D g2d = image.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                int red = 255 - color.getRed();
                int green = 255 - color.getGreen();
                int blue = 255 - color.getBlue();
                Color negativeColor = new Color(red, green, blue);
                image.setRGB(x, y, negativeColor.getRGB());
            }
        }
        return image;
    }
}



 class Kayit {
    String path;
    public String kaydedici(BufferedImage img) throws IOException {
        JFileChooser fileChooser= new JFileChooser();
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            path = fileChooser.getSelectedFile().getAbsolutePath();
            path=path+".jpg";
            ImageIO.write(img, "JPG", new File(path));
        }
        return path;
    }

}


 class Paylasim {
    public static void shareOnTwitter(BufferedImage img,String path) throws URISyntaxException, IOException {
        try {
            // Fotoğrafı Imgur'a yükleme
            String imageUrl = uploadImageToImgur(path);

            // Fotoğraf URL'sini Twitter ile paylaşma
            String tweetText = "Yeni fotoğrafım!";
            String encodedText = URLEncoder.encode(tweetText, "UTF-8"); // Metin kodlaması
            String tweetUrl = "https://twitter.com/intent/tweet?text=" + encodedText;
            tweetUrl += "&url=" + imageUrl;

            // Tarayıcıyı açma ve paylaşma linkini yükleme
            Desktop.getDesktop().browse(new URI(tweetUrl));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Hata: Fotoğrafı Twitter'da paylaşırken bir sorun oluştu.");
        }
    }
    private static String uploadImageToImgur(String imagePath) throws IOException {
        String clientId = "40a71914c6d159f"; // Imgur API istemci kimliği
        String clientSecret = "ce9138c9cba94be8326808a419488c3c012bc0d8"; // Imgur API istemci sırrı

        // Resmi yükleme URL'sini oluşturma
        String uploadUrl = "https://api.imgur.com/3/image";

        // Resmi yükleme isteği oluşturma
        HttpURLConnection connection = (HttpURLConnection) new URL(uploadUrl).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Client-ID " + clientId);
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            Files.copy(Paths.get(imagePath), outputStream);
            outputStream.flush();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                String responseBody = response.toString();
                System.out.println("Fotoğraf Imgur'a yüklendi: " + responseBody);

                // JSON yanıtını ayrıştırma
                JSONObject json = new JSONObject(responseBody);
                JSONObject data = json.getJSONObject("data");
                String imageUrl = data.getString("link");

                return imageUrl;
            }
        } else {
            System.out.println("Hata: Fotoğrafı Imgur'a yüklerken bir sorun oluştu. Hata kodu: " + responseCode);
            return null;
        }
    }
}
public class Main {
    public static void main(String[] args) throws IOException {
        fotografCeken photo=new fotografCeken();
    }
}
