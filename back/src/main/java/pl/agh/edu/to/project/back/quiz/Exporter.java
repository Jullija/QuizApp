package pl.agh.edu.to.project.back.quiz;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1CFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import pl.agh.edu.to.project.back.form.Form;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;


@Component
//export quiz to pdf and xlsx (animal name, points, award)
public class Exporter {
    private static String QUIZ_PATH = "quiz";
    private XSSFSheet sheet;
    private CellStyle style;
    private XSSFFont font;
    private CellStyle redStyle;
    private XSSFFont redFont;


    private void createStyles(Workbook workbook){
        style = workbook.createCellStyle();
        font = (XSSFFont) workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        redStyle = workbook.createCellStyle();
        redStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        redStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        redFont = (XSSFFont) workbook.createFont();
        redFont.setFontHeight(14);
        redStyle.setFont(redFont);
    }

    private void writeHeader(boolean bool, Quiz quiz, XSSFWorkbook workbook) {
        sheet = workbook.createSheet("Quiz " + quiz.getId());
        Row row = sheet.createRow(0);
        createStyles(workbook);
        createCell(row, 0, "Animal name", style, bool);
        createCell(row, 1, "Points", style, bool);
        createCell(row, 2, "Award", style, bool);
    }

    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style, boolean bool) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (bool && row.getRowNum() != 0){
            cell.setCellStyle(redStyle);
        }else{
            cell.setCellStyle(style);
        }

        if (valueOfCell instanceof Float) {
            cell.setCellValue((Float) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        }else if (valueOfCell instanceof Boolean){
            cell.setCellValue("");
    }}

    private void write(Quiz quiz, XSSFWorkbook workbook) throws IOException {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Form form: quiz.getForms()) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 2;
            boolean isWinner = false;
            if (form.getAward() == null){
                createCell(row, columnCount--, false, style, isWinner);
            }else{
                isWinner = true;
                createCell(row, columnCount--, form.getAward().toString(), style, isWinner);
            }
            createCell(row, columnCount--, form.getPoints(), style, isWinner);
            createCell(row, columnCount--, form.getNick(), style, isWinner);

        }
    }

    private static String replacePolishCharacters(String input){
        if (input.length() > 25){
            input = input.substring(0, 25) + "...";
        }
        return input.replace("ł", "l")
                .replace("ą", "a")
                .replace("ę", "e")
                .replace("ś", "s")
                .replace("ó", "o")
                .replace("ż", "z")
                .replace("ź", "z")
                .replace("ń", "n")
                .replace("ć", "c")
                .replace("Ł", "L")
                .replace("Ą", "A")
                .replace("Ę", "E")
                .replace("Ś", "S")
                .replace("Ó", "O")
                .replace("Ż", "Z")
                .replace("Ź", "Z")
                .replace("Ń", "N")
                .replace("Ć", "C");
    }

    //to color, but doesnt work (yet)
    private static boolean isRowColored(Row row, Sheet sheet) {
        Cell firstCellInRow = row.getCell(0);
        CellStyle cellStyle = firstCellInRow.getCellStyle();

        short backgroundColorIndex = cellStyle.getFillForegroundColor();
        return backgroundColorIndex == 10;
    }

    private static void contentStreamConf(PDPageContentStream contentStream) throws IOException {
        contentStream.setFont(PDType1Font.COURIER, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(10, 800);
        contentStream.setLeading(14.5f);
    }
    private static void handleRow(PDPageContentStream contentStream, Sheet sheet, int currentRow) throws IOException {
        Row row = sheet.getRow(currentRow);
        for (Cell cell : row){

            String cellValue = replacePolishCharacters(cell.toString());
            contentStream.showText(cellValue);
            contentStream.newLineAtOffset(220, 0);
        }
    }


    private static void handleWritingToPage(Sheet sheet, PDPage page, PDDocument document) throws IOException {
        int currentRow = 0;
        int entriesOnPage = 27;

        while (currentRow < sheet.getPhysicalNumberOfRows()){

            if (currentRow % entriesOnPage == 0){
                page = new PDPage(PDRectangle.A4);
                document.addPage(page);
            }

            try(PDPageContentStream contentStream = new PDPageContentStream(document, page)){
                contentStreamConf(contentStream);
                int i = 0;
                while(i < entriesOnPage && currentRow < sheet.getPhysicalNumberOfRows()){
                    handleRow(contentStream, sheet, currentRow);
                    contentStream.newLineAtOffset(-660, -30);
                    currentRow += 1;
                    i += 1;
                }
                contentStream.endText();
            }

        }

    }




    public static void convertXlsxToPdf(String xlsxFilePath, String pdfFilePath) throws IOException {
        try (FileInputStream excelFile = new FileInputStream(xlsxFilePath);
             Workbook workbook = new XSSFWorkbook(excelFile);
             PDDocument document = new PDDocument()) {

            Sheet sheet = workbook.getSheetAt(0);
            PDPage page = new PDPage();

            handleWritingToPage(sheet, page, document);


            document.save(pdfFilePath);
        }
    }


    public void generateFile(Quiz quiz) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        writeHeader(true, quiz, workbook);
        write(quiz, workbook);
        String excelPath = getExportPath(quiz.getId()) + ".xlsx";
        String pdfPath = getExportPath(quiz.getId()) + ".pdf";
        saveToFile(excelPath, workbook);
        convertXlsxToPdf(excelPath, pdfPath);

    }

    private String getExportPath(int id) {
        return QUIZ_PATH + id;

    }

    public void saveToFile(String filePath, XSSFWorkbook workbook) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }

    }
}
