package org.example.demo2.model;

import lombok.*;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SwipeTransactionDto {
    // 기본 필드
    private String trmnlNo; // 터미널 번호, 빈 문자열이 할당되어 있음
    private String usid;    // 사용자 ID
    private String nm;      // 이름
    private String entrpsCode; // 기업 코드
    private String entrpsNm;   // 기업 이름

    // 위치 관련 필드
    private String psitnEntrpsCode1; // 위치 기업 코드 1
    private String psitnRoleCode1;   // 위치 역할 코드 1
    private String psitnEntrpsNm1;   // 위치 기업 이름 1
    private String psitnEntrpsCode2; // 위치 기업 코드 2
    private String psitnRoleCode2;   // 위치 역할 코드 2
    private String psitnEntrpsNm2;   // 위치 기업 이름 2
    private String psitnEntrpsCode3; // 위치 기업 코드 3
    private String psitnRoleCode3;   // 위치 역할 코드 3
    private String psitnEntrpsNm3;   // 위치 기업 이름 3

    // 거래 관련 필드
    private String mberCode;   // 멤버 코드
    private String mberCodeSn; // 멤버 코드 순번
    private String rciptNo;    // 영수증 번호
    private double splpc;      // 공급가액
    private double vat;        // 부가가치세
    private String delngSeCode;// 거래 구분 코드

    // 기타 필드
    private int instlmtMonth;  // 할부 개월
    private String issueCmpnyNm; // 발급 회사 이름
    private String cardNo;     // 카드 번호
    private String confmDt;    // 승인 날짜
    private String confmTime;  // 승인 시간
    private String confmNo;    // 승인 번호
    private String telno;      // 전화번호

    // 위치 정보 필드
    private String delngPositionAt; // 거래 위치
    private String setleDevice;     // 결제 장치
    private String setleMthdCode;   // 결제 방법 코드
    private String orginlExcclcCycle; // 원래의 계산 주기
    private String cnclConfmDt;     // 취소 승인 날짜
    private String cnclConfmTime;   // 취소 승인 시간
    private String lng;             // 경도
    private String lat;             // 위도

    // 기업 및 수수료 관련 필드
    private String acnutno;     // 계좌 번호
    private String feeTariff;   // 수수료 요율
    private String signImg;     // 서명 이미지
    private double storeFee;    // 매장 수수료
    private double storeSvc;    // 매장 서비스
    private double hedofc;      // 본사 수수료
    private double brffc;       // 지사 수수료
    private double agency;      // 대리점 수수료
    private double dealer;      // 판매점 수수료
}
