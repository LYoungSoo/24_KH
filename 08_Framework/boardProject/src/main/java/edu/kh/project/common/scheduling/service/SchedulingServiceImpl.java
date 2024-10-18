package edu.kh.project.common.scheduling.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.common.scheduling.mapper.SchedulingMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SchedulingServiceImpl implements SchedulingService {
	
	private final SchedulingMapper mapper;

	// DB 에 기록된 모든 파일명 조회
	@Override
	public List<String> getDbFileNameList() {
		return mapper.getDbFileNameList();
	}

}
