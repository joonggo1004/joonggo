package notice.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.Part;

public class WriteFileService {
	
	public void write(Part part, int no) {
		String path = "c:/projectfiles/notice/" + no;
		File file = new File(path);
		
		if (!file.exists()) {
			file.mkdirs();
		}
		
		try {
			part.write(path + "/" + part.getSubmittedFileName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
