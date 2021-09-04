package com.adecco.mentenance.service;

import com.adecco.mentenance.domain.Raport;
import com.adecco.mentenance.domain.Task;
import com.adecco.mentenance.storage.FileSystemStorageService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;


@Service
public class PdfService {
    @Autowired
    FileSystemStorageService storageService;

    public void createPdf(String filename, Raport raport) throws IOException, DocumentException, URISyntaxException {
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, new FileOutputStream(filename+".pdf"));
        document.open();

        Font font1 = FontFactory.getFont(FontFactory.COURIER, 25, BaseColor.BLACK);
        Font font = FontFactory.getFont(FontFactory.COURIER, 18, BaseColor.BLACK);
        document.add(new Paragraph(filename, font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );

        for(Task task: raport.getTasks()){
            List<Path> paths = storageService.getImagePaths(task.getTid());
            if(paths.size() != 0){
//
                document.add( new Paragraph( "Subansamblu: "+task.getComponent().getSubansamblu().getSname(),font) );
                document.add( new Paragraph( "Componenta: "+task.getComponent().getName(),font) );
                document.add( new Paragraph( "Lucrari conf. plan anual: "+task.getTaskType().getTtname(),font) );
                document.add( new Paragraph( "Actiune 1: "+task.getAction1(),font) );
                document.add( new Paragraph( "Actiune 2: "+task.getAction2(),font) );
                document.add( new Paragraph( "Actiune 3: "+task.getAction3(),font) );
                document.add( new Paragraph( "Personal Intern: "+task.getPintern(),font) );
                document.add( new Paragraph( "Observatii: "+task.getObsWorker(),font) );
                document.add( new Paragraph( "Lucrari conf. situatie reala: "+task.getRealSituation(),font) );
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
                String date = task.getDate()==null? "NULL" : task.getDate().format(formatter);
                document.add( new Paragraph( "Data: "+date,font) );
                document.add(new Paragraph("Images:",font));
                for(Path path: paths){
                    addImage(document, path);
                }
                document.newPage();
            }
            document.add( Chunk.NEWLINE );
        }
        document.add( Chunk.NEWLINE );
        document.close();
    }

//
//    public void createPdf(String filename, Raport raport) throws IOException, DocumentException, URISyntaxException {
//        Document document = new Document(PageSize.A4.rotate());
//        PdfWriter.getInstance(document, new FileOutputStream(filename+".pdf"));
//        document.open();
//
//        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
//        document.add(new Paragraph(filename, font));
//        document.add( Chunk.NEWLINE );
//        document.add( Chunk.NEWLINE );
//
//        PdfPTable table = new PdfPTable(7);
//        String[] strings = {"Subansamblu", "Lucrari conf. plan anual", "Actiune 1",
//                "Actiune 2", "Actiune 3", "Personal Intern", "Data"};
//        addTableHeader(table, strings);
//        for(Task task: raport.getTasks()){
//            addCustomRows(table,task);
//        }
//        document.add(table);
//
//        document.add( Chunk.NEWLINE );
//        document.add( Chunk.NEWLINE );
//
//        for(Task task: raport.getTasks()){
//            List<Path> paths = storageService.getImagePaths(task.getTid());
//            if(paths.size() != 0){
//                document.newPage();
//                document.add( new Paragraph( task.getComponent().getName()) );
//                for(Path path: paths){
//                addImage(document, path);
//              }
//            }
//            document.add( Chunk.NEWLINE );
//        }
//
//        table = new PdfPTable(2);
//        String[] strings1 = {"Subansamblu", "Observatii Lucrari"};
//        addTableHeader(table, strings1);
//        for(Task task: raport.getTasks()){
//            table.addCell(task.getComponent().getName());
//            table.addCell(task.getObsWorker());
//        }
//        document.newPage();
//        document.add( new Paragraph( "Observatii" ));
//        document.add( Chunk.NEWLINE );
//        document.add(table);
//        document.close();
//    }

    private void addTableHeader(PdfPTable table, String[] strings) {
        Stream<String> stream1 = Stream.of(strings);
        stream1
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addImage(Document document, Path path) throws DocumentException, IOException {
        //It does in work with relative path as String, we need relative path as Path type.
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        float scalerH = ((document.getPageSize().getHeight() - document.topMargin()
                - document.bottomMargin() - 20) / img.getHeight()) * 100;
        img.scalePercent(scalerH);
        document.add(img);
    }

    private void addCustomRows(PdfPTable table, Task task) {
        table.addCell(task.getComponent().getName());
        table.addCell(task.getTaskType().getTtname());
        table.addCell(task.getAction1());
        table.addCell(task.getAction2());
        table.addCell(task.getAction3());
        table.addCell(task.getPintern());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String date = task.getDate()==null? "NULL" : task.getDate().format(formatter);
        table.addCell(date);
    }

    public ResponseEntity<InputStreamResource> downloadPdf(String filename) {
        try
        {
            File file = new File(filename);
            HttpHeaders respHeaders = new HttpHeaders();
            MediaType mediaType = MediaType.parseMediaType("application/pdf");
            respHeaders.setContentType(mediaType);
            respHeaders.setContentLength(file.length());
            respHeaders.setContentDispositionFormData("attachment", file.getName());
            InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
            return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
        }
        catch (Exception e)
        {
            String message = "Error in pdf download:  "+filename+"; "+e.getMessage();
            System.out.println(message);
            return new ResponseEntity<InputStreamResource>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
