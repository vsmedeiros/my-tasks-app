package com.example.mytasks.data

import com.example.mytasks.domain.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val dao: TodoDao,
) : TodoRepository {

    override suspend fun insert(title: String, description: String?, id: Long?) {
        val entity = id?.let {
            dao.getBy(it)?.copy(
                title = title,
                description = description
            )
        } ?: TodoEntity(
            title = title,
            description = description,
            isCompleted = false,
        )

        dao.insert(entity)
    }

    override suspend fun updateCompleted(id: Long, isCompleted: Boolean) {
        val existentEntity = dao.getBy(id) ?: return
        val updatedEntity = existentEntity.copy(isCompleted = isCompleted)
        dao.insert(updatedEntity)
    }

    override suspend fun delete(id: Long) {
        val existentEntity = dao.getBy(id) ?: return
        dao.delete(existentEntity)
    }

    override fun getAll(): Flow<List<Todo>> {
        return dao.getAll().map {
            it.map { entity ->
                Todo(
                    id = entity.id,
                    title = entity.title,
                    description = entity.description,
                    isCompleted = entity.isCompleted
                )
            }
        }
    }

    override suspend fun getBy(id: Long): Todo? {
        return dao.getBy(id)?.let { entity ->
            Todo(
                id = entity.id,
                title = entity.title,
                description = entity.description,
                isCompleted = entity.isCompleted
            )
        }
    }
}