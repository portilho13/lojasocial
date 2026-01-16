package com.example.mobile.presentation.students

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.common.Resource
import com.example.mobile.data.remote.dto.Student
import com.example.mobile.domain.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StudentsState(
    val students: List<Student> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class StudentsViewModel @Inject constructor(
    private val repository: StudentRepository
) : ViewModel() {

    private val _state = MutableStateFlow(StudentsState())
    val state: StateFlow<StudentsState> = _state.asStateFlow()

    fun loadStudents() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            when (val result = repository.getStudents()) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        students = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: "Erro ao carregar estudantes"
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }
    }
}