package io.spring.todoapi.service

import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.OK

import java.text.DateFormat
import java.text.SimpleDateFormat

import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.transaction.Transactional

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

import io.spring.todoapi.dto.Folder
import io.spring.todoapi.dto.Todo
import io.spring.todoapi.dto.Users
import io.spring.todoapi.repository.FolderRespository
import io.spring.todoapi.repository.TodoRepository

@Service
class TodoService {

	@Autowired
	TodoRepository todoRepository

	@Autowired
	FolderRespository folderRespository

	@Autowired
	EntityManagerFactory entityManagerFactory

	@Autowired
	MyUserDetailsService myUserDetailsService


	/*
	 * Fetch Records based on query param.
	 * 
	 * all: Fetch all records
	 * 
	 * task_id,task_title... : For particular records from all task bundles.
	 * 
	 * @Return A List of Todo Objects.
	 */
	public List getAllTasks(String values){

		if(values.equals("all"))
			values="t"
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		int userId = myUserDetailsService.userId
		List tasks= entityManager.createQuery("SELECT "+values+" from Todo t WHERE t.archive=false AND t.users ="+userId).getResultList()

		if(values.equals("t")) {
			return tasks;
		}else {

			List<Map<String,String>> PartialTasksList = new ArrayList();
			String[] arr = values.split(",")

			for(int taskIndex=0;taskIndex<tasks.size();taskIndex++) {
				Map<String,String> map = new HashMap()

				for(int taskInnerIndex=0;taskInnerIndex<arr.length;taskInnerIndex++) {
					map.put(arr[taskInnerIndex], tasks[taskIndex][taskInnerIndex].toString())
				}

				PartialTasksList.add(map)
			}

			return PartialTasksList;
		}
	}

	/*
	 * Paging our database records.
	 * 
	 * @Param pageSet that show the set number of pages.
	 * 
	 * @Param size that shows number of records in a single set.
	 * 
	 * @Return A list of Todo records with only specified limit.
	 */
	public List<Todo> getPaginationTasks(int pageSet,int size){
		int userId = myUserDetailsService.userId
		Pageable pageable = PageRequest.of(pageSet, size)
		return todoRepository.getPaginationTasks(pageable,userId)
	}

	/*
	 * Search keywords in task objects.
	 * 
	 * @Param value of type String that is keyword to be searched in Task records.
	 * 
	 * @return Task those title or description includes the keyword.
	 */
	public List<Todo> searchTask(String value){
		return todoRepository.searchTask(value);
	}

	/*
	 * Find the task by Id of the Task.
	 * 
	 * @Param id of type integer.
	 * 
	 * @return an Object of task.
	 */
	public Object getTaskById(int id) {
		int userId = myUserDetailsService.userId

		if(todoRepository.checkIfExists(userId, id)>0){
			return todoRepository.findById(id)
		}else {
			ResponseEntity.ok("No Such Task Exists!")
		}
	}

	/*
	 * Find all the task of todo by priority.
	 * 
	 * @Param priority of type String.
	 * 
	 * @Return A List of Todo with same priority.
	 */
	public List<Todo> getTaskByPriority(String priority){
		return todoRepository.findByTaskPriority(priority)
	}

	/*
	 * Find all the task of TODO by category.
	 *
	 * @Param category of type String.
	 *
	 * @Return A List of TODO with same category.
	 */
	public List<Todo> getTaskByCategory(String category){
		return todoRepository.findByTaskCategory(category)
	}


	/*
	 * Add a task in the database.
	 * 
	 * @Param An object of Todo Tasks.
	 */
	public ResponseEntity<String> addTask(Todo todo) {



		Date startDate = todo.getTaskStartDate()
		Date endDate = todo.getTaskEndDate()

		int userId = myUserDetailsService.userId

		Calendar cal = Calendar.getInstance();
		Date date=cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date currentDate=dateFormat.parse(dateFormat.format(date));
		if(todo.taskTitle.equals(null) || todo.taskTitle.equals("")){
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task Title is mandatory field for creating Task!")
		}else
			if(endDate==null || endDate.compareTo(startDate)>=0 && startDate.compareTo(currentDate)>=0 ) {

				if(todo.folder == null) {

					String value = todoRepository.checkIdDefaultFolderExists(userId)
					println(value)
					if(value.equals("Optional.empty")) {
						folderRespository.save(new Folder("Default",new Users(userId,"","")))
					}
					value = todoRepository.checkIdDefaultFolderExists(userId)
					todo.setFolder(new Folder(Integer.parseInt(value.subSequence(9, value.length()-1)+""),"",new Users()))
				}

				Users user = new Users()
				user.setId(userId)

				todo.setUsers(user)
				todoRepository.save(todo)
				ResponseEntity.ok("Task Added successfully!")
			}
			else {
				ResponseEntity.ok("Enter a valid Task Start and End Date!")
			}
	}


	/*
	 * Update a task in the database.
	 *
	 * @Param An object of Todo Tasks.
	 * 
	 * @Param id of type integer.
	 */
	public ResponseEntity<String> updateTaskById(int id, Todo todo) {
		int userId = myUserDetailsService.userId

		if(todoRepository.checkIfExists(userId, id)>0){

			todo.setTaskId(id)
			todo.setUsers(new Users(userId,"",""))
			todoRepository.save(todo)

			ResponseEntity.ok("Task updated Successfully!")
		}else {
			ResponseEntity.ok("Task Not Exists!")
		}
	}

	/*
	 * Delete a task in the database.
	 *
	 * @Param id of type integer.
	 */
	public void deleteTaskById(int id) {
		todoRepository.deleteById(id)
	}

	/*
	 * Add folder to the tasks.
	 * 
	 * @Param folder object of Folder.
	 * 
	 * @return response Entity.
	 */
	public void addFolder(Folder folder){
		int userId = myUserDetailsService.userId
		Users user1 = new Users()
		user1.setId(userId)
		folder.setUser(user1)
		folderRespository.save(folder)
	}

	@Transactional
	public void deleteFolder(Long id) {
		int userId = myUserDetailsService.userId

		todoRepository.deleteByFolderId(id, userId)
		folderRespository.deleteById(id)
	}


	@Transactional
	public ResponseEntity<String> archiveTasks(int folderId, int taskId, boolean archive) {
		int userId = myUserDetailsService.userId
		int value = todoRepository.checkIfExists(userId,taskId)
		if(value > 0) {
			todoRepository.archiveTask(archive, taskId)
			ResponseEntity.status(OK).body("Task archived successfully!")
		}else {
			ResponseEntity.status(BAD_REQUEST).body("No Such Task Exists!")
		}
	}
}
