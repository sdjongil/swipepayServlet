package org.example.demo2.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.demo2.TestMapper;
import org.example.demo2.model.SearchTestDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

@WebServlet(name = "totalChunk", urlPatterns = {"/total-chunks"})
public class TotalChunkServlet extends HttpServlet {
    public static final int CHUNK_SIZE = 20000; // 청크 크기
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
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        SearchTestDto searchTestDto = objectMapper.readValue(request.getInputStream(), SearchTestDto.class);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            TestMapper mapper = session.getMapper(TestMapper.class);
            int totalRecords = mapper.selectCount(searchTestDto);
            int totalChunks = (int) Math.ceil((double) totalRecords / CHUNK_SIZE);

            response.getWriter().write(String.valueOf(totalChunks));
        }
    }
}
