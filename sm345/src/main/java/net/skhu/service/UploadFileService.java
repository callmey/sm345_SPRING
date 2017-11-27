package net.skhu.service;

import org.springframework.stereotype.Service;

import net.skhu.dto.UploadFile;

@Service
public class UploadFileService {

	public UploadFile create(String name, long size, int mentoroom_id, byte[] data, String path, int kind, String file_original){
		UploadFile file = new UploadFile();
		file.setFile_size(size);
		file.setFile_name(name);
		file.setFile_data(data);
		file.setFile_path(path);
		file.setFile_kind(kind);
		file.setMentoroom_id(mentoroom_id);
		file.setFile_originalname(file_original);
		return file;
	}
}