package org.example.demo2.servlet;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.demo2.GoogleSheetService;
import org.example.demo2.model.StoreTransactionsDTO;
import org.example.demo2.storeTransactionsMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/testSheets"})
public class GoogleSheetsServlet extends HttpServlet  {
    private SqlSessionFactory sqlSessionFactory;
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    private static final String spreadSheetId = "spreadSheetId";

    public GoogleSheetsServlet() throws GeneralSecurityException, IOException {
    }

    @Override
    public void init() throws ServletException {
        String resource = "mybatis-config.xml";
        try (Reader reader = Resources.getResourceAsReader(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new ServletException("Failed to initialize MyBatis", e);
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getOutputStream();
        List<StoreTransactionsDTO> storeTransactionsDTOList;
        List<StoreTransactionsDTO> expandedList = new ArrayList<>();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            storeTransactionsMapper mapper = session.getMapper(storeTransactionsMapper.class);
            storeTransactionsDTOList = mapper.getAllStoreTransactions(100,0);
        } catch (Exception e) {
            throw new ServletException("Failed to fetch storeTransactionsMapper", e);
        }
        for (int i = 0; i < 15; i++) {
            expandedList.addAll(storeTransactionsDTOList);
        }
        List<List<Object>> values = GoogleSheetService.convertDTOListToGoogleSheetsData(expandedList);
        String num = Integer.toString(expandedList.size()+1);
        String range = "sheet1!A1:AC"+ num;
        System.out.println(range);
        ValueRange data = new ValueRange().setValues(values);

        Sheets service = null;
        try {
            service = GoogleSheetService.getSheetsService();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        System.out.println(System.currentTimeMillis()+ "전송 시작");
        service.spreadsheets().values().update(spreadSheetId, range, data)
                .setValueInputOption("USER_ENTERED")
                .execute();
        System.out.println(System.currentTimeMillis()+ "전송 끝");

    }
}
