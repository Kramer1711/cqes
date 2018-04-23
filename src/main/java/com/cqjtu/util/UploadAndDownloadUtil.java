package com.cqjtu.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class UploadAndDownloadUtil {
	/**
	 * 上传文件
	 * 
	 * @param file 文件
	 * @param path 文件保存路径
	 * @return 
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static boolean upload(MultipartFile file,String savepath)  {
		if (!file.isEmpty()) {
			// 上传文件名
			String filename = file.getOriginalFilename();
			File filepath = new File(savepath, filename);
			// 判断路径是否存在，如果不存在就创建一个
			if (!filepath.getParentFile().exists()) {
				filepath.getParentFile().mkdirs();
			}
			// 将上传文件保存到一个目标文件当中
			try {
				file.transferTo(new File(savepath + File.separator + filename));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("上传失败");
				return false;
			}
			System.out.println(filepath+"\t上传成功");
			return true;
		} else {
			System.out.println("文件为空");
			return false;
		}
	}
}
