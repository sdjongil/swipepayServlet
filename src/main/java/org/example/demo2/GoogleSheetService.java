package org.example.demo2;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.example.demo2.model.StoreTransactionsDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoogleSheetService {
    private static final String CREDENTIALS_FILE_PATH = "plexiform-plane-429102-c8-df655c0d5adb.json";
    public static final String APPLICATION_NAME = "googleSheet";
    public static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    //SheetsScopes.SPREADSHEETS => 읽기 쓰기 모두 가능
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);

    public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        ClassLoader loader = GoogleSheetService.class.getClassLoader();
        try (InputStream in = loader.getResourceAsStream(CREDENTIALS_FILE_PATH)) {
            if (in == null) {
                throw new IOException("Resource not found: " + CREDENTIALS_FILE_PATH);
            }
            GoogleCredential credential = GoogleCredential.fromStream(in).createScoped(SCOPES);
            return credential;
        }
    }
    public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = getCredentials(HTTP_TRANSPORT);
        HttpRequestInitializer requestInitializer = new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest) throws IOException {
                credential.initialize(httpRequest);
                httpRequest.setReadTimeout(3 * 60000);  // 3분 (180000 밀리초)
                httpRequest.setConnectTimeout(3 * 60000);  // 3분 (180000 밀리초)
            }
        };

        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, requestInitializer)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
    public void writeSheets(List<StoreTransactionsDTO> storeTransactions) throws IOException {


    }

    public static List<List<Object>> convertDTOListToGoogleSheetsData(List<StoreTransactionsDTO> dtoList) {
        List<List<Object>> data = new ArrayList<>();

        // Header 추가
        List<Object> header = new ArrayList<>();
        header.add("구분");
        header.add("지사");
        header.add("대리점");
        header.add("딜러");
        header.add("승인번호");
        header.add("거래번호");
        header.add("방식");
        header.add("판매자");
        header.add("판매자연락처");
        header.add("상호");
        header.add("구매자연락처");
        header.add("판매금액");
        header.add("상점계정코드");
        header.add("할부");
        header.add("발급사명");
        header.add("카드번호");
        header.add("승인일시");
        header.add("취소일시");
        header.add("순간송금액");
        header.add("상점정산액");
        header.add("본사수익");
        header.add("지사수익");
        header.add("대리점수익");
        header.add("딜러수익");
        header.add("KSNETID");
        header.add("상점단말기번호");
        header.add("판매상품");
        header.add("결제단말");
        header.add("단말타입");
        data.add(header);

        // DTO 데이터 추가

        for (StoreTransactionsDTO dto : dtoList) {
            List<Object> row = new ArrayList<>();
            row.add(dto.get구분());
            row.add(dto.get지사());
            row.add(dto.get대리점());
            row.add(dto.get딜러());
            row.add(dto.get승인번호());
            row.add(dto.get거래번호());
            row.add(dto.get방식());
            row.add(dto.get판매자());
            row.add(dto.get판매자연락처());
            row.add(dto.get상호());
            row.add(dto.get구매자연락처());
            row.add(dto.get판매금액());
            row.add(dto.get상점계정코드());
            row.add(dto.get할부());
            row.add(dto.get발급사명());
            row.add(dto.get카드번호());
            row.add(dto.get승인일시());
            row.add(dto.get취소일시());
            row.add(dto.get순간송금액());
            row.add(dto.get상점정산액());
            row.add(dto.get본사수익());
            row.add(dto.get지사수익());
            row.add(dto.get대리점수익());
            row.add(dto.get딜러수익());
            row.add(dto.getKSNETID());
            row.add(dto.get상점단말기번호());
            row.add(dto.get판매상품());
            row.add(dto.get결제단말());
            row.add(dto.get단말타입());
            data.add(row);
        }

        return data;
    }

}
