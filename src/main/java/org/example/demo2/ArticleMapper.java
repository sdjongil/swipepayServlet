package org.example.demo2;


import org.example.demo2.model.ArticleDto;
import org.example.demo2.model.SearchArticleDto;
import org.example.demo2.model.TransactionDto;

import java.util.List;

public interface ArticleMapper {

    List<ArticleDto> selectAllInfoNotices();

    ArticleDto selectInfoNoticeBySn(int sn);

    void insertArticle(ArticleDto articleDto);

    List<ArticleDto> selectInfoNoticeSearch(SearchArticleDto searchArticleDto);

    List<TransactionDto.Detail> selectTransaction(TransactionDto transactionDto);

    TransactionDto selectUserInfo(TransactionDto transactionDto);


}
