package org.example.demo2.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
public class StoreTransactionsDTO {
    private String 구분;
    private String 지사;
    private String 대리점;
    private String 딜러;
    private String 승인번호;
    private String 거래번호;
    private String 방식;
    private String 판매자;
    private String 판매자연락처;
    private String 상호;
    private String 구매자연락처;
    private String 판매금액;
    private String 상점계정코드;
    private String 할부;
    private String 발급사명;
    private String 카드번호;
    private String 승인일시;
    private String 취소일시;
    private String 순간송금액;
    private String 상점정산액;
    private String 본사수익;
    private String 지사수익;
    private String 대리점수익;
    private String 딜러수익;
    private String KSNETID;
    private String 상점단말기번호;
    private String 판매상품;
    private String 결제단말;
    private String 단말타입;
    public List<String> toList() {
        return Arrays.asList(
                구분, 지사, 대리점, 딜러, 승인번호, 거래번호, 방식, 판매자, 판매자연락처,
                상호, 구매자연락처, 판매금액, 상점계정코드, 할부, 발급사명, 카드번호, 승인일시,
                취소일시, 순간송금액, 상점정산액, 본사수익, 지사수익, 대리점수익, 딜러수익,
                KSNETID, 상점단말기번호, 판매상품, 결제단말, 단말타입
        );
    }

}
