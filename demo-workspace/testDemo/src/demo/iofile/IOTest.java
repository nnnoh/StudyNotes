package demo.iofile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOTest {
	public static void main(String[] arg) {
		String dst="C:\\Users\\nieyuxu\\Desktop\\test\\dst.txt";
		String src="C:\\Users\\nieyuxu\\Desktop\\test\\src.txt";
		try {
			File dstFile = new File(dst);
			dstFile.delete();
			System.out.println("copy byte number: "+copyFile(dst,src));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int copyFile(String dst,String src) throws IOException {
		
		File srcFile = new File(src);
		File dstFile = new File(dst);
		if (!srcFile.exists()) {
			System.out.println("srcFile not exists");
			return -1;
		}
		if (dstFile.exists()) {
			System.out.println("dstFile exists");
			return -1;
		}
		else if (!dstFile.createNewFile()) {
			System.out.println("dstFile create fail");
			return -1;
		}

		FileInputStream srcIn = new FileInputStream(srcFile);
		BufferedInputStream srcBIn = new BufferedInputStream(srcIn);
		
		FileOutputStream dstOut = new FileOutputStream(dstFile);
		BufferedOutputStream dstBOut = new BufferedOutputStream(dstOut);
		
		byte[] temp=new byte[100];
		int sum=0,count;
		
		while((count=srcBIn.read(temp))!=-1) {
			System.out.println(count+" : ["+new String(temp,0,count,"utf-8")+"]");
			dstBOut.write(temp,0,count);
			sum+=count;
		}
		
		dstBOut.flush();
		dstOut.flush();
		
		srcBIn.close();
		srcIn.close();
		dstBOut.close();
		dstOut.close();
		return sum;
	}
}
