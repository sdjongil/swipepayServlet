package org.example.demo2.servlet;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.demo2.ArticleMapper;
import org.example.demo2.model.TransactionDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@WebServlet(name = "getPreview", urlPatterns = {"/getPreview"})
public class GetPreviewServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String startDt = request.getParameter("startDt");
        String endDt = request.getParameter("endDt");
        TransactionDto transactions = new TransactionDto();
        transactions.setUsid("SAU059131");
        transactions.setStartDt(startDt);
        transactions.setEndDt(endDt);
        System.out.println(transactions.toString());
        try (SqlSession session = sqlSessionFactory.openSession()) {
            ArticleMapper mapper = session.getMapper(ArticleMapper.class);
            List<TransactionDto.Detail> details = mapper.selectTransaction(transactions);
            transactions.setDetails(details);
            TransactionDto userInfo = mapper.selectUserInfo(transactions);
            transactions.setName(userInfo.getName());
            transactions.setBusinessNumber(userInfo.getBusinessNumber());
        } catch (Exception e) {
            throw new ServletException("Failed to fetch articles", e);
        }
        System.out.println("ezra " + transactions.toString());

        request.setAttribute("transactionDto", transactions);
        request.getRequestDispatcher("/WEB-INF/transaction.jsp").forward(request, response);
    }
}
