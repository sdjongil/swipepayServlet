package org.example.demo2.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class SearchArticleDto {
    private String startDate;
    private String endDate;
    private String title;
    private String content;
    private String branch;

    public static SearchArticleDto getSearchArticleDto() {
        return new SearchArticleDto(null,null,null,null,null);
    }
}
