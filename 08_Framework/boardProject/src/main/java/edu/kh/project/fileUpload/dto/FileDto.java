package edu.kh.project.fileUpload.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDto {
	private int fileNo;
	private String fileOriginalName;
	private String fileRename;
	private String filePath;
	private String uploadDate;
}
