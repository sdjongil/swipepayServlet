package org.example.demo2;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.demo2.model.StoreTransactionsDTO;

import java.util.List;

@Mapper
public interface storeTransactionsMapper {
    int insertStoreTransaction(StoreTransactionsDTO storeTransactionsDTO);
    List<StoreTransactionsDTO> getAllStoreTransactions(@Param("limit") int limit, @Param("offset") int offset);
    int countAllStoreTransactions();
}
