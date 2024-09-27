package org.example.demo2;

import org.apache.ibatis.annotations.Mapper;
import org.example.demo2.model.SearchTestDto;
import org.example.demo2.model.TestDto;

import java.util.List;

@Mapper
public interface TestMapper {
    int selectCount(SearchTestDto searchTestDto);
    List<TestDto> selectTestChunk(SearchTestDto searchTestDto);

}
