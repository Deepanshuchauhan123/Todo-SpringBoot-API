package io.spring.todoapi.repository

import org.springframework.data.jpa.repository.JpaRepository

import io.spring.todoapi.dto.Folder

interface FolderRespository extends JpaRepository<Folder,Long> {
	
}
