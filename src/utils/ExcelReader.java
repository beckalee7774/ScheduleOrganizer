package utils;
import model.Event;
import model.Schedule;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {
    private static Schedule schedule;

    public ExcelReader(Schedule schedule) {
        ExcelReader.schedule = schedule;
    }


    public Schedule readFromExcel() throws IOException {
        String excelPath = "./data/schedule.xlsx";
        FileInputStream inputStream = new FileInputStream(excelPath);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        int rows = 5;
        int cols = 7;

        for(int r = 1; r < rows; r++) {
            XSSFRow row = sheet.getRow(r);
            XSSFCell s_day_cell = row.getCell(0);
            XSSFCell s_hour_cell = row.getCell(1);
            XSSFCell s_minute_cell = row.getCell(2);
            XSSFCell e_day_cell = row.getCell(3);
            XSSFCell e_hour_cell = row.getCell(4);
            XSSFCell e_minute_cell = row.getCell(5);
            XSSFCell name_cell = row.getCell(6);
            String s_day_string = s_day_cell.getStringCellValue();
            double s_hour_double = s_hour_cell.getNumericCellValue();
            double s_minute_double = s_minute_cell.getNumericCellValue();
            String e_day_string = e_day_cell.getStringCellValue();
            double e_hour_double = e_hour_cell.getNumericCellValue();
            double e_minute_double = e_minute_cell.getNumericCellValue();
            String name = name_cell.getStringCellValue();
            int s_day = 0;
            switch(s_day_string) {
                case "Sunday": s_day = 0;
                    break;
                case "Monday": s_day = 1;
                    break;
                case "Tuesday": s_day = 2;
                    break;
                case "Wednesday": s_day = 3;
                    break;
                case "Thursday": s_day = 4;
                    break;
                case "Friday": s_day = 5;
                    break;
                case "Saturday": s_day = 6;
                    break;
                default:
                    break;
            }
            int s_hour = (int) s_hour_double;
            int s_minute = (int) s_minute_double;
            int e_day = 0;
            switch(e_day_string) {
                case "Sunday": e_day = 0;
                    break;
                case "Monday": e_day = 1;
                    break;
                case "Tuesday": e_day = 2;
                    break;
                case "Wednesday": e_day = 3;
                    break;
                case "Thursday": e_day = 4;
                    break;
                case "Friday": e_day = 5;
                    break;
                case "Saturday": e_day = 6;
                    break;
                default:
                    break;
            }
            int e_hour = (int) e_hour_double;
            int e_minute = (int) e_minute_double;
            Event event = new Event(name, s_day, s_hour, s_minute, e_day, e_hour, e_minute);
            schedule.addEvent(event);

        }
        return schedule;
    }

}
