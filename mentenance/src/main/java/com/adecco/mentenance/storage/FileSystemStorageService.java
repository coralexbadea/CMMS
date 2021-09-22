package com.adecco.mentenance.storage;


import com.adecco.mentenance.domain.Task;
import com.adecco.mentenance.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class FileSystemStorageService {

	private final Path tasksLocation;
	@Autowired
	TaskService taskService;
	

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.tasksLocation = Paths.get(properties.getTasksLocation());

	}

    public FileSystemStorageService() {
		this.tasksLocation = Paths.get("");
    }


	public String store(MultipartFile file, Long pid) throws StorageException, IOException {

		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		Path folder = Paths.get(tasksLocation.getFileName()+"/"+pid);
		checkSecurity(filename);
		checkType(filename);
		try (InputStream inputStream = file.getInputStream()) {
			Files.copy(inputStream, folder.resolve(filename),
					StandardCopyOption.REPLACE_EXISTING);
		}
		return filename;
	}

	public void checkType(String filename) throws StorageException {
		if (!(filename.contains(".jpg") ||
				filename.contains(".png") ||
				filename.contains(".jpeg")||
				filename.contains(".mp4"))) {
			throw new StorageException(
					"Tipul imaginii este incorect "
							+ filename);
		}
	}


	public void checkSecurity(String filename) throws StorageException{
		if (filename.equals("")) {
			throw new StorageException("Failed to store empty file " + filename);
		}
		if (filename.contains("..")) {
			// This is a security check
			throw new StorageException(
					"Cannot store file with relative path outside current directory "
							+ filename);
		}
	}

	public Stream<Path> loadAll(Long id) {
		try {
			return Files.walk(Paths.get(String.valueOf(this.tasksLocation)+"/"+id), 1)
					.filter(path -> !path.toString().equals(String.valueOf(tasksLocation)))
					.map(this.tasksLocation::relativize);
		}
		catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}
	}

	public Path load(String filename) {
		return tasksLocation.resolve(filename);
	}

	public Resource loadAsResource(String filename) {
		try {
			System.out.println("Okk");
			Path file = load(filename);
			System.out.println(file.toString());
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + filename);
			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(tasksLocation.toFile());
	}

	public void init() {
		try {
			Files.createDirectories(tasksLocation);
			List<Task> tasks = taskService.listAll();
			for(Task t: tasks){
				Path personalLocation = Paths.get(tasksLocation.getFileName() + "/" + t.getTid());
				Files.createDirectories(personalLocation);
			}
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}


	public void deleteFolder(Long id){
		Path personalLocation = Paths.get(tasksLocation.getFileName()+"/"+id);
		FileSystemUtils.deleteRecursively(personalLocation.toFile());
	}

	public void deleteFile(String imagePath){
		Path personalLocation = Paths.get(tasksLocation.getFileName()+"/"+imagePath);
		FileSystemUtils.deleteRecursively(personalLocation.toFile());
	}

	public List<Path> getImagePaths(Long tid) throws IOException {
		Stream<Path> paths = Files.walk(Paths.get("tasks"+"\\"+tid+"\\"));
		return paths.filter(p->p.toString().contains("jpg")|| p.toString().contains("png")||p.toString().contains("jpeg")).collect(Collectors.toList());
	}
}
