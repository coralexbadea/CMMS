package com.adecco.mentenance.export;

import com.adecco.mentenance.domain.Raport;
import org.springframework.stereotype.Service;

@Service
public class ExcelExportFactory {
    public static ExcelExport getExportType(String export, Object obj){
        if(export.equals("raport")){
            return new RaportExcelExporter((Raport) obj);
        }
        return null;
    }
}