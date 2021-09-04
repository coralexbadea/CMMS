package com.adecco.mentenance.export;

import com.adecco.mentenance.domain.Task;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class CostsExcelExporter extends ExcelExport<List<Task>> {
    public CostsExcelExporter(List<Task> tasks) {
        this.obj = tasks;
        workbook = new XSSFWorkbook();
    }

    void writeHeaderLine() {
        sheet = workbook.createSheet("Costs");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(10);
        style.setFont(font);

        createCell(row, 0, "Masina", style);
        createCell(row, 1, "Subansamblu", style);
        createCell(row, 2, "Componenta", style);
        createCell(row, 3, "Cantitate", style);
        createCell(row, 4, "Pret/Comp", style);
        createCell(row, 5, "Pret total", style);
        createCell(row, 6, "Data", style);
    }

    void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(10);
        style.setFont(font);

        for (Task task : obj) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
            String date = task.getDate()==null? "NULL" : task.getDate().format(formatter);
            createCell(row, columnCount++, task.getComponent().getMachine().getMname(), style);
            createCell(row, columnCount++, task.getComponent().getSubansamblu().getSname(), style);
            createCell(row, columnCount++, task.getComponent().getName(), style);
            createCell(row, columnCount++, task.getQuantity(), style);
            createCell(row, columnCount++, task.getPrice(), style);
            createCell(row, columnCount++, task.getTotalPrice(), style);
            createCell(row, columnCount++, date, style);
        }
    }
}
