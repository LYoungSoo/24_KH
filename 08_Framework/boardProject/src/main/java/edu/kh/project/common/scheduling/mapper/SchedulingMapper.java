package edu.kh.project.common.scheduling.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SchedulingMapper {

	/**
	 * DB 에 기록된 모든 파일명 조회
	 * @return
	 */
	List<String> getDbFileNameList();

}
