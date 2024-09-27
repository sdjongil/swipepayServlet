package org.example.demo2;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.demo2.model.ArticleDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(name = "UploadServlet", urlPatterns = {"/uploadArticle"})
@MultipartConfig
public class UploadServlet extends HttpServlet {
    private SqlSessionFactory sqlSessionFactory;
    String uploadPath = "C:\\Users\\lenovo\\Desktop\\demo2\\src\\main\\webapp\\img";

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

        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new ServletException("Content type is not multipart/form-data");
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        File repository = (File) getServletContext().getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        ArticleDto articleDto = ArticleDto.getArticleDTO();
        try {
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString("UTF-8");

                    switch (fieldName) {
                        case "noticeTitle":
                            articleDto.setNoticeTitle(fieldValue);
                            break;
                        case "noticeDetail":
                            articleDto.setNoticeDetail(fieldValue);
                            break;
                        case "noticeState":
                            articleDto.setNoticeState(Integer.parseInt(fieldValue));
                            break;
                        case "noticeType":
                            articleDto.setNoticeType(Integer.parseInt(fieldValue));
                            break;
                        case "noticePsint":
                            articleDto.setNoticePsint(fieldValue);
                            break;

                    }
                } else {
                    String fileName = item.getName();
                    if (fileName == null || fileName.trim().isEmpty()){
                        continue;
                    }
                    InputStream fileContent = item.getInputStream();
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
                    String formattedNow = now.format(formatter);
                    fileName = formattedNow + fileName;
                    articleDto.setNoticeImg(fileName);

                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }
                    File uploadedFile = new File(uploadDir + File.separator + fileName);
                    try (FileOutputStream fos = new FileOutputStream(uploadedFile)) {
                        IOUtils.copy(fileContent, fos);
                    }catch (Exception e){
                        System.out.println("File uploaded to: " + uploadedFile.getAbsolutePath());
                        response.getWriter().print("File uploaded to");
                        e.printStackTrace();
                        return;
                    }
                }
            }

            System.out.println(articleDto.toString());
            try (SqlSession session = sqlSessionFactory.openSession()) {
                ArticleMapper mapper = session.getMapper(ArticleMapper.class);
                mapper.insertArticle(articleDto);
                session.commit();
            } catch (Exception e) {

                throw new ServletException("Failed to fetch articles", e);
            }
            response.getWriter().print("Upload successful");

        } catch (Exception e) {
            throw new ServletException("File upload failed", e);
        }
    }
}
