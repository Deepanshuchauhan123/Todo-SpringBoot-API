package io.spring.todoapi.dto

class TodoResponse {

	Users user
	Long folderId
	String folderTitle
	List<Todo> todo;

	public TodoResponse(Long folderId, String folderTitle, List<Todo> todo) {
		super();
		this.folderId = folderId;
		this.folderTitle = folderTitle;
		this.todo = todo;
	}

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	public String getFolderTitle() {
		return folderTitle;
	}

	public void setFolderTitle(String folderTitle) {
		this.folderTitle = folderTitle;
	}

	public List<Todo> getTodo() {
		return todo;
	}

	public void setTodo(List<Todo> todo) {
		this.todo = todo;
	}
}
