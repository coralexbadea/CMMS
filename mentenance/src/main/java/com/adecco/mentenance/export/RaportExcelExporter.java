package com.adecco.mentenance.export;

import com.adecco.mentenance.domain.Raport;
import com.adecco.mentenance.domain.Task;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.time.format.DateTimeFormatter;

public class RaportExcelExporter extends ExcelExport<Raport> {

    public RaportExcelExporter(Raport raport) {
        this.obj = raport;
        workbook = new XSSFWorkbook();
    }

    void writeHeaderLine() {
        sheet = workbook.createSheet("Raport");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Tip", style);
        createCell(row, 1, "Subansamblu", style);
        createCell(row, 2, "Task", style);
        createCell(row, 3, "Actiune 1", style);
        createCell(row, 4, "Actiune 2", style);
        createCell(row, 5, "Actiune 3", style);
        createCell(row, 6, "Obs. Lucrari", style);
        createCell(row, 7, "Personal Intern", style);
        createCell(row, 8, "Lucrari conf. situatie reala", style);
        createCell(row, 9, "Last Update", style);

    }

    void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Task task : obj.getTasks()) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
            String date = task.getDate()==null? "NULL" : task.getDate().format(formatter);
            createCell(row, columnCount++, task.getComponent().getComponentType().getCtname(), style);
            createCell(row, columnCount++, task.getComponent().getName(), style);
            createCell(row, columnCount++, task.getTaskType().getTtname(), style);
            createCell(row, columnCount++, task.getAction1(), style);
            createCell(row, columnCount++, task.getAction2(), style);
            createCell(row, columnCount++, task.getAction3(), style);
            createCell(row, columnCount++, task.getObsWorker(), style);
            createCell(row, columnCount++, task.getPintern(), style);
            createCell(row, columnCount++, task.getRealSituation(), style);
            createCell(row, columnCount++, date, style);
        }
    }

}
