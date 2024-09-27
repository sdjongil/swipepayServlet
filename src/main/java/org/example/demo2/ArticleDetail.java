package org.example.demo2;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.demo2.model.ArticleDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

@WebServlet(name = "ArticleDetail", urlPatterns = {"/getArticle"})
public class ArticleDetail extends HttpServlet {
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
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sn = request.getParameter("sn");
        ArticleDto articleDto;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            ArticleMapper mapper = session.getMapper(ArticleMapper.class);
            articleDto = mapper.selectInfoNoticeBySn(Integer.parseInt(sn));
        } catch (Exception e) {
            throw new ServletException("Failed to fetch articles", e);
        }


        request.setAttribute("article", articleDto);
        request.getRequestDispatcher("/WEB-INF/articleDetail.jsp").forward(request, response);

    }
}
