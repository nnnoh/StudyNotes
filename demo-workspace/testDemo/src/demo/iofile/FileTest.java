package demo.iofile;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import demo.Utils;

public class FileTest {
	public static void main(String[] arg) {
		System.setProperty("user.dir", "C:\\Users\\nieyuxu\\Desktop\\test");
//		fileOpTest();
		pathTest();
	}
	
	public static void fileOpTest() {
		File file=new File("../");
		System.out.println("pwd: "+System.getProperty("user.dir"));
		System.out.println("AbsolutePath: "+file.getAbsolutePath());
		try {
			System.out.println("CanonicalPath: "+file.getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] list=new File("C:\\Users\\nieyuxu\\Desktop").list();
		Utils.printArray(list,"¡¢");
		
		FileFilter dirFilter=File::isDirectory;
		FilenameFilter filenameFilter=(File dir,String name)->{
			return name.endsWith(".lnk");
		};
		
		File[] fileList=file.listFiles(dirFilter);
		Utils.printArray(fileList,"¡¢");
		
		String[] filenameList=file.list(filenameFilter);
		Utils.printArray(filenameList,"¡¢");
	}
	
	public static void pathTest() {
		Path dir = Paths.get("./");
		try {
			System.out.println("real path: "+dir.toRealPath());
			try(Stream<Path> fileTree=Files.walk(dir)){
				fileTree.forEach((Path path)->{
					try {
						System.out.println(path.toRealPath()+"\tsize: "+Files.size(path));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					});
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void pathTest(File file) {
		Path dir = file.toPath();
		try {
			System.out.println("real path: "+dir.toRealPath());
			try(Stream<Path> fileTree=Files.walk(dir)){
				fileTree.forEach(System.out::println);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
