package gui;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PDFExporter {

    public static void exportTable(JTable table) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save PDF File");

            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {

                String filePath = fileChooser.getSelectedFile().getAbsolutePath() + ".pdf";

                PdfWriter writer = new PdfWriter(filePath);
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                DefaultTableModel model = (DefaultTableModel) table.getModel();

                Table pdfTable = new Table(model.getColumnCount());

                // ✅ HEADER
                for (int i = 0; i < model.getColumnCount(); i++) {
                    pdfTable.addHeaderCell(
                        new Cell().add(new Paragraph(model.getColumnName(i)))
                    );
                }

                // ✅ DATA
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {

                        Object value = model.getValueAt(i, j);

                        pdfTable.addCell(
                            new Cell().add(new Paragraph(
                                value == null ? "" : value.toString()
                            ))
                        );
                    }
                }

                document.add(pdfTable);
                document.close();

                JOptionPane.showMessageDialog(null, "PDF Exported Successfully!");

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}