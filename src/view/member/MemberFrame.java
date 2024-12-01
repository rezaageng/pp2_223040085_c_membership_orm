package view.member;

import dao.JenisMemberDao;
import dao.MemberDao;
import model.JenisMember;
import model.Member;

import javax.swing.*;
import java.util.List;

public class MemberFrame extends JFrame {
    private List<JenisMember> jenisMemberList;
    private List<Member> memberList;
    private JTextField textFieldNama;
    private MemberTableModel tableModel;
    private JComboBox<String> comboJenis; // Tambahkan tipe generic
    private MemberDao memberDao;
    private JenisMemberDao jenisMemberDao;

    public MemberFrame(MemberDao memberDao, JenisMemberDao jenisMemberDao) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.memberDao = memberDao;
        this.jenisMemberDao = jenisMemberDao;

        this.memberList = this.memberDao.findAll();
        this.jenisMemberList = this.jenisMemberDao.findAll();

        JLabel labelInput = new JLabel("Nama:");
        labelInput.setBounds(15, 40, 350, 10);

        textFieldNama = new JTextField();
        textFieldNama.setBounds(15, 60, 350, 30);

        JLabel labelJenis = new JLabel("Jenis Member:");
        labelJenis.setBounds(15, 100, 350, 10);

        comboJenis = new JComboBox<>();
        comboJenis.setBounds(15, 120, 150, 30);

        JButton button = new JButton("Simpan"); // Tambahkan inisialisasi untuk tombol
        button.setBounds(15, 160, 150, 30);

        JButton buttonDelete = new JButton("Delete");
        buttonDelete.setBounds(15, 190, 150, 30);
        buttonDelete.addActionListener(new MemberDeleteButtonActionListener(this, memberDao));

        JButton buttonUpdate = new JButton("Update");
        buttonUpdate.setBounds(15, 220, 150, 30);
        buttonUpdate.addActionListener(new MemberUpdateButtonActionListener(this, memberDao));

        JTable table = new JTable();
        JScrollPane scrollableTable = new JScrollPane(table);
        scrollableTable.setBounds(15, 220, 350, 200);

        tableModel = new MemberTableModel(memberList);
        table.setModel(tableModel); // Perbaiki nama variabel yang salah

        // Tambahkan action listener untuk tombol
        MemberSimpanButtonActionListener actionListener = new MemberSimpanButtonActionListener(this, memberDao);
        button.addActionListener(actionListener);

        // Tambahkan komponen ke frame
        this.add(button);
        this.add(buttonDelete);
        this.add(buttonUpdate);
        this.add(textFieldNama);
        this.add(labelInput);
        this.add(labelJenis);
        this.add(comboJenis);
        this.add(scrollableTable);

        // Atur ukuran dan tata letak frame
        this.setSize(400, 500);
        this.setLayout(null);

        // Isi combo box dengan data jenis member
        populateComboJenis();
    }

    public void populateComboJenis() {
        this.jenisMemberList = this.jenisMemberDao.findAll();
        comboJenis.removeAllItems();
        for (JenisMember jenisMember : this.jenisMemberList) {
            comboJenis.addItem(jenisMember.getNama());
        }
    }

    public String getNama() {
        return textFieldNama.getText();
    }

    public JenisMember getJenisMember() {
        return jenisMemberList.get(comboJenis.getSelectedIndex());
    }

    public void addMember(Member member) {
        tableModel.addMember(member); // Tambahkan member ke tabel
        textFieldNama.setText("");
    }

    public void showAlert(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public MemberTableModel getTableModel() {
        return tableModel;
    }

    public JTextField getTextFieldNama() {
        return textFieldNama;  // Return the JTextField for name input
    }
}
