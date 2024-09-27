package org.example.demo2.servlet;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.demo2.ArticleMapper;
import org.example.demo2.model.ArticleDto;
import org.example.demo2.model.SearchArticleDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchArticleServlet", urlPatterns = {"/searchArticle"})
public class SearchArticleServlet extends HttpServlet {
    private SqlSessionFactory sqlSessionFactory;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        SearchArticleDto searchArticleDto = SearchArticleDto.getSearchArticleDto();
        List<ArticleDto> articleDtoList;

        searchArticleDto.setStartDate(request.getParameter("startDate"));
        searchArticleDto.setEndDate(request.getParameter("endDate"));
        searchArticleDto.setTitle(request.getParameter("title"));
        searchArticleDto.setContent(request.getParameter("content"));
        searchArticleDto.setBranch(request.getParameter("branch"));

        try (SqlSession session = sqlSessionFactory.openSession()) {
            ArticleMapper mapper = session.getMapper(ArticleMapper.class);
            articleDtoList = mapper.selectInfoNoticeSearch(searchArticleDto);
        } catch (Exception e) {
            throw new ServletException("Failed to fetch articles", e);
        }

        for (ArticleDto articleDto : articleDtoList) {
            System.out.println(articleDto.toString());
        }

    }
}
