package io.spring.todoapi.repository


import org.hibernate.query.NativeQuery
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.http.ResponseEntity

import io.spring.todoapi.dto.Todo

interface TodoRepository extends JpaRepository<Todo,Integer>{

	@Query(value="FROM Todo WHERE taskPriority = ?1")
	public List<Todo> findByTaskPriority(String priority)

	@Query(value="SELECT * FROM Todo WHERE task_Category = :category", nativeQuery = true)
	public List<Todo> findByTaskCategory(@Param("category") String category)


	//	@Query(value = $/SELECT task_id,task_title FROM Todo/$, nativeQuery = true)
	//	public List<Object[]> findAllTasks()

	@Query(value='SELECT * FROM Todo t WHERE t.task_Title LIKE CONCAT("%",:values,"%") OR t.task_description LIKE CONCAT("%",:values,"%")', nativeQuery = true)
	public List<Todo> searchTask(@Param("values")String values)

	@Query("From Todo WHERE taskId = ?1")
	public Todo findById(int id)


	@Query(value="Select * FROM Todo WHERE user_Id = :userId", nativeQuery = true)
	public List<Todo> getPaginationTasks(Pageable pageable, @Param("userId") int userId)

	@Modifying
	@Query(value="DELETE FROM Todo WHERE folder_Id = ?1 AND user_Id=?2", nativeQuery= true)
	public void deleteByFolderId(Long id, Integer userId)
	
	
	@Query(value="SELECT COUNT(*) FROM TODO WHERE user_Id = ?1 AND task_Id = ?2", nativeQuery = true)
	public int checkIfExists(int userId,int todoId)
	
	@Query(value="SELECT folder_Id from folder WHERE folder_Title='Default' AND user_Id =?1",nativeQuery = true)
	public Optional<Integer> checkIdDefaultFolderExists(int userId)
	
	@Modifying
	@Query(value="UPDATE Todo SET archive =?1 WHERE task_Id = ?2")
	public void archiveTask(boolean archive, int taskId)

}
