package org.example.demo2.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private String usid;
    private String startDt;
    private String endDt;
    private String name;
    private String businessNumber;
    private List<Detail> details;

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Detail {
        private String date;
        private String transactionCount;
        private String transactionAmount;
    }
}
