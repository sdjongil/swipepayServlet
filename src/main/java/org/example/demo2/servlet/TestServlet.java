package org.example.demo2.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.demo2.TestMapper;
import org.example.demo2.model.SearchTestDto;
import org.example.demo2.model.TestDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static org.example.demo2.servlet.TotalChunkServlet.CHUNK_SIZE;

@WebServlet(name = "test", urlPatterns = {"/test"})
public class TestServlet extends HttpServlet {
    private SqlSessionFactory sqlSessionFactory;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int chunkIndex;
        try {
            chunkIndex = Integer.parseInt(request.getParameter("chunkIndex"));
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid chunk index");
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        SearchTestDto searchTestDto = objectMapper.readValue(request.getInputStream(), SearchTestDto.class);
        searchTestDto.setLimit(CHUNK_SIZE);

        response.setContentType("text/csv; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"transactions_chunk_" + chunkIndex + ".csv\"");


        try (SqlSession session = sqlSessionFactory.openSession();
             PrintWriter out = response.getWriter();) {
            int offset = chunkIndex * CHUNK_SIZE;
            searchTestDto.setOffSet(offset);
            TestMapper mapper = session.getMapper(TestMapper.class);
            List<TestDto> transactions = mapper.selectTestChunk(searchTestDto);

            if (transactions.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                return;
            }
            if (chunkIndex == 0) {
                out.println("정산주기, 판매자, 전화번호, 이름, 구매자연락처, 판매금액, 상점아이디," +
                        "할부개월, 카드정보, 카드번호, 판매날짜, 취소건");
            }
            for (TestDto dto : transactions) {
                List<String> values = dto.toList();
                for (int i = 0; i < values.size(); i++) {
                    if (i > 0) out.append(",");
                    out.append(values.get(i));
                }
                out.append("\n");
            }
            out.flush(); // 버퍼를 수동으로 플러시하여 클라이언트로 데이터 전송
        }
    }
}
