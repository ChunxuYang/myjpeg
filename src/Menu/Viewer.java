package Menu;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;

/**
 * Viewer - Viewer and converter for Wu4 file.
 */
public class Viewer extends JFrame {

    private JButton btnOpen, btnSaveBmp, btnSaveWu4; // �ļ�������ť
    private FileDialog openDia, saveDia; // �ļ������Ի���
    private File file; // ��ǰ���ļ�
    private BufferedImage image;
    private final int buttonX = 24, buttonY = 24;

    Viewer() {
        super("Wu4 Viewer");
        init();
    }

    public static void main(String[] args) {
        new Viewer();
    }

    /* ͼ���û����������ʼ�� */
    public void init() {

        /* ���ô��岼�� */
        setLayout(null);
        setBounds(300, 100, 960, 640);
        getContentPane().setBackground(Color.white);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        /* ����ļ������Ի��� */
        openDia = new FileDialog(this, "��", FileDialog.LOAD);
        saveDia = new FileDialog(this, "����", FileDialog.SAVE);

        /* ����ļ�������ť */
        btnOpen = new JButton(new ImageIcon("src\\Image\\OpenFile.png"));
        btnOpen.setBounds(buttonX, buttonY, 272, 48);
        btnOpen.addActionListener(new FileOpener());
        add(btnOpen);
        btnSaveBmp = new JButton(new ImageIcon("src\\Image\\SaveFileBmp.png"));
        btnSaveBmp.setBounds(buttonX, buttonY + 64, 128, 48);
        btnSaveBmp.addActionListener(new FileSaver());
        add(btnSaveBmp);
        btnSaveWu4 = new JButton(new ImageIcon("src\\Image\\SaveFileWu4.png"));
        btnSaveWu4.setBounds(buttonX + 144, buttonY + 64, 128, 48);
        btnSaveWu4.addActionListener(new FileSaver());
        add(btnSaveWu4);

        setVisible(true);// ���ô���ɼ�
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (image == null)
            return;

        /* ����ͼƬ����ͼ */
        int width = image.getWidth(), height = image.getHeight();
        float zoom = Math.min((float) width / 400, (float) height / 300);
        width = (int) (width / zoom);
        height = (int) (height / zoom);
        g.drawImage(image, 630 - width / 2, 350 - height / 2,
                width, height, this);

        /* ���ͼƬ��Ϣ */
        g.setFont(new Font("΢���ź�", Font.PLAIN, 24));
        g.drawString("width: " + width, buttonX + 32, buttonY + 400);
        g.drawString("height: " + height, buttonX + 32, buttonY + 432);
    }

    private class FileOpener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            openDia.setVisible(true);//��ʾ���ļ��Ի���

            String dirpath = openDia.getDirectory(); //��ȡ���ļ�·��
            String fileName = openDia.getFile(); //��ȡ���ļ�����
            if (dirpath == null || fileName == null) // ����ȡʧ���򷵻�
                return;

            file = new File(dirpath, fileName); // ���ļ�

            try { // ���Զ��ļ�
                if (fileName.endsWith(".wu4"))
                    image = null;
                else
                    image = ImageIO.read(file);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            repaint();
        }
    }

    private class FileSaver implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (file == null) {
                saveDia.setVisible(true); //��ʾ�����ļ��Ի���
                String dirpath = saveDia.getDirectory(); //��ȡ�����ļ�·��
                String fileName = saveDia.getFile(); //��ȡ�����ļ�����
                if (dirpath == null || fileName == null) // ����ȡʧ���򷵻�
                    return;
                else
                    file = new File(dirpath, fileName); // �½�һ���ļ�
            }
            try { // ����д���ļ�
                BufferedWriter bufw = new BufferedWriter(new FileWriter(file));
                String text = "I love wu4";
                bufw.write(text); //����ȡ�ı�����д�뵽�ַ������
                bufw.close(); //�ر��ļ�
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
