package org.example.demo2.model;

import lombok.*;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TestDto {
    private String 정산주기;
    private String 판매자;
    private String 전화번호;
    private String 이름;
    private String 구매자연락처;
    private String 판매금액;
    private String 상점아이디;
    private String 할부개월;
    private String 카드정보;
    private String 카드번호;
    private String 판매날짜;
    private String 취소건;
    public List<String> toList() {
        return Arrays.asList(
                정산주기, 판매자, 전화번호, 이름, 구매자연락처, 판매금액, 상점아이디,
                할부개월, 카드정보, 카드번호, 판매날짜, 취소건
        );
    }
}
