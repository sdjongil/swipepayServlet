package org.example.demo2;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


//가끔 라이브러리의 link가 깨질때가 있다. 그 떄는 다시 build.gradle을 수정

import lombok.Getter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExcelUtils {
    @Getter
    private Workbook workbook;
    @Getter
    private Sheet sheet;
    private CellStyle cellStyle;
    private int rowIndex = 0;
    private int maxCols = 0;
    private int offsetCol = 0;

    /**
     * 생성자
     * @param sheetName 시트명
     */
    public ExcelUtils(String sheetName) {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(sheetName);
    }

    /**
     * 생성자
     * @param sheetName 시트명
     * @param rowAccessWindowSize flushRows단위
     *        * 해당 값이 NULL일경우 XSSF를 , 없을 경우 SXSSF 대용량 라이브러리를 사용
     *        * XSSF : 엑셀 2007부터 지원하는 OOXML 파일 포맷인 *.xlsx 파일을 읽고 쓰는 컴포넌트(데이터를 메모리에 담아서 한번에 처리) => 메모리 부족현상 발생
     *        * SXSSF : SXSSF는 자동으로 메모리에 일정량의 데이터가 차면 메모리를 비워줌. *.xlsx 파일 생성
     */
    public ExcelUtils(String sheetName, Integer rowAccessWindowSize) { // -1 turn off auto-flushing and accumulate all rows in memory)
        if (rowAccessWindowSize != null) {
            workbook = new SXSSFWorkbook(rowAccessWindowSize);// row 단위 flush
        } else {
            workbook = new XSSFWorkbook();
        }
        sheet = workbook.createSheet(sheetName);
        cellStyle = workbook.createCellStyle();
    }

    /**
     * 엑셀의 시작점을 설정한다.
     * @param col 엑셀시작컬럼위치
     * @param row 엑셀시작로우위치
     */
    public void setOffset(int col, int row) {
        offsetCol = col;
        rowIndex = row;
    }

    /**
     * 엑셀에 ROW를 추가한다. (데이터 삽입)
     *  POI 라이브러리는 JXLS와는 다르게 row를 생성하고 각 셀에 일일이 데이터를 넣어주어야 한다.
     *  옆으로 한칸씩 이동하면서 넣어줌
     * @param rows 엑셀에 보여줄 ROW데이터
     */
    public <T> void addRow(List<T> rows) throws Exception {
        Row row = sheet.createRow(rowIndex++); //한줄 내려와서
        int cellIndex = offsetCol;
        for (T value : rows) {
            //엑셀에 cell생성 및 값 주입
            Cell cell = row.createCell(cellIndex++); //셀만큼 돌린다.
            if(value instanceof Long || value instanceof Double
                    || value instanceof BigInteger || value instanceof Integer){
                cell.setCellValue(getNumber(String.valueOf(value)));
                cellStyle.setAlignment(HorizontalAlignment.RIGHT); // 정렬
                cell.setCellStyle(cellStyle);
            }else{
                cell.setCellValue(String.valueOf(value));
            }
        }
        if (maxCols < cellIndex) {
            maxCols = cellIndex;
        }
    }
    public static String getNumber(String value) {
        try {
            double number = Double.parseDouble(value);
            // 숫자 포맷 설정 (예: 소수점 2자리)
            DecimalFormat formatter = new DecimalFormat("#,###.##");
            return formatter.format(number);
        } catch (NumberFormatException e) {
            // 숫자가 아닌 경우 원래 값을 반환하거나 예외 처리
            return value;
        }
    }
    /*

 //Blank workbook

     /**
      * 3일이 지난 엑셀파일을 삭제한다.
      * @param //rows 엑셀에 보여줄 ROW데이터
      */
    public void deleteExcel(String filePath) {
        // Calendar 객체 생성
        Calendar cal = Calendar.getInstance() ;
        long todayMil = cal.getTimeInMillis() ;     // 현재 시간(밀리 세컨드)
        long oneDayMil = 24*60*60*1000 ;            // 일 단위

        Calendar fileCal = Calendar.getInstance() ;
        Date fileDate = null ;

        File path = new File(filePath);
        File[] list = path.listFiles() ;            // 파일 리스트 가져오기

        if(list != null) {
            for(int j=0 ; j < list.length; j++){
                // 파일의 마지막 수정시간 가져오기
                fileDate = new Date(list[j].lastModified()) ;

                // 현재시간과 파일 수정시간 시간차 계산(단위 : 밀리 세컨드)
                fileCal.setTime(fileDate);
                long diffMil = todayMil - fileCal.getTimeInMillis() ;

                //날짜로 계산
                int diffDay = (int)(diffMil/oneDayMil) ;

                // 1일이 지난 파일 삭제
                if(diffDay >= 1 && list[j].exists()){
                    list[j].delete() ;
                }
            }
        }
    }

    /**
     * 엑셀에 ROW를 추가한다.
     * @param rsExcel 외부에서 생성한 ExcelUtils 객체
     */
    public void write(ExcelUtils rsExcel, HttpServletRequest req, HttpServletResponse res) throws IOException {
        rsExcel.setOffset(0, 0);
        //실제 파일로 생성 (중간중간 임시파일을 만들어 OOM을 방지한다)
        ServletOutputStream output = res.getOutputStream();
        output.flush();
        this.sheet.getWorkbook().write(output);
        output.flush();
        output.close();

        if (this.workbook instanceof SXSSFWorkbook) {
            //dispose 는 (2)번 과정에서 디스크에 임시로 저장해 두었던 파일을 삭제하는 메소드입니다
            //dispose메소드가 정상적으로 호출되지 않으면 디스크에 임시파일이 그대로 남아있게 되기때문에 flushRows메소드와 한쌍으로 사용하면 됩니다.
            //dispose of temporary files backing this workbook on disk
            ((SXSSFWorkbook) this.workbook).dispose();
        }
    }

    // manually control how rows are flushed to disk
    public void FlushRows(int number) throws IOException {
        ((SXSSFSheet)sheet).flushRows(number);
    }

    public void writeChunk(OutputStream outputStream) throws IOException {
        try {
            this.sheet.getWorkbook().write(outputStream);
            outputStream.flush();
            System.out.println("Chunk written successfully");
        } catch (IOException e) {
            System.err.println("Error writing chunk: " + e.getMessage());
            throw e;
        }//        outputStream.flush();
    }
}
