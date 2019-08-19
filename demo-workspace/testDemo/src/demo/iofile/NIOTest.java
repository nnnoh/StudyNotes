package demo.iofile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NIOTest {
	public static void main(String[] arg) {
		String tar="C:\\Users\\nieyuxu\\Desktop\\test\\zero.txt";
		createZeroFile(tar,2048);
		try {
			System.out.println("size: "+Files.size(Paths.get(tar)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createZeroFile(String pathname, int size) {
		FileOutputStream fis =null;
		int block=512;
		try {
			fis = new FileOutputStream(pathname);
			FileChannel fc = fis.getChannel();
			
			byte[] zero=new byte[block];
			ByteBuffer buffer=ByteBuffer.wrap(zero);
			
			for(;size>=block;size-=block) {
				fc.write(buffer);
				buffer.position(0);
			}
			
			if(size>0) {
				buffer.position(block-size);
				fc.write(buffer);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
