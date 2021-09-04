package com.adecco.mentenance.export;

import com.adecco.mentenance.domain.Component;
import com.adecco.mentenance.domain.Machine;
import com.adecco.mentenance.domain.Task;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class MachineExcelExporter extends ExcelExport<Machine> {
    public MachineExcelExporter(Machine machine) {
        this.obj = machine;
        workbook = new XSSFWorkbook();
    }

    void writeHeaderLine() {
        sheet = workbook.createSheet("Components_"+this.obj.getMname());

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(10);
        style.setFont(font);

        createCell(row, 0, "Cod", style);
        createCell(row, 1, "Subansamblu", style);
        createCell(row, 2, "Nume", style);
        createCell(row, 3, "CodSAP", style);
        createCell(row, 4, "Cost", style);
    }

    void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(10);
        style.setFont(font);
        List<Component> components = this.obj.getComponents().stream().filter(c -> c.isActive()).collect(Collectors.toList());
        for (Component component : components) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, component.getCode(), style);
            createCell(row, columnCount++, component.getSubansamblu().getSname(), style);
            createCell(row, columnCount++, component.getName(), style);

        }
    }
}
