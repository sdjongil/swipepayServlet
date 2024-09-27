package org.example.demo2.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ArticleDto {
    private Integer sn;                  // 자동 증가 기본 키
    private String noticeTitle;      // 제목
    private String noticeDetail;     // 내용
    private String noticeImg;        // 이미지
    private String noticePsint;      // 보여줄 소속 지사 - entrps_code로
    private Integer noticeState;        // 공지사항 상태: 0 - 대기, 1 - 진행
    private Integer noticeType;         // 공지사항 종류: 0 - 중요, 1 - 일반
    private String creatDt;          // 생성 변경 일시

    public static ArticleDto getArticleDTO() {
        return new ArticleDto(null,null,null,null,
                null,null, null,null);
    }
}
