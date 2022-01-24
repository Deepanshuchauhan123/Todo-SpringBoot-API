package io.spring.todoapi.dto

import javax.annotation.Generated
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.validation.constraints.Size

@Entity
class Folder {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long folderId

	//Users can create folders or categories having name length not more than 40 characters.
	@Size(min= 1, max=40)
	String folderTitle

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="USER_ID", referencedColumnName="id")
	Users user

	public Folder() {
		super()
	}

	

	public Folder(@Size(min = 1, max = 40) String folderTitle, Users user) {
		super();
		this.folderTitle = folderTitle;
		this.user = user;
	}



	public Folder(Long folderId, @Size(min = 1, max = 40) String folderTitle, Users user) {
		super();
		this.folderId = folderId;
		this.folderTitle = folderTitle;
		this.user = user;
	}

	public Long getFolderId() {
		return folderId
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId
	}

	public String getFolderTitle() {
		return folderTitle
	}

	public void setFolderTitle(String folderTitle) {
		this.folderTitle = folderTitle
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
	
}
