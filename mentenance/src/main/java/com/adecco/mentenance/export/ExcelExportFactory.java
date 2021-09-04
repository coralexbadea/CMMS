package com.adecco.mentenance.export;

import com.adecco.mentenance.domain.Machine;
import com.adecco.mentenance.domain.Raport;
import com.adecco.mentenance.domain.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExcelExportFactory {
    public static ExcelExport getExportType(String export, Object obj){
        if(export.equals("raport")){
            return new RaportExcelExporter((Raport) obj);
        }
        if(export.equals("costs")){
            return new CostsExcelExporter((List<Task>) obj);
        }
        if(export.equals("machine")){
            return new MachineExcelExporter((Machine) obj);
        }
        return null;
    }
}