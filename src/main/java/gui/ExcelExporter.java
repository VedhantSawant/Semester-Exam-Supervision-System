package gui;

import java.io.FileOutputStream;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporter {

    public static void exportTable(JTable table) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Excel File");

            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {

                String filePath = fileChooser.getSelectedFile().getAbsolutePath() + ".xlsx";

                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("Data");

                DefaultTableModel model = (DefaultTableModel) table.getModel();

                // Header
                Row header = sheet.createRow(0);
                for (int i = 0; i < model.getColumnCount(); i++) {
                    header.createCell(i).setCellValue(model.getColumnName(i));
                }

                // Data
                for (int i = 0; i < model.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Object value = model.getValueAt(i, j);
                        row.createCell(j).setCellValue(value == null ? "" : value.toString());
                    }
                }

                // Auto-size columns
                for (int i = 0; i < model.getColumnCount(); i++) {
                    sheet.autoSizeColumn(i);
                }

                FileOutputStream out = new FileOutputStream(filePath);
                wb.write(out);
                wb.close();
                out.close();

                JOptionPane.showMessageDialog(null, "Excel Exported Successfully!");

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}