package io.spring.todoapi.dto

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Temporal
import javax.validation.constraints.NotBlank
import org.springframework.boot.context.properties.bind.DefaultValue
import com.fasterxml.jackson.annotation.JsonFormat
import io.spring.todoapi.annotation.CategoryValidation
import io.spring.todoapi.annotation.PriorityValidation
import net.bytebuddy.implementation.bind.annotation.Default

@Entity
class Todo{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int taskId

	private String taskTitle
	private String taskDescription

	@Temporal(javax.persistence.TemporalType.DATE)
	@JsonFormat( pattern="dd-MM-yyyy")
	private Date taskStartDate

	@Temporal(javax.persistence.TemporalType.DATE)
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date taskEndDate

	@PriorityValidation
	private String taskPriority
	@CategoryValidation
	private String taskCategory

	private Boolean archive = false

	@ManyToOne
	@JoinColumn(name="USER_ID", referencedColumnName="id")
	private Users users;

	@ManyToOne
	@JoinColumn(name="FOLDER_ID", referencedColumnName="folderId")
	private Folder folder;

	public Todo() {
		super()
	}

	public Todo(int taskId, @NotBlank(message = "Title of the Task is Mandatory!!") String taskTitle,
	String taskDescription, Date taskStartDate, Date taskEndDate, String taskPriority, String taskCategory,
	Boolean archive, Users users, Folder folder) {
		super();
		this.taskId = taskId;
		this.taskTitle = taskTitle;
		this.taskDescription = taskDescription;
		this.taskStartDate = taskStartDate;
		this.taskEndDate = taskEndDate;
		this.taskPriority = taskPriority;
		this.taskCategory = taskCategory;
		this.archive = archive;
		this.users = users;
		this.folder = folder;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public Date getTaskStartDate() {
		return taskStartDate;
	}

	public void setTaskStartDate(Date taskStartDate) {
		this.taskStartDate = taskStartDate;
	}

	public Date getTaskEndDate() {
		return taskEndDate;
	}

	public void setTaskEndDate(Date taskEndDate) {
		this.taskEndDate = taskEndDate;
	}

	public String getTaskPriority() {
		return taskPriority;
	}

	public void setTaskPriority(String taskPriority) {
		this.taskPriority = taskPriority;
	}

	public String getTaskCategory() {
		return taskCategory;
	}

	public void setTaskCategory(String taskCategory) {
		this.taskCategory = taskCategory;
	}

	public Boolean getArchive() {
		return archive;
	}

	public void setArchive(Boolean archive) {
		this.archive = archive;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}
}
